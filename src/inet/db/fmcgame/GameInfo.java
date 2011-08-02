/*
 * Created on Jul 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package inet.db.fmcgame;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author phuongnt
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GameInfo {
	
	private String cusId=null;

	private String statusCode=null;

	private String statusMessage=null;

	private String contentUrl=null;

	private String dnId=null;

	/**
	 * @return Returns the contentUrl.
	 */
	public String getContentUrl() {
		return contentUrl;
	}
	/**
	 * @param contentUrl The contentUrl to set.
	 */
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	/**
	 * @return Returns the cusId.
	 */
	public String getCusId() {
		return cusId;
	}
	/**
	 * @param cusId The cusId to set.
	 */
	public void setCusId(String cusId) {
		this.cusId = cusId;
	}
	/**
	 * @return Returns the dnId.
	 */
	public String getDnId() {
		return dnId;
	}
	/**
	 * @param dnId The dnId to set.
	 */
	public void setDnId(String dnId) {
		this.dnId = dnId;
	}
	/**
	 * @return Returns the statusCode.
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode The statusCode to set.
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return Returns the statusMessage.
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param statusMessage The statusMessage to set.
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	/**************************************************************************************
	 * Module xu ly phan tich file xml phan hoi tu WDK ve thong tin game                  *
	 * File xml co dang:                                                                  *
	 * <?xml version='1.0' encoding='UTF-8'?>                                             *
	 * <dk_content>                                                                       *
	 * 	   <dk_cusid>iNET</dk_cusid>                                                      *
	 * 	   <dk_statuscode>1000</dk_statuscode>                                            *
	 *     <dk_statusmsg>ok</dk_statusmsg>                                                *  
	 *     <dk_contenturl>http://203.125.125.198/wap/ya-auth.asp?rid=1234</dk_contenturl> *
	 *     <dk_dnid>1234</dk_dnid>                                                        *
	 * </dk_content>                                                                      *
	 *                                                                                    *
	***************************************************************************************/
	public boolean parseInfo(String info) {
		StringReader rd = new StringReader(info);
		InputSource is = new InputSource(rd);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			try {
				builder = factory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				return false;
			}
			Document document = null;
			document = builder.parse(is);
			NodeList list = document.getFirstChild().getChildNodes();
			if(list.item(0).getFirstChild()!=null)
			setCusId(list.item(0).getFirstChild().getNodeValue());
			if(list.item(1).getFirstChild()!=null)
			setStatusCode(list.item(1).getFirstChild().getNodeValue());
			if(list.item(2).getFirstChild()!=null)
			setStatusMessage(list.item(2).getFirstChild().getNodeValue());
			if(list.item(3).getFirstChild()!=null)
			setContentUrl(list.item(3).getFirstChild().getNodeValue());
			if(list.item(4).getFirstChild()!=null)
			setDnId(list.item(4).getFirstChild().getNodeValue());


		} catch (Exception e) {
			e.printStackTrace( System.err);
			return false;
		}
		return true;
	}
	
	/**************************************************************************************
	 * In thong tin nhan duoc
	 **************************************************************************************/
	public void printValue(){
//		System.out.println("cusId         = " + cusId);
//		System.out.println("statusCode    = " + statusCode);
//		System.out.println("statusMessage = " + statusMessage);
		System.out.println("dnId          = " + dnId);
		System.out.println("contentUrl    = " + contentUrl);
	}
	
}
