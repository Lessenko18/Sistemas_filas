package banco;

public class Cliente {

    private int id;
    private String estado;
    private long horaEntradaFila;
    private long tempoEspera;

    public Cliente(int id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getHoraEntradaFila() {
        return horaEntradaFila;
    }

    public void setHoraEntradaFila(long horaEntradaFila) {
        this.horaEntradaFila = horaEntradaFila;
    }

    public long getTempoEspera() {
        return tempoEspera;
    }

    public void setTempoEspera(long tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    private long tempoAtendimento;

    public long getTempoAtendimento() {
        return tempoAtendimento;
    }

    public void setTempoAtendimento(long tempoAtendimento) {
        this.tempoAtendimento = tempoAtendimento;
    }

}
