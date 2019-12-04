/*
 * Names: Paul Scheeler, Devin Porter, Sydney Jenkins, Brandon Wong
 * CPS 350 01
 * Project 3
 * 
 * AVLTest.java
 * 
 */

public class AVLTest 
{

	public static void main(String[] args) 
	{
		// This test case is from Geeksforgeeks.org, I used this to test it since they have it drawn out and
		// it's easy to visualize this way.
		
		AVLTree tree = new AVLTree();
		
		int[] test = new int[10];	  //  preOrder
		int[] test2 = new int[10];	//  inOrder
		int[] test3 = new int[10];	//	postOrder

    tree.root = tree.insert(tree.root, 10); 
    tree.root = tree.insert(tree.root, 20); 
    tree.root = tree.insert(tree.root, 30); 
    tree.root = tree.insert(tree.root, 40); 
    tree.root = tree.insert(tree.root, 50); 
    tree.root = tree.insert(tree.root, 25); 
		
    //  TESTS THE DELETE METHOD
//  tree.root = tree.insert(tree.root, 100); 
//  tree.root = tree.delete(tree.root,  100);
		
    //  Displays the root node
		System.out.println("This is the root: " + tree.root.value);
		
		System.out.println();
		
		test = tree.preOrder(tree.root, test);

		System.out.println("preOrder array: ");
		for (int i = 0; i < test.length && test[i] != 0; i++)		System.out.print(test[i] + "  ");

		System.out.println();
		System.out.println();
		
		test2 = tree.inOrder(tree.root, test2);
		
		System.out.println("inOrder array: ");
		for (int i = 0; i < test2.length && test[i] != 0; i++)		System.out.print(test2[i] + "  ");

		System.out.println();
		System.out.println();
		
		test3 = tree.postOrder(tree.root, test3);
		
		System.out.println("postOrder array: ");
		for (int i = 0; i < test3.length && test[i] != 0; i++)		System.out.print(test3[i] + "  ");
		
	} //  End main method
} //  End Project 3
