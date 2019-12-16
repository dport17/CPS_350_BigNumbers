package bigNums;
import java.util.*;

/*Devin Porter, Paul Scheeler, Sydney Jenkins, Brandon Wong*/

public class Channel 
{ 
	//this is where we'll test e options from
	int[] eOptions= {11, 17, 257, 65537};
	//the two prime numbers from which the keys will be fashioned
	int p; 
	int q; 
	//for n in both keys
	int n;
	//for the value of n
	//for the public key
	int e;
	//for the private key
	int d;
	//both d and e will be switched over to BigNumbers eventually so we can encrypt and decrypt.
	
	//This constructor might be wrong, I just threw it together.
	//I'm also realizing that all the user plans on giving us is a message of characters. We might need to use this class to switch
	//a given message over to a unicode string, then into a BigNumber.
	
	//isPrime method... NOT SURE IF RIGHT!!!!!!!!!!!1
	/*
	public boolean isPrime(BigNumber num)
	{
	    if (num == 2)
	    { return true ; }
	    //# prime nums can't be less divisible by two (except 2)
	    if (num < 2) || (num % 2 == 0)
	    {return false ; }
	  for(int i = 2; i <= num/2; ++i){
		  if (num % n == 0)
			{return false ; }
	   else
		   return true ; }
	}
	*/
	
	public Channel(int p) {
		
		this.p=p;
		this.q=nextPrime(p);
		System.out.println(q+ " has been selected as q.");
		n=p*q;
		System.out.println(n + " has been calculated as n.");
		int phi=((p-1)*(q-1));
		System.out.println(phi + " has been calculated as phi.");
		//if both the user input values are prime, we're in business.
		//Other than, ya know, we don't have a message to apply them to.
		/*if(isPrime(p)&&isPrime(q)) {
			this.p=p;this.q=q;
			n=BigNumber.multi(this.p,this.q);
		}
		//if not, RIP. This case needs to be handled. Not sure how.
		else {
			throw new Exception("p, q, or both are not prime."); //if not prime, throw exception and terminate.
		}
		*/
		
		//continuing work on this. Now that we have p, q, and n, we need to find a d and an e to
		//craft the keys from. This is kind of complicated, because to some extent, both depend on n, p and q.
		//If phi, which is the same as ((p-1)*(q-1)), is a certain number, and there is no modular inverse d of 
		//e mod phi such that e*d mod phi=1. In that case, we need a new e. 
		//however, IF the greatest common divisor of e and phi is 1, which means the 2 are coprime, 
		//then there must exist a modular inverse of e mod phi, which is our d value. This means that 
		//we'll need to test e values until one of them is coprime with phi.
		//YAY. Apparently the array at the beginning of this class will allow us to do that.
		//For this, I need a gcd method. It's called greatestCommonDivisor.
		for (int i=0;i<eOptions.length;i++) {
			e=eOptions[i];
			System.out.println("e is currently: "+e);
			int result1=greatestCommonDivisor(this.p-1, e);
			int result2=greatestCommonDivisor(this.q-1, e);
			System.out.println("The greatest common divisor of "+e+" and p-1 is "+result1+" and the greatest common divisor of " +e+" and q-1 is "+ result2);
			if(result1==1&&result2==1) {
				break;
			}
		}//end for
		
		System.out.println(e + " has been selected as e.");
		
		//Okay, so since the value of d*e has to be larger than phi in order for (d*e)%phi to be 1, or anything
		//other than the number itself, and we already know e, I if I can find the first value of d that makes
		//d*e larger than phi, that's the first possibility value of d that might make (d*e)% phi=1.
		//SO
		//What if that doesn't work? Well, we can just continuously increment d by 1, and thus increase e*d 
		//by an addition of e repeatedly until we get a value for d that works, OR
		//realize that there won't be another opportunity for (d*e)%phi to be 1 until we have a value of (d*e) that
		//is greater than 2*phi.
		
		//The PROBLEM is, where's the stopping case? 
		//Obviously if we find a number that works, that's great. But what if we don't, because it doesn't exist,
		//and we just loop and loop and loop? For this, I'm making the assumption that because e and phi are coprime,
		//there will be a d that works.
		
		d=phi/e;
		d++;
		int i=2;
		while(((d*e)%phi)!=1) {
			int nextPossible=phi*i;
			d=nextPossible/e;
			d++;
			i++;
		}// end while
		System.out.println(d+ " has been selected as d.");
		
		//Okay, now all of them need to be BigNumbers so that I can operate on them. I could have made all of these BigNumbers before, but that didn't
		//happen. Might go back and do it later.		
	}//end Channel constructor
	
