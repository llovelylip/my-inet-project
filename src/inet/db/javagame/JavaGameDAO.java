package inet.db.javagame;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import java.io.ByteArrayInputStream;
import java.io.IOException;


import inet.util.*;
import inet.db.pool.*;
/**
 * <p>Copyright: 2005</p>
 * @author huyen
 * @version 1.0
 */
public class JavaGameDAO  {
    private Logger logger;
    private DBPoolX poolX = null;
    public JavaGameDAO() {
        logger = new Logger(this.getClass().getName());
        try {
			poolX=DBPoolX.getInstance(DBPoolXName.SERVICE_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public boolean insertRow(String name ,String desc,String model, byte[] image, byte[] wapImage,String partnerCode,BigDecimal catId,BigDecimal partnerId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL  = null;
        ResultSet rs   = null;
        boolean result = false;
        try {
            conn = poolX.getConnection();
            Vector vSeqNum = new Vector();
            strSQL = "SELECT INET_CODE FROM Partner_Game WHERE PARTNER_ID=? ORDER BY INET_CODE ASC";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, partnerId);
            rs = preStmt.executeQuery();
            while (rs.next()) {
                vSeqNum.addElement(rs.getString(1));
            }
            int inetCode = 0;
            int i = 1;
            while (i <500000) {
                if (!vSeqNum.contains("" + i)) {
                	inetCode = i;
                    break;
                }
                i++;
            }
	        preStmt.close();
            strSQL =
            	"INSERT INTO Partner_game (ID,NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
            	"IMAGE,WAP_IMAGE,PARTNER_ID,DOWNLOAD_COUNTER, CAT_ID,GEN_DATE,LAST_UPDATE,NAME_VN, STATUS_WEB, STATUS_WAP) " +
                "VALUES (Partner_game_seq.nextval,?, ?,?,?,?,?,?,?,0,?,SYSDATE,SYSDATE,?, " + JavaGame.DEFAULT + "," + JavaGame.DEFAULT + ")";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,name);
            preStmt.setString(2,desc);
            preStmt.setString(3,model);
            preStmt.setString(4,partnerCode);
            preStmt.setInt(5,inetCode);
            preStmt.setBinaryStream(6, new ByteArrayInputStream(image), image.length);
            preStmt.setBinaryStream(7, new ByteArrayInputStream(wapImage), wapImage.length);
            preStmt.setBigDecimal(8,partnerId);
            preStmt.setBigDecimal(9,catId);
            preStmt.setString(10,StringTool.removeSpecialCharsInString(UTF8Tool.coDau2KoDau(name)));           
            if (preStmt.executeUpdate() == 1) {
                result=true;
            } 
        } catch (SQLException e) {
        	e.printStackTrace();
            logger.error("insertRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch (Exception e) {
        	logger.error("insertRow: " + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs); 
        }return result;
    }
    
    public boolean updateRow(BigDecimal id,String name ,String desc,String model, byte[] image, byte[] wapImage,String partnerCode,BigDecimal catId,BigDecimal partnerId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL  = null;
        ResultSet rs   = null;
        boolean result = false;
        try {
            conn = poolX.getConnection();
            if(image!=null&&image.length>0&&wapImage!=null&&wapImage.length>0){
            	//System.out.println("vao day 1");
            	 strSQL =
                     "UPDATE Partner_GAME SET NAME=?, DESCRIPTION=?, MODEL=?, PARTNER_CODE=?, " +
                     "IMAGE=?, WAP_IMAGE=?, PARTNER_ID=?, CAT_ID=?, GEN_DATE=SYSDATE, LAST_UPDATE=SYSDATE, NAME_VN=? WHERE ID=?";
            	 preStmt = conn.prepareStatement(strSQL);
            	 preStmt.setString(1,name);
                 preStmt.setString(2,desc);
                 preStmt.setString(3,model);
                 preStmt.setString(4,partnerCode);
                 preStmt.setBinaryStream(5, new ByteArrayInputStream(image), image.length);
                 preStmt.setBinaryStream(6, new ByteArrayInputStream(wapImage), wapImage.length);
                 preStmt.setBigDecimal(7,partnerId);
                 preStmt.setBigDecimal(8,catId);                 
                 preStmt.setString(9,StringTool.removeSpecialCharsInString(UTF8Tool.coDau2KoDau(name)));
                 preStmt.setBigDecimal(10, id);
            }else if(image!=null&&image.length>0){
            	//System.out.println("vao day 2");
            	 strSQL =
                     "UPDATE Partner_GAME SET NAME=?,DESCRIPTION=?,MODEL=?,PARTNER_CODE=?," +
                     "IMAGE=?,PARTNER_ID=?,CAT_ID=?,GEN_DATE=SYSDATE,LAST_UPDATE=SYSDATE,NAME_VN=? WHERE ID=?";
            	 preStmt = conn.prepareStatement(strSQL);
            	 preStmt.setString(1,name);
                 preStmt.setString(2,desc);
                 preStmt.setString(3,model);
                 preStmt.setString(4,partnerCode);
                 preStmt.setBinaryStream(5, new ByteArrayInputStream(image), image.length);
                 preStmt.setBigDecimal(6,partnerId);
                 preStmt.setBigDecimal(7,catId);
                 preStmt.setString(8,StringTool.removeSpecialCharsInString(UTF8Tool.coDau2KoDau(name)));
                 preStmt.setBigDecimal(9, id);
            }else if(wapImage!=null&&wapImage.length>0){
            	//System.out.println("vao day 3");
            	 strSQL =
                     "UPDATE Partner_GAME SET NAME=?,DESCRIPTION=?,MODEL=?,PARTNER_CODE=?," +
                     "WAP_IMAGE=?,PARTNER_ID=?,CAT_ID=?,GEN_DATE=SYSDATE,LAST_UPDATE=SYSDATE,NAME_VN=? WHERE ID=?";
            	 preStmt = conn.prepareStatement(strSQL);
            	 preStmt.setString(1,name);
                 preStmt.setString(2,desc);
                 preStmt.setString(3,model);
                 preStmt.setString(4,partnerCode);
                 preStmt.setBinaryStream(5, new ByteArrayInputStream(wapImage), wapImage.length);
                 preStmt.setBigDecimal(6,partnerId);
                 preStmt.setBigDecimal(7,catId);
                 preStmt.setString(8,StringTool.removeSpecialCharsInString(UTF8Tool.coDau2KoDau(name)));
                 preStmt.setBigDecimal(9, id);
            }else {
            	//System.out.println("vao day 4");
            	 strSQL =
                     "UPDATE Partner_GAME SET NAME = ? , DESCRIPTION=?, MODEL=?, PARTNER_CODE=?," +
                     "PARTNER_ID=?, CAT_ID=?, GEN_DATE=SYSDATE, LAST_UPDATE=SYSDATE, NAME_VN=? WHERE ID = ?";
            	 preStmt = conn.prepareStatement(strSQL);
            	 preStmt.setString(1,name);
                 preStmt.setString(2,desc);
                 preStmt.setString(3,model);
                 preStmt.setString(4,partnerCode);                 
                 preStmt.setBigDecimal(5,partnerId);
                 preStmt.setBigDecimal(6,catId);
                 preStmt.setString(7,StringTool.removeSpecialCharsInString(UTF8Tool.coDau2KoDau(name)));
                 preStmt.setBigDecimal(8, id);
            }
            if (preStmt.executeUpdate() >= 1) {
            	result=true;
            }          
         }    catch (Exception e) {
        	e.printStackTrace();
            logger.error("insertRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally { 
            poolX.releaseConnection(conn, preStmt, rs);  
        }return result;
    }
    public byte[] getImage(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        byte[] content = null;
        try {
            conn = poolX.getConnection();
            
            strSQL =
                "SELECT image FROM Partner_Game WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("image"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (SQLException e) {
            logger.error("getImage: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch (IOException e) {
            logger.error("getImage: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return content;
    }
    public byte[] getWapImage(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        byte[] content = null;
        try {
            conn = poolX.getConnection();
            
            strSQL =
                "SELECT wap_image FROM Partner_Game WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("wap_image"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (SQLException e) {
            logger.error("getWapImage: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch (IOException e) {
            logger.error("getWapImage: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return content;
    }
    
    public boolean deleteRow(BigDecimal id) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "delete from Partner_game where id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            if (preStmt.executeUpdate() > 0) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("deleteRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt);
            
        }return result;
    }
    public JavaGame getGameById(BigDecimal id){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        JavaGame game = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB, DOWNLOAD_COUNTER, STATUS_WAP, MEDIA_CODE " +
                "FROM Partner_game GAME JOIN game_partner PARTNER ON PARTNER.ID=GAME.PARTNER_ID WHERE GAME.id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                game = new JavaGame();
                game.setId(id);
                game.setName(rs.getString(1));
                game.setDesc(rs.getString(2));
                game.setModel(rs.getString(3));
                game.setPartnerCode(rs.getString(4));
                game.setInetCode(rs.getInt(5));
                game.setPartnerId(rs.getBigDecimal(6));
                game.setCatId(rs.getBigDecimal(7));
                game.setGenDate(rs.getTimestamp(8));
                game.setLastUpdate(rs.getTimestamp(9));
                game.setStatusWeb(rs.getInt(10));
                game.setDownloadCounter(rs.getInt(11));
                game.setStatusWap(rs.getInt(12));
                game.setMediaCode(rs.getInt(13));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return game;
    	
    }
    public JavaGame getGameByInetCode(int inetCode){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        JavaGame game = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,IMAGE,WAP_IMAGE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB ,ID, DOWNLOAD_COUNTER, STATUS_WAP " +
                "FROM Partner_game WHERE inet_code = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, inetCode);
            rs = preStmt.executeQuery();
            if (rs.next()) {
            	 game = new JavaGame();
                 game.setName(rs.getString(1));
                 game.setDesc(rs.getString(2));
                 game.setModel(rs.getString(3));
                 game.setPartnerCode(rs.getString(4));
                 game.setInetCode(rs.getInt(5));
                 game.setPartnerId(rs.getBigDecimal(6));
                 game.setCatId(rs.getBigDecimal(7));
                 game.setGenDate(rs.getTimestamp(8));
                 game.setLastUpdate(rs.getTimestamp(9));
                 game.setStatusWeb(rs.getInt(10));
                 game.setId(rs.getBigDecimal(11));
                 game.setDownloadCounter(rs.getInt(12));
                 game.setStatusWap(rs.getInt(13));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return game;
    	
    }
    
    public Collection findByCatId(BigDecimal catId){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        JavaGame game = null;
        Vector result=new Vector();
        try {
            conn = poolX.getConnection();
            
            strSQL =
                "SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE, STATUS_WEB,ID, DOWNLOAD_COUNTER, STATUS_WAP " +
                "FROM Partner_game WHERE cat_Id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, catId);
            rs = preStmt.executeQuery();
            while (rs.next()) {
            	 game = new JavaGame();
                 game.setName(rs.getString(1));
                 game.setDesc(rs.getString(2));
                 game.setModel(rs.getString(3));
                 game.setPartnerCode(rs.getString(4));
                 game.setInetCode(rs.getInt(5));
                 game.setPartnerId(rs.getBigDecimal(6));
                 game.setCatId(rs.getBigDecimal(7));
                 game.setGenDate(rs.getTimestamp(8));
                 game.setLastUpdate(rs.getTimestamp(9));
                 game.setStatusWeb(rs.getInt(10));
                 game.setId(rs.getBigDecimal(11));
                 game.setDownloadCounter(rs.getInt(12));
                 game.setStatusWap(rs.getInt(13));
                 result.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    	
    }
    public Collection findByPartnerId(BigDecimal partnerId){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        JavaGame game = null;
        Vector result=new Vector();
        try {
            conn = poolX.getConnection();
            
            strSQL =
                "SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER, STATUS_WAP " +
                "FROM Partner_game WHERE partner_Id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, partnerId);
            rs = preStmt.executeQuery();
            while (rs.next()) {
            	 game = new JavaGame();
                 game.setName(rs.getString(1));
                 game.setDesc(rs.getString(2));
                 game.setModel(rs.getString(3));
                 game.setPartnerCode(rs.getString(4));
                 game.setInetCode(rs.getInt(5));
                 game.setPartnerId(rs.getBigDecimal(6));
                 game.setCatId(rs.getBigDecimal(7));
                 game.setGenDate(rs.getTimestamp(8));
                 game.setLastUpdate(rs.getTimestamp(9));
                 game.setStatusWeb(rs.getInt(10));
                 game.setId(rs.getBigDecimal(11));
                 game.setDownloadCounter(rs.getInt(12));
                 game.setStatusWap(rs.getInt(13));
                 result.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    	
    }
    
    
    public JavaGame getGameByPartnerCode(String partnerCode,BigDecimal partnerId){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        JavaGame game = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER, STATUS_WAP " +
                "FROM Partner_game WHERE partner_code = ? and partner_id=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, partnerCode);
            preStmt.setBigDecimal(2, partnerId);
            rs = preStmt.executeQuery();
            if (rs.next()) {
            	game = new JavaGame();
                game.setName(rs.getString(1));
                game.setDesc(rs.getString(2));
                game.setModel(rs.getString(3));
                game.setPartnerCode(rs.getString(4));
                game.setInetCode(rs.getInt(5));
                game.setPartnerId(rs.getBigDecimal(6));
                game.setCatId(rs.getBigDecimal(7));
                game.setGenDate(rs.getTimestamp(8));
                game.setLastUpdate(rs.getTimestamp(9));
                game.setStatusWeb(rs.getInt(10));
                game.setId(rs.getBigDecimal(11));
                game.setDownloadCounter(rs.getInt(12));
                game.setStatusWap(rs.getInt(13));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return game;
    	
    }
  

   
     /**
       * @param catId
       * @param page: = 1, 2, 3...
       * @param rowsPerPage = 1, 2, 3 ...
       * @return
       */
     public Collection findByPage(BigDecimal catId,BigDecimal partnerId, int page, int rowsPerPage) {
 		/*if (catId == null || page < 1 || rowsPerPage < 1) {
 			return null;
 		}*/
 		int startRow = (page - 1) * rowsPerPage + 1;
 		int stopRow = page * rowsPerPage;
 		Connection conn = null;
 		PreparedStatement preStmt = null;
 		ResultSet rs = null;
 		String strSQL = null;
 		String where = " WHERE 1 = 1 ";
 		
 		if(partnerId!=null){
		  	where = where +" AND PARTNER_ID="+partnerId.toString();
 		}
 		if(catId!=null){
			where = where +" AND CAT_ID="+catId.toString();
 		} 		
 		Vector result = null;
 		try {
 			conn = poolX.getConnection();
 			strSQL =

 			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP FROM " +
 				"(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
 				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP," +
 				" ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
 				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID " +
 				" " + where + " )" +
 			" WHERE R>=? AND R<=?  ";
 			preStmt = conn.prepareStatement(strSQL);
 			preStmt.setInt(1,startRow);
 			preStmt.setInt(2,stopRow);
 			rs = preStmt.executeQuery();
 			result = new Vector();
 			JavaGame game = null;
 			while (rs.next()) {
 				game = new JavaGame();
                game.setName(rs.getString(1));
                game.setDesc(rs.getString(2));
                game.setModel(rs.getString(3));
                game.setPartnerCode(rs.getString(4));
                game.setInetCode(rs.getInt(5));
                game.setPartnerId(rs.getBigDecimal(6));
                game.setCatId(rs.getBigDecimal(7));
                game.setGenDate(rs.getTimestamp(8));
                game.setLastUpdate(rs.getTimestamp(9));
                game.setStatusWeb(rs.getInt(10));
                game.setId(rs.getBigDecimal(11));
                game.setDownloadCounter(rs.getInt(12));
                game.setPartnerName(rs.getString(13));
                game.setMediaCode(rs.getInt(14));
                game.setCommandCode(rs.getString(15));
                game.setStatusWap(rs.getInt(16));
                result.add(game);
 			}
 		} catch (SQLException e) {
 			logger.error("findByPage: Error executing SQL " + strSQL + ">>>"
 					+ e.toString());
 		} finally {
 			poolX.releaseConnection(conn, preStmt, rs);

 		}
 		return result;
 	}
    
   
    public int count(BigDecimal catId,BigDecimal partnerId) {
          Connection conn = null;
          PreparedStatement preStmt = null;
          String strSQL = null;
          ResultSet rs = null;
          String where=" WHERE 1=1 ";
   		  if(catId!=null){
   				where =where +" AND CAT_ID="+catId.toString();
   		  }
   		  if(partnerId!=null){
   			  	where =where +" AND PARTNER_ID="+partnerId.toString();
   		  }
          int count = 0;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "SELECT COUNT(*) FROM partner_game "+where;
              preStmt = conn.prepareStatement(strSQL);
              rs = preStmt.executeQuery();
              if (rs.next()) {
                  count = rs.getInt(1);
              }
          } catch (SQLException e) {
              logger.log("count: Error executing SQL " + strSQL + " >>> " + e.toString());
          } finally {
        	  poolX.releaseConnection(conn, preStmt, rs);
              
          }return count;
      }
      public Collection findByPage(BigDecimal catId,BigDecimal partnerId, String game_name, String partner_code, String inet_code, int page, int rowsPerPage) {
   		/*if (catId == null || page < 1 || rowsPerPage < 1) {
   			return null;
   		}*/
   		int startRow = (page - 1) * rowsPerPage + 1;
   		int stopRow = page * rowsPerPage;
   		Connection conn = null;
   		PreparedStatement preStmt = null;
   		ResultSet rs = null;
   		String strSQL = null;
   		String where = " WHERE 1 = 1 ";
   		
   		if(partnerId!=null){
  		  	where = where +" AND PARTNER_ID="+partnerId.toString();
   		}
   		if(catId!=null){
  			where = where +" AND CAT_ID="+catId.toString();
   		}
   		if(game_name != null){
   			where = where + " AND (UPPER(GAME.NAME) LIKE '%" + game_name.toUpperCase() + "%' OR UPPER(GAME.NAME_VN) LIKE '%" + game_name.toUpperCase() + "%')";
   		}
   		if(partner_code != null){
   			where = where + " AND UPPER(PARTNER_CODE) LIKE '%" + partner_code.toUpperCase() + "%'";
   		}
   		if(inet_code != null){
   			where = where + " AND (UPPER(INET_CODE) LIKE '%" + inet_code.toUpperCase() + "%' OR (UPPER(MEDIA_CODE) || UPPER(INET_CODE)) LIKE '%" + inet_code.toUpperCase() + "%')";
		}
   		Vector result = null;
   		try {
   			conn = poolX.getConnection();
   			strSQL =

   			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP FROM " +
   				"(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP," +
   				" ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
   				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID " +
   				" " + where + " )" +
   			" WHERE R>=? AND R<=?  ";
   			preStmt = conn.prepareStatement(strSQL);
   			preStmt.setInt(1,startRow);
   			preStmt.setInt(2,stopRow);
   			rs = preStmt.executeQuery();
   			result = new Vector();
   			JavaGame game = null;
   			while (rs.next()) {
   				game = new JavaGame();
                  game.setName(rs.getString(1));
                  game.setDesc(rs.getString(2));
                  game.setModel(rs.getString(3));
                  game.setPartnerCode(rs.getString(4));
                  game.setInetCode(rs.getInt(5));
                  game.setPartnerId(rs.getBigDecimal(6));
                  game.setCatId(rs.getBigDecimal(7));
                  game.setGenDate(rs.getTimestamp(8));
                  game.setLastUpdate(rs.getTimestamp(9));
                  game.setStatusWeb(rs.getInt(10));
                  game.setId(rs.getBigDecimal(11));
                  game.setDownloadCounter(rs.getInt(12));
                  game.setPartnerName(rs.getString(13));
                  game.setMediaCode(rs.getInt(14));
                  game.setCommandCode(rs.getString(15));
                  game.setStatusWap(rs.getInt(16));
                  result.add(game);
   			}
   		} catch (SQLException e) {
   			logger.error("findByPage: Error executing SQL " + strSQL + ">>>"
   					+ e.toString());
   		} finally {
   			poolX.releaseConnection(conn, preStmt, rs);

   		}
   		return result;
   	}
     
    public int countSearch(BigDecimal catId,BigDecimal partnerId, String game_name, String partner_code, String inet_code) {
            Connection conn = null;
            PreparedStatement preStmt = null;
            String strSQL = null;
            ResultSet rs = null;
            
            String where=" WHERE 1=1 ";
     		  if(catId!=null){
     				where =where +" AND partner_game.CAT_ID="+catId.toString();
     		  }
     		  if(partnerId!=null){
     			  	where =where +" AND partner_game.PARTNER_ID="+partnerId.toString();
     		  }
     		 if(game_name != null){
        			where = where + " AND (UPPER(partner_game.NAME) LIKE '%" + game_name.toUpperCase() + "%' OR UPPER(partner_game.NAME_VN) LIKE '%" + game_name.toUpperCase() + "%')";
        		}
        		if(partner_code != null){
        			where = where + " AND UPPER(partner_game.PARTNER_CODE) LIKE '%" + partner_code.toUpperCase() + "%'";
        		}
        		if(inet_code != null){
        			where = where + " AND (UPPER(partner_game.INET_CODE) LIKE '%" + inet_code.toUpperCase() + "%' OR (UPPER(game_partner.MEDIA_CODE) || UPPER(partner_game.INET_CODE)) LIKE '%" + inet_code.toUpperCase() + "%')";
        		}
        	String order = " ORDER BY partner_game.ID";
            int count = 0;
            try {
                conn = poolX.getConnection();
                strSQL =
                    "SELECT COUNT(partner_game.ID) FROM partner_game join game_partner ON partner_game.PARTNER_ID = game_partner.ID "+where+order;
                preStmt = conn.prepareStatement(strSQL);
                rs = preStmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } catch (SQLException e) {
                logger.log("countSearch: Error executing SQL " + strSQL + " >>> " + e.toString());
            } finally {
          	  poolX.releaseConnection(conn, preStmt, rs);
                
            }return count;
    	}
    public Collection findByPageWapSearch(String game_name, String inet_code, int page, int rowsPerPage) {
   		/*if (catId == null || page < 1 || rowsPerPage < 1) {
   			return null;
   		}*/
   		int startRow = (page - 1) * rowsPerPage + 1;
   		int stopRow = page * rowsPerPage;
   		Connection conn = null;
   		PreparedStatement preStmt = null;
   		ResultSet rs = null;
   		String strSQL = null;
   		String where = " WHERE 1 = 1 ";
   		
   		if(game_name != null){
   			where = where + " AND ((UPPER(GAME.NAME) LIKE '%" + game_name.toUpperCase() + "%' OR UPPER(GAME.NAME_VN) LIKE '%" + game_name.toUpperCase() + "%')";
   		}
   		if(inet_code != null){
   			where = where + " OR (UPPER(INET_CODE) LIKE '%" + inet_code.toUpperCase() + "%' OR (UPPER(MEDIA_CODE) || UPPER(INET_CODE)) LIKE '%" + inet_code.toUpperCase() + "%'))";
		}
   		Vector result = null;
   		try {
   			conn = poolX.getConnection();
   			strSQL =

   			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP FROM " +
   				"(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP," +
   				" ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
   				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID " +
   				" " + where + " )" +
   			" WHERE R>=? AND R<=?  ";
   			preStmt = conn.prepareStatement(strSQL);
   			preStmt.setInt(1,startRow);
   			preStmt.setInt(2,stopRow);
   			rs = preStmt.executeQuery();
   			result = new Vector();
   			JavaGame game = null;
   			while (rs.next()) {
   				game = new JavaGame();
                  game.setName(rs.getString(1));
                  game.setDesc(rs.getString(2));
                  game.setModel(rs.getString(3));
                  game.setPartnerCode(rs.getString(4));
                  game.setInetCode(rs.getInt(5));
                  game.setPartnerId(rs.getBigDecimal(6));
                  game.setCatId(rs.getBigDecimal(7));
                  game.setGenDate(rs.getTimestamp(8));
                  game.setLastUpdate(rs.getTimestamp(9));
                  game.setStatusWeb(rs.getInt(10));
                  game.setId(rs.getBigDecimal(11));
                  game.setDownloadCounter(rs.getInt(12));
                  game.setPartnerName(rs.getString(13));
                  game.setMediaCode(rs.getInt(14));
                  game.setCommandCode(rs.getString(15));
                  game.setStatusWap(rs.getInt(16));
                  result.add(game);
   			}
   		} catch (SQLException e) {
   			logger.error("findByPage: Error executing SQL " + strSQL + ">>>"
   					+ e.toString());
   		} finally {
   			poolX.releaseConnection(conn, preStmt, rs);

   		}
   		return result;
   	}
     
    	public int countWapSearch(String game_name, String inet_code) {
            Connection conn = null;
            PreparedStatement preStmt = null;
            String strSQL = null;
            ResultSet rs = null;
            
            String where=" WHERE 1=1 ";
            if(game_name != null){
       			where = where + " AND ((UPPER(partner_game.NAME) LIKE '%" + game_name.toUpperCase() + "%' OR UPPER(NAME_VN) LIKE '%" + game_name.toUpperCase() + "%')";
       		}
       		if(inet_code != null){
       			where = where + " OR (UPPER(INET_CODE) LIKE '%" + inet_code.toUpperCase() + "%' OR (UPPER(MEDIA_CODE) || UPPER(INET_CODE)) LIKE '%" + inet_code.toUpperCase() + "%'))";
    		}
        	String order = " ORDER BY partner_game.ID";
            int count = 0;
            try {
                conn = poolX.getConnection();
                strSQL =
                    "SELECT COUNT(partner_game.ID) FROM partner_game join game_partner ON partner_game.PARTNER_ID = game_partner.ID "+where+order;
                preStmt = conn.prepareStatement(strSQL);
                rs = preStmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            } catch (SQLException e) {
                logger.log("countWapSearch: Error executing SQL " + strSQL + " >>> " + e.toString());
            } finally {
          	  poolX.releaseConnection(conn, preStmt, rs);
                
            }return count;
    	}
      public Collection findByPageStatusWeb(BigDecimal catId,BigDecimal partnerId, int status, int page, int rowsPerPage) {
   		/*if (catId == null || page < 1 || rowsPerPage < 1) {
   			return null;
   		}*/
   		int startRow = (page - 1) * rowsPerPage + 1;
   		int stopRow = page * rowsPerPage;
   		Connection conn = null;
   		PreparedStatement preStmt = null;
   		ResultSet rs = null;
   		String strSQL = null;
   		String where = " WHERE 1 = 1 ";
   		
   		if(partnerId!=null){
  		  	where = where +" AND PARTNER_ID = " + partnerId.toString();
   		}
   		if(catId!=null){
  			where = where +" AND CAT_ID = " + catId.toString();
   		}
   		if(status != -2){
   			where = where + " AND STATUS_WEB = " + status;
   		}
   		Vector result = null;
   		try {
   			conn = poolX.getConnection();
   			strSQL =

   			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP FROM " +
   				"(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP," +
   				" ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
   				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID " +
   				" " + where + " )" +
   			" WHERE R>=? AND R<=?  ";
   			preStmt = conn.prepareStatement(strSQL);
   			preStmt.setInt(1,startRow);
   			preStmt.setInt(2,stopRow);
   			rs = preStmt.executeQuery();
   			result = new Vector();
   			JavaGame game = null;
   			while (rs.next()) {
   				game = new JavaGame();
                  game.setName(rs.getString(1));
                  game.setDesc(rs.getString(2));
                  game.setModel(rs.getString(3));
                  game.setPartnerCode(rs.getString(4));
                  game.setInetCode(rs.getInt(5));
                  game.setPartnerId(rs.getBigDecimal(6));
                  game.setCatId(rs.getBigDecimal(7));
                  game.setGenDate(rs.getTimestamp(8));
                  game.setLastUpdate(rs.getTimestamp(9));
                  game.setStatusWeb(rs.getInt(10));
                  game.setId(rs.getBigDecimal(11));
                  game.setDownloadCounter(rs.getInt(12));
                  game.setPartnerName(rs.getString(13));
                  game.setMediaCode(rs.getInt(14));
                  game.setCommandCode(rs.getString(15));
                  game.setStatusWap(rs.getInt(16));
                  result.add(game);
   			}
   		} catch (SQLException e) {
   			logger.error("findByPageStatusWeb: Error executing SQL " + strSQL + ">>>"
   					+ e.toString());
   		} finally {
   			poolX.releaseConnection(conn, preStmt, rs);

   		}
   		return result;
   	}
      
     
    public int countStatusWeb(BigDecimal catId, BigDecimal partnerId, int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;
        String where=" WHERE 1=1 ";
        if(catId!=null){
			where =where +" AND CAT_ID = " + catId.toString();
        }
        if(partnerId!=null){
		  	where =where +" AND PARTNER_ID = " + partnerId.toString();
        }
        if(status != -2){
			where = where + " AND STATUS_WEB = " + status;
		}
        
        int count = 0;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM partner_game "+where;
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log("countStatusWeb: Error executing SQL " + strSQL + " >>> " + e.toString());
        } finally {
      	  poolX.releaseConnection(conn, preStmt, rs);
            
        }return count;
	}
    public Collection findByPageStatusWap(BigDecimal catId,BigDecimal partnerId, int status, int page, int rowsPerPage) {
   		/*if (catId == null || page < 1 || rowsPerPage < 1) {
   			return null;
   		}*/
   		int startRow = (page - 1) * rowsPerPage + 1;
   		int stopRow = page * rowsPerPage;
   		Connection conn = null;
   		PreparedStatement preStmt = null;
   		ResultSet rs = null;
   		String strSQL = null;
   		String where = " WHERE 1 = 1 ";
   		
   		if(partnerId!=null){
  		  	where = where +" AND PARTNER_ID = " + partnerId.toString();
   		}
   		if(catId!=null){
  			where = where +" AND CAT_ID = " + catId.toString();
   		}
   		if(status != -2){
   			where = where + " AND STATUS_WAP = " + status;
   		}
   		Vector result = null;
   		try {
   			conn = poolX.getConnection();
   			strSQL =

   			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP FROM " +
   				"(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP," +
   				" ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
   				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID " +
   				" " + where + " )" +
   			" WHERE R>=? AND R<=?  ";
   			preStmt = conn.prepareStatement(strSQL);
   			preStmt.setInt(1,startRow);
   			preStmt.setInt(2,stopRow);
   			rs = preStmt.executeQuery();
   			result = new Vector();
   			JavaGame game = null;
   			while (rs.next()) {
   				game = new JavaGame();
                  game.setName(rs.getString(1));
                  game.setDesc(rs.getString(2));
                  game.setModel(rs.getString(3));
                  game.setPartnerCode(rs.getString(4));
                  game.setInetCode(rs.getInt(5));
                  game.setPartnerId(rs.getBigDecimal(6));
                  game.setCatId(rs.getBigDecimal(7));
                  game.setGenDate(rs.getTimestamp(8));
                  game.setLastUpdate(rs.getTimestamp(9));
                  game.setStatusWeb(rs.getInt(10));
                  game.setId(rs.getBigDecimal(11));
                  game.setDownloadCounter(rs.getInt(12));
                  game.setPartnerName(rs.getString(13));
                  game.setMediaCode(rs.getInt(14));
                  game.setCommandCode(rs.getString(15));
                  game.setStatusWap(rs.getInt(16));
                  result.add(game);
   			}
   		} catch (SQLException e) {
   			logger.error("findByPageStatusWap: Error executing SQL " + strSQL + ">>>"
   					+ e.toString());
   		} finally {
   			poolX.releaseConnection(conn, preStmt, rs);

   		}
   		return result;
   	}
      
     
    public int countStatusWap(BigDecimal catId, BigDecimal partnerId, int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;
        String where=" WHERE 1=1 ";
        if(catId!=null){
			where =where +" AND CAT_ID = " + catId.toString();
        }
        if(partnerId!=null){
		  	where =where +" AND PARTNER_ID = " + partnerId.toString();
        }
        if(status != -2){
			where = where + " AND STATUS_WAP = " + status;
		}
        
        int count = 0;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM partner_game "+where;
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log("countStatusWap: Error executing SQL " + strSQL + " >>> " + e.toString());
        } finally {
      	  poolX.releaseConnection(conn, preStmt, rs);
            
        }return count;
	}
	public Collection findTopWeb( int number) {
   		Connection conn = null;
   		PreparedStatement preStmt = null;
   		ResultSet rs = null;
   		String strSQL = null;
   		Vector result = new Vector();
   		try {
   			conn = poolX.getConnection();
   			strSQL =

   			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP FROM " +
   			"(SELECT GAME.NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,IMAGE,WAP_IMAGE," +
   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,GAME.DOWNLOAD_COUNTER," +
   				" PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP,ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R " +
   			" FROM PARTNER_GAME GAME JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID WHERE STATUS_WEB = " + JavaGame.TOP_WEB + ")" +
   			" WHERE  R<=?  ";
   			preStmt = conn.prepareStatement(strSQL);
   			preStmt.setInt(1,number);
   			rs = preStmt.executeQuery();
   			JavaGame game = null;
   			while (rs.next()) {
   				  game = new JavaGame();
                  game.setName(rs.getString(1));
                  game.setDesc(rs.getString(2));
                  game.setModel(rs.getString(3));
                  game.setPartnerCode(rs.getString(4));
                  game.setInetCode(rs.getInt(5));
                  game.setPartnerId(rs.getBigDecimal(6));
                  game.setCatId(rs.getBigDecimal(7));
                  game.setGenDate(rs.getTimestamp(8));
                  game.setLastUpdate(rs.getTimestamp(9));
                  game.setStatusWeb(rs.getInt(10));
                  game.setId(rs.getBigDecimal(11));
                  game.setDownloadCounter(rs.getInt(12));
                  game.setPartnerName(rs.getString(13));
                  game.setMediaCode(rs.getInt(14));
                  game.setCommandCode(rs.getString(15));
                  game.setStatusWap(rs.getInt(16));
                  result.add(game);
   			}
   			//System.out.println("size kq :" + result.size());
   		} catch (SQLException e) {
   			System.out.println("findTopWeb: Error executing SQL " + strSQL + ">>>"
   					+ e.toString());
   		} finally {
   			poolX.releaseConnection(conn, preStmt, rs);

   		}
   		return result;
	}
    	public Collection findTopWap( int number) {
	   		Connection conn = null;
	   		PreparedStatement preStmt = null;
	   		ResultSet rs = null;
	   		String strSQL = null;
	   		Vector result = null;
	   		try {
	   			conn = poolX.getConnection();
	   			strSQL =
	
	   			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP FROM " +
	   			"(SELECT GAME.NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,IMAGE,WAP_IMAGE," +
	   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,GAME.DOWNLOAD_COUNTER," +
	   				" PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP,ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R " +
	   			" FROM PARTNER_GAME GAME JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID WHERE STATUS_WAP = " + JavaGame.TOP_WAP + ")" +
	   			" WHERE  R<=?  ";
	   			preStmt = conn.prepareStatement(strSQL);
	   			preStmt.setInt(1,number);
	   			rs = preStmt.executeQuery();
	   			result = new Vector();
	   			JavaGame game = null;
	   			while (rs.next()) {
	   				game = new JavaGame();
	                  game.setName(rs.getString(1));
	                  game.setDesc(rs.getString(2));
	                  game.setModel(rs.getString(3));
	                  game.setPartnerCode(rs.getString(4));
	                  game.setInetCode(rs.getInt(5));
	                  game.setPartnerId(rs.getBigDecimal(6));
	                  game.setCatId(rs.getBigDecimal(7));
	                  game.setGenDate(rs.getTimestamp(8));
	                  game.setLastUpdate(rs.getTimestamp(9));
	                  game.setStatusWeb(rs.getInt(10));
	                  game.setId(rs.getBigDecimal(11));
	                  game.setDownloadCounter(rs.getInt(12));
	                  game.setPartnerName(rs.getString(13));
	                  game.setMediaCode(rs.getInt(14));
	                  game.setCommandCode(rs.getString(15));
	                  game.setStatusWap(rs.getInt(16));
	                  result.add(game);
	   			}
	   		} catch (SQLException e) {
	   			System.out.println("findTopWap: Error executing SQL " + strSQL + ">>>"
	   					+ e.toString());
	   		} finally {
	   			poolX.releaseConnection(conn, preStmt, rs);
	   		}
	   		return result;
    	}
    	public Collection findNewest( int number) {
    		Connection conn = null;
    		PreparedStatement preStmt = null;
    		ResultSet rs = null;
    		String strSQL = null;
    		Vector result = null;
    		try {
    			conn = poolX.getConnection();
    			strSQL =

    			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP FROM " +
    			"(SELECT GAME.NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,IMAGE,WAP_IMAGE," +
	   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,GAME.DOWNLOAD_COUNTER," +
	   				" PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP,ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R " +
	   			" FROM PARTNER_GAME GAME JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID )" +
    			" WHERE  R<=? ";
    			preStmt = conn.prepareStatement(strSQL);
    			//System.out.println("findNewest:"+strSQL);
    			preStmt.setInt(1,number);
    			rs = preStmt.executeQuery();
    			result = new Vector();
    			JavaGame game = null;
	   			while (rs.next()) {
	   				game = new JavaGame();
	                  game.setName(rs.getString(1));
	                  game.setDesc(rs.getString(2));
	                  game.setModel(rs.getString(3));
	                  game.setPartnerCode(rs.getString(4));
	                  game.setInetCode(rs.getInt(5));
	                  game.setPartnerId(rs.getBigDecimal(6));
	                  game.setCatId(rs.getBigDecimal(7));
	                  game.setGenDate(rs.getTimestamp(8));
	                  game.setLastUpdate(rs.getTimestamp(9));
	                  game.setStatusWeb(rs.getInt(10));
	                  game.setId(rs.getBigDecimal(11));
	                  game.setDownloadCounter(rs.getInt(12));
	                  game.setPartnerName(rs.getString(13));
	                  game.setMediaCode(rs.getInt(14));
	                  game.setCommandCode(rs.getString(15));
	                  game.setStatusWap(rs.getInt(16));
	                  result.add(game);
	   			}
    		} catch (SQLException e) {
    			System.out.println("findNewest: Error executing SQL " + strSQL + ">>>"
    					+ e.toString());
    		} finally {
    			poolX.releaseConnection(conn, preStmt, rs);

    		}
    		return result;
    	}
    	public Collection findByName(String name, int page, int rowsPerPage) {
	   		if (name == null || page < 1 || rowsPerPage < 1) {
	   			return null;
	   		}
	   		int startRow = (page - 1) * rowsPerPage + 1;
	   		int stopRow = page * rowsPerPage;
	   		Connection conn = null;
	   		PreparedStatement preStmt = null;
	   		ResultSet rs = null;
	   		String strSQL = null;
	   		name="%"+name.toUpperCase()+"%";
	   		Vector result = null;
	   		try {
	   			conn = poolX.getConnection();
	   			strSQL =
	
	   			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP FROM " +
	   				"(SELECT GAME.NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,IMAGE,WAP_IMAGE," +
	   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,GAME.DOWNLOAD_COUNTER," +
	   				" PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP,ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
	 				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID where UPPER(NAME_VN) LIKE ? )" +
	   			" WHERE R>= ? AND R<= ?  ";
	   			preStmt = conn.prepareStatement(strSQL);
	   			preStmt.setString(1, name);
	   			preStmt.setInt(2,startRow);
	   			preStmt.setInt(3,stopRow);
	   			rs = preStmt.executeQuery();
	   			result = new Vector();
	   			JavaGame game = null;
	   			while (rs.next()) {
	   				game = new JavaGame();
	                  game.setName(rs.getString(1));
	                  game.setDesc(rs.getString(2));
	                  game.setModel(rs.getString(3));
	                  game.setPartnerCode(rs.getString(4));
	                  game.setInetCode(rs.getInt(5));
	                  game.setPartnerId(rs.getBigDecimal(6));
	                  game.setCatId(rs.getBigDecimal(7));
	                  game.setGenDate(rs.getTimestamp(8));
	                  game.setLastUpdate(rs.getTimestamp(9));
	                  game.setStatusWeb(rs.getInt(10));
	                  game.setId(rs.getBigDecimal(11));
	                  game.setDownloadCounter(rs.getInt(12));
	                  game.setPartnerName(rs.getString(13));
	                  game.setMediaCode(rs.getInt(14));
	                  game.setCommandCode(rs.getString(15));
	                  game.setStatusWap(rs.getInt(16));
	                  result.add(game);
	   			}
	   		} catch (SQLException e) {
	   			logger.error("findByName: Error executing SQL " + strSQL + ">>>"
	   					+ e.toString());
	   		} finally {
	   			poolX.releaseConnection(conn, preStmt, rs);
	
	   		}
	   		return result;
    	}
     
    public int count(String name) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;
        name="%"+name.toUpperCase()+"%";
        int count = 0;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM partner_game where UPPER(NAME_VN) LIKE ?";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log("count: Error executing SQL " + strSQL + " >>> " + e.toString());
        } finally {
      	  poolX.releaseConnection(conn, preStmt, rs);
            
        }return count;
    }
    
    public BigDecimal getCount(BigDecimal catId,String dongmay,String doimay,String ten) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;
        dongmay = (dongmay != null) ? "%" + dongmay.toUpperCase() + "%" : "%";
        doimay = doimay != null ? "%" + doimay.toUpperCase() + "%" : "%";
        ten = ten != null ? "%" + ten.toUpperCase() + "%" : "%";
        
        BigDecimal count =null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM partner_game WHERE  upper(model) like  ? AND upper(model) LIKE ? AND CAT_ID=? AND (upper(NAME) LIKE ? OR upper(NAME_VN) LIKE ?)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,dongmay);
            preStmt.setString(2,doimay);
            preStmt.setBigDecimal(3,catId);
            preStmt.setString(4,ten);
            preStmt.setString(5, ten);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                count = rs.getBigDecimal(1);
               
            }
        } catch (SQLException e) {
            System.out.println("getCount: Error executing SQL " + strSQL + " >>> " + e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
            
        }return count;
    }
    
    public Collection findByPage(BigDecimal catId,String dongmay,String doimay,String ten, int page, int rowsPerPage) {
 		if (catId == null || page < 1 || rowsPerPage < 1) {
 			return null;
 		}
 		int startRow = (page - 1) * rowsPerPage + 1;
 		int stopRow = page * rowsPerPage;
 		dongmay=dongmay!=null?"%"+dongmay.toUpperCase()+"%":"%";
 	    doimay=doimay!=null?"%"+doimay.toUpperCase()+"%":"%";
 	    ten=ten!=null?"%"+ten.toUpperCase()+"%":"%";
 		Connection conn = null;
 		PreparedStatement preStmt = null;
 		ResultSet rs = null;
 		String strSQL = null;
 		Vector result = null;
 		try {
 			conn = poolX.getConnection();
 			strSQL =

 			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP FROM " +
 			"(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
 				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP," +
 				" ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
 				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID " +
 			" WHERE  upper(model) like  ? AND upper(model) LIKE ? AND CAT_ID=? AND (upper(GAME.NAME) LIKE ? OR upper(NAME_VN) LIKE ?))" +
 			" WHERE R>=? AND R<=?  ";
 			preStmt = conn.prepareStatement(strSQL);
 			preStmt.setString(1,dongmay);
            preStmt.setString(2,doimay);
 			preStmt.setBigDecimal(3, catId);
 			preStmt.setString(4,ten);
 			preStmt.setString(5, ten);
 			preStmt.setInt(6,startRow);
 			preStmt.setInt(7,stopRow);
 			rs = preStmt.executeQuery();
 			result = new Vector();
 			JavaGame game = null;
 			while (rs.next()) {
 				game = new JavaGame();
                game.setName(rs.getString(1));
                game.setDesc(rs.getString(2));
                game.setModel(rs.getString(3));
                game.setPartnerCode(rs.getString(4));
                game.setInetCode(rs.getInt(5));
                game.setPartnerId(rs.getBigDecimal(6));
                game.setCatId(rs.getBigDecimal(7));
                game.setGenDate(rs.getTimestamp(8));
                game.setLastUpdate(rs.getTimestamp(9));
                game.setStatusWeb(rs.getInt(10));
                game.setId(rs.getBigDecimal(11));
                game.setDownloadCounter(rs.getInt(12));
                game.setPartnerName(rs.getString(13));
                game.setMediaCode(rs.getInt(14));
                game.setCommandCode(rs.getString(15));
                game.setStatusWap(rs.getInt(16));
                result.add(game);
 			}
 		} catch (SQLException e) {
 			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
 					+ e.toString());
 		} finally {
 			poolX.releaseConnection(conn, preStmt, rs);

 		}
 		return result;
 	}
    
    public BigDecimal getCount(String dongmay) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;
        dongmay="%"+dongmay.toUpperCase()+"%";
        BigDecimal count =null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM PARTNER_GAME WHERE  upper(model) like  ? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,dongmay);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                count = rs.getBigDecimal(1);
               
            }
        } catch (SQLException e) {
            System.out.println("getCount: Error executing SQL " + strSQL + " >>> " + e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
            
        }return count;
    }
    public Collection findByPage(String dongmay, int page, int rowsPerPage) {
 		int startRow = (page - 1) * rowsPerPage + 1;
 		int stopRow = page * rowsPerPage;
 		Connection conn = null;
 		PreparedStatement preStmt = null;
 		ResultSet rs = null;
 		String strSQL = null;
 		Vector result = null;
 		dongmay="%"+dongmay.toUpperCase()+"%";
 		try {
 			conn = poolX.getConnection();
 			strSQL =

 			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP FROM " +
 			" (SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
 				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP," +
 				" ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
 				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID" +
 			" WHERE  upper(model) like ? AND GAME.STATUS_WEB != -1)" +
 			" WHERE R>=? AND R<=?  ";
 			preStmt = conn.prepareStatement(strSQL);
 			preStmt.setString(1, dongmay);
 			preStmt.setInt(2,startRow);
 			preStmt.setInt(3,stopRow);
 			rs = preStmt.executeQuery();
 			result = new Vector();
 			JavaGame game = null;
 			while (rs.next()) {
 				game = new JavaGame();
                game.setName(rs.getString(1));
                game.setDesc(rs.getString(2));
                game.setModel(rs.getString(3));
                game.setPartnerCode(rs.getString(4));
                game.setInetCode(rs.getInt(5));
                game.setPartnerId(rs.getBigDecimal(6));
                game.setCatId(rs.getBigDecimal(7));
                game.setGenDate(rs.getTimestamp(8));
                game.setLastUpdate(rs.getTimestamp(9));
                game.setStatusWeb(rs.getInt(10));
                game.setId(rs.getBigDecimal(11));
                game.setDownloadCounter(rs.getInt(12));
                game.setPartnerName(rs.getString(13));
                game.setMediaCode(rs.getInt(14));
                game.setCommandCode(rs.getString(15));
                game.setStatusWap(rs.getInt(16));
                result.add(game);
 			}
 		} catch (SQLException e) {
 			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
 					+ e.toString());
 		} finally {
 			poolX.releaseConnection(conn, preStmt, rs);

 		}
 		return result;
 	}
    //For wap
    public Collection findRandom( int number) {
   	 Connection conn = null;
   	 PreparedStatement preStmt = null;
   	 ResultSet rs = null;
   	 String strSQL = null;
   	 Vector result = null;
   	 try {
   		 conn = poolX.getConnection();
   		 strSQL =
   			 
   			 "SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP FROM " +
   			 "(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
 				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP," +
 				" ROW_NUMBER() OVER( ORDER BY dbms_random.value ) AS R FROM PARTNER_GAME GAME " +
 				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID WHERE STATUS_WAP = " + JavaGame.TOP_WAP + " )" +
   			 " WHERE  ROWNUM <= ?  ";
   		 preStmt = conn.prepareStatement(strSQL);
   		 preStmt.setInt(1,number);
   		 rs = preStmt.executeQuery();
   		 result = new Vector();
   		 JavaGame game = null;
			while (rs.next()) {
				game = new JavaGame();
            game.setName(rs.getString(1));
            game.setDesc(rs.getString(2));
            game.setModel(rs.getString(3));
            game.setPartnerCode(rs.getString(4));
            game.setInetCode(rs.getInt(5));
            game.setPartnerId(rs.getBigDecimal(6));
            game.setCatId(rs.getBigDecimal(7));
            game.setGenDate(rs.getTimestamp(8));
            game.setLastUpdate(rs.getTimestamp(9));
            game.setStatusWeb(rs.getInt(10));
            game.setId(rs.getBigDecimal(11));
            game.setDownloadCounter(rs.getInt(12));
            game.setPartnerName(rs.getString(13));
            game.setMediaCode(rs.getInt(14));
            game.setCommandCode(rs.getString(15));
            game.setStatusWap(rs.getInt(16));
            result.add(game);
			}
   	 } catch (SQLException e) {
   		 System.out.println("findRandom: Error executing SQL " + strSQL + ">>>"
   				 + e.toString());
   	 } finally {
   		 poolX.releaseConnection(conn, preStmt, rs);
   		 
   	 }
   	 return result;
    }
    public Collection findHotRandom( int number) {
   	 Connection conn = null;
   	 PreparedStatement preStmt = null;
   	 ResultSet rs = null;
   	 String strSQL = null;
   	 Vector result = null;
   	 try {
   		 conn = poolX.getConnection();
   		 strSQL =
   			 
   			 "SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP FROM " +
   			 "(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
 				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE,STATUS_WAP," +
 				" ROW_NUMBER() OVER( ORDER BY dbms_random.value ) AS R FROM PARTNER_GAME GAME " +
 				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID WHERE STATUS_WAP = " + JavaGame.TOP_WAP_DOWN + " )" +
   			 " WHERE  ROWNUM <= ?  ";
   		 preStmt = conn.prepareStatement(strSQL);
   		 preStmt.setInt(1,number);
   		 rs = preStmt.executeQuery();
   		 result = new Vector();
   		JavaGame game = null;
		while (rs.next()) {
			game = new JavaGame();
	        game.setName(rs.getString(1));
	        game.setDesc(rs.getString(2));
	        game.setModel(rs.getString(3));
	        game.setPartnerCode(rs.getString(4));
	        game.setInetCode(rs.getInt(5));
	        game.setPartnerId(rs.getBigDecimal(6));
	        game.setCatId(rs.getBigDecimal(7));
	        game.setGenDate(rs.getTimestamp(8));
	        game.setLastUpdate(rs.getTimestamp(9));
	        game.setStatusWeb(rs.getInt(10));
	        game.setId(rs.getBigDecimal(11));
	        game.setDownloadCounter(rs.getInt(12));
	        game.setPartnerName(rs.getString(13));
	        game.setMediaCode(rs.getInt(14));
	        game.setCommandCode(rs.getString(15));
	        game.setStatusWap(rs.getInt(16));
	        result.add(game);
		}
   	 } catch (SQLException e) {
   		 System.out.println("findHotRandom: Error executing SQL " + strSQL + ">>>"
   				 + e.toString());
   	 } finally {
   		 poolX.releaseConnection(conn, preStmt, rs);
   		 
   	 }
   	 return result;
    }
    public Collection findByPageSearch(BigDecimal catId,BigDecimal partnerId, String game_name, String partner_code, String inet_code, int page, int rowsPerPage) {
   		/*if (catId == null || page < 1 || rowsPerPage < 1) {
   			return null;
   		}*/
   		int startRow = (page - 1) * rowsPerPage + 1;
   		int stopRow = page * rowsPerPage;
   		Connection conn = null;
   		PreparedStatement preStmt = null;
   		ResultSet rs = null;
   		String strSQL = null;
   		String where = " WHERE 1 = 1 ";
   		
   		if(partnerId!=null){
  		  	where = where +" AND PARTNER_ID="+partnerId.toString();
   		}
   		if(catId!=null){
  			where = where +" AND CAT_ID="+catId.toString();
   		}
   		if(game_name != null){
   			where = where + " AND (UPPER(GAME.NAME) LIKE '%" + game_name.toUpperCase() + "%' OR UPPER(GAME.NAME_VN) LIKE '%" + game_name.toUpperCase() + "%')";
   		}
   		if(partner_code != null){
   			where = where + " AND UPPER(PARTNER_CODE) LIKE '%" + partner_code.toUpperCase() + "%'";
   		}
   		if(inet_code != null){
   			where = where + " AND (UPPER(INET_CODE) LIKE '%" + inet_code.toUpperCase() + "%' OR (UPPER(MEDIA_CODE) || UPPER(INET_CODE)) LIKE '%" + inet_code.toUpperCase() + "%')";
		}
   		Vector result = null;
   		try {
   			conn = poolX.getConnection();
   			strSQL =

   			"SELECT NAME,DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE,PARTNER_ID,CAT_ID,GEN_DATE,LAST_UPDATE,STATUS_WEB,ID,DOWNLOAD_COUNTER,PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP FROM " +
   				"(SELECT GAME.NAME,GAME.DESCRIPTION,MODEL,PARTNER_CODE,INET_CODE," +
   				" PARTNER_ID,CAT_ID,GAME.GEN_DATE,GAME.LAST_UPDATE,STATUS_WEB,GAME.ID,DOWNLOAD_COUNTER,PARTNER.NAME AS PARTNER_NAME,MEDIA_CODE,COMMAND_CODE, STATUS_WAP," +
   				" ROW_NUMBER() OVER(ORDER BY LAST_UPDATE DESC) AS R FROM PARTNER_GAME GAME " +
   				" JOIN GAME_PARTNER PARTNER ON PARTNER.ID=GAME.PARTNER_ID " +
   				" " + where + " )" +
   			" WHERE R>=? AND R<=?  ";
   			preStmt = conn.prepareStatement(strSQL);
   			preStmt.setInt(1,startRow);
   			preStmt.setInt(2,stopRow);
   			rs = preStmt.executeQuery();
   			result = new Vector();
   			JavaGame game = null;
   			while (rs.next()) {
   				game = new JavaGame();
                  game.setName(rs.getString(1));
                  game.setDesc(rs.getString(2));
                  game.setModel(rs.getString(3));
                  game.setPartnerCode(rs.getString(4));
                  game.setInetCode(rs.getInt(5));
                  game.setPartnerId(rs.getBigDecimal(6));
                  game.setCatId(rs.getBigDecimal(7));
                  game.setGenDate(rs.getTimestamp(8));
                  game.setLastUpdate(rs.getTimestamp(9));
                  game.setStatusWeb(rs.getInt(10));
                  game.setId(rs.getBigDecimal(11));
                  game.setDownloadCounter(rs.getInt(12));
                  game.setPartnerName(rs.getString(13));
                  game.setMediaCode(rs.getInt(14));
                  game.setCommandCode(rs.getString(15));
                  game.setStatusWap(rs.getInt(16));
                  result.add(game);
   			}
   		} catch (SQLException e) {
   			logger.error("findByPage: Error executing SQL " + strSQL + ">>>"
   					+ e.toString());
   		} finally {
   			poolX.releaseConnection(conn, preStmt, rs);

   		}
   		return result;
   	}
     
    
    public boolean moveToCategory(BigDecimal id, BigDecimal newCatId) {
        if (id == null || newCatId == null) {
            return false;
        }

        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE partner_game SET CAT_ID = ? WHERE id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, newCatId);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate() >= 1) {
                result = true;
            }
        } catch(SQLException e) {
            logger.log("moveToCategory: Error executing SQL " + strSQL + ">>>"+ e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }
    public boolean updateStatusWeb(BigDecimal id, int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE partner_game SET STATUS_WEB = ? WHERE id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, status);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate() >= 1) {
                result = true;
            }
        } catch(SQLException e) {
            logger.log("updateStatusWeb: Error executing SQL " + strSQL + ">>>"+ e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }
    public boolean updateStatusWap(BigDecimal id, int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE partner_game SET STATUS_WAP = ? WHERE id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, status);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate() >= 1) {
                result = true;
            }
        } catch(SQLException e) {
            logger.log("updateStatusWap: Error executing SQL " + strSQL + ">>>"+ e.toString());
        } finally {
        	poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }
    public boolean upTopWeb(BigDecimal id){
    	return updateStatusWeb(id, JavaGame.TOP_WEB);
    }
    public boolean upTopWap(BigDecimal id){
    	return updateStatusWap(id, JavaGame.TOP_WAP);
    }
}
