/*
 * Project 4 Part 2: Index the Bible
 */

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

// Creates a binary tree of all words in a user-selected book of the Bible along with the chapter/verse references of where each word occurs in that book,
// then allows the user to repeatedly search for a word in the book, displaying all verses where the word appears in the book.
public class SearchBibleWord {
    public static void main(String[] args) throws IOException {
        BSTInterface<Reference> tree;   // Tree implementation type will be specified later
        Scanner scanner;                // Scanner variable for all instances of getting user input

        // Ask user which of the two tree implementations to use
        // If choice is 1, uses linked version. Otherwise uses matrix.
        scanner = new Scanner(System.in);
        System.out.println("Which binary search tree implementation would you like to use?\n1. Linked\n2. Matrix");
        int choice = scanner.nextInt();
        if (choice == 1) {  // Using linked implementation
            tree = new BinarySearchTree<Reference>();
            System.out.println("Using linked implementation.");
        }
        else {  // Using array and matrix implementation
            tree = new MatrixBST<Reference>();
            System.out.println("Using array and matrix implementation.");
        }

        // Get book of Bible to search and sort all words with their references into a binary search tree
        Reference wordToTry;
        Reference wordInTree;
        String book = "";
        boolean found = false;
        while (!found) {
            // Get book of Bible from user
            printBibleBooks();  // Display book options
            System.out.println("Enter a book of the Bible:");
            scanner = new Scanner(System.in);
            book = scanner.nextLine();

            // Set up Bible file for reading
            String fn = "kjv.txt";
            Scanner kjvBible = new Scanner(new FileReader(fn));

            // Create a binary search tree by alphabetical order of all words in the book with their references
            String line;
            while (kjvBible.hasNextLine()) {
                line = kjvBible.nextLine();
                if (line.toLowerCase().startsWith(book.toLowerCase())) {    // Ensure case insensitivity
                    found = true;   // Book has been found
                    String[] words = line.split("\\s+");    // Split line by spaces into individual strings
                    for (String word: words) {
                        // Remove any punctuation and capitalization. Delimiters are nonletters,'
                        word = word.replaceAll("[^a-zA-Z']", "").toLowerCase();

                        wordToTry = new Reference(word);
                        wordInTree = tree.get(wordToTry);

                        if (wordInTree == null) {
                            // Word not in tree yet, insert word into tree
                            wordToTry.addVerse(words[0]);
                            tree.add(wordToTry);
                        }
                        else if (!wordInTree.getVerses().contains(words[0])) {
                            // Word already in tree and verse not added to references yet, just add the verse
                            wordInTree.addVerse(words[0]);   
                        }
                    }
                }
            }

            if (!found)
                System.out.println("Book not found.");
        }

        // Allow user to repeatedly search for a word in the book.
        // Every time a new word is input, all verses where the word appears in the book are displayed.
        // Entering !quit stops the program.
        if (found) {    // Ensure book has been found
            System.out.println("Finished sorting words.");

            rebalanceTree(tree);    // Rebalance tree

            String userChoice = "";
            while (!userChoice.equals("!quit")) {
                System.out.println("Enter a word to search (!quit to quit):");
                userChoice = scanner.nextLine().toLowerCase();
                if (!userChoice.equals("!quit")) {
                    wordToTry = new Reference(userChoice);
                    wordInTree = tree.get(wordToTry);
                    if (wordInTree != null)
                        // Word exists in tree, display verses
                        System.out.println(wordInTree);
                    else
                        // Word not in tree
                        System.out.println("Word not found.");
                }
                else
                    // Stopping the program
                    System.out.println("Quitting...");
            }
        }
    }

    // Balances a given tree and returns the balanced tree
    public static BSTInterface<Reference> rebalanceTree(BSTInterface<Reference> tree) {
        System.out.println("Rebalancing tree...");
        Iterator<Reference> iter = tree.getIterator(BSTInterface.Traversal.Inorder);

        Reference[] tempArray = new Reference[tree.size()]; // Temporary array to hold items from tree

        // Add reference objects from tree to array (inorder)
        int i = 0;
        while (iter.hasNext()) {
            tempArray[i] = iter.next();
            i++;
        }

        // Clear tree
        iter = tree.iterator();
        while (iter.hasNext()) {
            tree.remove(iter.next());
        }

        // Add references back to tree in a balanced order
        addArrayToTree(0, tempArray.length - 1, tree, tempArray);

        // Verify tree is balanced
        System.out.println("Tree is balanced status (should be true): " + tree.isBalanced());

        return tree;
    }

    // Recursively create balanced nodes from the middle of sorted subarrays and build subtrees from remaining halves.
    private static void addArrayToTree(int low, int high, BSTInterface<Reference> tree, Reference[] array) {
        if (low <= high) {
            int mid = (low + high) / 2;
            tree.add(array[mid]);
            addArrayToTree(low, mid - 1, tree, array);  // Left subarrays/subtrees
            addArrayToTree(mid + 1, high, tree, array); // Right subarrays/subtrees
        }
    }

    // Displays the abbreviations of the books used in the Bible file
    public static void printBibleBooks() {
        String[][] books = {
            {"Ge", "Exo", "Lev", "Num", "Deu", "Josh", "Jdgs", "Ruth"},
            {"1Sm", "2Sm", "1Ki", "2Ki", "1Chr", "2Chr", "Ezra", "Neh"},
            {"Est", "Job", "Psa", "Prv", "Ecc", "SSol", "Isa", "Jer"},
            {"Lam", "Eze", "Dan", "Hos", "Joel", "Amos", "Obad", "Jonah"},
            {"Mic", "Nahum", "Hab", "Zep", "Hag", "Zec", "Mal", "Mat"},
            {"Mark", "Luke", "John", "Acts", "Rom", "1Cor", "2Cor", "Gal"},
            {"Eph", "Phi", "Col", "1Th", "2Th", "1Tim", "2Tim", "Titus"},
            {"Phmn", "Heb", "Jas", "1Pet", "2Pet", "1Jn", "2Jn", "3Jn"},
            {"Jude", "Rev"}
        };

        System.out.println("Book abbreviations:");
        for (String[] row: books) {
            for (String book: row) {
                System.out.printf("%-6s", book); // %-5s for left alignment in a 5-char field
            }
            System.out.println();
        }
    }
}