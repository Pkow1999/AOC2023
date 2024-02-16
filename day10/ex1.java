package day10;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;

public class ex1 {

    static int length = 0;
    static Vertex startVertex = null;
    static ArrayList<Vertex> allVertexes = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException{
        String inputFilePath = "Input";
        File file =  new File(inputFilePath);
        Scanner reader = new Scanner(file);

        ArrayList<char[]> pipeLineUp = new ArrayList<>(); 
        while(reader.hasNextLine())
        {
            String line = reader.nextLine();
            if(length == 0)
                length = line.length();
            char[] lineOfChars = line.toCharArray();
            pipeLineUp.add(lineOfChars);
        }
        reader.close();

        Graph graph = new Graph();
        allVertexes.ensureCapacity(length * pipeLineUp.size());
        for(int i = 0 ; i < pipeLineUp.size() * length; ++i)
            allVertexes.add(null);
        for(int i = 0 ; i < pipeLineUp.size(); ++i)
        {
            for(int j  = 0; j < length; ++j)
            {
                Vertex vertex = new Vertex(j, i);
                allVertexes.set((length * j) + i, vertex);
                graph.addVertex(vertex);
            }
                

        }

        for(int i = 0; i < pipeLineUp.size(); ++i)
        {
            for(int j = 0; j < length; ++j)
            {
                char pipeToCheck = pipeLineUp.get(i)[j];
                switch (pipeToCheck) {
                    case 'S':
                        startVertex = allVertexes.get(length * j + i);
                        break;
                    case '|':
                        checkForNorth(j, i, pipeLineUp, graph);
                        checkForSouth(j, i, pipeLineUp,graph);
                        break;
                    case '-':
                        checkForEast(j, i, pipeLineUp, graph);
                        checkForWest(j, i, pipeLineUp, graph);
                        break;
                    case 'L':
                        checkForWest(j, i, pipeLineUp, graph);
                        checkForNorth(j, i, pipeLineUp, graph);
                        break;
                    case 'J':
                        checkForNorth(j, i, pipeLineUp, graph);
                        checkForEast(j, i, pipeLineUp, graph);
                        break;
                    case '7':
                        checkForSouth(j, i, pipeLineUp,graph);
                        checkForEast(j, i, pipeLineUp, graph);
                        break;
                    case 'F':
                        checkForSouth(j, i, pipeLineUp,graph);
                        checkForWest(j, i, pipeLineUp, graph);
                        break;
                    default:
                        break;
                }
            }
        }
        System.out.println("Wynik: " + solve(graph, startVertex)/2);
    }

    static int solve(Graph graph, Vertex startVertex)
    {
        int counter = 0;
        Queue<Vertex> q = new LinkedList<>();
        HashMap<Vertex, Vertex> parent = new HashMap<>();
        HashMap<Vertex, Boolean> wasVisited = new HashMap<>();
        for(Entry<Vertex, ArrayList<Vertex>> set : graph.graphList.entrySet())
        {
            wasVisited.putIfAbsent(set.getKey(), false);
            parent.putIfAbsent(set.getKey(), null);
        }
        wasVisited.put(startVertex, true);
        q.add(startVertex);
        System.out.println("Starting in: " + startVertex.getX() + " " + startVertex.getY());
        while (!q.isEmpty()) {
            Vertex v = q.poll();
            
            ArrayList<Vertex> listOfAdjecentVertex = graph.graphList.get(v);
            for (Vertex adjVertex : listOfAdjecentVertex) {
                if(!wasVisited.get(adjVertex))
                {
                    wasVisited.put(adjVertex, true);
                    System.out.println("Visited: " + adjVertex.getX() + " " + adjVertex.getY());
                    q.add(adjVertex);
                }
            }
            ++counter;
        }
        return counter;
    }

    static void checkForEast(int x, int y, ArrayList<char[]> pipeLineUp, Graph graph)
    {
        if(x - 1 > 0)//east
        {
            char compatibilityPipe = pipeLineUp.get(y)[x - 1];
            if(compatibilityPipe == 'L' || compatibilityPipe == 'F' || compatibilityPipe == 'S' || compatibilityPipe == '-')
            {
                //length * j + i
                System.out.println("E");
                Vertex v0 = allVertexes.get((length * x) + y);
                Vertex v1 = allVertexes.get((length * (x - 1)) + y);
                graph.addEdge(v0, v1);
            }

        }
    }
    static void checkForWest(int x, int y, ArrayList<char[]> pipeLineUp, Graph graph)
    {
        if(x + 1 < length)//west
        {
            char compatibilityPipe = pipeLineUp.get(y)[x + 1];
            if(compatibilityPipe == '7' || compatibilityPipe == 'J' || compatibilityPipe == 'S' || compatibilityPipe == '-')
            {
                System.out.println("W");
                Vertex v0 = allVertexes.get((length * x) + y);
                Vertex v1 = allVertexes.get((length * (x + 1)) + y);
                graph.addEdge(v0, v1);
            }

        }
    }
    static void checkForNorth(int x, int y, ArrayList<char[]> pipeLineUp, Graph graph)
    {
        if(y - 1 > 0)//north
        {
            char compatibilityPipe = pipeLineUp.get(y - 1)[x];
            if(compatibilityPipe == '7' || compatibilityPipe == 'F' || compatibilityPipe == 'S' || compatibilityPipe == '|')
            {
                System.out.println("N");
                Vertex v0 = allVertexes.get((length * x) + y);
                Vertex v1 = allVertexes.get((length * x) + y - 1);
                graph.addEdge(v0, v1);
            }

        }
    }
    static void checkForSouth(int x, int y, ArrayList<char[]> pipeLineUp, Graph graph)
    {
        if(y + 1 < pipeLineUp.size())//south
        {
            char compatibilityPipe = pipeLineUp.get(y + 1)[x];
            if(compatibilityPipe == 'L' || compatibilityPipe == 'J' || compatibilityPipe == 'S' || compatibilityPipe == '|')
            {
                System.out.println("S");

                Vertex v0 = allVertexes.get(length * x + y);
                Vertex v1 = allVertexes.get((length * x) + y + 1);
                graph.addEdge(v0, v1); 
            }

        }
    }
}

class Vertex {
    private int x;
    private int y;
    Vertex(int x_, int y_)
    {
        x = x_;
        y = y_;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void set(int x_, int y_)
    {
        x = x_;
        y = y_;
    }
    @Override
    public String toString() {
        return "Vertex [x=" + x + ", y=" + y + "]";
    }
}
class Graph{
    Map<Vertex, ArrayList<Vertex>> graphList;
    Graph(){
        graphList = new HashMap<Vertex, ArrayList<Vertex>>();
    }
    void addVertex(int x0, int y0)
    {
        graphList.putIfAbsent(new Vertex(x0, y0), new ArrayList<>());
    }
    void addVertex(Vertex vertex)
    {
        graphList.putIfAbsent(vertex, new ArrayList<>());
    }
    void addEdge(Vertex vertex0, Vertex vertex1)
    {

        System.out.println("Adding Edge to: " + vertex0 + " and " + vertex1);
        //graf nieskierowany
        graphList.get(vertex0).add(vertex1);
        graphList.get(vertex1).add(vertex0);
        // System.out.println(vertex0 + " " + graphList.get(vertex0).size());
        // System.out.println(vertex1 + " " + graphList.get(vertex1).size());
        
    }
    
    void removeVertex(Vertex vertex)
    {
        graphList.values().stream().forEach(e -> e.remove(vertex));
        graphList.remove(vertex);
    }

}