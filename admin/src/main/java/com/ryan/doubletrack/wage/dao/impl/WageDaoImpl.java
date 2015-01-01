package com.ryan.doubletrack.wage.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ryan.doubletrack.wage.dao.WageDao;
import com.ryan.doubletrack.wage.entity.Wage;
import com.ryan.framework.dao.impl.BaseHibernateDaoSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.StringUtil;

@Repository
public class WageDaoImpl extends BaseHibernateDaoSupport<Wage, Long> implements WageDao<Wage,Long> {

	String hql = "select wage from Wage wage , Customer customer where wage.customerId=customer.customerId and customer.status <> 2 ";
	String countHql = "select count(*) from Wage wage , Customer customer where wage.customerId=customer.customerId and customer.status <> 2 ";
	public List<Wage> findByModel(Wage wage) {
		return findByModel(null, wage);
	}

	public List<Wage> findByModel(Page page, Wage wage) {
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append(hql);
		Map<String,Object> map = new HashMap<String,Object>();
		this.generateHql(hqlBuilder, wage,map);
		Query query =super.getSession().createQuery(hqlBuilder.toString());
		Set<String> keySet = map.keySet();
		for(Iterator<String> keyIterator =keySet.iterator();keyIterator.hasNext();){
			String key = keyIterator.next();
			query.setParameter(key, "%"+map.get(key)+"%");
		}
		if (page != null) {
			Integer totalRecord = new Integer(0);
			StringBuilder countHqlBuilder = new StringBuilder();
			countHqlBuilder.append(countHql);
			this.generateHql(countHqlBuilder, wage,map);
			Query countQuery =super.getSession().createQuery(countHqlBuilder.toString());
			Set<String> countKeySet = map.keySet();
			for(Iterator<String> keyIterator =countKeySet.iterator();keyIterator.hasNext();){
				String key = keyIterator.next();
				countQuery.setParameter(key, "%"+map.get(key)+"%");
			}
			Object totalRecordObject =countQuery.setMaxResults(1).uniqueResult();
			
			if(null!=totalRecordObject){
				totalRecord = Integer.parseInt(String.valueOf(totalRecordObject));
			}
			page.setTotalRecord(totalRecord);
			this.generatePage(page, totalRecord);
			query.setFirstResult((page.getTargetPage()-1) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
		}
		return query.list();
	}
	
	
	public void deleteByIds(String pks) {
		super.executeSqlUpdate("delete from wage where wage_id in ("+pks+")");
	}

	private void generateHql(StringBuilder hqlBuilder , Wage wage,Map<String,Object> map){
		if(!StringUtil.isEmpty(wage.getCustomerCode())){
			hqlBuilder.append(" and wage.customerCode like :customerCode");
			map.put("customerCode", wage.getCustomerCode());
		}
		if(!StringUtil.isEmpty(wage.getCustomerName())){
			hqlBuilder.append(" and wage.customerName like :customerName");
			map.put("customerName", wage.getCustomerName());
		}
		if(!StringUtil.isEmpty(wage.getPhone())){
			hqlBuilder.append(" and wage.phone like :phone");
			map.put("phone", wage.getPhone());
		}
		if(!StringUtil.isEmpty(wage.getIdCardNumber())){
			hqlBuilder.append(" and wage.idCardNumber like :idCardNumber");
			map.put("idCardNumber", wage.getIdCardNumber());
		}
		if(!StringUtil.isEmpty(wage.getBankAccount())){
			hqlBuilder.append(" and wage.bankAccount like :bankAccount");
			map.put("bankAccount", wage.getBankAccount());
		}
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

	
	public void cleanWageHistory(Wage model) {
		Date [] dates = null;
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
		String orderDeletehql = "delete from Wage "+whereHql.toString();
		super.execute(orderDeletehql,dates);
	}
}
