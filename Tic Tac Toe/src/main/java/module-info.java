module org.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.desktop;

    opens org.example.tictactoe to javafx.fxml;
    exports org.example.tictactoe;
}