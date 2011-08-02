/*******************************************************************************
 * File name: KrazeGameList.java
 * @author Nguyen Thien Phuong 
 * Copyright (c) 2007 iNET Media Corporation 
 * Created on Nov 28, 2007
 ******************************************************************************/


package inet.webservice.krazevina;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class KrazeGameList {
	
	public static final int KT_GAME		= 1;
	public static final int KT_KARAOKE	= 2;
	
	private BigDecimal id;		//
	private BigDecimal catId;	//
	private String name;		// Ten game/bai hat karaoke
	private String krazeCode;	// Ma so tu ben Kraze
	private String description;
	
	private String handset1;
	private String handset2;
	private String handset3;
	
	private int downloaded;		// So lan download
	private int type;			// Game = 1; Karaoke = 2;
	private byte[] imgLink;
	private Timestamp lastUpdated;
	private byte[] wapImage;
	
	public byte[] getWapImage() {
		return wapImage;
	}
	public void setWapImage(byte[] wapImage) {
		this.wapImage = wapImage;
	}
	/**
	 * @return Returns the lastUpdated.
	 */
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	/**
	 * @param lastUpdated The lastUpdated to set.
	 */
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	/**
	 * @return Returns the downloaded.
	 */
	public int getDownloaded() {
		return downloaded;
	}
	/**
	 * @param downloaded The downloaded to set.
	 */
	public void setDownloaded(int downloaded) {
		this.downloaded = downloaded;
	}
	/**
	 * @return Returns the id.
	 */
	public BigDecimal getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}
	/**
	 * @return Returns the catId.
	 */
	public BigDecimal getCatId() {
		return catId;
	}
	/**
	 * @param catId The catId to set.
	 */
	public void setCatId(BigDecimal catId) {
		this.catId = catId;
	}
	/**
	 * @return Returns the krazeCode.
	 */
	public String getKrazeCode() {
		return krazeCode;
	}
	/**
	 * @param krazeCode The krazeCode to set.
	 */
	public void setKrazeCode(String krazeCode) {
		this.krazeCode = krazeCode;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return Returns the imgLink.
	 */
	public byte[] getImgLink() {
		return imgLink;
	}
	/**
	 * @param imgLink The imgLink to set.
	 */
	public void setImgLink(byte[] imgLink) {
		this.imgLink = imgLink;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the handset1.
	 */
	public String getHandset1() {
		return handset1;
	}
	/**
	 * @param handset1 The handset1 to set.
	 */
	public void setHandset1(String handset1) {
		this.handset1 = handset1;
	}
	/**
	 * @return Returns the handset2.
	 */
	public String getHandset2() {
		return handset2;
	}
	/**
	 * @param handset2 The handset2 to set.
	 */
	public void setHandset2(String handset2) {
		this.handset2 = handset2;
	}
	/**
	 * @return Returns the handset3.
	 */
	public String getHandset3() {
		return handset3;
	}
	/**
	 * @param handset3 The handset3 to set.
	 */
	public void setHandset3(String handset3) {
		this.handset3 = handset3;
	}
}
