import java.util.*;

public class Channel 
{ 
	BigNumber p; 
	BigNumber q; 
	BigNumber n;
	BigNumber pTimesq=new BigNumber("");
	int e = 11 ; 
	public Channel(BigNumber p, BigNumber q) {
		if(p.isPrime()&&q.isPrime()) {
			this.p=p;this.q=q;
			n=pTimesq.multi(this.p,this.q);
		}
		else {
			System.out.println("p, q, or both are not prime.");
		}
	}//end Channel constructor
	
	
	
	
	
	public encrypt(String x) {
		
	
	}//end encrypt
	public decrypt{
		
		
	}//end decrypt
	
}	//	End Channel class
