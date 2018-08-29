package irisflower;

import java.util.List;

public class KNN extends Iris {

    private List<String> nearestSpecies;

    public KNN(List<String> nearestSpecies, float sepalLength, float sepalWidth, float petalLength, float petalWidth, String speciesIris) {
        super(sepalLength, sepalWidth, petalLength, petalWidth, speciesIris);
        this.nearestSpecies = nearestSpecies;
    }

    public KNN(Iris iris, List<String> nearestSpecies) {
        super(iris.getPetalLength(), iris.getPetalWidth(), iris.getSepalLength(), iris.getSepalWidth(), iris.getSpeciesIris());
        this.nearestSpecies = nearestSpecies;
    }

    public List<String> getNearestSpecies() {
        return nearestSpecies;
    }

    public void setNearestSpecies(List<String> nearestSpecies) {
        this.nearestSpecies = nearestSpecies;
    }

    public void addNearestKelas(String speciesIris) {
        this.nearestSpecies.add(speciesIris);
    }
    public String speciesToString() {
        String res ="";
        int itr = 0;

        for (String species : nearestSpecies) {
            res += species + " ";
            itr++;
        }

        return res;
    }

}
