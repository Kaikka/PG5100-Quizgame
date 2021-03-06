package org.quizgame.ejb;

import org.quizgame.entity.Quiz;
import org.quizgame.entity.SubCategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class QuizEjb {

    @PersistenceContext
    private EntityManager em;

    public long createQuiz(long subCategoryId, String question, String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer, int indexOfCorrectAnswer) {

        Quiz quiz = new Quiz();

        quiz.setQuestion(question);
        quiz.setAnswer1(firstAnswer);
        quiz.setAnswer2(secondAnswer);
        quiz.setAnswer3(thirdAnswer);
        quiz.setAnswer4(fourthAnswer);
        quiz.setCorrectAnswerIndex(indexOfCorrectAnswer);
        quiz.setSubcategory(em.find(SubCategory.class, subCategoryId));

        em.persist(quiz);

        return quiz.getId();
    }
}
