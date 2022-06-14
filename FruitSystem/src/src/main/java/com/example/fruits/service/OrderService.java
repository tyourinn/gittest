package com.example.fruits.service;

import com.example.fruits.bean.ResultInfo;
import com.example.fruits.domain.*;
import com.example.fruits.enums.ExceptionEnum;
import com.example.fruits.exception.LyException;
import com.example.fruits.mapper.*;
import com.example.fruits.utils.NumberUtils;
import com.example.fruits.utils.RedisUtil;
import com.example.fruits.utils.UserContextUtil;
import com.example.fruits.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private FruitsMapper fruitsMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${order.orderExpire}")
    private Long orderExpire;    //订单过期时间

    @Value("${order.orderExpirePre}")
    private String orderExpirePre;    //订单过期时间 key 前缀

    //根据商品fId，获取订单详情数据
    @Transactional
    public OrderDetailQuickVo orderDetailByFId(Integer fId) {
        //获取用户信息
        Users user = UserContextUtil.getUser();
        //获取用户默认收货地址
        List<Address> addressList = addressMapper.select(new Address(user.getuId(), true));
        //获取水果信息
        Fruits fruits = fruitsMapper.selectByPrimaryKey(fId);
        if(fruits == null){ //水果信息被删除
            throw new LyException( ExceptionEnum.FRUITS_INFO_INVALID);
        }
        //封装结果集
        OrderDetailQuickVo orderDetailVo = new OrderDetailQuickVo();
        //封装收货地址
        if(CollectionUtils.isEmpty(addressList)){   //没有收货地址
            orderDetailVo.setAddress(null);
        }else{
            orderDetailVo.setAddress(new AddressVo(addressList.get(0)));
        }
        //封装成list
        ArrayList<FruitsVo> fruitsVos = new ArrayList<>();
        //添加商品信息
        fruitsVos.add(new FruitsVo(fruits,1));

        //封装水果信息
        orderDetailVo.setFruits(fruitsVos);
        //封装总额
        orderDetailVo.setTotalMoney(fruits.getPriceOff()/10.0);
        //返回结果集
        return orderDetailVo;
    }

    /**
     * 提交订单
     * @param addId 地址id
     * @param fruitsVos 只有 水果id和数量信息
     */
    @Transactional
    public Integer addOrder(Integer addId, List<FruitsVo> fruitsVos,HttpServletResponse response) {
        //--获取 地址信息(address)--
        // 查询 地址信息
        Address address = addressMapper.selectByPrimaryKey(addId);
        //地址信息是否存在
        if(address==null){  //地址信息为查询到
            throw new LyException(ExceptionEnum.ADDRESS_INVALID_CREATE_ORDER_FAIL);
        }

        //--获取 水果信息并计算订单金额(fruits)--
        // 根据 fruitsVo的水果fId 查询到的水果结果集
        List<Fruits> fruitsList = new ArrayList<>();
        //计算总金额 单位：毛
        int totalMoney = 0;
        //遍历累加计算总金额
        for (FruitsVo fruitsVo : fruitsVos) {
            //根据水果id查询水果信息
            Fruits fruits = fruitsMapper.selectByPrimaryKey(fruitsVo.getFId());
            //计算总金额
            totalMoney += fruits.getPriceOff()*fruitsVo.getNum();
            //添加到list
            fruitsList.add(fruits);
            //删除购物车中 的项目
            shopCartMapper.delete(new ShopCart(UserContextUtil.getUser().getuId(),fruits.getFId()));
        }

        //--插入 订单数据(T_Order)--
        // 生成 订单编号
        String out_trade_no = NumberUtils.getOrderNo();
        //获取 用户信息
        Users user = UserContextUtil.getUser();
        //封装 订单信息 //收货地址id //订单编号 //订单总金额 //用户id
        TOrder order = new TOrder(addId,out_trade_no,totalMoney,user.getuId());
        //持久化 订单信息到数据库 订单表
        orderMapper.insertSelective(order);

        //--插入 订单详情（Order_Item）--
        // 获取 插入之后的订单oId
        Integer oId = order.getOId();
        //封装Order_item订单详细信息
        for (int index=0;index<fruitsList.size();index++) {
            //获取水果信息 数据库中
            Fruits fruits = fruitsList.get(index);
            //水果信息 前端传递
            FruitsVo fruitsVo = fruitsVos.get(index);
            //封装Order_item信息
            OrderItem orderItem = new OrderItem();
            //订单id
            orderItem.setOId(oId);
            //水果Id
            orderItem.setFId(fruits.getFId());
            //购买数量 前端传递过来的
            orderItem.setNum(fruitsVo.getNum());
            //商品价格
            orderItem.setPrice(fruits.getPriceOff());
            //商品标题
            orderItem.setTitle(fruits.getTitle()+" "+fruits.getSpecification());
            //持久化 订单详情
            orderItemMapper.insertSelective(orderItem);
        }

        //--设置 订单过期时间(Redis)--
        // 设置 redis中 订单过期时间 秒
        Long timeout_express = orderExpire;
        //设置 订单 过期的 key
        String key = orderExpirePre + oId;
        //将订单id作为 key 设置过期时间
        redisUtil.setSetWithTime(key,timeout_express,"");

        //--返回 插入后的订单oId--
        return oId;
    }

    /**
     *  根据订单oId 查询待付款信息
     * @param oId 订单主键
     * @return OrderWaitPayVo
     */
    public OrderWaitPayVo orderWaitPay(Integer oId) {
        //获取用户信息
        Users user = UserContextUtil.getUser();
        //--获取 待付款订单信息（T_Order）--
        // 根据 订单oId和 和用户uId 查询订单信息
        List<TOrder> orderList = orderMapper.select(new TOrder(oId,user.getuId()));
        if(CollectionUtils.isEmpty(orderList)){ //订单不存在，或订单已过期，被删除
            throw new LyException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        //获取订单信息
        TOrder order = orderList.get(0);
        return orderWaitPayTrue(true,true,order);
    }

    /**
     * 跟具订单 order对象封装 OrderWaitPayVo
     * @param isAddr 是否封装地址信息
     * @param isExpire 是否封装 订单过期时间
     * @param order 订单对象
     * @return
     */
    @Transactional
    public OrderWaitPayVo orderWaitPayTrue(Boolean isAddr,Boolean isExpire,TOrder order) {

        //--获取 订单详情(Order_Item)--
        // 到订单详情表中 根据订 单 oId 查询订单详情
        List<OrderItem> orderItemList = orderItemMapper.select(new OrderItem(order.getOId()));
        if(CollectionUtils.isEmpty(orderItemList)){ //存在订单信息，不存在订单详情
            throw new LyException(ExceptionEnum.SERVICE_IS_BUSY);
        }

        //--VO 封装 水果购买信息--
        // 水果购买信息 结果集 VO
        List<FruitsVo> fruitsVoList = new ArrayList<>();
        //封装 水果购买信息 VO
        for (OrderItem orderItem : orderItemList) {
            //根据订单详情中的水果fId查询 水果信息
            Fruits fruits = fruitsMapper.selectByPrimaryKey(orderItem.getFId());
            //获取水果搜索小图
            String image = fruits.getImage();
            //封装 水果VO
            FruitsVo fruitsVo = new FruitsVo();
            //水果fId
            fruitsVo.setFId(fruits.getFId());
            //水果图片
            fruitsVo.setImage(image);
            //水果 购买时的价格
            fruitsVo.setPrice(orderItem.getPrice()/10.0);
            //水果 购买时的标题
            fruitsVo.setTitle(orderItem.getTitle());
            //水果 购买数量
            fruitsVo.setNum(orderItem.getNum());
            //添加到 水果购买信息 fruitsVoList中
            fruitsVoList.add(fruitsVo);
        }

        //--VO封装 返回的结果集--
        OrderWaitPayVo orderWaitPayVo = new OrderWaitPayVo();
        if(isAddr){   //是否添加地址信息
            //--获取 地址信息(address)--
            // 根据订单表（order）中的收货地址oId，到地址表中查询地址信息
            Address address = addressMapper.selectByPrimaryKey(order.getAddId());
            //--VO 封装 收货地址 -
            AddressVo addressVoList = new AddressVo(address);
            //收货地址 VO
            orderWaitPayVo.setAddress(addressVoList);
        }

        //水果购买信息 VO
        orderWaitPayVo.setFruits(fruitsVoList);
        //订单金额
        orderWaitPayVo.setTotalMoney(order.getTotalMoney()/10.0);
        //订单 oId
        orderWaitPayVo.setOId(order.getOId());
        //判断订单是否支付
        if(order.getIsPlay()){//已支付
            //封装已支付标识
            orderWaitPayVo.setIsPay(true);
            //支付时间
            orderWaitPayVo.setOTime(order.getOTime());
        }else{
            if(isExpire){   //是否获取订单过期时间
                //-- 获取 订单剩余过期时间
                //根据订单 oid 到redis中获取 剩余过期时间 毫秒
                long orderExpire = redisUtil.getExpire(orderExpirePre + order.getOId() + "")*1000;
                //订单剩余过期时间
                orderWaitPayVo.setOrderExpire(orderExpire);
            }
        }
        return orderWaitPayVo;
    }

    /**
     * 订单过期 调用
     * 根据 oId删除订单信息和订单详情
     * @param oId
     */
    @Transactional
    public void orderExpire(Integer oId) {
        //根据订单oId删除订单信息（order）
        orderMapper.deleteByPrimaryKey(oId);
        //根据订单oId删除订单详情(order_item)
        orderItemMapper.delete(new OrderItem(oId));
    }

    //确认支付
    @Transactional
    public ResultInfo payOrder(Integer oId) {
        //返回的结果集
        ResultInfo resultInfo = null;

        //--获取订单信息(order)--
        //根据 oId获取订单信息
        TOrder order = orderMapper.selectByPrimaryKey(oId);
        //订单是否存在
        if(order == null){  //订单已过期，或不存在
            throw new LyException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        //订单是否已支付
        if(order.getIsPlay()){  //订单已支付
           resultInfo = new ResultInfo(false,"订单已支付！");
        }else{
            resultInfo = new ResultInfo(true,"支付成功！");
        }
        //修改 isPlay 字段为true
        order.setIsPlay(true);
        //设置 支付时间
        order.setOTime(new Timestamp(new Date().getTime()));
        //设置返回结果中的时间
        Map<String,String> map = new HashMap<>();
        String oTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getOTime().getTime()));
        map.put("oTime",oTime);
        resultInfo.setResult(map);
        //持久化 订单
        orderMapper.updateByPrimaryKey(order);

        //设置redis中订单过期
        String key = orderExpirePre+order.getOId();
        redisUtil.stringDelete(key);
        return resultInfo;
    }

    //根据购物车scId，获取订单详情数据
    @Transactional
    public OrderDetailQuickVo orderDetailByScId(Integer[] scIds) {
        //获取用户信息
        Users user = UserContextUtil.getUser();
        //获取用户默认收货地址
        List<Address> addressList = addressMapper.select(new Address(user.getuId(), true));
        //总额
        Double totalMoney = 0.0;
        //水果信息
        List<FruitsVo> fruitsVoList = new ArrayList<>();
        //--获取购物车信息--
        for (Integer scId : scIds) {
            ShopCart shopCart = shopCartMapper.selectByPrimaryKey(scId);
            if(shopCart == null){
                continue;
            }
            //根据水果id查询水果信息
            Fruits fruits = fruitsMapper.selectByPrimaryKey(shopCart.getfId());
            if(fruits == null){
                continue;
            }
            fruitsVoList.add(new FruitsVo(fruits,shopCart.getNum()));
            //累加总额
            double money = fruits.getPriceOff()/10.0*shopCart.getNum();
            totalMoney += money;
        }
        OrderDetailQuickVo orderDetailVo = new OrderDetailQuickVo();
        if(CollectionUtils.isEmpty(addressList)){   //没有收货地址
            orderDetailVo.setAddress(null);
        }else{
            orderDetailVo.setAddress(new AddressVo(addressList.get(0)));
        }
        orderDetailVo.setFruits(fruitsVoList);
        orderDetailVo.setTotalMoney(totalMoney);
        return orderDetailVo;
    }

    //用户中心
    @Transactional
    public MyCenterVo UserCenter() {
        Users user = UserContextUtil.getUser();
        MyCenterVo centerVo = new MyCenterVo(user.getUserName(), user.getHeadImage(),oderStatusNum());
        return centerVo;
    }

    //获取用户待付款订单信息
    public OrderStatusItem orderWaitPayByUser() {
        List<OrderWaitPayVo> resultList= new ArrayList<>();
        //获取用户信息
        Users user = UserContextUtil.getUser();
        //--获取 待付款订单信息（T_Order）--
        List<TOrder> orderList = orderMapper.select(new TOrder(user.getuId(),false));
        if(CollectionUtils.isEmpty(orderList)){ //订单不存在，或订单已过期，被删除
            throw new LyException(ExceptionEnum.ORDER_NOT_FOUND);
        }
        //获取订单信息
        for (TOrder order : orderList) {
            OrderWaitPayVo orderWaitPayVo = orderWaitPayTrue(false, true, order);
            resultList.add(orderWaitPayVo);
        }
        OrderStatusItem orderStatusItem = new OrderStatusItem(resultList,oderStatusNum());
        return orderStatusItem;
    }

    //取消订单
    @Transactional
    public void deleteOrder(Integer oId) {
        //数据库中删除信息
        orderMapper.deleteByPrimaryKey(oId);
        //订单详情表中删除信息
        orderItemMapper.delete(new OrderItem(oId));
        //redis 删除 该订单 id信息
        redisUtil.stringDelete(orderExpirePre+oId);
    }

    //获取用户待发货订单信息
    public OrderDetailItem orderWaitDeliverByUser() {
        return orderWaitByUser(false,true,false,false,ExceptionEnum.ORDER_WAIT_DELIVER_NOT_FOUND);
    }

    //获取用户待收货订单信息
    public OrderDetailItem orderWaitTakeByUser() {
        return orderWaitByUser(true,true,false,false,ExceptionEnum.ORDER_WAIT_TAKE_NOT_FOUND);
    }

    //获取用户待评价订单信息
    public OrderDetailItem orderWaitReviewByUser() {
        return orderWaitByUser(true,true,false,true,ExceptionEnum.ORDER_WAIT_REVIEW_NOT_FOUND);
    }

    //获取待操作订单 用户
    public OrderDetailItem orderWaitByUser(Boolean isDeliver,Boolean isPay,Boolean isReview,Boolean isTake,ExceptionEnum exceptionEnum){
        return orderWait(isDeliver,isPay,isReview,isTake,false,exceptionEnum);
    }

    //获取待操作订单
    @Transactional
    public OrderDetailItem orderWait(Boolean isDeliver,Boolean isPay,Boolean isReview,Boolean isTake,boolean allUser,ExceptionEnum exceptionEnum){
        List<OrderDetailVo> resultList = new ArrayList<>();
        //获取用户信息
        Users user = UserContextUtil.getUser();
        List<OrderItem> orderItemList = null;
        if(allUser){    //查询所有用户的信息
            //查询 未发货信息 Order_item
           orderItemList = orderItemMapper.orderWaitDeliverByUserAll(isDeliver,isPay,isReview,isTake);
        }else{
            //查询 未发货信息 Order_item
            orderItemList = orderItemMapper.orderWaitDeliverByUser(user.getuId(), isDeliver,isPay,isReview,isTake);
        }

        Map<String,String> map = new HashMap<>();
        if(CollectionUtils.isEmpty(orderItemList)){ //没有待发货信息
            throw new LyException(exceptionEnum);
        }
        for (OrderItem orderItem : orderItemList) { //遍历未发货的订单信息
            OrderDetailVo orderDetailVo = new OrderDetailVo();
            String oTime = map.get(orderItem.getOId()+"");
            if(StringUtils.isBlank(oTime)){ //设置 订单时间
                //根据 oid 查询订单信息 Order
                TOrder order = orderMapper.selectByPrimaryKey(orderItem.getOId());
                //格式化订单时间
                String oTimeStr = "";
                if(allUser){
                    oTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(order.getOTime().getTime()));
                }else{
                    oTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date(order.getOTime().getTime()));
                }
                map.put(order.getOId()+"", oTimeStr);
                //设置订单时间
                orderDetailVo.setOTime(oTimeStr);
            }else{  //设置订单时间
                orderDetailVo.setOTime(oTime);
            }
            //订单详情
            orderDetailVo.setOiId(orderItem.getOiId());
            //订单 oId
            orderDetailVo.setOId(orderItem.getOId());
            //计算价格
            Double totalMoney = orderItem.getPrice()/10.0*orderItem.getNum();
            orderDetailVo.setTotalMoney(totalMoney);
            //水果信息
            FruitsVo fruitsVo = new FruitsVo();
            fruitsVo.setNum(orderItem.getNum());
            fruitsVo.setTitle(orderItem.getTitle());
            Fruits fruits = fruitsMapper.selectByPrimaryKey(orderItem.getFId());
            if(fruits == null){
                throw new LyException(ExceptionEnum.FRUITS_INFO_INVALID);
            }
            fruitsVo.setImage(fruits.getImage());
            fruitsVo.setFId(fruits.getFId());
            fruitsVo.setPrice(fruits.getPriceOff()/10.0);
            orderDetailVo.setFruits(fruitsVo);
            resultList.add(orderDetailVo);
        }
        //返回的结果集
        OrderDetailItem orderDetailItem = new OrderDetailItem();
        //各状态订单 数量
        orderDetailItem.setOrderStatusNum(oderStatusNum());
        orderDetailItem.setOrderDetail(resultList);
        return orderDetailItem;
    }

    //确认收货
    public void confirmTake(Integer oiId) {
        orderItemMapper.updateByPrimaryKeySelective(new OrderItem().setOiId(oiId).setTake(true));
    }

    //获取用户各订单状态
    public OrderStatusNum oderStatusNum() {
        return oderStatusNumTrue(false);
    }

    //获取用户各订单状态 实现
    public OrderStatusNum oderStatusNumTrue(Boolean allUser) {
        //--获取用户信息--
        Users user = UserContextUtil.getUser(); //获取待付款订单
        Integer waitPayNum = 0;

        if(allUser){    //查询所有用户
            waitPayNum = orderMapper.selectCount(new TOrder(false));
        }else{
            waitPayNum = orderMapper.selectCount(new TOrder(user.getuId(), false));
        }
        //--获取待发货订单--
        //待发货数量
        Integer waitDeliverNum = 0;
        //待收货数量
        Integer waitTakeNum = 0;
        //待评价数量
        Integer  waitReviewNum = 0;
        //获取用户已支付的订单
        List<TOrder> orderList = orderMapper.select(new TOrder(user.getuId(), true));
        if(!CollectionUtils.isEmpty(orderList)){ //有待支付的订单
            for (TOrder order : orderList) {
                //获取该订单下 未发货的信息
                int count = orderItemMapper.selectCount(new OrderItem(false, order.getOId()));
                waitDeliverNum += count;
                //获取待收货订单
                count = orderItemMapper.selectCount(new OrderItem(true, order.getOId(),false));
                waitTakeNum += count;
                //待评价数量
                count = orderItemMapper.selectCount(new OrderItem(true, order.getOId(),true,false));
                waitReviewNum += count;
            }
        }
        OrderStatusNum orderStatusNum = new OrderStatusNum( waitPayNum, waitDeliverNum, waitTakeNum, waitReviewNum);
        return orderStatusNum;
    }

}
