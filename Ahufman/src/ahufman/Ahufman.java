/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahufman;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author mahmoudsaeed
 */
class Node {

    Node left;
    Node right;
    char symbol;
    int count = 0;
    ArrayList<Node> s = new ArrayList<>();
    ArrayList<String> encoder = new ArrayList<>();

    public void add(Node root, char symbol) {
        Node current = root;
        if (root != null) {
            if (current.symbol == 'n') {
                Node crr = new Node();
                Node crr1 = new Node();
                crr1.symbol = symbol;
                crr1.count++;
                current.symbol = 'y';
                crr.symbol = 'n';
                current.left = crr;
                current.right = crr1;
                s.add(crr1);
                current.right.left = null;
                current.right.right = null;
                current.left.left = null;
                current.left.right = null;
                return;
            } else {
                add(root.right, symbol);
            }
            //current.left = add(current.left, symbol);
            add(current.left, symbol);
        }
    }

    public Node searchSymbol(char symbol, Node root) {
        Node crr = root;
        if (root != null) {
            if (root.symbol == symbol) {
                root.count++;
                return root;
            } else {

                searchSymbol(symbol, crr.left);

            }
            searchSymbol(symbol, crr.right);
        }
        return root;
    }

    public void printLevelOrder(Node root) {
        // Base Case 
        if (root == null) {
            return;
        }

        // Create an empty queue for level order tarversal 
        Queue<Node> q = new LinkedList<Node>();

        // Enqueue Root and initialize height 
        q.add(root);

        while (true) {

            // nodeCount (queue size) indicates number of nodes 
            // at current level. 
            int nodeCount = q.size();
            if (nodeCount == 0) {
                break;
            }

            // Dequeue all nodes of current level and Enqueue all 
            // nodes of next level 
            while (nodeCount > 0) {
                Node node = q.peek();
                System.out.print(node.symbol + " " + node.count + " ");
                q.remove();
                if (node.left != null) {
                    q.add(node.left);
                }
                if (node.right != null) {
                    q.add(node.right);
                }
                nodeCount--;
            }
            System.out.println();
        }
    }

    public int count(Node root, int c) {
        if (root == null) {
            return c;
        }
        c += count(root.right, c);
        c += count(root.left, c);
        return root.count;
    }

    public int leftLeavesSum(Node root) {
        // Initialize result 
        int res = 0;

        // Update result if root is not NULL 
        if (root != null) {
            // If left of root is NULL, then add key of 
            // left child 
            if (isLeaf(root.right)) {
                res += root.right.count;
            } else // Else recur for left child of root 
            {
                res += leftLeavesSum(root.right);
            }

            // Recur for right child of root and update res 
            res += leftLeavesSum(root.left);
        }

        // return result
        return res;
    }

    public boolean isLeaf(Node node) {
        if (node == null) {
            return false;
        }
        if (node.left == null && node.right == null) {
            return true;
        }
        return false;
    }

    public void swapCount(Node root) {
        int c = 0, x = 0;
        c = leftLeavesSum(root.right);
        if (c == 0) {
            c = count(root.right, c);
        }
        x = leftLeavesSum(root.left);
        if (x == 0) {
            x = count(root.left, x);
        }
        if (x > c) {
            Node temp = root.right;
            root.right = root.left;
            root.left = temp;
        }

    }

