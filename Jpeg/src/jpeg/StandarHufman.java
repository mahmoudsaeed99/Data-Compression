/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpeg;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Comparator;

class HuffmanNode {

    int data;
    String c;

    HuffmanNode left;
    HuffmanNode right;
}

class MyComparator implements Comparator<HuffmanNode> {

    public int compare(HuffmanNode x, HuffmanNode y) {
        if(y.data > x.data){
            return -1;
        }
        else{
            return 1;
        }
    }
}

/**
 *
 * @author mahmoudsaeed
 */
public class StandarHufman {

    void printCode(HuffmanNode root, String s , ArrayList b) {

        // base case; if the left and right are null 
        // then its a leaf node and we print 
        // the code s generated by traversing the tree. 
        if (root.left
                == null
                && root.right
                == null
                && root.c!="-") {

            // c is the character in the node 
            String out = root.c + ":" + s;
            b.add(out);
            return;
        }

        // if we go to left then add "0" to the code. 
        // if we go to the right add"1" to the code. 
        // recursive calls for left and 
        // right sub-tree of the generated tree. 
        printCode(root.left, s + "1" ,b);
        printCode(root.right, s + "0",b);
    }

    /**
     * @param args the command line arguments
     */
    ArrayList main(ArrayList<String>buffer) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        ArrayList<String>returnBuffer = new ArrayList<>();
        ArrayList<String> charArray = new ArrayList<>();
        ArrayList<Integer> charfreq = new ArrayList<>();
        for(int i=0;i<buffer.size();i++){
            int freq = 0;
            boolean check  = true;
            for(int j=i;j<buffer.size();j++){
                if(buffer.get(i).equals(buffer.get(j))){
                    freq++;
                }
            }
            for(int j=0;j<charArray.size();j++){
                if(charArray.get(j).equals(buffer.get(i))){
                    
                
                    check = false;
                }
            }
            if(check){
                charArray.add(buffer.get(i));
                charfreq.add(freq);
            }
            
        }
        // creating a priority queue q. 
        // makes a min-priority queue(min-heap). 
        PriorityQueue<HuffmanNode> q
                = new PriorityQueue<HuffmanNode>(buffer.size(), new MyComparator());

        for (int i = 0; i < buffer.size()/2; i++) {

            // creating a Huffman node object 
            // and add it to the priority queue. 
            HuffmanNode hn = new HuffmanNode();
//            System.out.println(charArray.get(i));
            hn.c = charArray.get(i);
            hn.data = charfreq.get(i);

            hn.left = null;
            hn.right = null;

            // add functions adds 
            // the huffman node to the queue. 
            q.add(hn);
        }

        // create a root node 
        HuffmanNode root = null;

        // Here we will extract the two minimum value 
        // from the heap each time until 
        // its size reduces to 1, extract until 
        // all the nodes are extracted. 
        while (q.size() > 1) {

            // first min extract. 
            HuffmanNode x = q.peek();
            q.poll();

            // second min extarct. 
            HuffmanNode y = q.peek();
            q.poll();

            // new node f which is equal 
            HuffmanNode f = new HuffmanNode();

            // to the sum of the frequency of the two nodes 
            // assigning values to the f node. 
            f.data = x.data + y.data;
            f.c = "-";

            // first extracted node as left child. 
            f.left = x;

            // second extracted node as the right child. 
            f.right = y;

            // marking the f node as the root node. 
            root = f;

            // add this node to the priority-queue. 
            q.add(f);
        }

        // print the codes by traversing the tree 
        printCode(root, "",returnBuffer);
        return returnBuffer;
    }
    

}
