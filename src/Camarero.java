import java.sql.SQLOutput;

public class Camarero extends Thread{
    private String nombre;
    public Boolean ocupado;

    public Camarero(String nombre,Boolean ocupado){
        this.nombre = nombre;
        this.ocupado = ocupado;
    }

    public void prepararCafe(Cliente cliente){
        try{
            System.out.println(nombre + " está preparando café para " + cliente.getNombre() + ".......");
            //tiempo random de preparación de café
            int tiempoCafe = (int)(Math.random()*2000)+1000;
            Thread.sleep(tiempoCafe);
            System.out.println(nombre + " ha terminado el café de " + cliente.getNombre() + "!!");
            //Esto lleva a lo de café entregado
            cliente.CafeEntregado();
            this.ocupado = false;
        }
        catch (InterruptedException e){
            System.out.println(nombre + " se ha interrumpido");
        }
    }

    @Override
    public void run(){
        System.out.println(nombre + " está listo para atender \n");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
