/*******************************************************************************
 * File name: KrazeCategoryDAO.java
 * @author Nguyen Thien Phuong 
 * Copyright (c) 2007 iNET Media Corporation 
 * Created on Nov 29, 2007
 ******************************************************************************/


package inet.webservice.krazevina;
import inet.util.Logger;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Vector;
import inet.db.pool.*;
public class KrazeCategoryDAO {
	private Logger logger = new Logger(this.getClass().getName());
	private DBPoolX poolX=null;
	public KrazeCategoryDAO(){
		try {
			poolX=DBPoolX.getInstance(DBPoolXName.GALAFUN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean insertCategory(KrazeCategory cat) {
		if (cat == null) return false;
		boolean result = false;
		Connection conn = null;
		PreparedStatement preStmt = null;
		String strSQL = null;
		
		try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            strSQL = "INSERT INTO SOAP_CATEGORY_KRAZE (ID, NAME, TYPE) VALUES(SOAP_CATEGORY_KRAZE_SEQ.nextVal, ?, ?)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, cat.getName());
            preStmt.setInt(2, cat.getType());
            if (preStmt.executeUpdate() == 1) {
                conn.commit();
                result = true;
            } else {
                conn.rollback();
            }
        } catch (NoSuchElementException nse) {
    	    logger.error("insertCategory: Error executing >>>" + nse.toString());
        } catch (SQLException se) { 
            logger.error("insertCategory: Error executing SQL >>> " + strSQL + " >>> " + se.toString());
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
	
	public boolean updateCategory(KrazeCategory cat) {
		if (cat == null) return false;
		boolean result = false;
		Connection conn = null;
		PreparedStatement preStmt = null;
		String strSQL = null;
		
		try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            strSQL = "UPDATE SOAP_CATEGORY_KRAZE SET NAME = ? WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, cat.getName());
            preStmt.setBigDecimal(2, cat.getId());
            if (preStmt.executeUpdate() == 1) {
                conn.commit();
                result = true;
            } else {
                conn.rollback();
            }
        } catch (NoSuchElementException nse) {
    	    logger.error("updateCategory: Error executing >>>" + nse.toString());
        } catch (SQLException se) { 
            logger.error("updateCategory: Error executing SQL >>> " + strSQL + " >>> " + se.toString());
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
	
	public boolean deleteCategory(BigDecimal id) {
		if (id == null) return false;
		boolean result = false;
		Connection conn = null;
		PreparedStatement preStmt = null;
		String strSQL = null;
		
		try {
            conn = poolX.getConnection();
            conn.setAutoCommit(false);
            strSQL = "DELETE FROM SOAP_CATEGORY_KRAZE WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            if (preStmt.executeUpdate() == 1) {
                conn.commit();
                result = true;
            } else {
                conn.rollback();
            }
        } catch (NoSuchElementException nse) {
    	    logger.error("deleteCategory: Error executing >>>" + nse.toString());
        } catch (SQLException se) { 
            logger.error("deleteCategory: Error executing SQL >>> " + strSQL + " >>> " + se.toString());
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
	
	public Collection getCategories(int type) {
		Vector result = null;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		
		try {
            conn = poolX.getConnection();

            strSQL = "SELECT ID, NAME FROM SOAP_CATEGORY_KRAZE WHERE TYPE = ? ORDER BY ID ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, type);
            rs = preStmt.executeQuery();
            
            KrazeCategory cat = null;
            result = new Vector();
            while (rs.next()) {
            	cat = new KrazeCategory();
            	cat.setId(rs.getBigDecimal(1));
            	cat.setName(rs.getString(2));
            	cat.setType(type);
            	result.addElement(cat);
            }
            
        } catch (NoSuchElementException nse) {
    	    logger.error("getCategories: Error executing >>>" + nse.toString());
        } catch (SQLException se) { 
            logger.error("getCategories: Error executing SQL >>> " + strSQL + " >>> " + se.toString());
    	} catch (Exception e) {
            e.printStackTrace();
        } finally {
    	    poolX.releaseConnection(conn, preStmt);
        }
		return result;
	}
	
	public KrazeCategory getCategory(BigDecimal id) {
		KrazeCategory cat = null;
		Connection conn = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		String strSQL = null;
		
		try {
            conn = poolX.getConnection();

            strSQL = "SELECT ID, NAME, TYPE FROM SOAP_CATEGORY_KRAZE WHERE ID = ? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            
            if (rs.next()) {
            	cat = new KrazeCategory();
            	cat.setId(rs.getBigDecimal(1));
            	cat.setName(rs.getString(2));
            	cat.setType(rs.getInt(3));
            }
            
        } catch (NoSuchElementException nse) {
    	    logger.error("getCategory(id): Error executing >>>" + nse.toString());
        } catch (SQLException se) { 
            logger.error("getCategory(id): Error executing SQL >>> " + strSQL + " >>> " + se.toString());
    	} catch (Exception e) {
            e.printStackTrace();
        } finally {
    	    poolX.releaseConnection(conn, preStmt);
        }
		return cat;
	}
}
