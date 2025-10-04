import java.util.*; // Iterator, Comparator

// Implements BSTInterface using an adjacency matrix.
// Nodes are represented by indices.
public class MatrixBST<T> implements BSTInterface<T> {
    // Enumeration for relations in the adjacency matrix
    protected enum Relation {
        NONE('-'),  // Indicates no relationship
        L('L'),     // Indicates left child
        R('R');     // Indicates right child
        private final char RELATION;
        Relation(char RELATION) { this.RELATION = RELATION; }
    }

    protected final int DEFCAP = 3000;  // Default tree capacity
    protected int cap;                  // Tree capacity

    protected final int NUL = -1;       // Variable for invalid index

    protected int root = NUL;           // Root node
    protected Comparator<T> comp;       // Comparator used for all comparisons

    protected int numElements = 0;      // Represents number of elements in the tree

    protected T[] data;                 // Contains the data of a node
    protected Relation[][] matrix;      // Contains the relations of a node (rows are parents, columns are children)

    // Default constructor
    public MatrixBST() {
        cap = DEFCAP;
        data = (T[]) new Object[cap];
        matrix = new Relation[cap][cap];
        for (int i = 0; i < cap; i++) {
            for (int j = 0; j < cap; j++) {
                matrix[i][j] = Relation.NONE;   // Initialize all relations to none
            }
        }
        comp = new Comparator<T>() {
            public int compare(T element1, T element2) {
                return ((Comparable)element1).compareTo(element2);
            }
        };
    }

    // Constructor with custom capacity
    public MatrixBST(int customCap) {
        cap = customCap;
        data = (T[]) new Object[cap];
        matrix = new Relation[cap][cap];
        for (int i = 0; i < cap; i++) {
            for (int j = 0; j < cap; j++) {
                matrix[i][j] = Relation.NONE; // Initialize all relations to none
            }
        }
        comp = new Comparator<T>() {
            public int compare(T element1, T element2) {
                return ((Comparable)element1).compareTo(element2);
            }
        };
    }

    // Constructor with custom comparator
    public MatrixBST(Comparator<T> comp) {
        cap = DEFCAP;
        data = (T[]) new Object[cap];
        matrix = new Relation[cap][cap];
        for (int i = 0; i < cap; i++) {
            for (int j = 0; j < cap; j++) {
                matrix[i][j] = Relation.NONE; // Initialize all relations to none
            }
        }
        this.comp = comp;
    }

    // Constructor with custom capacity and custom comparator
    public MatrixBST(int customCap, Comparator<T> comp) {
        cap = customCap;
        data = (T[]) new Object[customCap];
        matrix = new Relation[customCap][customCap];
        for (int i = 0; i < cap; i++) {
            for (int j = 0; j < cap; j++) {
                matrix[i][j] = Relation.NONE; // Initialize all relations to none
            }
        }
        this.comp = comp;
    }

    // Adds an element to the tree at its appropriate location
    // Returns true if added successfully, otherwise false
    public boolean add(T element) {
        if (isFull())
            return false; // Array is full
        else {
            if (root == NUL) { // Tree is empty, add at root
                root = 0;
                data[root] = element;
                numElements++;
            }
            else // Tree is not empty, add at appropriate location
                recAdd(element, root);
            return true; // Added successfully
        }
    }

    // Finds the correct location to insert element, updating data and matrix as needed
    public void recAdd(T element, int node) {
        if (comp.compare(element, data[node]) <= 0) {   // Element is less than current node
            int nextNode = findLeft(node);  // Check for a left child
            if (nextNode != NUL)
                // Child exists, add in left subtree
                recAdd(element, nextNode);
            else {
                // Left is free, add there
                int index = findFree(); // Find a free index in the array
                data[index] = element;
                matrix[node][index] = Relation.L; // Update relation
                numElements++;
            }
        }
        else {  // Element is greater than current node
            int nextNode = findRight(node); // Check for a right child
            if (nextNode != NUL)
                // Child exists, add in right subtree
                recAdd(element, nextNode);
            else {
                // Right is free, add there
                int index = findFree(); // Find a free index in the array
                data[index] = element;
                matrix[node][index] = Relation.R; // Update relation
                numElements++;
            }
        }
    }

    // Find the first free index (node) in the data array
    // Preconditions: isFull() has been used elsewhere and returned false
    // Postconditions: returns the index of the first free index in the array
    //                 returns NUL (which is -1) if not found (which should never happen if preconditions are met)
    private int findFree() {
        // Loop and find free index
        for (int i = 0; i < cap; i++) {
            if (data[i] == null) {
                return i;
            }
        }
        return NUL;
    }

    // Returns info i from node of this BST where comp.compare(target, i) == 0.
    // If no such node exists, returns null.
    public T get(T target) {
        if (isEmpty())
            return null;
        return recGet(target, root);
    }

