package inet.db.game;

import java.math.BigDecimal;

public class Game {
    private BigDecimal id;
    private String name;
    private String info;
    private String model;
    private int free;
    private byte[] gifFile;
    private byte[] wapFile;
    private byte[] jarFile;
    private int status;
    private int downloadCounter;
    private BigDecimal catId;
    private String nameVn;
    private String catName;
    public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getNameVn() {
		return nameVn;
	}
	public void setNameVn(String nameVn) {
		this.nameVn = nameVn;
	}
	public Game() {
    }
    public BigDecimal getId() {
        return id;
    }
    public void setId(BigDecimal id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
  public String getInfo() {
    return info;
  }
  public byte[] getJarFile() {
    return jarFile;
  }
  public void setInfo(String info) {
    this.info = info;
  }
  public void setJarFile(byte[] jarFile) {
    this.jarFile = jarFile;
  }
  public byte[] getGifFile() {
    return gifFile;
  }
  public void setGifFile(byte[] gifFile) {
    this.gifFile = gifFile;
  }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
	public int getFree() {
		return free;
	}
	public void setFree(int free) {
		this.free = free;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public byte[] getWapFile() {
		return wapFile;
	}
	public void setWapFile(byte[] wapFile) {
		this.wapFile = wapFile;
	}
}
