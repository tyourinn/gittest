package com.example.fruits.service;

import com.example.fruits.bean.Page;
import com.example.fruits.domain.*;
import com.example.fruits.enums.ExceptionEnum;
import com.example.fruits.exception.LyException;
import com.example.fruits.mapper.*;
import com.example.fruits.utils.UserContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class FruitsService {

    @Autowired
    private FruitsMapper fruitsMapper;

    @Autowired
    private FruitsImagesMapper fruitsImagesMapper;

    @Autowired
    private FruitsImageDetailsMapper fruitsImageDetailsMapper;

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ShopCartMapper shopCartMapper;

    //首页分页获取水果信息，并可搜索
    public Page<Fruits> index(Integer page, Integer size, String search) {
        if(StringUtils.isBlank(search)){    //分页获取水果信息
            return fruits(page,size);
        }else{  //根据search搜索水果信息
            return search(search);
        }
    }

    //分页获取水果信息
    public Page<Fruits> fruits(Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);
        //查询
        List<Fruits> fruitsList = fruitsMapper.selectAll();
        if (CollectionUtils.isEmpty(fruitsList)){//系统未添加水果信息
            throw new LyException(ExceptionEnum.SYSTEM_NOT_ADD_FRUITS);
        }
        //填充水果图片
        fillImage(fruitsList);
        return new Page<>(new PageInfo(fruitsList));
    }

    //搜索水果信息
    public Page<Fruits> search(String search) {
        Example example = new Example(Fruits.class);
        Example.Criteria criteria = example.createCriteria();
        //根据标题模糊查询
        criteria.andLike("title", "%"+search+"%");
        //查询
        List<Fruits> fruitsList = fruitsMapper.selectByExample(example);
        //填充水果图片
        fillImage(fruitsList);
        return new Page<>(new PageInfo(fruitsList));
    }

    //填充水果图片
    public void fillImage(List<Fruits> fruitsList){
        for (Fruits fruits : fruitsList) {
            //填充水果图片
            fruits.setFruitsImages(getFruitsImagesById(fruits.getFId()));
        }
    }

    //填充水果详情图片
    public void fillFruitsImageDetails(List<Fruits> fruitsList){
        for (Fruits fruits : fruitsList) {
            //填充水果详情图片
            fruits.setFruitsImageDetails(getFruitsImageDetailsById(fruits.getFId()));
        }
    }

    //填充水果图片和水果详情图片
    public void fillImageAndDetailImage(List<Fruits> fruitsList){
        for (Fruits fruits : fruitsList) {
            //填充水果图片
            fruits.setFruitsImages(getFruitsImagesById(fruits.getFId()));
            //填充水果详情图片
            fruits.setFruitsImageDetails(getFruitsImageDetailsById(fruits.getFId()));
        }
    }

    //根据水果id查询水果图片
    public List<FruitsImages> getFruitsImagesById(Integer fId){
        return fruitsImagesMapper.select(new FruitsImages(fId));
    }

    //根据水果id查询水果详情图片
    public List<FruitsImageDetails> getFruitsImageDetailsById(Integer fId){
        return fruitsImageDetailsMapper.select(new FruitsImageDetails(fId));
    }

    //根据水果id获取水果详细信息
    public Fruits detail(Integer fId) {
        //查询水果信息
        Fruits fruits = fruitsMapper.selectByPrimaryKey(fId);
        if(fruits == null){ //水果信息被删除
            throw new LyException(ExceptionEnum.FRUITS_INFO_INVALID);
        }
        //填充水果图片
        fruits.setFruitsImages(getFruitsImagesById(fId));
        //填充水果详情图片
        fruits.setFruitsImageDetails(getFruitsImageDetailsById(fId));
        //获取该商品评论信息
        List<Review> reviews = reviewMapper.select(new Review(fId));
        //根据评论信息获取用户信息
        for (Review review : reviews) {
            Users users = usersMapper.selectByPrimaryKey(review.getUId());
            if(review.getIsAnonymous()){    //匿名评论
                String userName = users.getUserName().substring(0,1);
                for(int i = 1;i<users.getUserName().length();i++){
                    userName += "*";
                }
                users.setUserName(userName);
            }
            review.setUsers(users);
        }
        //封装评论信息
        fruits.setReviews(reviews);
        //获取当前用户信息
        Users user = UserContextUtil.getUser();
        if(user!=null){
            //根据用户id查询该用户购物车商品数量
            int shopCartNum = shopCartMapper.selectCount(new ShopCart(user.getuId()));
            //封装该用户购物车商品数量
            fruits.setShopCartNum(shopCartNum);
        }
        return fruits;
    }
}
