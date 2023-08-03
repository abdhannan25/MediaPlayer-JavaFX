package com.mediaplayer.media.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class MediaPlayerController {

    private String path;
    private MediaPlayer mediaPlayer;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider progressBar;

    @FXML
    private Slider volumeSlider;


    public void chooseFileMethod(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        if (path != null){
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty widthprop = mediaView.fitWidthProperty();
            DoubleProperty heightprop = mediaView.fitHeightProperty();

            widthprop.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            heightprop.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    progressBar.setValue(t1.toSeconds());
                }
            });
            progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });
            progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration total = media.getDuration();
                    progressBar.setMax(total.toSeconds());
                }
            });

            volumeSlider.setValue(mediaPlayer.getVolume() * 100);
            volumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeSlider.getValue()/100);
                }
            });
            mediaPlayer.play();

        }
    }

    public void play(ActionEvent event){
        mediaPlayer.play();
        mediaPlayer.setRate(1);

    }
    public void pause(ActionEvent event){
        mediaPlayer.pause();
    }
    public void stop(ActionEvent event){
        mediaPlayer.stop();
    }

    public void fast(ActionEvent event ){
        mediaPlayer.setRate(2);
    }
    public void slow(ActionEvent event){
        mediaPlayer.setRate(0.5);
    }
    public void skip10(ActionEvent event){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(10)));
    }
    public void back10(ActionEvent event){
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-10)));
    }
}