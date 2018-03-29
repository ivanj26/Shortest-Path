package AStarAlgorithm;

import java.util.ArrayList;

public class AStarAlgorithm {
    //Atribut : Places dan Matrix Bobot
    private ArrayList<String> places;
    private Float[][] matriksBobot;

    public AStarAlgorithm(ArrayList<String> places, Float[][] matriksBobot){
        this.places = places;
        this.matriksBobot = matriksBobot;
    }
}
