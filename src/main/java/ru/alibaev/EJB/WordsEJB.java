package ru.alibaev.EJB;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.SelectableDataModel;
import ru.alibaev.entities.Words;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.*;
import java.util.List;

@Stateless
public class WordsEJB {



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
        em.merge(word);
    }

    public void removeWord(int id) {
        Query query = em.createQuery("DELETE FROM Words w WHERE w.id = :id");
        query.setParameter("id", id).executeUpdate();
    }

}
