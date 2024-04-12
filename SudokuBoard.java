/**
 * Class: 44-242 Data Structures
 * Author: Kaylee Harp
 * Description: create a sudoku solver using recursion
 * Due: 10/22 
 * I pledge that I have completed the programming assignment independently.
   I have not copied the code from a student or any source.
   I have not given my code to any other student.
   I have not given my code to any other student and will not share this code
   with anyone under any circumstances.
*/
package com.datastructures;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SudokuBoard
{
    private final int[][] board;
    
    public SudokuBoard(String fname) throws FileNotFoundException
    {
        board = new int[9][9];
        load(fname);
    }
    
    public void load(String fname) throws FileNotFoundException
    {
        //TODO: Milestone 2
        File file = new File(fname);
        
        Scanner scanner = new Scanner(file);
        for (int row = 0; row < 9; row++) {
            String [] values = scanner.nextLine().split(" ");
            for (int col = 0; col < 9; col++) {
                board[row][col] = Integer.parseInt(values[col]);
            }
        }
    }
    
    public void print(){
        for (int i=0; i<9; i++)
        {
            if (i%3 == 0)
                printHdiv();
            for (int j=0; j<9; j++)
            {
                if (j%3 == 0)
                    System.out.print("| ");
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println("|");     
        }
        printHdiv();
    }
    
    private static void printHdiv()
    {
        int DIV_LEN = 25;
        // divider
        for (int i=0; i<DIV_LEN; i++)
            System.out.print('-');
        System.out.println("");
    }
    
    private boolean allowedInRow(int row, int value)
    {
        // TODO: Milestone 3
        //can't have repeat values. 
        //if(0, 7) check entire row.
        for(int col = 0; col < 9 ; col ++){
            if(board[row][col] == value){
                return false;
            }    
        }
        return true;
        
    }
    
    private boolean allowedInCol(int col, int value)
    {
        // TODO: Milestone 3
        for(int row = 0; row < 9 ; row ++){
            if(board[row][col] == value){
                return false;
            }    
        }
        return true;
    }
    
    private boolean allowedInBlock(int row, int col, int value)
    {
        // TODO: Milestone 3
        
        //calculate starting row and column indexes
        int blockRow = (row / 3) * 3;
        int blockCol = (col / 3) * 3;
        
        //iterate through 3x3 block
        for (int r = blockRow; r < blockRow + 3; r++){
            for (int c = blockCol; c < blockCol + 3; c++){
                if(board[r][c] == value){
                    return false; //value already present
                }
            }
        }
        //value not found - can be added
        return true;
    }
    
    private boolean allowed(int row, int col, int value)
    {
        return allowedInRow(row, value) && allowedInCol(col, value) && 
                allowedInBlock(row, col, value);
    }
    
    // solution functions
    public boolean solve()
    {
        return solve(0,0);
    }
    
    public boolean solve(int row, int col){
        // TODO: Milestone 4
        
        // base cases
        if (col == 9){
            col = 0; //move down to next line
            row ++;
        }
        //no more board to solve
        if(row == 9){
            return true; 
        }
        
        //empty spot
        if(board[row][col] == 0){
           for(int val = 1; val <= 9; val ++){
                if(allowed(row, col, val)){
                    board[row][col] = val;
                    if(solve(row, col + 1)){ //recursive call
                        return true;
                    }
                    //reset value
                    board[row][col] = 0;   
                }
            }
            return false;
        }
        else{
            return solve(row, col + 1); //recursive call
        } 
    }
    
    public static void main(String[] args)
    {
        try {
        SudokuBoard b = new SudokuBoard("board1.txt");
        boolean solved = b.solve();
        System.out.println("Solved: " + solved);
        b.print();
        
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }
        
    }
}

