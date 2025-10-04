/*
 * Name: Joshua Yin
 * Course Number: CIS-221-82A
 * Code Description: The code in MatrixBST.java implements a binary search tree using an array and adjacency matrix.
 *                   The code in MatrixBSTTestDriver.java tests the methods in MatrixBST.java.
 *                   The code in SearchBibleWord.java creates a binary tree of all the words in a user-selected book of the Bible along with the chapter/
 *                     verse references of where each word occurs in the book, and can use either the adjacency matrix implementation of MatrixBST.java
 *                     or the linked structure binary search tree from the textbook found in BinarySearchTree.java.
 * Integrity Statement: I did not copy code from other people or sources other than our CIS-221 textbook. I did not use any AI software to help write code.
*/

import java.util.Iterator;

// Test MatrixBST methods
public class MatrixBSTTestDriver {
    public static void main(String[] args) {

        // Integers for testing
        int testIntA = 0;
        int testIntB = 10;
        int testIntC = 20;
        int testIntD = 30;
        int testIntE = 40;
        int testIntF = 50;
        int testIntG = 60;

        int customCap = 10; // Custom max capacity for BST

        BSTInterface<Integer> tree = new MatrixBST<>(customCap); // Matrix binary search tree with custom max capacity

        /*
         * Test Case 1: add
         */
        System.out.print("======= Test Case 1: add method =======");

        // Add when empty
        System.out.println("\nAdding to tree when it is empty:");
        System.out.println("Adding " + testIntD + " to tree (expecting true): " + tree.add(testIntD));
        testPrint(tree);

        // Add an element lesser
        System.out.println("\nAdding an element lesser than the root element in the tree:");
        System.out.println("Adding " + testIntB + " to tree...");
        tree.add(testIntB);
        testPrint(tree);

        // Add an element greater
        System.out.println("\nAdding an element greater than the root element in the tree:");
        System.out.println("Adding " + testIntE + " to tree...");
        tree.add(testIntE);
        testPrint(tree);

        // Add when array is full
        System.out.println("\nAdding an element to tree when it is full:");
        System.out.println("Filling array to max capacity (" + customCap + ")...");
        for (int i = 0; i < customCap; i++) { // Note: tree may already be full, this just ensures it is full
            tree.add(testIntA);
        }
        System.out.println("Adding " + testIntA + " to tree (expecting false): " + tree.add(testIntA));

        /*
         * Test Case 2: get
         */
        System.out.print("\n======= Test Case 2: get method =======");

        // Get when empty
        System.out.println("\nGet when tree is empty:");
        clearTree(tree);
        System.out.println("Getting " + testIntG + " when tree is empty (expecting null): " + tree.get(testIntG));

        // Get when only one node
        System.out.println("\nGet when tree has only one node:");
        tree.add(testIntE);
        System.out.println("Getting " + testIntE + " when it is the only element (expecting " + testIntE + "): " + tree.get(testIntE));

        // Get an element lesser
        System.out.println("\nGet an element less than the root:");
        System.out.println("Adding one greater element and one lesser element to the tree...");
        tree.add(testIntG);
        tree.add(testIntA);
        testPrint(tree);
        System.out.println("Getting the element less than " + testIntE + " (expecting " + testIntA + "): " + tree.get(testIntA));

        // Get an element greater
        System.out.println("\nGet an element greater than the root:");
        System.out.println("Getting the element greater than " + testIntE + " (expecting " + testIntG + "): " + tree.get(testIntG));

        // Get an element not in tree
        System.out.println("\nGet an element not in the tree:");
        System.out.println("Getting an element not in the tree (expecting null): " + tree.get(testIntB));

        /*
         * Test Case 3: contains
         */
        System.out.print("\n======= Test Case 3: contains method =======");

        // Contains when empty
        System.out.println("\nContains when tree is empty:");
        clearTree(tree);
        System.out.println("Tree contains " + testIntC + " when tree is empty (expecting false): " + tree.contains(testIntA));

        // Contains when only one node
        System.out.println("\nContains when tree has only one node:");
        tree.add(testIntE);
        System.out.println("Tree contains " + testIntE + " when it is the only element (expecting true): " + tree.contains(testIntE));

        // Contains on element lesser
        System.out.println("\nContains an element less than the root:");
        System.out.println("Adding one greater element and one lesser element to the tree...");
        tree.add(testIntB);
        tree.add(testIntF);
        testPrint(tree);
        System.out.println("Tree contains the element less than " + testIntE + " (expecting true): " + tree.contains(testIntB));

        // Contains on element greater
        System.out.println("\nContains an element greater than the root:");
        System.out.println("Tree contains the element greater than " + testIntE + " (expecting true): " + tree.contains(testIntF));

        // Contains on element not in tree
        System.out.println("\nContains an element not in the tree:");
        System.out.println("Tree contains an element not in the tree (expecting false): " + tree.contains(testIntA));

        /*
         * Test Case 4: isFull
         */
        System.out.print("\n======= Test Case 4: isFull method =======");

        // isFull when empty
        System.out.println("\nCall isFull on empty tree:");
        clearTree(tree);
        System.out.println("isFull when tree is empty (expecting false): " + tree.isFull());

        // isFull when full
        System.out.println("\nCall isFull on full tree:");
        System.out.println("Filling array to max capacity (" + customCap + ")...");
        for (int i = 0; i < customCap; i++) { // Note: tree may already be full, this just ensures it is full
            tree.add(testIntA);
        }
        System.out.println("isFull when tree is full (expecting true): " + tree.isFull());

        /*
         * Test Case 5: isEmpty
         */
        System.out.print("\n======= Test Case 5: isEmpty method =======");

        // isEmpty when empty
        System.out.println("\nCall isEmpty on empty tree:");
        clearTree(tree);
        System.out.println("isEmpty when tree is empty (expecting true): " + tree.isEmpty());

        // isEmpty when not empty
        System.out.println("\nCall isEmpty on non-empty tree:");
        System.out.println("Adding one element to the tree...");
        tree.add(testIntA);
        System.out.println("isEmpty when tree has at least one element (expecting false): " + tree.isEmpty());

        /*
         * Test Case 6: size
         */
        System.out.print("\n======= Test Case 6: size method =======");

        // size when tree is empty
        System.out.println("\nSize when tree is empty:");
        clearTree(tree);
        System.out.println("Size (expecting 0): " + tree.size());

        // size when tree has some elements
        int addNums = 3;  // Number of elements to add
        System.out.println("\nSize when tree has " + addNums + " elements:");
        for (int i = 0; i < addNums; i++) {
            tree.add(i);
        }
        testPrint(tree);
        System.out.println("Size (expecting " + addNums + "): " + tree.size());

        /*
         * Test Case 7: min
         */
        System.out.print("\n======= Test Case 7: min method =======");

        // min when empty
        System.out.println("\nCall min when tree is empty:");
        clearTree(tree);
        System.out.println("Min when tree is empty (expecting null): " + tree.min());

        // min when one element
        System.out.println("\nCall min when tree has one element:");
        tree.add(testIntB);
        System.out.println("Min when tree has one element (expecting " + testIntB + "): " + tree.min());
        tree.remove(testIntB);

        // min when more than one element
        System.out.println("\nCall min when tree has more than one element:");
        // Add elements to tree in "random" order
        tree.add(testIntD);
        tree.add(testIntB);
        tree.add(testIntF);
        tree.add(testIntE);
        tree.add(testIntG);
        tree.add(testIntC);
        tree.add(testIntA);
        testPrint(tree);
        System.out.println("Min when tree has more than one element (expecting " + testIntA + "): " + tree.min());
        
        /*
         * Test Case 8: max
         */
        System.out.print("\n======= Test Case 8: max method =======");

        // max when empty
        System.out.println("\nCall max when tree is empty:");
        clearTree(tree);
        System.out.println("Max when tree is empty (expecting null): " + tree.max());

        // max when one element
        System.out.println("\nCall max when tree has one element:");
        tree.add(testIntB);
        System.out.println("Max when tree has one element (expecting " + testIntB + "): " + tree.max());
        tree.remove(testIntB);
        
        // max when more than one element
        System.out.println("\nCall max when tree has more than one element:");
        // Add elements to tree in "random" order
        tree.add(testIntD);
        tree.add(testIntB);
        tree.add(testIntF);
        tree.add(testIntE);
        tree.add(testIntG);
        tree.add(testIntC);
        tree.add(testIntA);
        testPrint(tree);
        System.out.println("Max when tree has more than one element (expecting " + testIntG + "): " + tree.max());

        /*
         * Test Case 9: remove
         */
        System.out.print("\n======= Test Case 9: remove method =======");

        // Remove when empty
        System.out.println("\nRemove when tree is empty:");
        clearTree(tree);
        System.out.println("Removing " + testIntB + " from tree (expecting false): " + tree.remove(testIntB));

        // Remove when one node
        System.out.println("\nRemove when tree has one element:");
        tree.add(testIntC);
        System.out.println("Removing " + testIntC + " from tree (expecting true): " + tree.remove(testIntC));
        tree.remove(testIntC);

        // Remove when two nodes
        System.out.println("\nRemove an element when tree has more than one element:");
        // Add elements to tree in "random" order
        tree.add(testIntD);
        tree.add(testIntB);
        tree.add(testIntF);
        tree.add(testIntE);
        tree.add(testIntG);
        tree.add(testIntC);
        tree.add(testIntA);
        testPrint(tree);
        System.out.println("Tree graphical representation:");
        tree.printTree();

        // Remove a node with no children
        System.out.println("\nRemove an element with no children:");
        System.out.println("Removing " + testIntA + " from tree (expecting true): " + tree.remove(testIntA));
        testPrint(tree);
        tree.printTree();

        // Remove a node with one child
        System.out.println("\nRemove an element with one child:");
        System.out.println("Removing " + testIntB + " from tree (expecting true): " + tree.remove(testIntB));
        testPrint(tree);
        tree.printTree();

        // Remove a node with two children
        System.out.println("\nRemove an element with two children:");
        System.out.println("Removing " + testIntF + " from tree (expecting true): " + tree.remove(testIntF));
        testPrint(tree);
        tree.printTree();

        /*
         * Test Case 10: iterator
         */
        System.out.print("\n======= Test Case 10: iterator methods =======");

        // Iterator when empty
        System.out.println("\nIterator hasNext when tree is empty: ");
        clearTree(tree);
        Iterator<Integer> iter = tree.iterator(); // Default iterator is inorder
        System.out.println("Iterator hasNext (expecting false): " + iter.hasNext());

        // Test preorder, inorder, and postorder
        System.out.println("\nTesting preorder, inorder, and postorder iterators:");
        System.out.println("Adding elements to tree...");
        // Add elements to tree in "random" order
        tree.add(testIntB);
        tree.add(testIntF);
        tree.add(testIntD);
        tree.add(testIntE);
        tree.add(testIntG);
        tree.add(testIntC);
        tree.add(testIntA);
        testPrint(tree);
        System.out.println("Tree graphical representation:");
        tree.printTree();

        // Iterator preorder
        System.out.println("\nPrint tree elements using preorder iterator:");
        iter = tree.getIterator(BSTInterface.Traversal.Preorder);
        System.out.println("Iterator preorder:");
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
            if (!iter.hasNext())    // Add an extra line break after last element
                System.out.println();
        }

