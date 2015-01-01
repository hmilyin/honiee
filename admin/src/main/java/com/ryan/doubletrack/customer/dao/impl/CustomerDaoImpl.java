package com.ryan.doubletrack.customer.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ryan.doubletrack.customer.dao.CustomerDao;
import com.ryan.doubletrack.customer.entity.Customer;
import com.ryan.framework.dao.impl.BaseHibernateDaoSupport;
import com.ryan.framework.entity.Tree;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.StringUtil;

@Repository
public class CustomerDaoImpl extends BaseHibernateDaoSupport<Customer, Long> implements CustomerDao<Customer,Long> {


	public List<Customer> findByParent(Customer customer){
		List<Criterion> criterionList=new ArrayList<Criterion>();
		if(!StringUtil.isEmpty(customer.getCustomerCode())){
			criterionList.add(Restrictions.eq("parentCustomer", customer.getParentCustomer()));
		}
		List<Customer> customerList = this.find(criterionList);
		return customerList;
	}

	
	public List<Customer> findByModel(Customer model) {
		return this.findByModel(model,null,null);
	}

	
	public List<Customer> findByModel(Page page, Customer model) {
		return this.findByModel(page,model,null,null);
	}
	
	
	public List<Customer> findByModel(Customer customer, String orderByProperty,Boolean isAsc) {
		if(null!=orderByProperty&&null!=isAsc){
			List<Customer> customerList = this.find(orderByProperty,isAsc,generateCriterionList(customer));
			return customerList;
		}else{
			List<Customer> customerList = this.find(generateCriterionList(customer));
			return customerList;
		}
	}

	
	public List<Customer> findByModel(Page page, Customer customer,String orderByProperty, Boolean isAsc) {
		if(null!=orderByProperty&&null!=isAsc){
			List<Customer> customerList = this.find(page,orderByProperty,isAsc,generateCriterionList(customer));
			return customerList;
		}else{
			List<Customer> customerList = this.find(page,generateCriterionList(customer));
			return customerList;
		}
	}
	
	
	public void deleteByIds(String pks) {
		super.executeSqlUpdate("delete from customer where customer_id in ("+pks+")");
	}

	private List<Criterion> generateCriterionList(Customer customer){
		List<Criterion> criterionList=new ArrayList<Criterion>();
		if(!StringUtil.isEmpty(customer.getCustomerCode())){
			criterionList.add(Restrictions.like("customerCode", customer.getCustomerCode(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(customer.getCustomerName())){
			criterionList.add(Restrictions.like("customerName", customer.getCustomerName(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isNull(customer.getIdCardNumber())){
			criterionList.add(Restrictions.like("idCardNumber", customer.getIdCardNumber(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isNull(customer.getCustomerType())&&customer.getCustomerType()>-1){
			criterionList.add(Restrictions.eq("customerType", customer.getCustomerType()));
		}
		if(!StringUtil.isNull(customer.getStatus())&&customer.getStatus()>-1){
			criterionList.add(Restrictions.eq("status", customer.getStatus()));
		}
		
		if(!StringUtil.isNull(customer.getActivatStatus())&&customer.getActivatStatus()>-1){
			criterionList.add(Restrictions.eq("activatStatus", customer.getActivatStatus()));
		}
		
		return criterionList;
	}
	
	public List<String> getPksList(List<Long> pks){
		List<String> idsList = new ArrayList<String>();
		StringBuilder pksStrBuilder = new StringBuilder();
		int i=0;
		for(Long pk:pks){
			pksStrBuilder.append(",").append(pk);
			if(i==998){
				String pksStr=pksStrBuilder.toString();
				pksStr = pksStr.substring(1);
				idsList.add(pksStr);
				pksStrBuilder = new StringBuilder();
				i=-1;
			}
		}
		if(i==998){
			String pksStr = pksStrBuilder.toString();
			if(null!=pksStr&&!"".equals(pksStr)){
				pksStr = pksStr.substring(1);
			}
		}
		return idsList;
	}

	
	public List<Customer> findByIds(String pks) {
		return super.find("from Customer customer where customer.customerId in ("+pks+")");
	}

	
	public void getCustomerTree(List<Customer> list,Customer parentCustomer) {
		List<Customer> tempList = super.findBy("parentCustomer", parentCustomer);
		if(null!=tempList&&!tempList.isEmpty()){
			for(Customer customer :tempList){
				list.add(customer);
				getCustomerTree(list,customer);
			}
		}
	}

	
	public String getCustomerTreeHtml(List<Customer> list,Customer parentCustomer,StringBuilder treeHtmlBuilder) {
		List<Customer> tempList = super.findBy("parentCustomer", parentCustomer);
		if(null!=tempList&&!tempList.isEmpty()){
			treeHtmlBuilder.append("<ul>");
			for(Customer customer :tempList){
				Tree tree = new Tree();
				tree.setHref("@requestContextPath@/customer/customerView.htm?customerId="+customer.getCustomerId());
				tree.setTaget("navTab");
				tree.setRel("customerTreeBox");
				tree.setTitle("客户详情");
				treeHtmlBuilder.append("<li><a href=\""+tree.getHref()+"\"").append(" target=\"").append(tree.getTaget()).append("\"").append(" rel=\"").append(tree.getRel()).append("\"").append(" title=\"").append(tree.getTitle()).append("\"").append(">").append(customer.getCustomerName()).append("</a>");
				list.add(customer);
				getCustomerTreeHtml(list,customer,treeHtmlBuilder);
				treeHtmlBuilder.append("</li>");
			}
			treeHtmlBuilder.append("</ul>");
		}
		return treeHtmlBuilder.toString();
	}

	
	public void updateStatusByIds(String pks, Integer status) {
		super.executeSqlUpdate("update customer set status = "+status+" where customer_id in ("+pks+")");
	}
}
