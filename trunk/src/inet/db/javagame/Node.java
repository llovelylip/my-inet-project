package inet.db.javagame;
import java.util.*;
import java.math.BigDecimal;
import inet.util.Logger;
 /**
  * Each Node object represent a node on the tree of category.
  * A node has 2 elements: info:NodeInfo and childrenIdx:int[].
  *
  * e.g:
  * Node node = new Root(); // any node
  * node.setGroupId(xxx);
  * Tree tree = node.getTree();
  * .......
  *
  */
@SuppressWarnings("serial")
public class Node implements java.io.Serializable {
    private Tree tree;

    //NodeInfo info = null;
    private JavaGameCat info = null;

    private int[] childrenIdx = null;

    /* used to indicate whether this Node has more children. */
    private int nextChildIdx;

    private JavaGameCatDAO catDao = null;
    private Logger logger = null;
    //CONSTRUCTORS
    public Node () {
        catDao = new JavaGameCatDAO();
        logger = new Logger(this.getClass().getName());
    }

    public Node (JavaGameCat info) {
        this.info = info;
        catDao = new JavaGameCatDAO();
        logger = new Logger(this.getClass().getName());
    }

    public Node (JavaGameCat info, int[] childrenIdx) {
        this.info = info;
        this.childrenIdx = childrenIdx;
        catDao = new JavaGameCatDAO();
        logger = new Logger(this.getClass().getName());
    }


    /* Creates new TreeNode */
    public Tree getTree() {
        Node[] nodes = this.buildNodesForTree();
        nodes = this.addChildrenIdxIntoNodes(nodes);
        this.tree = new Tree(nodes, 0);
        return this.tree;
    }

    public Node[] getChildren() {
        Node[] nodes = null;
        try {
            Collection cCats = catDao.findByParentId(this.info.getId());
            if (cCats != null && cCats.size() > 0) {
                nodes = new Node[cCats.size()];
                int i = 0;
                for (Iterator it = cCats.iterator(); it.hasNext(); i++) {
                	JavaGameCat cat = (JavaGameCat) it.next();
                    nodes[i] = new Node(cat);
                }
            }
            return nodes;
        } catch (Exception e) {
            logger.log("getChildren: " + e.getMessage());
            return null;
        }
    }
    public Node[] getBrothers() {
        Node[] nodes = null;
        try {
            Collection cCats = catDao.findByParentId(this.info.getParentId());
            if (cCats != null && cCats.size() > 0) {
                nodes = new Node[cCats.size()];
                int i = 0;
                for (Iterator it = cCats.iterator(); it.hasNext(); i++) {
                    JavaGameCat cat = (JavaGameCat) it.next();
                    nodes[i] = new Node(cat);
                }
            }
            return nodes;
        } catch (Exception e) {
            logger.log("getBrothers: " + e.getMessage());
            return null;
        }
    }

    /* Categories at the same level */
    public Node[] getNeighbors() {
        Node[] nodes = null;
        try {
            Collection cCats = catDao.findByLevel(this.info.getLevel());
            if (cCats != null && cCats.size() > 0) {
                nodes = new Node[cCats.size()];
                int i = 0;
                for (Iterator it = cCats.iterator(); it.hasNext(); i++) {
                    JavaGameCat cat = (JavaGameCat) it.next();
                    nodes[i] = new Node(cat);
                }
            }
            return nodes;
        } catch (Exception e) {
            logger.log("getNeighbors: " + e.getMessage());
            return null;
        }
    }

    /* Any Category can be presented as Node --> getTree() */
    public static Node getFirst() {
        Node node = null;
        try {
            JavaGameCatDAO catDao = new JavaGameCatDAO();
            JavaGameCat cat = catDao.findFirst();
            // Needed to get tree
            node = new Node(cat);
            return node;
        } catch (Exception e) {
            System.out.println("Node.getFirst: " + e.getMessage());
            return null;
        }

    }

    /* Root > Child 1 > Child 1.1 > Child 1.1.1(=catId: we are here) */
    public static Node[] getThreadBackToRoot(BigDecimal catId) {
        Vector vThreadOfCats  = new Vector();

        loadCategory(vThreadOfCats, catId);

        if (vThreadOfCats.size() <= 0) {
            return null;
        }
        Node[] nodes = new Node[vThreadOfCats.size()];
        vThreadOfCats.toArray(nodes);
        nodes = reverse(nodes);
        return nodes;

    }
    /**
     *  loadCategory:
     *  IN: CatId, OUT: vThreadOfCats
     */
    public static void loadCategory(Vector vThreadOfCats, BigDecimal catId) {
        try {
            JavaGameCatDAO catDao = new JavaGameCatDAO();
            JavaGameCat cat = catDao.getRow(catId);
            Node node = new Node(cat);
            vThreadOfCats.add(node);
            if (cat.getParentId().intValue() > 0) {
                //De quy
                loadCategory(vThreadOfCats, cat.getParentId());
            }
        } catch (Exception e) {
            System.out.println("Node.loadCategory: " + e.getMessage());
        }
    }
    private static Node[] reverse(Node[] nodes){
        int len = nodes.length;
        Node[] newNodes = new Node[len];
        for(int i=0; i<len; i++) {
            newNodes[i] = nodes[len-1-i];
        }
        return newNodes;
    }

