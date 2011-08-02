package inet.db.game;

public class Downloader {
	private String mobile;
	private int count;
	private String ddmmyyyy;
	public Downloader(){
		
	}
	public Downloader(String mobile,int count,String ddmmyyyy){
		this.mobile=mobile;
		this.count=count;
		this.ddmmyyyy=ddmmyyyy;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDdmmyyyy() {
		return ddmmyyyy;
	}
	public void setDdmmyyyy(String ddmmyyyy) {
		this.ddmmyyyy = ddmmyyyy;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