        // Iterator inorder
        System.out.println("\nPrint tree elements using inorder iterator:");
        iter = tree.getIterator(BSTInterface.Traversal.Inorder);
        System.out.println("Iterator inorder:");
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
            if (!iter.hasNext())    // Add an extra line break after last element
                System.out.println();
        }

        // Iterator postorder
        System.out.println("\nPrint tree elements using postorder iterator:");
        iter = tree.getIterator(BSTInterface.Traversal.Postorder);
        System.out.println("Iterator postorder:");
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
            if (!iter.hasNext())    // Add an extra line break after last element
                System.out.println();
        }

        // Iterator remove
        System.out.println("\nAttempt to use remove on iterator (expecting exception):");
        iter = tree.iterator(); // Default iterator is inorder
        try {
            iter.remove();
        } catch (Exception e) {
            System.out.println("Exception successfully caught: " + e);
        }

        /*
         * End of tests
         */
        System.out.println("======= End of tests =======");
        System.out.println("All tests completed.");
    }
    
    // Clear/remove all elements from tree
    public static void clearTree(BSTInterface<Integer> tree) {
        System.out.println("Clearing tree...");
        Iterator<Integer> iter = tree.iterator();
        while (iter.hasNext()) {
            tree.remove(iter.next());
        }
    }

    // Prints elements in tree in order
    public static void testPrint(BSTInterface<Integer> tree) {
        System.out.print("BST state: ");
        int hold;
        Iterator<Integer> iter = tree.iterator(); // Default iterator is inorder
        while (iter.hasNext()) {
            hold = iter.next();
            System.out.print(hold);
            if (iter.hasNext()) // Add a space between elements and a newline after the last element
                System.out.print(" ");
            else
                System.out.print("\n");
        }
    }
}