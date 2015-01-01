package com.ryan.doubletrack.order.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ryan.doubletrack.order.dao.OrderDao;
import com.ryan.doubletrack.order.entity.Order;
import com.ryan.framework.dao.impl.BaseHibernateDaoSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.StringUtil;

@Repository
public class OrderDaoImpl extends BaseHibernateDaoSupport<Order, Long> implements OrderDao<Order,Long> {

	public List<Order> findByModel(Order order) {
		List<Order> orderList = this.find(generateCriterionList(order));
		return orderList;
	}

	public List<Order> findByModel(Page page, Order order) {
		List<Order> orderList = this.find(page,generateCriterionList(order));
		return orderList;
	}
	
	
	public void deleteByIds(String pks) {
		super.executeSqlUpdate("delete from orders where order_id in ("+pks+")");
	}

	private List<Criterion> generateCriterionList(Order order){
		List<Criterion> criterionList=new ArrayList<Criterion>();
		if(!StringUtil.isEmpty(order.getOrderCode())){
			criterionList.add(Restrictions.like("orderCode", order.getOrderCode(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(order.getCustomerCode())){
			criterionList.add(Restrictions.like("customerCode", order.getCustomerCode(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(order.getCustomerName())){
			criterionList.add(Restrictions.like("customerName", order.getCustomerName(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isNull(order.getCustomerType())&&order.getCustomerType()>-1){
			criterionList.add(Restrictions.eq("customerType", order.getCustomerType()));
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

	
	public void cleanOrderHistory(Order model) {
		Date [] dates = null;
		StringBuilder orderSelecthql = new StringBuilder("from Order");
		StringBuilder whereHql = new StringBuilder(" where 1=1");
		if(null!=model.getStartCreateTime()&&null!=model.getEndCreateTime()){
			dates = new Date[2];
			dates[0] = model.getStartCreateTime();
			dates[1] = model.getEndCreateTime();
		}else if(null!=model.getStartCreateTime()&&null==model.getEndCreateTime()){
			dates = new Date[1];
			dates[0] = model.getStartCreateTime();
			whereHql.append(" and createTime >= ?");
		}
		if(null==model.getEndCreateTime()&&null!=model.getEndCreateTime()){
			dates = new Date[1];
			dates[0] = model.getEndCreateTime();
			whereHql.append(" and createTime <= ?");
		}
		List<Order> orderList = super.find(orderSelecthql.append(whereHql).toString(), dates);
		if(null!=orderList&&!orderList.isEmpty()){
			StringBuilder orderIdsBuidler = new StringBuilder();
			for(Order order:orderList){
				orderIdsBuidler.append(",").append(order.getOrderId());
			}
			super.executeSqlUpdate("delete from order_product_list where order_id in ("+orderIdsBuidler.toString().substring(1)+")");
			String orderDeletehql = "delete from Order "+whereHql.toString();
			super.execute(orderDeletehql,dates);
		}
	}
}
