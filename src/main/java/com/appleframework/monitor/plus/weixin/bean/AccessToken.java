package com.appleframework.monitor.plus.weixin.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信accessToken对象
 */
public class AccessToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String accessToken;
	private Date accessTime;
	private Integer status;
	private Date cancleTime;
	private String jsapiTicket;

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken
	 *            the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the accessTime
	 */
	public Date getAccessTime() {
		return accessTime;
	}

	/**
	 * @param accessTime
	 *            the accessTime to set
	 */
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	/**
	 * @return the statu
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param statu
	 *            the statu to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the cancleTime
	 */
	public Date getCancleTime() {
		return cancleTime;
	}

	/**
	 * @param cancleTime
	 *            the cancleTime to set
	 */
	public void setCancleTime(Date cancleTime) {
		this.cancleTime = cancleTime;
	}

	/**
	 * @return the jsapiTicket
	 */
	public String getJsapiTicket() {
		return jsapiTicket;
	}

	/**
	 * @param jsapiTicket
	 *            the jsapiTicket to set
	 */
	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

}

