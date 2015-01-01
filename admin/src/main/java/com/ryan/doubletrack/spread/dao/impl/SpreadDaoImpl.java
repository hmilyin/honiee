package com.ryan.doubletrack.spread.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ryan.doubletrack.spread.dao.SpreadDao;
import com.ryan.doubletrack.spread.entity.Spread;
import com.ryan.framework.dao.impl.BaseHibernateDaoSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.StringUtil;

@Repository
public class SpreadDaoImpl extends BaseHibernateDaoSupport<Spread, Integer> implements SpreadDao<Spread,Integer> {

	public List<Spread> findByModel(Spread spread) {
		List<Spread> spreadList = this.find(generateCriterionList(spread));
		return spreadList;
	}

	public List<Spread> findByModel(Page page, Spread spread) {
		List<Spread> spreadList = this.find(page,generateCriterionList(spread));
		return spreadList;
	}

	public void deleteByIds(Integer pks) {
		super.executeSqlUpdate("delete from spread where id in ("+pks+")");
	}
	private List<Criterion> generateCriterionList(Spread spread){
		List<Criterion> criterionList=new ArrayList<Criterion>();
		if(!StringUtil.isEmpty(spread.getChannel())){
			criterionList.add(Restrictions.like("channel", spread.getChannel(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isNull(spread.getSpreadId())){
			criterionList.add(Restrictions.eq("spreadId", spread.getSpreadId()));
		}
		if(!StringUtil.isEmpty(spread.getCodeName())){
			criterionList.add(Restrictions.like("codeName", spread.getCodeName(),MatchMode.ANYWHERE));
		}
		return criterionList;
	}
	
}