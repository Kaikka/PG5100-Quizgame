package org.quizgame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.quizgame.entity.Category;
import org.quizgame.entity.Quiz;
import org.quizgame.entity.SubCategory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class EntityTestBase {

    private EntityManagerFactory factory;
    protected EntityManager em;

    @BeforeEach
    public void init() {
        factory = Persistence.createEntityManagerFactory("DB");
        em = factory.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        em.close();
        factory.close();
    }

    public boolean persistInATransaction(Object... obj) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for(Object o : obj) {
                em.persist(o);
            }
            tx.commit();
        } catch (Exception e) {
            System.out.println("FAILED TRANSACTION: " + e.toString());
            tx.rollback();
            return false;
        }
        return true;
    }

    // help methods

    protected SubCategory createSubCategory(String name, Category category) {

        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);

        category.getSubcategories().add(subCategory); // is this really needed?
        subCategory.setCategory(category);

        return subCategory;
    }

    protected Category createCategory(String name) {

        Category category = new Category();
        category.setName(name);

        return category;
    }

    /**
     *  Using this method will lead to constraints always failing
     */
    protected Quiz createQuiz() {
        return createQuiz("Question", null);
    }

    protected Quiz createQuiz(String question, SubCategory subcategory) {
        Quiz quiz = new Quiz();

        quiz.setQuestion(question);
        quiz.setAnswer1("A");
        quiz.setAnswer2("B");
        quiz.setAnswer3("C");
        quiz.setAnswer4("D");
        quiz.setCorrectAnswerIndex(1);
        quiz.setSubcategory(subcategory);

        return quiz;
    }
}
