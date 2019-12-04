/*
 * Names: Paul Scheeler, Devin Porter, Sydney Jenkins, Brandon Wong
 * CPS 350 01
 * Project 3
 * 
 * AVLTree.java
 */

public class AVLTree extends TreeNode
{
	TreeNode root;
	
	int counter = 0;		//	Initialized at 0 for populating the arrays in preOrder method
	int counter2 = 0;		//	Initialized at 0 for populating the arrays in inOrder method
	int counter3 = 0;		//	Initialized at 0 for populating the arrays in postOrder method
	
	public AVLTree()
	{
		root = null;		//	Sets 'root' to null if not given a root value
	
	} //	End default constructor
	
	public AVLTree(int v)
	{
		root = new TreeNode(v);	//	Creates new root node with the value 'v'
	
	} //	End partial constructor
	
	public int height(TreeNode r)
	{
		if (r == null)			return 0;
		else				return r.height;
		
	} //	End height method
	
	public int max(int a, int b)
	{
		if (a > b)		return a;
		else			return b;
		
	} //	End max method
	
    public int getBalanceFactor(TreeNode r) 
    { 
        if (r == null) 			return 0; 
        else			 	return height(r.left) - height(r.right); 
        
    } // 	End getBalanceFactor method
	
    public TreeNode rightRotate(TreeNode r) 
    {
    	//	Create new nodes that will be moved during the rotation
        TreeNode temp = r.left; 
        TreeNode temp2 = temp.right; 
  
        // 	Perform the rotation 
        temp.right = r; 
        r.left = temp2; 
  
        // 	Update the height of each path 
        r.height = 	max(height(r.left), height(r.right)) + 1; 
        temp.height = 	max(height(temp.left), height(temp.right)) + 1; 
  
        // 	Return the new root of the rotated section
        return temp; 
        
    } //	End rightRotate method
    
    public TreeNode leftRotate(TreeNode r) 
    {
    	//	Create new nodes that will be moved during the rotation
        TreeNode temp = r.right; 
        TreeNode temp2 = temp.left; 
  
        // 	Perform the rotation 
        temp.left = r; 
        r.right = temp2; 
  
        //  	Update the height of each path 
        r.height = max(height(r.left), height(r.right)) + 1; 
        temp.height = max(height(temp.left), height(temp.right)) + 1; 
  
        // 	Return the new root of the rotated section
        return temp; 
        
    } //	End leftRotate method
	
	public TreeNode insert(TreeNode r, int insertValue)
	{			
		//	Create the new TreeNode
		if (r == null)		return (new TreeNode(insertValue));
		
		if 	(insertValue < r.value)		r.left = insert(r.left, insertValue);
		else if (insertValue > r.value)		r.right = insert(r.right, insertValue);
		else					return r; // 	Do nothing since there duplicates are not allowed.
		
		//	This updates the height of a path
		r.height = 1 + max(height(r.left), height(r.right));	
		
		//	This gets the balanceFactor to see if it's less than -1 or greater than 1 (unbalanced)
		int balanceFactor = getBalanceFactor(r); 		
		
        if (balanceFactor > 1 && insertValue < r.left.value) 		return rightRotate(r); 	// 	Right rotation
        if (balanceFactor < -1 && insertValue > r.right.value) 		return leftRotate(r);	//	Left rotation
  
        // 	Left-Right rotation 
        if (balanceFactor > 1 && insertValue > r.left.value) 
        { 
            r.left = leftRotate(r.left);	// 	Left Rotation first
            return rightRotate(r); 		//	Right Rotation second
            
        } 
  
        // 	Right-Left rotation
        if (balanceFactor < -1 && insertValue < r.right.value) 
        { 
            r.right = rightRotate(r.right); 	//	Right Rotation first
            return leftRotate(r); 		//	Left Rotation second
            
        } 
  
        return r; 
        
	} //	End insert method
	
