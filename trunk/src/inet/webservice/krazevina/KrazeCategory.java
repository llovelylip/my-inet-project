/*******************************************************************************
 * File name: KrazeCategory.java
 * @author Nguyen Thien Phuong 
 * Copyright (c) 2007 iNET Media Corporation 
 * Created on Nov 29, 2007
 ******************************************************************************/


package inet.webservice.krazevina;

import java.math.BigDecimal;

public class KrazeCategory {
	
	private BigDecimal id;
	private String name;
	private int type;
	
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
}
