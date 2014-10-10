/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;
import java.util.ArrayList;

import kalaha.*;

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
    
    class Result
    {
	public int move;
	public int value;
        public int bestValue;
        public int state;
        
//        Result(int p_Value)
//        {
//            move = 0;
//            value = p_Value;
//            state = State.GOOD.getValue();
//        }
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
	    value = 100;
	}
        else if(winner == m_Opponent)
	{
	    value = -1000;
	}
        
        return value;
    }
    
    public int calculateValue(GameState p_GameState)
    {
        int value = 0;
               
        int score = p_GameState.getScore(m_Player);
        int oScore = p_GameState.getScore(m_Opponent);
        
        int diffScore = score - m_RootScore;
        int oDiffScore = oScore - m_ORootScore;
        
        value += diffScore - oDiffScore;
        
        if( score >= 37)
        {
            value += 37;
            //m_Win = true;
        }
        
        int potentScore = 0;
        int oPotentScore = 0;
        int potentValue = 0;
        
        int seed = 1;
        int oSeed = 1;
        int tempValue = 0;
        
        ArrayList<Integer> seeds = new ArrayList<Integer>();
        ArrayList<Integer> oSeeds = new ArrayList<Integer>();
        for(int i = 1; i < 7; i++)
        {
            seed = p_GameState.getSeeds(i, m_Player);
            oSeed = p_GameState.getSeeds(i, m_Opponent);
            
            seeds.add(seed);
            oSeeds.add(oSeed);
            
            if(oSeed == 0)
            {
                potentValue -= seed;
            }
            
            if(seed == 0)
            {
                potentValue += oSeed;
            }
            
            potentScore += seed;
            oPotentScore += oSeed;
        }
	
	for(int i = 6; i > 0; i--)
	{
	    seed = p_GameState.getSeeds(i, m_Player);
            oSeed = p_GameState.getSeeds(i, m_Opponent);
	    
	    
	    int highest = 0;
	    for(int j = 0; j < i; j++)
	    {
		if(seeds.get(i-1) == 8 + j)
		{
		    if(seeds.get(j) == 0)
		    {
			int temp = oSeeds.get(j)*2;
			if(temp > highest)
			    highest = temp;
		    }
		}
	    }
	    
	    int lowest = 0;
	    for(int j = 0; j < i; j++)
	    {
		if(oSeeds.get(i-1) == 8 + j)
		{
		    if(oSeeds.get(j) == 0)
		    {
			int temp = seeds.get(j)*2;
			if(temp > lowest)
			    lowest = temp;
		    }
		}
	    }
	    
	    value += highest - lowest;
	}
                
        //m_PrevValue = value;
        value += potentScore - oPotentScore + potentValue;
        
        //if(value > m_PrevValue)
            //value = m_PrevValue;
        
	return value;
    }
}
