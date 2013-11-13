/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw10;

import java.util.BitSet;

/**
 * This class is used to create a two-dimensional bit-matrix for use in Graph.java to store relations of nodes in given graphs
 * Code downloaded from: http://web.engr.oregonstate.edu/~budd/Books/jds/info/src/jds/collection/BitMatrix.java
 * 
 */

/**
 * BitMatrix - two dimensional indexed collection of bit values;
 * for use with book
 * <a href="http://www.cs.orst.edu/~budd/books/jds/">Classic Data Structures 
 * in Java</a>
 * by <a href="http://www.cs.orst.edu/~budd">Timothy A Budd</a>, 
 * published by <a href="http://www.awl.com">Addison-Wesley</a>, 2001.
 *
 * @author Timothy A. Budd
 * @version 1.1 September 1999
 * @see jds.Collection
 */

public class BitMatrix {
    
    protected BitSet[] rows;
    protected int size;
    
     // constructor
    /**
     * initialize a newly created matrix of bit values
     *
     * @param numRows number of rows in new matrix
     * @param numColumns number of columns in new matrix
     */
    public BitMatrix (int numRows, int numColumns) {
            super();
            rows = new BitSet[numRows];
            for (int i = 0; i < numRows; i++)
                    rows[i] = new  BitSet(numColumns);
            
            size = numRows;
    }

            // operations
    /**
     * clear a value in the bit matrix
     *
     * @param i row index
     * @param j column index
     */
    public void clear (int i, int j) { rows[i].clear(j); }

    /**
     * get a value from the bit matrix
     *
     * @param i row index
     * @param j column index
     * @return true if the bit is set, false otherwise
     */
    public boolean get (int i, int j) { return rows[i].get(j); }

    /**
     * set a value in the bit matrix
     *
     * @param i row index
     * @param j column index
     */
    public void set (int i, int j) { rows[i].set(j); }
    
    /**
     * Multiplies two matrixes
     * HINT: https://courses.cs.ut.ee/MTAT.03.238/2013_fall/uploads/Main/08_alg_Graphs.6up.pdf
     * @param a
     * @return 
     */
    public void multiply()
    {
        for(int s=0; s<this.size; s++)
        {
            for(int t=0; t<this.size; t++)
            {
                for(int i=0; i<this.size; i++)
                {
                    if(this.get(s,i)&& this.get(i,t))
                        this.set(s,t);
                }
            }
        }
    }
    
    public void closure()
    {
        int i=1;//we multiply together two matrices in the first step so we start from one
        while(i<this.size)
        {
            this.multiply();
            i++;
        }
    }
    
    /**
     * Warshall algorithm that calculates the transitive closure of the given adjacency matrix
     * @param matrix
     * @return 
     */
    public void warshall()
    {
        for(int i=0; i<this.size; i++)
        {
            for(int s=0; s<this.size; s++)
            {
                for(int t=0; t<this.size; t++)
                {
                    if(this.get(s,i)&& this.get(i,t))
                        this.set(s,t);
                }
            }
        }
    }
    
    /**
     * An improved version of the Warshall algorithm that breaks the loop if a[s][i] is false
     * 
     */
    public void impWarshall()
    {
        for(int i=0; i<this.size; i++)
        {
            for(int s=0; s<this.size;s++)
            {
                //no point to continue on from here if the value is false
                if(this.get(s,i) == true)
                {                    
                    for(int t=0; t<this.size; t++)
                    {
                        if(this.get(i,t))
                        {
                            this.set(s,t);
                        }
                    }
                }
            } 
        }
    }
    
    /**
     * Prints the given adjacency matrix
     * Used for small data sets
     * @param matrix 
     */
    public void printMatrix()
    {
        //our values start from 1 so we start the printing cycle from 1 as well
        for (int i=0; i<this.size; i++)
        {
            for (int j=0; j<this.size; j++)
            {
                System.out.print(boolToInt(this.get(i,j))+" ");
            }
            System.out.println();
        }
    }
    
    /**
     * Prints the adjacency list for the given matrix
     * @param matrix 
     */
    public void printAdjList()
    {
        System.out.println("Adjacency list");
        
        int count = 0;
        
        for (int i=0; i<this.size; i++)
        {
            for (int j=0; j<this.size; j++)
            {
                if(this.get(i,j) == true)
                {
                    System.out.println(i+">"+j);
                    count++;
                }
                    
            }
        }
        
        System.out.println(count);
    }
    
    /**
     * A small helper that converts the given boolean type bit value to 1 or 0
     * @param val
     * @return 
     */
    public static int boolToInt(boolean val)
    {
        if (val == false)
            return 0;
        else
            return 1;
    }
    
    /**
     * A small helper that return boolean value whether two matrices are equal
     * @param w - the matrix to be compared with
     * @return 
     */
    public boolean equal(BitMatrix w)
    {
        boolean eq = true;
        for(int i=0; i<this.size; i++)
        {
            if(this.rows[i].equals(w.rows[i]) == false)
            {
                eq = false;
                break;
            }
        }
        
        return eq;
    }
    
}
