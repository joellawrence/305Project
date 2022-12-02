package ca.macewan.cmpt305.jfxproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AssessmentClasses {
    private String class1, class2, class3;
    private int class1Percent, class2Percent, class3Percent;

    public AssessmentClasses(){ }

    public String getClass1() { return class1; }

    public String getClass2() { return class2; }

    public String getClass3() { return class3; }

    public List<String> getClassNames() {
        List<String> classNameList = new ArrayList<>();
        if (class1 != null)
            classNameList.add(class1);
        if (class2 != null)
            classNameList.add(class2);
        if (class3 != null)
            classNameList.add(class3);

        return classNameList;
    }

    public List<String> getClasses() {
        List<String> classList = new ArrayList<>();
        if (class1 != null)
            classList.add(class1 + " " + class1Percent + "%");
        if (class2 != null)
            classList.add(class2 + " " + class2Percent + "%");
        if (class3 != null)
            classList.add(class3 + " " + class3Percent + "%");

        return classList;
    }

    public void setClass1(String class1, int class1Percent){
        this.class1 = class1;
        this.class1Percent = class1Percent;
    }

    public void setClass2(String class2, int class2Percent){
        this.class2 = class2;
        this.class2Percent = class2Percent;
    }

    public void setClass3(String class3, int class3Percent){
        this.class3 = class3;
        this.class3Percent = class3Percent;
    }

    @Override
    public String toString(){
        return String.join(",", getClasses());
    }

    @Override
    public boolean equals(Object anObject) {
        if (anObject == this)
            return true;
        if (anObject instanceof AssessmentClasses) {
            AssessmentClasses anAssessmentClasses = (AssessmentClasses) anObject;
            return anAssessmentClasses.getClasses().equals(getClasses());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(class1, class2, class3, class1Percent, class2Percent, class3Percent);
    }
}
