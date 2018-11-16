package vitor.edu.br.malhagrafos.model;

import vitor.edu.br.malhagrafos.model.graphRota.Graph;
import vitor.edu.br.malhagrafos.model.graphVoos.GraphVoo;

public class Estrutura {

    private Graph rotas;
    private GraphVoo voos;

    public Estrutura(Graph rotas, GraphVoo voos) {
        this.rotas = rotas;
        this.voos = voos;
    }

    public Estrutura() {
        this.rotas = new Graph();
        this.voos = new GraphVoo();
    }

    public Graph getRotas() {
        return rotas;
    }

    public void setRotas(Graph rotas) {
        this.rotas = rotas;
    }

    public GraphVoo getVoos() {
        return voos;
    }

    public void setVoos(GraphVoo voos) {
        this.voos = voos;
    }

    @Override
    public String toString() {
        return "Estrutura{" +
                "rotas=" + rotas.toString()+
                ", voos=" + voos.toString() +
                '}';
    }
}
