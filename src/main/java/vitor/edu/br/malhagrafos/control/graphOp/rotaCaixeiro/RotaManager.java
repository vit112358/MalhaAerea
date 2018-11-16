package vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro;

import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;

import java.util.ArrayList;

public class RotaManager {
    private static ArrayList<Aeroporto> destinationAirport = new ArrayList<Aeroporto>();

    //Adiciona uma cidade à lista de cidades
    public static void addAirport(Aeroporto city) {
        destinationAirport.add(city);
    }

    //Retorna a cidade que se encontra na posição 'index' da lista de cidades
    public static Aeroporto getAirport(int index){
        return destinationAirport.get(index);
    }

    //Retorna o número de cidades da lista
    public static int numberOfAirports(){
        return destinationAirport.size();
    }
}