    // Returns info i from the subtree rooted at node such that comp.compare(target, i) == 0.
    // If no such info exists, returns null.
    private T recGet(T target, int node) {
        if (comp.compare(target, data[node]) < 0) {      // If target is less than current node info
            int nextNode = findLeft(node);                  // Check for a left child
            if (nextNode != NUL)                            // Left child exists
                return recGet(target, nextNode);               // Get from left subtree
            else                                            // No left child
                return null;                                   // Target was not found
        }
        else if (comp.compare(target, data[node]) > 0) { // If target is greater than current node info
            int nextNode = findRight(node);                 // Check for right child
            if (nextNode != NUL)                            // Right child exists
                return recGet(target, nextNode);               // Get from right subtree
            else                                            // No right child
                return null;                                   // Target was not found
        }
        else // Target is equal to current node info
            return data[node];
    }

    // Returns true if this BST contains a node with info i such that comp.compare(target, i) == 0.
    // Otherwise, returns false.
    public boolean contains(T target) {
        if (isEmpty())
            return false;
        return recContains(target, root);
    }

    // Returns true if the subtree rooted at node contains info i such that comp.compare(target, i) == 0.
    // Otherwise, returns false.
    private boolean recContains(T target, int node) {
        if (comp.compare(target, data[node]) < 0) {      // If target is less than current node data
            int nextNode = findLeft(node);                  // Check for a left child
            if (nextNode != NUL)                            // Left child exists
                return recContains(target, nextNode);          // Search left subtree
            else                                            // No left child
                return false;                                  // Target not found    
        }
        else if (comp.compare(target, data[node]) > 0) { // If target is less than current node data
            int nextNode = findRight(node);                 // Check for a right child
            if (nextNode != NUL)                            // Right child exists
                return recContains(target, nextNode);          // Search right subtree
            else                                            // No right child
                return false;                                  // Target not found
        }
        else // Target is equal to current node info
            return true;
    }

    // Removes a node with info i from tree such that comp.compare(target,i) == 0 and returns true.
    // If no such node exists, returns false.
    public boolean remove(T target) {
        if (isEmpty())
            return false;
        return recRemove(target, root);
    }

    // Removes element with info i from tree rooted at node such that comp.compare(target, i) == 0 and returns true.
    // If no such node exists, returns false. 
    private boolean recRemove(T target, int node) {
        if (comp.compare(target, data[node]) < 0) {  // If target is less than current node data
            int nextNode = findLeft(node);              // Check for a left child
            if (nextNode != NUL)                        // Left child exists
                return recRemove(target, nextNode);        // Search left subtree
            else                                        // No left child
                return false;                              // Target not found    
        }
        else if (comp.compare(target, data[node]) > 0) { // If target is less than current node data
            int nextNode = findRight(node);                 // Check for a right child
            if (nextNode != NUL)                            // Right child exists
                return recRemove(target, nextNode);            // Search right subtree
            else                                            // No right child
                return false;                                  // Target not found
        }
        else { // Target is equal to current node info
            removeNode(node);
            numElements--;
            return true;
        }
    }

    // Removes the specified node from the tree, rearranging relations if needed
    private void removeNode(int node) {
        int leftNode = findLeft(node);
        int rightNode = findRight(node);
        if (leftNode == NUL && rightNode == NUL) {
            // No children (leaf)
            if (numElements == 1) {
                root = NUL; // Removing only node, remove root index
            }
            for (int i = 0; i < cap; i++) { // Remove parent and child relations of node
                matrix[node][i] = Relation.NONE;
                matrix[i][node] = Relation.NONE;
            }
            data[node] = null;  // Remove node data
        }
        else if (leftNode == NUL) {
            // No left child, replace with right child
            overwriteNode(node, rightNode);
        }
        else if (rightNode == NUL) {
            // No right child, replace with left child
            overwriteNode(node, leftNode);
        }
        else {  // Two children
            // Find predecessor
            int tempNode = findLeft(node);
            int predecessor = tempNode;
            while (tempNode != NUL) {
                predecessor = tempNode;
                tempNode = findRight(tempNode);
            }

            // Replace node data with predecessor data
            data[node] = data[predecessor];

            // Remove old predecessor node
            int nextNode = findLeft(predecessor);
            if (nextNode != NUL) {
                // Left child of predecessor exists, replace predecessor with it
                overwriteNode(predecessor, nextNode);
            }
            else {
                // Predecessor has no children, remove it
                data[predecessor] = null;
                removeNode(predecessor);
            }
        }
    }

    // Copies the data and relations of fromNode to toNode and removes the data and all relations of fromNode
    private void overwriteNode(int toNode, int fromNode) {
        data[toNode] = data[fromNode];
        data[fromNode] = null;
        for (int i = 0; i < cap; i++) {
            matrix[toNode][i] = matrix[fromNode][i];    // Copy relations
            matrix[fromNode][i] = Relation.NONE;    // Remove relations of old node
            matrix[i][fromNode] = Relation.NONE;    // Remove parents of old node for safety
        }
    }

    // Returns true if the data array is full, otherwise false.
    public boolean isFull() {
        return numElements == cap;
    }

    // Returns true if the data array is empty, or if there is no root, or if there are no elements. Otherwise returns false.
    public boolean isEmpty() {
        return numElements == 0;    // Tree is empty if root is null or no elements
    }

