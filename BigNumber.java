package bigNums;
import java.util.regex.Pattern; // this allows you to split strings

/*
 * Names: Paul Scheeler, Devin Porter, Sydney Jenkins, Brandon Wong 
 * CPS 350 01
 * Project 1
 */

public class BigNumber 
{
	Digit head;  //	reference to the head node
	Digit tail;  //	reference to the tail node
	
	final int BASE_10 = 10;	// defined constant

	public void display_littleEnd()
	{
		System.out.println("\n");
		
		Digit iter = new Digit(); iter = this.head;
		
		//	NOTE: If this method is just to display the end, do you not need the "System.out.print(iter.number);"
		//	"iter" traverses through the list and stops before the tail node,
		//	and displays the int value of each node
		for(; iter != tail; iter = iter.next)		System.out.print(iter.number);
		
		// 	NOTE: Just a question, but this line is not part of the for loop right?
		//	Once "iter" is done traversing, display the "little end" within the tail node
		System.out.println(tail.number);
		
	}	// 	End display_littleEnd method
	
	public void display_bigEnd()
	{	
		System.out.println("\n");
		
		Digit iter = new Digit(); iter=this.tail;
		
		//	"iter" traverses through the list backwards and stops before the head node,
		//	and displays the int value of each node
		for(; iter != head; iter = iter.previous)	System.out.print(iter.number);
		
		// 	NOTE: Same question, but this line is not part of the for loop right?
		//	Once "iter" is done traversing, display the "big end" within the head node
		System.out.print(head.number);	
		
	}	// 	End display_bigEnd method

	//	This method gets rid of leading zeros
	//	NOTE: Does this method actually do anything besides traverse? Also, shouldn't it start at the head?
	//	NOTE: Ex. 0100 -> 0100 (nothing changed, but now the tail is at the node with 1 in it?
	public void simplify()
	{
		//this method gets rid of leading zeros
		while(this.tail.number==0 && this.tail!=this.head)
		{
			this.tail=this.tail.previous;
			this.tail.next=null;
		}
		
	}	// 	End simplify method
	
	public String backToString() {
		Digit iter=this.tail;
		String revertedBigNum="";
		for(; iter!=null;iter=iter.previous) {
			revertedBigNum=revertedBigNum+iter.number;
		}
		return revertedBigNum;
	}//end backToString
	
	public BigNumber(final String strNum)
	{
	    	// 	String number (assumed to be correct) to a doubly linked list

		// 	WE WILL USE LITTLE ENDIAN NOTATION TO MAKE ADDITION / MULT EASIER. THUS, "123" is 3-2-1
		// 	since you add the least significant digits first, then carry
		
		// 	create an array of your numbers
		String[] nums = strNum.split("");
		int max_pos = strNum.length() - 1; //minus one, since array starts at zero
		
		// 	create the head node
		head = new Digit(Integer.parseInt(nums[max_pos]));
		head.previous = null;
		
		Digit iter = new Digit();
		iter = head;

		// 	count from the bottom of the array up, add the digits to linked list
		// 	minus one, since head already has the last digit
		for(int i = max_pos-1; i >= 0; i--)
		{
			//	create digit with proper value
			Digit a = new Digit(Integer.parseInt(nums[i]));
			
			//	situate it into chain
			a.next = null;
			a.previous = iter;
			iter.next = a;
			
			tail = a;		// put the tail at the end	
			iter = iter.next;	// increment the iterator
			
		}	//	End for loop
	}	//	End constructor

	// 	copy constructor, notice that you need to create a new list which represents
	// 	the same number as num
	public BigNumber(final BigNumber num)
	{
		//	create the first node in the copy list
		Digit newNum = new Digit(num.head.number);
		this.head = newNum;
		this.tail = newNum;
		this.head.previous=null;
		
		Digit lead = new Digit(); lead = num.head.next;
		Digit follow = new Digit(); follow=this.head;
		
		for(; lead != null; lead = lead.next)
		{
			Digit n = new Digit(lead.number);
			follow.next = n;
			n.previous = follow;
			tail = n;
			
			follow = follow.next;
			
		}	// 	End for loop	
	}	//	End constructor

