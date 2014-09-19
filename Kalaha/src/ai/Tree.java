/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.util.ArrayList;
import kalaha.*;

/**
 *
 * @author rokc09
 */
public class Tree 
{
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
	
	public int getMove()
	{
	    return move;   
	}
    }
    
    private Node root;
    private GameState rootGameState;
    int currentPlayer;
    int depth;
    
    public Tree(GameState p_GameState, int p_Depth)
    {	
	rootGameState = p_GameState;
	depth = p_Depth;
	
	//currentPlayer = rootGameState.getNextPlayer() % 2 + 1;
	
	root = new Node();
	root.gameState = rootGameState;
	
	DepthLimitedSearch(root, 0);
    }
    
    
    
    public void DepthLimitedSearch(Node p_Node, int p_CurrentDepth)
    {
	if(p_CurrentDepth > depth)
	    return;
	
	GameState possibleGameState = p_Node.gameState;
	
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

	    DepthLimitedSearch(tempNode, p_CurrentDepth + 1);

	}
    }
}
