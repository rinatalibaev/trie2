import org.primefaces.event.SelectEvent;
import ru.alibaev.EJB.WordsEJB;
import ru.alibaev.datastructures.Trie2;
import ru.alibaev.datastructures.TrieInterface;
import ru.alibaev.datastructures.TrieNode;
import ru.alibaev.entities.Words;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ManagedBean(name="basicView")
public class BasicView {

    private String text;
    private String status;
    private String searchText;

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    private Words selectedRow;


    public Words getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(Words selectedRow) {
        this.selectedRow = selectedRow;
    }

    public void setRow(SelectEvent eventedRow) {
        System.out.println("SelectEvent eventedRow: " + eventedRow);
        this.selectedRow = (Words) eventedRow.getObject();
    }

    public void onRowSelect(SelectEvent event) {
        selectedRow = (Words) event.getObject();
        int i = 1 + 1;
        wordsEJB.removeWord(selectedRow.id);
    }


    private List<Words> filteredWords;
    @Inject
    Trie2 trie2;
    @Inject
    WordsEJB wordsEJB;

    boolean isPresent = false;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BasicView () {

    }

    public List<Words> getFilteredWords() {
        return filteredWords;
    }

    public void setFilteredWords(List<Words> filteredWords) {
        this.filteredWords = filteredWords;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public void addWord() {
        Words newWord = new Words();
        newWord.setStatus(status);
        newWord.setWord(text);
        newWord.setLength(text.length());
        wordsEJB.createWord(newWord);
    }

    public List<String> completeText(String query) {
        final String regex = "[А-яЁё]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(query);
        if (!matcher.matches()) {
            System.out.println("Only Russian");
            return null;
        }

        List<String> resultList = trie2.getWordsByPrefix(query);
        for (String s : resultList) {
            System.out.println(s);
        }
        return resultList;
    }

    public List<Words> getWords () {
        return wordsEJB.getAllWords();
    }

    public void removeWord() {
//        wordsEJB.removeWord(selectedRow.id);
    }
}


