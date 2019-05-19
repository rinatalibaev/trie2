package ru.alibaev.datastructures;

import ru.alibaev.EJB.WordsEJB;
import ru.alibaev.entities.Words;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ManagedBean
@ApplicationScoped
@Singleton
@Startup
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class Trie2 implements TrieInterface {

    private HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();

    public TrieNode getRoot() {
        return root;
    }

    private TrieNode root;
    @Inject
    WordsEJB wordsEJB;

//    @Lock(LockType.WRITE)
    @PostConstruct
    public void initialize() {
        root = new TrieNode();
        children = root.getChildren();
        List<String> words = wordsEJB.getActiveWords().stream().map(Words::getWord).collect(Collectors.toList());
        for(String s : words) {
            insertWord(s);
        }
    }

    // Inserts a word into the trie.
    @Override
    public void insertWord(String word) {

        HashMap<Character, TrieNode> localChildren = new HashMap<>();
        localChildren = (HashMap<Character, TrieNode>) children.clone();
        boolean rootPlace = true;
        boolean firstPlace = false;
        HashMap<Character, TrieNode> tempChildren = null;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);

            TrieNode t;
            TrieNode t1;
            if(localChildren.containsKey(c)){
                t = localChildren.get(c);
                t1 = children.get(c);
                if (rootPlace) {
                    tempChildren = children.get(c).getChildren();
                    rootPlace = false;
                } else {
                    tempChildren = tempChildren.get(c).getChildren();
                }
            }else{
                t = new TrieNode(c);
                if (rootPlace) {
                    children.put(c, t);
                    rootPlace = false;
                    firstPlace = true;
                    tempChildren = children.get(c).getChildren();
                } else {
                    tempChildren.put(c,t);
                    tempChildren = tempChildren.get(c).getChildren();
                }
            }

            localChildren = t.getChildren();

            //set leaf node
            if(i==word.length()-1)
                t.setLeaf(true);
        }
    }


//    @Lock(LockType.WRITE)
//    public void setStates(String country, List<String> states) {
//        countryStatesMap.put(country, states);
//    }

    @Override
    @Lock(LockType.READ)
    public List<String> getWordsByPrefix(String prefix) {
        initialize();
        HashMap<Character, TrieNode> localChildren = new HashMap<>();
        localChildren = (HashMap<Character, TrieNode>) children.clone();
        TrieNode t = null;
        boolean prefixIsPresent = false;
        List<String> words = new ArrayList<>();
        for(int i=0; i<prefix.length(); i++){
            char c = prefix.charAt(i);

            if(localChildren.containsKey(c)){
                t = localChildren.get(c);
                localChildren = t.getChildren();
            }else{
                return new ArrayList<String>();
            }
        }
        if (t.isLeaf()) {
            words.add(prefix);
        }
        if (t != null && localChildren != null) {
            words.addAll(getWordsByTrieNode(t,prefix));
            return words;
        }
        return null;
    }


    public List<String> getWordsByTrieNode (TrieNode trieNode, String tempWord) {
        List<String> wordsByPrefix = new ArrayList<>();
        Set<Map.Entry<Character, TrieNode>> localChildrenSet = trieNode.getChildren().entrySet();

        for (Map.Entry<Character, TrieNode> entry : localChildrenSet) {
            if(entry.getValue().isLeaf()) {
                wordsByPrefix.add(tempWord + entry.getKey());
            } else {
                wordsByPrefix.addAll(getWordsByTrieNode(entry.getValue(),tempWord + entry.getKey()));
            }
        }
        return wordsByPrefix;
    }
}
