package ca.macewan.cmpt305.jfxproject;

import java.util.Objects;

/**
 * Helper class to store neighbourhood ID, neighbourhood name, and
 * ward of a PropertyAssessment Object
 *
 * @author Joel Lawrence
 */
public class Neighbourhood {
    private int neighbourhoodID;
    private String neighbourhoodName;
    private String ward;

    public Neighbourhood(int neighbourhoodID, String neighbourhoodName, String ward) {
        this.neighbourhoodID = neighbourhoodID;
        this.neighbourhoodName = neighbourhoodName;
        this.ward = ward;
    }

    public int getNeighbourhoodID() { return neighbourhoodID; }

    public String getNeighbourhoodName() { return neighbourhoodName; }

    public String getWard() { return ward; }

    @Override
    public String toString() {
        if (!neighbourhoodName.isEmpty())
            return neighbourhoodName + " (" + ward + ")";
        else
            return "";
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject == this)
            return true;
        if (anObject instanceof Neighbourhood) {
            Neighbourhood aNeighbourhood = (Neighbourhood) anObject;
            return aNeighbourhood.getNeighbourhoodID() == getNeighbourhoodID();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighbourhoodID, neighbourhoodName);
    }
}
