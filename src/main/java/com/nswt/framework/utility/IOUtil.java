package com.nswt.framework.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ���� : 2010-5-25 <br>
 * ����: NSWT <br>
 * ���䣺nswt@nswt.com.cn <br>
 */
public class IOUtil {

	public static byte[] getBytesFromStream(InputStream is) throws IOException {
		return getBytesFromStream(is, Integer.MAX_VALUE);
	}

	public static byte[] getBytesFromStream(InputStream is, int max) throws IOException {
		byte[] buffer = new byte[1024];
		int read = -1;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] data = null;
		try {
			while ((read = is.read(buffer)) != -1) {
				if (bos.size() > max) {
					throw new IOException("ָ�������ֽ����������ֵ:" + max);
				}
				if (read > 0) {
					byte[] chunk = null;
					if (read == 1024) {
						chunk = buffer;
					} else {
						chunk = new byte[read];
						System.arraycopy(buffer, 0, chunk, 0, read);
					}
					bos.write(chunk);
				}
			}
			data = bos.toByteArray();
		} finally {
			if (bos != null) {
				bos.close();
				bos = null;
			}
		}
		return data;
	}
}
