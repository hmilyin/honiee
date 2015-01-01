package com.ryan.doubletrack.staff.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ryan.doubletrack.staff.dao.StaffDao;
import com.ryan.doubletrack.staff.entity.Staff;
import com.ryan.framework.dao.impl.BaseHibernateDaoSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.StringUtil;

@Repository
public class StaffDaoImpl extends BaseHibernateDaoSupport<Staff, Long> implements StaffDao<Staff,Long> {

	public List<Staff> findByModel(Staff staff) {
		List<Staff> staffList = this.find(generateCriterionList(staff));
		return staffList;
	}

	public List<Staff> findByModel(Page page, Staff staff) {
		List<Staff> staffList = this.find(page,generateCriterionList(staff));
		return staffList;
	}
	
	
	public void deleteByIds(String pks) {
		super.executeSqlUpdate("delete from staff where staff_id in ("+pks+")");
	}

	private List<Criterion> generateCriterionList(Staff staff){
		List<Criterion> criterionList=new ArrayList<Criterion>();
		if(!StringUtil.isEmpty(staff.getStaffCode())){
			criterionList.add(Restrictions.like("staffCode", staff.getStaffCode(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(staff.getStaffName())){
			criterionList.add(Restrictions.like("staffName", staff.getStaffName(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(staff.getIdCard())){
			criterionList.add(Restrictions.like("idCard", staff.getIdCard(),MatchMode.ANYWHERE));
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
}
