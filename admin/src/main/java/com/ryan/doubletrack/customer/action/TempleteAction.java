package com.ryan.doubletrack.customer.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.ryan.doubletrack.customer.dto.CustomerDto;
import com.ryan.doubletrack.customer.entity.Customer;
import com.ryan.doubletrack.customer.service.CustomerService;
import com.ryan.doubletrack.enums.ExceptionEnum;
import com.ryan.framework.action.BaseAction;
import com.ryan.framework.enums.AjaxReturnEnum;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.EnumUtil;
   
@Namespace("/customer")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class TempleteAction extends BaseAction<CustomerDto,Customer,Long> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private CustomerDto customerDto;
	private Page page;
	@Resource
	private CustomerService<CustomerDto,Customer,Long> customerService;

	@Action(value = "customerList", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerList.html") })
	public String list(){
		
		try{
			List<CustomerDto> customerList = customerService.find(page==null?new Page():page, customerDto);
			this.getActionContext().put("page", page);
			this.getActionContext().put("customerList", customerList);
			if(null==customerDto){
				customerDto = new CustomerDto();
			}
			this.getActionContext().put("customerDto", customerDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	@Action(value = "customerSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerList.html") })
	public String search(){
		page=new Page();
		return list();
	}

	@Action(value = "customerIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerList.html") })
	public String index(){
		page = new Page();
		customerDto = new CustomerDto();
		return list();
	}
	
	@Action(value = "customerAdd", results = {@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerSave.html")})
	public String add(){
		customerDto = new CustomerDto();
		this.getActionContext().put("customerDto",null==customerDto?new CustomerDto():customerDto);
		return SUCCESS;
	}
	
	@Action(value = "customerSave")
	public String save(){
		try {
			customerDto = customerService.save(customerDto);
			ajaxReturnDataDto.setMessage("保存成功！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch (AppException e) {
			ExceptionEnum exceptionEnum = EnumUtil.getEnumByPropertyName(ExceptionEnum.class, "key", e.getMessage());
			logger.error(exceptionEnum.getValue(), e);
			ajaxReturnDataDto.setMessage(exceptionEnum.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}catch(Exception e){
			logger.error("save error！", e);
			ajaxReturnDataDto.setMessage("保存失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("addCustomerNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "customerEdit", results = {@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerEdit.html")})
	public String edit(){
		try{
			Object customerId = this.getParameters().get("customerId");
			customerDto = customerService.findById(null==customerId?0L:Long.parseLong(((String [])customerId)[0]));
			this.getActionContext().put("customerDto",null==customerDto?new CustomerDto():customerDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "customerUpdate")
	public String update(){
		try{
			customerDto = customerService.merge(customerDto);
			ajaxReturnDataDto.setMessage("更新失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch (AppException e) {
			ExceptionEnum exceptionEnum = EnumUtil.getEnumByPropertyName(ExceptionEnum.class, "key", e.getMessage());
			logger.error(exceptionEnum.getValue(), e);
			ajaxReturnDataDto.setMessage(exceptionEnum.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}catch(Exception e){
			logger.error("update error!", e);
			ajaxReturnDataDto.setMessage("更新失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("editCustomerNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "customerView", results = {@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerView.html")})
	public String view(){
		try{
			Object customerId = this.getParameters().get("customerId");
			customerDto = customerService.findById(null==customerId?0L:Long.parseLong(((String [])customerId)[0]));
			this.getActionContext().put("customerDto",null==customerDto?new CustomerDto():customerDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "customerDelete")
	public String delete(){
		Object id = this.getParameters().get("id");
		if(null==id){
			ajaxReturnDataDto.setMessage("客户不能为空！请选择要删除的客户！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("customerId is null!");
		}else{
			String idStr = ((String[])id)[0];
			logger.debug("id :<"+idStr+">!");
			if("".equals(idStr)){
				ajaxReturnDataDto.setMessage("客户不能为空！请选择要删除的客户！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("customerId is ''!");
			}else{
				try{
					customerService.deleteById(Long.parseLong(idStr));
					ajaxReturnDataDto.setMessage("客户已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("customerId"+"<"+idStr+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("客户删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("customer delete error customerId"+"<"+idStr+">", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("customerListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "customerDeleteAll")
	public String deleteAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("客户不能为空！请选择要删除的客户！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("customerId"+" is null!");
		}else{
			String[] idArray = (String[])ids;
			if("".equals(idArray)||idArray.length<1){
				ajaxReturnDataDto.setMessage("客户不能为空！请选择要删除的客户"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("customerId"+" is ''!");
			}else{
				try{
					customerService.deleteByIds(idArray);
					ajaxReturnDataDto.setMessage("客户已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("customerId"+"<"+idArray.toString()+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("客户删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("customerId"+"<"+idArray.toString()+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("customerListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
		
	}

	public CustomerDto getCustomerDto() {
		return customerDto;
	}

	public void setCustomerDto(CustomerDto customerDto) {
		this.customerDto = customerDto;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}
}
