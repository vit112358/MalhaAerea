package vitor.edu.br.malhagrafos.vision;

import vitor.edu.br.malhagrafos.control.IO.GraphExport;
import vitor.edu.br.malhagrafos.control.IO.Leitura;
import vitor.edu.br.malhagrafos.control.IO.TableExport;
import vitor.edu.br.malhagrafos.control.graphOp.Controls;
import vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro.Caixeiro;
import vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro.Djikstra;
import vitor.edu.br.malhagrafos.model.Estrutura;
import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.graphRota.Vertex;

import javax.swing.JOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Leitura l = new Leitura(); 
        
        Estrutura e = l.readFile("voos.txt");
        Controls c = new Controls();

        System.out.println("==============Programa de Demonstração de Rotas Aéreas===========================");
        System.out.println("\n");
        System.out.println("==============Por Vitor Fernandes e Patricia Pieroni=============================");
        System.out.println("\n");
        System.out.println("==================================Menu===========================================");
        System.out.println("==============12 -> Gerar gráfico de rotas e tabelas de rotas e voos ============");
        System.out.println("==============13 -> Mostrar Rota enre 2 aeroportos ==============================");
        System.out.println("==============2 -> Mostrar Voos Diretos ==========================================");
        System.out.println("==============3 -> Viagem de Menor Custo entre 2 destinos ========================");
        System.out.println("==============4 -> Cobertura de Aeroportos =======================================");
        System.out.println("==============5 -> Tempo Minímo ==================================================");
        System.out.println("==============0 -> Sair ==========================================================");
        int op=-1;
        while(op!=0){
            op = Integer.parseInt(JOptionPane.showInputDialog("Digite o Código"));
            switch (op){
                case 12:
                    TableExport.exportExcel(e);
                    GraphExport.createGraph(e);
                    break;
                case 13:
                    Aeroporto a1 = l.pedeAeroporto(e.getRotas().getVertices());
                    Aeroporto b1 = l.pedeAeroporto(e.getRotas().getVertices());
                    if((a1!=null) && (b1!=null)){
                        Djikstra x = new Djikstra(e.getVoos(),e.getRotas());
                        x.execute(a1);
                        LinkedList<Aeroporto> path = x.getPath(b1);
                        for (Aeroporto aeroporto : path) {
                            System.out.println(aeroporto.toString());
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Aeroportos inválidos", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 2:
                    List<Voo> vs=c.buscaVoosDiretos(e);
                    for (Voo voo:vs) {
                        System.out.println(voo.toString());
                    }
                    break;
                case 3:
                    String origem = JOptionPane.showInputDialog("Informe a abreviação do aeroporto de origem");
                    String destino = JOptionPane.showInputDialog("Informe a abreviação do aeroporto de destino");
                    System.out.println("");
                    if(origem != null  && destino !=null)
                        c.menorCustoViagem(e.getVoos().getArestas(), origem, destino);
                    break;
                case 4:
                    Aeroporto aeroporto = l.pedeAeroporto(e.getRotas().getVertices());
                    if(aeroporto!=null){
                        List<LinkedList<Aeroporto>> rotas = new ArrayList<>();

                        Iterator it = e.getRotas().getVertices().entrySet().iterator();
                        Djikstra djikstra = new Djikstra(e.getVoos(),e.getRotas());
                        while(it.hasNext()){
                            Map.Entry par = (Map.Entry)it.next();
                            Vertex aux = (Vertex) par.getValue();
                            djikstra.execute(aeroporto);
                            if(!aeroporto.equals(aux.getAeroporto())){
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
                            JOptionPane.showMessageDialog(null,"Este Aeroporto consegue chegar em todos os outros aeroportos disponíveis");
                        }else{
                            JOptionPane.showMessageDialog(null, "Este Aeroporto não consegue levar a todos os outros");
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

                        System.out.println("Aeroportos que Afetam o Sistema Aéreo se Pararem: ");
                        for (Aeroporto escala : escalas) {
                            System.out.println(escala.toString());
                        }
                    }

                    break;
                case 5:
                    Aeroporto a = l.pedeAeroporto(e.getRotas().getVertices());

                    if(a!=null){
                        Caixeiro ca = new Caixeiro();
                        List<Aeroporto> rota = ca.montaRotas(e.getVoos(),e.getRotas(), a);

                        System.out.println("Rota");
                        for (Aeroporto aero:rota){
                            System.out.print(aero.getAbreviation()+"->");
                        }
                        System.out.println("");
                    }
                    break;
                case 0:
                    break;
            }
        }
    }
}
