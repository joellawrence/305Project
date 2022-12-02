package ca.macewan.cmpt305.jfxproject;

import java.text.NumberFormat;
import java.util.*;

/**
 * Object to store assessment data of a single property
 *
 * @author: Joel Lawrence
 */
public class PropertyAssessment implements Comparable<PropertyAssessment> {
    private int accountNum, assessVal;
    private Address address;
    private boolean hasGarage;
    private Neighbourhood neighbourhood;
    private Location location;
    private AssessmentClasses classes;

    public PropertyAssessment(int accountNum, Address address, boolean hasGarage, Neighbourhood neighbourhood,
                              int assessVal, Location location, AssessmentClasses classes) {
        this.accountNum = accountNum;
        this.address = address;
        this.hasGarage = hasGarage;
        this.neighbourhood = neighbourhood;
        this.assessVal = assessVal;
        this.location = location;
        this.classes = classes;
    }

    public static PropertyAssessment createPropertyAssessment(String[] arr) {
        int accountNum = Integer.parseInt(arr[0]);

        int houseNum = -1;
        if (!arr[2].isEmpty())
            houseNum = Integer.parseInt(arr[2]);
        Address address = new Address(arr[1], houseNum, arr[3]);

        boolean hasGarage = arr[4].equals("Y");

        int neighbourhoodID = -1;
        if (!arr[5].isEmpty())
            neighbourhoodID = Integer.parseInt(arr[5]);
        Neighbourhood neighbourhood = new Neighbourhood(neighbourhoodID, arr[6], arr[7]);

        int assessVal = Integer.parseInt(arr[8]);

        Location location = new Location(Double.parseDouble(arr[9]), Double.parseDouble(arr[10]), arr[11]);

        AssessmentClasses assessClasses = new AssessmentClasses();
        if (!arr[12].isEmpty()) {
            assessClasses.setClass1(arr[15], Integer.parseInt(arr[12]));
        }
        if (!arr[13].isEmpty()) {
            assessClasses.setClass2(arr[16], Integer.parseInt(arr[13]));
        }
        if (!arr[14].isEmpty()) {
            assessClasses.setClass3(arr[17], Integer.parseInt(arr[14]));
        }

        return new PropertyAssessment(accountNum, address, hasGarage, neighbourhood,
                assessVal, location, assessClasses);
    }

    public int getAccountNum() {
        return accountNum;
    }

    public Address getAddress() { return address; }

    public boolean isHasGarage() { return hasGarage; }

    public Neighbourhood getNeighbourhood() { return neighbourhood; }

    public int getAssessVal() { return assessVal; }

    public Location getLocation() { return location; }

    public AssessmentClasses getClasses() { return classes; }

    @Override
    public boolean equals(Object anObject) {
        if (anObject == this)
            return true;
        if (anObject instanceof PropertyAssessment) {
            PropertyAssessment aPropertyAssessment = (PropertyAssessment) anObject;
            return aPropertyAssessment.getAccountNum() == getAccountNum();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return accountNum; // accountNum is unique
    }

    @Override
    public String toString() {
        String paString = "Account Number = " + accountNum
                + "\nAddress = " + address
                + "\nAssessed value = $" + NumberFormat.getNumberInstance(Locale.US).format(assessVal)
                + "\nAssessment class = " + classes.getClasses()
                + "\nNeighbourhood = " + neighbourhood
                + "\nLocation = " + location;
        return paString;
    }

    @Override
    public int compareTo(PropertyAssessment other) {
        return getAssessVal() - other.getAssessVal();
    }

}

