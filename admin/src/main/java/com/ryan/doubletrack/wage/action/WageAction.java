package com.ryan.doubletrack.wage.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.ryan.doubletrack.enums.CustomerTypeEnum;
import com.ryan.doubletrack.enums.ExceptionEnum;
import com.ryan.doubletrack.enums.MessageEnum;
import com.ryan.doubletrack.rebates.dto.RebatesDto;
import com.ryan.doubletrack.rebates.entity.Rebates;
import com.ryan.doubletrack.rebates.service.RebatesService;
import com.ryan.doubletrack.wage.dto.WageDto;
import com.ryan.doubletrack.wage.entity.Wage;
import com.ryan.doubletrack.wage.service.WageService;
import com.ryan.framework.action.BaseAction;
import com.ryan.framework.enums.AjaxReturnEnum;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.EnumUtil;

@ParentPackage("system")    
@Namespace("/wage")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class WageAction extends BaseAction<WageDto,Wage,Long> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private WageDto wageDto;
	private Page page;
	@Resource
	private WageService<WageDto,Wage,Long> wageService;
	
	@Resource
	private RebatesService<RebatesDto,Rebates,Long> rebatesService;

	@Action(value = "wageList", results = { @Result(type="freemarker", location = "/WEB-INF/page/wage/wageList.html") })
	public String list(){
		try{
			List<WageDto> wageList = wageService.find(page==null?new Page():page, wageDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			this.getActionContext().put("page", page);
			this.getActionContext().put("wageList", wageList);
			if(null==wageDto){
				wageDto = new WageDto();
			}
			this.getActionContext().put("wageDto", wageDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "wageSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/wage/wageList.html") })
	public String search(){
		page=new Page();
		return list();
	}

	@Action(value = "wageIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/wage/wageList.html") })
	public String index(){
		page = new Page();
		wageDto = new WageDto();
		return list();
	}

	public String save(){
		try {
			wageDto = wageService.save(wageDto);
			ajaxReturnDataDto.setMessage(MessageEnum.ORDER_SAVE_SUCCESS.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch (AppException e) {
			ExceptionEnum exceptionEnum = EnumUtil.getEnumByPropertyName(ExceptionEnum.class, "key", e.getMessage());
			logger.error(exceptionEnum.getValue(), e);
			ajaxReturnDataDto.setMessage(exceptionEnum.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}catch(Exception e){
			logger.error(ExceptionEnum.ORDER_SAVE_ERROR.getValue(), e);
			ajaxReturnDataDto.setMessage(ExceptionEnum.ORDER_SAVE_ERROR.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("addCustomerNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	public String edit(){
		try{
			Object wageId = this.getParameters().get("wageId");
			wageDto = wageService.findById(null==wageId?0L:Long.parseLong(((String [])wageId)[0]));
			this.getActionContext().put("wageDto",null==wageDto?new WageDto():wageDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	public String update(){
		try{
			wageDto = wageService.merge(wageDto);
			ajaxReturnDataDto.setMessage(MessageEnum.ORDER_SAVE_SUCCESS.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch (AppException e) {
			ExceptionEnum exceptionEnum = EnumUtil.getEnumByPropertyName(ExceptionEnum.class, "key", e.getMessage());
			logger.error(exceptionEnum.getValue(), e);
			ajaxReturnDataDto.setMessage(exceptionEnum.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}catch(Exception e){
			logger.error(ExceptionEnum.ORDER_SAVE_ERROR.getValue(), e);
			ajaxReturnDataDto.setMessage(ExceptionEnum.ORDER_SAVE_ERROR.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("editCustomerNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "wageView", results = { @Result(type="freemarker", location = "/WEB-INF/page/wage/wageView.html") })
	public String view(){
		try{
			Object wageId = this.getParameters().get("wageId");
			wageDto = wageService.findById(null==wageId?0L:Long.parseLong(((String [])wageId)[0]));
			this.getActionContext().put("wageDto",null==wageDto?new WageDto():wageDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	public String add(){
		wageDto = new WageDto();
		wageDto.setCreateTime(new Date());
		List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
		this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
		return SUCCESS;
	}
	
	public String delete(){
		Object id = this.getParameters().get("wageId");
		if(null==id){
			ajaxReturnDataDto.setMessage("业绩不能为空！请选择要删除的业绩！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("wageId is null!");
		}else{
			String idStr = ((String[])id)[0];
			logger.debug("id :<"+idStr+">!");
			if("".equals(idStr)){
				ajaxReturnDataDto.setMessage("业绩不能为空！请选择要删除的业绩！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("wageId is ''!");
			}else{
				try{
					wageService.deleteById(Long.parseLong(idStr));
					ajaxReturnDataDto.setMessage("业绩已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("wageId"+"<"+idStr+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage(ExceptionEnum.ORDER_DELETE_ERROR.getDescription());
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error(ExceptionEnum.ORDER_DELETE_ERROR.getValue()+"wageId"+"<"+idStr+">", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("wageListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "wageDeleteAll")
	public String deleteAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("业绩不能为空！请选择要删除的业绩！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("wageId"+" is null!");
		}else{
			String[] idArray = (String[])ids;
			if(null==idArray||idArray.length<1){
				ajaxReturnDataDto.setMessage("业绩不能为空！请选择要删除的业绩"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("wageId"+" is ''!");
			}else{
				try{
					wageService.deleteByIds(idArray);
					ajaxReturnDataDto.setMessage("业绩已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("wageId"+"<"+idArray.toString()+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("业绩删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("wageId"+"<"+idArray.toString()+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("wageListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
		
	}

	@Action(value = "wageClean", results = { @Result(type="freemarker", location = "/WEB-INF/page/wage/cleanWageHistory.html") })
	public String clean(){
		page = new Page();
		wageDto = new WageDto();
		return list();
	}

	@Action(value = "cleanWageHistory")
	public String cleanWageHistory(){
		try {
			wageService.cleanWageHistory(wageDto);
			ajaxReturnDataDto.setMessage(MessageEnum.ORDER_CLEAN_HISTORY_SUCCESS.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch(Exception e){
			logger.error(ExceptionEnum.ORDER_CLEAN_HISTORY_SUCCESS.getValue(), e);
			ajaxReturnDataDto.setMessage(ExceptionEnum.ORDER_CLEAN_HISTORY_SUCCESS.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("cleanWageNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "resultsOfSettlement", results = { @Result(type="freemarker", location = "/WEB-INF/page/wage/wageList.html") })
	public String resultsOfSettlement(){
		try {
			rebatesService.resultsOfSettlement();
			ajaxReturnDataDto.setMessage(MessageEnum.REBATES_SETTLEMENT_SUCCESS.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch (AppException e) {
			ExceptionEnum exceptionEnum = EnumUtil.getEnumByPropertyName(ExceptionEnum.class, "key", e.getMessage());
			logger.error(exceptionEnum.getValue(), e);
			return ERROR;
		}
		return index();
	}
	
	public WageDto getWageDto() {
		return wageDto;
	}

	public void setWageDto(WageDto wageDto) {
		this.wageDto = wageDto;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}
}
