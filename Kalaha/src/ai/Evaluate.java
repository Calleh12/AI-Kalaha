/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;
import java.util.ArrayList;

import kalaha.*;
    
    /**
     * A state in which the search can be in.
     * Cutoff = When the depth limit has been reached.
     * Terminal = A state where there game is ended.
     * Timeout = The state when a search has exceeded maximum search time.
     */
    enum State
    {
        GOOD(0),
	CUTOFF(1),
	TERMINAL(2),
	FAILUIRE(3),
        TIMEOUT(4);
	
	private int State;
	
	State(int p_State)
	{
	    State = p_State;
	}
	
	public int getValue()
	{
	    return State;   
	}
    }
    /**
     * What is passed up whenever the recursive function returns.
     * move: the move in the node.
     * value: the value of the move.
     * state: Which state the result is in.
     */
    class Result
    {
	public int move;
	public int value;
        public int bestValue;
        public int state;
    }

/**
 *
 * @author rokc09
 */
public class Evaluate 
{
    private GameState m_RootGameState;
    private int m_Player, m_Opponent;
    private int m_RootScore, m_ORootScore;
    
    private int m_PrevValue;
    private boolean m_Win;
    
    public Evaluate(GameState p_RootGameState, int p_Player)
    {
	m_RootGameState = p_RootGameState;
        m_Player = p_Player;
        m_Opponent = p_Player % 2 + 1;
        
        m_RootScore = m_RootGameState.getScore(m_Player);
        m_ORootScore = m_RootGameState.getScore(m_Opponent);
        
        m_PrevValue = -100000;
        m_Win = false;
    }
    /**
     * Called when a terminal state is reached. It checks who is the winner,
     * player, opponent or if it is a draw.
     * 
     * @param p_GameState: The current state of the game.
     * @return the value, the value depends on who wins.
     */
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
	    value = -1000;
	}
        else if(winner == m_Opponent)
	{
	    value = -1000;
	}
        
        return value;
    }
    /**
     * Calculates the possible value gained from the gamestate. The value increases
     * with different things, score gained, possible steal, possible theft and 
     * possible potential score, the the amount of seeds a player has (hoarding).
     * 
     * @param p_GameState: Current state of the game.
     * @return the value of the move depending on the state.
     */
    public int calculateValue(GameState p_GameState)
    {
        int value = 0;
               
        int score = p_GameState.getScore(m_Player);
        int oScore = p_GameState.getScore(m_Opponent);
        
        int diffScore = score - m_RootScore;
        int oDiffScore = oScore - m_ORootScore;
        
        value = diffScore - oDiffScore;
        
        if(m_RootScore <= 37 && score >= 37)
        {
            value += 150;
            //m_Win = true;
        }
        if(m_ORootScore <= 37 &&  oScore >= 37)
        {
            value -= 150;
        }
        int seed = 1;
        int oSeed = 1;
        
        ArrayList<Integer> seeds = new ArrayList<Integer>();
        ArrayList<Integer> oSeeds = new ArrayList<Integer>();
        for(int i = 1; i < 7; i++)
        {
            seed = p_GameState.getSeeds(i, m_Player);
            oSeed = p_GameState.getSeeds(i, m_Opponent);
            
            seeds.add(seed);
            oSeeds.add(oSeed);
        }
        
        for(int i = 0; i < 5; i++)
        {
            int highest = 0;
            int lowest = 0;
            for(int j = i+1; j < 5; j++)
	    {
                if(seeds.get(i) == j-i)
                {
                    if(seeds.get(j) == 0)
                    {
                        int temp = oSeeds.get(j);
			if(temp > highest)
			    highest = temp;
                    }
                }
                
                if(oSeeds.get(i) == j-i)
                {
                    if(oSeeds.get(j) == 0)
                    {
                        int temp = seeds.get(j);
			if(temp > highest)
			    highest = temp;
                    }
                }
            }
            
            value += highest - lowest;
        }
	
	for(int i = 5; i >= 0; i--)
	{
	    int highest = 0;
	    int lowest = 0;
	    for(int j = i; j >= 0; j--)
	    {
		if(seeds.get(i) == 8 + j)
		{
		    if(seeds.get(j) == 0 || seeds.get(j) == 13)
		    {
			int temp = oSeeds.get(j);
			if(temp > highest)
			    highest = temp;
		    }
		}
                
                if(oSeeds.get(i) == 8 + j)
		{
		    if(oSeeds.get(j) == 0 || oSeeds.get(j) == 13)
		    {
			int temp = seeds.get(j);
			if(temp > lowest)
			    lowest = temp;
		    }
		}
	    }
	    
	    value += highest - lowest;
	}
        
	return value;
    }
}
