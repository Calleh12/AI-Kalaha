/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import kalaha.*;

/**
 *
 * @author rokc09
 */
public class Evaluate 
{
    
    public Evaluate()
    {
	
    }
    
    public int EvaluateTerminal(GameState p_GameState, int p_Player)
    {
	if(p_GameState.getWinner() == p_Player)
	{
	    return 50;
	}
	else if(p_GameState.getWinner() == 0)
	{
	    return 10;
	}
	else
	{
	    return -30;
	}
    }
    
    public int evaluateMove(Node p_Node, GameState p_GameState, int p_Player)
    {
	int nextPlayer = p_GameState.getNextPlayer();
	int maxScore = 0;
	if(nextPlayer == p_Player)
	{
	    for(int i = 0; i < 6; i++)
	    {
		if(p_GameState.getSeeds(i, p_Player) == i)
		{
		    maxScore = 2;
		    break;
		}
	    }
	    
	    int prevScore = p_Node.parent.gameState.getScore(p_Player);
	    int score = p_GameState.getScore(p_Player);
	    
	    if(score > prevScore)
		if(maxScore < 1)
		    maxScore = 1;
	}
	else
	{
	    for(int i = 0; i < 6; i++)
	    {
		if(p_GameState.getSeeds(i, nextPlayer) == i)
		{
		    maxScore = -2;
		    break;
		}
	    }
	    
	    int prevScore = p_Node.parent.gameState.getScore(nextPlayer);
	    int score = p_GameState.getScore(nextPlayer);
	    
	    if(score > prevScore)
		if(maxScore < -1)
		    maxScore = -1;
	}
	
	return maxScore;
    }
}
