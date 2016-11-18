package org.iMage.edge.detection.cli;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.iMage.edge.detection.base.ImageFilter;
import org.iMage.edge.detection.sobel.filter.BlurFilter;
import org.iMage.edge.detection.sobel.filter.GrayScaleFilter;
import org.iMage.edge.detection.sobel.filter.LowerThresholdFilter;
import org.iMage.edge.detection.sobel.filter.PrewittFilter;
import org.iMage.edge.detection.sobel.filter.SobelFilter;

/**
 * This class parses all command line parameters and applies the edge detection filter to the given image.
 * 
 * @author Mathias Landhäußer (swt1@ipd.kit.edu)
 * @author Hans-Peter Lehmann
 * @version 1.1
 */
public final class App {

    private static final String CMD_OPTION_INPUT_FILE = "i";
    private static final String CMD_OPTION_OUT_FILE   = "o";
    private static final String CMD_OPTION_BLUR       = "b";
    private static final String CMD_OPTION_PREWITT    = "p";
    private static final String CMD_OPTION_THRESHOLD  = "t";

    /** Private constructor: We don't instantiate App from the outside */
    private App() {
    }

    /**
     * Main method - reads the command line arguments and runs filter as requested.
     * 
     * @param args Command line arguments (to be parsed with commons-cli)
     */
    public static void main(String[] args) {
        CommandLine cmd = null;
        try {
            cmd = doCommandLineParsing(args);
        } catch (ParseException exception) {
            // oops, something went wrong
            System.err.println("Wrong command line arguments given: " + exception.getMessage());
            System.exit(1);
        }

        File inFile = new File(cmd.getOptionValue("inFile"));
        if (!inFile.exists() || inFile.isDirectory()) {
            System.err.println("Please provide a valid input file");
            System.exit(1);
        }

        File outFile = new File(cmd.getOptionValue("outFile"));
        if (outFile.isDirectory()) {
            System.err.println("Please provide a valid output file");
            System.exit(1);
        } else if (outFile.exists()) {
            System.err.println("Output file already exists");
            System.exit(1);
        }

        ArrayList<ImageFilter> filters = new ArrayList<>();

        filters.add(new GrayScaleFilter());
        if (cmd.hasOption("blur")) {
            filters.add(new BlurFilter());
        }
        if (cmd.hasOption("usePrewitt")) {
            filters.add(new PrewittFilter());
        } else {
            filters.add(new SobelFilter());
        }
        if (cmd.hasOption("threshold")) {
            int threshold = Integer.valueOf(cmd.getOptionValue("threshold"));
            filters.add(new LowerThresholdFilter(threshold));
        }

        try {
            System.out.println("Reading input file...");
            BufferedImage image = ImageIO.read(inFile);

            for (int i = 0; i < filters.size(); i++) {
                System.out.println("Applying " + filters.get(i).getClass().getSimpleName() + "...");
                image = filters.get(i).applyFilter(image);
            }

            System.out.println("Writing output file...");
            ImageIO.write(image, "png", outFile);
            System.out.println("Done.");

        } catch (IOException e) {
            System.err.println("Impossible to process image: " + e.getMessage());
            System.exit(1);
        }

    }


    /**
     * Parse and check command line arguments
     * 
     * @param args Command line arguments given by the user
     * @return CommandLine object that encapsulates all options
     * @throws ParseException Thrown iff wrong command line parameters or arguments given.
     */
    static CommandLine doCommandLineParsing(String[] args) throws ParseException {
        Options options = new Options();
        Option opt;

        /*
         * Define command line options and arguments
         */
        opt = new Option(CMD_OPTION_INPUT_FILE, "inFile", true, "Input file");
        opt.setRequired(true);
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option(CMD_OPTION_OUT_FILE, "outFile", true, "Output file");
        opt.setRequired(true);
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option(CMD_OPTION_BLUR, "blur", false, "Blur image before processing");
        opt.setRequired(false);
        opt.setType(Boolean.class);
        options.addOption(opt);

        opt = new Option(CMD_OPTION_PREWITT, "usePrewitt", false, "Use Prewitt filter instead of Sobel");
        opt.setRequired(false);
        opt.setType(Boolean.class);
        options.addOption(opt);

        opt = new Option(CMD_OPTION_THRESHOLD, "threshold", true, "Apply lower threshold filter to image [0-255]");
        opt.setRequired(false);
        opt.setType(Integer.class);
        options.addOption(opt);

        // create the parser
        CommandLineParser parser = new BasicParser();
        CommandLine line = null;
        // parse the command line arguments
        line = parser.parse(options, args);

        return line;
    }
}
