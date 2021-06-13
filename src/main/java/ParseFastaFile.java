
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


//Simple impletation to parse the fasta file into a list
//Consider replace this and the sequence java class with BioJava

public class ParseFastaFile {

    private final ArrayList<Sequence> sequenceList = new ArrayList<>();

    public ParseFastaFile(String fastaFile){
        File myFile = new File(fastaFile);

        try {
            Scanner myReader = new Scanner(myFile);
            String header = null;
            StringBuilder sequence = new StringBuilder();
            boolean headerParsed = false;

            while (myReader.hasNextLine()){
                String currLine = myReader.nextLine();
                if (currLine.startsWith(">") & headerParsed == false){
                    header = currLine.substring(1);
                    headerParsed = true;
                }

                else if (headerParsed == true & currLine.startsWith(">")){
                    Sequence GenomeScaffold = new Sequence(sequence.toString(), header);
                    this.sequenceList.add(GenomeScaffold);
                    sequence = new StringBuilder();
                    header = currLine.substring(1);
                }

                else if (headerParsed == true){
                    sequence.append(currLine);
                }

            }
            Sequence GenomeScaffold = new Sequence(sequence.toString(), header);
            this.sequenceList.add(GenomeScaffold);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateReverseComplement(){
        //For each sequence generate the Reverse Complement
        int len = this.sequenceList.size();
        for (int i = 0; i < len; i++){
            this.sequenceList.add(this.reverse(sequenceList.get(i)));
        }
    }


    public Sequence reverse(Sequence inputSequence){
        StringBuilder outputSequence = new StringBuilder();
        for (int i = inputSequence.getSequence().length() - 1; i >= 0;  i--){
            outputSequence.append(String.valueOf(reverseNucl(inputSequence.getSequence().charAt(i))));
        }
        Sequence outputSeq = new Sequence(outputSequence.toString(), inputSequence.getHeader() + " reverse");
        return outputSeq;
    }

    public char reverseNucl(char inputChar){
        char outputChar;
        switch (inputChar){
            case 'A':
                outputChar = new Character('T');
                break;
            case 'T':
                outputChar = new Character('A');
                break;
             case 'G':
                outputChar = new Character('C');
                break;
             case 'C':
                outputChar = new Character('G');
                break;

            default:
                outputChar = inputChar;
        }
    return outputChar;
    }

    public ArrayList<Sequence> getSequenceList(){
        return this.sequenceList;
    }



}
