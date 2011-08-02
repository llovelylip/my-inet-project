package inet.util;
import java.io.*;
public class WebUTF8Tool {
    private static final String[] a = {"&#97;", "&#225;", "&#224;", "&#7843;", "&#227;", "&#7841;", "&#259;", "&#7855;", "&#7857;", "&#7859;", "&#7861;", "&#7863;", "&#226;", "&#7845;", "&#7847;","&#7849;","&#7851;","&#7853;"};
    private static final String[] A = {"&#65;", "&#193;","&#192;", "&#7842;", "&#195;","&#7840;", "&#258;", "&#7854;", "&#7856;", "&#7858;","&#7860;","&#7862;", "&#194;", "&#7844;", "&#7846;", "&#7848;","&#7850;","&#7852;"};
    private static final String[] O = {"&#79;", "&#211;", "&#210;", "&#7886;", "&#213;", "&#7884;", "&#212;", "&#7888;", "&#7890;", "&#7892;", "&#7894;", "&#7896;", "&#416;", "&#7898;", "&#7900;","&#7902;", "&#7904;","&#7906;"};
    private static final String[] E = {"&#69;", "&#201;", "&#200;", "&#200;", "&#7868;", "&#7864;", "&#202;", "&#7870; ", "&#7872;", "&#7874;", "&#7876;","&#7878;"};
    private static final String[] I = {" &#73;", "&#205;", "&#204;", "&#7880;", "&#296;","&#7882;"};
    private static final String[] U = {"&#85;", "&#218;", "&#217;", " &#7910;", "&#360;","&#7908;","&#431;","&#7912;","&#7914;","&#7916;","&#7918;","&#7920;"};
    private static final String[] Y = {"&#89;", "&#221;", "&#7922;", "&#7922;", "&#7928;","&#7924;"};
    private static final String[] D = {"&#272;"}; //DD
    private static final String[] d = {"&#273;"}; //dd
    private static final String[] e = {"&#101;", "&#233;", "&#232;", "&#7867;","&#7869;", "&#7865;","&#234;","&#7871;", "&#7873;", "&#7875;", "&#7877;","&#7879;"};
    private static final String[] o = {"&#111;", "&#243;", "&#242;", "&#7887;", "&#245;", "&#7885;", "&#244;", "&#7889;", "&#7891;", "&#7893;", "&#7895;", "&#7897;", "&#417;", "&#7899;", "&#7901;", "&#7903;", "&#7905;","&#7907;"};
    private static final String[] i = {" &#105;","&#237;", "&#236;", "&#7881;", "&#297;","&#7883;"};
    private static final String[] u = {"&#117;", "&#250;", "&#249;","&#7911;", "&#361;", "&#7909;", "&#432;", "&#7913;", "&#7915;", "&#7917;", "&#7919;","&#7921;"};
    private static final String[] y = {"&#121;", "&#253;", "&#7923;", "&#7927;", "&#7929;","&#7925;"};
    private static final char[] ca = {'\u00E1', '\u00E0', '\u00E3', '\u1EA1', '\u1EA3', '\u00E2', '\u1EA7', '\u1EAB', '\u1EAD', '\u1EA9', '\u0103', '\u1EAF', '\u1EB1', '\u1EB5', '\u1EB7', '\u1EB3','\u1EA5'};
    private static final char[] cA = {'\u00C1', '\u00C0', '\u00C3', '\u1EA0', '\u1EA2', '\u00C2', '\u1EA6', '\u1EAA', '\u1EAC', '\u1EA8', '\u0102', '\u1EB0', '\u1EB4', '\u1EB6', '\u1EB2', '\u1EA4'};
    private static final char[] cO = {'\u00D3', '\u00D2', '\u00D5', '\u1ECC', '\u1EDE', '\u00D4', '\u1ED0', '\u1ED2', '\u1ED6', '\u1ED8', '\u1ED4', '\u01A0', '\u1EDA', '\u1EDC', '\u1EE0', '\u1EE2', '\u1EDE'};
    private static final char[] cE = {'\u00C9', '\u00C8', '\u1EBC', '\u1EB8', '\u1EBA', '\u00CA', '\u1EBE', '\u1EC0', '\u1EC4', '\u1EC6', '\u1EC2'};
    private static final char[] cI = {'\u00CD', '\u00CC', '\u0128', '\u1ECA', '\u1EC8'};
    private static final char[] cU = {'\u00DA', '\u00D9', '\u0168', '\u1EE4', '\u1EE6'};
    private static final char[] cY = {'\u00DD', '\u1EF2', '\u1EF8', '\u1EF4', '\u1EF6'};
    private static final char[] cD = {'\u0110'}; //DD
    private static final char[] cd = {'\u0111'}; //dd
    private static final char[] ce = {'\u00E9', '\u00E8', '\u1EBD', '\u1EB9', '\u1EBB', '\u00EA', '\u1EBF', '\u1EC1', '\u1EC5', '\u1EC7', '\u1EC3'};
    private static final char[] co = {'\u00F3', '\u00F2', '\u00F5', '\u1ECD', '\u1ECF', '\u00F4', '\u1ED1', '\u1ED3', '\u1ED7', '\u1ED9', '\u1ED5', '\u01A1', '\u1EDB', '\u1EDD', '\u1EE1', '\u1EE3', '\u1EDF'};
    private static final char[] ci = {'\u00ED', '\u00EC', '\u0129', '\u1ECB', '\u1EC9'};
    private static final char[] cu = {'\u00FA', '\u00F9', '\u1EE7', '\u1EE5', '\u01B0', '\u1EE9', '\u1EEB', '\u1EED', '\u1EF1', '\u0169', '\u1EEF'};
    private static final char[] cy = {'\u00FD', '\u1EF3', '\u1EF9', '\u1EF5', '\u1EF7'};
    public static String coDau2KoDau(String text) {
        text = convert(text, a, "a");
        text = convert(text, A, "A");
        text = convert(text, O, "O");
        text = convert(text, E, "E");
        text = convert(text, I, "I");
        text = convert(text, U,"U");
        text = convert(text, Y, "Y");
        text = convert(text, D, "D");
        text = convert(text, d, "d");
        text = convert(text, e, "e");
        text = convert(text, o, "o");
        text = convert(text, i, "i");
        text = convert(text, u, "u");
        text = convert(text, y, "y");
        text = convert(text, ca, 'a');
        text = convert(text, cA, 'A');
        text = convert(text, cO, 'O');
        text = convert(text, cE, 'E');
        text = convert(text, cI, 'I');
        text = convert(text, cU,'U');
        text = convert(text, cY, 'Y');
        text = convert(text, cD, 'D');
        text = convert(text, cd,'d');
        text = convert(text, ce, 'e');
        text = convert(text, co, 'o');
        text = convert(text, ci, 'i');
        text = convert(text, cu, 'u');
        text = convert(text, cy, 'y');
        return text;
    }
    private static String convert(String text, String[] oldChars, String newChar) {
        if (text == null) return null;
        for (int i=0; i<oldChars.length; i++) {
            text = text.replaceAll(oldChars[i], newChar);
        }
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
       String s=" H&#7885;c Trung Qu&#7889;c cùng Vineco - Tr&#432;&#7901;ng &#272;H S&#7921; ph&#7841;m Tr&#7841;m Giang";
       //s=s.replaceAll(regex, replacement);
       System.out.print(WebUTF8Tool.coDau2KoDau(s));
      //  System.out.println(UTF8Tool.coDau2KoDau("Nguy?n th? B�ch Huy?n"));
    }
}

