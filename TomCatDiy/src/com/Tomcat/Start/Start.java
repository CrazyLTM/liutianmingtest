package com.Tomcat.Start;


import java.io.InputStream;
import java.io.OutputStream;

public class Start {
    //���������ݶ���byte�����в�����byte����
	public byte[]  init(InputStream iStream) throws Exception {
		byte[] bs = new byte[1024];
		iStream.read(bs);
		return bs;
		}
	
}
