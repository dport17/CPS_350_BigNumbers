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
	BigNumber e;
	//for the private key
	BigNumber d;
	public Channel(BigNumber p, BigNumber q) {
		if(p.isPrime()&&q.isPrime()) {
			this.p=p;this.q=q;
			n=pTimesq.multi(this.p,this.q);
		}
		else {
			System.out.println("p, q, or both are not prime.");
		}
	}//end Channel constructor
	
	
	public BigNumber encrypt(BigNumber m) {
		BigNumber c;
		c=(m.toThePowerOf(e));
		c=BigNumber.modulus(c,n);
		return c;
	
	}//end encrypt
	public BigNumber decrypt(BigNumber c){
		BigNumber m;
		m=(c.toThePowerOf(d));
		m=BigNumber.modulus(m, n);
		return m;
	}//end decrypt
	
}	//	End Channel class
