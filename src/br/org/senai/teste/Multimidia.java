/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.teste;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 *
 * @author Simulado
 */
public class Multimidia extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Media media = new Media(new File("Instalando.mp4").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        StackPane raiz = new StackPane();
        raiz.getChildren().add(mediaView);
        Scene scene = new Scene(raiz, 600, 400);
        primaryStage.setTitle("Tocando Vídeo em JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

        mediaPlayer.play();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
