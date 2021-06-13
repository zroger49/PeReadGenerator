import com.martiansoftware.jsap.*;
import java.io.IOException;
import java.util.*;


/*
TODO:
[] Comand line application
[] Graphical interface (when running without arguments)
[] Code a SE mode for the reads (possibly refactor the code by implenting a more general class for SE and PE)
[] Fragment size variable? (How does this behave in real data?)
[] Add adaptor sequences in reads (to all sequences? to a fraction of sequences?)
[] Implement Indels and Improve on the error rate model (different rates for substitutions for each substitution and indels).
[] Basecalling model
[] Repetitive regions
[] Use BioJava module for parsing
 */


public class PeReadGenerator {

//    private static final String SEQUENCE_FILE = "sequence.fasta"; //file to generate the reads from
//    private static final String READ_OUTPUT_FILE = "simulatedReads"; //file to generate the reads from
//    private static final int NUMBER_OF_READS = 1000;     // Number of reads to be generated
//    private static final int READ_SIZE = 125;     // Read size
//    private static final int FRAGMENT_SIZE = 500;     // Size of the fragment (read 1 + reads 2 + space in between, which can be negative)
//    private static final double ERROR_RATE = 0.01; // Probability of a indel or substitution


    public static void main(String[] args) throws JSAPException {
        //Parse command line arguments:
        JSAP jsap = new JSAP();

        FlaggedOption argInputFile = new FlaggedOption("inputFile")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(true)
                .setShortFlag('i')
                .setLongFlag("input");

        FlaggedOption argOutputFile = new FlaggedOption("output")
                        .setStringParser(JSAP.STRING_PARSER)
                        .setRequired(true)
                        .setShortFlag('o')
                        .setLongFlag("output");

        FlaggedOption argReadNumber = new FlaggedOption("readNumber")
                        .setStringParser(JSAP.INTEGER_PARSER)
                        .setDefault(String.valueOf(1000))
                        .setRequired(true)
                        .setShortFlag('n')
                        .setLongFlag(JSAP.NO_LONGFLAG);

        FlaggedOption argReadSize = new FlaggedOption("readSize")
                        .setStringParser(JSAP.INTEGER_PARSER)
                        .setDefault(String.valueOf(125))
                        .setRequired(true)
                        .setShortFlag('s')
                        .setLongFlag(JSAP.NO_LONGFLAG);

        FlaggedOption argFragmentSize = new FlaggedOption("fragmentSize")
                        .setStringParser(JSAP.INTEGER_PARSER)
                        .setDefault(String.valueOf(500))
                        .setRequired(true)
                        .setShortFlag('f')
                        .setLongFlag(JSAP.NO_LONGFLAG);

        FlaggedOption argErrorRate = new FlaggedOption("errorRate")
                        .setStringParser(JSAP.DOUBLE_PARSER)
                        .setDefault(String.valueOf(0.01))
                        .setRequired(true)
                        .setShortFlag('e')
                        .setLongFlag(JSAP.NO_LONGFLAG);


        jsap.registerParameter(argInputFile);
        jsap.registerParameter(argOutputFile);
        jsap.registerParameter(argReadNumber);
        jsap.registerParameter(argReadSize);
        jsap.registerParameter(argFragmentSize);
        jsap.registerParameter(argErrorRate);

        JSAPResult config = jsap.parse(args);


        //Parse Fasta file
        ParseFastaFile fastaSequence = new ParseFastaFile(config.getString("inputFile"));
        fastaSequence.generateReverseComplement(); //Generate reverse complement sequences to get the PE reads
        ArrayList<Sequence> sequenceList = fastaSequence.getSequenceList(); //Get the list of sequences

        //Innitate the class
        generatePeReads generateMyPEReads = new generatePeReads(sequenceList, config.getString("output") ,config.getInt("readNumber"),
                config.getInt("readSize"), config.getInt("fragmentSize"), config.getDouble("errorRate"));

        //Generate reads
        try {
            generateMyPEReads.run();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
