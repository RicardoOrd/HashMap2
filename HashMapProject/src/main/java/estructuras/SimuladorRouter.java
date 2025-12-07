package estructuras;

public class SimuladorRouter {
    private TablaRuteo tablaRuteo;

    public SimuladorRouter() {
        this.tablaRuteo = new TablaRuteo();
        inicializarRutas();
    }

    private void inicializarRutas() {
        // Default Gateway
        tablaRuteo.agregarRuta(new Ruta("0.0.0.0/0", "0.0.0.0", "200.100.50.1", "eth0", 10, "STATIC"));
        // Red Local
        tablaRuteo.agregarRuta(new Ruta("192.168.1.0/24", "255.255.255.0", "0.0.0.0", "eth1", 0, "CONNECTED"));
        // Red OSPF
        tablaRuteo.agregarRuta(new Ruta("10.0.0.0/8", "255.0.0.0", "192.168.1.254", "eth1", 20, "OSPF"));
        // Red DMZ
        tablaRuteo.agregarRuta(new Ruta("172.16.0.0/16", "255.255.0.0", "192.168.1.253", "eth2", 5, "STATIC"));
    }

    public void procesarPaquetes() {
        String[] paquetesEntrantes = {
                "192.168.1.50", // Debe ir a Local
                "10.5.3.2",     // Debe ir a OSPF
                "172.16.10.5",  // Debe ir a DMZ
                "8.8.8.8",      // Debe ir a Default Gateway
                "192.168.2.1"
        };

        System.out.println("\n===== SIMULADOR DE ROUTER FORWARDING =====");

        for (String ipDestino : paquetesEntrantes) {
            System.out.println("------------------------------------------------");
            System.out.println("Paquete -> " + ipDestino);

            Ruta ruta = tablaRuteo.buscarRuta(ipDestino);

            if (ruta != null) {
                System.out.println("Ruta encontrada: " + ruta.getRedDestino());
                System.out.println("Next Hop: " + ruta.getNextHop());
                System.out.println("Interfaz salida: " + ruta.getInterfaz());
                System.out.printf("Protocolo: %s (metrica: %d)%n", ruta.getProtocolo(), ruta.getMetrica());
                System.out.println("ACCION: FORWARD");
            } else {
                System.out.println("ACCION: DROP (No route to host)");
            }
        }
        System.out.println("================================================");
        tablaRuteo.imprimirEstadisticas();
    }

    public static void main(String[] args) {
        SimuladorRouter simulador = new SimuladorRouter();
        simulador.procesarPaquetes();
    }
}