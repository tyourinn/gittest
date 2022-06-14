package com.example.fruits.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ExceptionEnum {

    ACCOUNT_EXIST(406,"账号已存在！"),
    USER_NAME_NOT_ALLOW_NULL(406,"用户名不能为空！"),
    USER_PASSWORD_NOT_ALLOW_NULL(406,"密码不能为空！"),
    USER_ACCOUNT_NOT_ALLOW_NULL(406,"账号不能为空！"),
    ACCOUNT_NOT_EXIST(406,"账号不存在！！"),
    PASSWORD_ERROR(406,"密码错误！"),

    SYSTEM_NOT_ADD_FRUITS(406,"抱歉，系统未添加水果信息！"),
    FRUITS_INFO_INVALID(406,"抱歉，水果信息已失效！"),
    ADDRESS_INVALID(406,"抱歉，用户地址信息已失效！"),

    SET_DEFAULT_ADDRESS_FAIL(406,"设置默认地址失败！"),
    DELETE_ADDRESS_FAIL(406,"删除地址失败！"),
    ADD_ADDRESS_FAIL(406,"添加地址失败！"),
    ADDR_DELETE_FAIL_EXISTS(406,"地址删除失败,地址被订单信息占用~"),
    MODIFY_ADDR_FAIL(406,"地址修改失败~"),
    ADDRESS_INFO_INVALID(406,"地址信息已失效~，请刷新页面~"),

    ADDRESS_INVALID_CREATE_ORDER_FAIL(406,"选择的地址信息已失效，提交失败！"),

    ORDER_NOT_FOUND(404,"未查询到待付款订单~"),
    ORDER_WAIT_DELIVER_NOT_FOUND(404,"未查询到待发货订单~"),
    ORDER_WAIT_TAKE_NOT_FOUND(404,"未查询到待收货订单~"),
    ORDER_WAIT_REVIEW_NOT_FOUND(404,"未查询到待评论订单~"),

    CART_NOT_FOUND(404,"购物车中还未添加数据~"),
    SHOP_CART_ITEM_INVALID(404,"购物车商品信息失效~"),

    REGISTER_FAILED(500,"注册失败!"),
    SERVICE_IS_BUSY(500,"服务繁忙!"),
    ;
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
