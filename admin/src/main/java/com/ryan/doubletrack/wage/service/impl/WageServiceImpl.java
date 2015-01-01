package com.ryan.doubletrack.wage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ryan.doubletrack.wage.dao.WageDao;
import com.ryan.doubletrack.wage.dto.WageDto;
import com.ryan.doubletrack.wage.entity.Wage;
import com.ryan.doubletrack.wage.service.WageService;
import com.ryan.framework.dto.DtoUtil;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.service.impl.BaseServiceSupport;
import com.ryan.framework.tag.pagination.Page;

@Service
public class WageServiceImpl extends BaseServiceSupport<WageDto, Wage, Long> implements WageService<WageDto,Wage,Long>{
	
	@Resource
	private WageDao<Wage,Long> wageDao;
	
	@SuppressWarnings("unchecked")
	private DtoUtil<WageDto,Wage> wageDtoUtil = DtoUtil.getInstance(WageDto.class, Wage.class);
	
	public WageDto save(final WageDto dto) {
		Wage model = wageDtoUtil.convertDto2Model(dto);
		Long wageId = this.wageDao.save(model);
		dto.setWageId(wageId);
		return dto;
    }
    
    public WageDto update(final WageDto dto) {
		Wage model = wageDtoUtil.convertDto2Model(dto);
		model.setLastModifyTime(new Date());
		wageDao.update(model);
		WageDto result = wageDtoUtil.convertModel2Dto(model);
        return result;
    }
    
	public void delete(final WageDto dto) {
		Wage model = wageDtoUtil.convertDto2Model(dto);
        wageDao.delete(model);
    }

	public WageDto findById(Long pk) {
		Wage model = wageDao.get(pk);
		WageDto wageDto = wageDtoUtil.convertModel2Dto(model);
        return wageDto;
    }

	public List<WageDto> getAll() {
        List<Wage> models = wageDao.getAll();
        List<WageDto> dtos = new ArrayList<WageDto>();
        for(Wage wage:models){
        	WageDto wageDto = wageDtoUtil.convertModel2Dto(wage);
        	dtos.add(wageDto);
        }
        return dtos;
    }

	public List<WageDto> getAll(final Page page) {
        List<Wage> models = wageDao.getAll(page);
        List<WageDto> dtos = new ArrayList<WageDto>();
        for(Wage wage:models){
        	WageDto wageDto = wageDtoUtil.convertModel2Dto(wage);
        	dtos.add(wageDto);
        }
        return dtos;
    }

	public WageDto doInIsolatedTx(WageDto dto) {
		Wage model = wageDtoUtil.convertDto2Model(dto);
		Long wageId = this.wageDao.save(model);
		model.setWageId(wageId);
		WageDto wageDto =wageDtoUtil.convertModel2Dto(model);
		return wageDto;
	}


	public WageDto findByName(String name) {
		return wageDtoUtil.convertModel2Dto(wageDao.findUniqueBy("name", name));
	}


	public void setWageDao(WageDao<Wage, Long> wageDao) {
		this.wageDao = wageDao;
	}

	public List<WageDto> find(Page page, WageDto wageDto) {
		List<Wage> models = new ArrayList<Wage>();
		if(null!=wageDto&&null!=page){
			Wage model = wageDtoUtil.convertDto2Model(wageDto);
			models= wageDao.findByModel(page,model);
		}else if(page==null&&wageDto==null){
			models=wageDao.getAll();
		}else if(wageDto==null&&null!=page){
			models=wageDao.getAll(page);
		}else{
			Wage model = wageDtoUtil.convertDto2Model(wageDto);
			models=wageDao.findByModel(model);
		}
        List<WageDto> dtos = new ArrayList<WageDto>();
        for(Wage wage:models){
        	WageDto wageDtoTemp = wageDtoUtil.convertModel2Dto(wage);
        	dtos.add(wageDtoTemp);
        }
		return dtos;
	}
	
	
	public void deleteById(Long pk) {
		wageDao.delete(pk);
    }

	
	public void deleteByIds(String[] ids) {
		for(String pks:super.getPksList(ids)){
			wageDao.deleteByIds(pks);
		}
	}
	
	
	public WageDto merge(WageDto dto) throws AppException {
		Wage model = wageDtoUtil.convertDto2Model(dto);
		model.setLastModifyTime(new Date());
		wageDao.merge(model);
		WageDto result = wageDtoUtil.convertModel2Dto(model);
        return result;
    }

	
	public WageDto findByCustomerId(Long customerId) {
		if(null!=customerId){
			return wageDtoUtil.convertModel2Dto(wageDao.findUniqueBy("customerId", customerId));
		}else{
			return null;
		}
	}

	
	public void cleanWageHistory(WageDto dto) {
		wageDao.cleanWageHistory(wageDtoUtil.convertDto2Model(dto));
	}
}
