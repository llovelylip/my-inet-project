package inet.util;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SDTEncrypter
{
  private static Map char2Digit;
  private static Map digit2Char = new Hashtable();

  static
  {
    char2Digit = new Hashtable();

    digit2Char.put("00", "aa");
    digit2Char.put("11", "bb");
    digit2Char.put("22", "cc");
    digit2Char.put("33", "dd");
    digit2Char.put("44", "ee");
    digit2Char.put("55", "ff");
    digit2Char.put("66", "gg");
    digit2Char.put("77", "hh");
    digit2Char.put("88", "ii");
    digit2Char.put("99", "kk");

    digit2Char.put("01", "ma");
    digit2Char.put("02", "mb");
    digit2Char.put("03", "mc");
    digit2Char.put("04", "md");
    digit2Char.put("05", "me");
    digit2Char.put("06", "mf");
    digit2Char.put("07", "mg");
    digit2Char.put("08", "mh");
    digit2Char.put("09", "mi");

    digit2Char.put("10", "na");
    digit2Char.put("12", "nb");
    digit2Char.put("13", "nc");
    digit2Char.put("14", "nd");
    digit2Char.put("15", "ne");
    digit2Char.put("16", "nf");
    digit2Char.put("17", "ng");
    digit2Char.put("18", "nh");
    digit2Char.put("19", "ni");

    digit2Char.put("20", "pa");
    digit2Char.put("21", "pb");
    digit2Char.put("23", "pc");
    digit2Char.put("24", "pd");
    digit2Char.put("25", "pe");
    digit2Char.put("26", "pf");
    digit2Char.put("27", "pg");
    digit2Char.put("28", "ph");
    digit2Char.put("29", "pi");

    digit2Char.put("30", "qa");
    digit2Char.put("31", "qb");
    digit2Char.put("32", "qc");
    digit2Char.put("34", "qd");
    digit2Char.put("35", "qe");
    digit2Char.put("36", "qf");
    digit2Char.put("37", "qg");
    digit2Char.put("38", "qh");
    digit2Char.put("39", "qi");

    digit2Char.put("40", "ja");
    digit2Char.put("41", "jb");
    digit2Char.put("42", "jc");
    digit2Char.put("43", "jd");
    digit2Char.put("45", "je");
    digit2Char.put("46", "jf");
    digit2Char.put("47", "jg");
    digit2Char.put("48", "jh");
    digit2Char.put("49", "ji");

    digit2Char.put("50", "sa");
    digit2Char.put("51", "sb");
    digit2Char.put("52", "sc");
    digit2Char.put("53", "sd");
    digit2Char.put("54", "se");
    digit2Char.put("56", "sf");
    digit2Char.put("57", "sg");
    digit2Char.put("58", "sh");
    digit2Char.put("59", "si");

    digit2Char.put("60", "xa");
    digit2Char.put("61", "xb");
    digit2Char.put("62", "xc");
    digit2Char.put("63", "xd");
    digit2Char.put("64", "xe");
    digit2Char.put("65", "xf");
    digit2Char.put("67", "xg");
    digit2Char.put("68", "xh");
    digit2Char.put("69", "xi");

    digit2Char.put("70", "ya");
    digit2Char.put("71", "yb");
    digit2Char.put("72", "yc");
    digit2Char.put("73", "yd");
    digit2Char.put("74", "ye");
    digit2Char.put("75", "yf");
    digit2Char.put("76", "yg");
    digit2Char.put("78", "yh");
    digit2Char.put("79", "yi");

    digit2Char.put("80", "wa");
    digit2Char.put("81", "wb");
    digit2Char.put("82", "wc");
    digit2Char.put("83", "wd");
    digit2Char.put("84", "we");
    digit2Char.put("85", "wf");
    digit2Char.put("86", "wg");
    digit2Char.put("87", "wh");
    digit2Char.put("89", "wi");

    digit2Char.put("90", "za");
    digit2Char.put("91", "zb");
    digit2Char.put("92", "zc");
    digit2Char.put("93", "zd");
    digit2Char.put("94", "ze");
    digit2Char.put("95", "zf");
    digit2Char.put("96", "zg");
    digit2Char.put("97", "zh");
    digit2Char.put("98", "zi");

    for (Iterator it = digit2Char.keySet().iterator(); it.hasNext(); ) {
      String d = (String)it.next();
      String c = (String)digit2Char.get(d);
      char2Digit.put(c, d);
    }
  }

  public static String decode(String mobile)
  {
    if ((mobile == null) || (mobile.length() < 3)) return mobile;

    if (mobile.endsWith("x")) {
      String nn = mobile.substring(mobile.length() - 3, mobile.length() - 1);
      nn = (String)char2Digit.get(nn);

      mobile = mobile.substring(0, mobile.length() - 3) + nn;
    }
    return mobile;
  }
  public static String encode(String mobile)
  {
    if ((mobile == null) || (mobile.length() < 3)) return mobile;

    if (mobile.endsWith("x")) return mobile;

    String nn = mobile.substring(mobile.length() - 2);
    nn = (String)digit2Char.get(nn);
    if (nn != null) mobile = mobile.substring(0, mobile.length() - 2) + nn + "x";
    return mobile;
  }

  public static void main(String[] args)
  {
	  System.out.println(SDTEncrypter.encode("84914261999"));
  }
}