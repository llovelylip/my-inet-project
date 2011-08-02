package inet.db.javagame;
import java.util.*;
import java.sql.*;
import java.math.BigDecimal;
import inet.util.Logger;
import inet.db.pool.*;
/**
 * @author Huyen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JavaGameCatDAO {
    private Logger logger = null;
    private DBPoolX poolX=null; 
    public JavaGameCatDAO() {
        logger = new Logger(this.getClass().getName());
        try {
			poolX=DBPoolX.getInstance(DBPoolXName.SERVICE_DEFAULT); 
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public boolean createNewRoot(String name) {
        try {
            int level = 0;
            int status = 1; //active
            BigDecimal parentId = new BigDecimal(0);
            BigDecimal groupId  = this.getGroupId();
            return insertRow(name, level, parentId, groupId, status);
        } catch (Exception e) {
            logger.log("createNewRoot: " + e.getMessage());
            return false;
        }
    }

    public boolean createNewChild(String name, int level, BigDecimal parentId, BigDecimal groupId) {
        try {
            int status = 1;
            return insertRow(name, level, parentId, groupId, status);
        } catch (Exception e) {
            logger.log("createNewChild: " + e.getMessage());
            return false;
        }
    }

   public boolean insertRow(String name, int level,
                                BigDecimal parentId, BigDecimal groupId, int status) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        String strSQL = null;
        boolean result=false;
        try {
            conn = poolX.getConnection();
            strSQL =
                "INSERT INTO Partner_Game_category (ID, NAME," +
                "INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS) " +
                "VALUES (Partner_Game_category_SEQ.nextval, ?,?, ?, ?, ?)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, name);
            preStmt.setInt(2, level);
            preStmt.setBigDecimal(3, parentId);
            preStmt.setBigDecimal(4, groupId);
            preStmt.setInt(5, status);
            if (preStmt.executeUpdate() == 1) {
            	 result=true;
             } 
            
        } catch (SQLException e) {
             logger.log("insertRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
            poolX. releaseConnection(conn, preStmt, stmt, rs);
           
        } return result;
    }

    public boolean insertRow(JavaGameCat cat) {
        if (cat == null) {
            logger.log("insertRow: cat is null");
            return false;
        }
        Connection conn = null;
        PreparedStatement preStmt = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean result=false;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "INSERT INTO Partner_Game_CATEGORY (ID, NAME, " +
                "INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS) " +
                "VALUES (Partner_Game_category_CATEGORY_SEQ.nextval, ?, ?, ?, ?, ?)";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, cat.getName());
            preStmt.setInt(2, cat.getLevel());
            preStmt.setBigDecimal(3, cat.getParentId());
            preStmt.setBigDecimal(4, cat.getGroupId());
            preStmt.setInt(5, cat.getStatus());
            if (preStmt.executeUpdate() == 1) {
                result=true;
            }
        } catch (SQLException e) {
             logger.log("insertRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
           
            poolX. releaseConnection(conn, preStmt, stmt, rs);
            
        }return result;
    }

    public boolean deleteRow(BigDecimal catId) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "delete from Partner_Game_CATEGORY where ID = ? ";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, catId);
            if (preStmt.executeUpdate() > 0) {
                result = true;
            }
        } catch(SQLException e) {
            logger.log("deleteRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	 poolX. releaseConnection(conn, preStmt);
            return result;
        }
    }
    public Collection getOthergameCat(BigDecimal Id,int nums){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        int i=0;
        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_CATEGORY " +
                "WHERE PARENT_ID =0 and id<>?  ORDER BY ID ASC";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, Id);
            rs = preStmt.executeQuery();
            result = new Vector();
             JavaGameCat cat = null;
            while (rs.next()&&i<3) {
            	i++;
                cat = new JavaGameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
               
                cat.setLevel(rs.getInt(3));
                cat.setParentId(rs.getBigDecimal(4));
                cat.setGroupId(rs.getBigDecimal(5));
                cat.setStatus(rs.getInt(6));
                
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.log("findByParentId: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	 poolX. releaseConnection(conn, preStmt, rs);
            return result;
        }
   	
    }
    public JavaGameCat getRow(BigDecimal catId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;
        JavaGameCat cat = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_category WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, catId);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                cat = new JavaGameCat();
                cat.setName(rs.getString(1));
                cat.setLevel(rs.getInt(2));
                cat.setParentId(rs.getBigDecimal(3));
                cat.setGroupId(rs.getBigDecimal(4));
                cat.setStatus(rs.getInt(5));
                cat.setId(catId);
                
            }

        } catch (SQLException e) {
            logger.log("getRow: Error executing SQL " + strSQL + ">>>" + e.toString());
            return cat;
        } finally {
        	 poolX. releaseConnection(conn, preStmt, rs);
        }
        return cat;
    }

    public boolean updateRow(JavaGameCat cat) {
        if (cat == null) {
            logger.log("cat is null --> could not be updated");
            return false;
        }

        boolean result = false;
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "UPDATE Partner_Game_CATEGORY SET NAME = ? ,INDENT_LEVEL = ?, " +
                "PARENT_ID = ?, GROUP_ID = ?, STATUS = ? WHERE ID = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setString(1, cat.getName());
            preStmt.setInt(2, cat.getLevel());
            preStmt.setBigDecimal(3, cat.getParentId());
            preStmt.setBigDecimal(4, cat.getGroupId());
            preStmt.setInt(5, cat.getStatus());
            preStmt.setBigDecimal(6, cat.getId());
            if (preStmt.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            logger.log("updateRow: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	 poolX.  releaseConnection(conn, preStmt);
            
        }return result;
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
                "SELECT ID, NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_CATEGORY WHERE PARENT_ID = 0";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            result = new Vector();
            
            JavaGameCat cat = null;
            while (rs.next()) {
                cat = new JavaGameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setLevel(rs.getInt(3));
                cat.setParentId(rs.getBigDecimal(4));
                cat.setGroupId(rs.getBigDecimal(5));
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
                "SELECT ID, NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_CATEGORY WHERE PARENT_ID = 0 AND STATUS = ?";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, status);
            rs = preStmt.executeQuery();
            result = new Vector();
            
            JavaGameCat cat = null;
            while (rs.next()) {
                cat = new JavaGameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setLevel(rs.getInt(3));
                cat.setParentId(rs.getBigDecimal(4));
                cat.setGroupId(rs.getBigDecimal(5));
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
     *
     * @return a collection of RingtoneCategory
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
                "SELECT ID, NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_CATEGORY";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            result = new Vector();
            JavaGameCat cat = null;
            while (rs.next()) {
                cat = new JavaGameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                
                cat.setLevel(rs.getInt(3));
                cat.setParentId(rs.getBigDecimal(4));
                cat.setGroupId(rs.getBigDecimal(5));
                cat.setStatus(rs.getInt(6));
                
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.log("findAll: Error executing SQL " + strSQL + ">>>" +e.toString());
        } finally {
        	 poolX. releaseConnection(conn, preStmt, rs);
            
        }return result;
    }

    public JavaGameCat findFirst() {
        Connection conn = null;
        PreparedStatement preStmt = null;
        String strSQL = null;
        ResultSet rs = null;
        JavaGameCat cat = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_CATEGORY " +
                "WHERE ID = (SELECT MIN(ID) FROM Partner_Game_CATEGORY)";
            preStmt = conn.prepareStatement(strSQL);
            rs = preStmt.executeQuery();
            if (rs.next()) {
                cat = new JavaGameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                
                cat.setLevel(rs.getInt(3));
                cat.setParentId(rs.getBigDecimal(4));
                cat.setGroupId(rs.getBigDecimal(5));
                cat.setStatus(rs.getInt(6));
                
            }
        } catch (SQLException e) {
            logger.log("findFirst: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	 poolX.  releaseConnection(conn, preStmt, rs);
            
        }return cat;
    }

  
    public Collection findByParentId(BigDecimal parentId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_CATEGORY " +
                "WHERE PARENT_ID = ?  ORDER BY ID ASC";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, parentId);
            rs = preStmt.executeQuery();
            result = new Vector();
            JavaGameCat cat = null;
            while (rs.next()) {
                cat = new JavaGameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setLevel(rs.getInt(3));
                cat.setParentId(rs.getBigDecimal(4));
                cat.setGroupId(rs.getBigDecimal(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.log("findByParentId: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	 poolX. releaseConnection(conn, preStmt, rs);
            
        }return result;
    }

    public Collection findByGroupId(BigDecimal groupId) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_CATEGORY " +
                "WHERE GROUP_ID = ? ORDER BY ID ASC";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setBigDecimal(1, groupId);
            rs = preStmt.executeQuery();
            result = new Vector();
            JavaGameCat cat = null;
            while (rs.next()) {
                cat = new JavaGameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setLevel(rs.getInt(3));
                cat.setParentId(rs.getBigDecimal(4));
                cat.setGroupId(rs.getBigDecimal(5));
                cat.setStatus(rs.getInt(6));
                
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.log("findByGroupId: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	 poolX. releaseConnection(conn, preStmt, rs);
            
        }return result;
    }

    public Collection findByLevel(int level) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet rs  = null;
        String strSQL = null;

        Vector result = null;
        try {
            conn = poolX.getConnection();
            strSQL =
                "SELECT ID, NAME, INDENT_LEVEL, PARENT_ID, GROUP_ID, STATUS " +
                "FROM Partner_Game_CATEGORY " +
                "WHERE INDENT_LEVEL = ? ORDER BY ID ASC";
            preStmt = conn.prepareStatement(strSQL);
            preStmt.setInt(1, level);
            rs = preStmt.executeQuery();
            result = new Vector();
            JavaGameCat cat = null;
            while (rs.next()) {
                cat = new JavaGameCat();
                cat.setId(rs.getBigDecimal(1));
                cat.setName(rs.getString(2));
                cat.setLevel(rs.getInt(3));
                cat.setParentId(rs.getBigDecimal(4));
                cat.setGroupId(rs.getBigDecimal(5));
                cat.setStatus(rs.getInt(6));
                result.addElement(cat);
            }
        } catch (SQLException e) {
            logger.log("findByLevel: Error executing SQL " + strSQL + ">>>" + e.toString());
        } finally {
        	 poolX. releaseConnection(conn, preStmt, rs);
            return result;
        }

    }


    public Node[] getArrayOfRootNodes() {
        Node[] roots = null;
        try {
            Collection cCats = findByParentId(new BigDecimal(0));
            if (cCats != null && cCats.size() > 0) {
                roots = new Node[cCats.size()];
                int i = 0;
                for (Iterator it = cCats.iterator(); it.hasNext(); i++) {
                	JavaGameCat cat = (JavaGameCat) it.next();
                    //create a node with children indexes of null
                    Node node = new Node(cat);
                    roots[i] = node;
                }
            }
            return roots;
        } catch (Exception e) {
            logger.log("getArrayOfRootNodes: " + e.getMessage());
            return null;
        }
    }


    /**
     *@return the groupID for the new root message
     */
     private BigDecimal getGroupId() {
         Connection conn = null;
         PreparedStatement preStmt = null;
         ResultSet rs = null;

         BigDecimal result = null;
         try {
             conn = poolX.getConnection();

             // Load from the database
             preStmt = conn.prepareStatement("select MAX(GROUP_ID) from Partner_Game_CATEGORY");
             rs = preStmt.executeQuery();
             if (rs.next()) {
                 long gid = rs.getLong(1);
                 result = new BigDecimal(gid + 1);
             }
         } catch (SQLException e) {
             logger.log("getGroupId(): " + e.getMessage());
             return result;
         } finally {
        	 poolX.releaseConnection(conn, preStmt, rs);
         }
         return result;
     }


     /* Any Category can be presented as Node --> getTree() */
     public Node getCategoryAsNode(BigDecimal catId) {
         Node node = null;
         try {
             JavaGameCat cat = getRow(catId);
             if (cat != null) {
                 // Needed to get tree
                 node = new Node(cat);
             }
             return node;
         } catch (Exception e) {
             logger.log("getCategoryAsNode: " + e.getMessage());
             return null;
         }
     }

     public static void main(String[] args) {
         
         
     }
}
