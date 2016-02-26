package operations;

import java.util.Random;

public class Multiplication extends Operation{
	public Multiplication(){
	}
	public void initiate(int a){
		Random ran = new Random();
		//Find the GCD of a
		for (int i = 1; i < a; i++) {
			if(a%i ==0){
				leftHandSide = i;
			}
		}
		
		rightHandSide = a/leftHandSide;
		answer = a;
	}
	@Override
	public boolean isRightShip(){
		if(leftHandSide *rightHandSide == answer){
			return true;
		}
		return false;
	}
	@Override 
	public int shipAnswer(){
		return leftHandSide*rightHandSide;
	}
}
