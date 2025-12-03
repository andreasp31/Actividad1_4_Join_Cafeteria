package com.example.interfaz;

import javafx.application.Platform;

import static java.lang.Thread.sleep;

public class Cola {
    private int cafes;
    private int capacidadMaxima = 40;
    private HelloController controller;
    private int preparadosTotal = 0;
    private int servidosTotal = 0;

    public Cola(int cafes, HelloController controller) {
        this.cafes = cafes;
        this.controller = controller;
    }

    public synchronized void put(){
        try {
            //Si la cantidad de cafés es igual o menos que la cantidad maxima pues el barista tiene que esperar
            //que el camarero recoga cafés
            while (cafes >= capacidadMaxima) {
                agregarMensaje("El barista espera - Buffer lleno: " + cafes + "/" +  capacidadMaxima );
                wait();
            }
            //Si puede meter más cafés los hace y nos suma cuantos tiene en total y notificamos a todos
            Thread.sleep(500);
            cafes++;
            preparadosTotal++;
            agregarMensaje("Barista prepara café, " + "lleva: " + cafes);
            agregarMensaje("Total preparados: " + preparadosTotal);
            controller.actualizarBuffer();
            notifyAll();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void get(String camareroNombre){
        try {
            //Si la cantidad de cafes es 0, el camarero tiene que esperar al que el barista añada café
            while (cafes == 0) {
                agregarMensaje(camareroNombre + " espera - Buffer vacío");
                wait();
            }
            //Si puede retirar lo retira y nos lo añade a cafés servidos
            Thread.sleep(300);
            cafes--;
            servidosTotal++;
            agregarMensajeCamarero("Café está siendo recogido por el camarero");
            agregarMensaje(camareroNombre +  " recogió un café");
            controller.actualizarBuffer();
            notifyAll();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Para pasar los mensajes a un textArea después
    public void agregarMensaje(String mensaje){
        Platform.runLater(() -> {
            controller.agregarMensaje(mensaje);
        });
    }
    //Para pasar los mensajes a otro textArea
    public void agregarMensajeCamarero(String mensaje){
        Platform.runLater(() -> {
            controller.agregarLog(mensaje);
        });
    }

    public synchronized int totalPreparados(){
        return preparadosTotal;
    }

    public synchronized int totalServidos(){
        return servidosTotal;
    }

    public synchronized int cafes(){
        return cafes;
    }
}

