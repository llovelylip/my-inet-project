package inet.db.javagame;
import java.math.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import inet.util.*;
import inet.db.pool.*;
public class PartnerDAO {
	Logger logger=new Logger(this.getClass().getName());
	private DBPoolX poolX=null;
	public PartnerDAO(){
		try {
			poolX=DBPoolX.getInstance(DBPoolXName.SERVICE_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public boolean insertRow(String name,int mediaCode,String url,String username,String password, String commandCode){
    	if (username == null ) {
            logger.log("insertRow: User  is null");
            return false;
        }
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "INSERT INTO GAME_PARTNER (ID,NAME,MEDIA_CODE,URL,USER_NAME,PASSWORD,GEN_DATE,LAST_UPDATED,COMMAND_CODE) " +
                " VALUES (GAME_PARTNER_SEQ.nextval, ?, ?, ?,?,?,SYSDATE,SYSDATE,?)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, name);
            preStmt.setInt(2, mediaCode);
            preStmt.setString(3, url);
            preStmt.setString(4, username);
            preStmt.setString(5, Encrypter.encrypt(password));
            preStmt.setString(6, commandCode);
            if (preStmt.executeUpdate() == 1) {
                result = true;
            }
        } catch (Exception e) {
             logger.error("insertRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }
    public boolean updateRow(BigDecimal id,String name,int mediaCode,String url,String username,String password, String commandCode){
    	if (id == null) {
            logger.log("updateRow: id is null --> could not be updated");
            return false;
        }

        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn =poolX.getConnection();
            strSQL =
                "UPDATE GAME_PARTNER SET NAME=?,MEDIA_CODE=?,URL=?,USER_NAME=?,PASSWORD=?,LAST_UPDATED=SYSDATE, COMMAND_CODE =? " +
                "WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, name);
            preStmt.setInt(2, mediaCode);
            preStmt.setString(3, url);
            preStmt.setString(4, username);
            preStmt.setString(5, Encrypter.encrypt(password));
            preStmt.setString(6, commandCode);
            preStmt.setBigDecimal(7,id);
            if (preStmt.executeUpdate() == 1) {
                result = true;
            }
        } catch (Exception e) {
            logger.error("updateRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	poolX. releaseConnection(conn, preStmt);
           
        } return result;
    }
    public boolean deleteRow(BigDecimal id){
    	 boolean result = false;
	        Connection conn = null;
	        PreparedStatement preStmt = null;

	        String strSQL = null;
	        try {
	           conn = poolX.getConnection();
	            strSQL =
	                "delete from GAME_PARTNER where ID = ? ";
	            preStmt = conn.prepareStatement(strSQL);
	            preStmt.setBigDecimal(1, id);
	            if (preStmt.executeUpdate() > 0) {
	                result = true;
	            }
	        } catch(SQLException e) {
	            logger.error("deleteRow: Error executing SQL " + strSQL + ">>>" + e.toString());
	        } finally {
	        	poolX. releaseConnection(conn, preStmt);
	            
	        }return result;
    }
    public Collection findAll(){
        Vector result = new Vector();
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;

        String strSQL = null;
        Partner partner = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID,NAME,MEDIA_CODE,URL,USER_NAME,PASSWORD, COMMAND_CODE FROM GAME_PARTNER ORDER BY GEN_DATE"  ;
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            while (rs.next()) {
            	partner = new Partner();
            	partner.setId(rs.getBigDecimal(1));
            	partner.setName(rs.getString(2));
            	partner.setMediaCode(rs.getInt(3));
            	partner.setUrl(rs.getString(4));
            	partner.setUsername(rs.getString(5));
            	partner.setPassword(rs.getString(6));
            	partner.setCommandCode(rs.getString(7));
            	result.add(partner);
            }
        } catch (SQLException e) {
            logger.error("findAll(): Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	poolX. releaseConnection(conn, preStmt, rs);
           
        } return result;
    } 
        public Partner getRow(BigDecimal id){
        	if (id == null) return null;
            Connection conn = null;
            PreparedStatement preStmt = null;
            ResultSet rs = null;
            String strSQL = null;
            Partner partner= null;
            try {
                conn = poolX.getConnection();
                strSQL =
                    "SELECT  ID,NAME,MEDIA_CODE,URL,USER_NAME,PASSWORD,COMMAND_CODE FROM GAME_PARTNER "  +
                    " WHERE ID = ? ";
                preStmt = conn.prepareStatement(strSQL);
                preStmt.setBigDecimal(1, id);
                rs = preStmt.executeQuery();
                if (rs.next()) {
                	partner = new Partner();
                	partner.setId(rs.getBigDecimal(1));
                	partner.setName(rs.getString(2));
                	partner.setMediaCode(rs.getInt(3));
                	partner.setUrl(rs.getString(4));
                	partner.setUsername(rs.getString(5));
                	partner.setPassword(Encrypter.decrypt(rs.getString(6)));	
                	partner.setCommandCode(rs.getString(7));
                }

            } catch (Exception e) {
                logger.error("getRow: Error executing SQL " + strSQL + ">>>" +e.toString());
            } finally {
            	poolX.releaseConnection(conn, preStmt, rs);
               
            } return partner;
    }    
}
