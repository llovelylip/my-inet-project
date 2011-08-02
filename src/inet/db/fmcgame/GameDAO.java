package inet.db.fmcgame;
import inet.db.pool.DBPoolX;
import inet.db.pool.DBPoolXName;
import inet.util.LOBs;
import inet.util.Logger;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

public class GameDAO  {
    private Logger logger;
    private DBPoolX poolX=null;
    public GameDAO() {
        logger = new Logger(this.getClass().getName());
        try {
			poolX=DBPoolX.getInstance(DBPoolXName.AAA);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public boolean insertRow(BigDecimal fmcId,String name ,String desc,String model, byte[] image,byte[] wapImage,BigDecimal catId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL  = null;
        ResultSet rs   = null;
        boolean result = false;
        try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            //1 - Get next Seq#
                 Vector vSeqNum = new Vector();
	            strSQL = "SELECT ID FROM FMC_Game order by id asc";
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
            	"INSERT INTO FMC_GAME (ID,FMC_ID,name,description,model,image,wap_image,cat_id,last_updated) " +
                "VALUES (?,?, ?,?,?,?,?,?,sysdate)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1,new BigDecimal(seqNum));
            preStmt.setBigDecimal(2,fmcId);
            preStmt.setString(3,name);
            preStmt.setString(4,desc);
            oracle.sql.CLOB newClob = oracle.sql.CLOB.createTemporary(conn,
					false, oracle.sql.CLOB.DURATION_SESSION);
			newClob.setString(1, model.replaceAll("\n", "<br/>"));
			preStmt.setClob(5, newClob);
            preStmt.setBlob(6, LOBs.createBlob(conn, image));
            preStmt.setBlob(7, LOBs.createBlob(conn, wapImage));
            preStmt.setBigDecimal(8,catId);         
            if (preStmt.executeUpdate() == 1) {
                conn.commit();
                result=true;
            } else {
                conn.rollback();
                result=false;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("insertRow: Error executing SQL " + strSQL + ">>>" + e.toString());
            try{
            	conn.rollback();
            }catch(Exception ie){
            	ie.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
            }
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return result;
    }
    
    public boolean updateRow(BigDecimal id ,String name,String desc,String model,byte[] image,byte[] wapImage, 
    		BigDecimal catId) {
        Connection conn = null;

        PreparedStatement preStmt = null;
        String strSQL  = null;
        ResultSet rs   = null;
        boolean result = false;
        try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            strSQL =
                "UPDATE fmc_game SET CAT_ID = ?,name=?,description=?,model=?,last_updated=sysdate WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, catId);
            preStmt.setString(2,name);
            preStmt.setString(3,desc);
            oracle.sql.CLOB newClob = oracle.sql.CLOB.createTemporary(conn,
					false, oracle.sql.CLOB.DURATION_SESSION);
			newClob.setString(1, model.replaceAll("\n", "<br/>"));
			preStmt.setClob(4, newClob);
            preStmt.setBigDecimal(5,id);
            if (preStmt.executeUpdate() >= 1) {
                 if(image!=null&image.length>0){
                	 System.out.println("Update hinh anh");
                	 updateImage(id,image,conn);
                 }
                 if(wapImage!=null&wapImage.length>0){
                	 System.out.println("Update hinh anh");
                	 updateWapImage(id,wapImage,conn);
                 }
                 result=true;
                 try{
                     conn.commit();
                 }catch(Exception e){
                	 e.printStackTrace();
                 }
            }else{     	
                conn.rollback();
            }                              
         
         }catch (Exception e) {
        	e.printStackTrace();
            System.out.println("UpdateRow: Error executing SQL " + strSQL + ">>>" + e.toString());
           try{
            conn.rollback();
           }catch(Exception ex){
           	ex.printStackTrace();
           }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
            }
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return result;
    }
    public boolean updateImage(BigDecimal id,byte[] image,Connection conn) {
        PreparedStatement preStmt = null;
        String strSQL  = null;
        boolean result = false;
        try {
            strSQL =
                    "UPDATE fmc_Game SET image=?,LAST_UPDATED=SYSDATE WHERE ID=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBlob(1, LOBs.createBlob(conn, image));
            preStmt.setBigDecimal(2,id);
            if(preStmt.executeUpdate()>=1){
            	result=true;
            }
           // preStmt.close();
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (IOException e) {
        	System.out.print("updateOta: " + e.toString());
        } finally {
        	try {
        		preStmt.close();   
			} catch (Exception e) {
				// TODO: handle exception
			} 
        }return result;
    }
    public boolean updateCounter(BigDecimal id) {
        PreparedStatement preStmt = null;
        Connection conn = null;
        String strSQL  = null;
        boolean result = false;
        try {
        	conn = poolX.getConnection();
            strSQL =
                    "UPDATE fmc_Game SET download_counter=download_counter+1 WHERE ID=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1,id);
            if(preStmt.executeUpdate()>=1){
            	result=true;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt);
        }return result;
    }
    public boolean updateTop(int top,BigDecimal id) {
        PreparedStatement preStmt = null;
        Connection conn = null;
        String strSQL  = null;
        boolean result = false;
        try {
        	conn = poolX.getConnection();
            strSQL =
                    "UPDATE fmc_Game SET top=?,LAST_UPDATED=SYSDATE WHERE ID=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1,top);
            preStmt.setBigDecimal(2,id);
            if(preStmt.executeUpdate()>=1){
            	result=true;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt);
        }return result;
    }
    public boolean updateCategory(BigDecimal id,BigDecimal newCatId) {
        PreparedStatement preStmt = null;
        Connection conn = null;
        String strSQL  = null;
        boolean result = false;
        try {
        	conn = poolX.getConnection();
            strSQL =
                    "UPDATE fmc_Game SET CAT_ID=?,LAST_UPDATED=SYSDATE WHERE ID=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1,newCatId);
            preStmt.setBigDecimal(2,id);
            if(preStmt.executeUpdate()>=1){
            	result=true;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt);
        }return result;
    }
    public boolean updateWapImage(BigDecimal id,byte[] image,Connection conn) {
        PreparedStatement preStmt = null;
        String strSQL  = null;
        boolean result = false;
        try {
            strSQL =
                    "UPDATE fmc_Game SET wap_image=?,LAST_UPDATED=SYSDATE WHERE ID=?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBlob(1, LOBs.createBlob(conn, image));
            preStmt.setBigDecimal(2,id);
            if(preStmt.executeUpdate()>=1){
            	result=true;
            }
           // preStmt.close();
        } catch (SQLException e) {
           e.printStackTrace();
        } catch (IOException e) {
        	System.out.print("updateWapImage: " + e.toString());
        } finally {
        	try {
        		preStmt.close();   
			} catch (Exception e) {
				// TODO: handle exception
			} 
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
            conn.setAutoCommit(false);
            strSQL =
                "SELECT image FROM fmc_Game WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("image"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (Exception e) {
            logger.error("getImage: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
            }
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
            conn.setAutoCommit(false);
            strSQL =
                "SELECT wap_image FROM fmc_Game WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                Blob myBlob = rs.getBlob("wap_image"); //GET BLOB LOCATOR
                content = LOBs.readByteBlob(myBlob);
            }
        } catch (Exception e) {
            logger.error("getWapImage: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
            }
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
                "delete from fmc_game where id = ?";
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
                "SELECT FMC_GAME.name,description,cat_id,image,model,FMC_ID,download_counter,FMC_GAME_CATEGORY.NAME " +
                "FROM FMC_game LEFT JOIN FMC_GAME_CATEGORY ON FMC_GAME_CATEGORY.ID=FMC_GAME.CAT_ID  WHERE FMC_GAME.id = ?";
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
                game.setModel(getString(rs.getClob(5)));
               
                game.setFmcId(rs.getBigDecimal(6));
                game.setDownloadCounter(rs.getInt(7));
                game.setCatName(rs.getString(8));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return game;
    	
    }
    public Game getGameByFmcId(BigDecimal fmcId){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        Game game = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT id,name,description,cat_id,image,model " +
                "FROM fmc_game WHERE fmc_id = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, fmcId);
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
                game.setModel(getString(rs.getClob(6)));
                game.setFmcId(fmcId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	poolX.releaseConnection(conn, preStmt, rs);
           
        } return game;
    	
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
                "SELECT ID FROM fmc_game ORDER BY ID";
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
                 "SELECT ID FROM fmc_game WHERE CAT_ID = ? ORDER BY ID";
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

 			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter FROM " +
 			"(SELECT ID,name,description,cat_id,model,FMC_ID,download_counter," +
 			"ROW_NUMBER() OVER(ORDER BY LAST_UPDATED DESC) AS R FROM fmc_game WHERE CAT_ID = ? AND STATUS=1)" +
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
 				game.setModel(getString(rs.getClob(5)));
 				game.setFmcId(rs.getBigDecimal(6));
 				game.setDownloadCounter(rs.getInt(7));
 				result.addElement(game);
 			}
 		} catch (SQLException e) {
 			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
 					+ e.toString());
 		} finally {
 			poolX.releaseConnection(conn, preStmt, rs);

 		}
 		return result;
 	}
     public Collection findHot( int number) {
  		Connection conn = null;
  		PreparedStatement preStmt = null;
  		ResultSet rs = null;
  		String strSQL = null;
  		Game game=null;
  		Vector result = null;
  		try {
  			conn = poolX.getConnection();
  			strSQL =

  			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter FROM " +
  			"(SELECT ID,name,description,cat_id,model,FMC_ID,download_counter," +
  			"ROW_NUMBER() OVER(ORDER BY DOWNLOAD_COUNTER DESC) AS R FROM fmc_game WHERE  STATUS=1)" +
  			" WHERE  R<=?  ";
  			preStmt = conn.prepareStatement(strSQL);
  			preStmt.setInt(1,number);
  			rs = preStmt.executeQuery();
  			result = new Vector();
  			while (rs.next()) {
  				game=new Game();
  				game.setId(rs.getBigDecimal(1));
  				game.setName(rs.getString(2));
  				game.setDesc(rs.getString(3));
  				game.setCatId(rs.getBigDecimal(4));
  				game.setModel(getString(rs.getClob(5)));
  				game.setFmcId(rs.getBigDecimal(6));
  				game.setDownloadCounter(rs.getInt(7));
  				result.addElement(game);
  			}
  		} catch (SQLException e) {
  			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
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
    		Game game=null;
    		Vector result = null;
    		try {
    			conn = poolX.getConnection();
    			strSQL =

    			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter,CAT_NAME FROM " +
    			"(SELECT FMC_GAME.ID,FMC_GAME.name,description,cat_id,model,FMC_ID,download_counter,FMC_GAME_CATEGORY.NAME CAT_NAME, " +
    			" ROW_NUMBER() OVER(ORDER BY FMC_GAME.LAST_UPDATED DESC) AS R " +
    			" FROM fmc_game LEFT JOIN FMC_GAME_CATEGORY ON FMC_GAME.CAT_ID=FMC_GAME_CATEGORY.ID )" +
    			" WHERE  R<=? ";
    			preStmt = conn.prepareStatement(strSQL);
    			//System.out.println("findNewest:"+strSQL);
    			preStmt.setInt(1,number);
    			rs = preStmt.executeQuery();
    			result = new Vector();
    			while (rs.next()) {
    				game=new Game();
    				game.setId(rs.getBigDecimal(1));
    				game.setName(rs.getString(2));
    				//System.out.println("name="+game.getName());
    				game.setDesc(rs.getString(3));
    				game.setCatId(rs.getBigDecimal(4));
    				game.setModel(getString(rs.getClob(5)));
    				game.setFmcId(rs.getBigDecimal(6));
    				game.setDownloadCounter(rs.getInt(7));
    				game.setCatName(rs.getString(8));
    				result.addElement(game);
    			}
    		} catch (SQLException e) {
    			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
    					+ e.toString());
    		} finally {
    			poolX.releaseConnection(conn, preStmt, rs);

    		}
    		return result;
    	}
     public Collection findTop( int number) {
   		Connection conn = null;
   		PreparedStatement preStmt = null;
   		ResultSet rs = null;
   		String strSQL = null;
   		Game game=null;
   		Vector result = null;
   		try {
   			conn = poolX.getConnection();
   			strSQL =

   			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter,CAT_NAME FROM " +
   			"(SELECT FMC_GAME.ID,FMC_GAME.name,description,cat_id,model,FMC_ID,download_counter,FMC_GAME_CATEGORY.NAME CAT_NAME, " +
   			" ROW_NUMBER() OVER(ORDER BY FMC_GAME.LAST_UPDATED DESC) AS R " +
   			" FROM fmc_game LEFT JOIN FMC_GAME_CATEGORY ON FMC_GAME.CAT_ID=FMC_GAME_CATEGORY.ID WHERE  TOP>=1)" +
   			" WHERE  R<=?  ";
   			preStmt = conn.prepareStatement(strSQL);
   			preStmt.setInt(1,number);
   			rs = preStmt.executeQuery();
   			result = new Vector();
   			//System.out.println("findTop:"+strSQL);
   			while (rs.next()) {
   				game=new Game();
   				game.setId(rs.getBigDecimal(1));
   				game.setName(rs.getString(2));
   				game.setDesc(rs.getString(3));
   				game.setCatId(rs.getBigDecimal(4));
   				game.setModel(getString(rs.getClob(5)));
   				game.setFmcId(rs.getBigDecimal(6));
   				game.setDownloadCounter(rs.getInt(7));
   				game.setCatName(rs.getString(8));
   				result.addElement(game);
   			}
   		} catch (SQLException e) {
   			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
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
                  "SELECT COUNT(*) FROM fmc_game";
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

     
      public BigDecimal getCount(BigDecimal catId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;

        BigDecimal count =null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM FMC_game WHERE  CAT_ID = ? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, catId); 
            rs = preStmt.executeQuery(); 
            if (rs.next()) {
                count = rs.getBigDecimal(1);
            }
            //System.out.println("counter="+count);
        } catch (SQLException e) {
            System.out.println("getCount: Error executing SQL " + strSQL + " >>> " + e.toString());
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
                "SELECT MAX(ID) FROM fmc_game";
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
                "UPDATE fmc_game SET CAT_ID = ? WHERE id = ?";
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
    public Collection findAllTop() {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		Game game=null;
		Vector result = null;
		try {
			conn = poolX.getConnection();
			strSQL =
			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter,top FROM fmc_game WHERE  TOP>=1 ORDER BY LAST_UPDATED " ;
			preStmt = conn.prepareStatement(strSQL);
			rs = preStmt.executeQuery();
			result = new Vector();
			while (rs.next()) {
				game=new Game();
				game.setId(rs.getBigDecimal(1));
				game.setName(rs.getString(2));
				game.setDesc(rs.getString(3));
				game.setCatId(rs.getBigDecimal(4));
				game.setModel(getString(rs.getClob(5)));
				game.setFmcId(rs.getBigDecimal(6));
				game.setDownloadCounter(rs.getInt(7));
				game.setTop(rs.getInt(8));
				result.addElement(game);
			}
		} catch (SQLException e) {
			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
					+ e.toString());
		} finally {
			poolX.releaseConnection(conn, preStmt, rs);

		}
		return result;
	}
    public Game findHome() {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		Game game=null;
		try {
			conn = poolX.getConnection();
			strSQL =
			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter,top FROM fmc_game WHERE  TOP=3" ;
			preStmt = conn.prepareStatement(strSQL);
			rs = preStmt.executeQuery();
			if (rs.next()) {
				game=new Game();
				game.setId(rs.getBigDecimal(1));
				game.setName(rs.getString(2));
				game.setDesc(rs.getString(3));
				game.setCatId(rs.getBigDecimal(4));
				game.setModel(getString(rs.getClob(5)));
				game.setFmcId(rs.getBigDecimal(6));
				game.setDownloadCounter(rs.getInt(7));
				game.setTop(rs.getInt(8));
			}else{
				preStmt.close();
				rs.close();
				strSQL ="SELECT * FROM "+
        		"(SELECT ID,name,description,cat_id,model,FMC_ID,download_counter,top FROM fmc_game WHERE TOP>=1 ORDER BY dbms_random.value)"+	
        		"WHERE rownum = 1";
				preStmt = conn.prepareStatement(strSQL);
				rs = preStmt.executeQuery();
				if (rs.next()) {
					game=new Game();
					game.setId(rs.getBigDecimal(1));
					game.setName(rs.getString(2));
					game.setDesc(rs.getString(3));
					game.setCatId(rs.getBigDecimal(4));
					game.setModel(getString(rs.getClob(5)));
					game.setFmcId(rs.getBigDecimal(6));
					game.setDownloadCounter(rs.getInt(7));
					game.setTop(rs.getInt(8));
				}	
			}
		} catch (SQLException e) {
			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
					+ e.toString());
		} finally {
			poolX.releaseConnection(conn, preStmt, rs);

		}
		return game;
	}
    public Game findDefaultHome() {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		Game game=null;
		try {
			conn = poolX.getConnection();
			strSQL =
			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter,top FROM fmc_game WHERE  TOP=3" ;
			preStmt = conn.prepareStatement(strSQL);
			rs = preStmt.executeQuery();
			while (rs.next()) {
				game=new Game();
				game.setId(rs.getBigDecimal(1));
				game.setName(rs.getString(2));
				game.setDesc(rs.getString(3));
				game.setCatId(rs.getBigDecimal(4));
				game.setModel(getString(rs.getClob(5)));
				game.setFmcId(rs.getBigDecimal(6));
				game.setDownloadCounter(rs.getInt(7));
				game.setTop(rs.getInt(8));
			}
		} catch (SQLException e) {
			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
					+ e.toString());
		} finally {
			poolX.releaseConnection(conn, preStmt, rs);

		}
		return game;
	}
    public static String getString(Clob clobData) {

		String content = "";
		try {

			Reader reader = clobData.getCharacterStream();
			CharArrayWriter writer = new CharArrayWriter();
			int i = -1;
			while ((i = reader.read()) != -1) {
				writer.write(i);
			}
			content = new String(writer.toCharArray());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return content;
	}
    public BigDecimal getMaxTdId() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        BigDecimal result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "select txid_game_seq.nextval from dual";
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
                "SELECT COUNT(*) FROM FMC_game WHERE  upper(model) like  ? ";
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
 		Game game=null;
 		Vector result = null;
 		dongmay="%"+dongmay.toUpperCase()+"%";
 		try {
 			conn = poolX.getConnection();
 			strSQL =

 			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter,CAT_NAME FROM " +
 			" (SELECT FMC_GAME.ID,FMC_GAME.name,description,cat_id,model,FMC_ID,download_counter,FMC_GAME_CATEGORY.NAME CAT_NAME, " +
 			" ROW_NUMBER() OVER(ORDER BY FMC_GAME.LAST_UPDATED DESC) AS R FROM fmc_game LEFT JOIN FMC_GAME_CATEGORY ON FMC_GAME.CAT_ID=FMC_GAME_CATEGORY.ID " +
 			" WHERE  upper(model) like ? AND FMC_GAME.STATUS=1)" +
 			" WHERE R>=? AND R<=?  ";
 			preStmt = conn.prepareStatement(strSQL);
 			preStmt.setString(1, dongmay);
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
 				game.setModel(getString(rs.getClob(5)));
 				game.setFmcId(rs.getBigDecimal(6));
 				game.setDownloadCounter(rs.getInt(7));
 				game.setCatName(rs.getString(8));
 				result.addElement(game);
 			}
 		} catch (SQLException e) {
 			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
 					+ e.toString());
 		} finally {
 			poolX.releaseConnection(conn, preStmt, rs);

 		}
 		return result;
 	}
    public BigDecimal getCount(BigDecimal catId,String dongmay,String doimay,String ten) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;
        dongmay=(dongmay!=null)?"%"+dongmay.toUpperCase()+"%":"%";
        doimay=doimay!=null?"%"+doimay.toUpperCase()+"%":"%";
        ten=ten!=null?"%"+ten+"%":"%";
        BigDecimal count =null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM FMC_game WHERE  upper(model) like  ? AND upper(model) LIKE ? AND CAT_ID=? AND NAME LIKE ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,dongmay);
            preStmt.setString(2,doimay);
            preStmt.setBigDecimal(3,catId);
            preStmt.setString(4,ten);
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
 	    ten=ten!=null?"%"+ten+"%":"%";
 		Connection conn = null;
 		PreparedStatement preStmt = null;
 		ResultSet rs = null;
 		String strSQL = null;
 		Game game=null;
 		Vector result = null;
 		try {
 			conn = poolX.getConnection();
 			strSQL =

 			"SELECT ID,name,description,cat_id,model,FMC_ID,download_counter FROM " +
 			"(SELECT ID,name,description,cat_id,model,FMC_ID,download_counter," +
 			"ROW_NUMBER() OVER(ORDER BY LAST_UPDATED DESC) AS R FROM fmc_game " +
 			" WHERE  upper(model) like  ? AND upper(model) LIKE ? AND CAT_ID=? AND NAME LIKE ?)" +
 			" WHERE R>=? AND R<=?  ";
 			preStmt = conn.prepareStatement(strSQL);
 			preStmt.setString(1,dongmay);
            preStmt.setString(2,doimay);
 			preStmt.setBigDecimal(3, catId);
 			preStmt.setString(4,ten);
 			preStmt.setInt(5,startRow);
 			preStmt.setInt(6,stopRow);
 			rs = preStmt.executeQuery();
 			result = new Vector();
 			while (rs.next()) {
 				game=new Game();
 				game.setId(rs.getBigDecimal(1));
 				game.setName(rs.getString(2));
 				game.setDesc(rs.getString(3));
 				game.setCatId(rs.getBigDecimal(4));
 				game.setModel(getString(rs.getClob(5)));
 				game.setFmcId(rs.getBigDecimal(6));
 				game.setDownloadCounter(rs.getInt(7));
 				result.addElement(game);
 			}
 		} catch (SQLException e) {
 			System.out.println("findByPage: Error executing SQL " + strSQL + ">>>"
 					+ e.toString());
 		} finally {
 			poolX.releaseConnection(conn, preStmt, rs);

 		}
 		return result;
 	}  
}
