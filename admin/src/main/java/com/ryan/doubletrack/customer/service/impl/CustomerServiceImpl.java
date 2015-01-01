package com.ryan.doubletrack.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ryan.doubletrack.customer.dao.CustomerDao;
import com.ryan.doubletrack.customer.dto.CustomerDto;
import com.ryan.doubletrack.customer.entity.Customer;
import com.ryan.doubletrack.customer.service.CustomerService;
import com.ryan.doubletrack.enums.CustomerStatusEnum;
import com.ryan.doubletrack.enums.ExceptionEnum;
import com.ryan.framework.dto.DtoUtil;
import com.ryan.framework.entity.Tree;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.service.impl.BaseServiceSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.DateUtil;
import com.ryan.utils.StringUtil;

@Service("customerService")
public class CustomerServiceImpl extends BaseServiceSupport<CustomerDto, Customer, Long> implements CustomerService<CustomerDto,Customer,Long>{
	private String delimiter = ",";
	
	@Resource
	private CustomerDao<Customer,Long> customerDao;

	@SuppressWarnings("unchecked")
	private DtoUtil<CustomerDto,Customer> customerDtoUtil = DtoUtil.getInstance(CustomerDto.class, Customer.class);
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	
	public CustomerDto save(final CustomerDto dto){
		Customer model = customerDtoUtil.convertDto2Model(dto);
		if(null!=dto.getParentCustomerDto()){
			model.setParentCustomer(customerDtoUtil.convertDto2Model(dto.getParentCustomerDto()));
			if(null!=dto.getParentCustomerDto().getLevel()){
				model.setLevel(dto.getParentCustomerDto().getLevel()+1);
			}
		}	
		if(null!=dto.getRecommendCustomerDto()){	
			model.setRecommendCustomer(customerDtoUtil.convertDto2Model(dto.getRecommendCustomerDto()));
		}

		Long customerId = customerDao.save(model);
		if(1==model.getLevel()){
			model.setCustomerSequence(String.valueOf(customerId));
		}else{
			if(null!=dto.getParentCustomerDto()&&null!=dto.getParentCustomerDto().getCustomerId()){
				model.setCustomerSequence(customerDao.get(dto.getParentCustomerDto().getCustomerId()).getCustomerSequence()+delimiter+String.valueOf(customerId));
			}else{
				logger.error("parent customer sequence not null");
			}
		}
		dto.setCustomerId(customerId);
		return dto;
	
    }
	   
    public CustomerDto update(final CustomerDto dto) throws AppException {
    	if(null!=dto){
    		Customer model = customerDtoUtil.convertDto2Model(dto);
    		if(null!=dto.getParentCustomerDto()){
    			model.setParentCustomer(customerDtoUtil.convertDto2Model(dto.getParentCustomerDto()));
    			if(null!=dto.getParentCustomerDto().getLevel()){
    				model.setLevel(dto.getParentCustomerDto().getLevel()+1);
    			}
    		}
    		if(null!=dto.getRecommendCustomerDto()){
    			model.setRecommendCustomer(customerDtoUtil.convertDto2Model(dto.getRecommendCustomerDto()));
    		}
    		if(checkParent(model)){
    			if(1==model.getLevel()){
    				model.setCustomerSequence(String.valueOf(model.getCustomerId()));
    			}else{
    				if(null!=dto.getParentCustomerDto()&&null!=dto.getParentCustomerDto().getCustomerId()){
    					model.setCustomerSequence(customerDao.get(dto.getParentCustomerDto().getCustomerId()).getCustomerSequence()+delimiter+String.valueOf(model.getCustomerId()));
    				}else{
    					logger.error("parent customer sequence not null");
    				}
    			}
        		customerDao.update(model);
                return dto;
    		}else{
    			throw new AppException(ExceptionEnum.CUSTOMER_BRANCH_ERROR.getKey());
    		}
    	}else{
    		return null;
    	}
    }

	
	public CustomerDto merge(CustomerDto dto) throws AppException {
    	if(null!=dto){
    		Customer model = customerDtoUtil.convertDto2Model(dto);
    		if(null!=dto.getParentCustomerDto()){
    			model.setParentCustomer(customerDtoUtil.convertDto2Model(dto.getParentCustomerDto()));
    			if(null!=dto.getParentCustomerDto().getLevel()){
    				model.setLevel(dto.getParentCustomerDto().getLevel()+1);
    			}
    		}
    		if(null!=dto.getRecommendCustomerDto()){
    			model.setRecommendCustomer(customerDtoUtil.convertDto2Model(dto.getRecommendCustomerDto()));
    		}
    		if(checkParent(model)){
    			if(1==model.getLevel()){
    				model.setCustomerSequence(String.valueOf(model.getCustomerId()));
    			}else{
    				if(null!=dto.getParentCustomerDto()&&null!=dto.getParentCustomerDto().getCustomerId()){
    					model.setCustomerSequence(customerDao.get(dto.getParentCustomerDto().getCustomerId()).getCustomerSequence()+delimiter+String.valueOf(model.getCustomerId()));
    				}else{
    					logger.error("parent customer sequence not null");
    				}
    			}
        		customerDao.merge(model);
                return dto;
    		}else{
    			throw new AppException(ExceptionEnum.CUSTOMER_BRANCH_ERROR.getKey());
    		}
    	}else{
    		return null;
    	}
    }
    
