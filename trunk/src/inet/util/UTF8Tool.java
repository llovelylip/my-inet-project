package inet.util;
import java.io.*;
public class UTF8Tool {
	  private static final char[] A={ 193, 192, 195, 7840, 7842, 194, 7846, 7850, 7852, 7848, 258, 7856, 7860, 7862, 7858, 7844 };
	  private static final char[] D= { 272 };
	  private static final char[] E= { 201, 200, 7868, 7864, 7866, 202, 7870, 7872, 7876, 7878, 7874 };
	  private static final char[] I={ 205, 204, 296, 7882, 7880 };
	  private static final char[] O={ 211, 210, 213, 7884, 7902, 212, 7888, 7890, 7894, 7896, 7892, 416, 7898, 7900, 7904, 7906, 7902 };
	  private static final char[] U= { 218, 217, 360, 7908, 7910 };
	  private static final char[] Y= { 221, 7922, 7928, 7924, 7926 };
	  private static final char[] a = { 225, 224, 227, 7841, 7843, 226, 7847, 7851, 7853, 7849, 259, 7855, 7857, 7861, 7863, 7859, 7845 };
	  private static final char[] d= { 273 };
	  private static final char[] e= { 233, 232, 7869, 7865, 7867, 234, 7871, 7873, 7877, 7879, 7875 };
	  private static final char[] i= { 237, 236, 297, 7883, 7881 };
	  private static final char[] o = { 243, 242, 245, 7885, 7887, 244, 7889, 7891, 7895, 7897, 7893, 417, 7899, 7901, 7905, 7907, 7903 };
	  private static final char[] u= { 250, 249, 7911, 7909, 432, 7913, 7915, 7917, 7921, 361, 7919 };
	  private static final char[] y= { 253, 7923, 7929, 7925, 7927 };
    public static String coDau2KoDau(String text) {
        text = convert(text, a, 'a');
        text = convert(text, A, 'A');
        text = convert(text, O, 'O');
        text = convert(text, E, 'E');
        text = convert(text, I, 'I');
        text = convert(text, U, 'U');
        text = convert(text, Y, 'Y');
        text = convert(text, D, 'D');
        text = convert(text, d, 'd');
        text = convert(text, e, 'e');
        text = convert(text, o, 'o');
        text = convert(text, i, 'i');
        text = convert(text, u, 'u');
        text = convert(text, y, 'y');
        return text;
    }
    private static String convert(String text, char[] oldChars, char newChar) {
        if (text == null) return null;
        for (int i=0; i<oldChars.length; i++) {
            text = text.replace(oldChars[i], newChar);
        }
        return text;
    }
    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\abc.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
              //  System.out.println(HexaTool.toHexString(line.getBytes()));
                System.out.println(coDau2KoDau(line));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String[] args) {
       String s="abc";
       //s=s.replaceAll(regex, replacement);
       System.out.print(UTF8Tool.coDau2KoDau(""));
      //  System.out.println(UTF8Tool.coDau2KoDau("Nguy?n th? Bích Huy?n"));
    }
}

