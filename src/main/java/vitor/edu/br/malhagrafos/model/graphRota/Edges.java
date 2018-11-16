package vitor.edu.br.malhagrafos.model.graphRota;

public class Edges {
    private int id;
    private Vertex origem;
    private Vertex destino;
    private Long paradas;
    private Long duracao;
    private Long distancia;

    public Edges(int id, Vertex origem, Vertex destino) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.paradas = (long) 0;
        this.duracao = (long) 0;
        this.distancia = (long) 0;
    }

    public Edges() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vertex getOrigem() {
        return origem;
    }

    public void setOrigem(Vertex origem) {
        this.origem = origem;
    }

    public Vertex getDestino() {
        return destino;
    }

    public void setDestino(Vertex destino) {
        this.destino = destino;
    }

    public Long getParadas() {
        return paradas;
    }

    public void setParadas(Long paradas) {
        this.paradas = paradas;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }

    public Long getDistancia() {
        return distancia;
    }

    public void setDistancia(Long distancia) {
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return "Edges{" +
                "id=" + id +
                ", origem=" + origem.getAeroporto().getAirportName() +
                ", destino=" + destino.getAeroporto().getAirportName() +
                ", paradas=" + paradas +
                ", duracao=" + duracao +
                ", distancia=" + distancia +
                '}';
    }
}