    public boolean checkParent(Customer customer){
    	List<Customer> customerList = customerDao.findByParent(customer);
    	if(null!=customerList&&!customerList.isEmpty()){
    		for(Customer customerTemp:customerList){
    			if(null!=customer&&null!=customer.getCustomerId()&&customer.getCustomerId().equals(customerTemp.getCustomerId())){
    				return true;
    			}
    		}
        	return false;
    	}else{
        	return true;
    	}
    }
    
	public void delete(final CustomerDto dto) {
		Customer model = customerDtoUtil.convertDto2Model(dto);
        customerDao.delete(model);
    }

	public CustomerDto findById(Long pk) {
		Customer customer = customerDao.get(pk);
		if(null!=customer){
			CustomerDto customerDto = customerDtoUtil.convertModel2Dto(customer);
	    	if(null!=customer.getParentCustomer()){
	    		customerDto.setParentCustomerDto(customerDtoUtil.convertModel2Dto(customer.getParentCustomer()));
	    	}
	    	if(null!=customer.getRecommendCustomer()){
	    		customerDto.setRecommendCustomerDto(customerDtoUtil.convertModel2Dto(customer.getRecommendCustomer()));
	    	}
			return customerDto;
		}else{
			return null;
		}
    }

	public List<CustomerDto> getAll() {
        List<Customer> models = customerDao.getAll();
        List<CustomerDto> dtos = new ArrayList<CustomerDto>();
        for(Customer customer:models){
        	CustomerDto customerDto = customerDtoUtil.convertModel2Dto(customer);
        	if(null!=customer.getParentCustomer()){
        		customerDto.setParentCustomerDto(customerDtoUtil.convertModel2Dto(customer.getParentCustomer()));
        	}
        	if(null!=customer.getRecommendCustomer()){
        		customerDto.setRecommendCustomerDto(customerDtoUtil.convertModel2Dto(customer.getRecommendCustomer()));
        	}
        	dtos.add(customerDto);
        }
        return dtos;
    }

	public List<CustomerDto> getAll(final Page page) {
        List<Customer> models = customerDao.getAll(page);
        List<CustomerDto> dtos = new ArrayList<CustomerDto>();
        for(Customer customer:models){
        	CustomerDto customerDto = customerDtoUtil.convertModel2Dto(customer);
        	if(null!=customer.getParentCustomer()){
        		customerDto.setParentCustomerDto(customerDtoUtil.convertModel2Dto(customer.getParentCustomer()));
        	}
        	if(null!=customer.getRecommendCustomer()){
        		customerDto.setRecommendCustomerDto(customerDtoUtil.convertModel2Dto(customer.getRecommendCustomer()));
        	}
        	dtos.add(customerDto);
        }
        return dtos;
    }

	public CustomerDto doInIsolatedTx(CustomerDto dto) {
		Customer model = customerDtoUtil.convertDto2Model(dto);
		Long customerId = customerDao.save(model);
		model.setCustomerId(customerId);
		CustomerDto customerDto =customerDtoUtil.convertModel2Dto(model);
		return customerDto;
	}


	public CustomerDto findByName(String name) {
		Customer customer = customerDao.findUniqueBy("name", name);
		if(null!=customer){
			CustomerDto customerDto = customerDtoUtil.convertModel2Dto(customer);
	    	if(null!=customer.getParentCustomer()){
	    		customerDto.setParentCustomerDto(customerDtoUtil.convertModel2Dto(customer.getParentCustomer()));
	    	}
	    	if(null!=customer.getRecommendCustomer()){
	    		customerDto.setRecommendCustomerDto(customerDtoUtil.convertModel2Dto(customer.getRecommendCustomer()));
	    	}
			return customerDto;
		}else{
			return null;
		}
	}


	public void setCustomerDao(CustomerDao<Customer, Long> customerDao) {
		this.customerDao = customerDao;
	}

