package com.ccnu.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class util_functions {

	public static boolean isPositiveInteger(String str) {  
		return str.matches("^[0-9]*[1-9][0-9]*$");
	}
	
	
	//change into string
	public static String readLine(InputStream in){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		String temp = null;
		byte[] buffer = new byte[1024];
		try {
			while((len=in.read(buffer))!=-1){
				baos.write(buffer,0,len);
			}
			in.close();
			baos.close();
			byte[] result = baos.toByteArray();
			temp= new String(result,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	
	}

	
	public static void main(String[] args){
		
	}
}
