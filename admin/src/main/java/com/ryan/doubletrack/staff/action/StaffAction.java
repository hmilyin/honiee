package com.ryan.doubletrack.staff.action;

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

import com.ryan.doubletrack.staff.dto.StaffDto;
import com.ryan.doubletrack.staff.entity.Staff;
import com.ryan.doubletrack.staff.service.StaffService;
import com.ryan.framework.action.BaseAction;
import com.ryan.framework.enums.AjaxReturnEnum;
import com.ryan.framework.tag.pagination.Page;

@ParentPackage("system")    
@Namespace("/staff")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class StaffAction extends BaseAction<StaffDto,Staff,Long> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private StaffDto staffDto;
	private Page page;
	@Resource
	private StaffService<StaffDto,Staff,Long> staffService;

	@Action(value = "staffList", results = { @Result(type="freemarker", location = "/WEB-INF/page/staff/staffList.html") })
	public String list(){
		try{
			List<StaffDto> staffList = staffService.find(page==null?new Page():page, staffDto);
			this.getActionContext().put("page", page);
			this.getActionContext().put("staffList", staffList);
			if(null==staffDto){
				staffDto = new StaffDto();
			}
			this.getActionContext().put("staffDto",staffDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "staffSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/staff/staffList.html") })
	public String search(){
		page=new Page();
		return list();
	}

	@Action(value = "staffIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/staff/staffList.html") })
	public String index(){
		try{
			page = new Page();
			staffDto = new StaffDto();
			return list();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "staffUpdate")
	public String update(){
		try{
			staffDto = staffService.update(staffDto);
			ajaxReturnDataDto.setMessage("员工信息已保存！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ajaxReturnDataDto.setMessage("员工信息保存失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("editStaffNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "staffEdit", results = { @Result(type="freemarker", location = "/WEB-INF/page/staff/staffEdit.html") })
	public String edit(){
		return view();
	}

	@Action(value = "staffSave")
	public String save(){
		try{
			staffDto = staffService.save(staffDto);
			ajaxReturnDataDto.setMessage("员工信息已保存！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			ajaxReturnDataDto.setMessage("员工信息保存失败！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("addStaffNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "staffView", results = { @Result(type="freemarker", location = "/WEB-INF/page/staff/staffView.html") })
	public String view(){
		try{
			Object staffId = this.getParameters().get("staffId");
			staffDto = staffService.findById(null==staffId?0L:Long.parseLong(((String [])staffId)[0]));
			this.getActionContext().put("staffDto",null==staffDto?new StaffDto():staffDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "staffAdd", results = { @Result(type="freemarker", location = "/WEB-INF/page/staff/staffSave.html") })
	public String add(){
		staffDto = new StaffDto();
		staffDto.setStaffCode(staffService.generateStaffCode());
		staffDto.setCreateTime(new Date());
		return SUCCESS;
	}

	@Action(value = "staffDelete")
	public String delete(){
		Object id = this.getParameters().get("id");
		if(null==id){
			ajaxReturnDataDto.setMessage("员工不能为空！请选择要删除的员工"+"！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("productId"+" is null!");
		}else{
			String idStr = ((String[])id)[0];
			logger.debug("id :<"+idStr+">!");
			if("".equals(idStr)){
				ajaxReturnDataDto.setMessage("员工不能为空！请选择要删除的员工"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("staffId"+" is ''!");
			}else{
				try{
					staffService.deleteById(Long.parseLong(idStr));
					ajaxReturnDataDto.setMessage("员工已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("productId"+"<"+idStr+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("员工删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("staffId"+"<"+idStr+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("staffListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "staffDeleteAll")
	public String deleteAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("员工不能为空！请选择要删除的员工！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("productId"+" is null!");
		}else{
			String[] idArray = (String[])ids;
			if("".equals(idArray)||idArray.length<1){
				ajaxReturnDataDto.setMessage("员工不能为空！请选择要删除的员工"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("staffId"+" is ''!");
			}else{
				try{
					staffService.deleteByIds(idArray);
					ajaxReturnDataDto.setMessage("员工已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("staffId"+"<"+idArray.toString()+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("员工删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("staffId"+"<"+idArray.toString()+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("staffListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
		
	}
	public StaffDto getStaffDto() {
		return staffDto;
	}

	public void setStaffDto(StaffDto staffDto) {
		this.staffDto = staffDto;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}
}