	public List<CustomerDto> find(Page page, CustomerDto customerDto) {
		List<Customer> models = new ArrayList<Customer>();
		if(null!=customerDto&&null!=page){
			Customer model = customerDtoUtil.convertDto2Model(customerDto);
			models= customerDao.findByModel(page,model,"customerId",true);
		}else if(page==null&&customerDto==null){
			models=customerDao.getAll("customerId",true);
		}else if(customerDto==null&&null!=page){
			models=customerDao.getAll(page,"customerId",true);
		}else{
			Customer model = customerDtoUtil.convertDto2Model(customerDto);
			models=customerDao.findByModel(model,"customerId",true);
		}
        List<CustomerDto> dtos = new ArrayList<CustomerDto>();
        for(Customer customer:models){
        	CustomerDto customerDtoTemp = customerDtoUtil.convertModel2Dto(customer);
        	if(null!=customer.getParentCustomer()){
            	customerDtoTemp.setParentCustomerDto(customerDtoUtil.convertModel2Dto(customer.getParentCustomer()));
        	}
        	if(null!=customer.getRecommendCustomer()){
            	customerDtoTemp.setRecommendCustomerDto(customerDtoUtil.convertModel2Dto(customer.getRecommendCustomer()));
        	}
        	dtos.add(customerDtoTemp);
        }
		return dtos;
	}
	
	
	public void deleteById(Long pk) {
		customerDao.delete(pk);
    }

	
	public void deleteByIds(String[] ids) {
		for(String pks:super.getPksList(ids)){
			customerDao.deleteByIds(pks);
		}
	}

	
	public List<CustomerDto> findByIds(String idsStr) {
		if(!StringUtil.isEmpty(idsStr)){
			List<CustomerDto> resultList = new ArrayList<CustomerDto>();
			String [] ids = idsStr.split(",");
			for(String pks:super.getPksList(ids)){
				List<Customer> customerList = customerDao.findByIds(pks);
				if(null!=customerList&&!customerList.isEmpty()){
					for(Customer customer:customerList){
						CustomerDto customerDto = customerDtoUtil.convertModel2Dto(customer);
						customerDto.setParentCustomerDto(customerDtoUtil.convertModel2Dto(customer.getParentCustomer()));
						customerDto.setRecommendCustomerDto(customerDtoUtil.convertModel2Dto(customer.getRecommendCustomer()));
						resultList.add(customerDto);
					}
				}
			}
			return resultList;
		}else{
			return null;
		}
	}

	
	public List<String> getCustomerTreeHtmlList() {
		List<String> customerTreeHtmlList = new ArrayList<String>();
		StringBuilder customerTreeHtml=new StringBuilder();
		List<Customer> rootCustomerList = customerDao.findBy("level", 1);
		if(null!=rootCustomerList&&!rootCustomerList.isEmpty()){
			for(Customer customer : rootCustomerList){
				Tree tree = new Tree();
				tree.setHref("@requestContextPath@/customer/customerView.htm?customerId="+customer.getCustomerId());
				tree.setTaget("navTab");
				tree.setRel("customerTreeBox");
				tree.setTitle("客户详情");
				List<Customer> customerList = new ArrayList<Customer>(); 
				customerTreeHtml.append("<ul class=\"tree treeFolder\">");
				if(null!=customer){
					customerList.add(customer);
					customerTreeHtml.append("<li><a href=\""+tree.getHref()+"\"").append(" target=\"").append(tree.getTaget()).append("\"").append(" rel=\"").append(tree.getRel()).append("\"").append(" title=\"").append(tree.getTitle()).append("\"").append(">").append(customer.getCustomerName()).append("</a>");
					customerDao.getCustomerTreeHtml(customerList, customer,customerTreeHtml);
					customerTreeHtml.append("<li>");
				}
				customerTreeHtml.append("</ul>");
				customerTreeHtmlList.add(customerTreeHtml.toString());
			}
		}
		return customerTreeHtmlList;
	}

	
	public Long max(String propertyName, CustomerDto dto) {
		return customerDao.max(propertyName);
	}

	
	public String generateCustomerCode() {
		Long orderId = customerDao.max("customerId");
		String currentTimeStr = DateUtil.long2String(System.currentTimeMillis(), "yyyyMMdd");
		String initCustomerCode = currentTimeStr+"1001";
		if(null==orderId){
			return initCustomerCode;
		}else{
			Customer customer = customerDao.get(orderId);
			if(null!=customer&&null!=customer.getCustomerCode()){
				int i = customer.getCustomerCode().indexOf(currentTimeStr);
				if(i==0){
					Integer index = Integer.parseInt(customer.getCustomerCode().substring(8));
					if(index<9999){
						index++;
						return customer.getCustomerCode().substring(0, 8)+index;
					}else{
						logger.error("generateCustomerCode index greater than 9999");
						return null;
					}
				}else{
					return initCustomerCode;
				}
			}else{
				return initCustomerCode;
			}
		}
	}

	
	public void freezeByIds(String[] ids) {
		for(String pks:super.getPksList(ids)){
			customerDao.updateStatusByIds(pks, CustomerStatusEnum.FREEZE.getKey());
		}
	}

	
	public void thawByIds(String[] ids) {
		for(String pks:super.getPksList(ids)){
			customerDao.updateStatusByIds(pks, CustomerStatusEnum.NORMAL.getKey());
		}
	}
}
