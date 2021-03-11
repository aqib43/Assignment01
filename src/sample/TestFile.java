package sample;
import java.text.DecimalFormat;


public class TestFile {
    private String fileName;
    private String spamProbability;
    private String actualClass;

    public TestFile(String fileName, String spamProbability, String actualClass) {
        this.fileName=(fileName);
        this.spamProbability=(spamProbability);
        this.actualClass=(actualClass);
    }

    public TestFile() {
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName=(fileName);
    }

    public String getSpamProbability() {
        return spamProbability;
    }

    public void setSpamProbability(String spamProbability) {
        this.spamProbability=(spamProbability);
    }

    public String getActualClass() {
        return actualClass;
    }

    public void setActualClass(String actualClass) {
        this.actualClass=(actualClass);
    }

    public String getSpamRounded() {
        DecimalFormat df=new DecimalFormat("0.00000");
        return df.format(this.spamProbability);
    }

}