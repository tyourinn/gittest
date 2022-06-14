package com.example.fruits.vo;

import com.example.fruits.domain.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public  class AddressVo{

    private Integer addId;
    private String name;
    private String phone;
    private String addressDetail;
    private String addr;
    private String addDetails;
    private boolean idShow;
    private Boolean isDefault;

    public AddressVo(Address address) {
        this.addId = address.getAddId();
        this.name = address.getName();
        this.phone = address.getPhone();
        this.addr = address.getAddr();
        this.addDetails = address.getAddDetails();
        this.addressDetail = address.getAddr()+" "+address.getAddDetails();
        this.isDefault = address.getIsDefault();
    }

}