package org.quizgame;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class QuizEntityTest {

    @Test
    public void testQuiz() {

        Quiz quiz = new Quiz();
        quiz.setQuestion("WoW real main class?");
        quiz.setAnswer1("Priest");
        quiz.setAnswer2("Paladin");
        quiz.setAnswer3("Monk");
        quiz.setAnswer4("Shaman");
        quiz.setCorrectAnswerIndex(1);

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("DB");
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
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
        }
    }
}