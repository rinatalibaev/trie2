package ru.alibaev.datastructures;

import java.util.HashMap;

public class TrieNode {
    char c;
    private HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    private boolean isLeaf;

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public TrieNode() {}

    public TrieNode(char c){
        this.c = c;
    }


}
