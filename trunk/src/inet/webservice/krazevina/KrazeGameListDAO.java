/*******************************************************************************
 * File name: KrazeGameListDAO.java
 * @author Nguyen Thien Phuong 
 * Copyright (c) 2007 iNET Media Corporation 
 * Created on Nov 28, 2007
 ******************************************************************************/


package inet.webservice.krazevina;
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
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;
import inet.db.pool.*;


public class KrazeGameListDAO {
	public KrazeGameListDAO(){
		try {
			poolX=DBPoolX.getInstance(DBPoolXName.GALAFUN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Logger logger = new Logger(this.getClass().getName());
	private DBPoolX poolX=null;
	public boolean insertGame(KrazeGameList game) {
		if (game == null) return false;
		boolean result = false;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		
		try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            
//          1 - Get next Id
			Vector vSeqNum = new Vector();
			strSQL = "SELECT ID FROM SOAP_GAME_KRAZE ORDER BY ID";
			preStmt = conn.prepareStatement(strSQL);
			rs = preStmt.executeQuery();
			int maxId = 1;
			while (rs.next()) {
				if (rs.getInt(1) > maxId) maxId = rs.getInt(1);
				vSeqNum.addElement(rs.getString(1));
			}
			int seqNum = 1;
			int i = 1;
			while (i <= maxId + 1) {
				if (!vSeqNum.contains("" + i)) {
					seqNum = i;
					break;
				}
				i++;
			}
			preStmt.close();
            
            strSQL = "INSERT INTO SOAP_GAME_KRAZE (ID, CAT_ID, NAME, CODE, DESCRIPTION, IMG_LINK,WAP_IMAGE, TYPE, HANDSET1, HANDSET2, HANDSET3)"
					+ " VALUES(?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, new BigDecimal(String.valueOf(seqNum)));
            preStmt.setBigDecimal(2, game.getCatId());
            preStmt.setString(3, game.getName());
            preStmt.setString(4, game.getKrazeCode());
            preStmt.setString(5, game.getDescription());
            preStmt.setBlob(6, LOBs.createBlob(conn, game.getImgLink() )); 
            preStmt.setBlob(7, LOBs.createBlob(conn, game.getWapImage() )); 
            preStmt.setInt(8, game.getType());
            preStmt.setString(9, game.getHandset1());
            preStmt.setString(10, game.getHandset2());
            preStmt.setString(11, game.getHandset3());
            if (preStmt.executeUpdate() == 1) {
					conn.commit();
					result = true;
            } else {
                conn.rollback();
            }
        } catch (NoSuchElementException nse) {
    	    logger.error("insertGame: Error executing >>>" + nse.toString());
        } catch (SQLException se) { 
            logger.error("insertGame: Error executing SQL >>> " + strSQL + " >>> " + se.toString());
    	} catch (Exception e) {
            e.printStackTrace();
        } finally {
    	    if (conn != null) {
	            try {
	                conn.setAutoCommit(true);
	            } catch (SQLException se) {}
    	    }
    	    poolX.releaseConnection(conn, preStmt, rs);
        }
		return result;
	}
	
	public boolean updateGame(KrazeGameList game) {
		if (game == null) return false;
		boolean result = false;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		
		try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
           
            strSQL = "UPDATE SOAP_GAME_KRAZE SET CAT_ID = ?, NAME = ?, CODE = ?, DESCRIPTION = ?, HANDSET1 = ?, HANDSET2 = ?, HANDSET3 = ? "
					+ " WHERE ID = ? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, game.getCatId());
            preStmt.setString(2, game.getName());
            preStmt.setString(3, game.getKrazeCode());
            preStmt.setString(4, game.getDescription());
            preStmt.setString(5, game.getHandset1());
            preStmt.setString(6, game.getHandset2());
            preStmt.setString(7, game.getHandset3());
            preStmt.setBigDecimal(8, game.getId());
            if (preStmt.executeUpdate() == 1) {
            	if(game.getImgLink()!=null&&game.getImgLink().length>0){
            		updateImage(game.getId(),game.getImgLink(),conn);
            	}
            	if(game.getWapImage()!=null&&game.getWapImage().length>0){
            		updateWapImage(game.getId(),game.getWapImage(),conn);
            	}
				conn.commit();
				result=true;
            } else {
                conn.rollback();
                result=false;
            }
        } catch (NoSuchElementException nse) {
    	    logger.error("updateGame: Error executing >>>" + nse.toString());
        } catch (SQLException se) { 
            logger.error("updateGame: Error executing SQL >>> " + strSQL + " >>> " + se.toString());
    	} catch (Exception e) {
            e.printStackTrace();
        } finally {
    	    if (conn != null) {
	            try {
	                conn.setAutoCommit(true);
	            } catch (SQLException se) {}
    	    }
    	    poolX.releaseConnection(conn, preStmt, rs);
        }
		return result;
	}
	 public boolean updateImage(BigDecimal id,byte[] image,Connection conn) {
	        PreparedStatement preStmt = null;
	        String strSQL  = null;
	        boolean result = false;
	        try {
	            strSQL =
	                    "UPDATE SOAP_GAME_KRAZE SET IMG_LINK=? WHERE ID=?";
	            preStmt = conn.prepareStatement(strSQL);
	            preStmt.setBlob(1, LOBs.createBlob(conn, image));
	            preStmt.setBigDecimal(2,id);
	            if(preStmt.executeUpdate()>=1){
	            	result=true;
	            }
	        } catch (SQLException e) {
	           e.printStackTrace();
	        } catch (IOException e) {
	        	System.out.print("updateImage: " + e.toString());
	        } finally {
	        	try {
	        		preStmt.close();   
				} catch (Exception e) {
					e.printStackTrace();
				} 
	        }return result;
	    }
	    public boolean updateWapImage(BigDecimal id,byte[] image,Connection conn) {
	        PreparedStatement preStmt = null;
	        String strSQL  = null;
	        boolean result = false;
	        try {
	            strSQL =
	                    "UPDATE SOAP_GAME_KRAZE SET WAP_IMAGE=? WHERE ID=?";
	            preStmt = conn.prepareStatement(strSQL);
	            preStmt.setBlob(1, LOBs.createBlob(conn, image));
	            preStmt.setBigDecimal(2,id);
	            if(preStmt.executeUpdate()>=1){
	            	result=true;
	            }
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
	public boolean deleteGame(BigDecimal id) {
		if (id == null) return false;
		boolean result = false;
		Connection conn = null;
		PreparedStatement preStmt = null;
		String strSQL = null;
		
		try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            strSQL = "DELETE FROM SOAP_GAME_KRAZE WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            if (preStmt.executeUpdate() == 1) {
                conn.commit();
                result = true;
            } else {
                conn.rollback();
            }
        } catch (NoSuchElementException nse) {
    	    logger.error("deleteGame: Error executing >>>" + nse.toString());
        } catch (SQLException se) { 
            logger.error("deleteGame: Error executing SQL >>> " + strSQL + " >>> " + se.toString());
    	} catch (Exception e) {
            e.printStackTrace();
        } finally {
    	    if (conn != null) {
	            try {
	                conn.setAutoCommit(true);
	            } catch (SQLException se) {}
    	    }
    	    poolX.releaseConnection(conn, preStmt);
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
			strSQL = "SELECT IMG_LINK FROM SOAP_GAME_KRAZE WHERE ID = ?";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setBigDecimal(1, id);
			rs = preStmt.executeQuery();
			if (rs.next()) {
				Blob myBlob = rs.getBlob("IMG_LINK"); // GET BLOB LOCATOR
				content = LOBs.readByteBlob(myBlob);
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("getContentGif: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("getContentGif: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (IOException e) {
			logger.error("getContentGif: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);
		}
		return content;
	}
	public byte[] getContentWapImage(BigDecimal id) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		byte[] content = null;
		try {
			conn = poolX.getConnection();
			strSQL = "SELECT WAP_IMAGE FROM SOAP_GAME_KRAZE WHERE ID = ?";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setBigDecimal(1, id);
			rs = preStmt.executeQuery();
			if (rs.next()) {
				Blob myBlob = rs.getBlob("WAP_IMAGE"); // GET BLOB LOCATOR
				content = LOBs.readByteBlob(myBlob);
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("getContentWapImage: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("getContentWapImage: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (IOException e) {
			logger.error("getContentWapImage: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);
		}
		return content;
	}
	public Collection findByPage(int type, BigDecimal catId, int page, int rowsPerPage) {
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
			strSQL = "SELECT ID, NAME, CODE, DESCRIPTION, DOWNLOADED, LAST_UPDATED, HANDSET1, HANDSET2, HANDSET3 FROM "
					+ "(SELECT ID, NAME, CODE, DESCRIPTION, DOWNLOADED, LAST_UPDATED, HANDSET1, HANDSET2, HANDSET3, ROW_NUMBER() OVER(ORDER BY LAST_UPDATED DESC) AS R "
					+ "	FROM SOAP_GAME_KRAZE WHERE CAT_ID = ? AND TYPE = "
					+ type + ") WHERE R >= ? AND R <= ?  ";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setBigDecimal(1, catId);
			preStmt.setInt(2,startRow);
			preStmt.setInt(3,stopRow);
			rs = preStmt.executeQuery();
			result = new Vector();
			KrazeGameList game = null;
			while (rs.next()) {
				game = new KrazeGameList();
				game.setCatId(catId);
				game.setId(rs.getBigDecimal(1));
				game.setName(rs.getString(2));
				game.setKrazeCode(rs.getString(3));
				game.setDescription(rs.getString(4));
				game.setDownloaded(rs.getInt(5));
				game.setLastUpdated(rs.getTimestamp(6));
				game.setHandset1(rs.getString(7));
				game.setHandset2(rs.getString(8));
				game.setHandset3(rs.getString(9));
				result.addElement(game);
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("findByPage: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("findByPage: Error executing SQL " + strSQL + ">>>"
					+ e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);

		}
		return result;
	}
	public Collection findByPage(int page, int rowsPerPage) {
		int startRow = (page - 1) * rowsPerPage + 1;
		int stopRow = page * rowsPerPage;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;

		Vector result = null;
		try {
			conn = poolX.getConnection();
			strSQL = "SELECT ID, NAME, CODE, DESCRIPTION, DOWNLOADED, LAST_UPDATED, HANDSET1, HANDSET2, HANDSET3 FROM "
					+ "(SELECT ID, NAME, CODE, DESCRIPTION, DOWNLOADED, LAST_UPDATED, HANDSET1, HANDSET2, HANDSET3, ROW_NUMBER() OVER(ORDER BY LAST_UPDATED DESC) AS R "
					+ "	FROM SOAP_GAME_KRAZE ) WHERE R >= ? AND R <= ?  ";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setInt(1,startRow);
			preStmt.setInt(2,stopRow);
			rs = preStmt.executeQuery();
			result = new Vector();
			KrazeGameList game = null;
			while (rs.next()) {
				game = new KrazeGameList();
				game.setId(rs.getBigDecimal(1));
				game.setName(rs.getString(2));
				game.setKrazeCode(rs.getString(3));
				game.setDescription(rs.getString(4));
				game.setDownloaded(rs.getInt(5));
				game.setLastUpdated(rs.getTimestamp(6));
				game.setHandset1(rs.getString(7));
				game.setHandset2(rs.getString(8));
				game.setHandset3(rs.getString(9));
				result.addElement(game);
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("findByPage: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("findByPage: Error executing SQL " + strSQL + ">>>"
					+ e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);

		}
		return result;
	}
	public int  getIdGameByName(String name){
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
                "SELECT ID " +
                "FROM SOAP_GAME_KRAZE WHERE UPPER(REPLACE(NAME,' ','')) like ? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1,name );
            rs = preStmt.executeQuery();
            if (rs.next()) {
                seq=rs.getInt(1);
            }else{
            	preStmt.close();
            	rs.close();
            	preStmt = conn.prepareStatement("SELECT ID FROM SOAP_GAME_KRAZE WHERE UPPER(REPLACE(NAME,' ','')) like ? ");
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
	public int total(int type, BigDecimal catId) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;

		int result = 0;
		try {
			conn = poolX.getConnection();
			strSQL = "SELECT COUNT(ID) FROM SOAP_GAME_KRAZE WHERE CAT_ID = ? AND TYPE = " + type;
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setBigDecimal(1, catId);
			rs = preStmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("total: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("total: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);

		}
		return result;
	}
	public int total() {
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		int result = 0;
		try {
			conn = poolX.getConnection();
			strSQL = "SELECT COUNT(ID) FROM SOAP_GAME_KRAZE " ;
			preStmt = conn.prepareStatement(strSQL);
			rs = preStmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("total: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("total: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);

		}
		return result;
	}
	
	
	public KrazeGameList getGame(BigDecimal id) {
		
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		KrazeGameList game = null;
		try {
			conn = poolX.getConnection();
			strSQL = "SELECT ID, NAME, CODE, DESCRIPTION, DOWNLOADED, IMG_LINK, LAST_UPDATED, HANDSET1, HANDSET2, HANDSET3,CAT_ID "
					+ "	FROM SOAP_GAME_KRAZE WHERE ID = ? ";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setBigDecimal(1, id);
			rs = preStmt.executeQuery();
			
			if (rs.next()) {
				game = new KrazeGameList();
				game.setCatId(id);
				game.setId(rs.getBigDecimal(1));
				game.setName(rs.getString(2));
				game.setKrazeCode(rs.getString(3));
				game.setDescription(rs.getString(4));
				game.setDownloaded(rs.getInt(5));
				game.setImgLink(LOBs.readByteBlob(rs.getBlob(6)));
				game.setLastUpdated(rs.getTimestamp(7));
				game.setHandset1(rs.getString(8));
				game.setHandset2(rs.getString(9));
				game.setHandset3(rs.getString(10));
				game.setCatId(rs.getBigDecimal(11));
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("getGame: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("getGame: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);
		}
		return game;
	}
public KrazeGameList getGameByCode(String code) {
		
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		KrazeGameList game = null;
		try {
			conn = poolX.getConnection();
			strSQL = "SELECT ID, NAME, CODE, DESCRIPTION, DOWNLOADED, IMG_LINK, LAST_UPDATED, HANDSET1, HANDSET2, HANDSET3 "
					+ "	FROM SOAP_GAME_KRAZE WHERE CODE = ? ";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setString(1,code);
			rs = preStmt.executeQuery();
			
			if (rs.next()) {
				game = new KrazeGameList();
				
				game.setId(rs.getBigDecimal(1));
				game.setName(rs.getString(2));
				game.setKrazeCode(rs.getString(3));
				game.setDescription(rs.getString(4));
				game.setDownloaded(rs.getInt(5));
				game.setImgLink(LOBs.readByteBlob(rs.getBlob(6)));
				game.setLastUpdated(rs.getTimestamp(7));
				game.setHandset1(rs.getString(8));
				game.setHandset2(rs.getString(9));
				game.setHandset3(rs.getString(10));
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("getGame: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("getGame: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);
		}
		return game;
	}
	
	public Hashtable getGameTable(int type) {
		
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		KrazeGameList game = null;
		Hashtable gameTable = new Hashtable();
		try {
			conn = poolX.getConnection();
			strSQL = "SELECT ID, NAME, CODE, CAT_ID FROM SOAP_GAME_KRAZE WHERE TYPE = " + type;
			preStmt = conn.prepareStatement(strSQL);
			rs = preStmt.executeQuery();
			
			while (rs.next()) {
				game = new KrazeGameList();
				game.setId(rs.getBigDecimal(1));
				game.setName(rs.getString(2));
				game.setKrazeCode(rs.getString(3));
				game.setCatId(rs.getBigDecimal(4));
				gameTable.put(game.getId(), game);
			}
		} catch (NoSuchElementException nse) {
    	    logger.error("getGameTable: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("getGameTable: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (Exception e) {
            e.printStackTrace();
        }  finally {
			poolX.releaseConnection(conn, preStmt, rs);
		}
		return gameTable;
	}
	
	public void updateDownloaded(String krazeCode) {
		Connection conn = null;
		PreparedStatement preStmt = null;
		String strSQL = null;
		try {
			conn = poolX.getConnection();
			conn.setAutoCommit(false);
			strSQL = "UPDATE SOAP_GAME_KRAZE SET DOWNLOADED = DOWNLOADED + 1 WHERE CODE = ?";
			preStmt = conn.prepareStatement(strSQL);
			preStmt.setString(1, krazeCode);
			if (preStmt.executeUpdate() == 1) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (NoSuchElementException nse) {
    	    logger.error("updateDownloaded: Error executing >>>" + nse.toString());
        } catch (SQLException e) {
			logger.error("updateDownloaded: Error executing SQL " + strSQL + ">>>" + e.toString());
		} catch (Exception e) {
			logger.error("updateDownloaded: Error executing >>>" + e.toString());
        }  finally {
        	try {
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
			}
			poolX.releaseConnection(conn, preStmt);
		}
	}
}
