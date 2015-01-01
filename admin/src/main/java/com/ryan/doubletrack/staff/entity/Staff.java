package com.ryan.doubletrack.staff.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.ryan.framework.entity.Model;


@Entity
@Table(name = "staff")
public class Staff implements Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long staffId;//员工ID
	private String staffCode;//员工编号
	private String staffName;//员工名称
	private Integer organizationId;//机构编号
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
	
	public Staff() {
		
	}
	public Staff(Long staffId, String staffCode, String staffName) {
		this.staffId = staffId;
		this.staffCode = staffCode;
		this.staffName = staffName;
	}
	
	public Staff(Long staffId, String staffCode, String staffName,
			Integer organizationId, Integer positionId, String officeTel,
			String officePhone, String officeEmail, String homeAddress,
			String zipCode, String personalTel, String personalPhone,
			String personalEmail, String idCard, Date birthday, Integer gender,
			Integer education, String hobby, String specialty, String training,
			Date regeditDate, Date createTime, Date lastModifyTime) {
		super();
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
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "staff_id", unique = true, nullable = false)
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	
	@Column(name = "staff_code", nullable = false, length = 12)
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	@Column(name = "staff_name", nullable = false, length = 50)
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	@Column(name = "organization_id", length = 11)
	public Integer getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}
	@Column(name = "position_id", length = 11)
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}
	@Column(name = "office_tel", length = 15)
	public String getOfficeTel() {
		return officeTel;
	}
	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}
	@Column(name = "office_phone" , length = 15)
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	@Column(name = "office_email" , length = 128)
	public String getOfficeEmail() {
		return officeEmail;
	}
	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}
	@Column(name = "home_address", length = 128)
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	@Column(name = "zip_code" , length = 10)
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	@Column(name = "personal_tel" , length = 15)
	public String getPersonalTel() {
		return personalTel;
	}
	public void setPersonalTel(String personalTel) {
		this.personalTel = personalTel;
	}
	@Column(name = "personal_phone" , length = 15)
	public String getPersonalPhone() {
		return personalPhone;
	}
	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}
	@Column(name = "personal_email" , length = 128)
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	@Column(name = "id_card" , length = 30)
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "birthday")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Column(name = "gender" , length = 2)
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	@Column(name = "education" , length = 2)
	public Integer getEducation() {
		return education;
	}
	public void setEducation(Integer education) {
		this.education = education;
	}
	@Column(name = "hobby" , length = 400)
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	@Column(name = "specialty" , length = 400)
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	@Column(name = "training" , length = 800)
	public String getTraining() {
		return training;
	}
	public void setTraining(String training) {
		this.training = training;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "regedit_date")
	public Date getRegeditDate() {
		return regeditDate;
	}
	public void setRegeditDate(Date regeditDate) {
		this.regeditDate = regeditDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modify_time")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	
}


