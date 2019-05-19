import org.primefaces.event.SelectEvent;
import ru.alibaev.EJB.WordsEJB;
import ru.alibaev.datastructures.Trie2;
import ru.alibaev.entities.Words;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean(name="basicView")
public class BasicView {

    private String text;
    private String text2;
    private String status;
    private String searchText;
    private Words selectedRow;
    private List<Words> filteredWords;

    @Inject Trie2 trie2;
    @Inject WordsEJB wordsEJB;

    public BasicView () {
    }

    public void addWord() {
        Words newWord = new Words();
        newWord.setStatus(status);
        newWord.setWord(text2);
        newWord.setLength(text2.length());
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

    public void removeWord(Words word) {
        wordsEJB.removeWord(word.id);
    }

    public void updateRow() {
//        wordsEJB.updateWord(selectedRow);
    }

    public Words getSelectedRow() {
        if (this.wordsEJB.toUpdate == true) {
            this.wordsEJB.updateWord(this.wordsEJB.selectedRow);
            this.wordsEJB.toUpdate = false;
        }
        return selectedRow;
    }

    public void setSelectedRow(Words selectedRow) {
        this.selectedRow = selectedRow;
    }

    public Words getWordEJBSelectedRow() {
        return wordsEJB.selectedRow;
    }

    public void setWordEJBSelectedRow(Words selectedRow) {
        this.wordsEJB.toUpdate = true;
        this.wordsEJB.selectedRow = selectedRow;
    }

    public void onRowSelect(SelectEvent event) {
        selectedRow = (Words) event.getObject();
    }

    // Getters and setters

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

    public WordsEJB getWordsEJB() {
        return wordsEJB;
    }

    public void setWordsEJB(WordsEJB wordsEJB) {
        this.wordsEJB = wordsEJB;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearchText() {
        return searchText;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    //    public void setRow(SelectEvent eventedRow) {
    //        System.out.println("SelectEvent eventedRow: " + eventedRow);
    //        this.selectedRow = (Words) eventedRow.getObject();
    //    }
}


