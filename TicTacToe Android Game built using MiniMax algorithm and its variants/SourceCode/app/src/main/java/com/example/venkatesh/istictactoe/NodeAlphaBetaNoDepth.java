package com.example.venkatesh.istictactoe;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Venkatesh on 11/12/2016.
 */
public class NodeAlphaBetaNoDepth {

    char [][] matrix;

    //creates matrix of size of N*N where n is the size specified by the user. Assigns the ‘\0’ as the value to the matrix
    public NodeAlphaBetaNoDepth() {

        this.matrix = new char[MainActivity.size][MainActivity.size];

        for(int i=0;i<MainActivity.size;i++)
        {
            for (int j=0;j<MainActivity.size;j++)
            {
                matrix[i][j]='\0';
            }
        }
    }

    //place() → places X in the matrix to identify as the computer’s move and places O in the matrix to identify as the player’s move
    public void place(int row, int col,MainActivity.Seed x)
    {
        if(x==MainActivity.myseed)
            matrix[row][col]='X';
        else
            matrix[row][col]='O';
    }


    //getCompNextMove() → Checks if the solution state has been reached else calls the minimax function to get the computer’s next move
    int[] getCompNextMove()
    {

        int ifend = checkend();

        if(ifend>0)
        {
            return new int[] {-1,-1,ifend};

        }
        else
        {
            int[] temp = new int[2];
            temp = minimax(MainActivity.myseed,Integer.MIN_VALUE,Integer.MAX_VALUE);
            return new int[] {temp[1],temp[2],0};

        }

    }



