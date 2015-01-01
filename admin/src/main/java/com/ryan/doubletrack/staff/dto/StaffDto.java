package com.ryan.doubletrack.staff.dto;


import java.util.Date;

import com.ryan.framework.dto.Dto;




public class StaffDto implements Dto {
	
	private Long staffId;//员工ID
	private String staffCode;//员工编号
	private String staffName;//员工名称
	private Integer organizationId;//机构编号 ,现在不使用
	private Integer positionId;//外键，职位等级
	private String officeTel;//办公电话
	private String officePhone;//办公手机号
	private String officeEmail;//办公邮箱
	private String homeAddress;//家庭地址
	private String zipCode;//邮政编码
	private String personalTel;//个人电话
	private String personalPhone;//个人电话2
	private String personalEmail;//个人邮箱
	private String idCard;//身份证
	private Date birthday;//生日
	private Integer gender;//性别:0.未知;1.男;2.女
	private Integer education;//学历:0-未知,1-高中以下,2-专科,3-本科,4-研究生,5-博士,6-博士后
	private String hobby;//兴趣爱好
	private String specialty;//专长，特长
	private String training;//培训经历
	private Date regeditDate;//注册时间
	private Date createTime;//创建时间
	private Date lastModifyTime;//最后修改时间
	
	
	
	public StaffDto() {
		
	}
	public StaffDto(Long staffId, String staffCode, String staffName) {
		this.staffId = staffId;
		this.staffCode = staffCode;
		this.staffName = staffName;
	}
	public StaffDto(Long staffId, String staffCode, String staffName,
			Integer organizationId, Integer positionId, String officeTel,
			String officePhone, String officeEmail, String homeAddress,
			String zipCode, String personalTel, String personalPhone,
			String personalEmail, String idCard, Date birthday, Integer gender,
			Integer education, String hobby, String specialty, String training,
			Date regeditDate, Date createTime, Date lastModifyTime) {
		this.staffId = staffId;
		this.staffCode = staffCode;
		this.staffName = staffName;
		this.organizationId = organizationId;
		this.positionId = positionId;
		this.officeTel = officeTel;
		this.officePhone = officePhone;
		this.officeEmail = officeEmail;
		this.homeAddress = homeAddress;
		this.zipCode = zipCode;
		this.personalTel = personalTel;
		this.personalPhone = personalPhone;
		this.personalEmail = personalEmail;
		this.idCard = idCard;
		this.birthday = birthday;
		this.gender = gender;
		this.education = education;
		this.hobby = hobby;
		this.specialty = specialty;
		this.training = training;
		this.regeditDate = regeditDate;
		this.createTime = createTime;
		this.lastModifyTime = lastModifyTime;
	}
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getOfficeEmail() {
		return officeEmail;
	}
	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPersonalTel() {
		return personalTel;
	}
	public void setPersonalTel(String personalTel) {
		this.personalTel = personalTel;
	}
	public String getPersonalPhone() {
		return personalPhone;
	}
	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getEducation() {
		return education;
	}
	public void setEducation(Integer education) {
		this.education = education;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getTraining() {
		return training;
	}
	public void setTraining(String training) {
		this.training = training;
	}
	public Date getRegeditDate() {
		return regeditDate;
	}
	public void setRegeditDate(Date regeditDate) {
		this.regeditDate = regeditDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	
}
