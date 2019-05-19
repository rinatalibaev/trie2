package ru.alibaev.datastructures;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TrieInterface {

    public List<String> getWordsByPrefix(String prefix);
    public void insertWord(String word);
    public List<String> getWordsByTrieNode (TrieNode trieNode, String tempWord);

}
