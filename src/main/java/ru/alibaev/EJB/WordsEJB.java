package ru.alibaev.EJB;

import ru.alibaev.entities.Words;

import javax.ejb.*;
import javax.persistence.*;
import java.util.List;

@Stateless
public class WordsEJB {

    public Words selectedRow;
    public boolean toUpdate = false;

    @PersistenceContext(unitName = "postgresPU")
    EntityManager em;

    public List<Words> getActiveWords() {
        Query query = em.createQuery("from Words where status='ACTIVE'");
        return (List<Words>) query.getResultList();
    }

    public List<Words> getAllWords() {
        Query query = em.createQuery("from Words");
        return (List<Words>) query.getResultList();
    }

    public void createWord(Words word) {
        em.persist(word);
    }

    public void removeWord(int id) {
        Query query = em.createQuery("DELETE FROM Words w WHERE w.id = :id");
        query.setParameter("id", id).executeUpdate();
    }

    public void updateWord (Words word) {
        em.createQuery("UPDATE Words w SET w.word = :word, w.status = :status, w.length = :length WHERE w.id = :id")
        .setParameter("id", word.id)
        .setParameter("word", word.word)
        .setParameter("status", word.status)
        .setParameter("length", word.length).executeUpdate();
    }

    public Words getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(Words selectedRow) {
        this.selectedRow = selectedRow;
    }

}
