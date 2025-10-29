package com.example.interfaz;

public class Cliente extends Thread{
    private String nombre;
    private int tiempoEspera;
    public Camarero[] camareros;
    private boolean cafeEntregado = false;
    private HelloController controller;

    public Cliente(String nombre, int tiempoEspera, Camarero[] camareros, HelloController controller){
        this.nombre = nombre;
        this.tiempoEspera = tiempoEspera;
        this.camareros = camareros;
        this.controller = controller;
    }

    @Override
    public void run(){
        try{
            Camarero ocupado = null;
            for(Camarero c: camareros){
                if(!c.ocupado){
                    ocupado = c;
                    c.ocupado = true;
                    break;
                }
            }

            if(ocupado != null){
                controller.actualizarEstadoCliente(nombre, " Es atendido por " + ocupado.getNombre());
                ocupado.prepararCafe(this);

                long tiempoInicio = System.currentTimeMillis();
                while(!cafeEntregado){
                    if(System.currentTimeMillis() - tiempoInicio > tiempoEspera){
                        //Pasamos la información al controller
                        controller.actualizarEstadoCliente("❌\u200B " + nombre," Se cansó de esperar y se fue!");
                        return;
                    }
                    Thread.sleep(100);
                }
                //Pasamos la información al controller
                controller.actualizarEstadoCliente("☕\u200B " + nombre," Contento con su café - SE VA");
                controller.actualizarListaClientes("Cliente contento: " ,nombre);
            }
            else{
                //Pasamos la información al controller
                controller.actualizarEstadoCliente("❌\u200B " + nombre," No encontró camarero libre - SE VA");
                controller.actualizarListaClientes("Cliente insatisfecho: " ,nombre);
            }
        }
        catch (InterruptedException e){
            //Pasamos la información al controller
            controller.actualizarEstadoCliente(nombre, " Interrumpido");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public Camarero[] getCamareros() {
        return camareros;
    }

    public boolean isCafeEntregado() {
        return cafeEntregado;
    }

    public HelloController getController() {
        return controller;
    }

    public void CafeEntregado(){
        this.cafeEntregado = true;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public void setCamareros(Camarero[] camareros) {
        this.camareros = camareros;
    }

    public void setCafeEntregado(boolean cafeEntregado) {
        this.cafeEntregado = cafeEntregado;
    }

    public void setController(HelloController controller) {
        this.controller = controller;
    }
}
