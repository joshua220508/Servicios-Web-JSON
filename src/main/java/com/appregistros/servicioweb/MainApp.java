package com.appregistros.servicioweb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("clientes-view.fxml"));
        stage.setTitle("Clientes - Supabase");
        stage.setScene(new Scene(loader.load(), 1050, 600));
        stage.setMinWidth(760);
        stage.setMinHeight(360);
        stage.show();
    }
}
