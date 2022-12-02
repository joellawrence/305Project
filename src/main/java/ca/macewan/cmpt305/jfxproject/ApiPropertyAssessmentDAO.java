package ca.macewan.cmpt305.jfxproject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * API data access object
 *
 * @author Joel Lawrence
 */
class ApiPropertyAssessmentDAO implements PropertyAssessmentDAO {

    /**
     * Opens csv file from API endpoint and parses from given query
     * @return string of property assessment fields separated by commas.
     */
    public String[] openByQuery(String inputQuery) {
        // Open csv file from API and parse its data
        String endpoint = "https://data.edmonton.ca/resource/q7d6-ambg.csv"; // request csv response
        String query = endpoint + inputQuery;
        System.out.println(query);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(query))
                .GET()
                .build();

        String responseString = "";
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseString = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Obtain string array of http response
        String[] propertyAssessmentArray = responseString
                .replaceAll("\"", "")   // remove all double quotations
                .split("\n");                      // separate list by commas

        return propertyAssessmentArray;
    }

    @Override
    public Statistics getStatistics(List<PropertyAssessment> propertyAssessments) {
        return new Statistics(propertyAssessments);
    }

    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        String query = "?account_number=" + accountNumber;
        String[] propertyAssessmentArray = openByQuery(query);

        return PropertyAssessment.createPropertyAssessment(propertyAssessmentArray[1].split(","));
    }

    @Override
    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood) {
        String query = "?neighbourhood=" + neighbourhood.toUpperCase();
        String[] propertyAssessmentArray = openByQuery(query);

        List<PropertyAssessment> hoodPropertyAssessments = new ArrayList<>();
        for (String row : propertyAssessmentArray) {
            if (row == propertyAssessmentArray[0]) continue; // skip header
            hoodPropertyAssessments.add(PropertyAssessment.createPropertyAssessment(row.split(",")));
        }

        return hoodPropertyAssessments;
    }

    @Override
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass) {
        String query = "?$where='" + assessmentClass.toUpperCase() + "'%20IN%20(mill_class_1,mill_class_2,mill_class_3)";
        String[] propertyAssessmentArray = openByQuery(query);

        List<PropertyAssessment> classPropertyAssessments = new ArrayList<>();
        for (String row : propertyAssessmentArray) {
            if (row == propertyAssessmentArray[0]) continue; // skip header
            classPropertyAssessments.add(PropertyAssessment.createPropertyAssessment(row.split(",")));
        }

        return classPropertyAssessments;
    }

    @Override
    public List<PropertyAssessment> getByAddress(String address) {
        address = address.toUpperCase().replaceAll(" ", "%20");
        String query = "?$where=suite%20%7C%7C%20house_number%20%7C%7C%20street_name%20like" +
                "'%25" + address + "%25'";
        String[] propertyAssessmentArray = openByQuery(query);

        List<PropertyAssessment> addressPropertyAssessments = new ArrayList<>();
        for (String row : propertyAssessmentArray) {
            if (row == propertyAssessmentArray[0]) continue; // skip header
            addressPropertyAssessments.add(PropertyAssessment.createPropertyAssessment(row.split(",")));
        }

        return addressPropertyAssessments;
    }

    @Override
    public List<PropertyAssessment> getByAssessedValueRange(int min, int max) {
        String query = "?$where=assessed_value%20between%20" + min + "%20and%20" + max;
        String[] propertyAssessmentArray = openByQuery(query);

        List<PropertyAssessment> rangePropertyAssessments = new ArrayList<>();
        for (String row : propertyAssessmentArray) {
            if (row == propertyAssessmentArray[0]) continue; // skip header
            rangePropertyAssessments.add(PropertyAssessment.createPropertyAssessment(row.split(",")));
        }

        return rangePropertyAssessments;
    }

    public List<PropertyAssessment> getByFilters(String accountNumber, String address, String neighbourhood,
                                                 String assessmentClass, String minValue, String maxValue) {
        String query = "?$where=";
        if (!accountNumber.isEmpty()){
            query += "account_number='" + accountNumber + "'and%20";
        }
        if (!address.isEmpty()){
            query += "suite%20%7C%7C%20house_number%20%7C%7C%20street_name%20like" +
                    "'%25" + URLEncoder.encode(address.toUpperCase(), StandardCharsets.UTF_8) + "%25'and%20";
        }
        if (!neighbourhood.isEmpty()){
            query += "neighbourhood='" + URLEncoder.encode(neighbourhood.toUpperCase(), StandardCharsets.UTF_8) + "'and%20";
        }
        if (!assessmentClass.isEmpty()){
            query += "'" + URLEncoder.encode(assessmentClass.toUpperCase(), StandardCharsets.UTF_8) + "'%20IN%20(mill_class_1,mill_class_2,mill_class_3)and%20";
        }
        if (!minValue.isEmpty()){
            query += "assessed_value%20%3E%3D%20" + minValue + "%20and%20";
        }
        if (!maxValue.isEmpty()) {
            query += "assessed_value%20%3C%3D%20" + maxValue + "%20and%20";
        }
        if (query.endsWith("and%20")){
            query = query.substring(0,query.length()-6);
        }
        String[] propertyAssessmentArray = openByQuery(query);

        List<PropertyAssessment> propertyAssessments = new ArrayList<>();
        for (String row : propertyAssessmentArray) {
            if (row == propertyAssessmentArray[0]) continue; // skip header
            propertyAssessments.add(PropertyAssessment.createPropertyAssessment(row.split(",")));
        }
        System.out.println("done");

        return propertyAssessments;

    }

    @Override
    public List<PropertyAssessment> getAll(int limit, int offset) {
        String query = "?$limit=" + limit + "&$offset=" + offset;
        String[] propertyAssessmentArray = openByQuery(query);

        List<PropertyAssessment> allPropertyAssessments = new ArrayList<>();
        for (String row : propertyAssessmentArray) {
            if (row == propertyAssessmentArray[0]) continue; // skip header
            allPropertyAssessments.add(PropertyAssessment.createPropertyAssessment(row.split(",")));
        }

        return allPropertyAssessments;
    }

    @Override
    public List<PropertyAssessment> getAll() {
        String query = "";
        String[] propertyAssessmentArray = openByQuery(query);

        List<PropertyAssessment> allPropertyAssessments = new ArrayList<>();
        for (String row : propertyAssessmentArray) {
            if (row == propertyAssessmentArray[0]) continue; // skip header
            allPropertyAssessments.add(PropertyAssessment.createPropertyAssessment(row.split(",")));
        }

        return allPropertyAssessments;
    }

}

