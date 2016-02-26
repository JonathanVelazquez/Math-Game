package operations;

import java.awt.Rectangle;
import java.util.Random;

/**
 * Addition class
 * @author kyle
 *
 */
public class Addition extends Operation {
	
	/**
	 * no args constructor
	 * generates a random number in array
	 */
	@Override
	public void initiate(int a){
		Random ran = new Random();
		super.leftHandSide = ran.nextInt(a);
		super.rightHandSide = a - leftHandSide;
		super.answer = a;
	}
	//constructor overload
	public Addition(){
	}
	@Override
	public boolean isRightShip(){
		if(leftHandSide + rightHandSide == answer){
			return true;
		}
		return false;
	}
	@Override
	public int shipAnswer(){
		return leftHandSide + rightHandSide;
	}
}
