package com.example.interfaz;

public class Camarero extends Thread{
    private String nombre;
    public Boolean ocupado;
    private HelloController controller;
    //Añadir la cola para obtener los cafés
    private Cola cola;

    public Camarero(String nombre, Boolean ocupado, HelloController controller,Cola cola){
        this.nombre = nombre;
        this.ocupado = ocupado;
        this.controller = controller;
        this.cola = cola;
    }

    public void prepararCafe(Cliente cliente){
        try{
            //Pasamos la información al controller
            controller.agregarLog("⌛\u200B " + nombre + " ha tomado nota del pedido de:  " + cliente.getNombre());

            //Obtenemos un café de la Cola
            cola.get(nombre);

            //Este ahora es el tiempo de servir
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
