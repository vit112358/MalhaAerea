package vitor.edu.br.malhagrafos.control.graphOp;

import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.Estrutura;
import vitor.edu.br.malhagrafos.model.graphRota.Edges;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Controls {

    public Controls() {
    }

    /**
     * Esta função calcula a distância entre 2 pontos
     *
     * @param a: Primeiro ponto a ser calculado a distância;
     * @param b: Segundo ponto ser calculado a distância;
     * @return o valor da distância entre dois Pontos retornado pela fórmula
     * ((x_final-x_inicial)^2+(y_final-y_inicial)^2)^(1/2)
     *
     * Custo: O(1)
     */
    public long distanciaPontos(Ponto a, Ponto b) {
        double aux = Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
        return (long) aux;
    }

    /**
     * Esta função busca todos os voos diretos, ou seja que não tem escalas
     * @param voosRotas: Estrutura que simula os voos e as Rotas
     * @return lista dos Voos diretos
     */
    public List<Voo> buscaVoosDiretos(Estrutura voosRotas){
        List<Voo> voosDiretos = new ArrayList<>();
        for(Edges edges: voosRotas.getRotas().getArestas()){
            for(Voo v : voosRotas.getVoos().getArestas()){
                if ((edges.getOrigem().getAeroporto().equals(v.getOrigem())) && (edges.getDestino().getAeroporto().equals(v.getDestino()))){
                    voosDiretos.add(v);
                }
            }
        }
        return voosDiretos;
    }

    /**
     * Esta função Calcula a diferenca entre 2 horas
     * @param a: Hora A
     * @param b: Hora B
     * @return A diferença entre 2 horas em minutos
     */
    public long calculaDiferencaTempo(LocalTime a, LocalTime b){
        long result = Duration.between(b,a).toMinutes();
        if(result < 0){
            result = result*-1;
        }
        return result;
    }

    public Voo buscaVoo(List<Voo> voos, String origem, String destino){
        Voo result = new Voo();
        for (Voo v: voos
             ) {
            if(v.getOrigem().getAbreviation().equals(origem) && v.getDestino().getAbreviation().equals(destino)){
                result = v;
            }
        }
        return result;
    }
    
    public List<Voo> buscaVoos(List<Voo> voos, String origem, String destino){
        List<Voo> result = new ArrayList<>();
        for (Voo v: voos
             ) {
            if(v.getOrigem().getAbreviation().equals(origem) && v.getDestino().getAbreviation().equals(destino)){
                result.add(v);
            }
        }
        return result;
    }

    /**
     * Retorna a viagem verificando tanto a menor distancia quanto a menor duracao,
     * Retorna a viagem verificando apenas a menor distancia,
     * Retorna a viagem verificando apenas a menor duracao;
     *
     * @param voos
     * @param origem
     * @param destino
     * @return
     */
    public void menorCustoViagem(List<Voo> voos, String origem, String destino) {

        long menorDistancia = 999999999;
        long menorDist = 999999999;
        long menorDuracao = 999999999;
        long menorDur = 999999999;

        List<Voo> viagens = new ArrayList<>();

        Voo vooMenorDistancia = new Voo();
        Voo vooMenorDuracao = new Voo();
        Voo vooMenorDistanciaEDuracao = new Voo();

        for (Voo voo : voos) {

            if (voo.getOrigem().getAbreviation().equals(origem) &&
                    voo.getDestino().getAbreviation().equals(destino)) {
                viagens.add(voo);
            }
        }
        
        if(!viagens.isEmpty()){
            
        // viagens partindo da origem e destino escolhidos
        for (Voo viagem : viagens) {
            if (viagem.getDistancia() < menorDistancia) {
                menorDistancia = viagem.getDistancia();
                vooMenorDistancia = viagem;
            }

            Long tempoViagem = calculaDiferencaTempo(viagem.getChegada(), viagem.getPartida());

            if (tempoViagem < menorDuracao) {
                menorDuracao = tempoViagem;
                vooMenorDuracao = viagem;
            }

            if (viagem.getDistancia() < menorDist && tempoViagem < menorDur) {
                menorDist = viagem.getDistancia();
                menorDur = tempoViagem;
                vooMenorDistanciaEDuracao = viagem;
            }
        }

        System.out.println();
        System.out.println("Aeroport de Origem :"+origem);
        System.out.println("Aeroport de Destino :"+destino);
        System.out.println("Viagem menor distância e duração: " + vooMenorDistanciaEDuracao.toString());
        System.out.println("Distância: " + vooMenorDistanciaEDuracao.getDistancia() + "km");
        System.out.println("Duração: " + menorDur + " min");
        System.out.println();
        System.out.println("Viagem menor distância: " + vooMenorDistancia.toString());
        System.out.println("Distância: " + vooMenorDistancia.getDistancia() + "km");
        System.out.println();
        System.out.println("Viagem menor duração: " + vooMenorDuracao.toString());
        System.out.println("Duração: " + menorDur + " min");
        
        } else {
            
            System.out.println();
            System.out.println("Aeroport de Origem :"+origem);
            System.out.println("Aeroport de Destino :"+destino);
            System.out.println("Não foi encontrado viagens com a origem e destino solicitados ! :( ");
            System.out.println();
            
        }


    }
}
