package com.callerid.popup;

import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.callerid.common.CIDRecord;

/** Setup program to configure Ethernet Link Devices. 
 * @author Grant
 * @author www.callerid.com
 * @version 0.9.2
 * @since 0.9.0
 */
public class ELPopup implements ActionListener{

	//static Scanner sc = new Scanner(System.in);
	public String version = "0.9.2";
	private MenuItem miShowMainWindow = new MenuItem("Show Main Window");
	private MenuItem miShowOptionWindow = new MenuItem("Options");
	private MenuItem miQuit = new MenuItem("Quit");
	private DisplayWindow displayWindow = new DisplayWindow();
	public OptionWindow optionWindow = new OptionWindow();
	
	//Options
	public int optPopupTimeout = 5000;
	public Boolean optPopupOutbound = false;
	public Boolean optPopupInbound = true;
	public Boolean optLogToFile = false;
	public Boolean trayAvaliable = false;
	public File optLogFile = new File("cid.log");
	public Preferences prefs = Preferences.userNodeForPackage(getClass());
	
	public static void main(String[] args) {
		ELPopup program = new ELPopup();
		program.runProgram();
	}

	public void runProgram() 
	{
		optionWindow.optionWindowPanel.program = this;
		displayWindow.displayWindowPanel.program = this;
		if (prefs.get("popupInbound", "true").equals("true")) optPopupInbound = true; else optPopupInbound = false;
		if (prefs.get("popupOutbound", "false").equals("true")) optPopupOutbound = true; else optPopupOutbound = false;
		if (prefs.get("logToFile", "false").equals("true")) optLogToFile = true; else optLogToFile = false;
		optLogFile = new File(prefs.get("logFile", "cid.log"));
		//Document doc = getDocument();
				
		optionWindow.optionWindowPanel.setSettings();
		
		
		//optionWindow.setVisible(true);
		CIDRecord cidItem = new CIDRecord("");
		//System Tray
		try
		{
			SystemTray tray = SystemTray.getSystemTray();
			java.net.URL imageURL = com.callerid.common.CIDFunctions.class.getResource("Phone.png");
			ImageIcon image = new ImageIcon(imageURL); 
				//Toolkit.getDefaultToolkit().getImage("/images/Phone.png");
			PopupMenu popup = new PopupMenu();
			miShowMainWindow.addActionListener(this);
			miShowOptionWindow.addActionListener(this);
			miQuit.addActionListener(this);
			popup.add(miShowMainWindow);
			popup.add(miShowOptionWindow);
			popup.add(miQuit);
			TrayIcon trayIcon = new TrayIcon(image.getImage(), "JPopup", popup);
			tray.add(trayIcon);
			trayAvaliable = true;
		} catch (java.lang.UnsupportedOperationException e){
			trayAvaliable = false;
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			trayAvaliable = false;
			System.err.println("Can't add to tray");
		}
		displayWindow.setVisible(true);
		displayWindow.Println("ELPopup Java version " + version);
	
		try {
			DatagramSocket socket = new DatagramSocket(3520);
			socket.setReuseAddress(true);
			byte[] buffer = new byte[1024];
			byte[] lastBuffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			//Program Loop
			while (true)
			{	
				buffer = new byte[1024];
				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				if (new String(buffer).equals(new String(lastBuffer))) continue; else lastBuffer = buffer.clone();
				cidItem.importCallRecord(new String(buffer));
				displayWindow.Println(cidItem.simpleResult);
				if (cidItem.line > 0)
				{
					if (cidItem.startRecord)
					{
						displayWindow.displayWindowPanel.tbDisplay[cidItem.line - 1].setMode(cidItem.detailType, cidItem.formatName());
					}else{
						displayWindow.displayWindowPanel.tbDisplay[cidItem.line - 1].setMode("N", cidItem.formatName());
					}
					if (!cidItem.detail)
					{
						if (cidItem.startRecord & cidItem.inbound & optPopupInbound)
						{
							popup(cidItem);
						}
						if (cidItem.startRecord & !cidItem.inbound & optPopupOutbound)
						{
							popup(cidItem);
						}					
					}
				}
				writeFile(cidItem.simpleResult);
			}
		} catch (SocketException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Another application is using port 3520. (Only one program can use this port at a time)\n" +  
					"This might be ELSetup, a third party application, or another copy of ELPopup.\n" +		
					" Please make sure any of these applications are closed before attempting to use ELPopup");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Writes the text to the log file in optLogFile
	 * @param text Text to be written to the log file.
	 */
	public void writeFile(String text)
	{
		if (optLogToFile & optLogFile.exists())
		{
			PrintWriter fOut;
			try {
				fOut = new PrintWriter(new BufferedWriter(new FileWriter(optLogFile, true)), true);
				fOut.println(text);
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates a popup on screen using the CIDRecord to determine location, text, and color.
	 * @param cidItem The CIDRecord you want the popup to display
	 */
	public void popup(CIDRecord cidItem)
	{
		PopupWindow pop = new PopupWindow(5000, cidItem);
		Thread tPop = new Thread(pop);
		tPop.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miQuit)
		{
			System.exit(0);
		}
		if (e.getSource() == miShowMainWindow)
		{
			displayWindow.setVisible(true);
		}
		if (e.getSource() == miShowOptionWindow)
		{
			optionWindow.setVisible(true);
		}
	}
}