    //minimax() → it’s a recursive function which implements the minimax algorithm with alpha beta pruning
    private int[] minimax(MainActivity.Seed player, int alpha, int beta) {
        // Generate possible next moves in a List of int[2] of {row, col}.
        List<int[]> nextMoves = generateMoves();

        // mySeed is maximizing; while oppSeed is minimizing
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        Log.d("debug1","doingwork "+alpha+" "+beta);

        if (nextMoves.isEmpty()) {
            // Gameover, evaluate score
            currentScore = evaluate();
            return new int[] {currentScore, bestRow, bestCol};

        } else {
            for (int[] move : nextMoves) {
                // Try this move for the current "player"

                MainActivity.countnodes++;

                if(player==MainActivity.Seed.Cross)
                    matrix[move[0]][move[1]]='X';
                else
                    matrix[move[0]][move[1]]='O';

                if (player == MainActivity.myseed) {  // mySeed (computer) is maximizing player
                    currentScore = minimax(MainActivity.oppseed,alpha,beta)[0];
                    if (currentScore > alpha) {
                        alpha=currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // oppSeed is minimizing player
                    currentScore = minimax(MainActivity.myseed,alpha,beta)[0];
                    if (currentScore < beta) {
                        beta = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // Undo move
                matrix[move[0]][move[1]] = '\0';
            }
        }

        if(player==MainActivity.myseed)
            return new int[] {alpha, bestRow, bestCol};
        else
            return new int[] {beta, bestRow, bestCol};
    }



    //generateMoves() → generates the next set of available moves that the computer can play.
    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List

        // If gameover, i.e., no next move
        if (checkend()>0) {
            return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        for (int row = 0; row < MainActivity.size; ++row) {
            for (int col = 0; col < MainActivity.size; ++col) {
                if (matrix[row][col] == '\0') {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }


    //evaluate() → It is called by the minimax function. Evaluates each move or the state of the matrix and returns the evaluated value back to the minimax function.
    private int evaluate() {
        int score = 0;

        for(int i=0;i<MainActivity.size;i++)
        {
            score=score+evaluateeachrow(i);
        }

        for(int i=0;i<MainActivity.size;i++)
        {
            score=score+evaluateeachcol(i);
        }

        score = score + evaluateeachdiag1();
        score =  score + evaluateeachdiag2();

        return score;

    }

    //evaluateeachdiag1() → This function is called by the evaluate() function. This function evaluates the primary diagonal of the matrix and returns the value back to the evaluate function.
    public int evaluateeachdiag1()
    {
        int score=0;

        for (int i=0;i<MainActivity.size;i++)
        {
            for (int j=0;j<MainActivity.size;j++)
            {
                if(i==j)
                {

                    if(matrix[i][j]=='X')
                    {
                        if(score>0)
                        {
                            score=score*10;
                        }
                        else if(score<0)
                        {
                            return 0;
                        }
                        else
                        {
                            score=10;
                        }

                    }
                    else if(matrix[i][j]=='O')
                    {
                        if(score<0)
                        {
                            score=score*10;
                        }
                        else if(score>0)
                        {
                            return 0;
                        }
                        else
                        {
                            score=-10;
                        }

                    }

                }
            }
        }

        return score;
    }

    //evaluateeachdiag2() → This function is called by the evaluate() function. This function evaluates the secondary diagonal of the matrix and returns the value back to the evaluate function.
    public int evaluateeachdiag2()
    {
        int score=0;

        for (int i=0;i<MainActivity.size;i++)
        {
            for (int j=0;j<MainActivity.size;j++)
            {
                if(i+j==MainActivity.size-1)
                {

                    if(matrix[i][j]=='X')
                    {
                        if(score>0)
                        {
                            score=score*10;
                        }
                        else if(score<0)
                        {
                            return 0;
                        }
                        else
                        {
                            score=10;
                        }

                    }
                    else if(matrix[i][j]=='O')
                    {
                        if(score<0)
                        {
                            score=score*10;
                        }
                        else if(score>0)
                        {
                            return 0;
                        }
                        else
                        {
                            score=-10;
                        }

                    }

                }
            }
        }

        return score;
    }

    //evaluateeachrow() → This function is called by the evaluate() function. This function evaluates the each row of the matrix and returns the value back to the evaluate function.
    public int evaluateeachrow(int i)
    {
        int score=0;
        int emptycells=0;

        for(int j=0;j<MainActivity.size;j++)
        {
            if(matrix[i][j]=='X'){

                if(score>0)
                {
                        score=score*10;
                }
                else if(score<0)
                {
                    return 0;
                }
                else {
                    score = 10;
                }
            }
            else if(matrix[i][j]=='O')
            {
                if(score<0)
                {
                    score=score*10;
                }
                else if(score>0)
                {
                    return 0;
                }
                else {

                    score=-10;
                }
            }
            else if(matrix[i][j]=='\0')
            {
                emptycells++;
            }

        }

        return score+emptycells;
    }


    //evaluateeachcol() → This function is called by the evaluate() function. This function evaluates the each column of the matrix and returns the value back to the evaluate function.
    public int evaluateeachcol(int j)
    {
        int score=0;
        int emptycells=0;

        for(int i=0;i<MainActivity.size;i++)
        {
            if(matrix[i][j]=='X'){

                if(score>0)
                {
                    score=score*10;
                }
                else if(score<0)
                {
                    return 0;
                }
                else {
                    score = 10;
                }
            }
            else if(matrix[i][j]=='O')
            {
                if(score<0)
                {
                    score=score*10;
                }
                else if(score>0)
                {
                    return 0;
                }
                else {

                    score=-10;
                }
            }
            else if(matrix[i][j]=='\0')
            {
                emptycells++;
            }

        }

        return score;
    }



    //checkend() → This function checks the matrix to see if the goal state has been reached. Like Computer Won or Player Won or Game Drawn or still the game on.
    public int checkend()
    {
        int count=0;

        int [] winrow= new int[MainActivity.size];
        int [] wincol= new int[MainActivity.size];
        int  windiag1=0;
        int  windiag2=0;

        int foundwin=0;

        int checkdraw=0;

        for(int i=0;i<MainActivity.size;i++)
        {
            for(int j=0;j<MainActivity.size;j++)
            {
                if(matrix[i][j]=='X')
                {
                    checkdraw++;

                    winrow[i]++;
                    wincol[j]++;

                    if(i==j)
                        windiag1++;

                    if((i+j)==MainActivity.size-1)
                        windiag2++;
                }
                else if(matrix[i][j]=='O')
                {
                    checkdraw++;

                    winrow[i]--;
                    wincol[j]--;

                    if(i==j)
                        windiag1--;

                    if((i+j)==MainActivity.size-1)
                        windiag2--;
                }
            }
        }



        for(int i=0; i<MainActivity.size && (foundwin!=1 || foundwin!=2);i++ )
        {
            if(winrow[i]==MainActivity.size)
            {
                foundwin = 1;

                break;
            }
            else if(winrow[i]==(-1*MainActivity.size))
            {
                foundwin = 2;

                break;
            }
            else if(wincol[i]==MainActivity.size)
            {
                foundwin = 1;

                break;
            }
            else if(wincol[i]==(-1*MainActivity.size))
            {
                foundwin = 2;

                break;
            }
        }

        if(windiag1==MainActivity.size || windiag2==MainActivity.size)
        {
            foundwin = 1;
        }
        else if(windiag1==(-1*MainActivity.size) || windiag2==(-1*MainActivity.size))
        {
            foundwin = 2;

        }


        if((foundwin!=1 || foundwin!=2)&& checkdraw==(MainActivity.size*MainActivity.size))
            return 3;

        return foundwin;

    }

}
