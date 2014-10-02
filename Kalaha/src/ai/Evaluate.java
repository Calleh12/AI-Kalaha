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
    private int m_RootScore, m_ORootScore;
    
    public Evaluate(GameState p_RootGameState, int p_Player)
    {
	m_RootGameState = p_RootGameState;
        m_Player = p_Player;
        m_Opponent = p_Player % 2 + 1;
        
        m_RootScore = m_RootGameState.getScore(m_Player);
        m_ORootScore = m_RootGameState.getScore(m_Opponent);
    }
    
    public int calculateTerminal(GameState p_GameState)
    {
        int value = 0;
        int winner = p_GameState.getWinner();
        if(winner == m_Player)
	{
	    value = 1000;
	}
	else if(winner == 0)
	{
	    value = 10;
	}
        else if(winner == m_Opponent)
	{
	    value = -1000;
	}
        
        return value;
    }
    
    public int calculateValue(GameState p_GameState, int p_Move)
    {
        int value = 0;
        
        int score = p_GameState.getScore(m_Player);
        int oScore = p_GameState.getScore(m_Opponent);
        
        int diffScore = score - m_RootScore;
        int oDiffScore = oScore - m_ORootScore;
        
        value += diffScore - oDiffScore;
        
        if(m_RootScore + score >= 37)
            value += 100;
        
        int potentScore = 0;
        int oPotentScore = 0;
        
        int seeds = 0;
        int oSeeds = 0;
        for(int i = 1; i < 7; i++)
        {
            seeds = p_GameState.getSeeds(i, m_Player);
            oSeeds = p_GameState.getSeeds(i, m_Opponent);
            if(oSeeds == 0)
                value -= seeds;
            
            if(seeds == 0)
                value += oSeeds;
            
            potentScore += seeds;
            oPotentScore += oSeeds;
        }
        
        value += (potentScore * 1.25) - oPotentScore;
        
	return value;
    }
}
