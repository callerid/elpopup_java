package com.callerid.setup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.callerid.common.CIDFunctions;

public class MainWindowPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public JButton btnTest = new JButton("Test");
	private JLabel lblSerial = new JLabel("Serial Number");
	private JLabel lblUnit = new JLabel("Unit ID");
	private JLabel lblIP = new JLabel("IP Address");
	private JLabel lblMAC = new JLabel("MAC Address");
	//private JLabel lblPort = new JLabel("Port Number");
	private JLabel lblDestIP = new JLabel("Destination IP");
	private JLabel lblDestMAC = new JLabel("Destination MAC");
	private JLabel lblDestPort = new JLabel("Port");
	private JLabel lblCommand = new JLabel("Command");
	
	public JTextField tbSerial = new JTextField("000000");
	public JTextField tbUnit = new JTextField("000000");
	public JTextField tbIP = new JTextField("000000");
	public JTextField tbMAC = new JTextField("000000");
	public JTextField tbPort = new JTextField("000000");
	public JTextField tbDestIP = new JTextField("000000");
	public JTextField tbDestMAC = new JTextField("000000");
	public JTextField tbDestPort = new JTextField("000000");
	public JTextField tbCommand = new JTextField();
	
	//private JButton btnSerial = new JButton("Change");
	private JButton btnUnit = new JButton("Change");
	private JButton btnIP = new JButton("Change");
	private JButton btnMAC = new JButton("Change");
	private JButton btnPort = new JButton("Change");
	private JButton btnDestIP = new JButton("Change");
	private JButton btnDestMAC = new JButton("Change");
	private JButton btnDestPort = new JButton("Change");
	private JButton btnCommand = new JButton("Send");
	public ELSetup program;
	public JTextArea tbArchive = new JTextArea(10, 30);

	
	public JButton btnRefresh = new JButton("Refresh");
	public MainWindowPanel()
	{
		JScrollPane scroll = new JScrollPane(tbArchive, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 50.0;
        gc.weighty = 1.0;
        gc.gridheight = 1;
        gc.gridwidth = 1;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.NONE;
        
		this.setLayout(new GridBagLayout());
		gc.gridx = 0; gc.gridy = 1;
		this.add(lblSerial, gc);
		gc.gridx = 0; gc.gridy = 2;
		this.add(lblUnit, gc);
		gc.gridx = 0; gc.gridy = 3;
		this.add(lblIP, gc);
		gc.gridx = 0; gc.gridy = 4;
		this.add(lblMAC, gc);
		gc.gridx = 0; gc.gridy = 5;
		//this.add(lblPort, gc);
		this.add(lblDestPort, gc);
		gc.gridx = 0; gc.gridy = 6;
		this.add(lblDestIP, gc);
		gc.gridx = 0; gc.gridy = 7;
		this.add(lblDestMAC, gc);
		gc.gridx = 0; gc.gridy = 8;
		this.add(lblCommand, gc);
		gc.gridx = 0; gc.gridy = 9;
		gc.weighty = 60.0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 3;
		this.add(scroll, gc);
		
		
		gc.gridwidth = 1;
		gc.weightx = 150.0;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 1; gc.gridy = 1;
		this.add(tbSerial, gc);
		gc.gridx = 1; gc.gridy = 2;
		this.add(tbUnit, gc);
		gc.gridx = 1; gc.gridy = 3;
		this.add(tbIP, gc);
		gc.gridx = 1; gc.gridy = 4;
		this.add(tbMAC, gc);
		gc.gridx = 1; gc.gridy = 5;
		//this.add(tbPort, gc);
		this.add(tbDestPort, gc);
		gc.gridx = 1; gc.gridy = 6;
		this.add(tbDestIP, gc);
		gc.gridx = 1; gc.gridy = 7;
		this.add(tbDestMAC, gc);
		gc.gridx = 1; gc.gridy = 8;
		this.add(tbCommand, gc);
		
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 0; gc.gridy = 9; gc.gridwidth = 3;
		gc.weightx = 100.0;
		gc.weighty = 100;
		//this.add(this.btnTest, gc);
		gc.weighty = 1; gc.gridwidth = 1;
		
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2; gc.gridy = 0;

		gc.gridx = 2; gc.gridy = 1;
		this.add(btnRefresh, gc);
		//this.add(btnSerial, gc);
		gc.gridx = 2; gc.gridy = 2;
		this.add(btnUnit, gc);
		gc.gridx = 2; gc.gridy = 3;
		this.add(btnIP, gc);
		gc.gridx = 2; gc.gridy = 4;
		this.add(btnMAC, gc);
		gc.gridx = 2; gc.gridy = 5;
		this.add(btnDestPort, gc);
		//this.add(btnPort, gc);
		gc.gridx = 2; gc.gridy = 6;
		this.add(btnDestIP, gc);
		gc.gridx = 2; gc.gridy = 7;
		this.add(btnDestMAC, gc);
		gc.gridx = 2; gc.gridy = 8; 
		this.add(btnCommand, gc);
		
		btnTest.addActionListener(this);
		btnUnit.addActionListener(this);
		//btnSerial.addActionListener(this);
		btnIP.addActionListener(this);
		btnMAC.addActionListener(this);
		btnPort.addActionListener(this);
		btnDestIP.addActionListener(this);
		btnDestMAC.addActionListener(this);
		btnDestPort.addActionListener(this);
		btnCommand.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Change Buttons
		if (e.getSource() == btnUnit) program.sendParam(tbUnit.getText().toUpperCase(), 'U');
		//if (e.getSource() == btnSerial) System.out.println("^^IdS" + tbSerial.getText().toUpperCase());
		if (e.getSource() == btnDestIP)   program.sendParam(CIDFunctions.hexFromIP(tbDestIP.getText()).toUpperCase(), 'D');
		if (e.getSource() == btnDestMAC)  program.sendParam(tbDestMAC.getText().replace("-", "").toUpperCase(), 'C');
		if (e.getSource() == btnDestPort)
		{
			String hexPort = Integer.toHexString(Integer.parseInt(tbDestPort.getText())).toUpperCase();
			while (hexPort.length() < 4) hexPort = "0" + hexPort;
			program.sendParam(hexPort, 'T');
		}
		if (e.getSource() == btnIP)   program.sendParam(CIDFunctions.hexFromIP(tbIP.getText()).toUpperCase(), 'I');
		if (e.getSource() == btnMAC)  program.sendParam(tbMAC.getText().replace("-", "").toUpperCase(), 'M');
		/*if (e.getSource() == btnPort) 
		{
			String hexPort = Integer.toHexString(Integer.parseInt(tbPort.getText())).toUpperCase();
			while (hexPort.length() < 4) hexPort = "0" + hexPort;
			System.out.println("^^IdG" + hexPort);
		}*/
		if (e.getSource() == btnCommand)
		{
			program.sendParam(tbCommand.getText(), '-');
		}
	}
	
	/**
	 * Displays the text in the large text field, followed by a newline
	 * @param text String to be displayed
	 */
	public void printLn(String text)
	{
		this.tbArchive.setText(tbArchive.getText() + text + "\n");
	}
	/**
	 * Blanks out all the fields in preparation for getting new data.
	 */
	public void blankText()
	{
		this.tbDestIP.setText("0.0.0.0");
		this.tbDestMAC.setText("00-00-00-00-00-00");
		this.tbDestPort.setText("0");
		this.tbIP.setText("0.0.0.0");
		this.tbMAC.setText("00-00-00-00-00-00");
		this.tbPort.setText("0");
		this.tbSerial.setText("000000000000");
		this.tbUnit.setText("000000000000");
	}
}
