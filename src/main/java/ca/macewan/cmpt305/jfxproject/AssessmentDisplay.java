package ca.macewan.cmpt305.jfxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AssessmentDisplay extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Edmonton Property Assessments");
        Parent root = FXMLLoader.load(getClass().getResource("display-view.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}