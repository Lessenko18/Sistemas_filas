package banco;

import java.util.LinkedList;
import java.util.Queue;

public class Fila {

    private final Queue<Cliente> clientes = new LinkedList<>();
    private final int capacidade;

    private long somaTemposEspera = 0;
    private long maiorTempoEspera = 0;
    private int totalAtendidos = 0;

    public Fila(int capacidade) {
        this.capacidade = capacidade;
    }

    public synchronized void entrar(Cliente cliente) {
        while (clientes.size() >= capacidade) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        cliente.setHoraEntradaFila(System.currentTimeMillis());
        clientes.add(cliente);
        notifyAll();
    }

    public synchronized Cliente proximo() {
        while (clientes.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Cliente cliente = clientes.poll();
        long espera = System.currentTimeMillis() - cliente.getHoraEntradaFila();
        cliente.setTempoEspera(espera);

        somaTemposEspera += espera;
        totalAtendidos++;
        if (espera > maiorTempoEspera) {
            maiorTempoEspera = espera;
        }

        notifyAll();
        return cliente;
    }

    public synchronized int tamanho() {
        return clientes.size();
    }

    public synchronized boolean estaVazia() {
        return clientes.isEmpty();
    }

    public synchronized int getTotalAtendidos() {
        return totalAtendidos;
    }

    public synchronized double getTempoMedioEspera() {
        if (totalAtendidos == 0)
            return 0;
        return (double) somaTemposEspera / totalAtendidos;
    }

    public synchronized long getMaiorTempoEspera() {
        return maiorTempoEspera;
    }
}