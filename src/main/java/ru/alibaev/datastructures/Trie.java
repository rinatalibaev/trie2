package ru.alibaev.datastructures;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

//@LocalBean
//@Stateful
public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.getChildren();

        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);

            TrieNode t;
            if(children.containsKey(c)){
                t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c, t);
            }

            children = t.getChildren();

            //set leaf node
            if(i==word.length()-1)
                t.setLeaf(true);
        }
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);

        if(t != null && t.isLeaf())
            return true;
        else
            return false;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if(searchNode(prefix) == null)
            return false;
        else
            return true;
    }

    public TrieNode searchNode(String str){
        Map<Character, TrieNode> children = root.getChildren();
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.getChildren();
            }else{
                return null;
            }
        }

        return t;
    }

    public Map <Character, TrieNode> findNodesByPrefix (String str) {
        Map<Character, TrieNode> children = root.getChildren();
        Map<Character, TrieNode> nodesByPrefix = new HashMap<>();
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                nodesByPrefix.put(c,t);
                children = t.getChildren();
            }else{
                return null;
            }
        }

        return nodesByPrefix;
    }
}
