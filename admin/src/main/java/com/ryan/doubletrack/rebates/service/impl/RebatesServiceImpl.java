package com.ryan.doubletrack.rebates.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ryan.doubletrack.customer.dto.CustomerDto;
import com.ryan.doubletrack.customer.entity.Customer;
import com.ryan.doubletrack.customer.service.CustomerService;
import com.ryan.doubletrack.rebates.dao.RebatesDao;
import com.ryan.doubletrack.rebates.dto.RebatesDto;
import com.ryan.doubletrack.rebates.entity.Rebates;
import com.ryan.doubletrack.rebates.service.RebatesService;
import com.ryan.doubletrack.wage.dto.WageDto;
import com.ryan.doubletrack.wage.entity.Wage;
import com.ryan.doubletrack.wage.service.WageService;
import com.ryan.framework.dto.DtoUtil;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.service.impl.BaseServiceSupport;
import com.ryan.framework.tag.pagination.Page;

@Service
public class RebatesServiceImpl extends BaseServiceSupport<RebatesDto, Rebates, Long> implements RebatesService<RebatesDto,Rebates,Long>{
//	每组结算套数
	private int aGroupCount = 60;
//	按1:2或者2:1比例结算，每组小区结算套数，aGroupCount/3
	private int smallBranchUnitCount = aGroupCount/3;
//	按1:2或者2:1比例结算，每组大区结算套数：aGroupCount/3*2
	private int largeBranchUnitCount = aGroupCount/3*2;
//	按1:1比例结算，每组大区和小区结算套数
	private int equalBranchUnitCount = aGroupCount/2;
//	每套返利金额
	private int unitPrice = 78;
//	封顶结算套数
	private int topCount = 300;
//	最低结算组数
	private int lowestGroupCount = 3;
	
	@Resource
	private RebatesDao<Rebates,Long> rebatesDao;

	@Resource
	private CustomerService<CustomerDto,Customer,Long> customerService;

	@Resource
	private WageService<WageDto,Wage,Long> wageService;
	
	@SuppressWarnings("unchecked")
	private DtoUtil<RebatesDto,Rebates> rebatesDtoUtil = DtoUtil.getInstance(RebatesDto.class, Rebates.class);
	
	public RebatesDto save(final RebatesDto dto) {
		Rebates model = rebatesDtoUtil.convertDto2Model(dto);
		Long rebatesId = this.rebatesDao.save(model);
		dto.setRebatesId(rebatesId);
		return dto;
    }
    
    public RebatesDto update(final RebatesDto dto) {
		Rebates model = rebatesDtoUtil.convertDto2Model(dto);
		model.setLastModifyTime(new Date());
		rebatesDao.update(model);
		RebatesDto result = rebatesDtoUtil.convertModel2Dto(model);
        return result;
    }
    
	public void delete(final RebatesDto dto) {
		Rebates model = rebatesDtoUtil.convertDto2Model(dto);
        rebatesDao.delete(model);
    }

	public RebatesDto findById(Long pk) {
		Rebates model = rebatesDao.get(pk);
		RebatesDto rebatesDto = rebatesDtoUtil.convertModel2Dto(model);
        return rebatesDto;
    }

	public List<RebatesDto> getAll() {
        List<Rebates> models = rebatesDao.getAll();
        List<RebatesDto> dtos = new ArrayList<RebatesDto>();
        for(Rebates rebates:models){
        	RebatesDto rebatesDto = rebatesDtoUtil.convertModel2Dto(rebates);
        	dtos.add(rebatesDto);
        }
        return dtos;
    }

	public List<RebatesDto> getAll(final Page page) {
        List<Rebates> models = rebatesDao.getAll(page);
        List<RebatesDto> dtos = new ArrayList<RebatesDto>();
        for(Rebates rebates:models){
        	RebatesDto rebatesDto = rebatesDtoUtil.convertModel2Dto(rebates);
        	dtos.add(rebatesDto);
        }
        return dtos;
    }

	public RebatesDto doInIsolatedTx(RebatesDto dto) {
		Rebates model = rebatesDtoUtil.convertDto2Model(dto);
		Long rebatesId = this.rebatesDao.save(model);
		model.setRebatesId(rebatesId);
		RebatesDto rebatesDto =rebatesDtoUtil.convertModel2Dto(model);
		return rebatesDto;
	}


	public RebatesDto findByName(String name) {
		return rebatesDtoUtil.convertModel2Dto(rebatesDao.findUniqueBy("name", name));
	}


	public void setRebatesDao(RebatesDao<Rebates, Long> rebatesDao) {
		this.rebatesDao = rebatesDao;
	}

