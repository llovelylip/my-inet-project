package inet.db.fmcgame;

import java.math.BigDecimal;

public class Game {
	private BigDecimal id;
    private String name;
    private BigDecimal catId;
    private String desc;
    private byte[] image;
    private String model;
    private byte[] wapImage;
    private BigDecimal fmcId;
    private int downloadCounter;
    private String catName;
    private int top;
    
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public int getDownloadCounter() {
		return downloadCounter;
	}
	public void setDownloadCounter(int downloadCounter) {
		this.downloadCounter = downloadCounter;
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
	public BigDecimal getFmcId() {
		return fmcId;
	}
	public void setFmcId(BigDecimal fmcId) {
		this.fmcId = fmcId;
	}
	public byte[] getWapImage() {
		return wapImage;
	}
	public void setWapImage(byte[] wapImage) {
		this.wapImage = wapImage;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
}
