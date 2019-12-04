/*
 * Name: Paul Scheeler, Devin Porter, Sydney Jenkins, Brandon Wong 
 * CPS 350 01
 * Project 3
 * 
 */

public class TreeNode 
{
	int value;		//	Value of the node
	int height;		//	Helps calculate the balance factor of the node
	
	TreeNode left;		//	Reference to left child
	TreeNode right;		//	Reference to right child
	
	public TreeNode()
	{
		value = 0;
		height = 1;
		
		left = null;
		right = null;
		
	} //	End default constructor
	
	public TreeNode(int v)
	{
		value = v;
		height = 1;
		
		left = null;
		right = null;
		
	} // 	End defined node constructor
} // 	End TreeNode Class
