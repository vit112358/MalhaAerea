package vitor.edu.br.malhagrafos.model.graphRota;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Graph {
    private int id;
    private Map<String, Vertex> vertices;
    private List<Edges> arestas;

    public Graph(int id, Map<String, Vertex> vertices, List<Edges> arestas) {
        this.id = id;
        this.vertices = vertices;
        this.arestas = arestas;
    }

    public Graph() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Map<String, Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(Map<String, Vertex> vertices) {
        this.vertices = vertices;
    }

    public List<Edges> getArestas() {
        return arestas;
    }

    public void setArestas(List<Edges> arestas) {
        this.arestas = arestas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph graph = (Graph) o;
        return id == graph.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GraphRota{" +
                "id=" + id +
                '}';
    }
}
