package org.quizgame.ejb;

import org.quizgame.entity.Category;
import org.quizgame.entity.Quiz;
import org.quizgame.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ResetEjb {

    @PersistenceContext
    private EntityManager em;

    public void deleteDb(){
        deleteEntities(Quiz.class);
        deleteEntities(SubCategory.class);
        deleteEntities(Category.class);
    }

    private void deleteEntities(Class<?> entity) {
        Query q = em.createQuery("DELETE FROM " + entity.getSimpleName());
        q.executeUpdate();
    }
}
