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
 * @author Urmas
 */
public class Markov {
    private ArrayList<String> values;
    private int size;
    private double[][] probMatrix;
    
    public Markov(String fileName) throws IOException
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
        
        this.probMatrix = new double[this.size][this.size];
        
        for (int i=0; i<this.values.size(); i++)
        {
            String[] lineParts = this.values.get(i).split("\\s+");//any number of whitespace characters as delimiter

            int y = Integer.parseInt(lineParts[0]);//first node value, represents the start of edge and row number in the matrix (y-axis)
            int x = Integer.parseInt(lineParts[1]);//second node value, represents the end of edge and column number in the matrix (x-axis)
            double prob = Double.parseDouble(lineParts[2]);
            
            this.probMatrix[y][x] = prob;
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        Markov markov = new Markov("RandomWalk.txt");
        
        double[][] copy = markov.copyMatrix(markov.probMatrix);
        
        printMatrix(markov.probMatrix);
        System.exit(0);
        
        int i = 1;
        while (i <= 10)
        {
            copy = multiply(copy, markov.probMatrix);
            i++;
        }
            
        System.out.println();
        roundValues(copy);
        printMatrix(copy);
    }
    
    /**
     * Prints the given object's probability matrix
     * Used for small data sets
     * @param matrix 
     */
    public static void printMatrix(double[][] matrix)
    {
        //our values start from 1 so we start the printing cycle from 1 as well
        for (int i=1; i<matrix.length; i++)
        {
            for (int j=1; j<matrix.length; j++)
            {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    /**
     * Takes the probability matrix of the given objects an multiplies it with the parameter matrix
     */
    public static double[][] multiply(double[][] a, double[][] b)
    {
        double[][] matrix = new double[a.length][a.length];
        
        for(int s=0; s<matrix.length; s++)
        {
            for(int t=0; t<matrix.length; t++)
            {
                for(int i=0; i<matrix.length; i++)
                {
                    matrix[s][t] += a[s][i] * b[i][t];
                }
            }
        }
        return matrix;
    }
    
    public static double[][] copyMatrix(double[][] a)
    {
        double[][] copy = new double[a.length][a.length];
        
        for(int i=0; i<a.length; i++)
        {
            for(int j=0; j<a.length; j++)
            {
                copy[i][j] = a[i][j];
            }
        }
        
        return copy;
    
    }
    
    public static void roundValues(double[][] matrix)
    {
        for (int i=0; i<matrix.length; i++)
        {
            for (int j=0; j<matrix.length; j++)
            {
                matrix[i][j] = Math.round(matrix[i][j]*10000.0)/10000.0;
            }
        }
    }
    
}
