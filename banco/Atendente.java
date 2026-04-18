package banco;

public class Atendente {
    private Fila naoAtendidos;
    private Fila atendidos;

    public Atendente(Fila naoAtendidos, Fila atendidos) {
        this.naoAtendidos = naoAtendidos;
        this.atendidos = atendidos;
    }
}
