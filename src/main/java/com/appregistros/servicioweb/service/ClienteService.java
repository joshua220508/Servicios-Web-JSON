package com.appregistros.servicioweb.service;

import com.appregistros.servicioweb.model.Cliente;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/** Consume la tabla "customers" de Supabase mediante su API REST. */
public class ClienteService {
    private static final String URL_POR_DEFECTO = "https://sqpvqkkdfgtzdgrjrgxs.supabase.co";
    private static final String RUTA_CLIENTES = "/rest/v1/customers?select=*&order=id.asc";
    private static final String ENV_API_KEY = "SUPABASE_API_KEY";
    private static final String ENV_URL = "SUPABASE_URL";

    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Cliente> obtenerClientes() {
        String apiKey = System.getenv(ENV_API_KEY);
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Configure la variable de entorno " + ENV_API_KEY);
        }

        try {
            HttpResponse<String> respuesta = http.send(crearPeticion(apiKey), HttpResponse.BodyHandlers.ofString());

            if (respuesta.statusCode() < 200 || respuesta.statusCode() >= 300) {
                throw new IllegalStateException("Error al obtener clientes: HTTP " + respuesta.statusCode());
            }
            return convertir(mapper.readTree(respuesta.body()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Consulta cancelada", e);
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo conectar con el servicio de clientes", e);
        }
    }

    private HttpRequest crearPeticion(String apiKey) {
        String base = System.getenv(ENV_URL);
        if (base == null || base.isBlank()) {
            base = URL_POR_DEFECTO;
        }
        return HttpRequest.newBuilder()
                .uri(URI.create(base + RUTA_CLIENTES))
                .timeout(Duration.ofSeconds(15))
                .header("apikey", apiKey)
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .GET()
                .build();
    }

    private List<Cliente> convertir(JsonNode filas) {
        List<Cliente> clientes = new ArrayList<>();
        for (JsonNode fila : filas) {
            String nombre = (texto(fila, "first_name") + " " + texto(fila, "last_name")).trim();
            clientes.add(new Cliente(
                    fila.hasNonNull("id") ? fila.get("id").asInt() : null,
                    nombre,
                    texto(fila, "email"),
                    texto(fila, "phone"),
                    texto(fila, "city"),
                    texto(fila, "status")));
        }
        return clientes;
    }

    /** Devuelve el campo como texto, o "" si falta o es null (evita el "null" de asText()). */
    private static String texto(JsonNode nodo, String campo) {
        JsonNode valor = nodo.get(campo);
        return valor == null || valor.isNull() ? "" : valor.asText().trim();
    }
}
