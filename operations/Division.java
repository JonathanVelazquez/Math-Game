package operations;

import java.util.Random;


public class Division extends Operation{
	public Division(){
	}
	public void initiate(int a){
		Random ran = new Random();
		rightHandSide = ran.nextInt(10)+2;
		leftHandSide = a*rightHandSide;
		answer = a;
	}
	@Override
	public boolean isRightShip(){
		if(answer*rightHandSide == leftHandSide){
			return true;
		}
		return false;
	}
	@Override
	public int shipAnswer(){
		return answer;
	}

	
}