    //----------------------------------
    public JavaGameCat getInfo(){
        return info;
    }
    public void setInfo(JavaGameCat info) {
        this.info = info;
    }
    //----------------------------------
    public int[] getChildrenIdx() {
        return childrenIdx;
    }
    public void setChildrenIdx(int[] childrenIdx) {
        this.childrenIdx = childrenIdx;
        this.nextChildIdx = 0;
    }
    //----------------------------------
    /**
     * @param idx specifies the index of the next child of this node.
     *            This index will be added to the children index array.
     */
    public void addChildIdx(int idx) {
        if(idx<=0) return; //0 -> rootNode
        else if (childrenIdx==null) {
            childrenIdx = new int[1];
            childrenIdx[0] = idx;
        }else {
            int oldSize = childrenIdx.length;
            int[] result = new int[oldSize + 1];
            for (int i=0; i<oldSize; i++)
                result[i] = childrenIdx[i];
            result[oldSize] = idx;
            childrenIdx = result;
         }
    }
    //--------------------------------------------------
    public boolean hasNextChild() {
        if(nextChildIdx < getNumOfChildren()) // e.g: 1 child -> 0 < 1
            return true;
        else
            return false;
    }
    //--------------------------------------------------
    public int getNextChildIdx() {
        if(nextChildIdx < getNumOfChildren()) {
            int result = nextChildIdx;
            nextChildIdx++;
            return result;
        }else
            return -1; // should consider to throw an exception
    }
    //--------------------------------------------------
    public int getNumOfChildren() {
       if (childrenIdx==null)
            return 0;
        else
            return childrenIdx.length;
    }


    //*************************************************************//
    //         PRIVATE METHODS FOR CREATING A TREE                 //
    //*************************************************************//

    /**
     * 1) This is the first step of create a tree.
     * @return an array of Node objects. Each node has node info
     *         and the children indexes of null. The first element,
     *         which has the index of 0, is the root of this tree.
     */
    private Node[] buildNodesForTree() {
        Node[] nodes = null;
        try {
            // Select all of current group
            Collection cCats = catDao.findByGroupId(this.info.getGroupId());
            if (cCats != null && cCats.size() > 0) {
                nodes = new Node[cCats.size()];
                int i = 0;
                for (Iterator it = cCats.iterator(); it.hasNext(); i++) {
                    JavaGameCat cat = (JavaGameCat) it.next();
                    //create a node with children indexes of null
                    nodes[i] = new Node(cat);
                }
            }
            // Finding the index of root: usually the last node.
            // Since we array messages by ID, in ASCENDING order;
            // And the root message must be created first.
            int rootIdx = findRootIdx(nodes);
            // If not the first element then move it to there.
            if (rootIdx > 0)
                this.exchangeNode(nodes, 0, rootIdx);
        } catch (Exception e) {
            logger.log("buildNodesForTree(): " + e.getMessage());
        }
        return nodes;
    }


    /**
     * 2) This is the second step of creating a tree
     * Specifying the children indexes for each node on the tree.
     *
     */
    private Node[] addChildrenIdxIntoNodes(Node[] nodes) {
        int size = nodes.length;
        for(int i=1; i<size; i++) {
            BigDecimal catId = nodes[i].getInfo().getId();
            BigDecimal parentId = nodes[i].getInfo().getParentId();
            //Finding the index of current message's parent.
            int parentIdx = this.findIndexById(parentId, nodes);
            //util.log("Idx: " + parentIdx);
            //util.log("ParentId: " + msgParentId);
            if(parentIdx >= 0) {
                nodes[parentIdx].addChildIdx(i);
            }
        }
        return nodes;
    }

    /**
     * @return the index of element whose msgLevel has the value of 0.
     * If not found, this method will return the value of -1.
     */
    private int findRootIdx(Node[] nodes) {
        for(int i=0; i<nodes.length; i++) {
           //usually the root node is the first one of the nodes array.
           //But we should find, just in case
           if((nodes[i].getInfo().getLevel())==0) return i;
        }
        return -1;
    }
    /**
     * @return the index of element whose msgId equals the msgId parameter.
     * If not found, this method will return the value of -1.
     */
    private int findIndexById(BigDecimal msgId, Node[] nodes) {
        JavaGameCat info = null;
        for (int i=0; i<nodes.length; i++) {
            info = nodes[i].getInfo();
            if(msgId.equals(info.getId()))
                return i;
        }
        return -1;
    }
    private void exchangeNode(Node[] nodes, int idx1, int idx2){
        Node temp;
        temp = nodes[idx1];
        nodes[idx1] = nodes[idx2];
        nodes[idx2] = temp;
    }

}

