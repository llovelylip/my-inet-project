package inet.db.fmcgame;
import inet.db.pool.*;
import inet.util.Logger;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

/**
 * @author Huyenntb
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GameLogDAO{

	private Logger logger;

	/**
	 * 
	 */
	private DBPoolX poolX=null;
	public GameLogDAO(){
		logger = new Logger(this.getClass().getName());
		try {
			poolX=DBPoolX.getInstance(DBPoolXName.AAA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/*	CREATE TABLE FMC_GAME_LOG(
	ID 			NUMBER ( 10 ) NOT NULL CONSTRAINT PK_JAVA_GAME_LOG  PRIMARY KEY,
	MOBILE_NUMBER 	VARCHAR2 ( 20 ) NOT NULL,
	DNID		VARCHAR ( 20 ) NOT NULL,
	GAME_CODE	VARCHAR(30) NOT NULL,
	RECEIVER	VARCHAR2 ( 20 ),	-- So dt nhan
	STATUS		NUMBER(2)  DEFAULT 0,  -- 0/1  not download/downloaded successfully.
	TIME_DOWNLOAD DATE DEFAULT SYSDATE,
	CODE		VARCHAR2(5),	-- Ma danh dau log nay cua Galafun (JA)/VTM (GAME)?
	SMS_REQUEST varchar2(160),
	SMS_RESPONSE varchar2(160),
	SERVICE_NUMBER varchar2(20)
	)
	*/

    public boolean insertRow(String mobileNumber, String dnid, String gameCode,
            String code, String receiver, String smsRequest, String smsResponse, String serviceNumber,String mobileOperator) {
        
    	if (mobileNumber == null || dnid == null || gameCode == null || code == null
        		|| "".equals(mobileNumber) || "".equals(dnid) || "".equals(gameCode) || "".equals(code) ) {
            logger.log("insertRow: parameters is null");
            return false;
        }
    	//
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();

            strSQL =
                "INSERT INTO FMC_GAME_LOG(ID, MOBILE_NUMBER, DNID, GAME_CODE, CODE, RECEIVER, SMS_REQUEST, SMS_RESPONSE, SERVICE_NUMBER,MOBILE_OPERATOR) " +
                "VALUES (FMC_GAME_LOG_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, mobileNumber);
            preStmt.setString(2, dnid);
            preStmt.setString(3, gameCode);
            preStmt.setString(4, code);
            preStmt.setString(5, receiver);
            preStmt.setString(6, smsRequest);
            preStmt.setString(7, smsResponse);
            preStmt.setString(8, serviceNumber);
            preStmt.setString(9, mobileOperator);
            if (preStmt.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            logger.error("insertRow: Error executing " + strSQL + " >>> " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("insertRow: " + ex.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        return result;
    }
    public String getUrlFromLog(String mobileNumber, String gameCode, String code) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;   
        String result = null;
        try {
            conn = poolX.getConnection();
            rs = preStmt.executeQuery();
            strSQL =
            	  " SELECT SMS_RESPONSE " +
                  " FROM FMC_GAME_LOG WHERE MOBILE_NUMBER=? AND CODE='VIETEL' AND GAME_CODE=? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, mobileNumber);
            preStmt.setString(2, gameCode);
            rs=preStmt.executeQuery();
            if (rs.next()) {
            	result=rs.getString(1);
            	if(!result.startsWith("http")){
            		result="http://"+result;
            	}
            }
           
        } catch (SQLException ex) {
            logger.error("getUrlFromLog: Error executing " + strSQL + " >>> " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("getUrlFromLog: " + ex.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        
        return result;

    }
	
	public Collection findAll(){
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                " SELECT ID, MOBILE_NUMBER, DNID, GAME_CODE, RECEIVER, STATUS, TIME_DOWNLOAD, CODE, SMS_REQUEST, SMS_RESPONSE " +
                " FROM FMC_GAME_LOG ORDER BY ID";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            result = new Vector();
           GameLog jgLog = null;
            
            while (rs.next()) {
            	jgLog = new GameLog();
            	jgLog.setId(rs.getBigDecimal(1));
            	jgLog.setMobileNumber(rs.getString(2));
            	jgLog.setDnid(rs.getString(3));
            	jgLog.setGameCode(rs.getString(4));
            	jgLog.setReceiver(rs.getString(5));
            	jgLog.setStatus(rs.getInt(6));
            	jgLog.setTimeDownload(rs.getTimestamp(7));
            	jgLog.setCode(rs.getString(8));
            	jgLog.setSmsRequest(rs.getString(9));
            	jgLog.setSmsResponse(rs.getString(10));

                result.addElement(jgLog);
            }
        } catch (SQLException e) {
            logger.error("findAll: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        return result;		
	}
	
	public Collection findByDay(String ddmmyyyy){
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                " SELECT ID, MOBILE_NUMBER, DNID, GAME_CODE, RECEIVER, STATUS, TIME_DOWNLOAD, CODE, SMS_REQUEST, SMS_RESPONSE " +
                " FROM FMC_GAME_LOG WHERE TO_CHAR(TIME_DOWNLOAD,'DD/MM/YYYY') = ? ORDER BY ID DESC";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, ddmmyyyy);
            rs = preStmt.executeQuery();
            result = new Vector();
            GameLog jgLog = null;
            
            while (rs.next()) {
            	jgLog = new GameLog();
            	jgLog.setId(rs.getBigDecimal(1));
            	jgLog.setMobileNumber(rs.getString(2));
            	jgLog.setDnid(rs.getString(3));
            	jgLog.setGameCode(rs.getString(4));
            	jgLog.setReceiver(rs.getString(5));
            	jgLog.setStatus(rs.getInt(6));
            	jgLog.setTimeDownload(rs.getTimestamp(7));
            	jgLog.setCode(rs.getString(8));
            	jgLog.setSmsRequest(rs.getString(9));
            	jgLog.setSmsResponse(rs.getString(10));

                result.addElement(jgLog);
            }
        } catch (SQLException e) {
            logger.error("findByDay: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        return result;		
	}
		
		
//	Tim ban ghi theo mobile cua khach hang 
	public Collection findByMobile(String mobile){
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        GameLog jgLog = null;
        Vector result = null;
        try {
            conn =poolX.getConnection();
            strSQL =
                " SELECT ID, MOBILE_NUMBER, DNID, GAME_CODE, RECEIVER, STATUS, TIME_DOWNLOAD, CODE, SMS_REQUEST, SMS_RESPONSE " +
                " FROM FMC_GAME_LOG WHERE MOBILE_NUMBER = ? ORDER BY ID";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,mobile);
            rs = preStmt.executeQuery();
            result = new Vector();
            while (rs.next()) {
            	jgLog = new GameLog();
            	jgLog.setId(rs.getBigDecimal(1));
            	jgLog.setMobileNumber(rs.getString(2));
            	jgLog.setDnid(rs.getString(3));
            	jgLog.setGameCode(rs.getString(4));
            	jgLog.setReceiver(rs.getString(5));
            	jgLog.setStatus(rs.getInt(6));
            	jgLog.setTimeDownload(rs.getTimestamp(7));
            	jgLog.setCode(rs.getString(8));
            	jgLog.setSmsRequest(rs.getString(9));
            	jgLog.setSmsResponse(rs.getString(10));

                result.addElement(jgLog);
            }
        } catch (SQLException e) {
            logger.error("findAll: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        return result;			
	}
	
//	Loc theo ma so chi ra log thuong JA(Galafun) hay GAME(VTM)	
	public Collection findPageByCode(int page, int rowsPerPage, String code){
        int startRow = (page-1) * rowsPerPage + 1;
        int stopRow  = page * rowsPerPage;

        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        GameLog jgLog = null;
        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                " SELECT ID, MOBILE_NUMBER, DNID, GAME_CODE, RECEIVER, STATUS, TIME_DOWNLOAD, CODE, SMS_REQUEST, SMS_RESPONSE " +
                " FROM (" +
                "		SELECT ID, MOBILE_NUMBER, DNID, GAME_CODE, RECEIVER, STATUS, TIME_DOWNLOAD, CODE, SMS_REQUEST, SMS_RESPONSE," +
                "			ROW_NUMBER() OVER(ORDER BY ID DESC) AS R " +
                " 		FROM FMC_GAME_LOG " +
                "		WHERE CODE = ?) " +
                " WHERE R BETWEEN ? AND ? ";
			
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,code);
            preStmt.setInt(2,startRow);
            preStmt.setInt(3,stopRow);
            
            rs = preStmt.executeQuery();
            result = new Vector();
            while (rs.next()) {
            	jgLog = new GameLog();
            	jgLog.setId(rs.getBigDecimal(1));
            	jgLog.setMobileNumber(rs.getString(2));
            	jgLog.setDnid(rs.getString(3));
            	jgLog.setGameCode(rs.getString(4));
            	jgLog.setReceiver(rs.getString(5));
            	jgLog.setStatus(rs.getInt(6));
            	jgLog.setTimeDownload(rs.getTimestamp(7));
            	jgLog.setCode(rs.getString(8));
            	jgLog.setSmsRequest(rs.getString(9));
            	jgLog.setSmsResponse(rs.getString(10));
                result.addElement(jgLog);
            }
        } catch (SQLException e) {
            logger.error("findPageByCode: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        return result;			
	}
	
	//	Tim ban ghi theo dnid do ben WDK cung cap	
	public GameLog findByDnid(String dnid){
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        GameLog jgLog = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                " SELECT ID, MOBILE_NUMBER, DNID, GAME_CODE, RECEIVER, STATUS, TIME_DOWNLOAD, CODE, SMS_REQUEST, SMS_RESPONSE " +
                " FROM FMC_GAME_LOG WHERE DNID = ? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,dnid);
            rs = preStmt.executeQuery();
            
            if (rs.next()) {
            	jgLog = new GameLog();
            	jgLog.setId(rs.getBigDecimal(1));
            	jgLog.setMobileNumber(rs.getString(2));
            	jgLog.setDnid(rs.getString(3));
            	jgLog.setGameCode(rs.getString(4));
            	jgLog.setReceiver(rs.getString(5));
            	jgLog.setStatus(rs.getInt(6));
            	jgLog.setTimeDownload(rs.getTimestamp(7));
            	jgLog.setCode(rs.getString(8));
            	jgLog.setSmsRequest(rs.getString(9));
            	jgLog.setSmsResponse(rs.getString(10));
            }
        } catch (SQLException e) {
            logger.error("findByDnid: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        return jgLog;			
	}	
	
//	get max ID	
	public BigDecimal getMaxId(){
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        
        BigDecimal result = null;
		
        try {
            conn =poolX.getConnection();
            strSQL =
                " SELECT MAX(ID) FROM FMC_GAME_LOG  ";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            
            if (rs.next()) {
            	result = rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            logger.error("getMaxId: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        return result;			
	}
	public Collection count(String mmyyyy,String dauso){
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        TK tk=null;
        Vector result=new Vector();
        try {
            conn = poolX.getConnection();
            strSQL =
                " SELECT COUNT(*),CODE,MOBILE_OPERATOR FROM FMC_GAME_LOG  WHERE TO_CHAR(TIME_DOWNLOAD,'MM/YYYY')=? AND SERVICE_NUMBER=? GROUP BY MOBILE_OPERATOR,CODE";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,mmyyyy);
            preStmt.setString(2,dauso);
            rs = preStmt.executeQuery();
            while(rs.next()) {
            	tk=new TK();
            	tk.setCount(rs.getInt(1));
            	tk.setCommandCode(rs.getString(2));
            	tk.setMobileOperator(rs.getString(3));
            	result.add(tk);
            }
        } catch (SQLException e) {
            logger.error("findPageByCode: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
        }
        return result;			
	}
		
	
}
