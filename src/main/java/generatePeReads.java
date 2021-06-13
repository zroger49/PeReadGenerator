import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.Math;

public class generatePeReads {

    private static ArrayList<Sequence> listOfSequences = new ArrayList<>();
    private static int numberOfreads;
    private static int readSize;
    private static int fragmentSize;
    private static double errorRate;
    private static int readCounter;
    private static ArrayList<readSequence> fReadList;
    private static ArrayList<readSequence> rReadList;
    private static String fReadOutputFile;
    private static String rReadOutputFile;

    public generatePeReads(ArrayList<Sequence> listOfSequences, String outFile, int numberOfReads, int readSize, int fragmentSize, double errorRate){
        this.listOfSequences = listOfSequences;
        this.numberOfreads = numberOfReads;
        this.readSize = readSize;
        this.fragmentSize = fragmentSize;
        this.errorRate = errorRate;
        this.readCounter = 1;
        this.fReadList = new ArrayList<>();
        this.rReadList = new ArrayList<>();
        this.fReadOutputFile = outFile + "_F.fastq";
        this.rReadOutputFile = outFile + "_R.fastq";
    }

    public static void run() throws IOException {
        for (int i = 0; i < numberOfreads; i++) {
            generate();
        }
        mutate();
        printToFile(rReadOutputFile, rReadList);
        printToFile(fReadOutputFile, fReadList);
    }


    public static void printToFile(String fileName, ArrayList<readSequence> sequenceList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (readSequence readSeq:sequenceList){
            writer.append(readSeq.getHeader());
            writer.append('\n');
            writer.append(readSeq.getSequence());
            writer.append('\n');
            writer.append(readSeq.getTrdLine());
            writer.append('\n');
            writer.append(readSeq.getQuality());
            writer.append('\n');
            }
        writer.close();
    }

    public static void generate(){
        Random rand = new Random();
        int sequenceNumber = rand.nextInt(listOfSequences.size()/2);  //Get foward sequence, max is not inclusive
        int reverseSequeceNumber = sequenceNumber + listOfSequences.size()/2;
        int startPos = rand.nextInt(listOfSequences.get(sequenceNumber).getSequence().length() - fragmentSize); //This number is exclusive
        int rStartPos = listOfSequences.get(reverseSequeceNumber).getSequence().length() - (fragmentSize + startPos);
        String fRead = listOfSequences.get(sequenceNumber).getSequence().substring(startPos, startPos + readSize); // end is exclusive
        String rRead = listOfSequences.get(reverseSequeceNumber).getSequence().substring(rStartPos, rStartPos + readSize); //end is exclusive

        String fheader = "@" + "RANDOM:" + listOfSequences.get(sequenceNumber).getHeader() + ":" + String.valueOf(startPos + 1) + "-"  +
                String.valueOf(startPos + readSize) +  ":FOWARD-READ-NUMBER-" + readCounter;
        String rheader = "@" + "RANDOM:" + listOfSequences.get(sequenceNumber).getHeader() + ":" + String.valueOf(startPos + fragmentSize) + "-"  +
                String.valueOf(startPos + fragmentSize - readSize) +  ":REVERSE-READ-NUMBER-" + readCounter;

        String qualityString = "~".repeat(fRead.length());

        fReadList.add(new readSequence(fheader, fRead, "+", qualityString));
        rReadList.add(new readSequence(rheader, rRead, "+", qualityString));
        readCounter++;
    }

    public static void mutate(){
        /* Mutate reads by computing the number of expected errors
         and randomly interating through the read lists*/

        //Calculate the number of expected errors
        double expectedErrorNumber = errorRate * numberOfreads * 2 * readSize;
        int expectedErrorNumberRounded = (int) Math.round(expectedErrorNumber);

        Random rand = new Random();

        //interate through the number of expected erros
        for (int i = 0; i < expectedErrorNumber; i++) {
            int randStrand = rand.nextInt(1);
            int randonSequence = rand.nextInt(numberOfreads);
            int randonPost = rand.nextInt(readSize);

            StringBuilder mutableString = new StringBuilder(); //Using StringBuilders because it is mutable
            readSequence sequenceToBeMutated;
            if (randStrand == 0){ //Get the reads
                sequenceToBeMutated = fReadList.get(randonSequence);
            }
            else{
                sequenceToBeMutated = rReadList.get(randonSequence);
            }
            mutableString.append(sequenceToBeMutated.getSequence());

            char possibleMutation[];

            switch (mutableString.charAt(randonPost)){
                case 'A':
                    possibleMutation = new char[]{'T', 'G', 'C'};
                case 'T':
                    possibleMutation = new char[]{'A', 'G', 'C'};
                case 'G':
                    possibleMutation = new char[]{'A', 'T', 'C'};
                case 'C':
                    possibleMutation = new char[]{'A', 'G', 'T'};
                default:
                    possibleMutation = new char[]{'A', 'G', 'T', 'C'};
            }

            //Mutate Sequence
            mutableString.setCharAt(randonPost, possibleMutation[rand.nextInt(3)]);

            sequenceToBeMutated.setSequence(mutableString.toString());

            if (randStrand == 0){
                fReadList.set(randonSequence, sequenceToBeMutated);
            }
            else{
                rReadList.set(randonSequence, sequenceToBeMutated);
            }

        }
    }


    //Getters and Setter
    public static ArrayList<Sequence> getListOfSequences() {
        return listOfSequences;
    }

    public static int getNumberOfreads() {
        return numberOfreads;
    }

    public static int getReadSize() {
        return readSize;
    }

    public static int getFragmentSize() {
        return fragmentSize;
    }

    public static double getErrorRate() {
        return errorRate;
    }

    public static void setListOfSequences(ArrayList<Sequence> listOfSequences) {
        generatePeReads.listOfSequences = listOfSequences;
    }

    public static void setNumberOfreads(int numberOfreads) {
        generatePeReads.numberOfreads = numberOfreads;
    }

    public static void setReadSize(int readSize) {
        generatePeReads.readSize = readSize;
    }

    public static void setFragmentSize(int fragmentSize) {
        generatePeReads.fragmentSize = fragmentSize;
    }

    public static void setErrorRate(double errorRate) {
        generatePeReads.errorRate = errorRate;
    }
}
