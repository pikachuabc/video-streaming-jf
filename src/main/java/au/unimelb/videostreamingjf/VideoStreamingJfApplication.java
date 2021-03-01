package au.unimelb.videostreamingjf;

import au.unimelb.videostreamingjf.utils.Constant;
import org.apache.commons.cli.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class VideoStreamingJfApplication {

    private static void help(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("VideoStreamingJfApplication",Constant.helpHeader, options, Constant.helpFooter, true);
        System.exit(0);
    }

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("rootPath", true, "absolute path of your repository");
        options.addOption("help", "help information");

        CommandLineParser commandLineParser = new DefaultParser();
        try {
            CommandLine commandLine = commandLineParser.parse(options, args);
            if (commandLine.hasOption("help")) {
                help(options);
            } else if (commandLine.hasOption("rootPath")) {
                Constant.rootPath = commandLine.getOptionValue("rootPath");
            } else {
                throw new ParseException("need a root path");
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            help(options);
        }

        SpringApplication.run(VideoStreamingJfApplication.class, args);
    }

}
