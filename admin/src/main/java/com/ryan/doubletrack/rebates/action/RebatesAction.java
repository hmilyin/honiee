package com.ryan.doubletrack.rebates.action;

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
import com.ryan.framework.action.BaseAction;
import com.ryan.framework.enums.AjaxReturnEnum;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.EnumUtil;

@ParentPackage("system")    
@Namespace("/rebates")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class RebatesAction extends BaseAction<RebatesDto,Rebates,Long> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private RebatesDto rebatesDto;
	private Page page;
	@Resource
	private RebatesService<RebatesDto,Rebates,Long> rebatesService;

	@Action(value = "rebatesList", results = { @Result(type="freemarker", location = "/WEB-INF/page/rebates/rebatesList.html") })
	public String list(){
		try{
			List<RebatesDto> rebatesList = rebatesService.find(page==null?new Page():page, rebatesDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			this.getActionContext().put("page", page);
			this.getActionContext().put("rebatesList", rebatesList);
			if(null==rebatesDto){
				rebatesDto = new RebatesDto();
			}
			this.getActionContext().put("rebatesDto", rebatesDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "rebatesSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/rebates/rebatesList.html") })
	public String search(){
		page=new Page();
		return list();
	}

	@Action(value = "rebatesIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/rebates/rebatesList.html") })
	public String index(){
		page = new Page();
		rebatesDto = new RebatesDto();
		return list();
	}
	
	@Action(value = "rebatesSave")
	public String save(){
		try {
			rebatesDto = rebatesService.save(rebatesDto);
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
		ajaxReturnDataDto.setNavTabId("addRebatesNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	public String edit(){
		try{
			Object rebatesId = this.getParameters().get("rebatesId");
			rebatesDto = rebatesService.findById(null==rebatesId?0L:Long.parseLong(((String [])rebatesId)[0]));
			this.getActionContext().put("rebatesDto",null==rebatesDto?new RebatesDto():rebatesDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	@Action(value = "rebatesUpdate")
	public String update(){
		try{
			rebatesDto = rebatesService.merge(rebatesDto);
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
		ajaxReturnDataDto.setNavTabId("editRebatesNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "rebatesView", results = { @Result(type="freemarker", location = "/WEB-INF/page/rebates/rebatesView.html") })
	public String view(){
		try{
			Object rebatesId = this.getParameters().get("rebatesId");
			rebatesDto = rebatesService.findById(null==rebatesId?0L:Long.parseLong(((String [])rebatesId)[0]));
			this.getActionContext().put("rebatesDto",null==rebatesDto?new RebatesDto():rebatesDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	public String add(){
		rebatesDto = new RebatesDto();
		rebatesDto.setCreateTime(new Date());
		List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
		this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
		return SUCCESS;
	}

	public String delete(){
		Object id = this.getParameters().get("rebatesId");
		if(null==id){
			ajaxReturnDataDto.setMessage("业绩不能为空！请选择要删除的业绩！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("rebatesId is null!");
		}else{
			String idStr = ((String[])id)[0];
			logger.debug("id :<"+idStr+">!");
			if("".equals(idStr)){
				ajaxReturnDataDto.setMessage("业绩不能为空！请选择要删除的业绩！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("rebatesId is ''!");
			}else{
				try{
					rebatesService.deleteById(Long.parseLong(idStr));
					ajaxReturnDataDto.setMessage("业绩已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("rebatesId"+"<"+idStr+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage(ExceptionEnum.ORDER_DELETE_ERROR.getDescription());
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error(ExceptionEnum.ORDER_DELETE_ERROR.getValue()+"rebatesId"+"<"+idStr+">", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("rebatesListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	public String deleteAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("业绩不能为空！请选择要删除的业绩！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("rebatesId"+" is null!");
		}else{
			String[] idArray = (String[])ids;
			if(null==idArray||idArray.length<1){
				ajaxReturnDataDto.setMessage("业绩不能为空！请选择要删除的业绩"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("rebatesId"+" is ''!");
			}else{
				try{
					rebatesService.deleteByIds(idArray);
					ajaxReturnDataDto.setMessage("业绩已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("rebatesId"+"<"+idArray.toString()+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("业绩删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("rebatesId"+"<"+idArray.toString()+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("rebatesListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
		
	}
	public RebatesDto getRebatesDto() {
		return rebatesDto;
	}

	public void setRebatesDto(RebatesDto rebatesDto) {
		this.rebatesDto = rebatesDto;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}
}
