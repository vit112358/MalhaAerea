package vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.graphRota.Graph;
import vitor.edu.br.malhagrafos.model.graphRota.Vertex;
import vitor.edu.br.malhagrafos.model.graphVoos.GraphVoo;

/**
 *
 * @author vitor
 */
public class Caixeiro {
    
    public List<Aeroporto> montaRotas(GraphVoo gv, Graph g, Aeroporto origem){
        
        /*Listas*/
        List<Aeroporto> result = new ArrayList<>();
        List<Voo> voos = gv.getArestas();
        List<Vertex> aeroportos = new ArrayList<>(g.getVertices().values());
        
        /*Algoritmo De Djikstra*/
        Djikstra d = new Djikstra(gv, g);
        
        //Primeiro Destino
        Aeroporto firstDestiny;
        
        /*verifica se Terminou o While*/
        boolean terminou=false;
        /*Aleatório*/
        Random r = new Random();
        
        /*Sorteia um aeroporto e ve se a origem é igual ao destino*/
        do {            
            int pos = r.nextInt(aeroportos.size());
            firstDestiny = aeroportos.get(pos).getAeroporto();
        } while (origem.equals(firstDestiny) == true);
        
        /*Executa o Djikstra*/
        Djikstra x = new Djikstra(gv,g);
        x.execute(origem);
        LinkedList<Aeroporto> path = x.getPath(firstDestiny);
        
        /*Retira os aeroportos que já foram conferidos*/
        for (Aeroporto aeroporto : path) {
            Vertex aux = new Vertex();
            for (Vertex v : aeroportos) {
                if(v.getAeroporto().equals(aeroporto)){
                    aux = v;
                }
            }
            aeroportos.remove(aux);
        }
        
        for (Aeroporto aeroporto : path) {
            if(result.contains(aeroporto)==false){
                result.add(aeroporto);
            }
        }
        
        Aeroporto nextOrigin = new Aeroporto();
        Aeroporto proxDestiny = new Aeroporto();
        while (!terminou) {
            nextOrigin = path.get(path.size()-1);
            if(!path.isEmpty()){
                path = new LinkedList<>();
            }
            
            do {            
                int pos = r.nextInt(aeroportos.size());
                proxDestiny = aeroportos.get(pos).getAeroporto();
            } while (nextOrigin.equals(proxDestiny) == true);
            
            x.execute(nextOrigin);
            path = x.getPath(proxDestiny);
            
            if(!path.isEmpty()){
                for (Aeroporto aeroporto : path) {
                Vertex aux = new Vertex();
                for (Vertex v : aeroportos) {
                    if(v.getAeroporto().equals(aeroporto)){
                        aux = v;
                    }
                }
                aeroportos.remove(aux);
                }
            
                for (Aeroporto aeroporto : path) {
                    if(result.contains(aeroporto)==false){
                        result.add(aeroporto);
                    }
                }
            
                if(aeroportos.isEmpty()){
                    terminou = true;
                }
            }else{
                if(aeroportos.isEmpty()){
                    terminou = true;
                }
            }
        }
        return result;
    }
}
