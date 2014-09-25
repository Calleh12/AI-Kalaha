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
	int score;
	if(nextPlayer == p_Player)
	{
	    int prevScore = p_Node.parent.gameState.getScore(p_Player);
	    score = p_GameState.getScore(p_Player);
	    
	    if(score > prevScore)
		score = 10;
	}
	else
	{
	    int prevScore = p_Node.parent.gameState.getScore(nextPlayer);
	    score = p_GameState.getScore(nextPlayer);
	    
	    if(score > prevScore)
		score = -10;
	}
	
	return score;
    }
}
