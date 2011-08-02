
package inet.util.security;

import java.io.PrintStream;

// Referenced classes of package sms.util.security:
//            EncryptionException, StringEncrypter

public class StringEncrypterTest
{

    public StringEncrypterTest()
    {
    }

    public static void main(String args[])
    {
        StringEncrypterTest test = new StringEncrypterTest();
        try
        {
            test.testEncryptsUsingDes();
            test.testDecryptsUsingDes();
        }
        catch(EncryptionException ex)
        {
            ex.printStackTrace();
        }
    }

    public void testThrowsErrorOnInvalidKeySpec()
        throws EncryptionException
    {
        String encryptionScheme = "asdf";
        String encryptionKey = "123456789012345678901234567890";
        StringEncrypter encrypter;
        try
        {
            encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Encryption scheme not supported: asdf  " + e.getMessage());
        }
    }

    public void testEncryptsUsingDesEde()
        throws EncryptionException
    {
        String stringToEncrypt = "test";
        String encryptionKey = "123456789012345678901234567890";
        String encryptionScheme = "DESede";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        String encryptedString = encrypter.encrypt(stringToEncrypt);
        System.out.println("encryptedString=" + encryptedString);
    }

    public void testEncryptsUsingDes()
        throws EncryptionException
    {
        String stringToEncrypt = "test";
        String encryptionKey = "123456789012345678901234567890";
        String encryptionScheme = "DES";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        String encryptedString = encrypter.encrypt(stringToEncrypt);
        System.out.println("encryptedString=" + encryptedString);
    }

    public void testEncryptionKeyCanContainLetters()
        throws EncryptionException
    {
        String string = "test";
        String encryptionKey = "ASDF asdf 1234 8983 jklasdf J2Jaf8";
        String encryptionScheme = "DESede";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        String encryptedString = encrypter.encrypt(string);
        System.out.println("encryptedString=" + encryptedString);
    }

    public void testDecryptsUsingDesEde()
        throws EncryptionException
    {
        String string = "Ni2Bih3nCUU=";
        String encryptionKey = "123456789012345678901234567890";
        String encryptionScheme = "DESede";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        String decryptedString = encrypter.decrypt(string);
        System.out.println("decryptedString= " + decryptedString);
    }

    public void testDecryptsUsingDes()
        throws EncryptionException
    {
        String string = "oEtoaxGK9ns=";
        String encryptionKey = "123456789012345678901234567890";
        String encryptionScheme = "DES";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        String decryptedString = encrypter.decrypt(string);
        System.out.println("decryptedString=" + decryptedString);
    }

    public void testCantInstantiateWithNullEncryptionKey()
        throws EncryptionException
    {
        String encryptionScheme = "DESede";
        String encryptionKey = null;
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
    }

    public void testCantInstantiateWithEmptyEncryptionKey()
        throws Exception
    {
        String encryptionScheme = "DESede";
        String encryptionKey = "";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
    }

    public void testCantDecryptWithNullString()
        throws EncryptionException
    {
        String encryptionScheme = "DESede";
        String encryptionKey = "123456789012345678901234";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        encrypter.decrypt(null);
    }

    public void testCantDecryptWithEmptyString()
        throws EncryptionException
    {
        String encryptionScheme = "DESede";
        String encryptionKey = "123456789012345678901234";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        encrypter.decrypt("");
    }

    public void testCantEncryptWithNullString()
        throws EncryptionException
    {
        String encryptionScheme = "DESede";
        String encryptionKey = "123456789012345678901234";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        encrypter.encrypt(null);
    }

    public void testCantEncryptWithEmptyString()
        throws EncryptionException
    {
        String encryptionScheme = "DESede";
        String encryptionKey = "123456789012345678901234";
        StringEncrypter encrypter = new StringEncrypter(encryptionScheme, encryptionKey);
        encrypter.encrypt("");
    }
}