/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.util.ArrayList;
import kalaha.*;
import ai.Evaluate;

/**
 *
 * @author rokc09
 */
public class Tree 
{
    class Result
    {
	
    }
    
    enum Move
    {
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
    
    private Evaluate eval;
    private Node root;
    private GameState rootGameState;
    int currentPlayer;
    int depth;
    
    public Tree(GameState p_GameState)
    {	
	eval = new Evaluate();
	
	rootGameState = p_GameState;
	
	//currentPlayer = rootGameState.getNextPlayer() % 2 + 1;
	
	root = new Node();
	root.gameState = rootGameState;
	
	//DepthLimitedSearch(root, p_Depth);
    }
    
    public int depthLimitedSearch(Node p_Node, int p_Depth)
    {
	if(p_Depth <= 0)
	{
	    return eval.evaluateMove();
	}
	
	GameState possibleGameState = p_Node.gameState;
	
	if(possibleGameState.gameEnded())
	    return eval.evaluateMove();
	
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

	    p_Node.value = depthLimitedSearch(tempNode, p_Depth - 1);
	    
	    return p_Node.value;
	}
	
	return 100;
    }
    
    public int iterativeDeepening(double p_MaxTime, int p_StartDepth)
    {
	depth = p_StartDepth;
	long startTimer = System.currentTimeMillis();
	
	int move = -1;
	
	double time = 0;
	double lastTime = 0;
	
	while(p_MaxTime >= time)
	{
	    move = depthLimitedSearch(root, depth);	
    
	    long tot = System.currentTimeMillis() - startTimer;
	    time = (double)tot / (double)1000;
	    lastTime = time;
	    depth++;
	}
	return move;
    }
    
    public Node getRoot()
    {
	return root;
    }
}
