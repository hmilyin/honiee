package com.ryan.doubletrack.spread.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ryan.framework.entity.Model;

@Entity
@Table(name = "spread")
public class Spread implements Model{

	private static final long serialVersionUID = 1L;
	private Integer Id;
	private String requestUrl;
	private String channel;
	private Integer spreadId;
	private String codeName;
	private String mark;
	private String codePath;
	private String logoName;
	private String logoPath;
	private String logoWidth;
	private String logoHeigth;


	public Spread() {
		super();
	}

	public Spread(Integer id) {
		super();
		Id = id;
	}

	public Spread(Integer id, String requestUrl, String channel, Integer spreadId,
			String codeName, String mark, String codePath, String logoName,String logoPath,
			String logoWidth, String logoHeigth) {
		super();
		Id = id;
		this.requestUrl = requestUrl;
		this.channel = channel;
		this.spreadId = spreadId;
		this.codeName = codeName;
		this.mark = mark;
		this.codePath = codePath;
		this.logoName = logoName;
		this.logoPath = logoPath;
		this.logoWidth = logoWidth;
		this.logoHeigth = logoHeigth;
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	
	
	@Column(name = "request_url")
	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Column(name = "channel",length = 11)
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "spread_id", length = 11)
	public Integer getSpreadId() {
		return spreadId;
	}

	public void setSpreadId(Integer spreadId) {
		this.spreadId = spreadId;
	}
	@Column(name = "code_name", length = 50)
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	@Column(name = "mark")
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	@Column(name = "code_path", length = 50)
	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}
	
	@Column(name = "logo_name", length = 50)
	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	@Column(name = "logo_path", length = 28)
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	@Column(name = "logo_width", length = 4)
	public String getLogoWidth() {
		return logoWidth;
	}

	public void setLogoWidth(String logoWidth) {
		this.logoWidth = logoWidth;
	}
	@Column(name = "logo_heigth", length = 4)
	public String getLogoHeigth() {
		return logoHeigth;
	}

	public void setLogoHeigth(String logoHeigth) {
		this.logoHeigth = logoHeigth;
	}

}
