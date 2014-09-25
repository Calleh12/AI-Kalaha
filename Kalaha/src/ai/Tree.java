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
    
    private Node m_Root;
   
    public Tree(GameState p_GameState)
    {
	m_Root = new Node();
	m_Root.gameState = p_GameState;
    }
    
    public Node getRoot()
    {
	return m_Root;
    }
}
