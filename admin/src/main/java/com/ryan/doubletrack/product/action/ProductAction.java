package com.ryan.doubletrack.product.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.ryan.doubletrack.enums.ProductCategoryEnum;
import com.ryan.doubletrack.product.dto.ProductDto;
import com.ryan.doubletrack.product.entity.Product;
import com.ryan.doubletrack.product.service.ProductService;
import com.ryan.framework.action.BaseAction;
import com.ryan.framework.enums.AjaxReturnEnum;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.EnumUtil;

@ParentPackage("system")    
@Namespace("/product")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class ProductAction extends BaseAction<ProductDto,Product,Long> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private ProductDto productDto;
	private Page page;
	@Resource
	private ProductService<ProductDto,Product,Long> productService;

	@Action(value = "productList", results = { @Result(type="freemarker", location = "/WEB-INF/page/product/productList.html") })
	public String list(){
		try{
			List<ProductDto> productList = productService.find(page==null?new Page():page, productDto);
			List<ProductCategoryEnum> productCategoryEnumList = EnumUtil.getEnumList(ProductCategoryEnum.class);
			this.getActionContext().put("productCategoryEnumList", productCategoryEnumList);
			this.getActionContext().put("page", page);
			this.getActionContext().put("productList", productList);
			if(null==productDto){
				productDto = new ProductDto();
			}
			this.getActionContext().put("productDto", productDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "productSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/product/productList.html") })
	public String search(){
		page=new Page();
		return list();
	}

	@Action(value = "productIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/product/productList.html") })
	public String index(){
		try{
			page = new Page();
			productDto = new ProductDto();
			return list();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "productUpdate")
	public String update(){
		try{
			productDto = productService.update(productDto);
			ajaxReturnDataDto.setMessage("商品信息已保存！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ajaxReturnDataDto.setMessage("商品信息保存失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("editProductNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "productEdit", results = { @Result(type="freemarker", location = "/WEB-INF/page/product/productEdit.html") })
	public String edit(){
		return view();
	}

	@Action(value = "productSave")
	public String save(){
		try{
			productDto = productService.save(productDto);
			ajaxReturnDataDto.setMessage("商品信息已保存！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ajaxReturnDataDto.setMessage("商品信息保存失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("addProductNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "productView", results = { @Result(type="freemarker", location = "/WEB-INF/page/product/productView.html") })
	public String view(){
		try{
			Object productId = this.getParameters().get("productId");
			productDto = productService.findById(null==productId?0L:Long.parseLong(((String [])productId)[0]));
			List<ProductCategoryEnum> productCategoryEnumList = EnumUtil.getEnumList(ProductCategoryEnum.class);
			this.getActionContext().put("productCategoryEnumList", productCategoryEnumList);
			this.getActionContext().put("productDto",null==productDto?new ProductDto():productDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "productAdd", results = { @Result(type="freemarker", location = "/WEB-INF/page/product/productSave.html") })
	public String add(){
		productDto = new ProductDto();
		List<ProductCategoryEnum> productCategoryEnumList = EnumUtil.getEnumList(ProductCategoryEnum.class);
		this.getActionContext().put("productCategoryEnumList", productCategoryEnumList);
		return SUCCESS;
	}

	@Action(value = "productDelete")
	public String delete(){
		Object id = this.getParameters().get("id");
		if(null==id){
			ajaxReturnDataDto.setMessage("商品不能为空！请选择要删除的商品"+"！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("productId"+" is null!");
		}else{
			String idStr = ((String[])id)[0];
			logger.debug("id :<"+idStr+">!");
			if("".equals(idStr)){
				ajaxReturnDataDto.setMessage("商品不能为空！请选择要删除的商品"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("productId"+" is ''!");
			}else{
				try{
					productService.deleteById(Long.parseLong(idStr));
					ajaxReturnDataDto.setMessage("商品已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("productId"+"<"+idStr+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("商品删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("productId"+"<"+idStr+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("productListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "productDeleteAll")
	public String deleteAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("商品不能为空！请选择要删除的商品！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("productId"+" is null!");
		}else{
			String[] idArray = (String[])ids;
			if("".equals(idArray)||idArray.length<1){
				ajaxReturnDataDto.setMessage("商品不能为空！请选择要删除的商品"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("productId"+" is ''!");
			}else{
				try{
					productService.deleteByIds(idArray);
					ajaxReturnDataDto.setMessage("商品已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("productId"+"<"+idArray.toString()+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("商品删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("productId"+"<"+idArray.toString()+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("productListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
		
	}
	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}
}
