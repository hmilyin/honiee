package com.ryan.doubletrack.spread.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ryan.doubletrack.spread.dao.SpreadDao;
import com.ryan.doubletrack.spread.dto.SpreadDto;
import com.ryan.doubletrack.spread.entity.Spread;
import com.ryan.doubletrack.spread.service.SpreadService;
import com.ryan.doubletrack.utils.QRCodeUtil;
import com.ryan.framework.dto.DtoUtil;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.service.impl.BaseServiceSupport;
import com.ryan.framework.tag.pagination.Page;
@Service
public class SpreadServiceImpl extends BaseServiceSupport<SpreadDto, Spread, Integer> implements SpreadService<SpreadDto,Spread,Integer>{

	@Resource
	private SpreadDao<Spread,Integer> spreadDao;
	@SuppressWarnings({ "unchecked" })
	private DtoUtil<SpreadDto,Spread> spreadDtoUtil = DtoUtil.getInstance(SpreadDto.class, Spread.class);
	
	public void delete(final SpreadDto dto) throws AppException {
		Spread model = spreadDtoUtil.convertDto2Model(dto);
		spreadDao.delete(model);
	}

	public void deleteById(Integer pk) throws AppException {
		spreadDao.delete(pk);
	}

	public SpreadDto findById(Integer pk) {
		Spread model = spreadDao.get(pk);
		SpreadDto spreadDto = spreadDtoUtil.convertModel2Dto(model);
        return spreadDto;
	}

	public List<SpreadDto> getAll() {
		 List<Spread> models = spreadDao.getAll();
	        List<SpreadDto> dtos = new ArrayList<SpreadDto>();
	        for(Spread spread:models){
	        	SpreadDto spreadDto = spreadDtoUtil.convertModel2Dto(spread);
	        	dtos.add(spreadDto);
	        }
	        return dtos;
	}

	public List<SpreadDto> getAll(final Page page) {
		 List<Spread> models = spreadDao.getAll(page);
	        List<SpreadDto> dtos = new ArrayList<SpreadDto>();
	        for(Spread spread:models){
	        	SpreadDto spreadDto = spreadDtoUtil.convertModel2Dto(spread);
	        	dtos.add(spreadDto);
	        }
	        return dtos;
	}

	public SpreadDto merge(SpreadDto dto) throws AppException {
		Spread model = spreadDtoUtil.convertDto2Model(dto);
		spreadDao.merge(model);
		SpreadDto result = spreadDtoUtil.convertModel2Dto(model);
        return result;
	}

	public SpreadDto save(final SpreadDto dto) throws AppException {
		Spread model = spreadDtoUtil.convertDto2Model(dto);
		String codestr = dto.getRequestUrl()+"?channel="+dto.getChannel()+"&spreadId="+dto.getSpreadId();
		String fileName =dto.getChannel()+"-"+dto.getSpreadId()+".jpg";
		String codePath=this.getClass().getResource("/").getPath()+"../../";
		try {
			QRCodeUtil.encode(codestr, codePath+dto.getLogoPath()+"/"+dto.getLogoName(), codePath+dto.getCodePath(),fileName, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.setCodeName(fileName);
		Integer id = this.spreadDao.save(model);
		dto.setId(id);
		return dto;
	}

	public SpreadDto update(final SpreadDto dto) throws AppException {
		Spread model = spreadDtoUtil.convertDto2Model(dto);
		spreadDao.update(model);
		SpreadDto result = spreadDtoUtil.convertModel2Dto(model);
        return result;
	}

	public List<SpreadDto> find(Page page, SpreadDto spreadDto) {
		List<Spread> models = new ArrayList<Spread>();
		if(null!=spreadDto&&null!=page){
			Spread model = spreadDtoUtil.convertDto2Model(spreadDto);
			models= spreadDao.findByModel(page,model);
		}else if(page==null&&spreadDto==null){
			models=spreadDao.getAll();
		}else if(spreadDto==null&&null!=page){
			models=spreadDao.getAll(page);
		}else{
			Spread model = spreadDtoUtil.convertDto2Model(spreadDto);
			models=spreadDao.findByModel(model);
		}
        List<SpreadDto> dtos = new ArrayList<SpreadDto>();
        for(Spread spread:models){
        	SpreadDto spreadDtoTemp = spreadDtoUtil.convertModel2Dto(spread);
        	dtos.add(spreadDtoTemp);
        }
		return dtos;
	}

}