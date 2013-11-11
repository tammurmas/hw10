/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
    
    public static void main(String[] args) throws IOException
    {
        Graph g = new Graph("adj.txt");
        
        g.adjMatrix.printMatrix();
        
        System.out.println();
        
        BitMatrix a = warshall(g.adjMatrix);
        
        System.out.println();
        
        a.printMatrix();
        
        System.out.println();
        
        //g.adjMatrix.printAdjList();
        
        BitMatrix gg = multiply(g.adjMatrix, g.adjMatrix);
        
        gg.printMatrix();
        
        /*gg.printAdjList();
        
        BitMatrix ggg = multiply(gg, g.adjMatrix);
        
        ggg.printMatrix();*/
        
        //ggg.printAdjList();
    }
    
    /**
     * Multiplies matrix to itself the given times
     * HINT: https://courses.cs.ut.ee/MTAT.03.238/2013_fall/uploads/Main/08_alg_Graphs.6up.pdf
     * @param numOfTimes - the number of times matrix is going to be multiplied to itself
     * @return 
     */
    public static BitMatrix multiply(BitMatrix a, BitMatrix b)
    {
        BitMatrix c = new BitMatrix(a.size, b.size);//create an empty matrix
        
        for(int s=0; s<c.size; s++)
        {
            for(int t=0; t<c.size; t++)
            {
                for(int i=0; i<c.size; i++)
                {
                    if(a.get(s,i)&& b.get(i,t))
                        c.set(s,t);
                }
            }
        }
            
        return c;
    }
    
    /**
     * Warshall algorithm
     * @param matrix
     * @return 
     */
    public static BitMatrix warshall(BitMatrix matrix)
    {
        BitMatrix a = new BitMatrix(matrix.size, matrix.size);
        a = matrix;
        
        for(int i=0; i<a.size; i++)
        {
            for(int s=0; s<a.size; s++)
            {
                for(int t=0; t<a.size; t++)
                {
                    if(a.get(s,i)&& a.get(i,t))
                        a.set(s,t);
                }
            }
        }
        
        return a;
    }
            
}
