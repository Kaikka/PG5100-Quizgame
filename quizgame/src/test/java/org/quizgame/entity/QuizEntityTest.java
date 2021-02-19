package org.quizgame.entity;

import org.junit.jupiter.api.Test;

import javax.persistence.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class QuizEntityTest extends EntityTestBase {

    @Test
    public void testQuiz() {

        Quiz quiz = createQuiz();

        assertFalse(persistInATransaction(quiz));
    }

    @Test
    public void testQuizWithSubcategory() {

        Category category = createCategory("Test category");
        SubCategory subCategory = createSubCategory("Test subcategory", category);
        //category.getSubcategories().add(subCategory); // is this really needed?
        Quiz quiz = createQuiz("Test quiz", subCategory);

        // Quiz has @NotNull for subcategory, so it depends on subCategory being added first
        assertTrue(persistInATransaction(category,subCategory,quiz));
    }

    @Test
    public void testQueries() {

        Category JEE = createCategory("JEE");

        SubCategory JPA = createSubCategory("JPA", JEE);
        SubCategory EJB = createSubCategory("EJB", JEE);
        SubCategory JSF = createSubCategory("JSF", JEE);

        assertTrue(persistInATransaction(JEE, JPA, EJB, JSF));

        Quiz quiz1 = createQuiz("JPA", JPA);
        Quiz quiz2 = createQuiz("JPA2", JPA);
        Quiz quiz3 = createQuiz("EJB", EJB);
        Quiz quiz4 = createQuiz("JSF", JSF);

        assertTrue(persistInATransaction(quiz1, quiz2, quiz3, quiz4));

        TypedQuery<Quiz> query = em.createQuery("Select q from Quiz q where q.subcategory.name = 'JPA'", Quiz.class);
        List<Quiz> quizzes = query.getResultList();
        assertEquals(2, quizzes.size());

        TypedQuery<Quiz> query2 = em.createQuery("Select q from Quiz q where q.subcategory.category.name = 'JEE'", Quiz.class);
        List<Quiz> quizzes2 = query2.getResultList();
        assertEquals(4, quizzes2.size());
    }
}