package com.ryan.doubletrack.wage.dao;

import java.io.Serializable;
import java.util.List;

import com.ryan.framework.dao.BaseHibernateDao;
import com.ryan.framework.entity.Model;
import com.ryan.framework.tag.pagination.Page;

public interface WageDao<M extends Model,PK extends Serializable> extends BaseHibernateDao<M,PK>{
	public List<M> findByModel(M model);
	public List<M> findByModel(Page page,M model);
	public void deleteByIds(String pks);
//	public M findUnique(M model);
	public void cleanWageHistory(M model);
}