	public List<RebatesDto> find(Page page, RebatesDto rebatesDto) {
		List<Rebates> models = new ArrayList<Rebates>();
		if(null!=rebatesDto&&null!=page){
			Rebates model = rebatesDtoUtil.convertDto2Model(rebatesDto);
			models= rebatesDao.findByModel(page,model);
		}else if(page==null&&rebatesDto==null){
			models=rebatesDao.getAll();
		}else if(rebatesDto==null&&null!=page){
			models=rebatesDao.getAll(page);
		}else{
			Rebates model = rebatesDtoUtil.convertDto2Model(rebatesDto);
			models=rebatesDao.findByModel(model);
		}
        List<RebatesDto> dtos = new ArrayList<RebatesDto>();
        for(Rebates rebates:models){
        	RebatesDto rebatesDtoTemp = rebatesDtoUtil.convertModel2Dto(rebates);
        	dtos.add(rebatesDtoTemp);
        }
		return dtos;
	}
	
	
	public void deleteById(Long pk) {
		rebatesDao.delete(pk);
    }

	
	public void deleteByIds(String[] ids) {
		for(String pks:super.getPksList(ids)){
			rebatesDao.deleteByIds(pks);
		}
	}

	
	public RebatesDto findByCustomerIdAndBranch(RebatesDto dto) {
		Rebates model = rebatesDtoUtil.convertDto2Model(dto);
		Rebates rebates =rebatesDao.findByCustomerIdAndBranch(model);
		if(null!=rebates){
			return rebatesDtoUtil.convertModel2Dto(rebates);
		}else{
			return null;
		}
	}

	
	public RebatesDto merge(RebatesDto dto) throws AppException {
		Rebates model = rebatesDtoUtil.convertDto2Model(dto);
		model.setLastModifyTime(new Date());
		rebatesDao.merge(model);
		RebatesDto result = rebatesDtoUtil.convertModel2Dto(model);
        return result;
    }

	
	public void resultsOfSettlement() throws AppException {
		List<CustomerDto> customerDtoList = customerService.getAll();
		for(CustomerDto customerDto: customerDtoList){
			if(null!=customerDto){
				Rebates leftSearchRebates = new Rebates();
				leftSearchRebates.setCustomerId(customerDto.getCustomerId());
//				根据客户ID和分区查找左区业绩
				Rebates leftRebates = rebatesDao.findByCustomerIdAndBranch(leftSearchRebates);
				Rebates rightSearchRebates = new Rebates(); 
				rightSearchRebates.setCustomerId(customerDto.getCustomerId());
//				根据客户ID和分区查找右区业绩
				Rebates rightRebates = rebatesDao.findByCustomerIdAndBranch(rightSearchRebates);
				if(null!=leftRebates&&null!=rightRebates){
					if((leftRebates.getCount()+rightRebates.getCount())>aGroupCount){
						if(leftRebates.getCount()>rightRebates.getCount()){
//							左区业绩大于右区业绩
//							int leftEqualBranchCount = leftRebates.getCount()/equalBranchUnitCount;
							int smallEqualBranchCount = rightRebates.getCount()/equalBranchUnitCount;
							int largeBranchCount = leftRebates.getCount()/largeBranchUnitCount;
							int smallBranchCount = rightRebates.getCount()/smallBranchUnitCount;
							this.updateRebates(smallEqualBranchCount, largeBranchCount, leftRebates, smallBranchCount, rightRebates, customerDto);
						}else if(leftRebates.getCount()==rightRebates.getCount()){
//							左区业绩等于右区业绩
							int equalBranch = rightRebates.getCount()/equalBranchUnitCount;
							if(equalBranch>lowestGroupCount){
								leftRebates.setCount(leftRebates.getCount()-equalBranch*equalBranchUnitCount);
								rightRebates.setCount(rightRebates.getCount()-equalBranch*equalBranchUnitCount);
								updateRebates(equalBranch,leftRebates,rightRebates,customerDto,true);
							}
						}else{
//							右区业绩大于左区业绩
							int smallEqualBranchCount = leftRebates.getCount()/equalBranchUnitCount;
							int largeBranchCount = rightRebates.getCount()/largeBranchUnitCount;
							int smallBranchCount = leftRebates.getCount()/smallBranchUnitCount;
							this.updateRebates(smallEqualBranchCount, largeBranchCount, rightRebates, smallBranchCount, leftRebates, customerDto);
						}
					}
				}
			}
		}
	}

