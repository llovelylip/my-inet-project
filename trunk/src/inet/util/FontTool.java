package inet.util;

import java.io.*;

public class FontTool {
    private static final char[] a = {0xB8, 0xB5, 0xB6, 0xB9, 0xA9, 0xCA, 0xC7, 0xC8, 0xCB, 0xA8, 0xBE, 0xBB, 0xBC, 0xC6, 0x95, 0xBD, 0xC9, '·'};
    private static final char[] A = {0xA2}; //A^
    private static final char[] O = {0xA4}; //O^
    private static final char[] E = {0xA3}; //E^
    private static final char[] D = {0xA7}; //DD
    private static final char[] d = {0xAE}; //dd
    private static final char[] e = {0xD0, 0xCC, 0xCE, 0xD1, 0xAA, 0xD5, 0xD2, 0xD3, 0xD6, 0xCF, 0xD4 };
    private static final char[] o = {0xE3, 0xDF, 0xE1, 0xE4, 0xAB, 0xE8, 0xE5, 0xE6, 0xE9, 0xAC, 0xED, 0xEA, 0xEB, 0xEE, 0xE2, 0xE7, 0xEC};
    private static final char[] i = {0xDD, 0xD7, 0xD8, 0xDC, 0xDE};
    private static final char[] u = {0xF3, 0xEF, 0xF1, 0xF2, 0xF4, 0xF7, 0xAD, 0xF8, 0xF5, 0xF6, 0xF9};
    private static final char[] y = {0xFD, 0xFA, 0xFB, 0xFE, 0xFC};


    public static String coDau2KoDau(String text) {
        text = convert(text, a, 'a');
        text = convert(text, A, 'A');
        text = convert(text, O, 'O');
        text = convert(text, E, 'E');
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
            BufferedReader reader = new BufferedReader(new FileReader("abc.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(HexaTool.toHexString(line.getBytes()));
                System.out.println(coDau2KoDau(line));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        FontTool abc = new FontTool();
        abc.read();
    }
}
