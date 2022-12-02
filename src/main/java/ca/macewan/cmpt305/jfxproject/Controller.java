package ca.macewan.cmpt305.jfxproject;

import com.opencsv.exceptions.CsvException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private Label lblTitle;
    @FXML
    private TableView<PropertyAssessment> tblPropertyAssessments;
    @FXML
    private TableColumn<PropertyAssessment, Integer> tcAccount;
    @FXML
    private TableColumn<PropertyAssessment, String> tcAddress;
    @FXML
    private TableColumn<PropertyAssessment, Integer> tcAssessedValue;
    @FXML
    private TableColumn<PropertyAssessment, String> tcAssessmentClass;
    @FXML
    private TableColumn<PropertyAssessment, String> tcNeighbourhood;
    @FXML
    private TableColumn<PropertyAssessment, String> tcLocation;
    @FXML
    private TextField tfAccount;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfMaxValue;
    @FXML
    private TextField tfMinValue;
    @FXML
    private TextField tfNeighbourhood;
    @FXML
    private Button btnReadData;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnSearch;
    @FXML
    private ChoiceBox<String> cBoxAssessmentClass;
    @FXML
    private ChoiceBox<String> cBoxSource;
    @FXML
    private Label lblAccount;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblAssessedRange;
    @FXML
    private Label lblAssessmentClass;
    @FXML
    private Label lblFindPA;
    @FXML
    private Label lblNeighbourhood;
    @FXML
    private Label lblSource;

    private PropertyAssessmentDAO dao;
    private Boolean fromFile;
    private ObservableList<PropertyAssessment> dataList;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cBoxSource.getItems().addAll("","CSV File", "Edmonton Open Data Portal");
        cBoxAssessmentClass.getItems().addAll("","RESIDENTIAL", "COMMERCIAL", "FARMLAND", "OTHER RESIDENTIAL");
        cBoxAssessmentClass.setValue("");
        dataList = FXCollections.observableArrayList();

        tcAccount.setCellValueFactory((new PropertyValueFactory<>("accountNum")));
        tcAddress.setCellValueFactory((new PropertyValueFactory<>("address")));
        tcAssessmentClass.setCellValueFactory((new PropertyValueFactory<>("classes")));
        tcNeighbourhood.setCellValueFactory((new PropertyValueFactory<>("neighbourhood")));
        tcLocation.setCellValueFactory((new PropertyValueFactory<>("location")));
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        tcAssessedValue.setCellValueFactory((new PropertyValueFactory<>("assessVal")));
        tcAssessedValue.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Integer value, boolean empty){
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(0);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });

        tblPropertyAssessments.setItems(dataList);

        handleClickEvent();
    }

    private void handleClickEvent() {
        btnReadData.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dataList.clear();
                String source = cBoxSource.getValue();
                fromFile = source.equals("CSV File");   // 'True' for CSV File; 'False' for API
                try{
                    dao = fromFile ? new CsvPropertyAssessmentDAO() : new ApiPropertyAssessmentDAO();
                } catch (IOException | CsvException e) {
                    throw new RuntimeException();
                }
                dataList.addAll(dao.getAll(100,0));
            }
        });

        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dataList.clear();
                String source = cBoxSource.getValue();
                fromFile = source.equals("CSV File");   // 'True' for CSV File; 'False' for API
                try{
                    dao = fromFile ? new CsvPropertyAssessmentDAO() : new ApiPropertyAssessmentDAO();
                } catch (IOException | CsvException e) {
                    throw new RuntimeException();
                }

                String inputAccount = tfAccount.getText();
                String inputAddress = tfAddress.getText();
                String inputNeighbourhood = tfNeighbourhood.getText();
                String inputAssessmentClass = cBoxAssessmentClass.getValue();
                String inputMinValue = tfMinValue.getText();
                String inputMaxValue = tfMaxValue.getText();

                if (dao instanceof CsvPropertyAssessmentDAO) {
                    dataList.addAll(dao.getAll());
                    if (!inputAccount.isEmpty())
                        dataList.retainAll(dao.getByAccountNumber(Integer.parseInt(inputAccount)));
                    if (!inputAddress.isEmpty())
                        dataList.retainAll(dao.getByAddress(inputAddress));
                    if (!inputNeighbourhood.isEmpty())
                        dataList.retainAll(dao.getByNeighbourhood(inputNeighbourhood));
                    if (!inputAssessmentClass.isEmpty())
                        dataList.retainAll(dao.getByAssessmentClass(inputAssessmentClass));
                    if (!inputMinValue.isEmpty() && !inputMaxValue.isEmpty()) {
                        dataList.retainAll(dao.getByAssessedValueRange(Integer.parseInt(inputMinValue),
                                Integer.parseInt(inputMaxValue)));
                    } else if (inputMaxValue.isEmpty()) {
                        dataList.retainAll(dao.getByAssessedValueRange(Integer.parseInt(inputMinValue),
                                Integer.MAX_VALUE));
                    } else {
                        dataList.retainAll(dao.getByAssessedValueRange(0, Integer.parseInt(inputMaxValue)));
                    }

                } else if (dao instanceof ApiPropertyAssessmentDAO apiDAO){
                    dataList.addAll(apiDAO.getByFilters(inputAccount,inputAddress,inputNeighbourhood,
                            inputAssessmentClass,inputMinValue, inputMaxValue));
                }
            }
        });

        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cBoxSource.getSelectionModel().selectFirst();
                tfAccount.clear();
                tfAddress.clear();
                tfNeighbourhood.clear();
                cBoxAssessmentClass.getSelectionModel().selectFirst();
                tfMinValue.clear();
                tfMaxValue.clear();
                dataList.clear();
            }
        });
    }
}