package ca.macewan.cmpt305.jfxproject;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Helper class to store size, maximum and minimum value, range,
 * mean, and median from a PropertyAssessment Object
 * @author Joel Lawrence
 */
public class Statistics {
    private List<PropertyAssessment> data;

    public Statistics(List<PropertyAssessment> data) {
        this.data = data;
    }

    public int getN() {
        return this.data.size();
    }

    // Find max value in assessment value column
    public int getMax() {
        int max = -1;

        for (PropertyAssessment pa : data) {
            int val = pa.getAssessVal();
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    // Find min value in assessment value column
    public int getMin() {
        int min = Integer.MAX_VALUE;

        for (PropertyAssessment pa : data) {
            int val = pa.getAssessVal();
            if (val < min) {
                min = val;
            }
        }
        return min;
    }

    public int getRange() {
        return getMax() - getMin();
    }

    // Find mean of all assessment values
    public int getMean() {
        long sum = 0;
        long mean;
        for (PropertyAssessment pa : data) {
            sum += pa.getAssessVal();
        }
        mean = sum / (long) data.size();

        return (int) mean;
    }

    // Find median of all assessment values
    public int getMedian() {
        Collections.sort(data);

        if (data.size() % 2 != 0)
            return data.get((data.size() + 1) / 2).getAssessVal();
        else {
            int lower = data.get(data.size() / 2 - 1).getAssessVal();
            int upper = data.get(data.size() / 2).getAssessVal();

            return (lower + upper) / 2;
        }
    }

    @Override
    public String toString() {
        return "n = " + getN() +
                "\nmin = $" + NumberFormat.getNumberInstance(Locale.US).format(getMin()) +
                "\nmax = $" + NumberFormat.getNumberInstance(Locale.US).format(getMax()) +
                "\nrange = $" + NumberFormat.getNumberInstance(Locale.US).format(getRange()) +
                "\nmean = $" + NumberFormat.getNumberInstance(Locale.US).format(getMean()) +
                "\nmedian = $" + NumberFormat.getNumberInstance(Locale.US).format(getMedian());
    }
}