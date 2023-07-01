package Methods;

import Structures.StringFreq;
import Structures.Trie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.min;

public class Functions {
    public static void addToTrie(Trie r, String word) {
        for (int i = 0; i < word.length(); i++) {
            int index = (int) word.charAt(i) - 'a';
            if (r.children.get(index) == null) {
                r.children.set(index, new Trie());
            }
            r.tChild += 1;
            r.children.get(word.charAt(i) - 'a').val = word.charAt(i);
            r.children.get(word.charAt(i) - 'a').parent = r;
            r = r.children.get(word.charAt(i) - 'a');
        }
        r.isValid = true;
    }

    public static Trie read() {
        ArrayList<String> words = new ArrayList<>();
        Trie root = new Trie();

        try {
            File allWord = new File("D:\\semester 4\\DS\\FinalProject\\smart-keyboard\\Smart Keyboard" +
                    "\\src\\words.txt");
            Scanner fScanner = new Scanner(allWord);
            while (fScanner.hasNext()) {
                words.add(fScanner.next().toLowerCase().replace("-", ""));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found !");
            return null;
        }
        if (!words.isEmpty()) {
            for (String string :
                    words) {
                addToTrie(root, string);
            }
        } else {
            System.out.println("empty list");
        }
        root.print();
        return root;
    }

    public static Trie spellCheck(Trie mainTrie, String textFiled) {
        Trie root = mainTrie;

        for (int level = 0; level < textFiled.length(); level++) {

            int index = textFiled.charAt(level) - 'a';

            if (root.children.get(index) == null){
                return mainTrie;
            }

            root = root.children.get(index);
        }

        return root;
    }

    public static Trie findRoot(Trie mainTrie, String textFiled) {
        Trie root = mainTrie;

        for (int level = 0; level < textFiled.length(); level++) {

            int index = textFiled.charAt(level) - 'a';

            if (root.children.get(index) == null) {
                return root;
            }

            root = root.children.get(index);
        }

        return root;
    }

    public static ArrayList<String> AutoComplete(Trie endNode,String textField){
        ArrayList<StringFreq> stringFreqs = endNode.getAllChildren();

        ArrayList<String> result = new ArrayList<>();
        int maxAdd = 5;

        if (endNode.isValid){
            StringFreq nl= new StringFreq();
            nl.word = "";
            nl.frequency = -1;
            StringFreq selfWord = endNode.getWord(nl,false);
            result.add(selfWord.word);
            maxAdd = maxAdd - 1;
        }
        if (stringFreqs.size() < maxAdd)
            maxAdd = stringFreqs.size();

        for (int i = 0; i < maxAdd ; i++){
            int minFreq = 0;
            StringFreq max = new StringFreq();
            max.frequency = -1;
            for (int j = stringFreqs.size()-1; j > -1  ; j--) {
                if (stringFreqs.get(j).frequency >= minFreq && !result.contains(stringFreqs.get(j).word)){
                    max = stringFreqs.get(j);
                    minFreq = stringFreqs.get(j).frequency;
                }
            }
            if (max.frequency != -1)
                result.add(max.word);
        }
        return result;
    }

}
