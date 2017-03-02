package edu.kit.pse.osip.monitoring.view.dashboard;

import java.io.PrintStream;
import java.util.Locale;

/**
 * This class can be used to print messages of the default output and error output stream to a GUI console.
 * 
 * @author Martin Armbruster
 * @version 1.1
 */
class UIOutputStream extends PrintStream {
    /**
     * Stores the LoggingConsole used for the output in the GUI.
     */
    private LoggingConsole con;
    private boolean isAlreadyOneLinePrinted;
    
    /**
     * Creates a new output stream for GUI.
     * 
     * @param console LoggingConsole used for the output.
     * @throws NullPointerException when the LoggingConsole is null.
     */
    UIOutputStream(LoggingConsole console) {
        super(System.out);
        if (console == null) {
            throw new NullPointerException("LoggingConsole is null.");
        }
        con = console;
        isAlreadyOneLinePrinted = false;
    }
    
    @Override
    public void write(byte[] buf, int off, int len) {
        for (int i = off; i < off + len; i++) {
            print(buf[i]);
        }
    }
    
    @Override
    public void write(int b) {
        print(b);
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public void close() {
    }
    
    @Override
    public boolean checkError() {
        return false;
    }
    
    @Override
    protected void setError() {
    }
    
    @Override
    protected void clearError() {
    }
    
    @Override
    public PrintStream append(char c) {
        print(c);
        return this;
    }
    
    @Override
    public PrintStream append(CharSequence csq) {
        print(csq);
        return this;
    }
    
    @Override
    public PrintStream append(CharSequence csq, int start, int end) {
        print(csq.subSequence(start, end));
        return this;
    }
    
    @Override
    public PrintStream format(Locale l, String format, Object... args) {
        print(String.format(l, format, args));
        return this;
    }
    
    @Override
    public PrintStream format(String format, Object... args) {
        print(String.format(format, args));
        return this;
    }
    
    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        print(String.format(l, format, args));
        return this;
    }
    
    @Override
    public PrintStream printf(String format, Object... args) {
        print(String.format(format, args));
        return this;
    }
    
    @Override
    public void print(boolean b) {
        print(Boolean.toString(b));
    }
    
    @Override
    public void print(char c) {
        print(Character.toString(c));
    }
    
    @Override
    public void print(char[] s) {
        print(String.copyValueOf(s));
    }
    
    @Override
    public void print(double d) {
        print(Double.toString(d));
    }
    
    @Override
    public void print(float f) {
        print(Float.toString(f));
    }
    
    @Override
    public void print(int i) {
        print(Integer.toString(i));
    }
    
    @Override
    public void print(long l) {
        print(Long.toString(l));
    }
    
    @Override
    public void print(Object obj) {
        print(obj.toString());
    }
    
    @Override
    public void print(String s) {
        if (isAlreadyOneLinePrinted) {
            con.logWithoutTime(s);
        } else {
            con.log(s);
            isAlreadyOneLinePrinted = true;
        }
    }
    
    @Override
    public void println() {
        con.logWithoutTime("\n");
        isAlreadyOneLinePrinted = false;
    }
    
    @Override
    public void println(boolean x) {
        print(x);
        println();
    }
    
    @Override
    public void println(char x) {
        print(x);
        println();
    }
    
    @Override
    public void println(char[] x) {
        print(x);
        println();
    }
    
    @Override
    public void println(double x) {
        print(x);
        println();
    }
    
    @Override
    public void println(float x) {
        print(x);
        println();
    }
    
    @Override
    public void println(int x) {
        print(x);
        println();
    }
    
    @Override
    public void println(long x) {
        print(x);
        println();
    }
    
    @Override
    public void println(Object x) {
        print(x);
        println();
    }
    
    @Override
    public void println(String x) {
        print(x);
        println();
    }
}
