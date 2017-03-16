package com.callerid.common;
import java.awt.Color;
import java.io.IOException;
import java.net.*;

/** Useful functions to deal with common tasks regarding Ethernet Link devices. 
 * @author Grant
 * @author www.callerid.com
 * @version 0.9.1
 * @since 0.9.0
 */

public class CIDFunctions {
	public static final Color BEIGE = new Color(245,245,220);
	public static final Color MISTYROSE = new Color( 255,228,225 );
	public static final Color PALETURQUOISE = new Color	(175, 238, 238);
	/**
	 * Decodes a compressed ASCII string
	 * @param unitID the unitID/serial number in it's compressed ASCII format straight from the network packet
	 * @return A string with the hexadecimal codes from the ASCII compression
	 */
	public static String UID_Decoder(String unitID)
	{
		String sDecoded = "";
		String tmpDecoded = "";
		char caDecoded[];
		int nNumeric;
		caDecoded = unitID.toCharArray();
		for (int i = 0;i<caDecoded.length;i++)
		{
			nNumeric = (int) caDecoded[i];
			tmpDecoded = Integer.toHexString(nNumeric);//Need to pad this.
			if (tmpDecoded.length() == 1) tmpDecoded = "0" + tmpDecoded;
			sDecoded = sDecoded + tmpDecoded;
		}
		return sDecoded;
	}
	/** Starts a UDP listening socket and returns the next thing it gets on port 3520. Blocks forever.
	 * 
	 * @return The next UDP packet received on port 3520
	 */
	public static String udpReceiver()
	{
		try 
		{
			DatagramSocket socket = new DatagramSocket(3520);
			socket.setReuseAddress(true);
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			socket.close();
			return new String(buffer);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return "";
	}
	
	/** Returns in IP address string from a 8 digit hex string. C0A8005A > 192.168.0.90
	 * 
	 * @param hex 8 digit hexadecimal string.
	 * @return xxx.xxx.xxx.xxx style IP address. ie. "192.168.1.1"
	 */
	public static String ipFromHex(String hex)
	{
		String sSubHex;
		String sIP = "";
		while (hex.length() > 1)
		{
			sSubHex = hex.substring(0, 2);
			hex = hex.substring(2);
			sIP = sIP + Integer.parseInt(sSubHex, 16);
			if (hex.length() > 0) sIP = sIP + ".";
		}
		return sIP;
	}
	/** Converts a decimal IP address to hexadecimal IP address, 192.168.0.90 > C0A8005A 
	 * 
	 * @param ip period delimited IP address string.
	 * @return 8 digit hexadecimal string.
	 */
	public static String hexFromIP(String ip)
	{
		String[] subIP = ip.split("\\.");
		String hex = "";
		int ix = 0;
		while (ix < subIP.length)
		{
			String subHex;
			subHex = Integer.toHexString(Integer.parseInt(subIP[ix]));
			if (subHex.length() < 2) subHex = "0" + subHex;
			hex = hex + subHex;
			
			ix++;
		}
		return hex;
	}
	

}
