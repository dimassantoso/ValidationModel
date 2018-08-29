package irisflower;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity {

    static Scanner input = new Scanner(System.in);
    static List<Iris> datasetIris = new ArrayList<>();
    static List<Iris> dataTrainingList = new ArrayList<>();
    static List<Iris> dataTestingList = new ArrayList<>();
    static List<Iris> dataResultList = new ArrayList<>();
    static int jmlSetosa = 0, jmlVersicolor = 0, jmlVirginica = 0, itrSetosa = 0, itrVersicolor = 0, itrVirginica = 0;
    static float jmlTrSetosa, jmlTrVersicolor, jmlTrVirginica;
    static int K;

    public static void main(String[] args) throws ParseException {
        String ans = null;

        do {
            int choose = Menu();
            System.out.flush();
            switch (choose) {
                case 1:
                    printDataset();
                    inputKNN();
                    holdoutMethod();
                    getDistance();
                    printResult();
                    break;
                case 2:
                    printDataset();
                    inputKNN();
                    randomSubsampling();
                    getDistance();
                    printResult();
                    break;
                case 3:
                    printDataset();
                    inputKNN();
                    kFoldCross();
                    getDistance();
                    printResult();
                    break;
                case 4:
                    printDataset();
                    inputKNN();
                    LOOCross();
                    getDistance();
                    break;
                case 5:
                    printDataset();
                    inputKNN();
                    bootstrap();
                    getDistance();
                    printResult();
                default:
                    System.out.println("Error input");
                    break;
            }
            System.out.println("Back to main menu (y/n) ?");
            ans = input.next();
            K = 0;

        } while (ans.equals("y") || ans.equals("Y"));

    }

    private static void printDataset() {
        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader("src\\resources\\DatasetIris.csv"));
            while ((line = br.readLine()) != null) {

                String[] irisData = line.split(",");

                Iris iris = new Iris();
                iris.setSepalLength(Float.parseFloat(irisData[0]));
                iris.setSepalWidth(Float.parseFloat(irisData[1]));
                iris.setPetalLength(Float.parseFloat(irisData[2]));
                iris.setPetalWidth(Float.parseFloat(irisData[3]));
                iris.setSpeciesIris(String.valueOf(irisData[4]));

                datasetIris.add(iris);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Check the data
        for (Iris iris : datasetIris) {
            System.out.println(iris.getSepalLength() + "\t"
                    + iris.getSepalWidth() + "\t"
                    + iris.getPetalLength() + "\t"
                    + iris.getPetalWidth() + "\t"
                    + iris.getSpeciesIris());
        }
    }

    private static int Menu() {
        System.out.println("\nValidation Model : ");
        System.out.println("1. Holdout Method\n"
                + "2. Random Subsampling\n"
                + "3. K-Fold Cross Validation\n"
                + "4. Leave-one-out Cross Validation\n"
                + "5. Bootstrap");
        System.out.print("Your choose : ");
        int my_menu = input.nextInt();

        return my_menu;
    }

    private static void splitData() {
        //Data of species
        for (Iris iris : datasetIris) {
            switch (iris.getSpeciesIris()) {
                case "Iris-setosa":
                    jmlSetosa++;
                    break;
                case "Iris-versicolor":
                    jmlVersicolor++;
                    break;
                case "Iris-virginica":
                    jmlVirginica++;
                    break;
            }
        }

        //Find number of training data
        jmlTrSetosa = Math.round((((float) jmlSetosa / 100) * 80));
        jmlTrVersicolor = Math.round((((float) jmlVersicolor / 100) * 80));
        jmlTrVirginica = Math.round((((float) jmlVirginica / 100) * 80));

        //Split training and testing data
        for (Iris iris : datasetIris) {
            switch (iris.getSpeciesIris()) {
                case "Iris-setosa":
                    if (itrSetosa < jmlTrSetosa) {
                        dataTrainingList.add(iris);
                    } else {
                        dataTestingList.add(iris);
                    }
                    itrSetosa++;
                    break;
                case "Iris-versicolor":
                    if (itrVersicolor < jmlTrVersicolor) {
                        dataTrainingList.add(iris);
                    } else {
                        dataTestingList.add(iris);
                    }
                    itrVersicolor++;
                    break;
                case "Iris-virginica":
                    if (itrVirginica < jmlTrVirginica) {
                        dataTrainingList.add(iris);
                    } else {
                        dataTestingList.add(iris);
                    }
                    itrVirginica++;
                    break;
            }
        }
    }

    //Input k;
    private static void inputKNN() {
        System.out.println("\nk-NN");
        System.out.print("Input k : ");
        Scanner kNN = new Scanner(System.in);
        K = input.nextInt();
    }

    //Get Distance Area
    private static void getDistance() {
        List<Distance> distances = new ArrayList<>(K);

        for (int i = 0; i < K; i++) {
            distances.add(i, new Distance(1000, ""));
        }

        for (Iris dataTesting : dataTestingList) {
            for (int i = 0; i < K; i++) {
                distances.set(i, new Distance(1000, ""));
            }
            for (Iris dataTraining : dataTrainingList) {
                Collections.sort(distances);
                float tmpDistance = (float) Math.sqrt(Math.pow(dataTesting.getSepalLength() - dataTraining.getSepalLength(), 2)
                        + Math.pow(dataTesting.getSepalWidth() - dataTraining.getSepalWidth(), 2)
                        + Math.pow(dataTesting.getPetalLength() - dataTraining.getPetalLength(), 2)
                        + Math.pow(dataTesting.getPetalWidth() - dataTraining.getPetalWidth(), 2));

                if (tmpDistance < distances.get(0).getDistance()) {
                    //Log.i("hmmm", "getDistance: " + distances.toString());
                    distances.set(0, new Distance(tmpDistance, dataTraining.getSpeciesIris()));
                    //Log.i("hmmm", "getDistance: " + distances.toString());
                    //Log.i("hmmm", "=================================");
                }
            }
            List<String> nearestSpecies = new ArrayList<>();
            for (Distance dist : distances) {
                nearestSpecies.add(dist.getSpeciesIris());
            }
            dataResultList.add(new KNN(nearestSpecies, dataTesting.getSepalLength(), dataTesting.getSepalWidth(), dataTesting.getPetalLength(), dataTesting.getPetalWidth(), FindWinner(distances)));
        }
    }

    //Find Winner
    private static String FindWinner(List<Distance> distances) {
        int[] species = {0, 0, 0};

        for (Distance distance : distances) {
            switch (distance.getSpeciesIris()) {
                case "Iris-setosa":
                    species[0]++;
                    break;
                case "Iris-versicolor":
                    species[1]++;
                    break;
                case "Iris-virginica":
                    species[2]++;
                    break;
            }
        }

        int res = 0;
        int max = 0;
        String speciesIris = "";

        for (int i = 0; i < species.length; i++) {
            if (max < species[i]) {
                max = species[i];
                res = i + 1;
            }
            if (res == 1) {
                speciesIris = "Iris-setosa";
            } else if (res == 2) {
                speciesIris = "Iris-versicolor";
            } else {
                speciesIris = "Iris-virginica";
            }
        }
        return speciesIris;
    }

    //Print Result Area
    private static void printResult() {
        System.out.println("Classification Result");
        for (Iris iris : dataResultList) {
            System.out.println(iris.getSepalLength() + "\t"
                    + iris.getSepalWidth() + "\t"
                    + iris.getPetalLength() + "\t"
                    + iris.getPetalWidth() + "\t"
                    + iris.getSpeciesIris());
        }
        findError();
    }

    //Find Error
    private static void findError() {
        int error = 0;
        for (int i = 0; i < dataTestingList.size(); i++) {
            if (!dataTestingList.get(i).getSpeciesIris().equals(dataResultList.get(i).getSpeciesIris())) {
                error++;
            }
        }
        System.out.println("\nTotal Error : " + error + "/" + dataTestingList.size());
        System.out.println("Persentase Error : " + (float) error / dataTestingList.size() * 100 + "%");
    }

    private static void holdoutMethod() {
        System.out.println("\nHoldout Method");
        System.out.print("Input Size of Data Traing (%) : ");
        int dTraining = input.nextInt();
        //Data of species
        for (Iris iris : datasetIris) {
            switch (iris.getSpeciesIris()) {
                case "Iris-setosa":
                    jmlSetosa++;
                    break;
                case "Iris-versicolor":
                    jmlVersicolor++;
                    break;
                case "Iris-virginica":
                    jmlVirginica++;
                    break;
            }
        }

        //Find number of training data
        jmlTrSetosa = Math.round((((float) jmlSetosa / 100) * dTraining));
        jmlTrVersicolor = Math.round((((float) jmlVersicolor / 100) * dTraining));
        jmlTrVirginica = Math.round((((float) jmlVirginica / 100) * dTraining));

        //Split training and testing data
        for (Iris iris : datasetIris) {
            switch (iris.getSpeciesIris()) {
                case "Iris-setosa":
                    if (itrSetosa < jmlTrSetosa) {
                        dataTrainingList.add(iris);
                    } else {
                        dataTestingList.add(iris);
                    }
                    itrSetosa++;
                    break;
                case "Iris-versicolor":
                    if (itrVersicolor < jmlTrVersicolor) {
                        dataTrainingList.add(iris);
                    } else {
                        dataTestingList.add(iris);
                    }
                    itrVersicolor++;
                    break;
                case "Iris-virginica":
                    if (itrVirginica < jmlTrVirginica) {
                        dataTrainingList.add(iris);
                    } else {
                        dataTestingList.add(iris);
                    }
                    itrVirginica++;
                    break;
            }
        }
    }

    private static void randomSubsampling() {
        System.out.println("\nRandom Subsampling");
        System.out.print("Number of Testing Data : ");
        int dTesting = input.nextInt();
        dataTrainingList.addAll(datasetIris);

        for (int i = 0; i < dTesting; i++) {
            Random rand = new Random();
            int randomData = rand.nextInt(dataTrainingList.size() - 1);
            Iris dataTemp = dataTrainingList.remove(randomData);
            dataTestingList.add(dataTemp);
        }

    }

    private static void kFoldCross() {
        //Iris dataTemp = null;
        System.out.println("\nK-Fold Cross Validation");
        System.out.print("Number of Fold : ");
        int fold = input.nextInt();
        
        int numberData = dataTrainingList.size()/fold;
        
        int i = 0;
        for (Iris iris : datasetIris){
            dataTrainingList.addAll(datasetIris);
            if (i!=numberData){
                Iris dataTemp = dataTrainingList.remove(i);
                dataTestingList.add(dataTemp);
                i++;
            }
            numberData++;
            dataTrainingList.clear();
            dataTestingList.clear();
        }
    }

    private static void LOOCross() {
        System.out.println("\nLeave-One-Out Cross Validation");
                
        dataResultList.clear();
        int i = 0;
        for (Iris iris : datasetIris) {
            dataTrainingList.addAll(datasetIris);
            Iris dataTemp = dataTrainingList.remove(i);
            dataResultList.add(dataTemp);
            i++;
            System.out.println("\nExperiment-"+i);
            printResult();
            findErrorLOO();
            dataTrainingList.clear();
            dataResultList.clear();
        } 
    }

    private static void bootstrap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void findErrorLOO() {
        int error = 0;
        for (int i = 0; i < dataTestingList.size(); i++) {
            if (!dataTestingList.get(i).getSpeciesIris().equals(dataResultList.get(i).getSpeciesIris())) {
                error++;
            }
        }
        System.out.println("\nTotal Error : " + error + "/" + dataResultList.size());
        System.out.println("Persentase Error : " + (float) error / dataResultList.size() * 100 + "%");
    }
}
