package inet.db.game;

/**
 * @author Nguyen Trong Tho
 * @version 1.0
 */

import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

public class GameBuffer implements java.io.Serializable {
    static final int NUM_OF_BUFFERS = 16; //2^n (n=4)
    static final GameBuffer[] bufferArray = new GameBuffer[NUM_OF_BUFFERS];
    static {
        System.out.print("[PhotoBuffer] initializing " + NUM_OF_BUFFERS + " buffers...");
        for (int idx = bufferArray.length-1; idx >= 0; idx--) {
            bufferArray[idx] = new GameBuffer();
        }
        System.out.println("OK");
    }

    // key: id.toString; value: byte[] content
    private Map hashmap = new HashMap();
    private GameDAO dao = new GameDAO();

    /* Private methods */
    private byte[] lookupInternal(String theKey) {
        byte[] theVal = null;
        //synchronized (hashmap) {
            theVal = (byte[]) hashmap.get(theKey);
        //}
        if (theVal != null) {
            return theVal;
        }
        // Here theVal == null --> Load the value
        // theVal = ....
        theVal = dao.getGifFile(new BigDecimal(theKey));
        if (theVal != null) {
            synchronized (hashmap) {
                hashmap.put(theKey, theVal);
            }
        }
        return theVal;
    }
    private void updateInternal(String theKey, byte[] theVal) {
        if (theKey == null || theVal == null)
            return;
        synchronized (hashmap) {
            hashmap.put(theKey, theVal);
        }
    }
    private void removeInternal(String theKey) {
        if (theKey == null)
            return;
        synchronized (hashmap) {
            hashmap.remove(theKey);
        }
    }


    /* Public Class methods */
    public static byte[] lookup(String theKey) {
        int h = theKey.hashCode();
        int bucket = h & (NUM_OF_BUFFERS-1); // pick a buffer
        return bufferArray[bucket].lookupInternal(theKey);
    }
    public static void update(String theKey, byte[] theVal) {
        int h = theKey.hashCode();
        int bucket = h & (NUM_OF_BUFFERS - 1); // pick a buffer
        bufferArray[bucket].updateInternal(theKey, theVal);
    }
    public static void remove(String theKey) {
        int h = theKey.hashCode();
        int bucket = h & (NUM_OF_BUFFERS - 1); // pick a buffer
        bufferArray[bucket].removeInternal(theKey);
    }
}
