package com.ryan.doubletrack.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ryan.doubletrack.customer.dto.CustomerDto;
import com.ryan.doubletrack.customer.entity.Customer;
import com.ryan.doubletrack.customer.service.CustomerService;
import com.ryan.doubletrack.enums.ExceptionEnum;
import com.ryan.doubletrack.order.dao.OrderDao;
import com.ryan.doubletrack.order.dto.OrderDto;
import com.ryan.doubletrack.order.dto.OrderProductListDto;
import com.ryan.doubletrack.order.entity.Order;
import com.ryan.doubletrack.order.entity.OrderProductList;
import com.ryan.doubletrack.order.service.OrderService;
import com.ryan.doubletrack.rebates.dto.RebatesDto;
import com.ryan.doubletrack.rebates.entity.Rebates;
import com.ryan.doubletrack.rebates.service.RebatesService;
import com.ryan.framework.dto.DtoUtil;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.service.impl.BaseServiceSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.DateUtil;

@Service
public class OrderServiceImpl extends BaseServiceSupport<OrderDto, Order, Long> implements OrderService<OrderDto,Order,Long>{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private OrderDao<Order,Long> orderDao;

	@Resource
	private RebatesService<RebatesDto,Rebates,Long> rebatesService;

	@Resource
	private CustomerService<CustomerDto,Customer,Long> customerService;

	@SuppressWarnings("unchecked")
	private DtoUtil<OrderDto,Order> orderDtoUtil = DtoUtil.getInstance(OrderDto.class, Order.class);
	
	@SuppressWarnings("unchecked")
	private DtoUtil<OrderProductListDto,OrderProductList> orderProductListDtoUtil = DtoUtil.getInstance(OrderProductListDto.class, OrderProductList.class);
	
	public OrderDto save(final OrderDto dto) throws AppException {
		Order model = orderDtoUtil.convertDto2Model(dto);
		model.setOrderProductList(orderProductListDtoUtil.convertDto2Model(dto.getOrderProductListDto()));
		Order order = orderDao.findUniqueBy("orderCode", dto.getOrderCode());
		if(null!=order){
			logger.error(ExceptionEnum.ORDER_CODE_REPEAT.getValue());
			throw new AppException(ExceptionEnum.ORDER_CODE_REPEAT.getKey());
		}
		Long orderId = orderDao.save(model);
		dto.setOrderId(orderId);
		CustomerDto customerDto = customerService.findById(model.getCustomerId());
		if(null!=customerDto){
			updateRebatesByCustomer(dto,customerDto);
			model.getOrderProductList().setOrderId(orderId);
			if(null!=customerDto.getLevel()&&customerDto.getLevel()>1){
				List<CustomerDto> customerDtos = customerService.findByIds(customerDto.getCustomerSequence());
				if(null!=customerDtos&&!customerDtos.isEmpty()){
					for(CustomerDto customerDtoTemp:customerDtos){
						if(null!=customerDtoTemp.getLevel()&&customerDtoTemp.getLevel()>1){
							this.updateShareRebatesByCustomer(dto, customerDtoTemp);
						}
					}
				}
			}
		}else{
			logger.error("Can not find the associated customer!");
		}
		return dto; 
    }
    
	public void updateRebatesByCustomer(OrderDto dto,CustomerDto customerDto) throws AppException{
		RebatesDto rebatesDto = new RebatesDto();
		rebatesDto.setCustomerId(customerDto.getCustomerId());
		rebatesDto.setCount(dto.getOrderProductListDto().getProductCount());
		rebatesDto.setCustomerCode(customerDto.getCustomerCode());
		rebatesDto.setCustomerId(customerDto.getCustomerId());
		rebatesDto.setCustomerName(customerDto.getCustomerName());
		rebatesDto.setCustomerType(customerDto.getCustomerType());
		this.updateRebates(rebatesDto, dto, customerDto);
	}
    
	public void updateShareRebatesByCustomer(OrderDto dto,CustomerDto customerDto) throws AppException{
		RebatesDto rebatesDto = new RebatesDto();
		rebatesDto.setCustomerId(customerDto.getParentCustomerDto().getCustomerId());
		rebatesDto.setCount(dto.getOrderProductListDto().getProductCount());
		rebatesDto.setCustomerCode(customerDto.getParentCustomerDto().getCustomerCode());
		rebatesDto.setCustomerId(customerDto.getParentCustomerDto().getCustomerId());
		rebatesDto.setCustomerName(customerDto.getParentCustomerDto().getCustomerName());
		rebatesDto.setCustomerType(customerDto.getParentCustomerDto().getCustomerType());
		this.updateRebates(rebatesDto, dto, customerDto);
	}
	
