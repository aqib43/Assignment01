package sample;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Controller {

//reading the file word by word, and counting how many times a word appears in the directory
    public ArrayList<TestFile> Training(File file) throws FileNotFoundException {
        ArrayList<TestFile> files = new ArrayList();
        //Ham Section for training
        File directoryPathHam = new File(String.valueOf(file));
        File filesListHam[] = directoryPathHam.listFiles();

        ArrayList<TestFile> allHamFilesList = new ArrayList();
        ArrayList<TestFile> allSpamFilesList = new ArrayList();


        //checks if file is a directory
        if(file.isDirectory()){
            //parse each file inside the directory
            File[] content = file.listFiles();
            for(File current: content){
                parseFile(current);
            }
        }else{
            Scanner scanner = new Scanner(file);
            // scanning token by token
            while (scanner.hasNext()){
                String  token = scanner.next();
                if (isValidWord(token)){
                    wordCounter(token);
                }
            }
        }

        //wordCheckerHam function sets the parameter string currentWord and goes through the data to recieve the word
        public void wordCheckerHam (String currentWord){
            for (int i = 0; i < allHamFilesList.get(0).currentWord().size(); i++) {
                fileCount = 0;
                for (int j = 1; j < allHamFilesList.size(); j++) {

                    for (int k = 0; k < allHamFilesList.get(j).currentWord().size(); k++) {


                        if (allHamFilesList.get(0).currentWord().get(i).toString().equals(allHamFilesList.get(j).currentWord().get(k).toString())) {
                            //counts the frequency of the word
                            fileCount++;
                            word = allHamFilesList.get(0).currentWord().get(i).toString();
                        }

                    }

                }
                //puts the word and the frequency that appears into another tree map
                if (fileCount > 0 && word != null) {
                    trainHamFreq.put(word, fileCount);
                }
            }
        }
        //tested


        TreeMap<String, Integer> trainHamFreq = new TreeMap<String, Integer>();
        TreeMap<String, Integer> trainSpamFreq = new TreeMap<String, Integer>();
        int fileCount = 0;
        String word = null;
        ArrayList<String> matchingWords = new ArrayList();


//Tested
        //wordCheckerSpam function sets the parameter string currentWord and goes through the data to recieve the word
        public void wordCheckerSpam (String currentWord){
            word = null;
            for (int i = 0; i < allSpamFilesList.get(0).currentWords().size(); i++) {
                fileCount = 0;
                for (int j = 1; j < allSpamFilesList.size(); j++) {

                    for (int k = 0; k < allSpamFilesList.get(j).currentWords().size(); k++) {

                        if (allSpamFilesList.get(0).currentWords().get(i).toString().equals(allSpamFilesList.get(j).currentWords().get(k).toString())) {
                            fileCount++;
                            word = allSpamFilesList.get(0).currentWords().get(j).toString();
                        }

                    }
                }
                //puts the word and the frequency that appears into another tree map
                if (fileCount > 0 && word != null) {
                    trainSpamFreq.put(word, fileCount);
                }
            }

            //tree maps to store the probabilities from given formulas
            TreeMap<String, Double> probabilities = new TreeMap<String, Double>();
            TreeMap<String, Double> probabilities_w_s = new TreeMap<String, Double>();
            TreeMap<String, Double> probabilities_w_h = new TreeMap<String, Double>();


            //calculating spam probabilities:
            for (int i = 0; i < trainSpamFreq.size(); i++) {
                String key;
                key = (String) trainSpamFreq.keySet().toArray()[i];
                int files_s;
                files_s = trainSpamFreq.get(key);
                double prob;
                prob = (files_s / filesListSpam.length);
                probabilities_w_s.put(key, prob);

            }
            //calculating ham probabilities
            for (int i = 0; i < trainHamFreq.size(); i++) {
                String key;
                key = (String) trainHamFreq.keySet().toArray()[i];
                int files_s;
                files_s = trainHamFreq.get(key);
                double prob;
                prob = (files_s / filesListHam.length);
                probabilities_w_h.put(key, prob);
            }

            //this tree map is used to temporarily store values
            TreeMap<String, Double> p = new TreeMap<String, Double>();
            //loops through the tree maps and updates the keys using its associated probability
            for (int i = 0; i < probabilities_w_s.size(); i++) {
                String keys;
                keys = (String) probabilities_w_s.keySet().toArray()[i];
                String keyh;
                keyh = (String) probabilities_w_h.keySet().toArray()[i];

                double prob;
                prob = probabilities_w_s.get(keys) / (probabilities_w_s.get(keys) + probabilities_w_h.get(keyh));


                probabilities.put(keyh, prob);

            }
            files = fileProbabilty(probabilities, file);
            return files;
        }

        //displaying the probabilities and testing the threshold for whether or not is the file a spam or ham
        static ArrayList<TestFile> fileProbabilty HashMap<Object, Object> probabilities;
        (TreeMap < String, Double > probabilities, File file){
            File directoryPathHam = new File(file);
            File filesListHam[] = directoryPathHam.listFiles();
            File directoryPathSpam = new File(file);
            File filesListSpam[] = directoryPathSpam.listFiles();

            ArrayList<TestFile> files = new ArrayList<TestFile>();
            //get name of ham file
            for (int i = 0; i < filesListHam.length; i++) {
                TestFile f;
                f = new TestFile(filesListHam[i].getName(), "0.0000", "Ham");

                files.add(f);
            }
            //get name for spam file
            for (int i = 0; i < filesListSpam.length; i++) {
                TestFile f;
                f = new TestFile(filesListSpam[i].getName(), "0.0000", "Spam");

                files.add(f);
            }
            ArrayList<Double> p = new ArrayList();
            for (int i = 0; i < probabilities.size(); i++) {
                String keys;
                keys = (String) probabilities.keySet().toArray()[i];
                //calculating probability of a spam file
                double sigma;
                sigma = Math.log((1 - probabilities.get(keys)) - (Math.log(probabilities.get(keys))));
                double prob = 0;
                prob = 1 / (1 + sigma);
                //initialize probabililty to 0
                if (prob < 0) {
                    prob = 0;
                } else if (!(prob > 0)) {
                    prob = 0;
                }
                String w = Double.toString(prob);
                TestFile f;
                //getting the file name and its path for ham file or spam file
                f = new TestFile(filesListHam[i].getName(), w, filesListHam[i].getAbsolutePath());
                if (f.getActualClass().equals("ham")) {
                    f.setActualClass("Ham");
                } else {
                    f.setActualClass("Spam");
                }
                files.add(f);
            }
            return files;
        }
    }
}




