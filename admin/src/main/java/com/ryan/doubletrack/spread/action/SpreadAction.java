package com.ryan.doubletrack.spread.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.ryan.doubletrack.enums.ExceptionEnum;
import com.ryan.doubletrack.spread.dto.SpreadDto;
import com.ryan.doubletrack.spread.entity.Spread;
import com.ryan.doubletrack.spread.service.SpreadService;
import com.ryan.framework.action.BaseAction;
import com.ryan.framework.enums.AjaxReturnEnum;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.tag.pagination.Page;

@ParentPackage("system")    
@Namespace("/spread")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class SpreadAction extends BaseAction<SpreadDto,Spread,Integer> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private SpreadDto spreadDto;
	private Page page;
	@Resource
	private SpreadService<SpreadDto,Spread,Integer> spreadService;

	@Action(value = "spreadList", results = { @Result(type="freemarker", location = "/WEB-INF/page/spread/spreadList.html") })
	public String list(){
		try{
			List<SpreadDto> spreadList = spreadService.find(page==null?new Page():page, spreadDto);
			this.getActionContext().put("page", page);
			this.getActionContext().put("spreadList", spreadList);
			if(null==spreadDto){
				spreadDto = new SpreadDto();
			}
			this.getActionContext().put("SpreadDto", spreadDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "spreadSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/spread/spreadList.html") })
	public String search(){
		page=new Page();
		return list();
	}

	@Action(value = "spreadIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/spread/spreadList.html") })
	public String index(){
		page = new Page();
		spreadDto = new SpreadDto();
		return list();
	}
	
	@Action(value = "spreadSave")
	public String save() throws AppException{
		try{	
			spreadDto = spreadService.save(spreadDto);
			ajaxReturnDataDto.setMessage("推广信息已保存！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ajaxReturnDataDto.setMessage("推广信息保存失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("addStaffNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	@Action(value = "spreadAdd", results = { @Result(type="freemarker", location = "/WEB-INF/page/spread/spreadSave.html") })
	public String add(){
		spreadDto = new SpreadDto();
		return SUCCESS;
	}
	public String edit(){
		try{
			Object spreadId = this.getParameters().get("spreadId");
			spreadDto = spreadService.findById((int) (null==spreadId?0L:Integer.parseInt(((String [])spreadId)[0])));
			this.getActionContext().put("SpreadDto",null==spreadDto?new SpreadDto():spreadDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	@Action(value = "spreadUpdate")
	public String update() throws AppException{
		spreadDto = spreadService.merge(spreadDto);
		ajaxReturnDataDto.setNavTabId("editspreadNavTab");
		return AJAXDONE;
	}
	@Action(value = "spreadView", results = { @Result(type="freemarker", location = "/WEB-INF/page/spread/spreadView.html") })
	public String view(){
		try{
			Object spreadId = this.getParameters().get("spreadId");
			spreadDto = spreadService.findById((int) (null==spreadId?0L:Integer.parseInt(((String [])spreadId)[0])));
			this.getActionContext().put("SpreadDto",null==spreadDto?new SpreadDto():spreadDto);
			
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	public String delete(){
		Object id = this.getParameters().get("spreadId");
		if(null==id){
			ajaxReturnDataDto.setMessage("推广链接不能为空！请选择要删除的推广！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("spreadId is null!");
		}else{
			String idStr = ((String[])id)[0];
			logger.debug("id :<"+idStr+">!");
			if("".equals(idStr)){
				ajaxReturnDataDto.setMessage("推广链接不能为空！请选择要删除的推广！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("spreadId is ''!");
			}else{
				try{
					spreadService.deleteById((int) Integer.parseInt(idStr));
					ajaxReturnDataDto.setMessage("推广已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("spreadId"+"<"+idStr+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage(ExceptionEnum.ORDER_DELETE_ERROR.getDescription());
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error(ExceptionEnum.ORDER_DELETE_ERROR.getValue()+"spreadId"+"<"+idStr+">", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("spreadListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	public String deleteAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("推广链接不能为空！请选择要删除的推广！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("spreadId"+" is null!");
		}else{
			Integer[] idArray = (Integer[])ids;
			if(null==idArray||idArray.length<1){
				ajaxReturnDataDto.setMessage("推广链接不能为空！请选择要删除的推广！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("spreadId"+" is ''!");
			}else{
				try{
					/*spreadService.deleteById(idArray);*/
					ajaxReturnDataDto.setMessage("推广已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("spreadId"+"<"+idArray.toString()+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("推广删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("spreadId"+"<"+idArray.toString()+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("spreadListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;	
	}
	
	public SpreadDto getSpreadDto() {
		return spreadDto;
	}

	public void setSpreadDto(SpreadDto spreadDto) {
		this.spreadDto = spreadDto;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}
}
