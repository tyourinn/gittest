package com.example.fruits.service;

import com.example.fruits.domain.Fruits;
import com.example.fruits.domain.ShopCart;
import com.example.fruits.domain.Users;
import com.example.fruits.enums.ExceptionEnum;
import com.example.fruits.exception.LyException;
import com.example.fruits.mapper.FruitsMapper;
import com.example.fruits.mapper.ShopCartMapper;
import com.example.fruits.utils.UserContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Autowired
    private FruitsMapper fruitsMapper;

    //加入购物车
    @Transactional
    public boolean addItem(Integer fId) {
        //获取用户信息
        Users user = UserContextUtil.getUser();
        //根据用户id和水果id相等
        List<ShopCart> shopCartList = shopCartMapper.select(new ShopCart(user.getuId(), fId));
        //根据商品id查询商品信息
        Fruits fruits = fruitsMapper.selectByPrimaryKey(fId);
        if(CollectionUtils.isEmpty(shopCartList)){  // 不存在
            //封装购物车信息
            ShopCart shopCart = new ShopCart(user.getuId(),fId,1);
            //新增购物车项
            shopCartMapper.insert(shopCart);
            return true;
        }else{  //存在
            //获取购物车信息
            ShopCart shopCartDB = shopCartList.get(0);
            //将商品数量+1
            shopCartDB.setNum(shopCartDB.getNum()+1);
            //持久化更新
            shopCartMapper.updateByPrimaryKey(shopCartDB);
            return false;
        }
    }

    //获取用户购物车信息
    @Transactional
    public List<ShopCart> userCart() {
        //--获取用户信息--
        Users user = UserContextUtil.getUser();
        //--获取购物车信息（shopCart）--
        //根据用户uId到数据库购物车表中查询数据
        List<ShopCart> cartList = shopCartMapper.select(new ShopCart(user.getuId()));
        //是否存在购物车信息
        if(CollectionUtils.isEmpty(cartList)){  //不存在购物车信息
            throw new LyException(ExceptionEnum.CART_NOT_FOUND);
        }
        //封装购物车 商品数据
        for (ShopCart shopCart : cartList) {
            Fruits fruits = fruitsMapper.selectByPrimaryKey(shopCart.getfId());
            if(fruits == null){ //订单未查询到，数据库fruits表中没有该数据
                //删除该记录
                shopCartMapper.deleteByPrimaryKey(shopCart.getScId());
                continue;
            }
            shopCart.setFruits(fruits);
        }
        return cartList;
    }

    //购物车条目购买数量增加
    public void cartItemNumAdd(Integer scId) {
        cartItemNum(scId,1);
    }

    //购物车条目购买数量减少

    public void cartItemNumReduce(Integer scId) {
        cartItemNum(scId,-1);
    }

    //购物车条目购买数量减少 或 增加
    @Transactional
    public void cartItemNum(Integer scId,Integer num){
        ShopCart shopCart = shopCartMapper.selectByPrimaryKey(scId);
        if(shopCart==null){ //购物车商品信息失效
            throw new LyException(ExceptionEnum.SHOP_CART_ITEM_INVALID);
        }
        shopCart.setNum(shopCart.getNum()+num);
        shopCartMapper.updateByPrimaryKey(shopCart);
    }

    //删除购物车项
    @Transactional
    public void deleteItem(Integer[] scIds) {
        for (Integer scId : scIds) {
            shopCartMapper.deleteByPrimaryKey(scId);
        }
    }
}
