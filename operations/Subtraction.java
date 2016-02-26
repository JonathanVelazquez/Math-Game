package operations;
import java.util.Random;

/**
 * Subtraction class
 * @author kyle
 * subtract two random number from 0 to 50
 */
public class Subtraction extends Operation{
	public Subtraction(){
		
	}
	@Override
	public void initiate(int a){
		Random ran = new Random();
		rightHandSide = ran.nextInt(a);
		leftHandSide = a + rightHandSide;
		answer = a;
	}
	@Override
	public boolean isRightShip(){
		if(leftHandSide - rightHandSide == answer){
			return true;
		}
		return false;
	}
	@Override
	public int shipAnswer(){
		return leftHandSide-rightHandSide;
	}
}
