package proz.utils.converters;

import proz.database.models.Result;
import proz.models.ResultFxModel;

public class ResultConverter
{

    private ResultConverter() {}

    public static ResultFxModel resultToResultFx(Result result)
    {
        ResultFxModel fxModel = new ResultFxModel();
        fxModel.setResultId(result.getResultId());
        fxModel.setDate(result.getDate());
        fxModel.setScore(result.getScore());
        fxModel.setUser(UserConverter.userToUserFx(result.getUserId()));
        fxModel.setTest(TestConverter.testToTestFx(result.getTestId()));
        return fxModel;
    }

    public static Result resultFxToResult(ResultFxModel fxModel)
    {
        Result result = new Result();
        result.setResultId(fxModel.getResultId());
        result.setScore(fxModel.getScore());
        result.setUserId(UserConverter.userFxToUser(fxModel.getUser()));
        result.setTestId(TestConverter.testFxToTest(fxModel.getTest()));
        return result;
    }
}
