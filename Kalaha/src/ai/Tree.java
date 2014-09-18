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
	root.nextPlayer = rootGameState.getNextPlayer();
	
	build(root, rootGameState, 0);
    }
    
    public void build(Node p_Node, GameState p_PossibleGameState, int p_CurrentDepth)
    {
	if(p_CurrentDepth > depth)
	    return;
	
	for(int i = 0; i < 6; ++i)
	{
	    GameState possibleGameState = p_PossibleGameState;
	    
	    if(possibleGameState.getSeeds(i, p_Node.nextPlayer) > 0);
	    {
		Node tempNode = new Node();
		
		tempNode.parent = p_Node;
		tempNode.move = i;
		
		possibleGameState.makeMove(i);
		
		tempNode.nextPlayer = possibleGameState.getNextPlayer();
		
		p_Node.addChild(tempNode);
		
		build(tempNode, possibleGameState.clone(), p_CurrentDepth + 1);
	    }
	}
    }
}
