package com.ryan.doubletrack.staff.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ryan.doubletrack.staff.dao.StaffDao;
import com.ryan.doubletrack.staff.dto.StaffDto;
import com.ryan.doubletrack.staff.entity.Staff;
import com.ryan.doubletrack.staff.service.StaffService;
import com.ryan.framework.dto.DtoUtil;
import com.ryan.framework.exception.AppException;
import com.ryan.framework.service.impl.BaseServiceSupport;
import com.ryan.framework.tag.pagination.Page;
import com.ryan.utils.DateUtil;

@Service
public class StaffServiceImpl extends BaseServiceSupport<StaffDto, Staff, Long> implements StaffService<StaffDto,Staff,Long>{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
	private StaffDao<Staff,Long> staffDao;

	@SuppressWarnings("unchecked")
	private DtoUtil<StaffDto,Staff> staffDtoUtil = DtoUtil.getInstance(StaffDto.class, Staff.class);
	
	public StaffDto save(final StaffDto dto) {
		Staff model = staffDtoUtil.convertDto2Model(dto);
		Long staffId = this.staffDao.save(model);
		dto.setStaffId(staffId);;
		return dto;
    }
    
    public StaffDto update(final StaffDto dto) {
		Staff model = staffDtoUtil.convertDto2Model(dto);
		staffDao.update(model);
		StaffDto result =staffDtoUtil.convertModel2Dto(model);
        return result;
    }
    
	public void delete(final StaffDto dto) {
		Staff model = staffDtoUtil.convertDto2Model(dto);
        staffDao.delete(model);
    }

	public StaffDto findById(Long pk) {
		Staff model = staffDao.get(pk);
		StaffDto staffDto = staffDtoUtil.convertModel2Dto(model);
        return staffDto;
    }

	public List<StaffDto> getAll() {
        List<Staff> models = staffDao.getAll();
        List<StaffDto> dtos = new ArrayList<StaffDto>();
        for(Staff staff:models){
        	StaffDto staffDto = staffDtoUtil.convertModel2Dto(staff);
        	dtos.add(staffDto);
        }
        return dtos;
    }

	public List<StaffDto> getAll(final Page page) {
        List<Staff> models = staffDao.getAll(page);
        List<StaffDto> dtos = new ArrayList<StaffDto>();
        for(Staff staff:models){
        	StaffDto staffDto = staffDtoUtil.convertModel2Dto(staff);
        	dtos.add(staffDto);
        }
        return dtos;
    }

	public StaffDto doInIsolatedTx(StaffDto dto) {
		Staff model = staffDtoUtil.convertDto2Model(dto);
		Long staffId = this.staffDao.save(model);
		model.setStaffId(staffId);;
		StaffDto staffDto = staffDtoUtil.convertModel2Dto(model);
		return staffDto;
	}


	public StaffDto findByName(String name) {
		return staffDtoUtil.convertModel2Dto(staffDao.findUniqueBy("name", name));
	}


	public void setStaffDao(StaffDao<Staff, Long> staffDao) {
		this.staffDao = staffDao;
	}

	public List<StaffDto> find(Page page, StaffDto staffDto) {
		List<Staff> models = new ArrayList<Staff>();
		if(null!=staffDto&&null!=page){
			Staff model = staffDtoUtil.convertDto2Model(staffDto);
			models= staffDao.findByModel(page,model);
		}else if(page==null&&staffDto==null){
			models=staffDao.getAll();
		}else if(staffDto==null&&null!=page){
			models=staffDao.getAll(page);
		}else{
			Staff model = staffDtoUtil.convertDto2Model(staffDto);
			models=staffDao.findByModel(model);
		}
        List<StaffDto> dtos = new ArrayList<StaffDto>();
        for(Staff staff:models){
        	StaffDto staffDtoTemp = staffDtoUtil.convertModel2Dto(staff);
        	dtos.add(staffDtoTemp);
        }
		return dtos;
	}
	
	
	public void deleteById(Long pk) {
		staffDao.delete(pk);
    }

	
	public void deleteByIds(String[] ids) {
		for(String pks:super.getPksList(ids)){
			staffDao.deleteByIds(pks);
		}
	}

	public String generateStaffCode() {
		Long staffId = staffDao.max("staffId");
		String currentTimeStr = DateUtil.long2String(System.currentTimeMillis(), "yyyyMMdd");
		String initstaffCode = currentTimeStr+"1001";
		if(null==staffId){
			return initstaffCode;
		}else{
			Staff staff = staffDao.get(staffId);
			if(null!=staff&&null!=staff.getStaffCode()){
				int i = staff.getStaffCode().indexOf(currentTimeStr);
				if(i==0){
					Integer index = Integer.parseInt(staff.getStaffCode().substring(8));
					if(index<9999){
						index++;
						return staff.getStaffCode().substring(0, 8)+index;
					}else{
						logger.error("generateStaffCode index greater than 9999");
						return null;
					}
				}else{
					return initstaffCode;
				}
			}else{
				return initstaffCode;
			}
		}
	}
	
	public StaffDto merge(StaffDto dto) throws AppException {
		Staff model = staffDtoUtil.convertDto2Model(dto);
		staffDao.merge(model);
		StaffDto result = staffDtoUtil.convertModel2Dto(model);
        return result;
	}
}
