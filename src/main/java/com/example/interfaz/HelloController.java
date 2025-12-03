package com.example.interfaz;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//Busquedas: https://jenkov.com/tutorials/javafx/concurrency.html
// https://jenkov.com/tutorials/java-concurrency/creating-and-starting-threads.html#:~:text=Creating%20a%20thread%20in%20Java,thread.


public class HelloController implements Initializable {
    //texto, es el contenedor principal del flujo
    @FXML private TextArea texto;
    //botón que inicia la función iniciar
    @FXML private Button BotonEmpezar;
    @FXML private Button BotonCerrar;
    @FXML private Button NuevoCliente;
    //espacio que guarda la lista de los camareros
    @FXML private TextArea camarerosArea;
    //espacio que guarda la lista de los clientes
    @FXML private TextArea clientesArea;
    @FXML private TextArea baristaArea;
    @FXML private TextArea bufferArea;
    @FXML private TextArea bufferArea2;

    private Barista barista;
    private Cola cola;


    private int contadorClientes = 0;

    //listas para enseñar los datos en las pantallas de la derecha
    private ArrayList<Camarero> camareros;
    private ArrayList<Cliente> clientes;

    @Override
    //Función al ejecutar el ejercicio, nos aparece un mensaje de darle a un botón para inicializar y no nos deja editar los elementos
    public void initialize(URL location, ResourceBundle resources) {
        // Inicializar la interfaz y no deja editar los textArea
        camarerosArea.setEditable(false);
        clientesArea.setEditable(false);
        baristaArea.setEditable(false);
        bufferArea.setEditable(false);
        texto.setEditable(false);
        texto.appendText("Presiona 'Iniciar Simulación' para comenzar\n\n");
        NuevoCliente.setDisable(true);
    }

    @FXML
    private void iniciar() {
        BotonEmpezar.setDisable(true);
        NuevoCliente.setDisable(false);
        texto.clear();
        camarerosArea.clear();
        clientesArea.clear();
        baristaArea.clear();
        bufferArea.clear();

        //Creamos cola
        cola = new Cola(0,this);
        barista = new Barista(cola,this);

        barista.start();

        // Crear camareros
        camareros = new ArrayList<>();
        camareros.add(new Camarero("Dani",false,this,cola));
        camareros.add(new Camarero("Gonzalo",false,this,cola));

        // Crear clientes
        clientes = new ArrayList<>();

        // Mostrar estado inicial
        agregarLog("--- CAFETERÍA ROSAL ABRE SUS PUERTAS ---\n");
        agregarLog("Camareros listos: " + camareros.size()+ "\n");

        actualizarListaCamareros();

        // Iniciar camareros
        for(Camarero s : camareros){
            s.start();
        }
    }

    // Iniciar clientes
    public void clientesIniciar() {
        contadorClientes++;
        String nombreCliente = "Cliente " + contadorClientes;
        int tiempoEspera = (int)(Math.random()*2000) + 3000;
        Cliente nuevoCliente = new Cliente(nombreCliente,tiempoEspera,this);
        nuevoCliente.setCamareros(camareros);
        clientes.add(nuevoCliente);

        agregarLog("Llegó un nuevo cliente: " + nombreCliente);
        nuevoCliente.start();
    }

    //Ejecuta el código más tarde en el hilo que maneja la interfaz
    public void cerrarCafeteria(){
        Platform.runLater(() -> {
            if(barista!=null){
                barista.interrupt();
            }

            for (Camarero c : camareros) {
                try {
                    c.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Esperar que terminen todos los clientes
            for (Cliente c : clientes) {
                try {
                    c.join();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            agregarLog("\n--- JORNADA TERMINADA ---");
            BotonEmpezar.setDisable(false);
        });
    }

    public void agregarLog(String mensaje){
        //Ejecuta el código más tarde en el hilo que maneja la interfaz
        Platform.runLater(() -> {
            //añade el proceso al textArea
            texto.appendText(mensaje + "\n");
        });
    }

    public void agregarMensaje(String mensaje){
        Platform.runLater(() -> {
            baristaArea.appendText(mensaje + "\n");
        });
    }

    public void actualizarBuffer(){
        Platform.runLater(() -> {
            if(cola!= null){
                String informacion = "Cafés disponibles : " + cola.cafes();
                bufferArea.setText(informacion);
                String informacion2 = "Cafés servidos: " + cola.totalServidos();
                bufferArea2.setText(informacion2);
            }
        });

    }

    public void agregarMensajeCamarero(String mensaje){
        Platform.runLater(() -> {
            agregarMensaje(mensaje);
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
                for(int i=0; i<camareros.size(); i++){
                    camarerosArea.appendText("Camarero " + (i+1) + ": " + camareros.get(i).getNombre() + "\n");
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

    public ArrayList<Camarero> getCamareros() {
        return camareros;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
}
