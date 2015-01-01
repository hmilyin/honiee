package com.ryan.doubletrack.customer.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.ryan.doubletrack.customer.dto.CustomerDto;
import com.ryan.doubletrack.customer.entity.Customer;
import com.ryan.doubletrack.customer.service.CustomerService;
import com.ryan.doubletrack.enums.CustomerActivatStatusEnum;
import com.ryan.doubletrack.enums.CustomerStatusEnum;
import com.ryan.doubletrack.enums.CustomerTypeEnum;
import com.ryan.doubletrack.enums.ExceptionEnum;
import com.ryan.doubletrack.enums.MessageEnum;
import com.ryan.framework.action.BaseAction;
import com.ryan.framework.enums.AjaxReturnEnum;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.EnumUtil;
   
@Namespace("/customer")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class CustomerAction extends BaseAction<CustomerDto,Customer,Long> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private CustomerDto customerDto;
	private Page page;
	@Resource
	private CustomerService<CustomerDto,Customer,Long> customerService;

	@Actions({ 
		@Action(value = "customerList", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerList.html") }),
		@Action(value = "parentCustomerList", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/parentCustomerLookup.html") }),
		@Action(value = "orderCustomerList", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/orderCustomerLookup.html") }),
		@Action(value = "recommendCustomerList", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/recommendCustomerLookup.html") }),
		@Action(value = "customerFreezeList", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerFreezeList.html") })
	})
	public String list(){
		
		try{
			List<CustomerDto> customerList = customerService.find(page==null?new Page():page, customerDto);
			List<String> customerTreeHtmlList = customerService.getCustomerTreeHtmlList();
			StringBuffer requestURL = ServletActionContext.getRequest().getRequestURL();
			String contextPath = requestURL.toString().replace(ServletActionContext.getRequest().getServletPath(),"");
			List<String> customerTreeHtmlResultList = new ArrayList<String>();
			if(null!=customerTreeHtmlList&&!customerTreeHtmlList.isEmpty()){
				for(String customerTreeHtml:customerTreeHtmlList){
					customerTreeHtml = customerTreeHtml.replaceAll("@requestContextPath@", contextPath);
					customerTreeHtmlResultList.add(customerTreeHtml);
				}
			}
			List<CustomerStatusEnum> customerStatusEnumList = EnumUtil.getEnumList(CustomerStatusEnum.class);
			this.getActionContext().put("customerStatusEnumList", customerStatusEnumList);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			
			List<CustomerActivatStatusEnum> customerActivatStatusEnumList = EnumUtil.getEnumList(CustomerActivatStatusEnum.class);
			this.getActionContext().put("customerActivatStatusEnumList",customerActivatStatusEnumList);
			
			this.getActionContext().put("page", page);
			this.getActionContext().put("customerList", customerList);
			this.getActionContext().put("customerTreeHtmlList", customerTreeHtmlResultList);
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

	@Actions({ 
		@Action(value = "customerSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerList.html") }),
		@Action(value = "parentCustomerSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/parentCustomerLookup.html") }),
		@Action(value = "orderCustomerSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/orderCustomerLookup.html") }),
		@Action(value = "recommendCustomerSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/recommendCustomerLookup.html") }),
		@Action(value = "customerFreezeSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerFreezeList.html") })
	})
	public String search(){
		page=new Page();
		return list();
	}

	@Actions({ 
		@Action(value = "customerIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerList.html") }),
		@Action(value = "parentCustomerLookup", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/parentCustomerLookup.html") }),
		@Action(value = "orderCustomerLookup", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/orderCustomerLookup.html") }),
		@Action(value = "recommendCustomerLookup", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/recommendCustomerLookup.html") }),
		@Action(value = "customerFreezeIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/customer/customerFreezeList.html") })
	})
	public String index(){
		page = new Page();
		customerDto = new CustomerDto();
		customerDto.setParentCustomerDto(new CustomerDto());
		customerDto.setRecommendCustomerDto(new CustomerDto());
		return list();
	}

	@Action(value = "customerThawList", results = {@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerThawList.html")})
	public String thawList(){
		page=new Page();
		return list();
	}
	
	@Action(value = "customerThawSearch", results = {@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerThawList.html")})
	public String thawSearch(){
		page=new Page();
		return list();
	}

	@Action(value = "customerThawIndex", results = {@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerThawList.html")})
	public String thawIndex(){
		page = new Page();
		customerDto = new CustomerDto();
		customerDto.setStatus(CustomerStatusEnum.FREEZE.getKey());
		customerDto.setParentCustomerDto(new CustomerDto());
		customerDto.setRecommendCustomerDto(new CustomerDto());
		return list();
	}
	
	@Action(value = "customerAdd", results = {@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerSave.html")})
	public String add(){
		customerDto = new CustomerDto();
		customerDto.setCustomerCode(customerService.generateCustomerCode());
		List<CustomerStatusEnum> customerStatusEnumList = EnumUtil.getEnumList(CustomerStatusEnum.class);
		this.getActionContext().put("customerStatusEnumList", customerStatusEnumList);
		List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
		this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
		List<CustomerActivatStatusEnum> customerActivatStatusEnumList = EnumUtil.getEnumList(CustomerActivatStatusEnum.class);
		this.getActionContext().put("customerActivatStatusEnumList",customerActivatStatusEnumList);
		return SUCCESS;
	}
	
	@Action(value = "customerAddRoot", results = {@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerSaveRoot.html")})
	public String addRoot(){
		customerDto = new CustomerDto();
		List<CustomerStatusEnum> customerStatusEnumList = EnumUtil.getEnumList(CustomerStatusEnum.class);
		this.getActionContext().put("customerStatusEnumList", customerStatusEnumList);
		List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
		this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
		return SUCCESS;
	}
	
	@Action(value = "customerSave")
	public String save(){
		try {
			customerDto = customerService.save(customerDto);
			ajaxReturnDataDto.setMessage("客户信息已保存！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch (AppException e) {
			logger.error(e.getMessage(), e); 
			ajaxReturnDataDto.setMessage("客户信息保存失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ajaxReturnDataDto.setMessage("客户信息保存失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("addCustomerNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "customerEdit", results = { 
			@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerEdit.html"),
			@Result(type = "freemarker", name="editRoot", location = "/WEB-INF/page/customer/customerEditRoot.html") 
			})
	public String edit(){
		try{
			Object customerId = this.getParameters().get("customerId");
			customerDto = customerService.findById(null==customerId?0L:Long.parseLong(((String [])customerId)[0]));
			this.getActionContext().put("customerDto",null==customerDto?new CustomerDto():customerDto);
			List<CustomerStatusEnum> customerStatusEnumList = EnumUtil.getEnumList(CustomerStatusEnum.class);
			this.getActionContext().put("customerStatusEnumList", customerStatusEnumList);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			if(new Integer(1).equals(customerDto.getLevel())){
				return "editRoot";
			}
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
			ajaxReturnDataDto.setMessage(MessageEnum.CUSTOMER_SAVE_SUCCESS.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch (AppException e) {
			ExceptionEnum exceptionEnum = EnumUtil.getEnumByPropertyName(ExceptionEnum.class, "key", e.getMessage());
			logger.error(exceptionEnum.getValue(), e);
			ajaxReturnDataDto.setMessage(exceptionEnum.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}catch(Exception e){
			logger.error(ExceptionEnum.CUSTOMER_SAVE_ERROR.getValue(), e);
			ajaxReturnDataDto.setMessage(ExceptionEnum.CUSTOMER_SAVE_ERROR.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("editCustomerNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "customerView", results = { 
			@Result(type = "freemarker", location = "/WEB-INF/page/customer/customerView.html"),
			@Result(type = "freemarker", name="viewRoot", location = "/WEB-INF/page/customer/customerViewRoot.html") 
			})
	public String view(){
		try{
			Object customerId = this.getParameters().get("customerId");
			customerDto = customerService.findById(null==customerId?0L:Long.parseLong(((String [])customerId)[0]));
			this.getActionContext().put("customerDto",null==customerDto?new CustomerDto():customerDto);
			List<CustomerStatusEnum> customerStatusEnumList = EnumUtil.getEnumList(CustomerStatusEnum.class);
			this.getActionContext().put("customerStatusEnumList", customerStatusEnumList);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			List<CustomerActivatStatusEnum> customerActivatStatusEnumList = EnumUtil.getEnumList(CustomerActivatStatusEnum.class);
			this.getActionContext().put("customerActivatStatusEnumList",customerActivatStatusEnumList);
			if(new Integer(1).equals(customerDto.getLevel())){
				return "viewRoot";
			}
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
					ajaxReturnDataDto.setMessage(ExceptionEnum.CUSTOMER_DELETE_ERROR.getDescription());
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error(ExceptionEnum.CUSTOMER_DELETE_ERROR.getValue()+"customerId"+"<"+idStr+">", e);
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

	@Action(value = "customerFreezeAll")
	public String freezeAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("客户不能为空！请选择要冻结的客户！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("customerId"+" is null!");
		}else{
			String[] idArray = (String[])ids;
			if("".equals(idArray)||idArray.length<1){
				ajaxReturnDataDto.setMessage("客户不能为空！请选择要冻结的客户"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("customerId"+" is ''!");
			}else{
				try{
					customerService.freezeByIds(idArray);
					ajaxReturnDataDto.setMessage("客户已冻结！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("customerId"+"<"+idArray.toString()+"> freeze success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("客户冻结失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("customerId"+"<"+idArray.toString()+"> freeze faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("customerListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
		
	}

	@Action(value = "customerThawAll")
	public String thawAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("客户不能为空！请选择要解冻的客户！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("customerId"+" is null!");
		}else{
			String[] idArray = (String[])ids;
			if("".equals(idArray)||idArray.length<1){
				ajaxReturnDataDto.setMessage("客户不能为空！请选择要解冻的客户"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("customerId"+" is ''!");
			}else{
				try{
					customerService.thawByIds(idArray);
					ajaxReturnDataDto.setMessage("客户已解冻！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("customerId"+"<"+idArray.toString()+"> thaw success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("客户解冻失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("customerId"+"<"+idArray.toString()+"> thaw faile！", e);
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
