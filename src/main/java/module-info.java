module com.mediaplayer.media {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.mediaplayer.media to javafx.fxml;
    exports com.mediaplayer.media;
    exports com.mediaplayer.media.controllers;
    opens com.mediaplayer.media.controllers to javafx.fxml;
}