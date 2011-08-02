package inet.db.ichipgame;

import java.math.BigDecimal;
public class Game {
	private BigDecimal id;
    private String name;
    private BigDecimal catId;
    private String desc;
    private byte[] image;
    private byte[] wapImage;
    private String model1;
    private String model2;
    private String model3;
    private int seq;
    private int status;
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
	public String getModel1() {
		return model1;
	}
	public void setModel1(String model1) {
		this.model1 = model1;
	}
	public String getModel2() {
		return model2;
	}
	public void setModel2(String model2) {
		this.model2 = model2;
	}
	public String getModel3() {
		return model3;
	}
	public void setModel3(String model3) {
		this.model3 = model3;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public byte[] getWapImage() {
		return wapImage;
	}
	public void setWapImage(byte[] wapImage) {
		this.wapImage = wapImage;
	}
}
