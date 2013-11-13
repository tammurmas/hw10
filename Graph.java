/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 *
 * @author Dell
 */
public class Graph {
    
    
    private ArrayList<String> values;
    private int size;
    private BitMatrix adjMatrix;
    
    public Graph(String fileName) throws IOException
    {
        this.values = new ArrayList<String>();//data about edges from the file
        
        BufferedReader inputStream = null;

        String line;
        
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            
            while( (line = inputStream.readLine()) != null)
            {
                this.values.add(line);
            }
        }
        catch (IOException e){}
        finally{
             if (inputStream != null) {
                inputStream.close();
            }
        }
        
        this.size = 0;//size of the matrix
        
        //determine the size of the matrix
        for (int i=0; i<this.values.size(); i++)
        {
            String[] lineParts = this.values.get(i).split("\\s+");//any number of whitespace characters as delimiter
                
            int x = Integer.parseInt(lineParts[0]);
            int y = Integer.parseInt(lineParts[1]);

            if(x > this.size)
                this.size = x;

            if(y > this.size)
                this.size = y;
        }
        
        this.size++;//increment matrix size since indexing starts from 0
        
        this.adjMatrix = new BitMatrix(this.size, this.size);
        
        for (int i=0; i<this.values.size(); i++)
        {
            String[] lineParts = this.values.get(i).split("\\s+");//any number of whitespace characters as delimiter

            int y = Integer.parseInt(lineParts[0]);//first node value, represents the start of edge and row number in the matrix (y-axis)
            int x = Integer.parseInt(lineParts[1]);//second node value, represents the end of edge and column number in the matrix (x-axis)

            this.adjMatrix.set(y,x);
            
        }
    }
    
    /**
     * Constructor that creates a graph with random nodes and edges between them
     * @param nodes - number of nodes
     * @param edges - number of edges placed randomly between nodes
     */
    public Graph(int nodes, int edges)
    {
        this.size = nodes-1;
        
        this.adjMatrix = new BitMatrix(this.size, this.size);
        
        for (int i=0; i<edges; i++)
        {
            int y = randInt(0, this.size-1);
            int x = randInt(0, this.size-1);
            
            this.adjMatrix.set(y,x);
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        /*Graph g = new Graph("Warshall.txt");
        
        //create copies of the graph adjacency matrix
        BitMatrix w        = g.copyMatrix();
        BitMatrix regular  = g.copyMatrix();
        
        regular.closure();
        w.warshall();
        
        regular.printMatrix();
        System.out.println();
        
        w.printMatrix();
        System.out.println();
        
        System.out.println(w.equal(regular));//are they equal*/
        
        //Graph rand = new Graph(800,2500);
        Graph rand = new Graph("adj.txt");
        
        //create copies of the graph adjacency matrix
        BitMatrix w        = rand.copyMatrix();
        BitMatrix improved = rand.copyMatrix();
        BitMatrix regular  = rand.copyMatrix();
        
        StopWatch timer = new StopWatch();
        /*timer.start();
        regular.closure();
        timer.stop();
        
        System.out.println("Regular: "+timer.getElapsedTimeSecs());*/
        
        timer.start();
        w.warshall();
        timer.stop();
        
        System.out.println("Warshall: "+timer.getElapsedTimeSecs());
        
        timer.start();
        improved.impWarshall();
        timer.stop();
        
        System.out.println("Improved Warshall: "+timer.getElapsedTimeSecs());
        
        System.out.println(w.equal(improved));
        
        w.printMatrix();
        System.out.println();
        improved.printMatrix();
        
    }
    
    public BitMatrix copyMatrix()
    {
        BitMatrix a = this.adjMatrix;
        BitMatrix copy = new BitMatrix(this.adjMatrix.size, this.adjMatrix.size);
        
        for(int i=0; i<this.adjMatrix.size; i++)
        {
            copy.rows[i] = (BitSet)(this.adjMatrix.rows[i]).clone();//clone values of the original adjacency matrix
        }
        
        return copy;
    }
    
    
    public static void randValues(int size)
    {
        for (int i=0; i<size; i++)
        {
            int y = randInt(0, size);
            int x = randInt(0, size);
            
            System.out.println(y+">"+x);
        }
    }
    
    /**
     * A small helper for generating random integers
     * HINT: http://stackoverflow.com/questions/363681/generating-random-numbers-in-a-range-with-java
     * @param min
     * @param max
     * @return 
     */
    public static int randInt(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
            
}
