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
    private GameState m_RootGameState;
    
    public Evaluate(GameState p_RootGameState)
    {
	m_RootGameState = p_RootGameState;
    }
    
    public int EvaluateTerminal(GameState p_GameState, int p_Player)
    {
	if(p_GameState.getWinner() == p_Player)
	{
	    return 10;
	}
	else if(p_GameState.getWinner() == 0)
	{
	    return 3;
	}
	else
	{
	    return -10;
	}
    }
    
    public int calculateValue(GameState p_GameState, int p_Player)
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
	    
	    int prevScore = m_RootGameState.getScore(p_Player);
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
	    
	    int prevScore = m_RootGameState.getScore(nextPlayer);
	    int score = p_GameState.getScore(nextPlayer);
	    
	    if(score > prevScore)
		if(maxScore < -1)
		    maxScore = -1;
	}
	
	return maxScore;
    }
    
    public int evaluateMove()
    {
	return 0;
    }
}
