package vitor.edu.br.malhagrafos.vision;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import vitor.edu.br.malhagrafos.control.IO.Leitura;
import vitor.edu.br.malhagrafos.control.graphOp.Controls;
import vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro.Caixeiro;
import vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro.Djikstra;
import vitor.edu.br.malhagrafos.model.Estrutura;
import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.graphRota.Vertex;

public class Main {

    public static void main(String[] args) {

        Leitura l = new Leitura(); 
        
        Estrutura e = l.readFile("voos.txt");
        Controls c = new Controls();

        System.out.println("==============Programa de Demonstração de Rotas Aéreas============================");
        System.out.println("\n");
        System.out.println("==============Por Vitor Fernandes e Patricia Pieroni==============================");
        System.out.println("\n");
        System.out.println("==================================Menu============================================");
        System.out.println("==============11 -> Mostrar Rotas e Voos ========================================");
        System.out.println("==============12 -> Mostrar Rota enre 2 aeroportos ==============================");
        System.out.println("==============2 -> Mostrar Voos Diretos ==========================================");
        System.out.println("==============3 -> Viagem de Menor Custo entre 2 destinos ========================");
        System.out.println("==============4 -> Cobertura de Aeroportos =======================================");
        System.out.println("==============5 -> Tempo Minímo ==================================================");
        System.out.println("==============0 -> Sair ==========================================================");
        int op=-1;
        while(op!=0){
            op = Integer.parseInt(JOptionPane.showInputDialog("Digite o Código"));
            switch (op){
                case 11:
                    break;
                case 12:
                    List<Voo> v=c.buscaVoosDiretos(e);
                    v.forEach((voo) -> {
                        System.out.println(voo.toString());
                    });
                    break;
                case 13:
                    Aeroporto a1 = l.findAeroporto("ABQ", e.getRotas().getVertices());
                    Aeroporto b1 = l.findAeroporto("BNA", e.getRotas().getVertices());
                    Djikstra x = new Djikstra(e.getVoos(),e.getRotas());
                    x.execute(a1);
                    LinkedList<Aeroporto> path = x.getPath(b1);
                    for (Aeroporto aeroporto : path) {
                        System.out.println(aeroporto.toString());
                    }
                    break;    
                case 2:
                    break;
                case 3:
                    c.menorCustoViagem(e.getVoos().getArestas(), "ABQ", "ATL");
                    break;
                case 4:
                    Aeroporto abq = l.findAeroporto("ABQ", e.getRotas().getVertices());
                    List<LinkedList<Aeroporto>> rotas = new ArrayList<>();
                    
                    Iterator it = e.getRotas().getVertices().entrySet().iterator();
                    Djikstra djikstra = new Djikstra(e.getVoos(),e.getRotas());
                    while(it.hasNext()){
                        Map.Entry par = (Map.Entry)it.next();
                        Vertex aux = (Vertex) par.getValue();
                        djikstra.execute(abq);
                        if(!abq.equals(aux.getAeroporto())){
                            LinkedList<Aeroporto> rota = djikstra.getPath(aux.getAeroporto());
                            rotas.add(rota);
                        }
                    }
                    boolean verificaRota = false;
                    for(LinkedList<Aeroporto> rota: rotas){
                        if(rota == null){
                            verificaRota = true;
                            break;
                        }
                    }
                    
                    if(!verificaRota){
                        JOptionPane.showMessageDialog(null, "Este Aeroporto consegue chegar em todos os outros aeroportos disponíveis", "Mensagem", JOptionPane.OK_OPTION);
                    }else{
                        JOptionPane.showMessageDialog(null, "Este Aeroporto não consegue levar a todos os outros", "Mensagem", JOptionPane.ERROR_MESSAGE);
                    }
                    List<Aeroporto> escalas = new ArrayList<>();
                    for(LinkedList<Aeroporto> rota: rotas){
                        if(rota.size()>2){
                            for(int i=1; i<rota.size()-1;i++){
                                if(!escalas.contains(rota.get(i))){
                                    escalas.add(rota.get(i));
                                }
                            }
                        }
                    }
                    
                    for (Aeroporto escala : escalas) {
                        System.out.println(escala.toString());
                    }
                    
                    break;
                case 5:
                    Aeroporto a = l.findAeroporto("ABQ", e.getRotas().getVertices());
                    
                    Caixeiro ca = new Caixeiro();
                    ca.montaRotas(e.getVoos(),e.getRotas(), a);                                       
                    break;
                case 0:
                    break;
            }
        }
    }
    
    private File getFile(String fileName) {
	//Get file from resources folder
	ClassLoader classLoader = getClass().getClassLoader();
	return new File(classLoader.getResource(fileName).getFile());
    }
}
