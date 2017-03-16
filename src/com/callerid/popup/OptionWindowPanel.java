package com.callerid.popup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.prefs.BackingStoreException;

import javax.swing.*;

public class OptionWindowPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JCheckBox chkInboundPopup = new JCheckBox("Popups on Inbound Calls");
	public JCheckBox chkOutboundPopup = new JCheckBox("Popups on Outbound Calls");
	public JCheckBox chkLog = new JCheckBox("Log to file");
	public JButton btnBrowse = new JButton("Browse");
	public JTextField tbLogPath = new JTextField("");
	public JButton btnSave = new JButton("Save/Apply");
	public JButton btnCancel = new JButton("Cancel");
	
	public ELPopup program;
	public OptionWindowPanel()
	{
		this.setLayout(new GridBagLayout());
		
		
		addItem(this, chkInboundPopup,  0, 0, 2, 1, 0);
		addItem(this, chkOutboundPopup, 0, 1, 2, 1, 0);
		addItem(this, chkLog,           0, 2, 1, 1, 0);
		addItem(this, btnBrowse,        1, 2, 1, 1, 0);
		addItem(this, tbLogPath,        0, 3, 2, 1, GridBagConstraints.HORIZONTAL);
		addItem(this, btnSave,          0, 4, 1, 1, 0);
		addItem(this, btnCancel,        1, 4, 1, 1, 0);

		
		chkLog.addActionListener(this);
		btnBrowse.addActionListener(this);
		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int fill)
	{
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.weightx = 100.0;
        gc.weighty = 100.0;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = GridBagConstraints.WEST;
        if (x > 0) gc.anchor = GridBagConstraints.EAST;
        gc.fill = fill;
        p.add(c, gc);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == btnBrowse)
		{
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new LogFileFilter());
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fc.showOpenDialog(this);
			System.out.println(fc.getSelectedFile());
			if (result == JFileChooser.APPROVE_OPTION) 
			{
				tbLogPath.setText(fc.getSelectedFile().getAbsolutePath());
				chkLog.setSelected(true);
			}
		}
		if (e.getSource() == btnCancel) {this.setSettings(); program.optionWindow.setVisible(false);}
		if (e.getSource() == btnSave) {this.applySettings(); program.optionWindow.setVisible(false);}
	}
	
	public void setSettings()
	{
		if (program.optPopupInbound) chkInboundPopup.setSelected(true); else chkInboundPopup.setSelected(false);
		if (program.optPopupOutbound) chkOutboundPopup.setSelected(true); else chkOutboundPopup.setSelected(false);
		if (program.optLogToFile) chkLog.setSelected(true); else chkLog.setSelected(false);
		this.tbLogPath.setText(program.optLogFile.getAbsolutePath());
	}
	
	public void applySettings()
	{
		if (chkInboundPopup.isSelected()) 
		{
			program.optPopupInbound = true;
			program.prefs.put("popupInbound", "true");
		} else {
			program.optPopupInbound = false;
			program.prefs.put("popupInbound", "false");
		}
		
		if (chkOutboundPopup.isSelected()) 
		{
			program.optPopupOutbound = true;
			program.prefs.put("popupOutbound", "true");
		}else {
			program.optPopupOutbound = false;
			program.prefs.put("popupOutbound", "false");
		}
		
		
		if (this.chkLog.isSelected())
		{
			program.optLogFile = new java.io.File(this.tbLogPath.getText());

			try {
				program.optLogFile.createNewFile();
				chkLog.setSelected(true);
				program.optLogToFile = true;
				program.prefs.put("logFile", program.optLogFile.getAbsolutePath());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			program.optLogToFile = true;
			program.prefs.put("logToFile", "true");
		} else {
			program.optLogToFile = false;
			program.prefs.put("logToFile", "false");
		}
		
		try {
			program.prefs.flush();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Could not save preferences");
		}
	}
}
