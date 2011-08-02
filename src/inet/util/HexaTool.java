package inet.util;

/**
 * @author Nguyen Trong Tho
 * @version 1.0
 */

public class HexaTool {
    //////////////////////////////////////////////////////////////////////
   // HEX Conversion
   // You can display in hex using code like this:
   //       String hex = Integer.toString(i , 16 /* radix */ );
   // That won't apply any lead zeroes.
   // Here is how to get a lead 0 for a fixed two character hex representation
   // of a byte:    convert a byte b to 2-char hex string with possible leading zero.
   //       String s2 = Integer.toString( ( b & 0xff ) + 0x100, 16 /* radix */ ) .substring( 1 );
   // You can convert a hex String to internal binary like this:
   //       int i = Integer.parseInt(g .trim(), 16 /* radix */ );

   //////////////////////////////////////////////////////////////////////
   // Fast convert a byte array to a hex string
   // with possible leading zero.
   public static String toHexString(byte[] b) {
       StringBuffer sb = new StringBuffer(b.length * 2);
       for (int i = 0; i < b.length; i++) {
           // look up high nibble char
           sb.append(hexChar[ (b[i] & 0xF0) >>> 4]);
           // look up low nibble char
           sb.append(hexChar[b[i] & 0x0F]);
       }
       return sb.toString();
   }
   // table to convert a nibble to a hex char.
   static char[] hexChar = {
       '0', '1', '2', '3',
       '4', '5', '6', '7',
       '8', '9', 'A', 'B',
       'C', 'D', 'E', 'F'};

   /**
    * Convert a hex string to a byte array.
    * Permits upper or lower case hex.
    *
    * @param s String must have even number of characters.
    * and be formed only of digits 0-9 A-F or
    * a-f. No spaces, minus or plus signs.
    * @return corresponding byte array.
    */
   public static byte[] fromHexString(String s) throws IllegalArgumentException {
       int stringLength = s.length();
       if ( (stringLength & 0x1) != 0) { //Odd (le)
           throw new IllegalArgumentException("fromHexString requires an even number of hex characters");
       }
       byte[] b = new byte[stringLength / 2];

       for (int i = 0, j = 0; i < stringLength; i += 2, j++) {
           int high = charToNibble(s.charAt(i));
           int low = charToNibble(s.charAt(i + 1));
           b[j] = (byte) ( (high << 4) | low);
       }
       return b;
   }

   /**
    * convert a single char to corresponding nibble.
    *
    * @param c char to convert. must be 0-9 a-f A-F, no
    * spaces, plus or minus signs.
    *
    * @return corresponding integer
    */
   private static int charToNibble(char c) {
       if ('0' <= c && c <= '9') {
           return c - '0';
       } else if ('a' <= c && c <= 'f') {
           return c - 'a' + 0xa;
       } else if ('A' <= c && c <= 'F') {
           return c - 'A' + 0xa;
       } else {
           throw new IllegalArgumentException("Invalid hex character: " + c);
       }
   }

}
