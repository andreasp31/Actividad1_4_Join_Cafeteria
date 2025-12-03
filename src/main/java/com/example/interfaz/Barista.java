package com.example.interfaz;

public class Barista extends Thread{
    private Cola cola;
    private HelloController controller;

    public Barista(Cola cola,HelloController controller) {
        this.cola = cola;
        this.controller = controller;
    }

    public void run(){
        controller.agregarMensaje("Barista inició su turno");
        try{
            //Crea 40 cafés como máximo por jornada
            for(int i=1;i<40; i++){
                //Meter los cafés hechos a la cola
                cola.put();
                Thread.sleep(3000);
            }
            controller.agregarMensaje("Barista ha terminado su turno");
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}
