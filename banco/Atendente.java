package banco;

import java.util.Random;

public class Atendente extends Thread {

    private final Fila naoAtendidos;
    private final Fila atendidos;
    private final int escala; 

    private long maiorTempoAtendimento = 0;
    private long somaTemposAtendimento = 0;
    private int totalServidos = 0;

    public Atendente(int id, Fila naoAtendidos, Fila atendidos, int escala) {
        super("Atendente-" + id);
        this.naoAtendidos = naoAtendidos;
        this.atendidos = atendidos;
        this.escala = escala;
    }

    @Override
    public void run() {
        Random rand = new Random();

        while (!Thread.currentThread().isInterrupted()) {
            Cliente cliente = naoAtendidos.proximo();
            if (cliente == null) break;

            long tempoServico = 30 + rand.nextInt(91);
            cliente.setTempoAtendimento(tempoServico);
            cliente.setEstado("em atendimento");

            try {
                Thread.sleep(tempoServico * escala);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                atendidos.entrar(cliente);
                break;
            }

            somaTemposAtendimento += tempoServico;
            totalServidos++;
            if (tempoServico > maiorTempoAtendimento) {
                maiorTempoAtendimento = tempoServico;
            }

            cliente.setEstado("atendido");
            atendidos.entrar(cliente);
        }
    }

    public int getTotalServidos() {
        return totalServidos;
    }

    public long getMaiorTempoAtendimento() {
        return maiorTempoAtendimento;
    }

    public double getTempoMedioAtendimento() {
        if (totalServidos == 0) return 0;
        return (double) somaTemposAtendimento / totalServidos;
    }
}
