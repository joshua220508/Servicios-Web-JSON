package com.appregistros.servicioweb.controller;

import com.appregistros.servicioweb.model.Cliente;
import com.appregistros.servicioweb.service.ClienteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class ClientesController {
    @FXML
    private Button btnConsultar;

    @FXML
    private TextField txtBuscar;

    @FXML
    private Label lblEstado;

    @FXML
    private TableView<Cliente> tablaClientes;

    private final ClienteService servicio = new ClienteService();
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private final FilteredList<Cliente> visibles = new FilteredList<>(clientes, c -> true);

    @FXML
    private void initialize() {
        tablaClientes.setItems(visibles);
        txtBuscar.textProperty().addListener((obs, anterior, texto) -> {
            visibles.setPredicate(cliente -> cliente.coincideCon(texto));
            lblEstado.setText(visibles.size() + " cliente(s) mostrado(s)");
        });
    }

    @FXML
    private void onConsultar() {
        btnConsultar.setDisable(true);
        lblEstado.setText("Consultando clientes...");

        Task<List<Cliente>> tarea = new Task<>() {
            @Override
            protected List<Cliente> call() {
                return servicio.obtenerClientes();
            }
        };

        tarea.setOnSucceeded(e -> {
            clientes.setAll(tarea.getValue());
            lblEstado.setText(visibles.size() + " cliente(s) mostrado(s)");
            btnConsultar.setDisable(false);
        });

        tarea.setOnFailed(e -> {
            lblEstado.setText(mensajeDe(tarea.getException()));
            btnConsultar.setDisable(false);
        });

        Thread hilo = new Thread(tarea, "consulta-clientes");
        hilo.setDaemon(true);
        hilo.start();
    }

    @FXML
    private void onLimpiar() {
        txtBuscar.clear();
        lblEstado.setText(visibles.size() + " cliente(s) mostrado(s)");
    }

    private static String mensajeDe(Throwable error) {
        return error.getMessage() != null ? error.getMessage() : "Ocurrió un error inesperado";
    }
}
