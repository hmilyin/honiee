package com.ryan.doubletrack.staff.dao;

import java.io.Serializable;
import java.util.List;

import com.ryan.framework.dao.BaseHibernateDao;
import com.ryan.framework.entity.Model;
import com.ryan.framework.tag.pagination.Page;

public interface StaffDao<M extends Model,PK extends Serializable> extends BaseHibernateDao<M,PK>{
	public List<M> findByModel(M model);
	public List<M> findByModel(Page page,M model);
	public void deleteByIds(String pks);
}
