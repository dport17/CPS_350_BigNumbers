import java.util.*;

public class Channel 
{ 
	//the two prime numbers from which the keys will be fashioned
	BigNumber p; 
	BigNumber q; 
	//for n in both keys
	BigNumber n;
	//for the value of n
	BigNumber pTimesq=new BigNumber("");
	//for the public key
	BigNumber e=new BigNumber("11");
	//for the private key
	//In order for d*e to be one less than the product of (p-1)*(q-1), I think we need d=(((p-1)*(q-1)-1)/e)? Not sure if there's 
	//some mathematical principle where if you have a prime minus 1 times a prime minus one, and then take 1 away from that result,
	//you somehow get a number divisible by ANY prime? Idk.
	BigNumber d;
	
	//This constructor might be wrong, I just threw it together.
	//I'm also realizing that all the user plans on giving us is a message of characters. We might need to use this class to switch
	//a given message over to a unicode string, then into a BigNumber.
	public Channel(BigNumber p, BigNumber q) {
		
		//if both the user input values are prime, we're in business.
		//Other than, ya know, we don't have a message to apply them to.
		if(p.isPrime()&&q.isPrime()) {
			this.p=p;this.q=q;
			n=pTimesq.multi(this.p,this.q);
		}
		//if not, RIP. This case needs to be handled. Not sure how.
		else {
			System.out.println("p, q, or both are not prime.");
		}
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
		BigNumber c;
		c=(m.toThePowerOf(e));
		//Okay, now we need to find the remainder of c/n. n is, of course, part of both the public and private keys.
		//Once we find that remainder, we need to assign c to it.
		c=BigNumber.modulus(c,n);
		//Now that c has been calculated by using m and the values in the public key, it represents an encrypted version of m!
		return c;
	
	}//end encrypt
	
	//Okay, so now the receiver of the message just gets c, which is just some BigNumber that, if it were turned into a string,
	//wouldn't become the right unicode value to output the original string! We need to decrypt it.
	public BigNumber decrypt(BigNumber c){
		BigNumber m;
		//raise c to the d power and assign it to m.
		m=(c.toThePowerOf(d));
		//now find the remainder of m/n.
		m=BigNumber.modulus(m, n);
		//This SHOULD give back the unicode value, as a BigNumber, of the original message which we were meant to encrypt. From here, in the main
		//class, we can call a "backToString" method which exists in the BigNumber class, and somehow turn it back
		//into an actual string of characters. Idk about that part yet.
		return m;
	}//end decrypt
	
}	//	End Channel class
