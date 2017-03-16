package com.callerid.popup;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class LogFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if (f.isDirectory()) return true;
		if (f.getName().matches(".*\\.log")) return true;
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Log Files (*.log)";
	}

}
