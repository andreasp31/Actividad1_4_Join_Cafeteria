public class Main {
    public static void main(String[] args) {

        System.out.println("La cafetería Rosal ha abierto sus puertas! \n");

        Camarero camarero1 = new Camarero("Dani",false);
        Camarero camarero2 = new Camarero("Gonzalo",false);

        Camarero[] camareros = {camarero1,camarero2};

        Cliente[] clientes = {
                new Cliente("Ana",3000,camareros),
                new Cliente("Carlos",4000,camareros),
                new Cliente("Alan",2000,camareros),
                new Cliente("Marcos",1000,camareros),
                new Cliente("Lucía",5000,camareros),
                new Cliente("Sara",2000,camareros),
                new Cliente("Alex",4000,camareros),
                new Cliente("Emilio",6000,camareros),
        };

        //los camareros están listos
        for(Camarero s : camareros){
            s.start();
        }

        //Van apareciendo los clientes
        for(Cliente c : clientes){
            c.start();
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Esperar que acaben
        for(Cliente c : clientes){
            try {
                c.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n-- La cafetería ha cerrado --");
    }
}