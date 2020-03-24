package proz.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proz.database.daos.ResultDao;
import proz.database.daos.TestDao;
import proz.database.daos.UserDao;
import proz.database.models.Result;
import proz.database.models.Test;
import proz.database.models.User;
import proz.utils.converters.ResultConverter;
import proz.utils.converters.TestConverter;
import proz.utils.converters.UserConverter;
import proz.utils.exceptions.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ResultDataModel
{
    private static ObservableList<ResultFxModel> results = FXCollections.observableArrayList();
    private static ObjectProperty<ResultFxModel> result = new SimpleObjectProperty<>();
    private static ObservableList<UserFxModel> users = FXCollections.observableArrayList();
    private static ObjectProperty<UserFxModel> user = new SimpleObjectProperty<>();
    private static ObservableList<TestFxModel> tests = FXCollections.observableArrayList();
    private static ObjectProperty<TestFxModel> test = new SimpleObjectProperty<>();
    private static List<ResultFxModel> resultFxModelList = new ArrayList<>();
    private static ResultDao resultDao = new ResultDao();

    public ResultDataModel() {}

    private static void populateResults(List<Result> resultList)
    {
        results.clear();
        resultFxModelList.clear();
        resultList.forEach(result -> {
            ResultFxModel resultFx = ResultConverter.resultToResultFx(result);
            resultFxModelList.add(resultFx);
        });
        getResults().setAll(resultFxModelList);
    }

    public static void getResultsFromDataBase() throws ApplicationException
    {
        List<Result> resultsList = resultDao.queryForAll(Result.class);
        populateResults(resultsList);
    }

    public static void getUsersFromDataBase() throws ApplicationException
    {
        List<User> userList = null;
        UserDao userDao = new UserDao();
        userList = userDao.queryForAll(User.class);
        populateUsers(userList);
    }

    public static void getTestsFromDataBase() throws ApplicationException
    {
        List<Test> testList = null;
        TestDao testDao = new TestDao();
        testList = testDao.queryForAll(Test.class);
        populateTests(testList);
    }

    public static void saveResultInDataBase(int score, Test test, User user) throws ApplicationException
    {
        resultDao.createOrUpdate(new Result(score, test, user));
    }

    private Predicate<ResultFxModel> predicateTest(){
        return resultFxModel -> resultFxModel.getTest().getTestId() == getTest().getTestId();
    }

    private Predicate<ResultFxModel> predicateUser() {
        return resultFxModel -> resultFxModel.getUser().getUserId() == getUser().getUserId();
    }

    private void filterPredicate(Predicate<ResultFxModel> predicate) {
        List<ResultFxModel> newList = resultFxModelList.stream().filter(predicate).collect(Collectors.toList());
        results.setAll(newList);
    }

    public void filterResultList() {
        if (getUser() != null && getTest() != null) {
            filterPredicate(predicateTest().and(predicateUser()));
        } else if (getUser() != null) {
            filterPredicate(predicateUser());
        } else if (getTest() != null) {
            filterPredicate(predicateTest());
        } else {
            results.setAll(resultFxModelList);
        }
    }

    public void deleteResult(ResultFxModel resultFxModel) throws ApplicationException {
        resultDao.deleteById(Result.class, resultFxModel.getResultId());
        getResultsFromDataBase();
        filterResultList();
    }

    private static void populateUsers(List<User> userList)
    {
        users.clear();
        userList.forEach(user -> {
            UserFxModel userFx = UserConverter.userToUserFx(user);
            users.add(userFx);
        });
    }

    private static void populateTests(List<Test> testList)
    {
        tests.clear();
        testList.forEach(test -> {
            TestFxModel testFx = TestConverter.testToTestFx(test);
            tests.add(testFx);
        });
    }

    public static ObservableList<ResultFxModel> getResults() {
        return results;
    }

    public static void setResults(ObservableList<ResultFxModel> results) {
        ResultDataModel.results = results;
    }

    public static ResultFxModel getResult() {
        return result.get();
    }

    public static ObjectProperty<ResultFxModel> resultProperty() {
        return result;
    }

    public static void setResult(ResultFxModel result) {
        ResultDataModel.result.set(result);
    }

    public static ResultDao getResultDao() {
        return resultDao;
    }

    public static void setResultDao(ResultDao resultDao) {
        ResultDataModel.resultDao = resultDao;
    }

    public static ObservableList<UserFxModel> getUsers() {
        return users;
    }

    public static void setUsers(ObservableList<UserFxModel> users) {
        ResultDataModel.users = users;
    }

    public static UserFxModel getUser() {
        return user.get();
    }

    public static ObjectProperty<UserFxModel> userProperty() {
        return user;
    }

    public static void setUser(UserFxModel user) {
        ResultDataModel.user.set(user);
    }

    public static ObservableList<TestFxModel> getTests() {
        return tests;
    }

    public static void setTests(ObservableList<TestFxModel> tests) {
        ResultDataModel.tests = tests;
    }

    public static TestFxModel getTest() {
        return test.get();
    }

    public static ObjectProperty<TestFxModel> testProperty() {
        return test;
    }

    public static void setTest(TestFxModel test) {
        ResultDataModel.test.set(test);
    }
}
