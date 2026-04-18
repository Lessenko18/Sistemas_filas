package banco;

import java.util.Random;

public class Banco {

    static final int ESCALA = 5;

    static final int TEMPO_SIMULACAO = 7200; // segundos (11h as 13h)
    static final int CHEGADA_MIN = 5;
    static final int CHEGADA_MAX = 50;
    static final int META_ESPERA = 120; // segundos simulados
    static final int MAX_ATENDENTES = 10;

    public static void main(String[] args) throws InterruptedException {
        int melhorN = -1;

        for (int n = 1; n <= MAX_ATENDENTES; n++) {
            System.out.println("\n============================================");
            System.out.println(" Simulacao com " + n + " atendente(s)");
            System.out.println("============================================");

            double mediaEspera = rodarSimulacao(n);

            System.out.printf(" Tempo medio de espera: %.1f segundos%n", mediaEspera);

            if (mediaEspera <= META_ESPERA && melhorN == -1) {
                melhorN = n;
                System.out.println(" >>> META ATINGIDA com " + n + " atendente(s)!");
            }
        }

        System.out.println("\n============================================");
        if (melhorN != -1) {
            System.out.println("Numero minimo de atendentes necessarios: " + melhorN);
        } else {
            System.out.println("Nenhuma configuracao atingiu a meta dentro de " + MAX_ATENDENTES + " atendentes.");
        }
    }

    private static double rodarSimulacao(int numAtendentes) throws InterruptedException {
        Fila naoAtendidos = new Fila(1000);
        Fila atendidos = new Fila(1000);

        Atendente[] atendentes = new Atendente[numAtendentes];
        for (int i = 0; i < numAtendentes; i++) {
            atendentes[i] = new Atendente(i + 1, naoAtendidos, atendidos, ESCALA);
            atendentes[i].start();
        }

        Thread gerador = new Thread(() -> {
            Random rand = new Random();
            int idCliente = 1;
            long inicio = System.currentTimeMillis();
            long duracaoReal = (long) TEMPO_SIMULACAO * ESCALA;

            while (System.currentTimeMillis() - inicio < duracaoReal) {
                Cliente c = new Cliente(idCliente++, "aguardando");
                naoAtendidos.entrar(c);

                int intervalo = CHEGADA_MIN + rand.nextInt(CHEGADA_MAX - CHEGADA_MIN + 1);
                try {
                    Thread.sleep((long) intervalo * ESCALA);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }, "Gerador");

        gerador.start();
        gerador.join(); 

        long timeout = System.currentTimeMillis() + 30_000;
        while (!naoAtendidos.estaVazia() && System.currentTimeMillis() < timeout) {
            Thread.sleep(100);
        }

        Thread.sleep((long) 120 * ESCALA * 2);

        for (Atendente a : atendentes) {
            a.interrupt();
        }
        for (Atendente a : atendentes) {
            a.join(3000);
        }

        int totalAtendidos = naoAtendidos.getTotalAtendidos();
        double mediaEsperaMs = naoAtendidos.getTempoMedioEspera();
        long maiorEsperaMs = naoAtendidos.getMaiorTempoEspera();

        double mediaEsperaSim = mediaEsperaMs / ESCALA;
        double maiorEsperaSim = (double) maiorEsperaMs / ESCALA;

        int totalServidos = 0;
        long maiorAtendimento = 0;
        double somaMediaAtendimento = 0;

        for (Atendente a : atendentes) {
            totalServidos += a.getTotalServidos();
            if (a.getMaiorTempoAtendimento() > maiorAtendimento) {
                maiorAtendimento = a.getMaiorTempoAtendimento();
            }
            somaMediaAtendimento += a.getTempoMedioAtendimento();
        }

        double mediaAtendimento = numAtendentes > 0 ? somaMediaAtendimento / numAtendentes : 0;

        double leadTime = mediaEsperaSim + mediaAtendimento;

        System.out.printf(" Clientes que entraram na fila:  %d%n", totalAtendidos);
        System.out.printf(" Clientes atendidos (throughput): %d%n", totalServidos);
        System.out.printf(" Maior tempo de espera:          %.1f s%n", maiorEsperaSim);
        System.out.printf(" Maior tempo de atendimento:     %d s%n", maiorAtendimento);
        System.out.printf(" Tempo medio de atendimento:     %.1f s%n", mediaAtendimento);
        System.out.printf(" Lead time medio (espera+atend): %.1f s%n", leadTime);

        return mediaEsperaSim;
    }
}