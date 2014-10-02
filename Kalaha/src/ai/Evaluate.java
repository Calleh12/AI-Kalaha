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
    private int m_Player, m_Opponent;
    
    public Evaluate(GameState p_RootGameState, int p_Player)
    {
	m_RootGameState = p_RootGameState;
        m_Player = p_Player;
        m_Opponent = p_Player % 2 + 1;
    }
    
    public int EvaluateTerminal(GameState p_GameState)
    {
	if(p_GameState.getWinner() == m_Player)
	{
	    return 1000;
	}
	else if(p_GameState.getWinner() == 0)
	{
	    return 10;
	}
	else
	{
	    return -1000;
	}
    }
    
    public int calculateValue(GameState p_GameState, int p_Move)
    {
        int rootScore = m_RootGameState.getScore(m_Player);
        int score = p_GameState.getScore(m_Player);
        int oRootScore = m_RootGameState.getScore(m_Opponent);
        int oScore = p_GameState.getScore(m_Opponent);
        
        int diffScore = score - rootScore;
        int oDiffScore = oScore - oRootScore;
        
        int value = diffScore - oDiffScore;
        
        GameState tempState = p_GameState.clone();
        tempState.makeMove(p_Move);
        
        if(tempState.getNextPlayer() == m_Player)
            value++;
        else
            value--;
        
	return value;
    }
    
    public int evaluateMove()
    {
	return 0;
    }
}
