package com.ryan.doubletrack.rebates.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ryan.doubletrack.rebates.dao.RebatesDao;
import com.ryan.doubletrack.rebates.entity.Rebates;
import com.ryan.framework.dao.impl.BaseHibernateDaoSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.StringUtil;

@Repository
public class RebatesDaoImpl extends BaseHibernateDaoSupport<Rebates, Long> implements RebatesDao<Rebates,Long> {

	public List<Rebates> findByModel(Rebates rebates) {
		List<Rebates> rebatesList = this.find(generateCriterionList(rebates));
		return rebatesList;
	}

	public List<Rebates> findByModel(Page page, Rebates rebates) {
		List<Rebates> rebatesList = this.find(page,generateCriterionList(rebates));
		return rebatesList;
	}
	
	
	public void deleteByIds(String pks) {
		super.executeSqlUpdate("delete from rebates where rebates_id in ("+pks+")");
	}

	private List<Criterion> generateCriterionList(Rebates rebates){
		List<Criterion> criterionList=new ArrayList<Criterion>();
		if(!StringUtil.isEmpty(rebates.getCustomerCode())){
			criterionList.add(Restrictions.like("customerCode", rebates.getCustomerCode(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(rebates.getCustomerName())){
			criterionList.add(Restrictions.like("customerName", rebates.getCustomerName(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isNull(rebates.getCustomerType())&&rebates.getCustomerType()>-1){
			criterionList.add(Restrictions.eq("customerType", rebates.getCustomerType()));
		}
		return criterionList;
	}

	private List<Criterion> generateCustomerIdAndBranchCriterionList(Rebates rebates){
		List<Criterion> criterionList=new ArrayList<Criterion>();
		if(!StringUtil.isNull(rebates.getCustomerId())){
			criterionList.add(Restrictions.eq("customerId", rebates.getCustomerId()));
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

	
	public Rebates findByCustomerIdAndBranch(Rebates model) {
		return findUnique(generateCustomerIdAndBranchCriterionList(model));
	}
}
