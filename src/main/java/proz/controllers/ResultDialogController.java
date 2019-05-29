package proz.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import proz.models.ResultDataModel;
import proz.models.ResultFxModel;
import proz.models.TestFxModel;
import proz.models.UserFxModel;
import proz.utils.DialogsUtils;
import proz.utils.exceptions.ApplicationException;

import java.util.Date;

public class ResultDialogController
{
    @FXML
    public TableView<ResultFxModel> resultTableView;

    @FXML
    private ComboBox testComboBox;

    @FXML
    private ComboBox userComboBox;

    @FXML
    private TableColumn<ResultFxModel, UserFxModel> userColumn;

    @FXML
    private TableColumn< ResultFxModel, TestFxModel> testColumn;

    @FXML
    private TableColumn<ResultFxModel, Number> scoreColumn;

    @FXML
    private TableColumn<ResultFxModel, Date> dateColumn;

    @FXML
    private TableColumn<ResultFxModel, ResultFxModel> deleteColumn;

    @FXML
    void initialize()
    {
        ResultDataModel resultDataModel = new ResultDataModel();
        try {
            ResultDataModel.getResultsFromDataBase();
            ResultDataModel.getTestsFromDataBase();
            ResultDataModel.getUsersFromDataBase();
        } catch (ApplicationException e) {
            DialogsUtils.errorDialog(e.getMessage());
        }
        bindProperties();
        deleteColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        deleteColumn.setCellFactory(param -> new TableCell<ResultFxModel, ResultFxModel>(){
            Button button = createDeleteButton();
            @Override
            protected void updateItem(ResultFxModel item, boolean empty)
            {
                super.updateItem(item, empty);
                if(empty)
                {
                    setGraphic(null);
                }
                else
                {
                    setGraphic(button);
                    button.setOnAction(event -> {
                        try {
                            resultDataModel.deleteResult(item);
                        } catch (ApplicationException e) {
                            DialogsUtils.errorDialog(e.getMessage());
                        }
                    });
                }
            }
        });

    }

    private Button createDeleteButton()
    {
        Button button = new Button();
        button.setText("delete");
        return button;
    }

    public void filterOnAction()
    {
        ResultDataModel resultDataModel = new ResultDataModel();
        resultDataModel.filterResultList();
    }

    public void clearUserComboBox()
    {
        userComboBox.getSelectionModel().clearSelection();
    }

    public void clearTestComboBox()
    {
        testComboBox.getSelectionModel().clearSelection();
    }

    private void bindProperties()
    {
        resultTableView.setItems(ResultDataModel.getResults());
        testComboBox.setItems(ResultDataModel.getTests());
        userComboBox.setItems(ResultDataModel.getUsers());
        ResultDataModel.testProperty().bind(testComboBox.valueProperty());
        ResultDataModel.userProperty().bind(userComboBox.valueProperty());
        userColumn.setCellValueFactory(cellData -> cellData.getValue().userProperty());
        testColumn.setCellValueFactory(cellData -> cellData.getValue().testProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    }
}
