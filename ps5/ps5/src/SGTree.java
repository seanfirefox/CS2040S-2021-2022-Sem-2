/**

 * ScapeGoat Tree class

 * <p>

 * This class contains some basic code for implementing a ScapeGoat tree. This version does not include any of the

 * functionality for choosing which node to scapegoat. It includes only code for inserting a node, and the code for

 * rebuilding a subtree.

 */

public class SGTree {

    // Designates which child in a binary tree

    enum Child {LEFT, RIGHT}

    /**

     * TreeNode class.

     * <p>

     * This class holds the data for a node in a binary tree.

     * <p>

     * Note: we have made things public here to facilitate problem set grading/testing. In general, making everything

     * public like this is a bad idea!

     */

    public static class TreeNode {

        int key;

        public TreeNode left = null;

        public TreeNode right = null;

        int weight = 1;

        TreeNode(int k) {

            key = k;

        }

    }

    // Root of the binary tree

    public TreeNode root = null;

    /**

     * Counts the number of nodes in the specified subtree.

     *

     * @param node  the parent node, not to be counted

     * @param child the specified subtree

     * @return number of nodes

     */

    public int countNode_helper(TreeNode node, Child child, int countN) {

        if (node.left == null && node.right == null) {

            return 0;

        } else {

            if (child == Child.LEFT && node.left != null) {

                countN += countNodes(node.left, Child.LEFT) + countNodes(node.left, Child.RIGHT) + 1;

            } else if (child == Child.RIGHT && node.right != null) {

                countN += countNodes(node.right, Child.LEFT) + countNodes(node.right, Child.RIGHT) + 1;

            }

        }

        return countN;

    }

    public int countNodes(TreeNode node, Child child) {

        // TODO: Implement this

        return countNode_helper(node, child, 0);

    }

    /**

     * Builds an array of nodes in the specified subtree.

     *

     * @param node  the parent node, not to be included in returned array

     * @param child the specified subtree

     * @return array of nodes

     */

    int enumNodeCounter = 0;

    public TreeNode[] enumNode_helper(TreeNode node, TreeNode[] arr) {

        if (node != null) {

            enumNode_helper(node.left, arr);

            arr[enumNodeCounter] = node;

            enumNodeCounter++;

            enumNode_helper(node.right, arr);

        }

        return arr;

    }

    TreeNode[] enumerateNodes(TreeNode node, Child child) {

        // TODO: Implement this

        enumNodeCounter = 0;

        int nodelen = countNodes(node, child);

        TreeNode[] arr =  new TreeNode[nodelen];

        if (child == Child.RIGHT) {

            return enumNode_helper(node.right, arr);

        } else {

            return enumNode_helper(node.left, arr);

        }

    }

    /**

     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree

     *

     * @param nodeList ordered array of nodes

     * @return the new root node

     */

    public TreeNode buildTree_helper(int begin, int end, TreeNode[] nodeList) {

        if (begin > end) {

            return null;

        }

        int mid = (begin + end) / 2;

        TreeNode node = nodeList[mid];

        node.weight = end - begin + 1;

        node.right = buildTree_helper(mid + 1, end, nodeList);

        node.left = buildTree_helper(begin, mid - 1, nodeList);

        return node;

    }

    TreeNode buildTree(TreeNode[] nodeList) {

        // TODO: Implement this

        return buildTree_helper(0, nodeList.length - 1, nodeList);

    }

    /**

     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return

     * false. A node is unbalanced if either of its children has weight greater than 2/3 of its weight.

     *

     * @param node a node to check balance on

     * @return true if the node is balanced, false otherwise

     */

    public boolean checkBalance(TreeNode node) {

        // TODO: Implement this

        if (node == null || (node.right == null && node.left == null)) {

            return true;

        }

        int weight_left = 0;

        int weight_right = 0;

        int weight =  node.weight;

        if (node.right != null) {

            weight_right = node.right.weight;

        }

        if (node.left != null) {

            weight_left = node.left.weight;

        }

        return weight_right <= 2.0 /3.0 * (double) weight && weight_left <= 2.0/3.0 * (double) weight;

    }

    /**

     * Rebuilds the specified subtree of a node.

     *

     * @param node  the part of the subtree to rebuild

     * @param child specifies which child is the root of the subtree to rebuild

     */

    public void rebuild(TreeNode node, Child child) {

        // Error checking: cannot rebuild null tree

        if (node == null) return;

        // First, retrieve a list of all the nodes of the subtree rooted at child

        TreeNode[] nodeList = enumerateNodes(node, child);

        // Then, build a new subtree from that list

        TreeNode newChild = buildTree(nodeList);

        // Finally, replace the specified child with the new subtree

        if (child == Child.LEFT) {

            node.left = newChild;

        } else if (child == Child.RIGHT) {

            node.right = newChild;

        }

    }

    /**

     * Inserts a key into the tree.

     *

     * @param key the key to insert

     */

    public void insert(int key) {

        if (root == null) {

            root = new TreeNode(key);

            return;

        }

        TreeNode node = root;

        while (true) {

            node.weight++;

            if (key <= node.key) {

                if (node.left == null) break;

                node = node.left;

            } else {

                if (node.right == null) break;

                node = node.right;

            }

        }

        if (key <= node.key) {

            node.left = new TreeNode(key);

        } else {

            node.right = new TreeNode(key);

        }

        node = root;

        while (node != null) {

            if (!checkBalance(node.left)) {

                System.out.println(node.left.key);

                rebuild(node, Child.LEFT);

            }

            if (!checkBalance(node.right)) {

                System.out.println(node.right.key);

                rebuild(node, Child.RIGHT);

            }

            if (key <= node.key) {

                node = node.left;

            } else {

                node = node.right;

            }

        }

    }

    // Simple main function for debugging purposes

    public static void main(String[] args) {

        SGTree tree = new SGTree();

        for (int i = 0; i < 6; i++) {

            tree.insert(i);

        }

        tree.rebuild(tree.root, Child.RIGHT);

    }

}