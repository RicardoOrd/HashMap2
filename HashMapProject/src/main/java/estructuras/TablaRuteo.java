package estructuras;

import java.util.ArrayList;
import java.util.List;

public class TablaRuteo {
    private TablaHash<String, Ruta> tabla;
    // Lista auxiliar para poder iterar en buscarRuta (necesario para Longest Prefix Match)
    private List<String> listaRedes;

    private int consultasExitosas;
    private int consultasFallidas;

    public TablaRuteo() {
        this.tabla = new TablaHash<>();
        this.listaRedes = new ArrayList<>();
        this.consultasExitosas = 0;
        this.consultasFallidas = 0;
    }

    public void agregarRuta(Ruta ruta) {
        String clave = ruta.getRedDestino();

        if (tabla.containsKey(clave)) {
            Ruta rutaExistente = tabla.get(clave);
            // Solo actualizar si la nueva ruta tiene MENOR métrica (es mejor) [cite: 113]
            if (ruta.getMetrica() < rutaExistente.getMetrica()) {
                tabla.put(clave, ruta);
                System.out.println("[UPDATE] Ruta mejorada: " + clave);
            }
        } else {
            tabla.put(clave, ruta);
            listaRedes.add(clave); // Guardamos la clave para poder buscarla luego
            System.out.println("[ADD] Nueva ruta: " + clave);
        }
    }

    public boolean eliminarRuta(String redDestino) {
        if (tabla.containsKey(redDestino)) {
            tabla.remove(redDestino);
            listaRedes.remove(redDestino);
            System.out.println("[DELETE] Ruta eliminada: " + redDestino);
            return true;
        }
        return false;
    }

    public Ruta buscarRuta(String ipDestino) {
        // 1. Iterar sobre las redes conocidas (simulación de Longest Prefix Match)
        for (String red : listaRedes) {
            if (red.equals("0.0.0.0/0")) continue;

            // Verificamos si la IP pertenece a esta red
            if (ipPerteneceARedSimplificado(ipDestino, red)) {
                consultasExitosas++;
                return tabla.get(red); // [cite: 158]
            }
        }

        if (tabla.containsKey("0.0.0.0/0")) {
            consultasExitosas++;
            return tabla.get("0.0.0.0/0");
        }

        consultasFallidas++;
        return null;
    }

    private boolean ipPerteneceARedSimplificado(String ip, String redCidr) {
        try {
            String[] partes = redCidr.split("/");
            String ipRed = partes[0];
            int mascara = Integer.parseInt(partes[1]);

            String[] octetosIp = ip.split("\\.");
            String[] octetosRed = ipRed.split("\\.");

            if (mascara == 8) {
                return octetosIp[0].equals(octetosRed[0]);
            }
            else if (mascara == 16) {
                return octetosIp[0].equals(octetosRed[0]) &&
                        octetosIp[1].equals(octetosRed[1]);
            }
            else if (mascara >= 24) {
                return octetosIp[0].equals(octetosRed[0]) &&
                        octetosIp[1].equals(octetosRed[1]) &&
                        octetosIp[2].equals(octetosRed[2]);
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void imprimirEstadisticas() {
        System.out.println("=== ESTADISTICAS DE LA TABLA DE RUTEO ===");
        System.out.println("Consultas exitosas: " + consultasExitosas);
        System.out.println("Consultas fallidas: " + consultasFallidas);
    }
}
