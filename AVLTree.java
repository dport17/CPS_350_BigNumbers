import java.util.*;

/*
 * Names: Paul Scheeler, Devin Porter, Sydney Jenkins, Brandon Wong
 * CPS 350 01
 * Project 3
 * 
 */

public class AVLTree extends TreeNode
{
	TreeNode root;
	int balanceFactor;
	int counter = 0;				//	Initialized at 0 for populating the arrays in the traversal methods
	
	int[] order = new int[100];	// 	Initialized to handle 100 nodes
	
	public AVLTree()
	{
		root = null;						//	Sets 'root' to null if not given a root value
		balanceFactor = 0;					//	Sets the default balance factor to 0 since there's only the root
	
	}	//	End default constructor
	
	public AVLTree(int v)
	{
		root = new TreeNode(v);				//	Creates new root node with the value 'v'
		balanceFactor = 0;					//	Sets the default balance factor to 0 since there's only the root
	
	}	//	End partial constructor
	
	public AVLTree(int v, TreeNode child)
	{
		root = new TreeNode(v, child);		//	Creates new root node with the value 'v' and one reference to a child node
		balanceFactor = 0;					//	Sets the default balance factor to 0
		
		//	Since there will only be one parent node and one child node, there is no need for a loop
		if (root.left != null)		balanceFactor--;	//	If the child node is a left child,	balanceFactor = -1
		if (root.right != null)		balanceFactor++;	//	If the child node is a right child,	balanceFactor = +1
		
	}	//	End partial constructor
	
	public AVLTree(int v, TreeNode child1, TreeNode child2)
	{
		root = new TreeNode(v, child1, child2);		//	Creates new root node with the value 'v' and two references to children nodes
		balanceFactor = 0;							//	Sets the default balance factor to 0 since it is balanced upon initialization
		
	}	//	End full constructor
	
	public int[] preOrder (TreeNode r)
	{
		if (r != null)
		{
			order[counter] = r.value;
			counter++;						//	Increment the counter

			preOrder(r.left);
			preOrder(r.right);
			
		}	//	End if statement
		
		//	I don't believe this will ever execute?
		else	return order;
		
		//	Returns the array
		return order;
		
	}	//	End preOrder method
	
	public int[] inOrder (TreeNode r)
	{
		if (r != null)
		{
			order[counter] = r.value;
			counter++;						//	Increment the counter

			inOrder(r.left);
			inOrder(r.right);
			
		}	//	End if statement
		
		//	I don't believe this will ever execute?
		else	return order;
		
		//	Returns the array
		return order;
		
	}	//	End inOrder method
	
	public int[] postOrder (TreeNode r)
	{
		if (r != null)
		{
			order[counter] = r.value;
			counter++;						//	Increment the counter

			postOrder(r.left);
			postOrder(r.right);
			
		}	//	End if statement
		
		//	I don't believe this will ever execute?
		else	return order;
		
		//	Returns the array
		return order;
		
	}	//	End postOrder method
	
	//	NOTE: Still have to implement copy, insertion, and deletion
	
}	//	End AVL Tree class
