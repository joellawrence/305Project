package ca.macewan.cmpt305.jfxproject;

import java.util.Objects;

/**
 * Helper class to store latitude, longitude, and point of
 * a PropertyAssessment Object
 *
 * @author Joel Lawrence
 */
public class Location {
    private double latitude, longitude;
    private String point;

    public Location(double latitude, double longitude, String point) {
        rangeCheck(latitude, 180, "latitude");
        rangeCheck(longitude, 180, "longitude");

        this.latitude = latitude;
        this.longitude = longitude;
        this.point = point;
    }

    private static void rangeCheck(double arg, double max, String name) {
        if (Math.abs(arg) > max)
            throw new IllegalArgumentException(name + ": " + arg);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return "(" + this.latitude + ", " + this.longitude + ")";
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject == this)
            return true;
        if (anObject instanceof Location) {
            Location aLocation = (Location) anObject;
            return aLocation.getLatitude() == getLatitude() && aLocation.getLongitude() == getLongitude();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
