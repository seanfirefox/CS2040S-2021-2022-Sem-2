import java.util.ArrayList;

import java.util.Arrays;

public class Trie {

    // Wildcards

    final char WILDCARD = '.';

    private class TrieNode {

        // TODO: Create your TrieNode class here.

        TrieNode parent;

        TrieNode[] children;

        boolean endOfTrie;

        char ascii;

        public TrieNode() {

            this.children = new TrieNode[62];

            this.endOfTrie = false;

        }

        public TrieNode(char ascii) {

            this.children = new TrieNode[62];

            this.endOfTrie = false;

            this.ascii = ascii;

        }

        public TrieNode child(int index) {

            return this.children[index];

        }

        public void insertChild(TrieNode node, int index) {

            this.children[index] = node;

            this.parent = node;

        }

        public void flagEnd() {

            this.endOfTrie = true;

        }

    }

    TrieNode root;

    public Trie() {

        // TODO: Initialise a trie class here.

        this.root = new TrieNode();

    }

    /**

     * Inserts string s into the Trie.

     *

     * @param s string to insert into the Trie

     */

    void insert(String s) {

        // TODO

        TrieNode temp = this.root;

        for (int i = 0; i < s.length(); i++) {

            char ascii = s.charAt(i);

            int index = getArrayIndex(ascii);

            if (temp.child(index) == null) {

                TrieNode node = new TrieNode(ascii);

                temp.insertChild(node, index);

            }

            temp = temp.child(index);

        }

        temp.flagEnd();

    }

    int getArrayIndex(char ascii) {

        int asc = (int) ascii;

        if (asc <= 57 && asc >= 48) {

            return asc - 48;

        } else if (asc <= 90 && asc >= 65) {

            return asc - 65 + 10;

        } else if (asc <= 122 && asc >= 97) {

            return asc - 97 + 10 + 26;

        } else {

            return -1;

        }

    }

    /**

     * Checks whether string s exists inside the Trie or not.

     *

     * @param s string to check for

     * @return whether string s is inside the Trie

     */

    boolean contains(String s) {

        // TODO

        TrieNode temp = this.root;

        for (int i = 0; i < s.length(); i++) {

            char ascii = s.charAt(i);

            int index = getArrayIndex(ascii);

            temp = temp.child(index);

            if (temp == null) {

                return false;

            }

        }

        return temp.endOfTrie;

    }

    /**

     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the

     * results into the specified ArrayList. Only returns at most the first limit results.

     *

     * @param s       pattern to match prefixes with

     * @param results array to add the results into

     * @param limit   max number of strings to add into results

     */

    void prefixSearch(String s, ArrayList<String> results, int limit) {

        // TODO

        prefixSearch_helper(s, results, limit, 0, this.root, "");

    }

    void prefixSearch_helper(String s, ArrayList<String> results, int limit, int index, TrieNode node, String result) {

        // checks for flag to add String result to array and recurse through node

        if (s.length() == index) {

            if (results.size() < limit && node.endOfTrie) {

                results.add(result);

            }

            for (TrieNode child1 : node.children) {

                if (child1 != null) {

                    prefixSearch_helper(s, results, limit, index, child1, result + child1.ascii);

                }

                if (results.size() == limit) break;

            }

        } else {

            // if character is . then just add child1.ascii to string, if its still on prefix

            // continue to check for existence of character in node and continue recursing

            char asc = s.charAt(index);

            if (asc == WILDCARD) {

                for (TrieNode child1 : node.children) {

                    if (child1 != null) {

                        prefixSearch_helper(s, results, limit, index + 1, child1, result + child1.ascii);

                    }

                }

            } else {

                TrieNode child1 = node.child(getArrayIndex(asc));

                if (child1 != null) {

                    prefixSearch_helper(s, results, limit, index + 1, child1, result + child1.ascii);

                }

            }

        }

    }

    // Simplifies function call by initializing an empty array to store the results.

    // PLEASE DO NOT CHANGE the implementation for this function as it will be used

    // to run the test cases.

    String[] prefixSearch(String s, int limit) {

        ArrayList<String> results = new ArrayList<String>();

        prefixSearch(s, results, limit);

        return results.toArray(new String[0]);

    }

    public static void main(String[] args) {

        Trie t = new Trie();

//        t.insert("peter");

//        t.insert("piper");

//        t.insert("picked");

//        t.insert("a");

//        t.insert("peck");

//        t.insert("of");

//        t.insert("pickled");

//        t.insert("peppers");

//        t.insert("pepppito");

//        t.insert("pepi");

//        t.insert("pik");

        t.insert("abbde");

        t.insert("abcd");

        t.insert("abcdef");

        t.insert("abed");

        t.insert("x");

        String[] result1 = t.prefixSearch("pe", 10);

        String[] result2 = t.prefixSearch("ab.", 10);

        // result1 should be:

        // ["peck", "pepi", "peppers", "pepppito", "peter"]

        // result2 should contain the same elements with result1 but may be ordered arbitrarily

    }

}