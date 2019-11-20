import java.util.*;

/*
 * Name: Paul Scheeler, Devin Porter, Sydney Jenkins, Brandon Wong 
 * CPS 350 01
 * Project 3
 * 
 */

public class TreeNode 
{
	int value;	// Value of the node
	
	TreeNode left;	// Reference to left child
	TreeNode right;	// Reference to right child
	
	public TreeNode()
	{
		value = 0;
		left = null;
		right = null;
		
	} //	End default constructor
	
	public TreeNode(int v)
	{
		value = v;
		left = null;
		right = null;
		
	} // 	End partial constructor
	
	public TreeNode(int v, TreeNode child)
	{
		value = v;
		
		//	Executes when the child's value is larger than the parent's value
		if (child.value > value)
		{
			left = null;
			right = child;
			
		} //	End if statement
		
		//	Executes when the child's value is smaller than or equal to the parent's value
		else	// (child.value <= value)
		{
			left = child;
			right = null;
			
		} //	End else statement
	} // 	End partial constructor
	
	public TreeNode(int v, TreeNode child1, TreeNode child2)
	{
		value = v;
		
		//	This checks to make sure both children aren't smaller or larger than the parent
		if ((child1.value > value || child2.value > value) && (child1.value <= value || child2.value <= value))
		{
			//	Determines right child
			if (child1.value > value)	right = child1;
			if (child2.value > value)	right = child2;
			
			//	Determines left child
			if (child1.value <= value)	left = child1;
			if (child2.value <= value)	left = child2;
		
		} //	End if statement
	} //	End full constructor
	
	
	//	NOTE: This copy method most likely doesn't work
	public TreeNode(TreeNode copy)
	{
		value = copy.value;
		left = copy.left;
		right = copy.right;
		
	} // 	End copy constructor
} // 	End TreeNode Class