    //	The node 'r' in the parameters is the right subtree of the deleted node
    public TreeNode minValueNode(TreeNode r)  
    {  
        TreeNode iter = r;  
  
        //	This while loop traverses the left tree of the passed right subtree (r)
        while (iter.left != null)		iter = iter.left;  
        
        // 	This node is the minimum value node in the deleted node's right subtree
        return iter;  
        
    } //	End minValueNode method
    
    TreeNode delete(TreeNode r, int deleteValue)  
    {   
        if (r == null)  		return r;  
  
        // 	If the 'deleteValue' is less than 'r.value', it is in the left subtree 
        if (deleteValue < r.value)  		r.left = delete(r.left, deleteValue);  
  
        // 	If the 'deleteValue' is greater than 'r.value', it is in the right subtree 
        else if (deleteValue > r.value)  	r.right = delete(r.right, deleteValue);  
  
        // 	If the 'deleteValue' is equal to 'r.value', this is the node to be deleted
        else
        {  
            //	Executes if the node has only one child or no child  
            if ((r.left == null) || (r.right == null))  
            {  
                TreeNode temp = null;  
                
                if (temp == r.left)  	temp = r.right;  
                else					temp = r.left;  
  
                // 	Executes if there is no child node (temp stays null after the reassignment)
                if (temp == null)  
                {  
                    temp = r;  
                    r = null;  
                }  
                
                // 	Executes if there is one child node
                else   					r = temp; 	// Copy the non-empty child  
                
            }  
            
            //	Executes if the node has two children
            else
            {  
                // 	Get the smallest value in the right subtree 
                TreeNode temp = minValueNode(r.right);  
  
                // 	Copy the smallest value in the right subtree
                r.value = temp.value;  
  
                // 	Delete the original child node
                r.right = delete(r.right, temp.value);  
            }  
        }  
  
        // 	If the tree had only one node then return  
        if (r == null)  			return r;  
  
	//	This updates the height of a path
        r.height = 1 + max(height(r.left), height(r.right));  
  
	//	This gets the balanceFactor to see if it's less than -1 or greater than 1 (unbalanced)
        int balanceFactor = getBalanceFactor(r);  
        
        if (balanceFactor > 1 && getBalanceFactor(r.left) >= 0)  	return rightRotate(root);	// Right rotation
        if (balanceFactor < -1 && getBalanceFactor(r.right) <= 0)  	return leftRotate(root);	// Left rotation
        
        // 	Left-Right rotation  
        if (balanceFactor > 1 && getBalanceFactor(r.left) < 0)  
        {  
            r.left = leftRotate(r.left);  
            return rightRotate(r);  
        }  
        
        // 	Right-Left rotation  
        if (balanceFactor < -1 && getBalanceFactor(r.right) > 0)  
        {  
            r.right = rightRotate(r.right);  
            return leftRotate(r);  
        }  
  
        return r;  
        
    } //	End delete method
    
	public int[] preOrder (TreeNode r, int[] a)
	{
		if (r != null)
		{
			a[counter] = r.value;
			counter++;		//	Increment the counter

			preOrder(r.left, a);
			preOrder(r.right, a);
			
		}	//	End if statement
		
		//	I don't believe this will ever execute?
		else	return a;
		
		//	Returns the array
		return a;
		
	} //	End preOrder method
	
	public int[] inOrder (TreeNode r, int[] a)
	{
		if (r != null)
		{
			inOrder(r.left, a);
			
			a[counter2] = r.value;
			counter2++;		//	Increment the counter
			
			inOrder(r.right, a);
			
		}	//	End if statement
		
		//	I don't believe this will ever execute?
		else	return a;
		
		//	Returns the array
		return a;
		
	} //	End inOrder method
	
	public int[] postOrder (TreeNode r, int[] a)
	{
		if (r != null)
		{
			postOrder(r.left, a);
			postOrder(r.right, a);
			
			a[counter3] = r.value;
			counter3++;		//	Increment the counter
			
		}	//	End if statement
		
		//	I don't believe this will ever execute?
		else	return a;
		
		//	Returns the array
		return a;
		
	} //	End postOrder method	
} //	End AVL Tree class
