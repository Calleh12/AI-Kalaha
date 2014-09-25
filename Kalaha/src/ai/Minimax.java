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
    public enum Move
    {
	FAILUIRE(-2),
	TERMINAL(-1),
	CUTOFF(0),
	FIRST(1),
	SECOND(2),
	THREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6);
	
	private int move;
	
	Move(int p_Move)
	{
	    move = p_Move;
	}
	
	public int getValue()
	{
	    return move;   
	}
    }
    
    class Result
    {
	public int move;
	public int value;
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
	
	m_Tree = new Tree(p_GameState);
	
	m_Eval = new Evaluate();
		
	//currentPlayer = rootGameState.getNextPlayer() % 2 + 1;
    }
    
    public Result depthLimitedSearch(Node p_Node, int p_Depth, long p_StartTimer)
    {
	Result res = new Result();
	
	long tot = System.currentTimeMillis() - p_StartTimer;
	double time = (double)tot / (double)1000;
	if( m_MaxTime <= time )
	{
	    res.move = Move.CUTOFF.getValue();
	    //return res;
	}
	
	GameState possibleGameState = p_Node.gameState;
	if(p_Node.move != Move.FAILUIRE.getValue())
	    possibleGameState.makeMove(p_Node.move);
	
	res.move = p_Node.move;
	if(p_Depth <= 0)
	{
	    res.value = m_Eval.evaluateMove(p_Node, possibleGameState, m_Player);
	    return res;
	}
	
	if(possibleGameState.gameEnded())
	{
	    res.value = m_Eval.EvaluateTerminal(possibleGameState, m_Player);
	    return res;
	}
	
	int utility = 0;
	int prevUtility = 0;
	
	if(p_Node.gameState.getNextPlayer() == m_Player)
	{
	    utility = -100000;
	    prevUtility = -100000;
	}
	else
	{
	    utility = 100000;
	    prevUtility = 100000;
	}
	
	for(int i = 1; i < 7; ++i)
	{	    
	    if(!possibleGameState.moveIsPossible(i))
		continue;
		
	    Node tempNode = new Node();

	    tempNode.parent = p_Node;
	    tempNode.move = i;
	    tempNode.gameState = possibleGameState.clone();
	    
	    p_Node.addChild(tempNode);

	    res = depthLimitedSearch(tempNode, p_Depth - 1, p_StartTimer);
	    utility = res.value;
	    
	    if(p_Node.gameState.getNextPlayer() == m_Player)
		utility = Max(utility, prevUtility);
	    else
		utility = Min(utility, prevUtility);
	    
	    prevUtility = utility;
	    p_Node.value = utility;
	}
	
	return res;
    }
    
    public void iterativeDeepening(double p_MaxTime, int p_StartDepth, GameState p_GameState)
    {
	m_MaxTime = p_MaxTime;
	m_Depth = p_StartDepth;
	
	Result res = new Result();
	
	long startTimer = System.currentTimeMillis();
	int move = -1;
	
	double time = 0;
	double lastTime = 0;
	
	while(p_MaxTime >= time)
	{
	    m_Tree = null;
	    m_Tree = new Tree(p_GameState);
	    res = depthLimitedSearch(m_Tree.getRoot(), m_Depth, startTimer);
	    m_Depth++;
	    
	    if(p_MaxTime < (time + lastTime*2) || res.move == Move.CUTOFF.getValue())
	    {
		//break;
	    }
	    
	    long tot = System.currentTimeMillis() - startTimer;
	    time = (double)tot / (double)1000;
	    
	    lastTime = time;
	}
	
	addText("m_Depth: " + m_Depth);
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