	private void updateRebates (RebatesDto rebatesDto,OrderDto dto,CustomerDto customerDto) throws AppException{
		RebatesDto rebatesDtoResult =rebatesService.findByCustomerIdAndBranch(rebatesDto);
		if(null==rebatesDtoResult){
			try {
				rebatesDto.setCreateTime(new Date());
				rebatesService.save(rebatesDto);
			} catch (AppException e) {
				logger.error("save rebates error", e);
				throw e;
			}
		}else{
			try {
				rebatesDtoResult.setCount(rebatesDtoResult.getCount()+dto.getOrderProductListDto().getProductCount());
				rebatesDtoResult.setLastModifyTime(new Date());
				rebatesService.merge(rebatesDtoResult);
			} catch (AppException e) {
				logger.error("update rebates error", e);
				throw e;
			}
		}
	}

    public OrderDto update(final OrderDto dto) throws AppException {
		Order model = orderDtoUtil.convertDto2Model(dto);
		model.setLastModifyTime(new Date());
		model.setOrderProductList(orderProductListDtoUtil.convertDto2Model(dto.getOrderProductListDto()));
		Order order = orderDao.get(dto.getOrderId());
		int balance =dto.getOrderProductListDto().getProductCount()-order.getOrderProductList().getProductCount();
		orderDao.update(model);
		if(0!=balance){
			dto.getOrderProductListDto().setProductCount(balance);
			CustomerDto customerDto = customerService.findById(model.getCustomerId());
			if(null!=customerDto){
				updateRebatesByCustomer(dto,customerDto);
				model.getOrderProductList().setOrderId(dto.getOrderId());
				if(null!=customerDto.getLevel()&&customerDto.getLevel()>1){
					List<CustomerDto> customerDtos = customerService.findByIds(customerDto.getCustomerSequence());
					if(null!=customerDtos&&!customerDtos.isEmpty()){
						for(CustomerDto customerDtoTemp:customerDtos){
							if(null!=customerDtoTemp.getLevel()&&customerDtoTemp.getLevel()>1){
								this.updateShareRebatesByCustomer(dto, customerDtoTemp);
							}
						}
					}
				}
			}else{
				logger.error("Can not find the associated customer!");
			}
		}
        return dto;
    }
    
	public void delete(final OrderDto dto) throws AppException {
		Order order = orderDao.get(dto.getOrderId());
		int balance =-order.getOrderProductList().getProductCount();
        orderDao.delete(order);
		if(0!=balance){
			dto.getOrderProductListDto().setProductCount(balance);
			CustomerDto customerDto = customerService.findById(order.getCustomerId());
			if(null!=customerDto){
				updateRebatesByCustomer(dto,customerDto);
				order.getOrderProductList().setOrderId(dto.getOrderId());
				if(null!=customerDto.getLevel()&&customerDto.getLevel()>1){
					List<CustomerDto> customerDtos = customerService.findByIds(customerDto.getCustomerSequence());
					if(null!=customerDtos&&!customerDtos.isEmpty()){
						for(CustomerDto customerDtoTemp:customerDtos){
							this.updateShareRebatesByCustomer(dto, customerDtoTemp);
						}
					}
				}
			}else{
				logger.error("Can not find the associated customer!");
			}
		}
    }

	public OrderDto findById(Long pk) {
		Order model = orderDao.get(pk);
		OrderDto orderDto = orderDtoUtil.convertModel2Dto(model);
		orderDto.setOrderProductListDto(orderProductListDtoUtil.convertModel2Dto(model.getOrderProductList()));
        return orderDto;
    }

	public List<OrderDto> getAll() {
        List<Order> models = orderDao.getAll();
        List<OrderDto> dtos = new ArrayList<OrderDto>();
        for(Order order:models){
        	OrderDto orderDto = orderDtoUtil.convertModel2Dto(order);
    		orderDto.setOrderProductListDto(orderProductListDtoUtil.convertModel2Dto(order.getOrderProductList()));
        	dtos.add(orderDto);
        }
        return dtos;
    }

	public List<OrderDto> getAll(final Page page) {
        List<Order> models = orderDao.getAll(page);
        List<OrderDto> dtos = new ArrayList<OrderDto>();
        for(Order order:models){
        	OrderDto orderDto = orderDtoUtil.convertModel2Dto(order);
    		orderDto.setOrderProductListDto(orderProductListDtoUtil.convertModel2Dto(order.getOrderProductList()));
        	dtos.add(orderDto);
        }
        return dtos;
    }

	public OrderDto doInIsolatedTx(OrderDto dto) {
		Order model = orderDtoUtil.convertDto2Model(dto);
		model.setOrderProductList(orderProductListDtoUtil.convertDto2Model(dto.getOrderProductListDto()));
		Long orderId = this.orderDao.save(model);
		dto.setOrderId(orderId);
		return dto;
	}


	public OrderDto findByName(String name) {
		return orderDtoUtil.convertModel2Dto(orderDao.findUniqueBy("name", name));
	}


	public void setOrderDao(OrderDao<Order, Long> orderDao) {
		this.orderDao = orderDao;
	}

