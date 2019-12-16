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
	//  REPLY: it starts at the tail because the tail contains the most significant digit!--which is where
	//        leading zeros will be  
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
		tail=head;
		// 	count from the bottom of the array up, add the digits to linked list
		// 	minus one, since head already has the last digit
		for(int i = max_pos-1; i > 0; i--)
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
		// check for a negative sign... if no negative sign, simply add in the last number to the tail 
		// otherwise, make the current tail negative
		if( strNum.charAt(0) == '-')
		{
			this.tail.number = this.tail.number * -1;
		}
		else // otherwise, we assume the first element is a number 
		{
			// create digit with proper value
			Digit a = new Digit(Integer.parseInt(nums[0]));
			
			// situate it
			a.next = null;
			a.previous = iter;
			iter.next = a;
			
			tail = a;
		}
		
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
		if( a.isNegative() &&  b.isNegative() )
		{
			// (-a) + (-b) = -(a + b)
			a.make_negative(); b.make_negative();
			BigNumber c = new BigNumber(a);
			c.add_assign(b);
			c.make_negative();
			return c;
		} 
		else if( ! a.isNegative() && b.isNegative() )
		{
			// a + (-b) = a - b
			// make b positive
			b.make_negative();
			// subtract b from a
	    	BigNumber c = BigNumber.sub(a, b);
	    	c.simplify();
	    	return c;
		}
		else if( a.isNegative() && ! b.isNegative() )
		{
			// (-a) + b = b - a
			a.make_negative();
			BigNumber c = BigNumber.sub(b,a);
			c.simplify();
			return c;
		}
		// otherwise, just do things normally (both a and b are positve)
		BigNumber c = new BigNumber(a);
	    	c.add_assign(b);    // 	calling the method for +=
	    	
		return c;
	
	}	// 	End add method

	  // Please write your code for the other operations:
	  // -=, -, *=, *, /, /=, %, %=, in the same way.
	
	public void sub_assign(final BigNumber b)
	{
		if(this.head.next==null&&b.head.next==null) {
			this.head.number-=b.head.number; 
			return;
		}
		
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

		if( ! a.isNegative() && b.isNegative() )
		{
			// a - (-b) = a + b
			b.make_negative();
			BigNumber d = BigNumber.add(a, b);
			return d;
		}		
		else if( a.isNegative() && ! b.isNegative() )
		{
			// (-a) - b = -(a+b)
			a.make_negative(); 
			BigNumber d = BigNumber.add(a, b);
			d.make_negative();
			return d;
		}
		else if( a.isNegative() && b.isNegative() )
		{
			// now, if a and b are both negative, we compare the magnitudes of a and b
			// whichever has a larger magnitude will be the smaller number!
			a.make_negative(); b.make_negative();
			
			// switch a and b (represented here by taking the negation of first_smaller)
			boolean compare = ! first_smaller_than_second(a,b);
			
			if(compare)
			{
				BigNumber d = new BigNumber(b);
				d.sub_assign(a);
				d.simplify();
				d.make_negative();
				
				return d;
			}	// End if statement
			
			BigNumber d = new BigNumber(a);
			d.sub_assign(b);
			d.simplify();
			d.make_negative();
			
			return d;	
		}

		
		// otherwise, a and b are both positive
		boolean compare = compare = first_smaller_than_second(a,b);
		if(compare)
		{
			BigNumber d = new BigNumber(b);
			d.sub_assign(a);
			d.simplify();
			d.make_negative();
			
			return d;
		}	// End if statement
		
		BigNumber d = new BigNumber(a);
		d.sub_assign(b);
		d.simplify();
		
		return d;
		
	} 	//	End sub method
	
	public boolean isNegative()
	{
		if(this.tail.number < 0)
			return true;
		
		return false;
	}
	
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
		
		if(a.head.number > b.head.number)	return false;
		if(b.head.number >= a.head.number)	return true;

		return false;
		
	}	// 	End first_smaller_than_second method
	//compute and return a*b
	public static BigNumber multi(BigNumber a, BigNumber b) 
	{
		//first, figure out which number is smaller and which is larger. This makes it easier to multiply, at least it's more intuitive for me.
		BigNumber smallerNum=new BigNumber("0");
		BigNumber biggerNum=new BigNumber("0");
		
		if(first_smaller_than_second(a,b)) {
			smallerNum=a;
			biggerNum=b;
		}
		
		else {
			biggerNum=a;
			smallerNum=b;
		}
		
		//now, imagine you're doing multiplication by hand. You know there will be as many rows as there are digits in the number on the bottom, assuming
		//that the number on the bottom has exactly as many or less digits than the one on top, the larger number. This array will hold each of those rows.
		BigNumber[] numArray=new BigNumber[smallerNum.size()];
		
		//this will iterate over the smaller number.
		Digit iter2=smallerNum.head;
		
		//this will help determine two things: first, where to put the most recent BigNum in a row while solving, and also to help keep track of when we 
		//need to add a zero to a row before we can begin adding actual numerical values to it. 
		int count=-1;
		for(; iter2!=null;iter2=iter2.next) {
			
			//I named each of the rows "sum". Perhaps "row" would be more appropriate. Imagine it as one of the rows you see when solving a multiplication
			//problem by hand, before you eventually add them all up.
			
			//also, iter1 will iterate over the bigger number. Of course, each of the digits in the smaller number will need to me multiplied by each one
			//in the larger number. 
			Digit iter1=biggerNum.head;
			//This "sum" BigNumber resets to one Digit of number 0 every time we move from one digit in the smaller number to the next Digit in it.
			//This is because we have started a new row. 
			BigNumber sum=new BigNumber("0");
			//the sumIter will iterate over the sum BigNumber, so that more Digits can be appended to it and it can eventually be added to the numArray.
			Digit sumIter=sum.head;
			//we need to reset carry to 0 each time we move to a new digit in smallerNum.
			int carry=0;
			//we increase count by one each time we move to a new digit in smallerNum
			count++;
			
			//this will add the zeros to the new row. It also moves sumIter to the END DIGIT OF THE BIGNUMBER "SUM", which is coincidentally the tail.
			//This tail can and will change when the value of it is determined by multiplication.
			for(int i=1;i<=count;i++) {
				Digit nextDig=new Digit(0);
				sumIter.next=nextDig;
				nextDig.previous=sumIter;
				sumIter=sumIter.next;
				sum.tail=sumIter;
			}//end for loop
			
			//Now, we begin traversing the biggerNum.
			for(; iter1!=null; iter1=iter1.next) {
				//we store the product of the numbers of iter1 and iter2, which are really whatever digits the two iters are at 
				//at a given time.
				int storage=iter1.number*iter2.number;
				//we then assign the number of the Digit where sumIter is to the remainder of (storage+carry)/10, just as you would when
				//multiplying by hand.
				sumIter.number=(storage+carry)%10;
				//carry becomes, basically, the first number in a two digit integer, and this will evaluate to zero if the sum of itself and
				//storage is less than 10.
				carry=(storage+carry)/10;
				
				//Since sumIter.next will be null, we need to make a new Digit to follow it up.
				if(!((iter1.next==null)&&(carry==0))){
					
					sumIter.next=new Digit();
					sumIter.next.previous=sumIter;
					//and increment sumIter.
					sumIter=sumIter.next;
					//and set the tail to it.
					sum.tail=sumIter;
					
					//Now that we've added that digit, if iter1.next is null, this loop will not execute again. Because of that, we need to update the 
					//number data field of the new Digit to whatever is left over from carry.
					if(iter1.next==null&&carry!=0) sumIter.number=carry;
				}//end if					
				
			}//end for
			
			//Now, populate the array with the row, called sum, which is being treated as a BigNumber so that all of the rows can be added together
			//at the end, as in hand-done multiplication.
			numArray[count]=new BigNumber(sum);
			//now we can reset sum, carry, and sumIter, so that we can do the whole process again with the next digit in smallerNum.
		}//end outer for
		
		//Once the array has been populated, we need to add each of the BigNumbers, or "rows" that we placed in it.
		//I'll start by just calling the result the first BigNumber in the array.
		BigNumber result=new BigNumber(numArray[0]);
		
		for(int j=1; j<numArray.length;j++) {
			//now, I'll add each of the rows following the first and updated result every time, until I reach the end of the array.
			result=BigNumber.add(result, numArray[j]);
		}//end for loop
		
		//Now, we return the result, which is the product of the two BigNumbers we were given.
		return result;
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
	public static BigNumber modulus(BigNumber a, BigNumber b)
	{
		if(a==b) {
			return new BigNumber("0");
		}
		else if(first_smaller_than_second(a,b)) {
			return modulus(b,a);
		}
		
		BigNumber resultOfDiv=a.divideBy(b);
		BigNumber resOfMulti=multi(b, resultOfDiv);
		return sub(a, resOfMulti);
		
	} //end modulus method
	
	// compute: a/b
	public BigNumber divideBy(BigNumber smallerThanThis) {
		boolean oneWasNegative=false;
		if(this.tail.number<0||smallerThanThis.tail.number<0) {
			if(this.tail.number<0&&smallerThanThis.tail.number<0) {
				this.tail.number*=-1;
				smallerThanThis.tail.number*=-1;
			}
			else if(this.tail.number<0){
				this.tail.number*=-1;
				oneWasNegative=true;
			}
			else {
				smallerThanThis.tail.number*=-1;
				oneWasNegative=true;
			}
		}
		BigNumber numToSubFrom=new BigNumber(findNumToSubFrom(smallerThanThis, this));
		Digit numTrackIter=numToSubFrom.head;
		Digit thisIter=this.tail;
		
		for(int i=1;i<numToSubFrom.size();i++) {
			thisIter=thisIter.previous;
		}
		
		BigNumber[] result=new BigNumber[this.size()];
		int currResultPos=0;
		int currResSize=0;
		
		while(!(thisIter==null)) {
			
			BigNumber increment=new BigNumber("0");
			BigNumber currentVal=new BigNumber("0");
			//currentVal.display_bigEnd();
			BigNumber currRemainder=sub(numToSubFrom, currentVal);
			//currRemainder.display_bigEnd();
			
			
			while (first_smaller_than_second(smallerThanThis, currRemainder)) {
				increment.add_assign(new BigNumber("1"));
				currentVal=multi(smallerThanThis, increment);
				//currentVal.display_bigEnd();
				currRemainder=sub(numToSubFrom, currentVal);
				//currRemainder.display_bigEnd();
			}//end while
			
			result[currResultPos]=increment;
			currResultPos++;
			currResSize++;
			
			numToSubFrom=sub(numToSubFrom, currentVal);
			
			if(thisIter.previous!=null) {
				numTrackIter=numToSubFrom.head;
				numTrackIter.previous=new Digit(thisIter.previous.number);
				numTrackIter.previous.next=numTrackIter;
				numTrackIter=numTrackIter.previous;
				numToSubFrom.head=numTrackIter;
			}
			
			thisIter=thisIter.previous;
			
		}//end while
		
		BigNumber finalResult=new BigNumber("0");
		finalResult.tail=new Digit(result[0].head.number);
		Digit iter=finalResult.tail;
		finalResult.head=iter;
		
		for(int i=1;i<currResSize;i++) {
			iter.previous=new Digit(result[i].head.number);
			iter.previous.next=iter;
			iter=iter.previous;
			finalResult.head=iter;
		}//end for
		finalResult.simplify();
		if(oneWasNegative) {
			finalResult.make_negative();
		}
		return finalResult;
	}//end division
	
	//this is a helper method for div. It allows us to identify the smallest subset of digits from which we can 
	//subtract the closest divisor of the number to be divided by, to the number to be subtracted from using
	//long division techniques.
	
	private static BigNumber findNumToSubFrom(BigNumber smaller, BigNumber bigger) {
		if(first_smaller_than_second(bigger, smaller)) {
			return findNumToSubFrom(bigger, smaller);
		}
		Digit iter=bigger.tail;
		BigNumber newNum=new BigNumber("0");
		newNum.head=new Digit(iter.number);
		newNum.tail=newNum.head;
		Digit newNumIter=newNum.tail;
		
		while(first_smaller_than_second(newNum, smaller)) {
			newNumIter.previous=new Digit(iter.previous.number);
			newNumIter.previous.next=newNumIter;
			iter=iter.previous;
			newNumIter=newNumIter.previous;
			newNum.head=newNumIter;
		}
		//Basically what I'm returning here is a whole new BigNumber that's isolated the smallest sub-number, if you will,
		//of the number that we're trying to divide that is still larger than the number we're dividing by.
		return newNum;
	}
	
	public int size() {
		int count=0;
		Digit iter1=this.head;
		for(; iter1!=null;iter1=iter1.next) {
			count++;
		}
		return count;
	}
} 	//	End BigNumber Class

