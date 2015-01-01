package com.ryan.doubletrack.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ryan.doubletrack.product.dao.ProductDao;
import com.ryan.doubletrack.product.dto.ProductDto;
import com.ryan.doubletrack.product.entity.Product;
import com.ryan.doubletrack.product.service.ProductService;
import com.ryan.framework.dto.DtoUtil;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.service.impl.BaseServiceSupport;
import com.ryan.framework.tag.pagination.Page;

@Service
public class ProductServiceImpl extends BaseServiceSupport<ProductDto, Product, Long> implements ProductService<ProductDto,Product,Long>{
	
	@Resource
	private ProductDao<Product,Long> productDao;

	@SuppressWarnings("unchecked")
	private DtoUtil<ProductDto,Product> productDtoUtil = DtoUtil.getInstance(ProductDto.class, Product.class);
	
	public ProductDto save(final ProductDto dto) {
		Product model = productDtoUtil.convertDto2Model(dto);
		Long productId = this.productDao.save(model);
		dto.setProductId(productId);
		return dto;
    }
    
    public ProductDto update(final ProductDto dto) {
		Product model = productDtoUtil.convertDto2Model(dto);
		productDao.update(model);
		ProductDto result = productDtoUtil.convertModel2Dto(model);
        return result;
    }
    
	public void delete(final ProductDto dto) {
		Product model = productDtoUtil.convertDto2Model(dto);
        productDao.delete(model);
    }

	public ProductDto findById(Long pk) {
		Product model = productDao.get(pk);
		ProductDto productDto = productDtoUtil.convertModel2Dto(model);
        return productDto;
    }

	public List<ProductDto> getAll() {
        List<Product> models = productDao.getAll();
        List<ProductDto> dtos = new ArrayList<ProductDto>();
        for(Product product:models){
        	ProductDto productDto = productDtoUtil.convertModel2Dto(product);
        	dtos.add(productDto);
        }
        return dtos;
    }

	public List<ProductDto> getAll(final Page page) {
        List<Product> models = productDao.getAll(page);
        List<ProductDto> dtos = new ArrayList<ProductDto>();
        for(Product product:models){
        	ProductDto productDto = productDtoUtil.convertModel2Dto(product);
        	dtos.add(productDto);
        }
        return dtos;
    }

	public ProductDto doInIsolatedTx(ProductDto dto) {
		Product model = productDtoUtil.convertDto2Model(dto);
		Long productId = this.productDao.save(model);
		model.setProductId(productId);
		ProductDto productDto =productDtoUtil.convertModel2Dto(model);
		return productDto;
	}


	public ProductDto findByName(String name) {
		return productDtoUtil.convertModel2Dto(productDao.findUniqueBy("name", name));
	}


	public void setProductDao(ProductDao<Product, Long> productDao) {
		this.productDao = productDao;
	}

	public List<ProductDto> find(Page page, ProductDto productDto) {
		List<Product> models = new ArrayList<Product>();
		if(null!=productDto&&null!=page){
			Product model = productDtoUtil.convertDto2Model(productDto);
			models= productDao.findByModel(page,model);
		}else if(page==null&&productDto==null){
			models=productDao.getAll();
		}else if(productDto==null&&null!=page){
			models=productDao.getAll(page);
		}else{
			Product model = productDtoUtil.convertDto2Model(productDto);
			models=productDao.findByModel(model);
		}
        List<ProductDto> dtos = new ArrayList<ProductDto>();
        for(Product product:models){
        	ProductDto productDtoTemp = productDtoUtil.convertModel2Dto(product);
        	dtos.add(productDtoTemp);
        }
		return dtos;
	}
	
	
	public void deleteById(Long pk) {
		productDao.delete(pk);
    }

	
	public void deleteByIds(String[] ids) {
		for(String pks:super.getPksList(ids)){
			productDao.deleteByIds(pks);
		}
	}

	
	public ProductDto merge(ProductDto dto) throws AppException {
		Product model = productDtoUtil.convertDto2Model(dto);
		productDao.merge(model);
		ProductDto result = productDtoUtil.convertModel2Dto(model);
        return result;
	}
}
