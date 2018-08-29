package irisflower;

public class Distance implements Comparable<Distance> {

    private float distance;
    private String speciesIris;

    public Distance() {
    }

    public Distance(float distance, String speciesIris) {
        this.distance = distance;
        this.speciesIris = speciesIris;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getSpeciesIris() {
        return speciesIris;
    }

    public void setSpeciesIris(String speciesIris) {
        this.speciesIris = speciesIris;
    }

    @Override
    public int compareTo(Distance distance) {
        float distCompare = distance.getDistance() - this.getDistance();
        int res = (int) (distCompare * 1000);

        return res;
    }

}
