package com.callerid.popup;

import javax.swing.*;

public class DisplayWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	public DisplayWindowPanel displayWindowPanel = new DisplayWindowPanel();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DisplayWindow();
	}
	public DisplayWindow()
	{
		this.setSize(300, 400);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setTitle("ELPopup");
		
		this.add(displayWindowPanel);
	}
	
	public void Println(String text)
	{
		displayWindowPanel.tbArchive.setText(text + "\n" + displayWindowPanel.tbArchive.getText());
	}

}
