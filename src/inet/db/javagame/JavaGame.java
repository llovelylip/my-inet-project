/*
 * Created on Nov 4, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package inet.db.javagame;
import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * @author Huyen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JavaGame {
	private BigDecimal id;
    private String name;
    private BigDecimal catId;
    private BigDecimal partnerId;
    private String desc;
    private byte[] image;
    private byte[] wapImage;
    private String model;
    private String partnerCode; //ma game cua doi tac
    private int inetCode;
    private int statusWeb;
    private int statusWap;
    private int downloadCounter;
    private Timestamp genDate;
    private Timestamp lastUpdate;
    private String partnerName;
    private int mediaCode;
    private String commandCode;
    
	public static int NOT_RUN = -1;
	public static int DEFAULT = 0;
	public static int TOP_WAP = 1;
	public static int TOP_WEB = 2;
	public static int TOP_WAP_DOWN = 3;
	
	public int getMediaCode() {
		return mediaCode;
	}
	public void setMediaCode(int mediaCode) {
		this.mediaCode = mediaCode;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public BigDecimal getCatId() {
		return catId;
	}
	public void setCatId(BigDecimal catId) {
		this.catId = catId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getDownloadCounter() {
		return downloadCounter;
	}
	public void setDownloadCounter(int downloadCounter) {
		this.downloadCounter = downloadCounter;
	}
	public Timestamp getGenDate() {
		return genDate;
	}
	public void setGenDate(Timestamp genDate) {
		this.genDate = genDate;
	}
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public int getInetCode() {
		return inetCode;
	}
	public void setInetCode(int inetCode) {
		this.inetCode = inetCode;
	}
	public Timestamp getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public BigDecimal getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(BigDecimal partnerId) {
		this.partnerId = partnerId;
	}
	
	public int getStatusWeb() {
		return statusWeb;
	}
	public void setStatusWeb(int statusWeb) {
		this.statusWeb = statusWeb;
	}
	public int getStatusWap() {
		return statusWap;
	}
	public void setStatusWap(int statusWap) {
		this.statusWap = statusWap;
	}
	public byte[] getWapImage() {
		return wapImage;
	}
	public void setWapImage(byte[] wapImage) {
		this.wapImage = wapImage;
	}
	public String getCommandCode() {
		return commandCode;
	}
	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}
}
