module ca.macewan.cmpt305.jfxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires com.opencsv;

    opens ca.macewan.cmpt305.jfxproject to javafx.fxml;
    exports ca.macewan.cmpt305.jfxproject;
}