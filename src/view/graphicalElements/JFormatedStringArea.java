package view.graphicalElements;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class JFormatedStringArea extends JTextArea implements KeyListener {
	
	int maxLength;
	
	
	public JFormatedStringArea (int maxLength){
		super();
		this.maxLength = maxLength;
		
		/* add listener */
		this.addKeyListener(this);
	}

	
//	private boolean isCharProhibited(char c) {
//		return c == '/' || c == '\\' || c == ':' || c == '*' || c == '?' || c == '\"' ||
//			   c == '<' || c == '>' || c == '|';
//	}
	private boolean isCharProhibited(char c) {
		return c == '?' || c == '<' || c == '>' || c == '|' || c == '\\';
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			e.consume();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {
		if(isCharProhibited(e.getKeyChar()) || getText().length() >= maxLength) {
			e.consume();
		}
	}

}
