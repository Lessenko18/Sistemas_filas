package cozinha;

public class Lavador implements Runnable {
    private PilhaPratos sujos;
    private PilhaPratos escorredor;

    public Lavador(PilhaPratos sujos, PilhaPratos escorredor) {
        this.sujos = sujos;
        this.escorredor = escorredor;
    }

    @Override
    public void run() {
        while (sujos.temPrato()) {
            Prato praLavar = sujos.retiraPrato();
            System.out.println("Lavando " + praLavar);
            praLavar.setEstado(Estado.Molhado);
            escorredor.adicionaPrato(praLavar);
        }
    }
}
