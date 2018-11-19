package vitor.edu.br.malhagrafos.control.IO;

import vitor.edu.br.malhagrafos.control.graphOp.Controls;
import vitor.edu.br.malhagrafos.control.graphOp.Ponto;
import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.Estrutura;
import vitor.edu.br.malhagrafos.model.graphRota.Edges;
import vitor.edu.br.malhagrafos.model.graphRota.Graph;
import vitor.edu.br.malhagrafos.model.graphRota.Vertex;
import vitor.edu.br.malhagrafos.model.graphVoos.GraphVoo;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Leitura {

    private String tipoLeitura = "";

    public Estrutura readFile(String caminho){
        String tipo;
        Map<String, Vertex> vertices = new HashMap<>();
        List<Edges> arestas = new ArrayList<>();
        Graph rota = new Graph(1, vertices, arestas);
        List<Voo> listaVoos = new ArrayList<>();
        GraphVoo voos = new GraphVoo(2, listaVoos);
        Estrutura estruturaGrafos = new Estrutura();
        Controls c = new Controls();
        
        ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource(caminho).getFile());
        
        try {
            try (BufferedReader meuBuffer = new BufferedReader(new FileReader(file))) {
                String linha;
                //enquanto eu conseguir ler o arquivo faço algo
                while (meuBuffer.ready()) {
                    //lendo a linha
                    linha = meuBuffer.readLine();

                    tipo = confereTipo(linha);

                    switch (tipo){
                        case("aeroporto"):
                            if (!(linha.startsWith("#"))){
                                //quebrando a linha
                                String[] valores = linha.split("\\s+");
                                if (valores.length>5){
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 4;i<valores.length;i++){
                                        sb.append(valores[i]);
                                        sb.append(" ");
                                    }
                                    valores[4] = sb.toString();
                                }
                                Ponto p = new Ponto(Integer.parseInt(valores[2]),Integer.parseInt(valores[3]));
                                Aeroporto a = new Aeroporto(valores[0], valores[1], p, valores[4]);
                                Vertex vertice = new Vertex(a.getAbreviation(),a);
                                vertices.put(a.getAbreviation(),vertice);
                            }
                            break;
                        case("rota"):
                            if((!(linha.contains("!"))) && (!(linha.equals("")))){
                                String[] valores = linha.split("\\s+");
                                Vertex origem = vertices.get(valores[0]);
                                Vertex destino = vertices.get(valores[1]);
                                Edges aresta = new Edges(arestas.size()+1,origem,destino);
                                arestas.add(aresta);
                            }
                            break;
                        case("agenda"):
                            if(!(linha.startsWith("#"))){
                                String[] valores = linha.split("\\s+");
                                LocalTime lt1 = converteStringToDateTime(valores[3]);
                                LocalTime lt2 = converteStringToDateTime(valores[5]);
                                Aeroporto a1 = findAeroporto(valores[2],vertices);
                                Aeroporto a2 = findAeroporto(valores[4],vertices);
                                Long t = c.calculaDiferencaTempo(lt1, lt2);
                                a1.addDestination(t, a2);
                                long distancia = c.distanciaPontos(a1.getPosition(),a2.getPosition());
                                Voo v = new Voo(valores[0],valores[1],a1,lt1,a2,lt2,Integer.parseInt(valores[7]),distancia);
                                listaVoos.add(v);
                            }
                            break;
                        default:
                            break;
                    }
                }
                rota.setVertices(vertices);
                rota.setArestas(arestas);
                voos.setArestas(listaVoos);
                estruturaGrafos.setRotas(rota);
                estruturaGrafos.setVoos(voos);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler o Caminho! "
                    + "Verifique o caminho", "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Leitura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Leitura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return estruturaGrafos;
    }

    private String confereTipo(String linha){
        if (linha.contains("#Airport information - USA")){
            tipoLeitura = "aeroporto";
        }else if(linha.startsWith("!")){
            tipoLeitura = "rota";
        }else if(linha.contains("#Airplane information")){
            tipoLeitura = "aviao";
        }else if(linha.contains("#Airline Flight Schedule - USA")){
            tipoLeitura = "agenda";
        }

        return tipoLeitura;
    }

    private LocalTime converteStringToDateTime(String hora){
        char[] parteHoras = hora.toCharArray();
        int pHora = 0;
        int pMin = 0;
        if(parteHoras[parteHoras.length-1] == 'P'){
            if(Integer.parseInt(hora.substring(0,hora.length()-3))+12 == 24){
                pHora = 0;
            }else{
                pHora = Integer.parseInt(hora.substring(0,hora.length()-3))+12;
            }
            pMin = Integer.parseInt(hora.substring(hora.length()-3,hora.length()-1));
        }else{
            pHora = Integer.parseInt(hora.substring(0,hora.length()-3));
            pMin = Integer.parseInt(hora.substring(hora.length()-3,hora.length()-1));
        }
        return  LocalTime.of(pHora,pMin);
    }

    public Aeroporto findAeroporto(String codAeroporto, Map<String, Vertex> vertices){
        return vertices.get(codAeroporto).getAeroporto();
    }
    
    public Aeroporto pedeAeroporto(Map<String, Vertex> vertices){
        String abreviation = JOptionPane.showInputDialog(null, "Digite a Sigla do Aeroporto", "");
        Aeroporto a = findAeroporto(abreviation, vertices);
        if(a!=null){
            return a;
        }else{
            throw new RuntimeException("Erro Aeroporto não Encontrado");
        }
    }
}
