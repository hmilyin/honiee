package com.ryan.doubletrack.spread.dto;

import com.ryan.framework.dto.Dto;
public class SpreadDto implements Dto{
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

	public SpreadDto() {
	}

	public SpreadDto(Integer id) {
		Id = id;
	}

	public SpreadDto(Integer id, String requestUrl, String channel, Integer spreadId,
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

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getSpreadId() {
		return spreadId;
	}

	public void setSpreadId(Integer spreadId) {
		this.spreadId = spreadId;
	}

	public String getCodePath() {
		return codePath;
	}

	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}
	
	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getLogoWidth() {
		return logoWidth;
	}

	public void setLogoWidth(String logoWidth) {
		this.logoWidth = logoWidth;
	}

	public String getLogoHeigth() {
		return logoHeigth;
	}

	public void setLogoHeigth(String logoHeigth) {
		this.logoHeigth = logoHeigth;
	}
}
