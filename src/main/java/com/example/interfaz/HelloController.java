package com.example.interfaz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

//Busquedas: https://jenkov.com/tutorials/javafx/concurrency.html
// https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html#:~:text=Creating%20a%20thread%20in%20Java,thread.

public class HelloController implements Initializable {
    //texto, es el contenedor principal del flujo
    @FXML private TextArea texto;
    //botón que inicia la función iniciar
    @FXML private Button BotonEmpezar;
    //espacio que guarda la lista de los camareros
    @FXML private TextArea camarerosArea;
    //espacio que guarda la lista de los clientes
    @FXML private TextArea clientesArea;

    //listas para enseñar los datos en las pantallas de la derecha
    private Camarero[] camareros;
    private Cliente[] clientes;

    @Override
    //Función al ejecutar el ejercicio, nos aparece un mensaje de darle a un botón para inicializar y no nos deja editar los elementos
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar la interfaz y no deja editar los textArea
        camarerosArea.setEditable(false);
        clientesArea.setEditable(false);
        texto.setEditable(false);
        texto.appendText("Presiona 'Iniciar Simulación' para comenzar\n\n");
    }

    @FXML
    private void iniciar() {
        BotonEmpezar.setDisable(true);
        texto.clear();
        camarerosArea.clear();
        clientesArea.clear();
        
        // Crear camareros
        camareros = new Camarero[]{
                new Camarero("Dani", false, this),
                new Camarero("Gonzalo", false, this)
        };

        // Crear clientes
        clientes = new Cliente[]{
                new Cliente("Ana", 3000, camareros, this),
                new Cliente("Carlos", 4000, camareros, this),
                new Cliente("Alan", 2000, camareros, this),
                new Cliente("Marcos", 1000, camareros, this),
                new Cliente("Lucía", 5000, camareros, this),
                new Cliente("Sara", 2000, camareros, this),
                new Cliente("Alex", 4000, camareros, this),
                new Cliente("Emilio", 6000, camareros, this),
        };

        // Mostrar estado inicial
        agregarLog("--- CAFETERÍA ROSAL ABRE SUS PUERTAS ---\n");
        agregarLog("Camareros listos: " + camareros.length + "\n");

        actualizarListaCamareros();

        // Iniciar camareros
        for(Camarero s : camareros){
            s.start();
        }

        // Iniciar clientes
        new Thread(() -> {
            for(Cliente c : clientes){
                c.start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            // Esperar que terminen todos los clientes
            for(Cliente c : clientes){
                try {
                    c.join();

                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            //Ejecuta el código más tarde en el hilo que maneja la interfaz
            Platform.runLater(() -> {
                agregarLog("\n--- JORNADA TERMINADA ---");
                BotonEmpezar.setDisable(false);
            });
        }).start();


    }

    public void agregarLog(String mensaje){
        //Ejecuta el código más tarde en el hilo que maneja la interfaz
        Platform.runLater(() -> {
            //añade el proceso al textArea
            texto.appendText(mensaje + "\n");
        });
    }

    public void clientesSatisfechos(String mensaje){
        Platform.runLater(() -> {
            //añade el estado de los clientes al textArea
            clientesArea.appendText(mensaje + "\n");
        });
    }


    public void actualizarEstadoCliente(String nombre, String estado) {
        //Ejecuta el código más tarde en el hilo que maneja la interfaz
        Platform.runLater(() -> {
            agregarLog( nombre + " - " + estado);
        });
    }
    private void actualizarListaCamareros() {
        //Ejecuta el código más tarde en el hilo que maneja la interfaz
        Platform.runLater(() -> {
            camarerosArea.clear();
            if (camareros != null) {
                //lista de los camareros trabajando ese día
                for(int i=0; i<camareros.length; i++){
                    camarerosArea.appendText("Camarero " + (i+1) + ": " + camareros[i].getNombre() + "\n");
                }
            }
        });
    }

    public void actualizarListaClientes(String estado, String nombre) {
        //Ejecuta el código más tarde en el hilo que maneja la interfaz
        Platform.runLater(() -> {
            //Aquí vamos a enseñar si el cliente ha salido satisfecho o insatisfecho
            clientesSatisfechos(estado + nombre);
        });
    }
}