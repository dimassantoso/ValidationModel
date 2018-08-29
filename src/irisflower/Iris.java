package irisflower;

public class Iris {

    private float sepalLength;
    private float sepalWidth;
    private float petalLength;
    private float petalWidth;
    private String speciesIris;

    public Iris() {
    }

    public Iris(float sepalLength, float sepalWidth, float petalLength, float petalWidth, String speciesIris) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.speciesIris = speciesIris;
    }

    public float getSepalLength() {
        return sepalLength;
    }

    public void setSepalLength(float sepalLength) {
        this.sepalLength = sepalLength;
    }

    public float getSepalWidth() {
        return sepalWidth;
    }

    public void setSepalWidth(float sepalWidth) {
        this.sepalWidth = sepalWidth;
    }

    public float getPetalLength() {
        return petalLength;
    }

    public void setPetalLength(float petalLength) {
        this.petalLength = petalLength;
    }

    public float getPetalWidth() {
        return petalWidth;
    }

    public void setPetalWidth(float petalWidth) {
        this.petalWidth = petalWidth;
    }

    public String getSpeciesIris() {
        return speciesIris;
    }

    public void setSpeciesIris(String speciesIris) {
        this.speciesIris = speciesIris;
    }

}
