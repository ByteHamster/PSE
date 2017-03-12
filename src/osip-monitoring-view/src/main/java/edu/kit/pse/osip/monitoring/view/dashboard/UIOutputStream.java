package edu.kit.pse.osip.monitoring.view.dashboard;

import java.io.PrintStream;
import java.util.Locale;

/**
 * This class can be used to print messages of the default output and error output stream to a GUI console.
 * 
 * @author Martin Armbruster
 * @version 1.2
 */
class UIOutputStream extends PrintStream {
    /**
     * Stores the LoggingConsole used for the output in the GUI.
     */
    private LoggingConsole con;
    private boolean isAlreadyOneLinePrinted;
    private PrintStream oldStream;
    
    /**
     * Creates a new output stream for GUI.
     *
     * @param old The old stream to interept
     * @param console LoggingConsole used for the output.
     * @throws NullPointerException when the LoggingConsole is null.
     */
    UIOutputStream(PrintStream old, LoggingConsole console) {
        super(old);
        oldStream = old;
        if (console == null) {
            throw new NullPointerException("LoggingConsole is null.");
        }
        con = console;
        isAlreadyOneLinePrinted = false;
    }
    
    @Override
    public synchronized void write(byte[] buf, int off, int len) {
        for (int i = off; i < off + len; i++) {
            print(buf[i]);
        }
    }
    
    @Override
    public synchronized void write(int b) {
        print(b);
    }
    
    @Override
    public synchronized void flush() {
    }
    
    @Override
    public synchronized void close() {
    }
    
    @Override
    public synchronized boolean checkError() {
        return false;
    }
    
    @Override
    protected synchronized void setError() {
    }
    
    @Override
    protected synchronized void clearError() {
    }
    
    @Override
    public synchronized PrintStream append(char c) {
        print(c);
        return this;
    }
    
    @Override
    public synchronized PrintStream append(CharSequence csq) {
        print(csq);
        return this;
    }
    
    @Override
    public synchronized PrintStream append(CharSequence csq, int start, int end) {
        print(csq.subSequence(start, end));
        return this;
    }
    
    @Override
    public synchronized PrintStream format(Locale l, String format, Object... args) {
        print(String.format(l, format, args));
        return this;
    }
    
    @Override
    public synchronized PrintStream format(String format, Object... args) {
        print(String.format(format, args));
        return this;
    }
    
    @Override
    public synchronized PrintStream printf(Locale l, String format, Object... args) {
        print(String.format(l, format, args));
        return this;
    }
    
    @Override
    public synchronized PrintStream printf(String format, Object... args) {
        print(String.format(format, args));
        return this;
    }
    
    @Override
    public synchronized void print(boolean b) {
        print(Boolean.toString(b));
    }
    
    @Override
    public synchronized void print(char c) {
        print(Character.toString(c));
    }
    
    @Override
    public synchronized void print(char[] s) {
        print(String.copyValueOf(s));
    }
    
    @Override
    public synchronized void print(double d) {
        print(Double.toString(d));
    }
    
    @Override
    public synchronized void print(float f) {
        print(Float.toString(f));
    }
    
    @Override
    public synchronized void print(int i) {
        print(Integer.toString(i));
    }
    
    @Override
    public synchronized void print(long l) {
        print(Long.toString(l));
    }
    
    @Override
    public synchronized void print(Object obj) {
        print(obj.toString());
    }
    
    @Override
    public synchronized void print(String s) {
        oldStream.print(s);
        if (isAlreadyOneLinePrinted) {
            con.logWithoutTime(s);
        } else {
            con.log(s);
            isAlreadyOneLinePrinted = true;
        }
    }
    
    @Override
    public synchronized void println() {
        oldStream.println();
        con.logWithoutTime("\n");
        isAlreadyOneLinePrinted = false;
    }
    
    @Override
    public synchronized void println(boolean x) {
        print(x);
        println();
    }
    
    @Override
    public synchronized void println(char x) {
        print(x);
        println();
    }
    
    @Override
    public synchronized void println(char[] x) {
        print(x);
        println();
    }
    
    @Override
    public synchronized void println(double x) {
        print(x);
        println();
    }
    
    @Override
    public synchronized void println(float x) {
        print(x);
        println();
    }
    
    @Override
    public synchronized void println(int x) {
        print(x);
        println();
    }
    
    @Override
    public synchronized void println(long x) {
        print(x);
        println();
    }
    
    @Override
    public synchronized void println(Object x) {
        print(x);
        println();
    }
    
    @Override
    public synchronized void println(String x) {
        print(x);
        println();
    }
}
