
public class Sequence {

    private String sequence;
    private String header;


    public Sequence(String sequence, String header) {
        this.sequence = sequence;
        this.header = header;
    }


    public void setSequence(String newSequence){
        sequence = newSequence;
    }


    public String getSequence() {
        return this.sequence;
    }

    public String getHeader() {
        return this.header;
    }

    @Override
    public String toString() {
        return this.getSequence();
    }
}
