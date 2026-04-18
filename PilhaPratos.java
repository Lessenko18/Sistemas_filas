package cozinha;

import java.util.Arrays;

public class PilhaPratos {
    private Prato[] pratos;
    private int qtde;

    public PilhaPratos(int qtde) {
        pratos = new Prato[qtde];
    }

    public synchronized void adicionaPrato(Prato prato) {
        while (qtde >= pratos.length) {
            System.out.println(Thread.currentThread().getName() +": Sem espaço, vou esperar");
            try {
                wait();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(Thread.currentThread().getName() +": Adicionando " + prato);
        pratos[qtde] = prato;
        qtde++;
        notifyAll();
    }

    public synchronized Prato retiraPrato() {
        while (qtde == 0) {
            System.out.println(Thread.currentThread().getName() +": Sem pratos, vou esperar");
            try {
                wait();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        qtde--;
        Prato prato = pratos[qtde];
        pratos[qtde] = null;
        notifyAll();
        return prato;
    }

    public boolean temPrato() {
        return qtde > 0;
    }

    public boolean temEspaco() {
        return qtde < pratos.length;
    }

    @Override
    public String toString() {
        return "PilhaPratos: " + Arrays.toString(pratos);
    }
}
