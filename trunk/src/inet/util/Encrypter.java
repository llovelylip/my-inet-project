
package inet.util;

import inet.util.security.EncryptionException;
import inet.util.security.StringEncrypter;

public class Encrypter {

	public Encrypter() {
	}

	public static String decrypt(String encryptedText)
			throws EncryptionException {
		return encrypter.decrypt(encryptedText);
	}

	public static String encrypt(String clearText) throws EncryptionException {
		return encrypter.encrypt(clearText);
	}

	private static StringEncrypter encrypter;

	private static String encryptionKey;

	private static String encryptionScheme;

	static {
		encryptionKey = "Nguyen Trong Tho: trongtho@yahoo.com";
		encryptionScheme = "DES";
		encrypter = null;
		try {
			encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
		} catch (EncryptionException ex) {
			ex.printStackTrace();
		}

		//        break MISSING_BLOCK_LABEL_43;
		//        EncryptionException ex;
		//        ex;
		//        ex.printStackTrace();
	}

	public static void main(String[] args) {
		try {
        	System.out.println(inet.util.Encrypter.decrypt("yMqjChR21FKW32Vzbkrcqg=="));
        	//System.out.println(inet.util.Md5.Hash("vnmobile"));
		} catch (Exception ex) {
            
            ex.printStackTrace();
        }
	}
}