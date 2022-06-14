package com.example.fruits.bean;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Page<T> {
    
	private int totalRow;	//总记录数
	private int totalPage;	//总页数
	private int size;	//每页的数量
    private List<T> rows;//当前页数据

	public Page(PageInfo pageInfo) {
		this.totalRow = (int) pageInfo.getTotal();	//总记录数
		this.totalPage = pageInfo.getPages();	//总页数
		this.size = pageInfo.getPageSize();	//每页的数量
		this.rows = pageInfo.getList();		//当前页数据
	}

}
