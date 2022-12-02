package ca.macewan.cmpt305.jfxproject;

import java.util.List;

/**
 * Data access object interface for PropertyAssessment
 *
 * @author Joel Lawrence
 */
public interface PropertyAssessmentDAO {
    Statistics getStatistics(List<PropertyAssessment> propertyAssessments);
    PropertyAssessment getByAccountNumber(int accountNumber);
    List<PropertyAssessment> getByNeighbourhood(String neighbourhood);
    List<PropertyAssessment> getByAssessmentClass(String assessmentClass);
    List<PropertyAssessment> getByAddress(String address);
    List<PropertyAssessment> getByAssessedValueRange(int min, int max);
    List<PropertyAssessment> getAll(int limit, int offset);
    List<PropertyAssessment> getAll();
}