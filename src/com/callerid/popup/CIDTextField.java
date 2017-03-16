package com.callerid.popup;

import javax.swing.JTextField;

import java.awt.Color;
/** Custom text field that changes colors depending on line status (Outgoing call, incoming call, call ended...) 
 */
public class CIDTextField extends JTextField {

	/**
	 * Common color values and text strings.	
	 */
	public static final Color BEIGE = new Color(245,245,220);
	public static final Color MISTYROSE = new Color( 255,228,225 );
	public static final Color PALETURQUOISE = new Color	(175, 238, 238);
	public static final Color WHITE = new Color(255,255,255);
	
	public static final String ST_INCOMING = "Incoming Call...";
	public static final String ST_OUTGOING = "Outgoing Call...";
	
		
	private static final long serialVersionUID = 1L;
	
	private char mode;
	
	/**
	 * Sets the size and initial string. Most likely "".
	 * @param text Initial display string. 
	 */
	public CIDTextField(String text)
	{
		this.setText(text);
		this.setColumns(22);
	}
	/** Single character (as a string) to set the background color, and text to set the text for the text box.
	 * 
	 * @param text Single character event code to set the color<br>
	 * 				N = On hook, call ended, textbox goes white<Br>
	 * 				R = Ringing, incoming call, textbox goes pink(ish)<br>
	 * 				F = Off hook, call is picke up, textbox goes blue or beige depending on if the phone was just ringing.<br>
	 * 				O = Outgoing call, textbox goes blue<br>
	 * 				I = Incoming call, textboes goes beige<br>
	 * @param displayText The text to be displayed. This parameter isn't always used depending on the event code. For example, when the phone is
	 * ringing, it will just say "Incoming call" or whatever is in ST_INCOMING.
	 */
	public void setMode(String text, String displayText)
	{
		if (text.equals("O"))
		{
			mode = 'o';
			color(PALETURQUOISE);
			this.setText(displayText);
		}
		if (text.equals("I"))
		{
			mode = 'i';
			color(BEIGE);
			this.setText(displayText);
		}
		if (text.equals("N") | text.equals("E"))
		{
			if (this.getText().equals(ST_OUTGOING)) this.setText("");
			if (this.getText().equals(ST_INCOMING)) this.setText("");
			color(WHITE);
			mode = 'x';
		}
		if (text.equals("F"))
		{
			if (mode == 'r' | mode == 'i')
			{
				mode = 'i';
				color(BEIGE);
			} else {
				mode = 'o';
				color(PALETURQUOISE);
				this.setText(ST_OUTGOING);
			}
		}
		if (text.equals("R"))
		{
			mode = 'r';
			color(MISTYROSE);
			this.setText(ST_INCOMING);
		}
	}
	
	private void color(Color bgColor)
	{
		this.setBackground(bgColor);
		if (bgColor == WHITE)
		{
			this.setForeground(java.awt.Color.gray);
		} else {
			this.setForeground(java.awt.Color.black);
		}
	}
}
