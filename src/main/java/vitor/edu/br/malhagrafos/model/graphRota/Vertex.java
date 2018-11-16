package vitor.edu.br.malhagrafos.model.graphRota;

import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;


public class Vertex {
    private String id;
    private Aeroporto aeroporto;

    public Vertex() {
    }

    public Vertex(String id, Aeroporto aeroporto) {
        this.id = id;
        this.aeroporto = aeroporto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Aeroporto getAeroporto() {
        return aeroporto;
    }

    public void setAeroporto(Aeroporto aeroporto) {
        this.aeroporto = aeroporto;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +";"+ aeroporto.toString() +
                '}';
    }
}
