package com.example.interfaz;

public class Camarero extends Thread{
    private String nombre;
    public Boolean ocupado;
    private HelloController controller;

    public Camarero(String nombre, Boolean ocupado, HelloController controller){
        this.nombre = nombre;
        this.ocupado = ocupado;
        this.controller = controller;
    }

    public void prepararCafe(Cliente cliente){
        try{
            //Pasamos la información al controller
            controller.agregarLog("⌛\u200B " + nombre + " preparando café para " + cliente.getNombre());

            int tiempoCafe = (int)(Math.random()*2000)+1000;
            Thread.sleep(tiempoCafe);

            //Pasamos la información al controller
            controller.agregarLog( "✅\u200B " + nombre + " terminó el café de " + cliente.getNombre() + " en " + tiempoCafe + " ms");
            cliente.CafeEntregado();
            this.ocupado = false;


        }
        catch (InterruptedException e){
            //Pasamos la información al controller
            controller.agregarLog( nombre + " interrumpido");
        }
    }

    @Override
    public void run(){
        //Pasamos la información al controller
        controller.agregarLog( nombre + " listo para trabajar");

    }

    public String getNombre() {
        return nombre;
    }

    public Boolean getOcupado() {
        return ocupado;
    }

    public HelloController getController() {
        return controller;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }

    public void setController(HelloController controller) {
        this.controller = controller;
    }
}
