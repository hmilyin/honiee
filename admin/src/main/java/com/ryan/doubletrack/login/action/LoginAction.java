package com.ryan.doubletrack.login.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.ryan.doubletrack.enums.CustomerTypeEnum;
import com.ryan.doubletrack.order.dto.OrderDto;
import com.ryan.doubletrack.order.dto.OrderProductListDto;
import com.ryan.doubletrack.order.entity.Order;
import com.ryan.doubletrack.order.service.OrderService;
import com.ryan.doubletrack.product.dto.ProductDto;
import com.ryan.doubletrack.product.entity.Product;
import com.ryan.doubletrack.product.service.ProductService;
import com.ryan.framework.action.BaseAction;
import com.ryan.privilege.dto.OperatorDto;
import com.ryan.privilege.entity.Operator;
import com.ryan.privilege.enums.PrivilegeEnum;
import com.ryan.privilege.service.OperatorService;
import com.ryan.utils.EnumUtil;

@ParentPackage("system")    
@Namespace("/system")    
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") })  
public class LoginAction extends BaseAction<OperatorDto,Operator,Long> {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(this.getClass());
	private OperatorDto operatorDto;
	
    @Resource
	private OperatorService<OperatorDto,Operator,Long> operatorService;
    @Resource
	private OrderService<OrderDto,Order,Long> orderService;
    @Resource
	private ProductService<ProductDto,Product,Long> productService;
    
    @Action(value = "login", results = { @Result(name = "success", type="freemarker", location = "/WEB-INF/page/system/index.html") })
	public String login(){
		try{
			OperatorDto operatorResult = operatorService.login(operatorDto);

//			登陆成功加载快速下单页面
			OrderDto orderDto = new OrderDto();
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
			this.getActionContext().put("orderDto", orderDto);
			
			if(null!=operatorResult){
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpSession session = request.getSession();
				session.setAttribute(PrivilegeEnum.USER_PERMISSION_KEY.getKey(), operatorResult.getOperatorId());
				return SUCCESS;
			}else{
				throw new Exception("login fail!");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return ERROR;
		}
	}

	public OperatorDto getOperatorDto() {
		return operatorDto;
	}

	public void setOperatorDto(OperatorDto operatorDto) {
		this.operatorDto = operatorDto;
	}
	
}
