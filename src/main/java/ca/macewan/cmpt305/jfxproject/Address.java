package ca.macewan.cmpt305.jfxproject;

import java.util.Objects;

/**
 * Helper class that holds address information (suite, house number,
 * and street name) of a PropertyAssessment Object
 *
 * @author joellawrence
 */
public class Address {
    private int houseNum;
    private String suite, streetName;

    public Address(String suite, int houseNum, String streetName) {
        this.suite = suite;
        this.houseNum = houseNum;
        this.streetName = streetName;
    }

    public int getHouseNum() {
        return houseNum;
    }

    public String getSuite() {
        return suite;
    }

    public String getStreetName() {
        return streetName;
    }

    @Override
    public String toString() {
        if (houseNum != -1 && suite.isEmpty())
            return houseNum + " " + streetName;
        else if(!suite.isEmpty())
            return suite + " " + houseNum + " " + streetName;
        else
            return "";
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject == this)
            return true;
        if (anObject instanceof Address) {
            Address anAddress = (Address) anObject;
            return anAddress.getHouseNum() == getHouseNum() && anAddress.getSuite().equals(getSuite())
                    && anAddress.getStreetName().equals(getStreetName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseNum, streetName);
    }
}

