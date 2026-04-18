package cozinha;

public class Prato {
    private int id;
    private Estado estado;

    public Prato(int id, Estado estado) {
        this.id = id;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Prato "+ id + ":" + estado;
    }
}
