package com.callerid.common;
import java.util.regex.*;

/** Represents one Ethernet Link Device. 
 * @author Grant
 * @author www.callerid.com
 * @version 0.9.1
 * @since 0.9.0
 */

public class EthernetLink {
	public String serial;
	public String unitID;
	public String destinationIP;
	public String IP;
	public String destinationMAC = "";
	public String MAC = "";
	public int port = 0;
	public int destinationPort = 0;
	
	public EthernetLink(String initString){importData(initString);}
	/**
	 * 
	 * @param initString The parameter string from an Ethernet Link device, the result of sending a ^^IdX string.
	 */
	public void importData(String initString)
	{
		String uidData;
		String snData;
		Matcher elinkMatch = Pattern.compile("\\^\\^<U>").matcher(initString);
		if (elinkMatch.find())
		{
			uidData = initString.substring(elinkMatch.start() + 5, elinkMatch.start() + 11);
			unitID = CIDFunctions.UID_Decoder(uidData);
		}
		
		elinkMatch = Pattern.compile("<S>").matcher(initString);
		if (elinkMatch.find())
		{
			snData = initString.substring(elinkMatch.start() + 3, elinkMatch.start() + 9);
			serial = CIDFunctions.UID_Decoder(snData);
		}
		
		elinkMatch = Pattern.compile("<M>").matcher(initString);
		if (elinkMatch.find())
		{
			MAC = "";
			String tmpMac;
			tmpMac = CIDFunctions.UID_Decoder(initString.substring(elinkMatch.start() + 3, elinkMatch.start() + 9));
			tmpMac = tmpMac.toUpperCase();
			for (int i = 0; i < 12; i=i+2)
			{
				MAC = MAC + tmpMac.substring(i, i + 2);
				if (i != 10) MAC = MAC + "-";
			}
		}
		
		elinkMatch = Pattern.compile("<C>").matcher(initString);
		if (elinkMatch.find())
		{
			destinationMAC = "";
			String tmpMac;
			tmpMac = CIDFunctions.UID_Decoder(initString.substring(elinkMatch.start() + 3, elinkMatch.start() + 9));
			tmpMac = tmpMac.toUpperCase();
			for (int i = 0; i < 12; i=i+2)
			{
				destinationMAC = destinationMAC + tmpMac.substring(i, i + 2);
				if (i != 10) destinationMAC = destinationMAC + "-";
			}
		}

		elinkMatch = Pattern.compile("<I>").matcher(initString);
		if (elinkMatch.find())
		{
			String tmpIP;
			tmpIP = CIDFunctions.UID_Decoder(initString.substring(elinkMatch.start() + 3, elinkMatch.start() + 7));
			IP = CIDFunctions.ipFromHex(tmpIP);
		}
		
		elinkMatch = Pattern.compile("<D>").matcher(initString);
		if (elinkMatch.find())
		{
			String tmpIP;
			tmpIP = CIDFunctions.UID_Decoder(initString.substring(elinkMatch.start() + 3, elinkMatch.start() + 7));
			destinationIP = CIDFunctions.ipFromHex(tmpIP);
		}
		
		elinkMatch = Pattern.compile("<P>").matcher(initString);
		if (elinkMatch.find())
		{
			String tmpPort;
			tmpPort = CIDFunctions.UID_Decoder(initString.substring(elinkMatch.start() + 3, elinkMatch.start() + 5));
			port = Integer.parseInt(tmpPort, 16);
		}
		
		elinkMatch = Pattern.compile("<T>").matcher(initString);
		if (elinkMatch.find())
		{
			String tmpPort;
			tmpPort = CIDFunctions.UID_Decoder(initString.substring(elinkMatch.start() + 3, elinkMatch.start() + 5));
			destinationPort = Integer.parseInt(tmpPort, 16);
		}
	}
	

}
