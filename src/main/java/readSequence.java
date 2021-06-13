public class readSequence extends Sequence{

    private String quality;
    private String trdLine;

    public readSequence(String header, String sequence, String trdLine, String quality) {
        super(sequence, header);
        this.trdLine = trdLine;
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getTrdLine() {
        return trdLine;
    }

    public void setTrdLine(String trdLine) {
        this.trdLine = trdLine;
    }
}
