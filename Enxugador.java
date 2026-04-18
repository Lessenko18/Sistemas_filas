package cozinha;

public class Enxugador implements Runnable {
    private PilhaPratos escorredor;
    private PilhaPratos limpos;

    

    public Enxugador(PilhaPratos limpos, PilhaPratos escorredor ) {
        this.escorredor = escorredor;
        this.limpos = limpos;
    }

    @Override
    public void run() {
        while (limpos.temEspaco()) {
            Prato prato = escorredor.retiraPrato();
            System.out.println("Enxugando prato " + prato);
            prato.setEstado(Estado.Seco);
            limpos.adicionaPrato(prato);
        }
    }
}
