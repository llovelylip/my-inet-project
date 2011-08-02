package inet.db.fmcgame;
import inet.db.pool.DBPoolX;
import inet.db.pool.DBPoolXName;
import inet.util.Logger;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Vector;

public class GameCatDAO {
    private Logger logger;
    private DBPoolX poolX=null;
    public GameCatDAO() {
        logger = new Logger(this.getClass().getName());
        try{
    		poolX=DBPoolX.getInstance(DBPoolXName.AAA);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    public boolean insertRow(String name, BigDecimal parentId, int groupId, int indentLevel, int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;

        if (parentId == null) parentId = new BigDecimal(0);//root
        if (parentId.intValue() == 0) indentLevel = 0; //root
        if (groupId <= 0) groupId = getNewGroupId();

        boolean result = false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "INSERT INTO FMC_GAME_CATEGORY (ID, NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS) " +
                "VALUES (FMC_GAME_CATEGORY_SEQ.nextval, ?, ?, ?, ?, ?)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, name);
            preStmt.setBigDecimal(2, parentId);
            preStmt.setInt(3, groupId);
            preStmt.setInt(4, indentLevel);
            preStmt.setInt(5, status);
            if (preStmt.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            logger.error("insertRow: Error executing SQL " + strSQL + ">>>" + e.toString());
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
                "delete from FMC_GAME_CATEGORY where ID = ?";
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

    public GameCat getRow(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        GameCat cat = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS " +
                "FROM FMC_GAME_CATEGORY WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                cat = new GameCat();
                cat.setName(rs.getString(1));
                cat.setParentId(rs.getBigDecimal(2));
                cat.setGroupId(rs.getInt(3));
                cat.setIndentLevel(rs.getInt(4));
                cat.setStatus(rs.getInt(5));
                cat.setId(id);
            }

        } catch (SQLException e) {
            logger.error("getRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return cat;
    }

    public boolean updateRow(GameCat cat) {
        if (cat == null) {
            logger.log("GameCategory is null --> could not be updated");
            return false;
        }

        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE FMC_GAME_CATEGORY SET NAME = ?, PARENT_ID = ?, STATUS = ? " +
                "WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, cat.getName());
            preStmt.setBigDecimal(2, cat.getParentId());
            preStmt.setInt(3, cat.getStatus());
            preStmt.setBigDecimal(4, cat.getId());
            if (preStmt.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            logger.error("updateRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt);
           
        } return result;
    }

    /**
     * @return a collection of cat objects
     */
    public Collection findAll() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS " +
                "FROM FMC_GAME_CATEGORY";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            result = new Vector();
            GameCat cat = null;
            while (rs.next()) {
                cat = new GameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setParentId(rs.getBigDecimal(3));
                cat.setGroupId(rs.getInt(4));
                cat.setIndentLevel(rs.getInt(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.error("findAll: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }

    /**
     * @return a collection of cat objects
     */
    public Collection findAllRoots() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS " +
                "FROM FMC_GAME_CATEGORY WHERE PARENT_ID = 0";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            result = new Vector();
            GameCat cat = null;
            while (rs.next()) {
                cat = new GameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setParentId(rs.getBigDecimal(3));
                cat.setGroupId(rs.getInt(4));
                cat.setIndentLevel(rs.getInt(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.error("findAllRoots: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }

    /**
     * @return a collection of cat objects
     */
    public Collection findAllRoots(int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS " +
                "FROM FMC_GAME_CATEGORY WHERE PARENT_ID = 0 AND STATUS = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, status);
            rs = preStmt.executeQuery();
            result = new Vector();
            GameCat cat = null;
            while (rs.next()) {
                cat = new GameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setParentId(rs.getBigDecimal(3));
                cat.setGroupId(rs.getInt(4));
                cat.setIndentLevel(rs.getInt(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.error("findAllRoots: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }

    /**
     * @return a collection of cat objects
     */
    public Collection findTreeFromId(BigDecimal rootId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS " +
                "FROM FMC_GAME_CATEGORY " +
                "CONNECT BY PRIOR ID = PARENT_ID " +
                "START WITH ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, rootId);
            rs = preStmt.executeQuery();
            result = new Vector();
            GameCat cat = null;
            while (rs.next()) {
                cat = new GameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setParentId(rs.getBigDecimal(3));
                cat.setGroupId(rs.getInt(4));
                cat.setIndentLevel(rs.getInt(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.error("findTreeFromId: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return result;
    }

    /**
     * @return a collection of cat objects
     */
    public Collection findTreeFromId(BigDecimal rootId, int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS " +
                "FROM FMC_GAME_CATEGORY " +
                "WHERE STATUS = ? " +
                "CONNECT BY PRIOR ID = PARENT_ID " +
                "START WITH ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, status);
            preStmt.setBigDecimal(2, rootId);
            rs = preStmt.executeQuery();
            result = new Vector();
            GameCat cat = null;
            while (rs.next()) {
                cat = new GameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setParentId(rs.getBigDecimal(3));
                cat.setGroupId(rs.getInt(4));
                cat.setIndentLevel(rs.getInt(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.error("findTreeFromId: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }

    /**
     * @return a collection of cat objects
     */
    public Collection findByParentId(BigDecimal parentId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS " +
                "FROM FMC_GAME_CATEGORY WHERE PARENT_ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, parentId);
            rs = preStmt.executeQuery();
            result = new Vector();
            GameCat cat = null;
            while (rs.next()) {
                cat = new GameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setParentId(rs.getBigDecimal(3));
                cat.setGroupId(rs.getInt(4));
                cat.setIndentLevel(rs.getInt(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.error("findByParentId: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
           
        } return result;
    }
    /**
     * @return a collection of cat objects
     */
    public Collection findByParentId(BigDecimal parentId, int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, PARENT_ID, GROUP_ID, INDENT_LEVEL, STATUS " +
                "FROM FMC_GAME_CATEGORY WHERE PARENT_ID = ? AND STATUS = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, parentId);
            preStmt.setInt(1, status);
            rs = preStmt.executeQuery();
            result = new Vector();
            GameCat cat = null;
            while (rs.next()) {
                cat = new GameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setParentId(rs.getBigDecimal(3));
                cat.setGroupId(rs.getInt(4));
                cat.setIndentLevel(rs.getInt(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.error("findByParentId: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return result;
    }

    public int getNewGroupId() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        int groupId = 1;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT MAX(GROUP_ID) FROM FMC_GAME_CATEGORY";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                groupId = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("getNewGroupId: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return groupId;
    }
    public int getCountChidren(BigDecimal id) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;

        int count = 0;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT COUNT(*) FROM FMC_GAME_CATEGORY WHERE PARENT_ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, id);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log("getCountChidren: Error executing SQL " + strSQL + " >>> " + e.toString());
        } finally {
            poolX.releaseConnection(conn, preStmt, rs);
            
        }return count;
    }

}
