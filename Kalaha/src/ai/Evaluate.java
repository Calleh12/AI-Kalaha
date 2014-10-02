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
    
    public int calculateValue(GameState p_GameState, int p_Move)
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
        
        int rootScore = m_RootGameState.getScore(m_Player);
        int score = p_GameState.getScore(m_Player);
        int oRootScore = m_RootGameState.getScore(m_Opponent);
        int oScore = p_GameState.getScore(m_Opponent);
        
        int diffScore = score - rootScore;
        int oDiffScore = oScore - oRootScore;
        
        value += diffScore - oDiffScore;
        
        GameState tempState = p_GameState.clone();
        int currentNextPlayer = p_GameState.getNextPlayer();
        tempState.makeMove(p_Move);
        int newNextPlayer = tempState.getNextPlayer();
        
        if(currentNextPlayer == newNextPlayer && currentNextPlayer == m_Player)
            value += 2;
        else
            value--;
        
        int potentScore = 1;
        int oPotentScore = 1;
        for(int i = 1; i < 7; i++)
        {
            potentScore += p_GameState.getSeeds(i, m_Player);
            oPotentScore += p_GameState.getSeeds(i, m_Opponent);
        }
        
        int potentDiff = 0;
        if(potentScore >= oPotentScore)
            potentDiff = potentScore / oPotentScore;
        else
            potentDiff = oPotentScore / potentScore * -1;
        
        value += potentDiff;
        
	return value;
    }
}
