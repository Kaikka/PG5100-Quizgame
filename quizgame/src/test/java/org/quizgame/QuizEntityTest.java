package org.quizgame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class QuizEntityTest {

    private EntityManagerFactory factory;
    private EntityManager em;

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

    private boolean persistInATransaction(Object... obj) {
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

    @Test
    public void testQuiz() {

        Quiz quiz = new Quiz();
        quiz.setQuestion("WoW real main class?");
        quiz.setAnswer1("Priest");
        quiz.setAnswer2("Paladin");
        quiz.setAnswer3("Monk");
        quiz.setAnswer4("Shaman");
        quiz.setCorrectAnswerIndex(1);

        //EntityManagerFactory factory = Persistence.createEntityManagerFactory("DB");
        //EntityManager em = factory.createEntityManager();

        /*EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.persist(quiz);
            tx.commit();
        } catch (Exception e) {
            System.out.println("ERROR");
            tx.rollback();
        } finally {
            em.close();
            factory.close();
        }*/
        persistInATransaction(quiz);
    }

    @Test
    public void testQuizWithSubcategory() {

        Category category = new Category();
        category.setName("Test category");
        SubCategory subcategory = new SubCategory();
        subcategory.setName("Test Subcategory");

        category.setSubcategory(subcategory);

        Quiz quiz = new Quiz();
        quiz.setQuestion("WoW real main class?");
        quiz.setAnswer1("Priest");
        quiz.setAnswer2("Paladin");
        quiz.setAnswer3("Monk");
        quiz.setAnswer4("Shaman");
        quiz.setCorrectAnswerIndex(1);
        quiz.setSubcategory(subcategory);

        assertTrue(persistInATransaction(quiz,category,subcategory));

    }

    @Test
    public void testQueries() {

        Category category = new Category();
        category.setName("JEE");

        SubCategory JPA = new SubCategory();
        JPA.setName("JPA");
        SubCategory EJB = new SubCategory();
        EJB.setName("EJB");
        SubCategory JSF = new SubCategory();
        JSF.setName("JSF");

        category.setSubcategory(JPA);
        category.setSubcategory(EJB);
        category.setSubcategory(JSF);
        JPA.setCategory(category);
        EJB.setCategory(category);
        JSF.setCategory(category);

        Quiz quiz1 = new Quiz();
        quiz1.setQuestion("WoW real main class?");
        quiz1.setAnswer1("Priest");
        quiz1.setAnswer2("Paladin");
        quiz1.setAnswer3("Monk");
        quiz1.setAnswer4("Shaman");
        quiz1.setCorrectAnswerIndex(1);

        Quiz quiz2 = new Quiz();
        quiz2.setQuestion("WoW real main class?");
        quiz2.setAnswer1("Priest");
        quiz2.setAnswer2("Paladin");
        quiz2.setAnswer3("Monk");
        quiz2.setAnswer4("Shaman");
        quiz2.setCorrectAnswerIndex(1);

        Quiz quiz3 = new Quiz();
        quiz3.setQuestion("WoW real main class?");
        quiz3.setAnswer1("Priest");
        quiz3.setAnswer2("Paladin");
        quiz3.setAnswer3("Monk");
        quiz3.setAnswer4("Shaman");
        quiz3.setCorrectAnswerIndex(1);

        Quiz quiz4 = new Quiz();
        quiz4.setQuestion("WoW real main class?");
        quiz4.setAnswer1("Priest");
        quiz4.setAnswer2("Paladin");
        quiz4.setAnswer3("Monk");
        quiz4.setAnswer4("Shaman");
        quiz4.setCorrectAnswerIndex(1);

        quiz1.setSubcategory(JPA);
        quiz2.setSubcategory(JPA);
        quiz3.setSubcategory(EJB);
        quiz4.setSubcategory(JSF);

        persistInATransaction(category, quiz1, quiz2, quiz3, quiz4, JPA, EJB, JSF);

        TypedQuery<Quiz> query = em.createQuery("Select q from Quiz q where q.subcategory.name = 'JPA'", Quiz.class);
        List<Quiz> quizzes = query.getResultList();

        System.out.println(category.name);
        System.out.println(JPA.name);

        assertEquals(2, quizzes.size());

        TypedQuery<Quiz> query2 = em.createQuery("Select q from Quiz q where q.subcategory.category.name = 'JEE'", Quiz.class);
        List<Quiz> quizzes2 = query2.getResultList();
        assertEquals(4, quizzes2.size());

    }
}