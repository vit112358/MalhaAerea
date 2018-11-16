package vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro;

import vitor.edu.br.malhagrafos.control.graphOp.Controls;
import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.Estrutura;
import vitor.edu.br.malhagrafos.model.graphRota.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rota {

    private List<Aeroporto> aeroportos = new ArrayList<>();
    private long fitness;
    private long distancia;
    private Estrutura rotas;
    private static Controls c;

    public Rota(Estrutura rotas){
        for (int i = 0; i < RotaManager.numberOfAirports(); i++) {
            aeroportos.add(null);
        }
        this.rotas = rotas;
    }

    public Rota(List<Vertex> Listaeroportos) {
        for(Vertex v: Listaeroportos){
            this.aeroportos.add(v.getAeroporto());
        }
    }

    /**
     * Cria uma rota aleatórioa
     */
    public void geraAleatorio(){
        c = new Controls();
        for(int i = 0; i < RotaManager.numberOfAirports(); i++) {
            setCity(i, RotaManager.getAirport(i));
        }
        Collections.shuffle(aeroportos);
    }

    public Aeroporto getCity(int index){
        return aeroportos.get(index);
    }

    public void setCity(int position, Aeroporto city) {
        aeroportos.set(position, city);
        //Quando a composição da rota muda, resetamos os valores do fitness e da distancia
        fitness = 0;
        distancia = 0;
    }

    public long getFitness(){
        if (fitness == 0) {
            fitness = 1/(long)getDistance();
        }
        return fitness;
    }

    public long getDistance(){
        c = new Controls();
        if (distancia == 0){
            long tourDistance = 0;
            for (int i=0; i < tourSize(); i++) {
                Aeroporto fromAirport = getCity(i);
                Aeroporto destinationAirport;
                //Coloca a ultima cidade da rota igual à primeira
                //Isto porque segundo o problema o mercador deve voltar à cidade de partida
                if(i+1 < tourSize()){
                    destinationAirport = getCity(i+1);
                }else{
                    destinationAirport = getCity(0);
                }
                //Calcula a distancia entre duas cidades e soma ao comprimento total da rota

                tourDistance += c.distanciaPontos(fromAirport.getPosition(), destinationAirport.getPosition());
            }
            distancia = tourDistance;
        }
        return distancia;
    }

    public int tourSize() {
        return aeroportos.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rota= {");
        for (Aeroporto a :this.aeroportos
             ) {
            sb.append(a.toString());
            sb.append(" ;");
        }
        sb.append(" }");
        return sb.toString();
    }

    public boolean containsCity(Aeroporto city){
        return aeroportos.contains(city);
    }
}