	private void updateRebates(int smallEqualBranchCount,int largeBranchCount,Rebates largeRebates,int smallBranchCount,Rebates smallRebates,CustomerDto customerDto) throws AppException{
		if(smallEqualBranchCount>=lowestGroupCount){
			if(largeBranchCount>=lowestGroupCount){
				if(largeBranchCount>=smallBranchCount){
					largeRebates.setCount(largeRebates.getCount()-smallBranchCount*largeBranchUnitCount);
					smallRebates.setCount(smallRebates.getCount()-smallBranchCount*smallBranchUnitCount);
					updateRebates(largeBranchCount,largeRebates,smallRebates,customerDto,false);
				}else{
					if(smallEqualBranchCount>largeBranchCount){
						largeRebates.setCount(largeRebates.getCount()-smallEqualBranchCount*equalBranchUnitCount);
						smallRebates.setCount(smallRebates.getCount()-smallEqualBranchCount*equalBranchUnitCount);
						updateRebates(smallEqualBranchCount,largeRebates,smallRebates,customerDto,true);
					}else{
						largeRebates.setCount(largeRebates.getCount()-largeBranchCount*largeBranchUnitCount);
						smallRebates.setCount(smallRebates.getCount()-largeBranchCount*smallBranchUnitCount);
						updateRebates(smallBranchCount,largeRebates,smallRebates,customerDto,false);
					}
				}
			}else{
//				左区业绩等于右区业绩
				int equalBranch = smallRebates.getCount()/equalBranchUnitCount;
				if(equalBranch>lowestGroupCount){
					largeRebates.setCount(largeRebates.getCount()-equalBranch*equalBranchUnitCount);
					smallRebates.setCount(smallRebates.getCount()-equalBranch*equalBranchUnitCount);
					updateRebates(equalBranch,largeRebates,smallRebates,customerDto,true);
				}
			}
		}else if(largeBranchCount>=lowestGroupCount&&smallBranchCount>=lowestGroupCount){
			if(largeBranchCount>=smallBranchCount){
				largeRebates.setCount(largeRebates.getCount()-smallBranchCount*largeBranchUnitCount);
				smallRebates.setCount(smallRebates.getCount()-smallBranchCount*smallBranchUnitCount);
				updateRebates(largeBranchCount,largeRebates,smallRebates,customerDto,false);
			}else{
				largeRebates.setCount(largeRebates.getCount()-largeBranchCount*largeBranchUnitCount);
				smallRebates.setCount(smallRebates.getCount()-largeBranchCount*smallBranchUnitCount);
				updateRebates(smallBranchCount,largeRebates,smallRebates,customerDto,false);
			}
		}
	}
	
	private void updateRebates(int smallBranch,Rebates leftRebates,Rebates rightRebates,CustomerDto customerDto,boolean equalFlag) throws AppException{
		WageDto wageDto = new WageDto();
		wageDto.setCustomerId(customerDto.getCustomerId());
		wageDto.setCustomerCode(customerDto.getCustomerCode());
		wageDto.setCustomerName(customerDto.getCustomerName());
		wageDto.setPhone(customerDto.getPhone());
		wageDto.setIdCardNumber(customerDto.getIdCardNumber());
		wageDto.setBankAccount(customerDto.getBankAccount());
		wageDto.setBankType(customerDto.getBankType());
		if(equalFlag){
			wageDto.setSalary((float) (smallBranch*2*aGroupCount*unitPrice));
		}else{
			wageDto.setSalary((float) (smallBranch*3*aGroupCount*unitPrice));
		}
		wageDto.setCreateTime(new Date());
//		if(equalFlag&&smallBranch*aGroupCount>=topCount){
//			wageDto.setSalary((float) (topCount*unitPrice));
//			wageService.save(wageDto);
//			leftRebates.setCount(0);
//			rightRebates.setCount(0);
//			rebatesDao.merge(leftRebates);
//			rebatesDao.merge(rightRebates);
//		}else 
		if(smallBranch*aGroupCount>=topCount){
			wageDto.setSalary((float) (topCount*unitPrice));
			wageService.save(wageDto);
			leftRebates.setCount(0);
			rightRebates.setCount(0);
			rebatesDao.merge(leftRebates);
			rebatesDao.merge(rightRebates);
		}else{
			wageService.save(wageDto);
			rebatesDao.merge(leftRebates);
			rebatesDao.merge(rightRebates);
		}
	}
	
	
	public List<RebatesDto> findByCustomerId(RebatesDto dto) {
		List<Rebates> rebatesList =rebatesDao.findBy("customerId", dto.getCustomerId());
		if(null!=rebatesList){
			return rebatesDtoUtil.convertModels2Dtos(rebatesList);
		}else{
			return null;
		}
	}
}
