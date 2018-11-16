package vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro;

import vitor.edu.br.malhagrafos.model.Estrutura;

public class Populacao {

    Rota[] rotas;

    //Construtor da classe
    public Populacao(int populationSize, boolean initialise, Estrutura rotasAero) {
        rotas = new Rota[populationSize];
        if (initialise) {
            //Cria várias rotas aleatórias
            for (int i = 0; i < populationSize(); i++) {
                Rota newTour = new Rota(rotasAero);
                newTour.geraAleatorio();
                saveTour(i, newTour);
            }
        }
    }

    //Guarda uma rota do array de rotas
    public void saveTour(int index, Rota tour) {
        rotas[index] = tour;
    }

    //Retorna uma rota desta população
    public Rota getTour(int index) {
        return rotas[index];
    }

    //Retorna a melhor rota (a rota com maior fitness) desta população.
    //Como já referi a rota com maior fitness será a rota com o menor comprimento
    public Rota getFittest() {
        Rota fittest = getTour(0);
        for(int i = 1; i < populationSize(); i++) {
            if(fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }
        }
        return fittest;
    }

    //Retorna o comprimento da população (o número de rotas que esta contém)
    public int populationSize() {
        return rotas.length;
    }
}
