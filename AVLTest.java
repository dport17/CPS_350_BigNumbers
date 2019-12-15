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
		AVLTree tree = new AVLTree();
		
		int[] test = new int[10];	// 	preOrder
		int[] test2 = new int[10];	// 	inOrder
		int[] test3 = new int[10];	//	postOrder

        	tree.root = tree.insert(tree.root, 1); 
        	tree.root = tree.insert(tree.root, 2); 
        	tree.root = tree.insert(tree.root, 3); 
        	tree.root = tree.insert(tree.root, 4); 
        	tree.root = tree.insert(tree.root, 5); 
        	tree.root = tree.insert(tree.root, 6); 
        	tree.root = tree.insert(tree.root, 7); 
        	tree.root = tree.insert(tree.root, 8); 
        	tree.root = tree.insert(tree.root, 9); 

        	//	Tests the delete case
        	tree.root = tree.delete(tree.root, 6);
		
		System.out.println("This is the root: " + tree.root.value);
		System.out.println();
		
		test = tree.preOrder(tree.root, test);

		System.out.println("preOrder array: ");
		for (int i = 0; i < test.length && test[i] != 0; i++)		System.out.print(test[i] + "  ");

		System.out.println();
		System.out.println();
		
		test2 = tree.inOrder(tree.root, test2);
		
		System.out.println("inOrder array: ");
		for (int i = 0; i < test2.length && test2[i] != 0; i++)		System.out.print(test2[i] + "  ");

		System.out.println();
		System.out.println();
		
		test3 = tree.postOrder(tree.root, test3);
		
		System.out.println("postOrder array: ");
		for (int i = 0; i < test3.length && test3[i] != 0; i++)		System.out.print(test3[i] + "  ");
		
	}	//	End main method
}	//	End Project 3
