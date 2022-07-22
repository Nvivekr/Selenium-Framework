package keyboardpress;
import java.awt.AWTException;
import java.awt.Robot;


import static java.awt.event.KeyEvent.*;

public class Keyboard {
	private Robot robot;
	public Keyboard() throws AWTException{
		this.robot= new Robot();
	}

	public Keyboard(Robot robot) {
		this.robot=robot;
	}
	public void type(CharSequence characters) {
		int length = characters.length();
		for(int i=0; i<length; i++) {
			char character = characters.charAt(i);
			type(character);
		}
		
	}
	
	private void doType(int... keyCodes) {
		doType(keyCodes, 0 , keyCodes.length);
	}
	
	private void doType(int[] keyCodes,int offset, int length) {
		System.out.println("start of function  ");
		if(length==0) {
			return ;
		}
		System.out.println("key press event");;
		robot.keyPress(keyCodes[offset]);
		
		doType(keyCodes,offset+1,length-1);   // 
		System.out.println("key release event");
		robot.keyRelease(keyCodes[offset]);
	}
	
	public  void type(char character) {
		switch(character) {
		case 'a':doType(VK_A); break; case 'q':doType(VK_Q); break;
		case 'b':doType(VK_B); break; case 'r':doType(VK_R); break;
		case 'c':doType(VK_C); break; case 's':doType(VK_S); break;
		case 'd':doType(VK_D); break; case 't':doType(VK_T); break;
		case 'f':doType(VK_F); break; case 'u':doType(VK_U); break;
		case 'e':doType(VK_E); break; case 'v':doType(VK_V); break;
		case 'g':doType(VK_G); break; case 'w':doType(VK_W); break;
		case 'h':doType(VK_H); break; case 'x':doType(VK_X); break;
		case 'i':doType(VK_I); break; case 'y':doType(VK_Y); break;
		case 'j':doType(VK_J); break; case 'z':doType(VK_Z); break;
		case 'k':doType(VK_K); break;
		case 'l':doType(VK_L); break;
		case 'm':doType(VK_M); break;
		case 'n':doType(VK_N); break;
		case 'o':doType(VK_O); break;
		case 'p':doType(VK_P); break;
		case 'A':doType(VK_SHIFT,VK_A); break; case 'Q':doType(VK_SHIFT,VK_Q); break; case 'K':doType(VK_SHIFT,VK_K);
		case 'B':doType(VK_SHIFT,VK_B); break; case 'R':doType(VK_SHIFT,VK_R); break;case 'L':doType(VK_SHIFT,VK_L);
		case 'C':doType(VK_SHIFT,VK_C); break; case 'S':doType(VK_SHIFT,VK_S); break;case 'M':doType(VK_SHIFT,VK_M);
		case 'D':doType(VK_SHIFT,VK_D); break; case 'T':doType(VK_SHIFT,VK_T); break;case 'N':doType(VK_SHIFT,VK_N);
		case 'F':doType(VK_SHIFT,VK_F); break; case 'U':doType(VK_SHIFT,VK_U); break;case 'O':doType(VK_SHIFT,VK_O);
		case 'E':doType(VK_SHIFT,VK_E); break; case 'V':doType(VK_SHIFT,VK_V); break;case 'P':doType(VK_SHIFT,VK_P);
		case 'G':doType(VK_SHIFT,VK_G); break; case 'W':doType(VK_SHIFT,VK_W); break;
		case 'H':doType(VK_SHIFT,VK_H); break; case 'X':doType(VK_SHIFT,VK_X); break;
		case 'I':doType(VK_SHIFT,VK_I); break; case 'Y':doType(VK_SHIFT,VK_Y); break;
		case 'J':doType(VK_SHIFT,VK_J); break; case 'Z':doType(VK_SHIFT,VK_Z); break;
		default: throw new IllegalArgumentException("Cannot type charecter" + character);
	}
	}
}

