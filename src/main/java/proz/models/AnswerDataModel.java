package proz.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proz.database.daos.AnswerDao;
import proz.database.models.Answer;
import proz.database.models.Question;
import proz.utils.converters.AnswerConverter;
import proz.utils.exceptions.ApplicationException;

import java.util.Collection;
import java.util.List;

public class AnswerDataModel
{
    private static ObservableList<AnswerFxModel> answersFromQuestion = FXCollections.observableArrayList();
    private static ObservableList<AnswerFxModel> answersFromTest = FXCollections.observableArrayList();
    private static AnswerDao answerDao = new AnswerDao();

    private AnswerDataModel() {}

    public static void saveAnswerInDataBase(String answer, boolean isCorrect, Question question) throws ApplicationException
    {
        answerDao.createOrUpdate(new Answer(answer, isCorrect, question));
    }

    public static void saveManyAnswersInDataBase(Collection<Answer> answers) throws ApplicationException
    {
        answerDao.createMany(answers);
    }

    private static void populateAnswersFromQuestion(List<Answer> answerList)
    {
        answersFromQuestion.clear();
        answerList.forEach(answer -> {
            AnswerFxModel answerFx = AnswerConverter.answerToAnswerFx(answer);
            answersFromQuestion.add(answerFx);
        });
    }

    public static void getAnswersFromQuestion(int questionId) throws ApplicationException
    {
        List<Answer> answers = answerDao.queryForAnswersFromQuestion(questionId);
        populateAnswersFromQuestion(answers);
    }

    private static void populateAnswersFromTest(List<Answer> answerList)
    {
        answersFromTest.clear();
        answerList.forEach(answer -> {
            AnswerFxModel answerFx = AnswerConverter.answerToAnswerFx(answer);
            answersFromTest.add(answerFx);
        });
    }

    public static void getAnswersFromTest(int testId) throws ApplicationException
    {
        List<Answer> answers = answerDao.queryForAnswersFromTest(testId);
        populateAnswersFromTest(answers);
    }

    public static void updateAnswersInDataBase() throws ApplicationException
    {
        for(AnswerFxModel answer : answersFromQuestion)
            answerDao.update(AnswerConverter.answerFxToAnswer(answer));
    }

    public static ObservableList<AnswerFxModel> getAnswersFromQuestion()
    {
        return answersFromQuestion;
    }

    public static ObservableList<AnswerFxModel> getAnswersFromTest()
    {

        return answersFromTest;
    }

    public static AnswerDao getAnswerDao()
    {
        return answerDao;
    }
}
