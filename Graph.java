/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

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
        Graph g = new Graph("Warshall.txt");
        
        BitMatrix w = new BitMatrix(g.adjMatrix.size, g.adjMatrix.size);
        
        w = g.warshall();
        
        BitMatrix a = g.closure();
        
        a.printMatrix();
        System.out.println();
        w.printMatrix();
    }
    
    public BitMatrix closure()
    {
        //create new bitmatrices for calculations
        BitMatrix a = this.adjMatrix;
        BitMatrix result = new BitMatrix(this.adjMatrix.size, this.adjMatrix.size);
        BitMatrix temp   = new BitMatrix(this.adjMatrix.size, this.adjMatrix.size);
        
        for(int i=0; i<this.adjMatrix.size; i++)
        {
            result.rows[i] = (BitSet)(this.adjMatrix.rows[i]).clone();//clone values of the original adjacency matrix to inculde paths of size 1
            temp.rows[i] = (BitSet)(this.adjMatrix.rows[i]).clone();
        }
        
        int i=1;//we multiply together two matrices in the first step so we start from one
        while(i<this.adjMatrix.size)
        {
            temp = multiply(temp, a);
            sum(result, temp);
            i++;
        }
        
        return result;
    }
    
    /**
     * Multiplies two matrixes
     * HINT: https://courses.cs.ut.ee/MTAT.03.238/2013_fall/uploads/Main/08_alg_Graphs.6up.pdf
     * @param a
     * @return 
     */
    public static BitMatrix multiply(BitMatrix a, BitMatrix b)
    {
        BitMatrix c = new BitMatrix(a.size, a.size);//create an empty matrix
        
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
     * A small helper to sum together two matrices for the final transitive closure matrix
     * @param sum
     * @param a 
     */
    public static void sum(BitMatrix sum, BitMatrix a)
    {
        for(int i=0; i<sum.size; i++)
        {
            for(int j=0; j<sum.size; j++)
            {
                if(a.get(i,j))
                    sum.set(i,j);
            }
        }
    }
    
    /**
     * Warshall algorithm that calculates the transitive closure of the given adjacency matrix
     * @param matrix
     * @return 
     */
    public BitMatrix warshall()
    {
        BitMatrix a = new BitMatrix(this.adjMatrix.size, this.adjMatrix.size);
        //create a new bitmatrix
        for(int i=0; i<this.adjMatrix.size; i++)
        {
            a.rows[i] = (BitSet)(this.adjMatrix.rows[i]).clone();
        }
        
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
