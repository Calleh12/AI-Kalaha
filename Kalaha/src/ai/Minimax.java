/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import kalaha.GameState;
import javax.swing.JTextArea;
import java.util.ArrayList;

/**
 *
 * @author rokc09
 */
public class Minimax 
{

    
    private GameState m_RootGameState;
    int m_Depth;
    int m_Player;
    private Evaluate m_Eval;
    private JTextArea m_Text;
    double m_MaxTime;
    long m_StartTime;
    
    /**
     * Creates the tree and its first node, the root node.
     * 
     * @param p_GameState
     * @param p_Player 
     */
    public Minimax(GameState p_GameState, int p_Player, JTextArea p_Text)
    {
	m_RootGameState = p_GameState.clone();
	m_Player = p_Player;
	m_Text = p_Text;
        
        m_StartTime = 0;
        
	m_Eval = new Evaluate(m_RootGameState, p_Player);
    }
    /**
     * Depth limited search.
     * 
     * @param p_Depth, not to be confused with start depth. This depth decreases
     * and when it reaches zero, the cutoff is reached.
     * @param p_DepthMeter
     * @param p_GameState, the gamestate for the affected node.
     * @param p_Move, the previous move, which is made by the parent node.
     * @param p_Alpha
     * @param p_Beta
     * @return 
     */
    public Result depthLimitedSearch(int p_Depth, int p_DepthMeter, GameState p_GameState, int p_Move, int p_Alpha, int p_Beta)
    {
        Result res = new Result();
        GameState possibleGameState = p_GameState.clone();
	if(p_Move > 0)
	    possibleGameState.makeMove(p_Move);
        
        long tot = System.currentTimeMillis() - m_StartTime;
        double time = (double)tot / (double)1000;
        
        if(m_MaxTime < time)
        {
            res.state = State.TIMEOUT.getValue();
            return res;
        }
        
        if(possibleGameState.gameEnded())
	{
            res.state = State.TERMINAL.getValue();
            res.value = m_Eval.calculateTerminal(possibleGameState);
            return res;
	}
        
	if(p_Depth <= 0)
	{
            res.state = State.CUTOFF.getValue();
            res.value = m_Eval.calculateValue( possibleGameState);
            return res;
	}
        
        int nextPlayer = possibleGameState.getNextPlayer();
	
	int utility = 0;
	int bestUtility = -100000;
        int bestMove = 0;
            
        int alpha = p_Alpha;
        int beta = p_Beta;
        
	//Depending if it's min or max turn
        if(nextPlayer == m_Player)
        {
            utility = -100000;
        }
        else
        {
            utility = 100000;
        }
        
	for(int i = 1; i < 7; ++i)
	{	    
	    if(!possibleGameState.moveIsPossible(i))
		continue;
            
	    res = depthLimitedSearch(p_Depth - 1, p_DepthMeter + 1, possibleGameState, i, alpha, beta);
            res.move = i;
	    if(nextPlayer == m_Player)
            {
               if(res.value > utility)
               {
                   utility = res.value;
               }
               
               if(utility > beta)
               {
                   break;
               }
               
               if(utility > alpha)
                   alpha = utility;
            }
	    else
            {
                if(res.value < utility)
                {
                    utility = res.value;
                }
                
                if(utility < alpha)
                {
                    break;
                }
                if(utility < beta)
                    beta = utility;
            }
            if(utility > bestUtility)
            {
                bestMove = i;
                bestUtility = utility;
            }
	}
        res.bestValue = bestUtility;
        res.move = bestMove;
	return res;
    }
    /**
     * Iterative deepening search.
     * 
     * @param p_MaxTime, The max time it has to search.
     * @param p_StartDepth, the depth level it starts at.
     * @param p_GameState, the root state of the search.
     * @return 
     */
    public int iterativeDeepening(double p_MaxTime, int p_StartDepth, GameState p_GameState)
    {
	m_MaxTime = p_MaxTime;
	m_Depth = p_StartDepth;

	double time = 0;
	double lastTime = 0;
	
        Result res = new Result();
        
	m_StartTime = System.currentTimeMillis();
        
        int move = 0;
	
	while(p_MaxTime >= time)
	{
            long timeStamp = System.currentTimeMillis();
            /* It is safe to assume that another depth will atleast take the time*2
	    * it took for the previous depth 
	    */
            if(p_MaxTime <= (time + lastTime))
                break;
	    res = depthLimitedSearch(m_Depth, 1, m_RootGameState, 0, -100000, 100000);
            //If the timeout has been reached while searching the tree it should 
	    // abort and use the last tree best move.
            if(res.state == State.TIMEOUT.getValue())
            {
                addText("TimeOut");
                break;
            }
            long totStamp = System.currentTimeMillis() - timeStamp;
	    double stamp = (double)totStamp / (double)1000;
            
            addText("m_Depth: " + m_Depth + " Move: " + res.move + " Value: " + res.bestValue + " it took: " + stamp);
            move = res.move;
            //A terminal state has been found and determined to be the best value
	    //no need to dig deeper into the tree.
            if(res.state == State.TERMINAL.getValue())
            {
                break;
            }
            
	    m_Depth++;
	    
	    long tot = System.currentTimeMillis() - m_StartTime;
	    time = (double)tot / (double)1000;
	    
	    lastTime = time;
	}
        
        //addText("Depth " + m_Depth + " reached.");
        
        return move;
    }
    
    /**
     * Adds a text string to the GUI textarea.
     * 
     * @param txt The text to add
     */
    public void addText(String txt)
    {
        //Don't change this
        m_Text.append(txt + "\n");
        m_Text.setCaretPosition(m_Text.getDocument().getLength());
    }
}
