package proz.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import proz.database.daos.UserDao;

public class UserDataModel
{
    private static ObjectProperty<UserFxModel> currentUser = new SimpleObjectProperty<>(new UserFxModel());
    private static UserDao userDao = new UserDao();

    private UserDataModel() {}

    public static UserFxModel getCurrentUser()
    {
        return currentUser.get();
    }

    public static ObjectProperty<UserFxModel> currentUserProperty()
    {
        return currentUser;
    }

    public static void setCurrentUser(UserFxModel currentUser)
    {
        UserDataModel.currentUser.set(currentUser);
    }

    public static void clearCurrentUser()
    {
        currentUser.get().setPassword(null);
        currentUser.get().setUsername(null);
        currentUser.get().setIsTeacher(false);
        currentUser.get().setUserId(0);
        //currentUser.set(new UserFxModel());
    }

    public static UserDao getUserDao()
    {
        return userDao;
    }
}