	// 	addition assignment: adding the given number to the current number, +=
	public void add_assign(final BigNumber b)
	{
		Digit sum = new Digit(); sum = this.head;
	    
	    	// 	variables needed for loop
	    	Digit iter1 = new Digit(); iter1 = this.head;
	    	Digit iter2 = new Digit(); iter2 = b.head;
	    
	    	int carry = 0;
	    	int value = 0;
	    
	    	while(iter1 != null || iter2 != null || carry != 0)
	    	{
		    	// 	compute the value and carry		    
	    	    	// 	there are four cases, which have to do with the fact that our big nums might not be
	    	    	// 	the same length. additionally, it's possible to overflow
		    
	    		if(iter1 != null && iter2 != null)	value = iter1.number + iter2.number + carry;
	    		else if(iter1 != null && iter2 == null)	value = iter1.number + carry;
	    		else if(iter1 == null && iter2 != null)	value = iter2.number + carry;
	    		else					value = carry;
	    	
	    		//	compute the value and carry in digit form (both less than 9)
	    		carry = value / BASE_10; // BASE_10 = 9
	    		value = value % BASE_10;
	    	
	    		//	assign value
	    		sum.number = value;	
	    		this.tail = sum;
	    	
	    		//	increment
	    		if(iter1 != null)	iter1 = iter1.next;
	    		if(iter2 != null)	iter2 = iter2.next;
	    	
	    		if( iter1 != null || iter2 != null || carry != 0)
	    		{
	    			if(sum.next == null)
	    			{
		    			Digit newDigit = new Digit(); // create a new node and link it up
					
		    			sum.next = newDigit;
		    			newDigit.previous=sum;
		    			this.tail = newDigit;
	    			}
		
	    	    		sum = sum.next;
	    	    		sum.previous.number=value;	// place the correct digit in the sum
				
	    		}	// 	End outer if statement
	    	}	// End while loop
	}	// 	End add_assign method

	// 	addition: return a + b
	public static BigNumber add(final BigNumber a, final BigNumber b)
	{
		BigNumber c = new BigNumber(a);
	    	c.add_assign(b);    // 	calling the method for +=
	    	
		return c;
	
	}	// 	End add method

	  // Please write your code for the other operations:
	  // -=, -, *=, *, /, /=, %, %=, in the same way.
	
	public void sub_assign(final BigNumber b)
	{
		Digit sum = new Digit(this.head);
		Digit iter1 = new Digit(this.head);
		Digit iter2 = new Digit(b.head);
		

		int value = 0;
		int carry = 0;
		
		while(iter1 != null || iter2 != null)
		{
			//	useful numbers
			//	assign value
			if(iter1 != null && iter2 != null)
			{
				int a_val = iter1.number;
				int b_val = iter2.number;
				value = a_val - b_val + carry;
			}
			
			if(iter1 != null && iter2 == null)
			{
				int a_val = iter1.number;
				value = a_val - 0 + carry;
			}	
			
			if(iter1 == null && iter2 != null)
			{
				int b_val = iter2.number;
				value = 0 - b_val + carry;
			}	
			
			else if (iter1 == null && iter2 == null)	value = carry;
			
			//	NOTE: No else statement here
			
			if(iter1 != null)	iter1 = iter1.next;
			if(iter2 != null)	iter2 = iter2.next;
			
			if(value < 0)
			{
				if(iter1 != null || iter2 != null)
				{
					value = 10 + value;
					carry=-1; 	
				}
			}
			
			else		carry = 0;
			
			sum.number = value;
			
	    		if( iter1 != null || iter2 != null )
	    		{
	    			if(sum.next == null)
	    			{
	    				// 	create a new node and link it up
		    			Digit newDigit = new Digit();
		    			sum.next = newDigit;
		    			newDigit.previous=sum;
		    			this.tail = newDigit;
	    			}
		
	    	    	sum = sum.next;
	    	    	sum.previous.number=value; // place the correct digit in the sum
				
			}	//	End outer if statement
		}	// 	End while loop
	}	// 	End sub_assign method
	
	public static BigNumber sub(final BigNumber a, final BigNumber b)
	{
		boolean compare = first_smaller_than_second(a,b);
		
		if(compare==true)
		{
			BigNumber d = new BigNumber(b);
			d.sub_assign(a);
			d.simplify();
			d.make_negative();
			
			return d;
		}	// End if statement
		
		BigNumber c = new BigNumber(a);
		c.sub_assign(b);
		c.simplify();
		
		return c;
		
	} 	//	End sub method
	
