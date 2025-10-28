public class Cliente extends Thread{
    private String nombre;
    private int tiempoEspera;
    public Camarero[] camareros;
    private boolean cafeEntregado = false;


    public Cliente(String nombre,int tiempoEspera,Camarero[] camareros){
        this.nombre = nombre;
        this.tiempoEspera = tiempoEspera;
        this.camareros  = camareros;
    }

    @Override
    public void run(){
        try{
            System.out.println(nombre + " ha llegado a la cafetería");

            Camarero ocupado = null;
            for(Camarero c: camareros){
                //Buscamos un camarero libre y si lo hay marcarlo como ocupado
                if(!c.ocupado){
                    ocupado = c;
                    c.ocupado = true;
                    break;
                }
            }
            if(ocupado != null){
                //si encuentra camarero libre
                System.out.println(nombre + " es atendido por " + ocupado.getNombre());
                //empiezan a preparar el café
                ocupado.prepararCafe(this);

                //calcular cuando empezó a esperar, esto lo busqué https://www.w3api.com/Java/System/currentTimeMillis/
                long tiempoInicio = System.currentTimeMillis();
                while(!cafeEntregado){
                    //Si ya pasó el tiempo máximo de espera
                    if(System.currentTimeMillis() - tiempoInicio > tiempoEspera){
                        System.out.println(nombre +" se cansó de esperar y se fue!");
                        return;
                    }
                    Thread.sleep(100);
                }
                //Aquí ya recibió el café
                System.out.println("LISTO: "+ nombre + " está contento con su café y se va");
            }
            else{
                //no hay camarero libre
                System.out.println("PROBLEMA: " + nombre + " no encontró camarero libre y se fue.");
            }
        }
        catch (InterruptedException e){
            System.out.println(nombre + " se ha parado.");
        }
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTiempoEspera(int tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public void CafeEntregado(){
        this.cafeEntregado = true;
    }
}
