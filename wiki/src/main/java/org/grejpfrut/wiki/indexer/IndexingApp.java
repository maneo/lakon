package org.grejpfrut.wiki.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PatternOptionBuilder;
import org.apache.log4j.Logger;

/**
 * This is main class of data dump indexer. Index data dump file
 * with lucene and creates index file. It can index whole 
 * articles dump or abstract data dump. 
 */
public class IndexingApp {
    private static Logger logger = Logger.getLogger(IndexingApp.class);

    private final static String configuration = "/conf.properties";

    private static InputStream getConfigurationPath() {
        InputStream fis = IndexingApp.class.getResourceAsStream(configuration);
        return fis;
    }

    public static void main(final String[] args) {
        // Parse input arguments.
        final Options options = buildOptions();
        final CommandLineParser parser = new GnuParser();
        boolean showHelp = false;
        try {
            if (args.length == 0) {
                showHelp = true;
            } else {

                // Collect arguments
                final CommandLine cmd = parser.parse(options, args);

                final String dataDumpSourceFile = (String) cmd
                        .getOptionObject("dd");
                final File indexOutputPath = (File) cmd.getOptionObject("o");

                final InputStream confStream;
                if (cmd.hasOption("cp")) {
                    try {
                        confStream = new FileInputStream((File) cmd
                                .getOptionObject("cp"));
                    } catch (FileNotFoundException e) {
                        logger
                                .fatal("Configuration property file does not exist: "
                                        + cmd.getOptionObject("cp"));
                        return;
                    }
                } else {
                    confStream = IndexingApp.getConfigurationPath();
                }

                final boolean indexAbstracts = cmd.hasOption("a");

                final WikiDumpIndexer wdi = new WikiDumpIndexer(
                        dataDumpSourceFile, indexOutputPath
                                .getAbsolutePath(), confStream, indexAbstracts);
                wdi.index();
            }
        } catch (NumberFormatException e) {
            System.out.println("Not a number: " + e.toString());
        } catch (MissingOptionException e) {
            System.out.println("Missing program option: " + e.toString());
        } catch (ParseException e) {
            System.out.println("Error parsing arguments: " + e.toString());
            showHelp = true;
        } finally {
            if (showHelp) {
                new HelpFormatter().printHelp(IndexingApp.class.getName()
                        + " [OPTIONS]", options);
            }
        }
    }

    private final static Options buildOptions() {
        final Options options = new Options();

        final OptionGroup type = new OptionGroup();

        OptionBuilder.withDescription("Index wiki abstracts only.");
        OptionBuilder.isRequired(false);
        type.addOption(OptionBuilder.create("a"));

        OptionBuilder.withDescription("Index whole wiki pages.");
        OptionBuilder.isRequired(false);
        type.addOption(OptionBuilder.create("p"));

        type.setRequired(true);
        options.addOptionGroup(type);

        OptionBuilder.withArgName("file");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Wiki data dump file (XML).");
        OptionBuilder.isRequired(true);
        OptionBuilder.withLongOpt("data-dump");
        OptionBuilder.withType(PatternOptionBuilder.STRING_VALUE);
        options.addOption(OptionBuilder.create("dd"));

        OptionBuilder.withArgName("dir");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Output index folder.");
        OptionBuilder.isRequired(true);
        OptionBuilder.withLongOpt("index");
        OptionBuilder.withType(PatternOptionBuilder.FILE_VALUE);
        options.addOption(OptionBuilder.create("o"));

        OptionBuilder.withArgName("file");
        OptionBuilder.hasArg();
        OptionBuilder.withDescription("Configuration properties file.");
        OptionBuilder.isRequired(false);
        OptionBuilder.withLongOpt("config-properties");
        OptionBuilder.withType(PatternOptionBuilder.FILE_VALUE);
        options.addOption(OptionBuilder.create("cp"));

        return options;
    }
}
