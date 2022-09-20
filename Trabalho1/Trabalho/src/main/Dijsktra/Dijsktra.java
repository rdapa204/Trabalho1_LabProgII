package main.Dijsktra;

import main.Aeroportos.Aero;

import java.util.*;

public class Dijsktra {
    //Lista estática que vai armazenar o caminho para a entrada dada
    public static List<Integer> path = new ArrayList<>();
    //Valor atribuido por simplificação e representação da origem do percurso
    private static final int NO_PARENT = -1;

    public static class AdjListNode {
        int vertex, weight;
        AdjListNode(int v, int w) {
            vertex = v;
            weight = w;
        }
        int getVertex() {
            return vertex;
        }
        int getWeight() {
            return weight;
        }
    }


    //A metodologia abaixo implementa DiJsktra, calculando a árvore de menor caminho, e retornando
    //um vetor que indica o pai de cada nó, a fim de refazer o caminho percorrido dada uma origem e um destino.
    public static int[] dijkstra(int V, ArrayList<ArrayList<AdjListNode>> graph, int src, int end) {
        //Vetor para identificar o pai de cada nó naquele percurso dado
        int[] parents = new int[V];
        //A origem não tem pai
        parents[src] = NO_PARENT;
        int[] distance = new int[V+1];
        for (int i = 0; i < V; i++) {
            distance[i] = Integer.MAX_VALUE;
        }


        distance[src] = 0;

        //Fila de prioridade implementada para distingui a prioridade dos vértices para
        //encontrar o caminho de menor custo
        PriorityQueue<AdjListNode> pq = new PriorityQueue<>(
                (v1, v2) -> v1.getWeight() - v2.getWeight());
        pq.add(new AdjListNode(src, 0));

        while (pq.size() > 0) {
            AdjListNode current = pq.poll();
            for (AdjListNode n :
                    graph.get(current.getVertex())) {
               //Aqui é necessário que o vértice atual não seja o último vértice para garantir que
                // haverá pelo menos uma escala
                if (current.vertex != end) {
                    if (distance[current.getVertex()] + n.getWeight() < distance[n.getVertex()]) {
                        distance[n.getVertex()] = n.getWeight() + distance[current.getVertex()];
                        //Adiciona-se o pai daquele vértice na posição de mesmo índice do vértice no
                        //vetor parents
                        parents[n.vertex] = current.vertex;
                        pq.add(new AdjListNode(n.getVertex(), distance[n.getVertex()]));
                    }
                }
            }
        }
        return parents;
    }


    //Função recursiva para reconstruir o caminho e armazenar na variável estática path
    private static void printPath(int end, int[] parents) {

        //O caso base é quando chegamos a raiz
        if (end == NO_PARENT) {
            return;
        }
        printPath(parents[end], parents);
        path.add(end);
    }



    //Aqui é feita a construção do grafo que é representado por uma matriz de adjacência, a qual foi implementada
    //como uma lista de listas. Então essa função é responsável por construir os vértices e grafos
    public static List<Integer> graph (ArrayList<Aero> aeroList, int source, int end) {
        int V = aeroList.size();
        ArrayList<ArrayList<AdjListNode>> graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            //Para cada posição da lista inicial é adicionada uma lista, inicialmente vazia
            graph.add(new ArrayList<>());

            //A proxima linha surge apenas para simplificação da notação
            Aero A = aeroList.get(i);
            for (int j = 0; j < V; j++) {
                Aero B = aeroList.get(j);


                //Como é condição de que deve existir pelo menos uma escala, dada a origem e o destino,
                //A posição referente a o vértice entre ambas é atribuida um valor infinito e a todos outros valores
                //é calculado a distância a partir das diferenças de longitude.
                if ((i == source && j == end) || (i == end && j == source)) {
                    graph.get(i).add(new AdjListNode(j, Integer.MAX_VALUE));
                } else {
                    //Como as diferenças de distância, são consideráveis podemos considerar apenas os
                    //valores inteiros, que já caracterizam essa distância de forma satisfatória
                    graph.get(i).add(new AdjListNode(j, (int) A.calcular_distancia(B)));
                }

            }
        }

        //É retornada a árvore de todos caminhos de menor custo
        int[] distance = dijkstra(V, graph, source, end);
        //Essa função é responsável por reconstruir o caminho percorrido, a fim de podermos determinar com exatidão o
        //percurso de custo mínimo
        printPath(end, distance);
        return path;
    }
}
