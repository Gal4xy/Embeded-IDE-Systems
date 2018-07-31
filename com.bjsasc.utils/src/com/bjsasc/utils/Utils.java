/*==========================================================================
 * 
 * Utils.java
 * 
 * $Author: uwe_ewald $
 * $Revision: 1.5 $
 * $Date: 2011/05/30 20:04:37 $
 * $Name:  $
 * 
 * Created on 29-Dec-2003
 * Created by Marcel Palko alias Randallco (randallco@users.sourceforge.net)
 *==========================================================================*/
package com.bjsasc.utils;

public class Utils {
	/**
	 * Static array of all one-byte hex numbers (00...FF)
	 */
	public static boolean DEBUG = true;
	private static final String[] hexValues = new String[256];
	
	static {
		//
		// Fill in the array of hex strings
		//
		for (int i = 0; i < 256; i++) {
			String s = Integer.toHexString(i).toUpperCase();
			hexValues[i] = (s.length() < 2) ? ("0" + s) : s;
		} // for
	} // static

	/**
	 * Converts single byte to string
	 * @param b byte to convert
	 * @return string 2-digit hex string
	 */
	public static String byte2string(byte b) {
		return hexValues[((int) b) & 0xff];
	}
	public static byte[] long2byte(long value){
		return new byte[]{(byte) (value>>24),(byte) (value>>16),(byte) (value>>8),(byte) value};
	}
	
	/**
	 * Converts hex string to single byte
	 * @param s hex string to convert
	 * @return byte
	 */
	public static byte string2byte(String s) {
		if (s == null || s.length() == 0)
			return 0;
		try {
			byte b1 = Byte.parseByte("" + s.charAt(0), 16);
			if (s.length() < 2)
				return b1;
			byte b2 = Byte.parseByte("" + s.charAt(1), 16);
			return (byte) (((b1 & 0x0f) << 4) | b2);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Converts hex string to byte array
	 * @param s hex string to convert
	 * @return byte array
	 */
	public static byte[] string2bytes(String s) {
		if (s == null)
			return new byte[0];
		byte[] result = new byte[s.length() / 2];
		for (int i = 0, j = 0, k = 0, n = s.length(); i < result.length && j < n; i++, j += 2) {
			k = j + 2 < n ? j + 2 : n;
			result[i] = string2byte(s.substring(j, k));
		}
		return result;
	}

	/**
	 * Converts integer to string
	 * @param n Number to convert to String
	 * @return Zero-padded number converted to String
	 */
	public static String int2string(int n) {
		String nString = Integer.toHexString(n).toUpperCase();
		return "00000000".substring(nString.length()) + nString;
	}

	/**
	 * Converts the specified string of hex characters to a character string.
	 * @param s the hex characters
	 * @return the string
	 */
	public static String hexToChars(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i += 2) {
			sb.append((char) Utils.string2byte(s.substring(i)));
		}
		return sb.toString();
	}
	
	/**
	 * Converts the specified string of characters to a hex character string.
	 * @param s the characters
	 * @return the hex string
	 */
	public static String charsToHex(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			sb.append(Utils.byte2string((byte) c));
		}
		return sb.toString();
	}
	
	/**
	 * Returns true if the given string consists of at most 2 characters which represent a hex number.
	 * @param str the string
	 * @return true if valid
	 */
	public static boolean isValidHexNumber(String str) {
		return str != null && str.length() <= 2
				&& (str.length() > 0 ? Character.digit(str.charAt(0), 16) != -1 : true)
				&& (str.length() > 1 ? Character.digit(str.charAt(1), 16) != -1 : true);
	}

	public static boolean isValidHexString(String str, boolean allowEmptyString) {
		try {
			if (str != null && str.length() > 0 || allowEmptyString) {
				if (allowEmptyString && str.length() == 0)
					return true;
				Integer.parseInt(str, 16);
				return true;
			}
		} catch (NumberFormatException e) {
		}
		return false;
		
	}

	/**
	 * Returns the hex string converted to lowercase.
	 * 
	 * @param hex the hex string
	 * @return the hex string lowercase
	 */
	public static String toLowerCase(String hex) {
		return charsToHex(hexToChars(hex).toLowerCase());
	}


	/**
	 * Converts the string to "zero-padded" string 
	 * @param str string to pad
	 * @param len desired string length including zeros
	 * @return zero-padded string
	 */
	public static String zeroPadding(String str, int len) {
		StringBuffer buffer = new StringBuffer(str);
		for (int i = 0; i < len-str.length(); i++) {
			buffer.insert(0, '0');
		}
		return buffer.toString();		
	}
	public static long getReginValue(long regionValue,byte start,byte end){
		long temp =1L<<(end+1);
		long result=((temp-1)&regionValue) >>start;
		return result;
	}
	public static long setReginValue(long value,long regionValue,byte start,byte end){
		long temp=0xffffffffL&(~(((1L<<(end-start+1))-1)<<start));
		long result = (value&temp)|(regionValue<<start);
		return result;
	}
	public static void main(String[] args){
		byte[] temp = long2byte(0x12345678);
		System.out.println(Long.toHexString(setReginValue(0xffffffffL,0,(byte)30,(byte)31)));
	}
	public static long getNumberValue(String arg){
		if(arg==null)
			return -1;
		String temp=arg.trim().toUpperCase();
		long ret=-1;
		try{
		if(temp.length()<=1){
			ret=Long.parseLong(temp);
		}
		else if(temp.startsWith("0X")){
			ret=Long.parseLong(temp.substring(2), 16);
		}else if(temp.startsWith("0B")){
			ret=Long.parseLong(temp.substring(2), 2);
		}else if(temp.startsWith("0"))
			ret=Long.parseLong(temp.substring(1), 8);
		else{
			ret=Long.parseLong(temp);
		}
		}catch(NumberFormatException nfe){
			return -1;
		}
		return ret;
	}
	public static void println(String str) {
		if(DEBUG) {
			System.out.println(str);
		}
	}
	

}
