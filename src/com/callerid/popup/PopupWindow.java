package com.callerid.popup;


import java.awt.Color;

import javax.swing.JWindow;

import com.callerid.common.CIDFunctions;
import com.callerid.common.CIDRecord;

public class PopupWindow extends JWindow implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int duration ; 
	private String splashText;
	private int position;
	private Color bgColor = CIDFunctions.BEIGE;
	
	public PopupWindow(int d, CIDRecord cidItem) { 
		duration = d ; 
		splashText = cidItem.formatName();
		position = cidItem.line;
		if (cidItem.inbound) bgColor = CIDFunctions.BEIGE; else bgColor = CIDFunctions.PALETURQUOISE;
	} 
	public void run()
	{
		showSplashAndExit();
	}
	void ShowSplash(String text) { 
		
		javax.swing.JPanel content = (javax.swing.JPanel)getContentPane() ; 	
		content.setBackground(bgColor);
		
		// Set the window's bounds & center the window 
		int width = 330 ; 
		int height = 40 ; 
		//java.awt.Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize() ; 
		//int x = (screen.width-width) / 2 ; 
		//int y = (screen.height - height) / 2 ;
		int x = 0;
		int y = height * (position - 1) ;
		setBounds(x,y,width,height) ; 
		
		javax.swing.JLabel myLabel   = new javax.swing.JLabel(text, javax.swing.JLabel.LEFT) ; 
		myLabel.setFont(new java.awt.Font("Courier" , java.awt.Font.BOLD,16)) ; 
		
		content.add(myLabel, java.awt.BorderLayout.CENTER ) ; 
		content.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(3,30,105,40),2) ); 
	
		setVisible(true) ;    // Display it 
		this.setAlwaysOnTop(true);
		this.setFocusable(false);
		try { 
			Thread.sleep(duration) ;    // Wait a little while 
		}
		catch (Exception e) {}         // ignore any errors
	} 
	
	void showSplashAndExit() { 
		ShowSplash(splashText) ; 
		this.dispose();
	} 
}