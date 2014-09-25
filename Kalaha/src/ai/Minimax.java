/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import kalaha.GameState;

/**
 *
 * @author rokc09
 */
public class Minimax {
    Tree m_Tree;
    private GameState m_RootGameState;
    int m_Depth;
    int m_Player;
    private Evaluate m_Eval;
    
    /**
     * Creates the tree and its first node, the root node.
     * 
     * @param p_GameState
     * @param p_Player 
     */
    public Minimax(GameState p_GameState, int p_Player)
    {
	m_RootGameState = p_GameState;
	m_Player = p_Player;
	
	m_Tree = new Tree(p_GameState);
	
	m_Eval = new Evaluate();
		
	//currentPlayer = rootGameState.getNextPlayer() % 2 + 1;
    }
    
    public int depthLimitedSearch(Node p_Node, int p_Depth)
    {
	GameState possibleGameState = p_Node.gameState;
	
	if(p_Depth <= 0)
	{
	    return m_Eval.evaluateMove(possibleGameState, m_Player);
	}
	
	if(possibleGameState.gameEnded())
	    return m_Eval.EvaluateTerminal(possibleGameState, m_Player);
	
	for(int i = 1; i < 7; ++i)
	{	    
	    if(!possibleGameState.moveIsPossible(i))
		continue;
		
	    Node tempNode = new Node();

	    tempNode.parent = p_Node;
	    tempNode.move = i;

	    p_Node.gameState.makeMove(i);
	    tempNode.gameState = possibleGameState.clone();

	    p_Node.addChild(tempNode);

	    tempNode.value = depthLimitedSearch(tempNode, p_Depth - 1);
	    
	    return tempNode.value;
	}
	
	return p_Node.value;
    }
    
    public void iterativeDeepening(double p_MaxTime, int p_StartDepth)
    {
	m_Depth = p_StartDepth;
	long startTimer = System.currentTimeMillis();
	
	double time = 0;
	double lastTime = 0;
	
	while(p_MaxTime >= time)
	{
	    depthLimitedSearch(m_Tree.getRoot(), m_Depth);	
    
	    long tot = System.currentTimeMillis() - startTimer;
	    time = (double)tot / (double)1000;
	    	    
	    lastTime = time;
	    m_Depth++;
	}
    }
}
