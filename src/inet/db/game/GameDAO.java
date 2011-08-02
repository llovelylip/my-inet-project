package inet.db.game;

import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import java.io.IOException;
import inet.util.*;
import inet.db.pool.*;
import java.io.*;

/**
 * <p>Copyright: 2005</p>
 * @author Nguyen Trong Tho
 * @version 1.0
 */

public class GameDAO {
    private Logger logger;
    private DBPoolX poolX=null;
    public GameDAO() {
        logger = new Logger(this.getClass().getName());
        try{
    		poolX=DBPoolX.getInstance(DBPoolXName.AAA);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    public boolean insertRow(String name, String info, String model, byte[] gif, byte[] jar,byte[] wap, BigDecimal catId) {
        Connection conn = null;

        PreparedStatement preStmt = null;
        String strSQL  = null;
        ResultSet rs   = null;
        boolean result = false;
        try {
            conn = poolX.getConnection();
            //1 - Get next Seq#
            Vector vSeqNum = new Vector();
            strSQL = "SELECT ID FROM GAME ORDER BY ID ASC";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            while (rs.next()) {
                vSeqNum.addElement(rs.getString(1));
            }

            int seqNum = 0;
            int i = 1;
            while (i <500000) {
                if (!vSeqNum.contains("" + i)) {
                    seqNum = i;
                    break;
                }
                i++;
            }
            preStmt.close();
            strSQL =
                "INSERT INTO GAME (ID, NAME, INFO, MODEL, GIF_FILE, JAR_FILE,WAP_FILE, CAT_ID,NAME_VN) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, seqNum);
            preStmt.setString(2, name);
            preStmt.setString(3, info);
            preStmt.setString(4, model);
            preStmt.setBinaryStream(5, new ByteArrayInputStream(gif), gif.length);
            preStmt.setBinaryStream(6, new ByteArrayInputStream(jar),jar.length);
            preStmt.setBinaryStream(7, new ByteArrayInputStream(wap),wap.length);
            preStmt.setBigDecimal(8, catId);
            preStmt.setString(9, StringTool.removeSpecialCharsInString(UTF8Tool.coDau2KoDau(name)));
            if (preStmt.executeUpdate() == 1) {
                 result = true;
            }
        } catch (SQLException e) {
            logger.error("insertRow: Error executing SQL " + strSQL + ">>>" + e.toString());
           // conn.rollback();
        } finally {
            
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }
    public boolean updateFree(BigDecimal id, int free) {
        if (id == null) {
            return false;
        }
       // if (free != 0) free = 1;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME  SET Free = ?,LAST_UPDATED=SYSDATE WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, free);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate() >= 1) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("updateTop: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }
    public boolean updateTop(BigDecimal id, int top) {
        if (id == null) {
            return false;
        }
       // if (free != 0) free = 1;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME  SET top = ?,LAST_UPDATED=SYSDATE WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, top);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate() >= 1) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("updateTop: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }
    public boolean updateStatus(BigDecimal id, int status) {
        if (id == null) {
            return false;
        }
       // if (free != 0) free = 1;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME  SET Status = ?,LAST_UPDATED=SYSDATE WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, status);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate() >= 1) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("updateStatus: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }
    public boolean checkQua(BigDecimal id) {
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int free=0;
        boolean result=false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT status " +
                "FROM GAME WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                free=rs.getInt(1);
            }
            if(free>=1){
            	result=true;
            }
        } catch (SQLException e) {
            logger.error("getRow: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }
    public boolean checkFree(BigDecimal id) {
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int free=0;
        boolean result=false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT free " +
                "FROM GAME WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                free=rs.getInt(1);
            }
            if(free>=1){
            	result=true;
            }
        } catch (SQLException e) {
            logger.error("getRow: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }
    public Collection findFreeGame(int page,int rowsPerPage) {
    	int startRow = (page-1) * rowsPerPage + 1;
        int stopRow  = page * rowsPerPage;
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Game game=null;
        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
            	 "SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER,CAT_NAME FROM " +
                 "(SELECT GAME.ID, GAME.NAME, INFO, MODEL, DOWNLOAD_COUNTER,GAME_CATEGORY.NAME AS CAT_NAME," +
                 " ROW_NUMBER() OVER(ORDER BY GAME.ID DESC) AS R FROM GAME JOIN GAME_CATEGORY ON GAME.CAT_ID=GAME_CATEGORY.ID" +
                 " WHERE FREE =1 ) " +
                 "WHERE R >=? AND R <=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, startRow);
            preStmt.setInt(2, stopRow);
            rs = preStmt.executeQuery();
            result = new Vector();
            while (rs.next()) {
            	game = new Game();
                game.setId(rs.getBigDecimal(1));
                game.setName(rs.getString(2));
                game.setInfo(rs.getString(3));
                game.setModel(rs.getString(4));
                game.setDownloadCounter(rs.getInt(5));
                game.setCatName(rs.getString(6));
                result.addElement(game);
            }
        } catch(SQLException e) {
            logger.error("findFreeGame: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }
    public Collection findTopGame(int page,int rowsPerPage) {
    	int startRow = (page-1) * rowsPerPage + 1;
        int stopRow  = page * rowsPerPage;
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Game game=null;
        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
            	 "SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER FROM " +
                 "(SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER, " +
                 "ROW_NUMBER() OVER(ORDER BY GEN_DATE DESC) AS R FROM GAME WHERE TOP =1 ORDER BY GEN_DATE DESC) " +
                 "WHERE R >=? AND R <=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, startRow);
            preStmt.setInt(2, stopRow);
            rs = preStmt.executeQuery();
            result = new Vector();
            while (rs.next()) {
            	game = new Game();
                game.setId(rs.getBigDecimal(1));
                game.setName(rs.getString(2));
                game.setInfo(rs.getString(3));
                game.setModel(rs.getString(4));
                game.setDownloadCounter(rs.getInt(5));
                result.addElement(game);
            }
        } catch(SQLException e) {
            logger.error("findFreeGame: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }
    public Collection findTopGame() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Game game=null;
        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
            	 "SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER FROM GAME WHERE TOP =1 ORDER BY GEN_DATE DESC" ;     
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            result = new Vector();
            while (rs.next()) {
            	game = new Game();
                game.setId(rs.getBigDecimal(1));
                game.setName(rs.getString(2));
                game.setInfo(rs.getString(3));
                game.setModel(rs.getString(4));
                game.setDownloadCounter(rs.getInt(5));
                result.addElement(game);
            }
        } catch(SQLException e) {
            logger.error("findTopGame: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }
    public Collection findGameByStatus(int status,int page,int rowsPerPage) {
    	int startRow = (page-1) * rowsPerPage + 1;
        int stopRow  = page * rowsPerPage;
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Game game=null;
        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
            	 "SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER FROM " +
                 "(SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER, ROW_NUMBER() OVER(ORDER BY ID DESC) AS R FROM GAME WHERE STATUS =? ORDER BY ID DESC) " +
                 "WHERE R >=? AND R <=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, status);
            preStmt.setInt(2, startRow);
            preStmt.setInt(3, stopRow);
            rs = preStmt.executeQuery();
            result = new Vector();
            while (rs.next()) {
            	game = new Game();
                game.setId(rs.getBigDecimal(1));
                game.setName(rs.getString(2));
                game.setInfo(rs.getString(3));
                game.setModel(rs.getString(4));
                game.setDownloadCounter(rs.getInt(5));
                result.addElement(game);
            }
        } catch(SQLException e) {
            logger.error("findFreeGame: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }
    public boolean deleteRow(BigDecimal id) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "delete from GAME where ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            if (preStmt.executeUpdate() > 0) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("deleteRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }

    public boolean updateRow(Game game) {
        if (game == null) return false;

        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME SET NAME = ?, INFO = ?, MODEL = ?, CAT_ID = ?, NAME_VN=?,LAST_UPDATED=SYSDATE " +
                "WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
           
            preStmt.setString(1, game.getName());
            preStmt.setString(2, game.getInfo());
            preStmt.setString(3, game.getModel());
            preStmt.setBigDecimal(4, game.getCatId());
            preStmt.setString(5,StringTool.removeSpecialCharsInString(UTF8Tool.coDau2KoDau(game.getName())));
            preStmt.setBigDecimal(6, game.getId());
           
            if (preStmt.executeUpdate() > 0) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("updateRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            poolX.releaseConnection(conn, preStmt);
            
        }return result;
    }


    public boolean updateCategoryId(BigDecimal id, BigDecimal catId) {
        if (id == null || catId == null) {
            return false;
        }

        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME SET CAT_ID = ?,LAST_UPDATED=SYSDATE WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, catId);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate() >= 1) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("updateCategoryId: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }


    public Game getRow(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Game game = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT GAME.ID, GAME.NAME, INFO, MODEL, DOWNLOAD_COUNTER, CAT_ID ,NAME_VN,GAME_CATEGORY.NAME "  +
                "FROM GAME LEFT JOIN GAME_CATEGORY ON GAME.CAT_ID=GAME_CATEGORY.ID WHERE GAME.ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                game = new Game();
                game.setId(rs.getBigDecimal(1));
                game.setName(rs.getString(2));
                game.setInfo(rs.getString(3));
                game.setModel(rs.getString(4));
                game.setDownloadCounter(rs.getInt(5));
                game.setCatId(rs.getBigDecimal(6));
                game.setNameVn(rs.getString(7));
                game.setCatName(rs.getString(8));
            }
        } catch (SQLException e) {
            logger.error("getRow: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return game;
    }


    public byte[] getGifFile(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        byte[] content = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT GIF_FILE FROM GAME WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("GIF_FILE"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (SQLException e) {
            logger.error("getGifFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch (IOException e) {
            logger.error("getGifFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return content;
    }
    
    public byte[] getWapFile(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        byte[] content = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT WAP_FILE FROM GAME WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("WAP_FILE"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (SQLException e) {
            logger.error("getWapFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch (IOException e) {
            logger.error("getWapFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return content;
    }

    public byte[] getJarFile(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        byte[] content = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT JAR_FILE FROM GAME WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("JAR_FILE"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (SQLException e) {
            logger.error("getJarFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch (IOException e) {
            logger.error("getJarFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return content;
    }

    public boolean updateGifFile(BigDecimal id, byte[] gif) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        boolean result=false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME SET GIF_FILE=?,LAST_UPDATED=SYSDATE WHERE ID=? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBinaryStream(1,new ByteArrayInputStream(gif), gif.length);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate()==1) {
               result=true;
            }
        } catch(SQLException e) {
            logger.error("updateGifFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
        }return result;
    }
    
    public boolean updateJarFile(BigDecimal id, byte[] jar) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        boolean result=false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME SET  JAR_FILE=?,LAST_UPDATED=SYSDATE WHERE ID=? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBinaryStream(1,new ByteArrayInputStream(jar), jar.length);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate()==1) {
               result=true;
            }
        } catch(SQLException e) {
            logger.error("updateGifFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
        }return result;
    }
    public boolean updateWapFile(BigDecimal id, byte[] wap) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        boolean result=false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME SET  WAP_FILE=?,LAST_UPDATED=SYSDATE WHERE ID=? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBinaryStream(1,new ByteArrayInputStream(wap), wap.length);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate()==1) {
               result=true;
            }
        } catch(SQLException e) {
            logger.error("updateWapFile: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
        }return result;
    }


    //Return a collection of Games
    public Collection findNewGame(int number) {

        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, INFO, MODEL FROM " +
                "(SELECT ID, NAME, INFO, MODEL FROM GAME ORDER BY ID DESC) " +
                "WHERE ROWNUM <= ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, number);
            rs = preStmt.executeQuery();
            result = new Vector();
            Game game = null;
            while (rs.next()) {
                game = new Game();
                game.setId(rs.getBigDecimal(1));
                game.setName(rs.getString(2));
                game.setInfo(rs.getString(3));
                game.setModel(rs.getString(4));
                result.addElement(game);
            }
        } catch(SQLException e) {
            logger.error("findNewGame: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }


    /**
     * @return a collection of IDs
     */
    public Collection findAllIds() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID FROM GAME ORDER BY ID ASC";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();

            result = new Vector();
            while (rs.next()) {
                result.addElement(rs.getBigDecimal(1));
            }
        } catch (SQLException e) {
            logger.error("findAllIds: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return result;
    }

    /**
     * @return a collection of IDs
     */
    public Collection findAllIds(int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID FROM GAME WHERE STATUS = ? ORDER BY ID";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, status);

            rs = preStmt.executeQuery();
            result = new Vector();
            while (rs.next()) {
                result.addElement(rs.getBigDecimal(1));
            }
        } catch (SQLException e) {
            logger.error("findAllIds: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return result;
    }

    /**
      * @return a collection of IDs
      */
     public Collection findByCatId(BigDecimal catId) {
         Connection conn = null;
         PreparedStatement preStmt = null;
         ResultSet rs = null;
         String strSQL = null;

         Vector result = null;
         try {
             conn = poolX.getConnection();
             strSQL =
                 "SELECT ID FROM GAME WHERE CAT_ID = ? ORDER BY ID";
             preStmt = conn.prepareStatement(strSQL);
             preStmt.setBigDecimal(1, catId);

             rs = preStmt.executeQuery();
             result = new Vector();
             while (rs.next()) {
                 result.addElement(rs.getBigDecimal(1));
             }
         } catch (SQLException e) {
             logger.error("findByCatId: Error executing SQL " + strSQL + ">>>" +e.toString());
         } finally {
             poolX.releaseConnection(conn, preStmt, rs);
             
         }return result;
     }

     /**
       * @param catId
       * @param page: = 1, 2, 3...
       * @param rowsPerPage = 1, 2, 3 ...
       * @return
       */
      public Collection findByPage(BigDecimal catId, int page, int rowsPerPage) {
          if (catId == null || page < 1 || rowsPerPage < 1) {
              return null;
          }
          int startRow = (page-1) * rowsPerPage + 1;
          int stopRow  = page * rowsPerPage;

          Connection conn = null;
          PreparedStatement preStmt = null;
          ResultSet rs = null;
          String strSQL = null;

          Vector result = null;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER,CAT_NAME FROM " +
                  " (SELECT GAME.ID, GAME.NAME, INFO, MODEL, DOWNLOAD_COUNTER,GAME_CATEGORY.NAME CAT_NAME,ROW_NUMBER() OVER(ORDER BY GAME.ID DESC) AS R " +
                  " FROM GAME LEFT JOIN GAME_CATEGORY ON GAME.CAT_ID= GAME_CATEGORY.ID WHERE CAT_ID = ? ORDER BY GAME.ID DESC) " +
                  "WHERE R >=? AND R <=?";
              preStmt = conn.prepareStatement(strSQL);
              preStmt.setBigDecimal(1, catId);
              preStmt.setInt(2, startRow);
              preStmt.setInt(3, stopRow);
              rs = preStmt.executeQuery();
              result = new Vector();
              Game game = null;
              while (rs.next()) {
                  game = new Game();
                  game.setId(rs.getBigDecimal(1));
                  game.setName(rs.getString(2));
                  game.setInfo(rs.getString(3));
                  game.setModel(rs.getString(4));
                  game.setDownloadCounter(rs.getInt(5));
                  game.setCatName(rs.getString(6));
                  result.addElement(game);
              }
          } catch(SQLException e) {
              logger.error("findByPage: Error executing SQL " + strSQL + ">>>" + e.toString());
          } finally {
              poolX.releaseConnection(conn, preStmt, rs);
              
          }return result;
      }

      public Collection findByPage(String name, int page, int rowsPerPage) {
          if (name == null || page < 1 || rowsPerPage < 1) {
              return null;
          }
          int startRow = (page-1) * rowsPerPage + 1;
          int stopRow  = page * rowsPerPage;

          Connection conn = null;
          PreparedStatement preStmt = null;
          ResultSet rs = null;
          String strSQL = null;

          Vector result = null;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER FROM " +
                  "(SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER, ROW_NUMBER() " +
                  "OVER(ORDER BY ID DESC) AS R FROM GAME " +
                  "WHERE UPPER(NAME_VN) LIKE '%" + name.toUpperCase() + "%' ORDER BY ID DESC) " +
                  "WHERE R >=? AND R <=?";
             
              preStmt = conn.prepareStatement(strSQL);
              preStmt.setInt(1, startRow);
              preStmt.setInt(2, stopRow);
              rs = preStmt.executeQuery();
              result = new Vector();
              Game game = null;
              while (rs.next()) {
                  game = new Game();
                  game.setId(rs.getBigDecimal(1));
                  game.setName(rs.getString(2));
                  game.setInfo(rs.getString(3));
                  game.setModel(rs.getString(4));
                  game.setDownloadCounter(rs.getInt(5));
                  result.addElement(game);
              }
          } catch(SQLException e) {
              logger.error("findByPage: Error executing SQL " + strSQL + ">>>" + e.toString());
          } finally {
              poolX.releaseConnection(conn, preStmt, rs);
              
          }return result;
      }

      public int getCount() {
          Connection conn = null;
          PreparedStatement preStmt = null;
          String strSQL = null;
          ResultSet rs = null;

          int count = 0;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "SELECT COUNT(*) FROM GAME";
              preStmt = conn.prepareStatement(strSQL);
              rs = preStmt.executeQuery();
              if (rs.next()) {
                  count = rs.getInt(1);
              }
          } catch (SQLException e) {
              logger.log("getCount: Error executing SQL " + strSQL + " >>> " + e.toString());
          } finally {
              poolX.releaseConnection(conn, preStmt, rs);
              
          }return count;
      }

      public int getCount(BigDecimal catId) {
          Connection conn = null;
          PreparedStatement preStmt = null;
          String strSQL = null;
          ResultSet rs = null;

          int count = 0;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "SELECT COUNT(*) FROM GAME WHERE CAT_ID = ?";
              preStmt = conn.prepareStatement(strSQL);
              preStmt.setBigDecimal(1, catId);
              rs = preStmt.executeQuery();
              if (rs.next()) {
                  count = rs.getInt(1);
              }
          } catch (SQLException e) {
              logger.log("getCount: Error executing SQL " + strSQL + " >>> " + e.toString());
          } finally {
              poolX.releaseConnection(conn, preStmt, rs);
              
          }return count;
      }
      public int getCount(String name) {
          Connection conn = null;
          PreparedStatement preStmt = null;
          String strSQL = null;
          ResultSet rs = null;

          int count = 0;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "SELECT COUNT(*) FROM GAME WHERE UPPER(NAME_VN) LIKE '%" + name.toUpperCase() + "%'";
              preStmt = conn.prepareStatement(strSQL);
              rs = preStmt.executeQuery();
              if (rs.next()) {
                  count = rs.getInt(1);
              }
          } catch (SQLException e) {
              logger.log("getCount: Error executing SQL " + strSQL + " >>> " + e.toString());
          } finally {
              poolX.releaseConnection(conn, preStmt, rs);
              
          }return count;
      }
      public int getCount(BigDecimal catId,String dongmay,String doimay,String ten) {
          Connection conn = null;
          PreparedStatement preStmt = null;
          String strSQL = null;
          ResultSet rs = null;
          
          dongmay = (dongmay != null) ? "%" + dongmay.toUpperCase() + "%" : "%";
          doimay = doimay != null ? "%" + doimay.toUpperCase() + "%" : "%";
          ten = ten != null ? "%" + ten.toUpperCase() + "%" : "%";
          
          int count = 0;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "SELECT COUNT(*) FROM GAME WHERE upper(model) like  ? AND upper(model) LIKE ? AND CAT_ID=? AND (upper(NAME) LIKE ? OR upper(NAME_VN) LIKE ?)";
              preStmt = conn.prepareStatement(strSQL);
              preStmt.setString(1,dongmay);
              preStmt.setString(2,doimay);
              preStmt.setBigDecimal(3,catId);
              preStmt.setString(4,ten);
              preStmt.setString(5, ten);
              rs = preStmt.executeQuery();
              if (rs.next()) {
                  count = rs.getInt(1);
              }
          } catch (SQLException e) {
              logger.log("getCount: Error executing SQL " + strSQL + " >>> " + e.toString());
          } finally {
              poolX.releaseConnection(conn, preStmt, rs);
              
          }return count;
      }
      
      public Collection findByPage(BigDecimal catId,String dongmay,String doimay,String ten, int page, int rowsPerPage) {
          if (page < 1 || rowsPerPage < 1) {
              return null;
          }
          int startRow = (page-1) * rowsPerPage + 1;
          int stopRow  = page * rowsPerPage;

          Connection conn = null;
          PreparedStatement preStmt = null;
          ResultSet rs = null;
          String strSQL = null;
          dongmay=dongmay!=null?"%"+dongmay.toUpperCase()+"%":"%";
          doimay=doimay!=null?"%"+doimay.toUpperCase()+"%":"%";
          ten=ten!=null?"%"+ten.toUpperCase()+"%":"%";
   	    
          Vector result = null;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER FROM " +
                  "(SELECT ID, NAME, INFO, MODEL, DOWNLOAD_COUNTER, ROW_NUMBER() " +
                  "OVER(ORDER BY ID DESC) AS R FROM GAME " +
                  "WHERE upper(model) like  ? AND upper(model) LIKE ? AND CAT_ID=? AND (upper(GAME.NAME) LIKE ? OR upper(NAME_VN) LIKE ?) ORDER BY ID DESC) " +
                  "WHERE R >=? AND R <=?";
             
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
              Game game = null;
              while (rs.next()) {
                  game = new Game();
                  game.setId(rs.getBigDecimal(1));
                  game.setName(rs.getString(2));
                  game.setInfo(rs.getString(3));
                  game.setModel(rs.getString(4));
                  game.setDownloadCounter(rs.getInt(5));
                  result.addElement(game);
              }
          } catch(SQLException e) {
              logger.error("findByPage: Error executing SQL " + strSQL + ">>>" + e.toString());
          } finally {
              poolX.releaseConnection(conn, preStmt, rs);
              
          }return result;
      }
      public boolean updateID(BigDecimal oldId, BigDecimal newId) {
          if (oldId == null || oldId == null) {
              return false;
          }

          Connection conn = null;
          PreparedStatement preStmt = null;
          String strSQL = null;

          boolean result = false;
          try {
              conn = poolX.getConnection();
              strSQL =
                  "UPDATE GAME SET ID = ?,LAST_UPDATED=SYSDATE WHERE ID = ?";
              preStmt = conn.prepareStatement(strSQL);
              preStmt.setBigDecimal(1, newId);
              preStmt.setBigDecimal(2, oldId);
              if (preStmt.executeUpdate() >= 1) {
                  result = true;
              }
          } catch(SQLException e) {
              logger.error("updateID: Error executing SQL " + strSQL + ">>>" + e.toString());
          } finally {
              poolX.releaseConnection(conn, preStmt);
              
          }return result;
      }

    public BigDecimal getMaxId() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        BigDecimal result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT MAX(ID) FROM GAME";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                result = rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            logger.error("getMaxId: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return result;
    }


    public boolean increaseDownloadCounter(BigDecimal id) {
        if (id == null || id == null) {
            return false;
        }

        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE GAME SET DOWNLOAD_COUNTER = DOWNLOAD_COUNTER+1 WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            if (preStmt.executeUpdate() >= 1) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("increaseDownloadCounter: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }

    public static void main(String[] args) {
       GameDAO dao=new GameDAO();
       Vector cIds=(Vector)dao.findAllIds();
       Game game=null;
       BigDecimal id=null;
       for(int i=0;i<cIds.size();i++){
    	   id=(BigDecimal)cIds.get(i);
    	   game=dao.getRow(id);
    	   dao.updateRow(game);
       }
       DBPoolX.releaseAll();
       
    }
}
