package vitor.edu.br.malhagrafos.vision;

import vitor.edu.br.malhagrafos.control.graphOp.Controls;
import vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro.GeneticAlgorithm;
import vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro.Populacao;
import vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro.RotaManager;
import vitor.edu.br.malhagrafos.control.IO.Leitura;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.Estrutura;
import vitor.edu.br.malhagrafos.model.graphRota.Vertex;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.List;
import java.util.Map;
import vitor.edu.br.malhagrafos.control.IO.GraphExport;
import vitor.edu.br.malhagrafos.control.IO.TableExport;

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
        System.out.println("==============12 -> Gerar gráfico de rotas e tabelas de rotas e voos ================");
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
                    break;
                case 2:
                    System.out.println("");
                    List<Voo> v=c.buscaVoosDiretos(e);
                    for (Voo voo:v) {
                        System.out.println(voo.toString());
                    }
                    break;
                case 3:
                    String origem = JOptionPane.showInputDialog("Informe a origem");
                    String destino = JOptionPane.showInputDialog("Informe o destino");
                    System.out.println("");
                    if(origem != null  && destino !=null)
                        c.menorCustoViagem(e.getVoos().getArestas(), origem, destino);
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("");
                    for (Map.Entry<String, Vertex> aeroporo: e.getRotas().getVertices().entrySet()) {
                        RotaManager.addAirport(aeroporo.getValue().getAeroporto());
                    }

                    Populacao p = new Populacao(50, true, e);

                    System.out.println("Distância inicial: " + (int)p.getFittest().getDistance());

                    //Evoluí a população por 200 gerações
                    for (int i = 0; i < 200; i++) {
                        p = GeneticAlgorithm.evolvePopulation(p);
                    }

                    //Imprime o comprimento da melhor rota da população final
                    System.out.println("Distância final: " + (int)p.getFittest().getDistance());
                    //Imprime a solução encontrada para o problema
                    System.out.println("Solução:");
                    System.out.println(p.getFittest());
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
