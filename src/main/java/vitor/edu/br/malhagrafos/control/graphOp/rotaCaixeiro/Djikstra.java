package vitor.edu.br.malhagrafos.control.graphOp.rotaCaixeiro;

import com.sun.javafx.geom.Edge;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import vitor.edu.br.malhagrafos.control.graphOp.Controls;
import vitor.edu.br.malhagrafos.model.auxStruct.Aeroporto;
import vitor.edu.br.malhagrafos.model.auxStruct.Voo;
import vitor.edu.br.malhagrafos.model.graphRota.Graph;
import vitor.edu.br.malhagrafos.model.graphRota.Vertex;
import vitor.edu.br.malhagrafos.model.graphVoos.GraphVoo;

/**
 *
 * @author vitor
 */
public class Djikstra {
    
    private final List<Vertex> nodes;
    private final List<Voo> edges;
    private Set<Aeroporto> settledNodes;
    private Set<Aeroporto> unSettledNodes;
    private Map<Aeroporto, Aeroporto> predecessors;
    private Map<Aeroporto, Long> distance;
    private Controls c;

    public Djikstra(GraphVoo gv, Graph g) {
        this.nodes = new ArrayList<>(g.getVertices().values());
        this.edges = gv.getArestas();
        c = new Controls();
    }
    
    
    public void execute(Aeroporto source) {
        settledNodes = new HashSet<Aeroporto>();
        unSettledNodes = new HashSet<Aeroporto>();
        distance = new HashMap<Aeroporto, Long>();
        predecessors = new HashMap<Aeroporto, Aeroporto>();
        distance.put(source, (long)0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Aeroporto node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalTime(node);
        }
    }

    private void findMinimalTime(Aeroporto node) {
        List<Aeroporto> adjacentNodes = getNeighbors(node);
        for (Aeroporto target : adjacentNodes) {
            if (getShortestTime(target) > getShortestTime(node)
                    + getTime(node, target)) {
                distance.put(target, getShortestTime(node)
                        + getTime(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private List<Aeroporto> getNeighbors(Aeroporto node) {
        List<Aeroporto> neighbors = new ArrayList<Aeroporto>();
        for (Voo edge : edges) {
            if (edge.getOrigem().equals(node) && !isSettled(edge.getDestino())) {
                neighbors.add(edge.getDestino());
            }
        }
        return neighbors;
    }
    
    
    private Long getTime(Aeroporto node, Aeroporto target) {
        for (Voo edge : edges) {
            if (edge.getOrigem().equals(node)
                    && edge.getDestino().equals(target)) {
                return c.calculaDiferencaTempo(edge.getPartida(), edge.getPartida());
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private Aeroporto getMinimum(Set<Aeroporto> vertexes) {
        Aeroporto minimum = null;
        for (Aeroporto vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestTime(vertex) < getShortestTime(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Aeroporto vertex) {
        return settledNodes.contains(vertex);
    }
    
    private Long getShortestTime(Aeroporto destination) {
        Long d = distance.get(destination);
        if (d == null) {
            return Long.MAX_VALUE;
        } else {
            return d;
        }
    }
    
    public LinkedList<Aeroporto> getPath(Aeroporto target) {
        LinkedList<Aeroporto> path = new LinkedList<>();
        Aeroporto step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
    
}
