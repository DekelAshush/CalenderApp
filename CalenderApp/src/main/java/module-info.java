module calenderapp.calenderapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens calenderapp.calenderapp to javafx.fxml;
    exports calenderapp.calenderapp;
}