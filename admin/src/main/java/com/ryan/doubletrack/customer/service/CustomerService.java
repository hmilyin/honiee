package com.ryan.doubletrack.customer.service;

import java.io.Serializable;
import java.util.List;

import com.ryan.framework.dto.Dto;
import com.ryan.framework.entity.Model;
import com.ryan.framework.service.BaseService;
import com.ryan.framework.tag.pagination.Page;

public interface CustomerService <T extends Dto,M extends Model,PK extends Serializable> extends BaseService<T,M,PK>{
	//根据姓名查询
	public T findByName(String name);	
	//按分页的方式查询和查询条件
	public List<T> find(Page page, T dto);
	//批量删除
	public void deleteByIds(String[] ids);
	//冻结客户
	public void freezeByIds(String[] ids);
	//解冻客户
	public void thawByIds(String[] ids);
	//根据id的列表查询
	public List<T> findByIds(String idsStr);
	//获取客户树页面左边
	public List<String> getCustomerTreeHtmlList();
	//
	public PK max(String propertyName, T dto);
	//生成客户编码
	public String generateCustomerCode();
}
