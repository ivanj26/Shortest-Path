package AStarAlgorithm;

import java.util.ArrayList;

public class AStarAlgorithm {
    //Atribut : Places (nama tempat) dan Matrix Ketetanggaan Berbobot
    private ArrayList<String> places;
    private Float[][] matriksBobot;
    private Coordinate[] coordinates;

    public String getNameOfPlace(int i){
        return places.get(i);
    }

    public int getNumberOfPlace(String place){
        return places.indexOf(place);
    }

    public ArrayList<String> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<String> places) {
        this.places = places;
    }

    public Float[][] getMatriksBobot() {
        return matriksBobot;
    }

    public void setMatriksBobot(Float[][] matriksBobot) {
        this.matriksBobot = matriksBobot;
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public AStarAlgorithm(ArrayList<String> places, Float[][] matriksBobot, Coordinate[] coordinates){
        this.places = places;
        this.coordinates = coordinates;
        this.matriksBobot = matriksBobot;
    }
}