    // Returns the number of elements in the array.
    public int size() {
        return numElements;
    }

    // If the BST is empty, returns null. Otherwise returns the smallest element in the tree.
    public T min() {
        if (isEmpty())
            return null;
        else {
            int nextNode = 0;
            int node = 0;
            while (nextNode != NUL) {
                node = nextNode;
                nextNode = findLeft(node);
            }
            return data[node];
        }
    }    

    // If the BST is empty, returns null. Otherwise returns the largest element in the tree.
    public T max() {
        if (isEmpty())
            return null;
        else {
            int nextNode = 0;
            int node = 0;
            while (nextNode != NUL) {
                node = nextNode;
                nextNode = findRight(node);
            }
            return data[node];
        }
    }

    // Returns the index of the left child if found. Otherwise returns NUL.
    private int findLeft(int row) {
        for (int column = 0; column < cap; column++) {
            if (matrix[row][column] == Relation.L)
                return column;
        }
        return NUL;
    }

    // Returns the index of the right child if found. Otherwise returns NUL.
    private int findRight(int row) {
        for (int column = 0; column < cap; column++) {
            if (matrix[row][column] == Relation.R)
                return column;
        }
        return NUL;
    }

    // Creates and returns an Iterator providing a traversal of a "snapshot" of the current tree in the order indicated by the argument.
    // Supports Preorder, Postorder, and Inorder traversal.
    public Iterator<T> getIterator(BSTInterface.Traversal orderType) {
        final LinkedQueue<T> infoQueue = new LinkedQueue<T>();
        if (orderType == BSTInterface.Traversal.Preorder)
          preOrder(root, infoQueue);
        else if (orderType == BSTInterface.Traversal.Inorder)
          inOrder(root, infoQueue);
        else if (orderType == BSTInterface.Traversal.Postorder)
          postOrder(root, infoQueue);
    
        return new Iterator<T>() {
            // Returns true if the iteration has more elements; otherwise returns false.
            public boolean hasNext() {
                return !infoQueue.isEmpty();
            }

            // Returns the next element in the iteration.
            // Throws NoSuchElementException - if the iteration has no more elements
            public T next() { 
                if (!hasNext())
                    throw new IndexOutOfBoundsException("Illegal invocation of next in BinarySearchTree iterator.\n");
                return infoQueue.dequeue();
            }
        
            // Throws UnsupportedOperationException.
            // Not supported. Removal from snapshot iteration is meaningless.
            public void remove() {
                throw new UnsupportedOperationException("Unsupported remove attempted on BinarySearchTree iterator.\n");
            }
        };
    }

    // Enqueues the elements from the subtree rooted at node into q in preOrder.
    private void preOrder(int node, LinkedQueue<T> q) {
        if (node != NUL && data[node] != null) {
            q.enqueue(data[node]);
            preOrder(findLeft(node), q);
            preOrder(findRight(node), q);
        }
    }

    // Enqueues the elements from the subtree rooted at node into q in inOrder.
    private void inOrder(int node, LinkedQueue<T> q) {
        if (node != NUL && data[node] != null) {
            inOrder(findLeft(node), q);
            q.enqueue(data[node]);
            inOrder(findRight(node), q);
        }
    }
    
    // Enqueues the elements from the subtree rooted at node into q in postOrder.
    private void postOrder(int node, LinkedQueue<T> q) {
        if (node != NUL && data[node] != null) {
            postOrder(findLeft(node), q);
            postOrder(findRight(node), q);
            q.enqueue(data[node]);
        }
    }

    // InOrder is the default, "natural" order.
    public Iterator<T> iterator() {
        return getIterator(BSTInterface.Traversal.Inorder);
    }

    // Prints a graphical representation of the tree.
    // Does not work for all data types.
    public void printTree() {
        printBinaryTree(root, 0);    
    } 

    protected void printBinaryTree(int root, int level){
        if (root == NUL)
            return;
        printBinaryTree(findRight(root), level + 1);
        if (level != 0) {
            for(int i = 0; i < level - 1; i++)
                System.out.print("|\t");
            System.out.println("|-------" + data[root]);
        }
        else
            System.out.println(data[root]);
        printBinaryTree(findLeft(root), level + 1);
    }

    // Returns true if tree is balanced, otherwise false
    public boolean isBalanced() {
        return recIsBalanced(root) != -1;
    }

    // Determines if left and right subtrees are balanced. Subtrees are unbalanced when heights differ by more than 1.
    // Returns height of subtree if balanced, otherwise returns -1 if unbalanced.
    private int recIsBalanced(int node) {
        if (node == NUL)
            return 0;   // A null subtree is balanced and has height 0

        int leftHeight = recIsBalanced(findLeft(node));
        if (leftHeight == -1)
            return -1;  // Left subtree is unbalanced

        int rightHeight = recIsBalanced(findRight(node));
        if (rightHeight == -1)
            return -1;  // Right subtree is unbalanced

        if (Math.abs(leftHeight - rightHeight) > 1)
            return -1;  // Current node is unbalanced

        return 1 + Math.max(leftHeight, rightHeight);  // Return height if balanced
    }
}