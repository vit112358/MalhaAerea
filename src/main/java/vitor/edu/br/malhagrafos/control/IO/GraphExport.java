/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vitor.edu.br.malhagrafos.control.IO;

import java.io.File;
import java.io.FileWriter;
import vitor.edu.br.malhagrafos.model.Estrutura;
import vitor.edu.br.malhagrafos.utils.GraphViz;

/**
 *
 * @author Patricia Pieroni
 */
public class GraphExport {
    
    private static FileWriter fileWriter = null;

    public static void createGraph(Estrutura aeroporto) {
        
        
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());

       
        gv.addln("rankdir=LR;");
        gv.addln("size=\"8,5\"");
        
        
        for (int i = 0; i < aeroporto.getRotas().getArestas().size(); i++) {
            
            String origem = aeroporto.getRotas().getArestas().get(i).getOrigem().getAeroporto().getAbreviation();
            String destino = aeroporto.getRotas().getArestas().get(i).getDestino().getAeroporto().getAbreviation();
            
            gv.addln(origem+" -- "+destino+";");
        }
        
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());
        
    String type = "png";
    File out = new File("C:\\Users\\patri\\Documents\\NetBeansProjects\\MalhaAerea\\src\\main\\resources\\grafoRotas." + type);   // out.gif in this example
    //gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type,  "dot"), out);
     
    }
    
   
    
}
