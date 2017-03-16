package com.callerid.popup;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;

import javax.swing.*;

public class DisplayWindowPanel extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CIDTextField[] tbDisplay = new CIDTextField[4];
	public JTextArea tbArchive = new JTextArea(20, 30);
	public JButton tButton = new JButton("Quit");
	public JButton btnOption = new JButton("Options");
	public ELPopup program;
	public DisplayWindowPanel()
	{
		this.setLayout(new GridBagLayout());
		tbArchive.setFont(new Font(java.awt.Font.MONOSPACED, Font.PLAIN, 8));
		//tbArchive.setColumns(50);
		//tbArchive.setRows(15);
		tbArchive.setEditable(false);
		tbArchive.setLineWrap(false);
		JScrollPane scroll = new JScrollPane(tbArchive, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		tbDisplay[0] = new CIDTextField("");
		tbDisplay[1] = new CIDTextField("");
		tbDisplay[2] = new CIDTextField("");
		tbDisplay[3] = new CIDTextField("");
		//if (program.trayAvaliable){ //System tray need to be figured out before this is initialized
		//	addItem(this, tButton, 0,0,1,1, GridBagConstraints.HORIZONTAL, 1.0);
		//	addItem(this, btnOption, 1,0,1,1, GridBagConstraints.HORIZONTAL, 1.0);
		//}
		
		addItem(this, tbDisplay[0], 0, 1, 2, 1, GridBagConstraints.HORIZONTAL, 1.0);
		addItem(this, tbDisplay[1], 0, 2, 2, 1, GridBagConstraints.HORIZONTAL, 1.0);
		addItem(this, tbDisplay[2], 0, 3, 2, 1, GridBagConstraints.HORIZONTAL, 1.0);
		addItem(this, tbDisplay[3], 0, 4, 2, 1, GridBagConstraints.HORIZONTAL, 1.0);
		addItem(this, scroll,       0, 5, 2, 1, GridBagConstraints.BOTH, 150.0);
		//addItem(this, tButton,      0, 5, 1, 1, 0, 1.0);

		tButton.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
	private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int fill, double yWeight)
	{
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.weightx = 100.0;
        gc.weighty = yWeight;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = fill;
        p.add(c, gc);

	}
	
}
