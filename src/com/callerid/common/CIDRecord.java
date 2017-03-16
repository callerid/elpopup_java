/**
 * 
 */
package com.callerid.common;
import java.util.regex.*;

/** Represents one Caller ID event. May be an incoming call, or someone picking up the phone. 
 * @author Grant
 * @author www.callerid.com
 * @version 0.9.1
 * @since 0.9.0
 */
public class CIDRecord {
	public String name = "";
	public String phone = "";
	public int duration;
	public int line;
	public Boolean startRecord = true;
	public Boolean checksum;
	public Boolean inbound;
	//public time? callTime;
	//TODO: Find a call time type
	public Boolean detail;
	public String detailType;
	public int rings;
	public String ringType;
	public String unitID;
	public String serialNumber;
	
	/** The raw input string minus the unprintable serial and unit numbers
	 */
	public String simpleResult;
	
	/** Creates a new CallerID Record using the entire network packet received from an Ethernet Link device
	 * @param rawData The raw data coming from an Ethernet Link device
	 */
	
	public CIDRecord(String rawData)
	{
		importCallRecord(rawData);
	}
	/**
	 * Imports a raw Ethernet Link packet string to an existing Caller ID record. 
	 * importCallRecord will attempt to fill out as many parameters that it can.
	 * @param rstCallRecord The raw data coming from an Ethernet Link device
	 */
	public void importCallRecord(String rstCallRecord)
	{
		
		String rstSubSet;
		//String stCallRecord;
		Pattern cidPattern = Pattern.compile("\\^\\^<U>");
		Matcher cidMatch = cidPattern.matcher(rstCallRecord);
		
		if (cidMatch.find() & (rstCallRecord.length() > 20))
		{
			rstSubSet = rstCallRecord.substring(5,11);
			this.unitID = CIDFunctions.UID_Decoder(rstSubSet);
			rstSubSet = rstCallRecord.substring(14,20);
			this.serialNumber = CIDFunctions.UID_Decoder(rstSubSet);
		}
		//ix = rstCallRecord.indexOf('$');
		cidPattern = Pattern.compile("(\\d\\d) ([IO]) ([ES]) (\\d{4}) ([GB]) (.)(\\d) (\\d\\d/\\d\\d \\d\\d:\\d\\d [AP]M) (.{8,15})(.{0,15})");
		cidMatch = cidPattern.matcher(rstCallRecord);
		if (cidMatch.find()) //Full Record
		{
			simpleResult = cidMatch.group(0);
			System.out.println( "MATCH: " + cidMatch.group());
			detail = false;
			line = Integer.valueOf(cidMatch.group(1));
			if (cidMatch.group(2).equals("I")) inbound = true; else inbound = false;
			if (cidMatch.group(3).equals("S")) startRecord = true; else startRecord = false;
			detailType = cidMatch.group(2);
			duration = Integer.valueOf(cidMatch.group(4));
			if (cidMatch.group(5).equals("G")) checksum = true; else checksum = false;
			ringType = cidMatch.group(6);
			rings = Integer.valueOf(cidMatch.group(7));
			//TODO: Add Call Time for Full record
			phone = cidMatch.group(9).trim();
			name = cidMatch.group(10).trim();
		}
		cidPattern = Pattern.compile("(\\d\\d) ([NFR]) {13}(\\d\\d/\\d\\d \\d\\d:\\d\\d:\\d\\d)");
		cidMatch = cidPattern.matcher(rstCallRecord);
		if (cidMatch.find())
		{
			simpleResult = cidMatch.group(0);
			System.out.println("S MATCH: " + cidMatch.group());
			detail = true;
			detailType = cidMatch.group(2);
			line = Integer.valueOf(cidMatch.group(1));
			//TODO: Add call time for small record
		}		
	}
	/**
	 * Returns a phone number and name in a format that's a bit nicer to read.
	 * When the phone number and name are the same (Out-Of-Area, Private...) then it just returns the one item, instead of duplicating it.
	 * @return Phone number and Name as a string.
	 */
	public String formatName()
	{
		String fName = "";
		if (this.name.equals(this.phone))
		{
			fName = this.phone;
		}
		else
		{
			fName = this.phone;
			if (!this.name.trim().equals("")) fName = fName + ": " + this.name;
		}
		return fName;
	}
}
