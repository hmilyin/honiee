package com.ryan.doubletrack.product.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ryan.doubletrack.product.dao.ProductDao;
import com.ryan.doubletrack.product.entity.Product;
import com.ryan.framework.dao.impl.BaseHibernateDaoSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.StringUtil;

@Repository
public class ProductDaoImpl extends BaseHibernateDaoSupport<Product, Long> implements ProductDao<Product,Long> {

	public List<Product> findByModel(Product product) {
		List<Product> productList = this.find(generateCriterionList(product));
		return productList;
	}

	public List<Product> findByModel(Page page, Product product) {
		List<Product> productList = this.find(page,generateCriterionList(product));
		return productList;
	}
	
	
	public void deleteByIds(String pks) {
		super.executeSqlUpdate("delete from product where product_id in ("+pks+")");
	}

	private List<Criterion> generateCriterionList(Product product){
		List<Criterion> criterionList=new ArrayList<Criterion>();
		if(!StringUtil.isEmpty(product.getProductExpandName())){
			criterionList.add(Restrictions.like("productExpandName", product.getProductExpandName(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(product.getProductName())){
			criterionList.add(Restrictions.like("productName", product.getProductName(),MatchMode.ANYWHERE));
		}
		if(!StringUtil.isEmpty(product.getMerchantProductCode())){
			criterionList.add(Restrictions.like("merchantProductCode", product.getMerchantProductCode(),MatchMode.ANYWHERE));
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
