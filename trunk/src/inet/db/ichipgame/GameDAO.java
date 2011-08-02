/*
CREATE TABLE ICHIP_GAME (
 	SEQ NUMBER(10) NOT NULL PRIMARY KEY,
 	ID NUMBER(10) UNIQUE,
 	NAME VARCHAR2(30),
 	DESCRIPTION VARCHAR2(1000),
 	MODEL1 VARCHAR2(170),
 	MODEL2 VARCHAR2(170),
 	MODEL3 VARCHAR2(170),
 	IMAGE BLOB DEFAULT EMPTY_BLOB(),
 	CAT_ID NUMBER(10),
 	STATUS NUMBER(1) DEFAULT 1,
 	LAST_UPDATED DATE DEFAULT SYSDATE)
 	CREATE SEQUENCE ICHIP_GAME_SEQ INCREMENT BY 1 START WITH 1 ORDER NOCYCLE;
 */
package inet.db.ichipgame;
import inet.db.pool.DBPoolX;
import inet.db.pool.DBPoolXName;
import inet.util.LOBs;
import inet.util.Logger;
import inet.util.StringTool;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

public class GameDAO {
	private Logger logger;
    private DBPoolX poolX=null;
    public GameDAO() {
        logger = new Logger(this.getClass().getName());
        try {
        	poolX=DBPoolX.getInstance(DBPoolXName.GALAFUN);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public boolean insertRow(BigDecimal id, String name, String desc,
			String model1, String model2, String model3, byte[] image,
			 BigDecimal catId) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		String strSQL = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			conn = poolX.getConnection();
			conn.setAutoCommit(false);
			// 1 - Get next Seq#
			Vector vSeqNum = new Vector();
			strSQL = "SELECT seq FROM ICHIP_GAME order by seq asc";
			preStmt = conn.prepareStatement(strSQL);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				vSeqNum.addElement(rs.getString(1));
			}
			int seqNum = 0;
			int i = 1;
			while (i < 500000) {
				if (!vSeqNum.contains("" + i)) {
					seqNum = i;
					break;
				}
				i++;
			}
			preStmt.close();	
			Blob blob=LOBs.createBlob(conn, image);  
			strSQL = "INSERT INTO ICHIP_GAME (SEQ,ID,NAME,DESCRIPTION,MODEL1,MODEL2,MODEL3,IMAGE,CAT_ID,LAST_UPDATED) "
					+ "VALUES (?,?, ?,?,?,?,?,?,?,SYSDATE)";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setBigDecimal(1, new BigDecimal(seqNum+""));
			preStmt.setBigDecimal(2, id);
			preStmt.setString(3, name);
			preStmt.setString(4, desc);
			preStmt.setString(5, model1);
			preStmt.setString(6, model2);
			preStmt.setString(7, model3);
			preStmt.setBlob(8, blob);
			preStmt.setBigDecimal(9,catId);
			if (preStmt.executeUpdate() == 1) {
				result=true;
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("insertRow: Error executing SQL " + strSQL + ">>>"
					+ e.toString());
			try {
				conn.rollback();

			} catch (Exception ie) {
				ie.printStackTrace();
			}
		} catch (IOException e) {
			try {
				logger.error("insertRow: " + e.toString());
				conn.rollback();
			} catch (Exception ex) {

			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException ex) {
			}
			poolX.releaseConnection(conn, preStmt, rs);
		}
		return result;
	}
    public boolean insertRow(BigDecimal id, String name, String desc,
			String model1, String model2, String model3, byte[] image,byte[] wapImage,
			 BigDecimal catId) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		String strSQL = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			conn = poolX.getConnection();
			conn.setAutoCommit(false);
			// 1 - Get next Seq#
			Vector vSeqNum = new Vector();
			strSQL = "SELECT seq FROM ICHIP_GAME order by seq asc";
			preStmt = conn.prepareStatement(strSQL);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				vSeqNum.addElement(rs.getString(1));
			}
			int seqNum = 0;
			int i = 1;
			while (i < 500000) {
				if (!vSeqNum.contains("" + i)) {
					seqNum = i;
					break;
				}
				i++;
			}
			preStmt.close();	 
			strSQL = "INSERT INTO ICHIP_GAME (SEQ,ID,NAME,DESCRIPTION,MODEL1,MODEL2,MODEL3,IMAGE,WAP_IMAGE,CAT_ID,LAST_UPDATED) "
					+ "VALUES (?,?, ?,?,?,?,?,?,?,?,SYSDATE)";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setBigDecimal(1, new BigDecimal(seqNum+""));
			preStmt.setBigDecimal(2, id);
			preStmt.setString(3, name);
			preStmt.setString(4, desc);
			preStmt.setString(5, model1);
			preStmt.setString(6, model2);
			preStmt.setString(7, model3);
			preStmt.setBlob(8, LOBs.createBlob(conn, image));
			preStmt.setBlob(9, LOBs.createBlob(conn, wapImage));
			preStmt.setBigDecimal(10,catId);
			if (preStmt.executeUpdate() == 1) {
				result=true;
				conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("insertRow: Error executing SQL " + strSQL + ">>>"
					+ e.toString());
			try {
				conn.rollback();

			} catch (Exception ie) {
				ie.printStackTrace();
			}
		} catch (IOException e) {
			try {
				logger.error("insertRow: " + e.toString());
				conn.rollback();
			} catch (Exception ex) {

			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException ex) {
			}
			poolX.releaseConnection(conn, preStmt, rs);
		}
		return result;
	}
    public boolean updateRow(BigDecimal id, String name, String desc,
			String model1, String model2, String model3, byte[] image,byte[] wapImage, BigDecimal catId) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		String strSQL = null;
		ResultSet rs = null;
		boolean result = false;
		try {
			conn = poolX.getConnection();
			if(image!=null&&image.length>0&&wapImage!=null&&wapImage.length>0){
				strSQL = "UPDATE ICHIP_GAME SET CAT_ID = ?,name=?,description=?,model1=?,model2=?,model3=?,last_updated=sysdate,image=?,wap_image=? WHERE ID = ?";
				preStmt = conn.prepareStatement(strSQL);
				preStmt.setBigDecimal(1, catId);
				preStmt.setString(2, name);
				preStmt.setString(3, desc);
				preStmt.setString(4, model1);
				preStmt.setString(5, model2);
				preStmt.setString(6, model3);
				preStmt.setBlob(7,LOBs.createBlob(conn, image));
				preStmt.setBlob(8,LOBs.createBlob(conn, wapImage));
				preStmt.setBigDecimal(9, id);
			}else if(image!=null&&image.length>0){
				strSQL = "UPDATE ICHIP_GAME SET CAT_ID = ?,name=?,description=?,model1=?,model2=?,model3=?,last_updated=sysdate,image=? WHERE ID = ?";
				preStmt = conn.prepareStatement(strSQL);
				preStmt.setBigDecimal(1, catId);
				preStmt.setString(2, name);
				preStmt.setString(3, desc);
				preStmt.setString(4, model1);
				preStmt.setString(5, model2);
				preStmt.setString(6, model3);
				preStmt.setBlob(7,LOBs.createBlob(conn, wapImage));
				preStmt.setBigDecimal(8, id);
			}else if(wapImage!=null&&wapImage.length>0){
				strSQL = "UPDATE ICHIP_GAME SET CAT_ID = ?,name=?,description=?,model1=?,model2=?,model3=?,last_updated=sysdate,wap_image=? WHERE ID = ?";
				preStmt = conn.prepareStatement(strSQL);
				preStmt.setBigDecimal(1, catId);
				preStmt.setString(2, name);
				preStmt.setString(3, desc);
				preStmt.setString(4, model1);
				preStmt.setString(5, model2);
				preStmt.setString(6, model3);
				preStmt.setBlob(7,LOBs.createBlob(conn, wapImage));
				preStmt.setBigDecimal(8, id);
			}else{
				strSQL = "UPDATE ICHIP_GAME SET CAT_ID = ?,name=?,description=?,model1=?,model2=?,model3=?,last_updated=sysdate WHERE ID = ?";
				preStmt = conn.prepareStatement(strSQL);
				preStmt.setBigDecimal(1, catId);
				preStmt.setString(2, name);
				preStmt.setString(3, desc);
				preStmt.setString(4, model1);
				preStmt.setString(5, model2);
				preStmt.setString(6, model3);
				preStmt.setBigDecimal(7, id);
			}
			if (preStmt.executeUpdate() >= 1) {
				result=true;
			}else{
				result=false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("updateRow: Error executing SQL " + strSQL + ">>>"
					+ e.toString());
		}finally {
			poolX.releaseConnection(conn, preStmt, rs);
		}
		return result;
	}

    public byte[] getContentGif(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        byte[] content = null;
        try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            strSQL =
                "SELECT image FROM ICHIP_GAME WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("image"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (SQLException e) {
            logger.error("getContentGif: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch (IOException e) {
            logger.error("getContentGif: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
            }
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return content;
    }
    public byte[] getContentWap(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        byte[] content = null;
        try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            strSQL =
                "SELECT wap_image FROM ICHIP_GAME WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("wap_image"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (SQLException e) {
            logger.error("getContentWap: Error executing SQL " + strSQL + ">>>" + e.toString());
        } catch (IOException e) {
            logger.error("getContentWap: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
            }
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return content;
    }
    public boolean updateStatus(BigDecimal id,int status) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "update ICHIP_game set status=? where id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1,status);
            preStmt.setBigDecimal(2, id);
            if (preStmt.executeUpdate() > 0) {
                result = true;
            }
        } catch(SQLException e) {
            logger.error("updateStatus: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt);
            
        }return result;
    }
    public boolean deleteRow(BigDecimal id) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "delete from ICHIP_GAME where id = ?";
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
    public Game getGameById(BigDecimal id){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Game game = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT name,description,cat_id,image,model1,model2,model3,seq,status " +
                "FROM ICHIP_GAME WHERE id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                game = new Game();
                game.setId(id);
                game.setName(rs.getString(1));
                game.setDesc(rs.getString(2));
                game.setCatId(rs.getBigDecimal(3));
                try{
                	Blob myBlob = rs.getBlob(4); //GET BLOB LOCATOR
                	game.setImage(LOBs.readByteBlob(myBlob));
                }catch(Exception e){
                	e.printStackTrace();
                }
                game.setModel1(rs.getString(5));
                game.setModel2(rs.getString(6));
                game.setModel3(rs.getString(7));
                game.setSeq(rs.getInt(8));
                game.setStatus(rs.getInt(9));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return game;
    	
    }
    public int  getSeqGameByName(String name){
    	if(name==null||name.equals(""))return 0;
    	name=StringTool.removeSpecialCharsInString(name.toUpperCase());
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int seq=0;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT seq " +
                "FROM ICHIP_GAME WHERE UPPER(REPLACE(name,' ','')) like ? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,name );
            rs = preStmt.executeQuery();
            if (rs.next()) {
                seq=rs.getInt(1);
            }else{
            	preStmt.close();
            	rs.close();
            	preStmt = conn.prepareStatement("SELECT seq FROM ICHIP_GAME WHERE UPPER(REPLACE(name,' ','')) like ? ");
                preStmt.setString(1, "%"+name+"%");
                rs = preStmt.executeQuery();
                if (rs.next()) {
                    seq=rs.getInt(1);
                }
            }     
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return seq;
    	
    }
    public Game getGameBySeq(int seq){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Game game = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT id,name,description,cat_id,image,model1,model2,model3,STATUS " +
                "FROM ICHIP_GAME WHERE seq = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, seq);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                game = new Game();
                game.setId(rs.getBigDecimal(1));
                game.setName(rs.getString(2));
                game.setDesc(rs.getString(3));
                game.setCatId(rs.getBigDecimal(4));
                try{
                	Blob myBlob = rs.getBlob(5); //GET BLOB LOCATOR
                	game.setImage(LOBs.readByteBlob(myBlob));
                }catch(Exception e){
                	e.printStackTrace();
                }
                game.setModel1(rs.getString(6));
                game.setModel2(rs.getString(7));
                game.setModel3(rs.getString(8));
                game.setStatus(rs.getInt(9));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return game;
    	
    }
    public BigDecimal getIChipId(int seq){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        BigDecimal ichipId = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT id " +
                "FROM ICHIP_GAME WHERE seq = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, seq);
            rs = preStmt.executeQuery();
            if (rs.next()) {
               ichipId=rs.getBigDecimal(1);   
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return ichipId;
    	
    }
    
    public int getSeqById(BigDecimal id){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int seq = 0;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT SEQ " +
                "FROM ICHIP_GAME WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
               seq=rs.getInt(1);   
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return seq;
    	
    }
    
    public Collection findAllIds() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID FROM ICHIP_GAME ORDER BY ID";
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
           
        } return result;
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
                 "SELECT ID FROM ICHIP_GAME WHERE CAT_ID = ? ORDER BY ID";
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
            
         } return result;
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
 		int startRow = (page - 1) * rowsPerPage + 1;
 		int stopRow = page * rowsPerPage;
 		Connection conn = null;
 		PreparedStatement preStmt = null;
 		ResultSet rs = null;
 		String strSQL = null;
 		Game game=null;
 		Vector result = null;
 		try {
 			conn = poolX.getConnection();
 			strSQL =

 			"SELECT ID,NAME,DESCRIPTION,CAT_ID,MODEL1,MODEL2,MODEL3,STATUS,SEQ FROM " +
 				"(SELECT ID,NAME,DESCRIPTION,CAT_ID,MODEL1,MODEL2,MODEL3,STATUS,SEQ," +
 				"ROW_NUMBER() OVER(ORDER BY LAST_UPDATED DESC) AS R FROM ICHIP_GAME WHERE  CAT_ID = ? AND STATUS=1)" +
 				" WHERE R>=? AND R<=?  ";
 			preStmt = conn.prepareStatement(strSQL);
 			preStmt.setBigDecimal(1, catId);
 			preStmt.setInt(2,startRow);
 			preStmt.setInt(3,stopRow);
 			rs = preStmt.executeQuery();
 			result = new Vector();
 			while (rs.next()) {
 				game=new Game();
 				game.setId(rs.getBigDecimal(1));
 				game.setName(rs.getString(2));
 				game.setDesc(rs.getString(3));
 				game.setCatId(rs.getBigDecimal(4));
 				game.setModel1(rs.getString(5));
 				game.setModel2(rs.getString(6));
 				game.setModel3(rs.getString(7));
 				game.setStatus(rs.getInt(8));
 				game.setSeq(rs.getInt(9));
 				result.addElement(game);
 			}
 		} catch (SQLException e) {
 			logger.error("findByPage: Error executing SQL " + strSQL + ">>>"
 					+ e.toString());
 		} finally {
 			poolX.releaseConnection(conn, preStmt, rs);

 		}
 		return result;
 	}
     public Collection findSeqByPage(BigDecimal catId, int page, int rowsPerPage) {
  		if (catId == null || page < 1 || rowsPerPage < 1) {
  			return null;
  		}
  		int startRow = (page - 1) * rowsPerPage + 1;
  		int stopRow = page * rowsPerPage;
  		Connection conn = null;
  		PreparedStatement preStmt = null;
  		ResultSet rs = null;
  		String strSQL = null;

  		Vector result = null;
  		try {
  			conn = poolX.getConnection();
  			strSQL =

  			"SELECT SEQ FROM " +
  			"(SELECT SEQ,ROW_NUMBER() OVER(ORDER BY LAST_UPDATED DESC) AS R FROM ICHIP_GAME WHERE  CAT_ID = ? AND STATUS=1)" +
  			" WHERE R>=? AND R<=?  ";
  			preStmt = conn.prepareStatement(strSQL);
  			preStmt.setBigDecimal(1, catId);
  			preStmt.setInt(2,startRow);
  			preStmt.setInt(3,stopRow);
  			rs = preStmt.executeQuery();
  			result = new Vector();
  			BigDecimal id=null;
  			while (rs.next()) {
  				id = rs.getBigDecimal(1);
  				result.addElement(id);
  			}
  		} catch (SQLException e) {
  			logger.error("findByPage: Error executing SQL " + strSQL + ">>>"
  					+ e.toString());
  		} finally {
  			poolX.releaseConnection(conn, preStmt, rs);

  		}
  		return result;
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
                  "SELECT COUNT(*) FROM ICHIP_GAME";
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
        int count =0;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM ICHIP_GAME WHERE  CAT_ID = ? ";
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

    public BigDecimal getMaxId() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        BigDecimal result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT MAX(ID) FROM ICHIP_GAME";
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
                "UPDATE ICHIP_GAME SET CAT_ID = ? WHERE id = ?";
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
}
