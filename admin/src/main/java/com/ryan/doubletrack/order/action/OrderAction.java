package com.ryan.doubletrack.order.action;

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
import com.ryan.doubletrack.order.dto.OrderDto;
import com.ryan.doubletrack.order.dto.OrderProductListDto;
import com.ryan.doubletrack.order.entity.Order;
import com.ryan.doubletrack.order.service.OrderService;
import com.ryan.doubletrack.product.dto.ProductDto;
import com.ryan.doubletrack.product.entity.Product;
import com.ryan.doubletrack.product.service.ProductService;
import com.ryan.framework.action.BaseAction;
import com.ryan.framework.enums.AjaxReturnEnum;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.EnumUtil;

@ParentPackage("system")    
@Namespace("/order")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class OrderAction extends BaseAction<OrderDto,Order,Long> {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private OrderDto orderDto;
	private Page page;
	@Resource
	private OrderService<OrderDto,Order,Long> orderService;
	@Resource
	private ProductService<ProductDto,Product,Long> productService;

	@Action(value = "orderList", results = { @Result(type="freemarker", location = "/WEB-INF/page/order/orderList.html") })
	public String list(){
		try{
			List<OrderDto> orderList = orderService.find(page==null?new Page():page, orderDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			this.getActionContext().put("page", page);
			this.getActionContext().put("orderList", orderList);
			if(null==orderDto){
				orderDto = new OrderDto();
			}
			this.getActionContext().put("orderDto", orderDto);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	@Action(value = "orderClean", results = { @Result(type="freemarker", location = "/WEB-INF/page/order/cleanOrderHistory.html") })
	public String clean(){
		page = new Page();
		orderDto = new OrderDto();
		return list();
	}

	@Action(value = "cleanOrderHistory")
	public String cleanOrderHistory(){
		try {
			orderService.cleanOrderHistory(orderDto);
			ajaxReturnDataDto.setMessage(MessageEnum.ORDER_CLEAN_HISTORY_SUCCESS.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
		}catch(Exception e){
			logger.error(ExceptionEnum.ORDER_CLEAN_HISTORY_SUCCESS.getValue(), e);
			ajaxReturnDataDto.setMessage(ExceptionEnum.ORDER_CLEAN_HISTORY_SUCCESS.getDescription());
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
		}
		ajaxReturnDataDto.setNavTabId("cleanOrderNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "orderSearch", results = { @Result(type="freemarker", location = "/WEB-INF/page/order/orderList.html") })
	public String search(){
		page=new Page();
		return list();
	}

	@Action(value = "orderIndex", results = { @Result(type="freemarker", location = "/WEB-INF/page/order/orderList.html") })
	public String index(){
		page = new Page();
		orderDto = new OrderDto();
		return list();
	}
	
	@Action(value = "orderSave")
	public String save(){
		try {
			orderDto.getOrderProductListDto().setTotalPrice(orderDto.getOrderProductListDto().getProductPrice()*orderDto.getOrderProductListDto().getProductCount());
			orderDto.setTotalPrice(orderDto.getOrderProductListDto().getProductPrice()*orderDto.getOrderProductListDto().getProductCount());
			orderDto = orderService.save(orderDto);
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
		ajaxReturnDataDto.setNavTabId("addOrderNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "orderEdit", results = { @Result(type="freemarker", location = "/WEB-INF/page/order/orderEdit.html") })
	public String edit(){
		try{
			Object orderId = this.getParameters().get("orderId");
			orderDto = orderService.findById(null==orderId?0L:Long.parseLong(((String [])orderId)[0]));
			this.getActionContext().put("orderDto",null==orderDto?new OrderDto():orderDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "orderUpdate")
	public String update(){
		try{
			orderDto.getOrderProductListDto().setTotalPrice(orderDto.getOrderProductListDto().getProductPrice()*orderDto.getOrderProductListDto().getProductCount());
			orderDto.setTotalPrice(orderDto.getOrderProductListDto().getProductPrice()*orderDto.getOrderProductListDto().getProductCount());
			orderDto = orderService.merge(orderDto);
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
		ajaxReturnDataDto.setNavTabId("editOrderNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}
	
	@Action(value = "orderView", results = { @Result(type="freemarker", location = "/WEB-INF/page/order/orderView.html") })
	public String view(){
		try{
			Object orderId = this.getParameters().get("orderId");
			orderDto = orderService.findById(null==orderId?0L:Long.parseLong(((String [])orderId)[0]));
			this.getActionContext().put("orderDto",null==orderDto?new OrderDto():orderDto);
			List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
			this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
			return SUCCESS;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}
	
	@Action(value = "orderAdd", results = { @Result(type="freemarker", location = "/WEB-INF/page/order/orderSave.html") })
	public String add(){
		orderDto = new OrderDto();
		orderDto.setOrderCode(orderService.generateOrderCode());
		ProductDto productDto = productService.findById(1L);
		if(null!=productDto){
			OrderProductListDto orderProductListDto = new OrderProductListDto();
			orderProductListDto.setProductId(productDto.getProductId());
			orderProductListDto.setProductName(productDto.getProductName());
			orderProductListDto.setProductPrice(productDto.getBasicPrice());
			orderDto.setOrderProductListDto(orderProductListDto);
		}
		orderDto.setCreateTime(new Date());
		List<CustomerTypeEnum> customerTypeEnumList = EnumUtil.getEnumList(CustomerTypeEnum.class);
		this.getActionContext().put("customerTypeEnumList", customerTypeEnumList);
		return SUCCESS;
	}

	@Action(value = "orderDelete")
	public String delete(){
		Object id = this.getParameters().get("orderId");
		if(null==id){
			ajaxReturnDataDto.setMessage("订单不能为空！请选择要删除的订单！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("orderId is null!");
		}else{
			String idStr = ((String[])id)[0];
			logger.debug("id :<"+idStr+">!");
			if("".equals(idStr)){
				ajaxReturnDataDto.setMessage("订单不能为空！请选择要删除的订单！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("orderId is ''!");
			}else{
				try{
					orderService.deleteById(Long.parseLong(idStr));
					ajaxReturnDataDto.setMessage("订单已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("orderId"+"<"+idStr+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage(ExceptionEnum.ORDER_DELETE_ERROR.getDescription());
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error(ExceptionEnum.ORDER_DELETE_ERROR.getValue()+"orderId"+"<"+idStr+">", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("orderListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
	}

	@Action(value = "orderDeleteAll")
	public String deleteAll(){
		Object ids = this.getParameters().get("ids");
		if(null==ids){
			ajaxReturnDataDto.setMessage("订单不能为空！请选择要删除的订单！");
			ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
			logger.error("orderId"+" is null!");
		}else{
			String[] idArray = (String[])ids;
			if(null==idArray||idArray.length<1){
				ajaxReturnDataDto.setMessage("订单不能为空！请选择要删除的订单"+"！");
				ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
				logger.error("orderId"+" is ''!");
			}else{
				try{
					orderService.deleteByIds(idArray);
					ajaxReturnDataDto.setMessage("订单已删除！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.SUCCESS.getKey());
					logger.warn("orderId"+"<"+idArray.toString()+"> delete success！");
				}catch(Exception e){
					ajaxReturnDataDto.setMessage("订单删除失败！");
					ajaxReturnDataDto.setStatusCode(AjaxReturnEnum.ERROR.getKey());
					logger.error("orderId"+"<"+idArray.toString()+"> delete faile！", e);
				}
			}
		}
		ajaxReturnDataDto.setNavTabId("orderListNavTab");
		this.getActionContext().put("ajaxReturnDataDto", ajaxReturnDataDto);
		return AJAXDONE;
		
	}
	public OrderDto getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(OrderDto orderDto) {
		this.orderDto = orderDto;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}
}
