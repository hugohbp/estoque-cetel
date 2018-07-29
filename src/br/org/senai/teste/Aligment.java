/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.senai.teste;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Simulado
 */
public class Aligment extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Text text = new Text("Centralizado.");
        text.setFont(Font.font(null, FontWeight.BOLD, 18));
        text.setFill(Color.WHITE);
        Ellipse ellipse = new Ellipse();
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(ellipse, text);
        Scene scene = new Scene(stackPane, 400, 100);
        ellipse.radiusXProperty().bind(scene.widthProperty().divide(2));
        ellipse.radiusYProperty().bind(scene.heightProperty().divide(2));
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