	//What all this is doing:
	//Now, I don't get the math here, but it's what we had in the assignment. 
		//Apparently, if I have 7 numbers:
		//m, c, d, e, p, q, and n
	
		//where n=p*q, and p and q are both prime numbers
		//and (d*e)%((p-1)*(q-1))=1
		//then, no matter what d and e become, as long as they satisfy the above equation
		//I can take any number m, and raise it to the power of e. Then take the result of it, and find result%n.
	
		//Now, let's call "result%m", "c".
	
		//Apparently, if I take c, and raise it to the "d" power instead of the "e", and then use the result
		//(call it newResult)
		//I will find that "newResult%n" will give me the exact same number as m.
	
	//Okay, so we get the super long unicode value, m, as a BigNumber and pop it in here. This will raise that number to the power of
	//e, which is part of the public key, and assign the result of that to c.
	public BigNumber encrypt(BigNumber m) {
		System.out.println("hi");
		m.simplify();
		BigNumber encryptorE=new BigNumber(Integer.toString(e));
		BigNumber keyN=new BigNumber(Integer.toString(n));
		BigNumber c;
		System.out.println("Still here...");
		encryptorE.display_bigEnd();
		m.display_bigEnd();
		c=(m.toThePowerOf(encryptorE));
		System.out.println("The number to be modded by n is ");
		c.display_bigEnd();
		//Okay, now we need to find the remainder of c/n. n is, of course, part of both the public and private keys.
		//Once we find that remainder, we need to assign c to it.
		c=BigNumber.modulus(c,keyN);
		//Now that c has been calculated by using m and the values in the public key, it represents an encrypted version of m!
		return c;
	
	}//end encrypt
	
	//Okay, so now the receiver of the message just gets c, which is just some BigNumber that, if it were turned into a string,
	//wouldn't become the right unicode value to output the original string! We need to decrypt it.
	
	public BigNumber decrypt(BigNumber c){
		BigNumber m;
		BigNumber decryptorD=new BigNumber(Integer.toString(d));
		System.out.println("The value of d to use for decryption is: ");
		decryptorD.display_bigEnd();
		BigNumber keyN=new BigNumber(Integer.toString(n));
		System.out.println("The value of n to be used for decryption is");
		keyN.display_bigEnd();
		//raise c to the d power and assign it to m.
		m=(c.toThePowerOf(decryptorD));
		System.out.println("After raising to the power of d, the value of m to be modded by n is ");
		m.display_bigEnd();
		//now find the remainder of m/n.
		m=BigNumber.modulus(m, keyN);
		//This SHOULD give back the unicode value, as a BigNumber, of the original message which we were meant to encrypt. From here, in the main
		//class, we can call a "backToString" method which exists in the BigNumber class, and somehow turn it back
		//into an actual string of characters. Idk about that part yet.
		
		while (m.size()%3!=0) {
			m.tail.next=new Digit(0);
			m.tail.next.previous=m.tail;
			m.tail=m.tail.next;
		}
		
		System.out.println("The result of decryption is: ");
		m.display_bigEnd();
		return m;
		
	}//end decrypt
	
	private int nextPrime(int p) {
		int primeNum=p+1;
		while(!(isPrime(primeNum))) {
			primeNum++;
		}
		return primeNum;
	}
	
	private boolean isPrime(int num) {
		boolean prime=true;
		for(int i=2;i<(int)(Math.sqrt(num));i++) {
			if(num%i==0) {
				prime=false;
				break;
			}
		}//end for
		return prime;
	}//end isPrime
	
	private int greatestCommonDivisor(int a, int b) {
		if(a==0) {
			return b;
		}
		if(b==0) {
			return a;
		}
		
		else {return greatestCommonDivisor(b, a%b);}
	}
}	//	End Channel class
