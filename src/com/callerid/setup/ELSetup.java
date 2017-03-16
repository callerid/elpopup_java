/**
 * 
 */
package com.callerid.setup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

import javax.swing.*;

import com.callerid.common.*;
/** Setup program to configure Ethernet Link Devices. 
 * @author Grant
 * @author CallerID.com
 * @version 0.9.1
 * @since 0.9.0
 */
public class ELSetup implements ActionListener {

	public String version = "0.9.1";
	private JFrame mainWindow = new JFrame();
	private MainWindowPanel mainWindowPanel = new MainWindowPanel();
	
	public Timer tRefresh = new Timer(50, this);
	public boolean bRefresh = false;
	public int iRefreshMode = 0;
	public EthernetLink mainElink = new EthernetLink("");
	/**
	 * A list of commands to send to the Ethernet Link. One command is sent every time the UDP receiver times out (50 times per second).
	 */
	public LinkedList<String> sendQueue = new LinkedList<String>();
	
	public static void main(String[] args) {
		ELSetup program = new ELSetup();
		program.run();
	}
	public void run()
	{
		mainWindow.setTitle("EL Setup");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setSize(460,520);
		mainWindow.add(mainWindowPanel);
		mainWindowPanel.program = this;
		mainWindowPanel.printLn("ELSetup - Java, version: " + version);
		mainWindow.setResizable(false);
		mainWindowPanel.btnRefresh.addActionListener(this);
		mainWindow.setVisible(true);
		//getData();
		tRefresh.start();
		try {
			DatagramSocket socket = new DatagramSocket(3520);
			socket.setReuseAddress(true);
			socket.setSoTimeout(20);
			byte[] buffer = new byte[128];
			byte[] lastBuffer = new byte[128]; 
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			String inboundData;
			char cNull = (char)0;
			//Program Loop
			sendQueue.add("^^IdX");
			while (true)
			{	
				try
				{
					buffer = new byte[128];
					packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					if (buffer.equals(lastBuffer)) 
					{
						mainWindowPanel.printLn("Repeat"); 
						continue;
					} else {
						lastBuffer = buffer.clone();
						inboundData = new String(buffer, "ISO-8859-1");
					
						if (inboundData.length() > 21)
						{
							if (inboundData.substring(21, 24).equals("<M>"))
							{
								mainElink.importData(inboundData);
								bRefresh = true;
								iRefreshMode = 0;
							} else {
								inboundData = inboundData.replace(cNull, ' ').trim();
								if (inboundData.length() > 21)mainWindowPanel.printLn(inboundData.substring(21));
							}
						}
					}
				} catch (java.net.SocketTimeoutException e) {
					//check to see if anything needs to be sent
					if (!sendQueue.isEmpty())
					{
						byte[] outgoingData = sendQueue.poll().getBytes();
						socket.send(new DatagramPacket(outgoingData, outgoingData.length, InetAddress.getByName("255.255.255.255"), 3520));
					}
				}
				
			}
		} catch (SocketException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Another application is using port 3520. (Only one program can use this port at a time)\n" +  
					"This might be ELPopup, a third party application, or another copy of ELSetup.\n" +		
					" Please make sure any of these applications are closed before attempting to use ELPopup");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainWindowPanel.btnRefresh)
		{
			//getData();
			sendQueue.add("^^IdX");
			//bRefresh = true;
			//iRefreshMode = 0;
		}
		if (e.getSource() == this.tRefresh)
		{
			refresh();
		}
	}
	/**
	 * After all the items on the main window are set to "0" (or their various format's equivalent to "0"), this will step
	 * though each item and set it to the most recent EthernetLink item. 
	 * 
	 * When all the fields are updated instantly, it's hard to tell if it was updated at all. When all the fields fill in one by one, the user
	 * can tell that they are getting new values from scratch, and nothing is being cached.
	 */
	public void refresh()
	{
		if (this.bRefresh)
		{
			switch (iRefreshMode)
			{
			case 0:
				mainWindowPanel.blankText();
				break;
			case 1:
				mainWindowPanel.tbSerial.setText(mainElink.serial);
				break;
			case 2:
				mainWindowPanel.tbUnit.setText(mainElink.unitID);
				break;
			case 3:
				mainWindowPanel.tbIP.setText(mainElink.IP);
				break;
			case 4:
				mainWindowPanel.tbMAC.setText(mainElink.MAC);
				break;
			case 5:
				mainWindowPanel.tbDestIP.setText(mainElink.destinationIP);
				break;
			case 6:
				mainWindowPanel.tbDestMAC.setText(mainElink.destinationMAC);
				break;
			case 7:
				mainWindowPanel.tbDestPort.setText(String.valueOf(mainElink.destinationPort));
				break;			
			case 8:
				bRefresh = false;
				break;
			}
			iRefreshMode++;
		}
	}
	/**
	 * Adds an outgoing command to the send queue. To be sent next time the UDP receiver times out (within 1/10th of a second).
	 * @param param The command text (probably as a hexadecimal string) to send to the Ethernet Link.
	 * @param mode The command to send. '-' relays commands to the underlying Whozz Calling? unit, and other letters talk to the Ethernet Link 
	 * directly.
	 */
	public void sendParam(String param, char mode)
	{
		sendQueue.add("^^Id" + mode + param);
	}
}
