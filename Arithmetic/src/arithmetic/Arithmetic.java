/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arithmetic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author mahmoudsaeed
 */
class charData {

    float probLow;
    float probHigh;
    float highChange;
    float lowChange;
    char c;
}

class Compress {
    String code;

    void compressInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("number of characters you will set");
        int number = in.nextInt();
        // array to save the char and prob 
        ArrayList<charData> probChar = new ArrayList<>();
        System.out.println("probabilities of Characters like");
        System.out.println("x' 'y");
        float countProbability = 0;
        for (int i = 0; i < number; i++) {
            charData cData = new charData();
            cData.c = in.next().charAt(0);
            float prob = in.nextFloat();
            cData.probLow = countProbability;
            cData.lowChange = countProbability;
            countProbability += prob;
            cData.probHigh = countProbability;
            cData.highChange = countProbability;
            probChar.add(cData);
        }
        String word;
        System.out.println("enter the word that you want to compress it");
        word = in.nextLine();
        compress(probChar, word);
        //in.nextLine();
    }

    void compress(ArrayList<charData> probChar, String word) {
        Scanner in = new Scanner(System.in);

        float lower = 0;
        float upper = 1;
        for (int i = 0; i < word.length() - 1; i++) {
            float range = upper - lower;
            char symbol = word.charAt(i);
            for (int j = 0; j < probChar.size(); j++) {
                if (symbol == probChar.get(j).c) {
                    System.out.println(symbol);
                    lower = probChar.get(j).lowChange;
                    upper = probChar.get(j).highChange;
                    System.out.println("lower : " + lower);
                    System.out.println("upper : " + upper);
                    float range2 = (upper - lower);
                    System.out.println("range : " + range2);
                    for (int k = 0; k < probChar.size(); k++) {
                        probChar.get(k).lowChange = probChar.get(j).lowChange + range2 * probChar.get(k).probLow;
                        probChar.get(k).highChange = probChar.get(j).lowChange + range2 * probChar.get(k).probHigh;
                    }
                }
            }
        }
        arithmeticJF jf= new arithmeticJF();
        
        for (int i = 0; i < probChar.size(); i++) {
            if (word.charAt(word.length() - 1) == probChar.get(i).c) {
                code = (probChar.get(i).highChange + probChar.get(i).lowChange) / 2+"";
                compressCode();
                System.out.println((probChar.get(i).highChange + probChar.get(i).lowChange) / 2);
            }
        }
    }
    String compressCode(){
        return code;
    }

    void compressFile() throws FileNotFoundException, IOException {
        File fin = new File("decompress.txt");
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader inFirst = new BufferedReader(new InputStreamReader(fis));
        String aLineFirst = null;
        ArrayList<charData> probChar = new ArrayList<>();
        float countProbability = 0;
        String word = null;
        while ((aLineFirst = inFirst.readLine()) != null) {
            //Process each line and add output to Dest.txt file
            String[] s = aLineFirst.split(" ");
            if (s.length > 1) {
                float f1 = Float.parseFloat(s[1]);
                charData cData = new charData();
                cData.c = s[0].charAt(0);
                float prob = f1;
                cData.probLow = countProbability;
                cData.lowChange = countProbability;
                countProbability += prob;
                cData.probHigh = countProbability;
                cData.highChange = countProbability;
                probChar.add(cData);
            } else {
                word = s[0];
            }
        }
        inFirst.close();
        compress(probChar, word);
    }

}

class Decompress {
      String code ="";
    void decompressInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("number of characters you will set");
        int number = in.nextInt();
        ArrayList<charData> probChar = new ArrayList<>();
        float countProbability = 0;
        for (int i = 0; i < number; i++) {
            charData cData = new charData();
            cData.c = in.next().charAt(0);
            float prob = in.nextFloat();
            cData.probLow = countProbability;
            cData.lowChange = countProbability;
            countProbability += prob;
            cData.probHigh = countProbability;
            cData.highChange = countProbability;
            probChar.add(cData);

        }
        for(int i=0;i<probChar.size();i++){
            System.out.println(probChar.get(i).c);
        }
        System.out.println("enter compress code");
        float compress = in.nextFloat();
        decompress(probChar , compress);
    }

    void decompress(ArrayList<charData> probChar , float compress) {
        Scanner in = new Scanner(System.in);
        float lower = 0;
        float upper = 1;
        ArrayList<Character> compressChar = new ArrayList<>();
        for (int i = 0; i <= probChar.size(); i++) {
            float range = upper - lower;
            for (int j = 0; j < probChar.size(); j++) {
                if (probChar.get(j).lowChange < compress && compress < probChar.get(j).highChange) {
                    compressChar.add(probChar.get(j).c);
                    lower = probChar.get(j).lowChange;
                    upper = probChar.get(j).highChange;
                    System.out.println("lower : " + lower);
                    System.out.println("upper : " + upper);
                    float range2 = (upper - lower);
                    System.out.println("range : " + range2);
                    for (int k = 0; k < probChar.size(); k++) {
                        probChar.get(k).lowChange = probChar.get(j).lowChange + range2 * probChar.get(k).probLow;
                        probChar.get(k).highChange = probChar.get(j).lowChange + range2 * probChar.get(k).probHigh;
                    }
                    j = probChar.size();
                }
            }
        }
        for (int i = 0; i < compressChar.size(); i++) {
          
            System.out.print(compressChar.get(i));
            code+=compressChar.get(i)+"";
        }

    }
    String decompressCode(){
        return code;
    }

    void decompressFile() throws FileNotFoundException, IOException{
        File fin = new File("compress.txt");
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader inFirst = new BufferedReader(new InputStreamReader(fis));
        String aLineFirst = null;
        ArrayList<charData> probChar = new ArrayList<>();
        float countProbability = 0;
        float compress = 0 ;
        while ((aLineFirst = inFirst.readLine()) != null) {
            //Process each line and add output to Dest.txt file
            String[] s = aLineFirst.split(" ");
            if (s.length > 1) {
                float f1 = Float.parseFloat(s[1]);
                charData cData = new charData();
                cData.c = s[0].charAt(0);
                float prob = f1;
                cData.probLow = countProbability;
                cData.lowChange = countProbability;
                countProbability += prob;
                cData.probHigh = countProbability;
                cData.highChange = countProbability;
                probChar.add(cData);
            } else {
                float f1 = Float.parseFloat(s[0]);
                compress = f1;
            }
        }
        inFirst.close();
        decompress(probChar, compress);
    }
}

public class Arithmetic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        arithmeticJF  jf= new arithmeticJF();
        Decompress decompress = new Decompress();
        decompress.decompressInput();
//        boolean bool = true;
//        while (bool) {
//            System.out.println("1-compress");
//            System.out.println("2-decompress");
//            System.out.println("0-end");
//            int x = in.nextInt();
//            if (x == 1) {
//                Compress compress = new Compress();
//                compress.compressFile();
//
//            } else if (x == 2) {
//                Decompress decompress = new Decompress();
//                decompress.decompressFile();
//
//            } else {
//                bool = false;
//            }
//        }
    }
}
