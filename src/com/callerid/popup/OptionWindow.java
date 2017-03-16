package com.callerid.popup;

import javax.swing.*;

public class OptionWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OptionWindowPanel optionWindowPanel = new OptionWindowPanel();

	public OptionWindow()
	{
		this.setSize(217,230);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("ELPopup Options");
		
		this.add(optionWindowPanel);
	}

}
