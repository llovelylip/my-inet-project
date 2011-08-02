package inet.db.fmcgame;


import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author phuongnt
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GameLog {
	
/*	CREATE TABLE JAVA_GAME_LOG(
			ID 			NUMBER ( 10 ) NOT NULL CONSTRAINT PK_JAVA_GAME_LOG  PRIMARY KEY,
			MOBILE_NUMBER 	VARCHAR2 ( 20 ) NOT NULL,
			DNID		VARCHAR ( 20 ) NOT NULL,
			GAME_CODE	VARCHAR(30) NOT NULL,
			RECEIVER	VARCHAR2 ( 20 ),	-- So dt nhan
			STATUS		NUMBER(2)  DEFAULT 0,  -- 0/1  not download/downloaded successfully.
			TIME_DOWNLOAD DATE DEFAULT SYSDATE,
			CODE		VARCHAR2(5),	-- Ma danh dau log nay cua Galafun (JA)/VTM (GAME)?
		)
	
*/
	private BigDecimal id;
	private String mobileNumber;
	private String dnid;
	private String gameCode;
	private String receiver;
	private int status;
	private Timestamp timeDownload;
	private String code;
	private String smsRequest;
	private String smsResponse;
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return Returns the dnid.
	 */
	public String getDnid() {
		return dnid;
	}
	/**
	 * @param dnid The dnid to set.
	 */
	public void setDnid(String dnid) {
		this.dnid = dnid;
	}
	/**
	 * @return Returns the gameCode.
	 */
	public String getGameCode() {
		return gameCode;
	}
	/**
	 * @param gameCode The gameCode to set.
	 */
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
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
	 * @return Returns the mobileNumber.
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	/**
	 * @param mobileNumber The mobileNumber to set.
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	/**
	 * @return Returns the receiver.
	 */
	public String getReceiver() {
		return receiver;
	}
	/**
	 * @param receiver The receiver to set.
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return Returns the timeDownload.
	 */
	public Timestamp getTimeDownload() {
		return timeDownload;
	}
	/**
	 * @param timeDownload The timeDownload to set.
	 */
	public void setTimeDownload(Timestamp timeDownload) {
		this.timeDownload = timeDownload;
	}
    /***************************************************************
     * @return Returns the smsRequest.
     ***************************************************************/
    public String getSmsRequest() {
        return smsRequest;
    }
    /***************************************************************
     * @param smsRequest The smsRequest to set.
     ***************************************************************/
    public void setSmsRequest(String smsRequest) {
        this.smsRequest = smsRequest;
    }
    /***************************************************************
     * @return Returns the smsResponse.
     ***************************************************************/
    public String getSmsResponse() {
        return smsResponse;
    }
    /***************************************************************
     * @param smsResponse The smsResponse to set.
     ***************************************************************/
    public void setSmsResponse(String smsResponse) {
        this.smsResponse = smsResponse;
    }
}

