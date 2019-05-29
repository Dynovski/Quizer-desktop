package proz.models;

import javafx.beans.property.*;
import java.util.Date;

public class ResultFxModel
{
    private IntegerProperty resultId = new SimpleIntegerProperty();
    private ObjectProperty<Date> date = new SimpleObjectProperty<>();
    private IntegerProperty score = new SimpleIntegerProperty();
    private ObjectProperty<UserFxModel> user = new SimpleObjectProperty<>();
    private ObjectProperty<TestFxModel> test = new SimpleObjectProperty<>();

    public int getResultId()
    {
        return resultId.get();
    }

    public IntegerProperty resultIdProperty()
    {
        return resultId;
    }

    public void setResultId(int resultId)
    {
        this.resultId.set(resultId);
    }

    public Date getDate()
    {
        return date.get();
    }

    public ObjectProperty<Date> dateProperty()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date.set(date);
    }

    public int getScore()
    {
        return score.get();
    }

    public IntegerProperty scoreProperty()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score.set(score);
    }

    public UserFxModel getUser()
    {
        return user.get();
    }

    public ObjectProperty<UserFxModel> userProperty()
    {
        return user;
    }

    public void setUser(UserFxModel user)
    {
        this.user.set(user);
    }

    public TestFxModel getTest()
    {
        return test.get();
    }

    public ObjectProperty<TestFxModel> testProperty()
    {
        return test;
    }

    public void setTest(TestFxModel test)
    {
        this.test.set(test);
    }

    @Override
    public String toString()
    {
        return "ResultFxModel{" +
                "resultId=" + resultId.get() +
                ", date=" + date.get() +
                ", score=" + score.get() +
                ", user=" + user.get().getUsername() +
                ", test=" + test.get().getTestName() +
                '}';
    }
}