	public void make_negative()
	{
		this.tail.number = this.tail.number * -1;
		
	}	//	End make_negative method
	
	public static boolean first_smaller_than_second(final BigNumber a, final BigNumber b)
	{
	
		//	if b > a, return true
		Digit iter_a = a.head;
		Digit iter_b = b.head;
		
		//	first check if the a & b are the same length. here a & b refer to the sizes of a and b
		//	if a > b, return false. if b > a, true.
		//	if a = b, check digit by digit starting at the tail to see a>b, b>a, a=b. if a>b, return false. 
		//	if b>a, return true
		//	if a = b, return false
		
		int size_a = 0; 
		int size_b = 0;
		
		for(; iter_a != null; iter_a = iter_a.next)	size_a++;
		for(; iter_b != null; iter_b = iter_b.next)	size_b++;
		
		//	check the easy conditions
		if(size_a > size_b)	return false;
		if(size_b > size_a)	return true;
		
		//	otherwise, size_a=size_b
		//	begin at the most significant digits
		iter_a = a.tail;
		iter_b = b.tail;
		
		while(iter_a != a.head && iter_b != b.head)
		{
			if(iter_a.number > iter_b.number)	return false;
			if(iter_b.number > iter_a.number)	return true;
			
			//	otherwise we need to check again!
			iter_a = iter_a.previous;
			iter_b = iter_b.previous;
		}
		
		if(a.head.number >= b.head.number)	return false;
		if(b.head.number > a.head.number)	return true;

		return false;
		
	}	// 	End first_smaller_than_second method
	public void multi(BigNumber a, BigNumber b) 
	{
		BigNumber c = new BigNumber("1");
		BigNumber sum = new BigNumber("0");
		while(!(a.head == a.tail) && (a.head.number == 0)) 
		{
			sum.add_assign(b);
			a.sub_assign(c);
		}
		
	}	//	end multi method
	
	/*
	This will return a BigNumber to a given power. HOWEVER, since we're using Little Endian notation, IT DOES NOT
	RETURN A STRING IN THE CORRECT ORDER. In the Channel class, then, we'll need to convert the BigNumbers 
	back to strings to the correct order before we can analyze the string of numbers, 
	which we'll have to do since it's in unicode, and convert it back to an actual message.
	I'm writing a method for that called "backToString"
	*/

	public BigNumber toThePowerOf(BigNumber a) 
	{
		BigNumber minusOne = new BigNumber("1");
		BigNumber result = new BigNumber(this);
		
		while(!((a.head.number <= 1) && (a.head == a.tail))) 
		{
			result.add_assign(multi(this, result));
			a.sub_assign(minusOne);
		}
		
		return result;
		
	}	//	end toThePowerOf method
	
	public boolean isPrime(int n) 
	{
        	if (n < 0)			return false;	// negative number
        	if (n == 0 || n == 1) 		return false;	// 0 or 1
        	if (n == 2 || n == 3) 		return true;	// 2 or 3
        	if ((n * n - 1) % 24 == 0)	return true; 	// if this executes, then it is a prime number
		else				return false;	// every other number should be a composite number
       
	}	//	End isPrime method
	
	// compute: a % b
	static BigNumber modulus(BigNumber a, BigNumber b)
	{
		
		/* 		to compute a % b
		 * 		do a - b, until b<a is false
		 * 		return a, which will be the remainder
		 */

		while( first_smaller_than_second(b,a) )	
			a = sub(a,b);

		//after this process is done, a will be zero (if a/b is whole number) or the remainder
		return a;
	} //end modulus method
	
	// compute: a/b
	static BigNumber div(BigNumber a, BigNumber b)
	{
		BigNumber one = new BigNumber("1");
		BigNumber quotient = new BigNumber("0");
		
		/* 		compute a % b
		 * 		do a - b, until b>a
		 * 
		 */
		
		// every time you can successfully subtract b from a, increment quoetient--since, quotient * b  = a - remainder
		while( first_smaller_than_second(b,a) )
		{
			a = sub(a,b);
			quotient.add_assign(one);
			
		}
		
		//after this process is done, a will be zero (if a/b is whole number) or the remainder
		return quotient;
	} //end div method
} 	//	End BigNumber Class

