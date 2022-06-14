package com.example.fruits.service;

import com.example.fruits.domain.Address;
import com.example.fruits.domain.Users;
import com.example.fruits.enums.ExceptionEnum;
import com.example.fruits.exception.LyException;
import com.example.fruits.mapper.AddressMapper;
import com.example.fruits.utils.UserContextUtil;
import com.example.fruits.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    //获取用户收货地址
    public List<AddressVo> address() {
        //获取用信息
        Users user = UserContextUtil.getUser();
        //根据用户id到数据库地址表中查询信息
//        List<Address> addressList = addressMapper.select(new Address(user.getuId()));
        List<Address> addressList = addressMapper.selectAddress(user.getuId());
        //返回的结果
        List<AddressVo> result = new ArrayList<>();
        for (Address address : addressList) {
            String phone = address.getPhone().substring(0,3);
            phone += "****";
            phone += address.getPhone().substring(8);
            address.setPhone(phone);
            result.add(new AddressVo(address));
        }
        return result;
    }

    //设置用户默认收货地址
    public void setDefault(Integer addId) {
        //获取用户信息
        Users user = UserContextUtil.getUser();
        //根据用户id查询 之前的默认收货地址
        List<Address> addresses = addressMapper.select(new Address(user.getuId(), true));
        if(CollectionUtils.isEmpty(addresses)){ //地址未查找到
            throw new LyException(ExceptionEnum.ADDRESS_INVALID);
        }
        //获取地址信息
        Address addressBeforeDefault = addresses.get(0);
        //将之前的默认收货地址取消
        addressBeforeDefault.setIsDefault(false);
        //持久化修改
        int flag = addressMapper.updateByPrimaryKey(addressBeforeDefault);
        if(flag<=0){    //修改失败 ,之前设置默认地址的 地址信息 未查找到
            throw new LyException(ExceptionEnum.SET_DEFAULT_ADDRESS_FAIL);
        }
        //根据地址id和用户id 查询信息 要设置成默认地址的 信息
        addresses = addressMapper.select(new Address(user.getuId(), addId));
        if(CollectionUtils.isEmpty(addresses)){ //页面中存在信息，数据库中未查找到
            throw new LyException(ExceptionEnum.ADDRESS_INVALID);
        }
        //获取地址信息
        Address address = addresses.get(0);
        //设置为默认收货地址
        address.setIsDefault(true);
        //将该项记录 设置默认地址
        flag = addressMapper.updateByPrimaryKey(address);
        if(flag<=0){    //修改失败
            throw new LyException(ExceptionEnum.SET_DEFAULT_ADDRESS_FAIL);
        }
    }

    //获取一条地址信息
    public AddressVo getAddressOne(Integer addId) {
        Address address = addressMapper.selectByPrimaryKey(addId);
        if(address == null){//未查询到信息
            throw new LyException(ExceptionEnum.ADDRESS_INVALID);
        }
        //获取用户信息
        Users user = UserContextUtil.getUser();
        if(address.getuId() != user.getuId()){  //用户已登录，但该条记录并不是该用户所查询
            return null;
        }
        return new AddressVo(address);
    }

    //删除地址
    public void deleteAddr(Integer addId) {
        if(existsAddId(addId)){
            throw new LyException(ExceptionEnum.ADDR_DELETE_FAIL_EXISTS);
        }
        Address address = addressMapper.selectByPrimaryKey(addId);
        if(address==null){
            throw new LyException(ExceptionEnum.ADDRESS_INFO_INVALID);
        }

//        int flag = addressMapper.deleteByPrimaryKey(addId);
//        if(flag<=0){
//            throw new LyException(ExceptionEnum.DELETE_ADDRESS_FAIL);
//        }
        addressMapper.deleteAddress(addId);
    }


    //地址是否使用过
    private boolean existsAddId(Integer addId){
        return addressMapper.exists(addId);
    }

    //添加收货地址
    public void saveAddr(Address addressV) {
        //获取用户信息
        Users user = UserContextUtil.getUser();
        //根据用户id查询 之前的默认收货地址
        List<Address> addresses = addressMapper.select(new Address(user.getuId(), true));
        if(CollectionUtils.isEmpty(addresses)){ //地址未查找到
            addressV.setIsDefault(true);
        }else{
            addressV.setIsDefault(false);
        }
        addressV.setUId(user.getuId());
        int count = addressMapper.insert(addressV);
        if(count<=0){
            throw new LyException(ExceptionEnum.ADD_ADDRESS_FAIL);
        }
    }

    //修改地址信息
    public void modifyAddr(Address addressV) {
        if(existsAddId(addressV.getAddId())){
            throw new LyException(ExceptionEnum.ADDR_DELETE_FAIL_EXISTS);
        }
        int flag = addressMapper.updateByPrimaryKeySelective(addressV);
        if(flag<=0){
            throw new LyException(ExceptionEnum.MODIFY_ADDR_FAIL);
        }
    }

    //获取用户手机号
    public String addPhone(Integer addId) {
        Address address = addressMapper.selectByPrimaryKey(addId);
        if(address!=null){
            return  address.getPhone();
        }
        return "";
    }
}
