package com.ryan.doubletrack.product.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ryan.framework.dto.Dto;
import com.ryan.framework.entity.Model;
import com.ryan.framework.service.BaseService;
import com.ryan.framework.tag.pagination.Page;

@Transactional
public interface ProductService <T extends Dto,M extends Model,PK extends Serializable> extends BaseService<T,M,PK>{
	public T findByName(String name);	
	public List<T> find(Page page, T dto);
	public void deleteByIds(String[] ids);
}