    public void swapParent(Node root, Node crr) {
        if ((root.left.symbol == crr.symbol) || (root.right.symbol == crr.symbol)) {
            return;
        }
        for (int i = 0; i < 1;) {
            if (root != null) {
                if (root.symbol == crr.symbol) {
                    i++;
                } else if (isLeaf(root.left)) {
                    if (root.left.symbol == crr.symbol) {
                        i++;
                    } else if (root.left.count < crr.count && root.left.count != 0) {
                        
                        Node temp = new Node();
                        temp.symbol = root.left.symbol;
                        temp.count = root.left.count;
                        root.left.symbol = crr.symbol;
                        root.left.count = crr.count;
                        crr.count = temp.count;
                        crr.symbol = temp.symbol;
                        i++;
                    } else {
                        root = root.right;
                    }
                } else if (isLeaf(root.right)) {
                    if (root.right.symbol == crr.symbol) {
                        i++;
                    } else if (root.right.count < crr.count && root.right.count != 0) {
                        Node temp = new Node();
                        temp.symbol = root.right.symbol;
                        temp.count = root.right.count;
                        root.right.symbol = crr.symbol;
                        root.right.count = crr.count;
                        crr.count = temp.count;
                        crr.symbol = temp.symbol;
                        i++;
                    } else {
                        root = root.left;
                    }
                } else if (isLeaf(root) && root.symbol != crr.symbol) {
                    if (root.count < crr.count && root.count != 0) {
                        Node temp = new Node();
                        temp.symbol = root.symbol;
                        temp.count = root.count;
                        root.symbol = crr.symbol;
                        root.count = crr.count;
                        crr.count = temp.count;
                        crr.symbol = temp.symbol;
                        i++;
                    }
                } else {
                    i++;
                }
            }
        }
    }

    public String getCode(Node root, char c) {
        String code = "";
        boolean bool = true;
        while (bool) {
            if (root != null) {
                if (root.left.symbol == c) {
                    code = code + "0";
                    bool = false;
                    root = null;
                } else if (root.right.symbol == c) {
                    code = code + "1";
                    bool = false;
                    root = null;
                } else if (root.left.symbol == 'y') {
                    code = code + "0";
                    root = root.left;
                } else if (root.right.symbol == 'y') {
                    code = code + "1";
                    root = root.right;
                } else {
                    bool = false;
                }

            } else {
                break;
            }
        }

        return code;
    }

    public char searchCode(Node root, ArrayList<Character> s) {
        for (int i = 0; i < s.size(); i++) {

            if (s.get(i) == '0') {
                root = root.left;
            } else if (s.get(i) == '1') {

                root = root.right;
            }
        }
        return root.symbol;
    }

}

