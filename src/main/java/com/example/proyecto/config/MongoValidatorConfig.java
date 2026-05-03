package com.example.proyecto.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;

// Esta clase se ejecuta al iniciar la aplicación para verificar que MongoDB
// esté accesible y que la base de datos 'biblioteca_bd' exista.
// Si no se encuentra, se mostrará un error crítico en consola.
// (El cierre forzado está comentado — descomenta el throw cuando estés en producción)

@Component
public class MongoValidatorConfig implements CommandLineRunner {

    private final MongoClient mongoClient;

    // Lee el nombre de la BD directamente desde application.properties
    // Ejemplo: spring.data.mongodb.database=biblioteca_bd
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    // Inyectamos el MongoClient para interactuar con el servidor de MongoDB
    public MongoValidatorConfig(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    // Este método se ejecuta automáticamente al arrancar la aplicación
    @Override
    public void run(String... args) {
        System.out.println("-----------------------------------------------");
        System.out.println("--- Iniciando verificación de base de datos ---");

        // 1. Obtenemos la lista de bases de datos disponibles en el servidor
        List<String> databases = mongoClient
                .listDatabaseNames()
                .into(new ArrayList<>());

        System.out.println("Bases de datos detectadas: " + databases);

        // 2. Verificamos si existe nuestra base de datos 'biblioteca_bd'
        if (!databases.contains(databaseName)) {
            System.err.println("CRITICAL ERROR: La base de datos '" + databaseName + "' no fue encontrada.");
            System.err.println("Bases de datos detectadas: " + databases);

            // Descomenta esta línea cuando quieras forzar el cierre en producción:
            // throw new IllegalStateException("Fallo en el arranque: La base de datos '" + databaseName + "' es obligatoria.");
            return;
        }

        // Si llegamos aquí, la conexión fue exitosa y la base de datos existe
        System.out.println("CONEXIÓN EXITOSA: Base de datos '" + databaseName + "' verificada y lista.");
        System.out.println("-----------------------------------------------");
    }
}
