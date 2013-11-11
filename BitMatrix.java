/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw10;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        
        for (int i=1; i<this.size; i++)
        {
            for (int j=1; j<this.size; j++)
            {
                if(this.get(i,j) == true)
                    System.out.println(i+">"+j);
            }
        }
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
    
}
