package estructuras;

public class Ruta {
    private String redDestino;
    private String mascaraSubred;
    private String nextHop;
    private String interfaz;
    private int metrica;
    private String protocolo;

    public Ruta(String redDestino, String mascaraSubred, String nextHop,
                String interfaz, int metrica, String protocolo) {
        this.redDestino = redDestino;
        this.mascaraSubred = mascaraSubred;
        this.nextHop = nextHop;
        this.interfaz = interfaz;
        this.metrica = metrica;
        this.protocolo = protocolo;
    }

    public String getRedDestino() { return redDestino; }
    public String getMascaraSubred() { return mascaraSubred; }
    public String getNextHop() { return nextHop; }
    public String getInterfaz() { return interfaz; }
    public int getMetrica() { return metrica; }
    public String getProtocolo() { return protocolo; }

    @Override
    public String toString() {
        return "Ruta{" + redDestino + " via " + nextHop + " (" + interfaz + ")}";
    }
}
