package ca.macewan.cmpt305.jfxproject;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * .csv file data access object
 *
 * @author Joel Lawrence
 */
class CsvPropertyAssessmentDAO implements PropertyAssessmentDAO {
    private List<PropertyAssessment> data;

    public CsvPropertyAssessmentDAO() throws IOException, CsvException {
        this.data = readData("src/Property_Assessment_Data_2022.csv");
    }

    /**
     * Parse file given a filename and create a list of PropertyAssessment Objects
     * @param filename .csv file to retrieve data
     * @return PropertyAssessment Object List from data
     */
    public List<PropertyAssessment> readData(String filename) throws IOException, CsvException {
        FileReader fileReader = new FileReader(filename);
        CSVReader csvReader = new CSVReaderBuilder(fileReader)
                .withSkipLines(1)
                .build();
        List<String[]> dataStrList = csvReader.readAll();
        List<PropertyAssessment> dataList = new ArrayList<>();
        for (String[] row : dataStrList) {
            PropertyAssessment newProperty = PropertyAssessment.createPropertyAssessment(row);
            dataList.add(newProperty);
        }

        // return all data from file as List of Strings
        return dataList;
    }

    @Override
    public Statistics getStatistics(List<PropertyAssessment> propertyAssessments) {
        return new Statistics(propertyAssessments);
    }

    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        for (PropertyAssessment pa : data) {
            if (pa.getAccountNum() == accountNumber) {
                return pa;
            }
        }
        return null;
    }

    @Override
    public List<PropertyAssessment> getByNeighbourhood(String neighbourhood) {
        List<PropertyAssessment> hoodPAs = new ArrayList<>();
        for (PropertyAssessment pa : data) {
            if (pa.getNeighbourhood().getNeighbourhoodName().equals(neighbourhood.toUpperCase())) {
                hoodPAs.add(pa);
            }
        }

        if (!hoodPAs.isEmpty()) {
            return hoodPAs;
        }

        return null;
    }

    @Override
    public List<PropertyAssessment> getByAssessmentClass(String assessmentClass) {
        List<PropertyAssessment> classPAs = new ArrayList<>();
        for (PropertyAssessment pa : data) {
            if (pa.getClasses().getClassNames().contains(assessmentClass)) {
                classPAs.add(pa);
            }
        }

        if (!classPAs.isEmpty()) {
            return classPAs;
        }

        return null;
    }

    @Override
    public List<PropertyAssessment> getByAddress(String address) {
        List<PropertyAssessment> addressPAs = new ArrayList<>();
        for (PropertyAssessment pa : data){
            if (pa.getAddress().toString().contains(address.toUpperCase())) {
                addressPAs.add(pa);
            }
        }
        return addressPAs;
    }

    @Override
    public List<PropertyAssessment> getByAssessedValueRange(int min, int max) {
        List<PropertyAssessment> rangePAs = new ArrayList<>();
        for (PropertyAssessment pa : data){
            if (min <= pa.getAssessVal() && pa.getAssessVal() <= max) {
                rangePAs.add(pa);
            }
        }
        System.out.println("done");

        return rangePAs;
    }

    /**
     * Retrieve subset of all property assessments from csv file
     * @param limit number of results to return
     * @param offset index of result array where to start the returned list of results
     * @return subset of all property assessments
     */
    @Override
    public List<PropertyAssessment> getAll(int limit, int offset) {
        List<PropertyAssessment> allPAs = new ArrayList<>();
        for (int i=offset; i<limit; i++){
            allPAs.add(data.get(i));
        }

        return allPAs;
    }

    /**
     * Default getAll method sets limit to 100 and offset to 0
     * @return subset of first 100 property assessments
     */
    @Override
    public List<PropertyAssessment> getAll() {
        return data;
    }
}
