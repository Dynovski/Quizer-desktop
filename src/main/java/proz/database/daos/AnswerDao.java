package proz.database.daos;

import com.j256.ormlite.stmt.QueryBuilder;
import proz.database.models.Answer;
import proz.utils.exceptions.ApplicationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AnswerDao extends CommonDao
{
    public AnswerDao()
    {
        super();
    }

    public List<Answer> queryForAnswersFromQuestion(int questionId) throws ApplicationException
    {
        try {
            QueryBuilder<Answer, Object> queryBuilder = this.getQueryBuilder(Answer.class);
            return queryBuilder.where().eq("QUESTION_ID", questionId).query();
        } catch (SQLException e) {
            throw new ApplicationException("Query for answers from question error");
        } finally {
            try {
                this.connectionSource.close();
            } catch (IOException e) {
                throw new ApplicationException("Close connection error");
            }
        }
    }

    public List<Answer> queryForAnswersFromTest(int testId) throws ApplicationException
    {
        try {
            QueryBuilder<Answer, Object> queryBuilder = this.getQueryBuilder(Answer.class);
            QuestionDao questionDao = new QuestionDao();
            return queryBuilder.where().in("QUESTION_ID",
                    questionDao.queryForQuestionsFromTest(testId)).query();
        } catch (SQLException e) {
            throw new ApplicationException("Query for answers from test error");
        } finally {
            try {
                this.connectionSource.close();
            } catch (IOException e) {
                throw new ApplicationException("Close connection error");
            }
        }
    }

    public void deleteAnswersFromQuestion(int questionId) throws ApplicationException
    {
        try {
            this.getDao(Answer.class).executeRaw("delete from ANSWERS where QUESTION_ID = " + questionId);
        } catch (SQLException e) {
            throw new ApplicationException("Delete answers from question error");
        } finally {
            try {
                this.connectionSource.close();
            } catch (IOException e) {
                throw new ApplicationException("Close connection error when deleting answers from question: " + questionId);
            }
        }
    }
}