	public List<OrderDto> find(Page page, OrderDto orderDto) {
		List<Order> models = new ArrayList<Order>();
		if(null!=orderDto&&null!=page){
			Order model = orderDtoUtil.convertDto2Model(orderDto);
			model.setOrderProductList(orderProductListDtoUtil.convertDto2Model(orderDto.getOrderProductListDto()));
			models= orderDao.findByModel(page,model);
		}else if(page==null&&orderDto==null){
			models=orderDao.getAll();
		}else if(orderDto==null&&null!=page){
			models=orderDao.getAll(page);
		}else{
			Order model = orderDtoUtil.convertDto2Model(orderDto);
			model.setOrderProductList(orderProductListDtoUtil.convertDto2Model(orderDto.getOrderProductListDto()));
			models=orderDao.findByModel(model);
		}
        List<OrderDto> dtos = new ArrayList<OrderDto>();
        for(Order order:models){
        	OrderDto orderDtoTemp = orderDtoUtil.convertModel2Dto(order);
        	orderDtoTemp.setOrderProductListDto(orderProductListDtoUtil.convertModel2Dto(order.getOrderProductList()));
        	dtos.add(orderDtoTemp);
        }
		return dtos;
	}
	
	
	public void deleteById(Long pk) throws AppException {
		Order order = orderDao.get(pk);
		OrderDto dto = orderDtoUtil.convertModel2Dto(order);
		dto.setOrderProductListDto(orderProductListDtoUtil.convertModel2Dto(order.getOrderProductList()));
		orderDao.delete(pk);
		if(null!=order.getOrderProductList()&&0!=order.getOrderProductList().getProductCount()){
			int balance =-order.getOrderProductList().getProductCount();
			dto.getOrderProductListDto().setProductCount(balance);
			CustomerDto customerDto = customerService.findById(order.getCustomerId());
			if(null!=customerDto){
				updateRebatesByCustomer(dto,customerDto);
				if(null!=customerDto.getLevel()&&customerDto.getLevel()>1){
					List<CustomerDto> customerDtos = customerService.findByIds(customerDto.getCustomerSequence());
					if(null!=customerDtos&&!customerDtos.isEmpty()){
						for(CustomerDto customerDtoTemp:customerDtos){
							if(null!=customerDtoTemp.getLevel()&&customerDtoTemp.getLevel()>1){
								this.updateShareRebatesByCustomer(dto, customerDtoTemp);
							}
						}
					}
				}
			}else{
				logger.error("Can not find the associated customer!");
			}
		}
    }

	
	public void deleteByIds(String[] ids) throws NumberFormatException, AppException {
		for(String pk:ids){
			deleteById(Long.parseLong(pk));
		}
	}

	
	public Long max(String propertyName, OrderDto dto) {
		return orderDao.max(propertyName);
	}

	
	public String generateOrderCode() {
		Long orderId = orderDao.max("orderId");
		String currentTimeStr = DateUtil.long2String(System.currentTimeMillis(), "yyyyMMdd");
		String initOrderCode = currentTimeStr+"1001";
		if(null==orderId){
			return initOrderCode;
		}else{
			Order order = orderDao.get(orderId);
			if(null!=order&&null!=order.getOrderCode()){
				int i = order.getOrderCode().indexOf(currentTimeStr);
				if(i==0){
					Integer index = Integer.parseInt(order.getOrderCode().substring(8));
					if(index<9999){
						index++;
						return order.getOrderCode().substring(0, 8)+index;
					}else{
						logger.error("generateOrderCode index greater than 9999");
						return null;
					}
				}else{
					return initOrderCode;
				}
			}else{
				return initOrderCode;
			}
		}
	}

	
	public OrderDto merge(OrderDto dto) throws AppException {
		Order model = orderDtoUtil.convertDto2Model(dto);
		model.setLastModifyTime(new Date());
		model.setOrderProductList(orderProductListDtoUtil.convertDto2Model(dto.getOrderProductListDto()));
		Order order = orderDao.get(dto.getOrderId());
		int balance =dto.getOrderProductListDto().getProductCount()-order.getOrderProductList().getProductCount();
		orderDao.merge(model);
		if(0!=balance){
			dto.getOrderProductListDto().setProductCount(balance);
			CustomerDto customerDto = customerService.findById(model.getCustomerId());
			if(null!=customerDto){
				updateRebatesByCustomer(dto,customerDto);
				model.getOrderProductList().setOrderId(dto.getOrderId());
				if(null!=customerDto.getLevel()&&customerDto.getLevel()>1){
					List<CustomerDto> customerDtos = customerService.findByIds(customerDto.getCustomerSequence());
					if(null!=customerDtos&&!customerDtos.isEmpty()){
						for(CustomerDto customerDtoTemp:customerDtos){
							if(null!=customerDtoTemp.getLevel()&&customerDtoTemp.getLevel()>1){
								this.updateShareRebatesByCustomer(dto, customerDtoTemp);
							}
						}
					}
				}
			}else{
				logger.error("Can not find the associated customer!");
			}
		}
        return dto;
    }

	
	public void cleanOrderHistory(OrderDto orderDto) {
		orderDao.cleanOrderHistory(orderDtoUtil.convertDto2Model(orderDto));
	}
}
