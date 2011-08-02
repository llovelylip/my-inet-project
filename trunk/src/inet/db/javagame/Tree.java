package inet.db.javagame;

import java.math.BigDecimal;
import java.lang.Exception;

public class Tree {
    private Node[] nodes;

    /**
     * @return an array of nodes in order as nodes in a tree.
     * The first element, index of 0, is the root of this tree.
     */
    public Node[] getNodes() {
        return this.nodes;
    }
    public void setNodes(Node[] nodes) {
        this.nodes = nodes;
    }


    // Empty CONTRUCTOR
    public Tree() {
        this.nodes = null;
    }
    /**
     * @param nodes is an array of nodes arranged in order of created time of
     *              message which it represents.
     * NOTE:
     * 1. Only call the postOrder(*) or preOrder(*) if the number of elements
     *    is greater than 1.
     * 2. Depending on situation, we must call the reverse() method.
     *
     * @param rootIdx is the index of the root element of this tree.
     *
     */
    public Tree(Node[] nodes, int rootIdx) {
	if (nodes.length > 1) {
            //this.postOrder(nodes, rootIdx);
            //this.reverse();
	    this.preOrder(nodes, rootIdx);
	} else {
	    this.nodes = nodes;
	}
    }
    /**
     * We can use preOrder(*) or postOrder(*)
     */
    private void preOrder(Node[] nodes, int rootIdx) {
        if ((nodes == null) || (nodes.length < 2))  return;
        Node rootNode = nodes[rootIdx];
        this.addNodeIntoTree(rootNode);
        int[] childrenIdx = rootNode.getChildrenIdx();

        // Having children ?
        if (childrenIdx != null && childrenIdx.length > 0) {
            for (int i = 0; i < childrenIdx.length; i++) {
                int idx = childrenIdx[i];
                Node aChildNode = nodes[idx];
                if ( (aChildNode.getChildrenIdx()) == null) { //is a leaf
                    this.addNodeIntoTree(aChildNode);
                } else { // not a leaf; has its own children.
                    this.preOrder(nodes, idx);
                }
            }
        }
    }

    private void postOrder(Node[] nodes, int rootIdx) {
        if (nodes==null) return;
        Node rootNode = nodes[rootIdx];
        int[] childrenIdx = rootNode.getChildrenIdx();

        // Having children ?
        if (childrenIdx != null && childrenIdx.length > 0) {
            for (int i = 0; i < childrenIdx.length; i++) {
                int idx = childrenIdx[i];
                Node aChildNode = nodes[idx];
                if ( (aChildNode.getChildrenIdx()) == null) { //is a leaf
                    this.addNodeIntoTree(aChildNode);
                } else { // not a leaf; has its own children.
                    this.postOrder(nodes, idx);
                }
            }//for
        }
        this.addNodeIntoTree(rootNode);
    }
    private void addNodeIntoTree(Node aNode) {
        if (this.nodes==null) { // empty tree
            this.nodes = new Node[1];
            this.nodes[0] = aNode;
        } else {
            int oldSize = this.nodes.length;
            Node[] result = new Node[oldSize + 1];
            for (int i=0; i<oldSize; i++)
                result[i] = this.nodes[i];
            result[oldSize] = aNode;
            this.nodes = result;
        }
    }
    /**
     * reverse tree after the postOrder algorithm.
     */
    private void reverse(){
        int len = this.nodes.length;
        Node[] tempNodes = new Node[len];
        for(int i=0; i<len; i++) {
            tempNodes[i] = this.nodes[len-1-i];
        }
        this.nodes = tempNodes;
    }
}
