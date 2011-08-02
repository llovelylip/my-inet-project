package inet.util;

/**
 * <p>Copyright: 2005</p>
 * @author Nguyen Trong Tho
 * @version 1.0
 */
public class Logger {
    static final boolean VERBOSE = true;


    private String classname = "";
    public Logger(String classname) {
        this.classname = classname;
    }

    public void log (String msg) {
        if (VERBOSE) {
            System.out.println("[" + this.classname + "] " + msg);
        }
    }

    //print error message
    public void error (String msg) {
        if (VERBOSE) {
            System.out.println("[ERROR] [" + this.classname + "] " + msg);
        }
    }

}
