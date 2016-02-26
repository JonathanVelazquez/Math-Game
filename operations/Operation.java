package operations;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * @author kyle
 * Parent class
 */

public class Operation {
	protected int leftHandSide;
	protected int rightHandSide;
	protected int  answer;
	//accessor
	public int getLeft(){
		return leftHandSide;
	}
	public int getRight(){
		return rightHandSide;
	}
	public void setLeft(int left){
		this.leftHandSide =  left;
	}
	public void setRight(int right){
		this.rightHandSide = right;
	}
	public int getAnswer(){
		return this.answer;
	}
	//override this
	public boolean isRightShip(){
		return true;
	}
	//Override this
	public int shipAnswer(){
		return 0;
	}
	//override this
	public void initiate(int a){
		
	}
	
}
