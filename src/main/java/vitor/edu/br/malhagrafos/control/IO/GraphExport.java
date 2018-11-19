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

import javax.swing.JFileChooser;

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
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.showOpenDialog(null);
        String pasta = jfc.getSelectedFile().getAbsolutePath();
        //System.out.println(pasta+"\\grafoRotas." + type);
        File out = new File(pasta+"\\grafoRotas." + type);   // out.gif in this example  // out.gif in this example
        //File out = new File("C:\\Users\\vitor\\Desktop\\grafoRotas.png");
        //gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type,  "dot"), out);
    }
}