public class Ahufman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("0-exit");
            System.out.println("1-Compression");
            System.out.println("2-DeCompression");
            int num = in.nextInt();
            in.nextLine();
            if(num==0){
                break;
            }
            else if (num == 1) {
                System.out.println("enter characters");
                ArrayList<Character> sym = new ArrayList<>();
                String s = in.nextLine();
                Node root = new Node();
                for (int i = 0; i < s.length(); i++) {
                    root.symbol = '0';
                    int y = 0;
                    boolean bool = true;
                    while (bool) {
                        for (int j = 0; j < sym.size(); j++) {

                            if (root.s.get(j).symbol == s.charAt(i)) {
                               
                                root.encoder.add(root.getCode(root, s.charAt(i)));
                                Node temp = root.searchSymbol(s.charAt(i), root);
                                if (root.left.symbol != root.s.get(j).symbol || root.right.symbol != root.s.get(j).symbol) {
                                    root.swapParent(root, root.s.get(j));
                                }
                                root.swapCount(root);

                                bool = false;
                                y++;
                                break;
                            }
                        }
                        if (i == 0) {
                            Node lCurrent = new Node();
                            Node rCurrent = new Node();
                            lCurrent.symbol = 'n';
                            rCurrent.symbol = s.charAt(i);
                            rCurrent.count++;
                            root.right = rCurrent;
                            root.s.add(rCurrent);
                            root.left = lCurrent;
                            root.right.left = null;
                            root.right.right = null;
                            root.left.left = null;
                            root.left.right = null;
                            sym.add(s.charAt(i));
                            if (s.charAt(i) == 'A') {
                                root.encoder.add("00");
                            } else if (s.charAt(i) == 'B') {
                                root.encoder.add("01");
                            } else if (s.charAt(i) == 'C') {
                                root.encoder.add("10");
                            }
                            bool = false;
                        } else if (y == 0) {
                            sym.add(s.charAt(i));
                            root.encoder.add(root.getCode(root, 'n'));
                            if (s.charAt(i) == 'A') {
                                root.encoder.add("00");
                            } else if (s.charAt(i) == 'B') {
                                root.encoder.add("01");
                            } else if (s.charAt(i) == 'C') {
                                root.encoder.add("10");
                            }
                            root.add(root, s.charAt(i));
//                    for (int j = 0; j < root.s.size(); j++) {
//                        if (root.s.get(j).symbol == s.charAt(i)) {
//                            root.swapParent(root, root.s.get(j));
//                        }
//                    }
                            root.swapCount(root);

                            bool = false;
                        }

                    }
                    root.printLevelOrder(root);
                }
                root.printLevelOrder(root);
                for (int i = 0; i < root.encoder.size(); i++) {
                    System.out.print(root.encoder.get(i));
                }
                System.out.println();
            } else if (num == 2) {
                System.out.println("enter characters ");
                String s = in.nextLine();
                Node root = new Node();
                ArrayList<Character> character = new ArrayList<>();
                for (int i = 0; i < s.length(); i++) {

                    if (i == 0) {
                        //String input = c[i] + c[i + 1] + "";
                        //System.out.println(input);
                        Node lCurrent = new Node();
                        Node rCurrent = new Node();
                        lCurrent.symbol = 'n';
//                        root.right.left = null;
//                        root.right.right = null;
//                        root.left.left = null;
//                        root.left.right = null;

                        if (s.charAt(i) == '0' && s.charAt(i + 1) == '0') {
                            rCurrent.symbol = 'A';
                            rCurrent.count++;
                            root.right = rCurrent;
                            root.s.add(rCurrent);
                            character.add('A');
                        } else if (s.charAt(i) == '0' && s.charAt(i + 1) == '1') {
                            rCurrent.symbol = 'B';
                            rCurrent.count++;
                            root.right = rCurrent;
                            root.s.add(rCurrent);
                            character.add('B');
                        } else if (s.charAt(i) == '1' && s.charAt(i + 1) == '0') {
                            rCurrent.symbol = 'C';
                            rCurrent.count++;
                            root.right = rCurrent;
                            root.s.add(rCurrent);
                        }
                        root.left = lCurrent;
                        i++;
                    } else {
                        boolean bool = true;
                        Node rCurrent = new Node();
                        ArrayList<Character> shortCode = new ArrayList<>();
                        while (bool) {
                            shortCode.add(s.charAt(i));
                            if (root.searchCode(root, shortCode) == 'n') {

                                character.add('n');
                                if (s.charAt(i + 1) == '0' && s.charAt(i + 2) == '0') {
                                    root.add(root, 'A');
                                    character.add('A');
                                    root.s.add(rCurrent);
                                } else if (s.charAt(i + 1) == '0' && s.charAt(i + 2) == '1') {
                                    root.add(root, 'B');
                                    character.add('B');
                                    root.s.add(rCurrent);
                                } else if (s.charAt(i + 1) == '1' && s.charAt(i + 2) == '0') {
                                    root.add(root, 'C');
                                    character.add('C');
                                    root.s.add(rCurrent);
                                }
                                root.swapCount(root);
                                i += 2;
                                bool = false;
                            } else if (root.searchCode(root, shortCode) == 'y') {
                                i++;
                            } else {
                                for(int j=0;j<shortCode.size();j++){
                                    System.out.print(shortCode.get(j));
                                }
                                System.out.println();
                                for (int j = 0; j < character.size(); j++) {
                                    if (root.s.get(j).symbol == root.searchCode(root, shortCode)) {
                                        System.out.println(root.s.get(j).symbol+"  mkm");
                                        character.add(root.s.get(j).symbol);
                                        root.searchSymbol(root.s.get(j).symbol, root);
                                        root.swapParent(root, root.s.get(j));
                                        root.swapCount(root);
                                        System.out.println(root.s.get(j).symbol);
                                        
                                        j = character.size();
                                    }
                                }

                                bool = false;

                            }

                        }
                        root.printLevelOrder(root);
                    }

                }
                for (int i = 0; i < character.size(); i++) {
                    System.out.print(character.get(i));
                }
                System.out.println();
            } else {
                System.out.println("you should enter only 1 or 2");
                Thread.sleep(1000);
            }
        }

    }
}
