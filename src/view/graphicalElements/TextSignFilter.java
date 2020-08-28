package view.graphicalElements;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;


/**
 * This Filter filters in realtime given signs from textfield 
 */
public class TextSignFilter extends DocumentFilter {


	String pattern;    



	/**
	 * Creates a new Filter with given regular expression pattern 
	 * @param pattern Regular Expression pattern
	 */
	public TextSignFilter(String pattern)
	{
		this.pattern = pattern;
	}



	/* (non-Javadoc)
	 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.DocumentFilter.FilterBypass, int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	public void insertString(DocumentFilter.FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException 
	{ 
		replace(fb, offset, 0, str, attr); 
	}    


	/* (non-Javadoc)
	 * @see javax.swing.text.DocumentFilter#replace(javax.swing.text.DocumentFilter.FilterBypass, int, int, java.lang.String, javax.swing.text.AttributeSet)
	 */
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException 
	{
		String textInDocument = fb.getDocument().getText(0, fb.getDocument().getLength());
		String testString = textInDocument + str;

		if (testString.matches(pattern)) 
		{            
			fb.replace(offset, length, str, attrs);
		}
		else 
		{
			throw new BadLocationException("New characters exceeds max size of document", offset);
		}
	}
}