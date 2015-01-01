package com.ryan.doubletrack.customer.dao;

import java.io.Serializable;
import java.util.List;

import com.ryan.doubletrack.customer.entity.Customer;
import com.ryan.framework.dao.BaseHibernateDao;
import com.ryan.framework.entity.Model;
import com.ryan.framework.tag.pagination.Page;

public interface CustomerDao<M extends Model,PK extends Serializable> extends BaseHibernateDao<M,PK>{
	public List<M> findByModel(M model);
	public List<M> findByModel(M model,String orderByProperty, Boolean isAsc);
	public List<M> findByModel(Page page,M model);
	public List<M> findByModel(Page page,M model,String orderByProperty, Boolean isAsc);
	public void deleteByIds(String pks);
	public void updateStatusByIds(String pks,Integer status);
	public List<M> findByParent(M model);
	public List<M> findByIds(String pks);
	public void getCustomerTree(List<M> list,M parentCustomer);
	public String getCustomerTreeHtml(List<Customer> list,Customer parentCustomer,StringBuilder treeHtmlBuilder);
}
