/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import kalaha.*;
import java.util.ArrayList;

/**
 *
 * @author rokc09
 */
public class Node
{
    public int move;
    public int value;
    GameState gameState;
    //public int nextPlayer;
    public Node parent;
    public ArrayList<Node> children;
    
    public Node()
    {
	move = -2;
	value = 0;
	parent = null;
	children = new ArrayList<Node>();	
    }
    
    void addChild(Node p_Node)
    {
	p_Node.parent = this;
	children.add(p_Node);
    }
    
    void removeChild(int p_Child)
    {
	if(children.isEmpty())
	    children.remove(p_Child);
	else
	{
	    for(int i = 0; i < children.size(); i++)
	    {
		children.get(p_Child).removeChild(i);
	    }
	}
    }
}
