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

        Quiz quiz = createQuiz();

        assertTrue(persistInATransaction(quiz));
    }

    @Test
    public void testQuizWithSubcategory() {

        Category category = createCategory("Test category");
        SubCategory subCategory = createSubCategory("Test subcategory", category);
        Quiz quiz = createQuiz(subCategory);

        assertTrue(persistInATransaction(quiz,category,subCategory));
    }

    @Test
    public void testQueries() {

        Category JEE = createCategory("JEE");

        SubCategory JPA = createSubCategory("JPA", JEE);
        SubCategory EJB = createSubCategory("EJB", JEE);
        SubCategory JSF = createSubCategory("JSF", JEE);

        assertTrue(persistInATransaction(JEE, JPA, EJB, JSF));

        Quiz quiz1 = createQuiz(JPA);
        Quiz quiz2 = createQuiz(JPA);
        Quiz quiz3 = createQuiz(EJB);
        Quiz quiz4 = createQuiz(JSF);

        assertTrue(persistInATransaction(quiz1, quiz2, quiz3, quiz4));

        TypedQuery<Quiz> query = em.createQuery("Select q from Quiz q where q.subcategory.name = 'JPA'", Quiz.class);
        List<Quiz> quizzes = query.getResultList();
        assertEquals(2, quizzes.size());

        TypedQuery<Quiz> query2 = em.createQuery("Select q from Quiz q where q.subcategory.category.name = 'JEE'", Quiz.class);
        List<Quiz> quizzes2 = query2.getResultList();
        assertEquals(4, quizzes2.size());
    }


    // help methods

    private SubCategory createSubCategory(String name, Category category) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(name);
        subCategory.setCategory(category);

        return subCategory;
    }

    private Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);

        return category;
    }

    private Quiz createQuiz() {
        return createQuiz(null);
    }

    private Quiz createQuiz(SubCategory subcategory) {
        Quiz quiz = new Quiz();

        // Premade quiz for all createQuiz, can (and should) be edited to make quiz with parameters or something
        quiz.setQuestion("WoW real main class?");
        quiz.setAnswer1("Priest");
        quiz.setAnswer2("Paladin");
        quiz.setAnswer3("Monk");
        quiz.setAnswer4("Shaman");
        quiz.setCorrectAnswerIndex(1);
        quiz.setSubcategory(subcategory);

        return quiz;
    }
}