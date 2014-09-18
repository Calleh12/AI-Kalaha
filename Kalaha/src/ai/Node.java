/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.util.ArrayList;

/**
 *
 * @author rokc09
 */
public class Node
{
    public int move;
    public int nextPlayer;
    public Node parent;
    public ArrayList<Node> children;
    
    public Node()
    {
	move = -1;
	parent = null;
	children = null;	
    }
    
    void addChild(int p_Move, int p_NextPlayer)
    {
	Node node = new Node();
	node.move = p_Move;
	node.nextPlayer = p_NextPlayer;
	
	node.parent = this;
	children.add(node);
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
