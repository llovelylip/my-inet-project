package inet.util;

import java.io.*;

public class MyFileTool {

	public MyFileTool() {
		super();
		
	}

	/**
	 * @param args
	 */
	public static byte[] getBytesFromFile(InputStream in) throws IOException

	{

		long length = in.available();

		byte[] bytes = new byte[(int) length];

		int offset = 0;

		int numRead = 0;

		while (offset < bytes.length

		&& (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {

			offset += numRead;

		}

		if (offset < bytes.length) {

			throw new IOException("Khong the doc het tap tin ");

		}
		in.close();

		return bytes;

	}

	public void readFromKeyBoard() {
		try

		{

			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));

			String str = "";

			while ((str = in.readLine()) != null)

			{

				// x? l� d? li?u

			}

			in.close();

		}

		catch (IOException ioe) {
		}
	}

	public void readFromFile(File file) {
		try

		{

			BufferedReader in = new BufferedReader(new FileReader(file));

			String str = "";

			while ((str = in.readLine()) != null)

			{

				// x? l� d? li?u

			}

		}

		catch (IOException ioe) {
		}
	}

	public void writeToFile(File file,boolean add,String s) {
		try

		{

			BufferedWriter out = new BufferedWriter(new FileWriter(file,
					add));

			out.write(s);

			out.close();

		}

		catch (IOException ioe) {
		}
	}

	public void isExist(String fileName) {
		File file = new File(fileName); // new File(�tenthumuc�);

		boolean isExists = file.exists();

		if (isExists)

		{

			// l�m g� ?�

		}

		else

		{

			// c?ng l�m g� ?�

		}
	}

	public static byte[] getBytesFromFile(File file) throws IOException

	{

		FileInputStream is = new FileInputStream(file);
		long length = file.length();

		if (length > Integer.MAX_VALUE)

		{

			// t?p tin qu� l?n

		}

		// T?o m?ng byte ?? ch?a d? li?u

		byte[] bytes = new byte[(int) length];

		// ??c d? li?u v�o bytes

		int offset = 0;

		int numRead = 0;

		while (offset < bytes.length

		&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {

			offset += numRead;

		}

		// Ki?m tra xem ?� ??c h?t to�n b? t?p tin hay ch?a

		if (offset < bytes.length) {

			throw new IOException("Kh�ng th? ??c h?t t?p tin " + file.getName());

		}

		// ?�ng lu?ng

		is.close();

		return bytes;

	}

	public static void visitAllDirsAndFiles(File dir) {

		// l�m g� ?� v?i dir ? ?�y�

		// ���������

		if (dir.isDirectory())

		{

			String[] children = dir.list();

			for (int i = 0; i < children.length; i++)

			{

				visitAllDirsAndFiles(new File(dir, children[i]));

			}

		}

	}

}
