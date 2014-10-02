/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import kalaha.GameState;
import javax.swing.JTextArea;

/**
 *
 * @author rokc09
 */
public class Minimax 
{
    public enum State
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
        public int state;
    }
    
    Tree m_Tree;
    private GameState m_RootGameState;
    int m_Depth;
    int m_Player;
    private Evaluate m_Eval;
    private JTextArea m_Text;
    double m_MaxTime;
    
    /**
     * Creates the tree and its first node, the root node.
     * 
     * @param p_GameState
     * @param p_Player 
     */
    public Minimax(GameState p_GameState, int p_Player, JTextArea p_Text)
    {
	m_RootGameState = p_GameState;
	m_Player = p_Player;
	m_Text = p_Text;
	
	//m_Tree = new Tree(p_GameState);
	
	m_Eval = new Evaluate();
		
	//currentPlayer = rootGameState.getNextPlayer() % 2 + 1;
    }
    
    public Result depthLimitedSearch(int p_Depth, long p_StartTimer, GameState p_GameState, int p_Move)
    {
        Result res = new Result();
        res.move = p_Move;
        //res.value = p_Value;
        res.state = State.GOOD.getValue();
        
	GameState possibleGameState = p_GameState;
	if(p_Move > 0)
	    possibleGameState.makeMove(p_Move);
        
        long tot = System.currentTimeMillis() - p_StartTimer;
        double time = (double)tot / (double)1000;
        
        if(m_MaxTime < time)
        {
            res.state = State.TIMEOUT.getValue();
            return res;
        }
        
        if(possibleGameState.gameEnded())
	{
            res.state = State.TERMINAL.getValue();
            res.value = m_Eval.EvaluateTerminal(possibleGameState, m_Player);
            return res;
	}
        
	if(p_Depth <= 0)
	{
            res.state = State.CUTOFF.getValue();
            res.value = m_Eval.evaluateMove(m_RootGameState, possibleGameState, m_Player);
            return res;
	}
	
	int utility = 0;
	int prevUtility = 0;
        int prevMove = 0;
	
	if(possibleGameState.getNextPlayer() == m_Player)
	    prevUtility = -100000;
	else
	    prevUtility = 100000;
        
	for(int i = 1; i < 7; ++i)
	{	    
	    if(!possibleGameState.moveIsPossible(i))
		continue;
		
	    //Node tempNode = new Node();

	    //tempNode.parent = p_Node;
	    p_Move = i;
	    GameState gameState = possibleGameState.clone();
	    
	    //p_Node.addChild(tempNode);

	    res = depthLimitedSearch(p_Depth - 1, p_StartTimer, gameState, i);
            utility = res.value;
            //addText("Move: " + res.move);

            //p_Value = utility;            
            
	    if(possibleGameState.getNextPlayer() == m_Player)
            {
		if(utility < prevUtility)
                {
                    utility = prevUtility;
                }
            }
	    else
            {
                if(utility > prevUtility)
                {
                    utility = prevUtility;
                }
            }
                
	    prevUtility = utility;
	}
        
        //p_Node.value = utility;
        res.value = utility;
        //res.move = p_Move;
        //res.state = State.GOOD.getValue();
	return res;
    }
    
    public int iterativeDeepening(double p_MaxTime, int p_StartDepth, GameState p_GameState)
    {
	m_MaxTime = p_MaxTime;
	m_Depth = p_StartDepth;

	double time = 0;
	double lastTime = 0;
	
        Result res = new Result();
        
	long startTimer = System.currentTimeMillis();
        
        int move = 0;
	
	while(p_MaxTime >= time)
	{
            if(p_MaxTime <= (time + lastTime))
                break;
            
            if(res.state == State.TIMEOUT.getValue())
                break;
            
            
	    //m_Tree = null;
	    //m_Tree = new Tree(p_GameState);
            
            //addText("m_Depth: " + m_Depth);
	    res = depthLimitedSearch(m_Depth, startTimer, p_GameState, 0);
            //addText("Move: " + res.move + " Value: " + res.value );
            move = res.move;
                        
	    m_Depth++;
	    
	    long tot = System.currentTimeMillis() - startTimer;
	    time = (double)tot / (double)1000;
	    
	    lastTime = time;
	}
        
        addText("Best move: " + move);

        return move;
    }
    
    private int Max(int p_Util, int p_PrevUtil)
    {
	if(p_Util < p_PrevUtil)
	    p_Util = p_PrevUtil;
	return p_Util;
    }
    
    private int Min(int p_Util, int p_PrevUtil)
    {
	if(p_Util > p_PrevUtil)
	    p_Util = p_PrevUtil;
	return p_Util;
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
