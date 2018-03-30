package AStarAlgorithm;

import java.util.*;

public class AStarAlgorithm {
    //Atribut : Places (nama tempat) dan Matrix Ketetanggaan Berbobot
    private ArrayList<String> places;
    private float[][] matriksBobot;
    private Coordinate[] coordinates;
    private Queue<Path> pathQueue;
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

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinate[] coordinates) {
        this.coordinates = coordinates;
    }

    public AStarAlgorithm(ArrayList<String> places, float[][] matriksBobot, Coordinate[] coordinates){
        this.places = places;
        this.coordinates = coordinates;
        this.matriksBobot = matriksBobot;
    }

    /**
     * @param source      Simpul/Tempat awal
     * @param destination Simpul/Tempat tujuan
     */
    public void findShortestPath(String source, String destination) {
        Path solutionPath = null;
        pathQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.getF() > o2.getF()) {
                return 1;
            } else if (o1.getF() < o2.getF()) {
                return -1;
            }
            return 0;
        });
        int src = places.indexOf(source);
        int dest = places.indexOf(destination);

        //Add semua jalur dimana suatu tempat yang bertetangga dengan tempat awal
        //pathQueue sudah terurut membesar
        for (int j = 0; j < places.size(); j++) {
            if (matriksBobot[src][j] != -1.0f) {
                Path path = new Path(places.get(src), places.get(j), matriksBobot[src][j], Coordinate.calculateDistance(coordinates[j], coordinates[dest]));
                pathQueue.add(path);
                matriksBobot[src][j] = -1.0f;
                matriksBobot[j][src] = -1.0f;
            }
        }

        while (!pathQueue.isEmpty()) {
            //minPath punya ongkos f lebih kecil dibanding fungsi f pada path lain
            Path minPath = pathQueue.remove();
            if (places.indexOf(Path.getLastPlace(minPath)) == dest) {
                if (solutionPath == null) {
                    solutionPath = minPath;
                    if (!pathQueue.isEmpty()) {
                        Path tempPath = pathQueue.remove();
                        if (solutionPath.getF() >= tempPath.getF()) {
                            pathQueue.add(tempPath);
                        } else {
                            pathQueue.clear();
                        }
                    }
                }
            } else {
                src = places.indexOf(Path.getLastPlace(minPath));
                for (int j = 0; j < places.size(); j++) {
                    if (matriksBobot[src][j] != -1.0f) {
                        Path tempPath  = new Path(minPath, places.get(j), matriksBobot[src][j], Coordinate.calculateDistance(coordinates[j], coordinates[dest]));
                        pathQueue.add(tempPath);
                        matriksBobot[src][j] = -1.0f;
                        matriksBobot[j][src] = -1.0f;
                        tempPath.printPath();
                        System.out.println();
                    }
                }
            }
        }

        solutionPath.printPath();
    }

    public void setMatriksBobot(float[][] matriksBobot) {
        this.matriksBobot = matriksBobot;
    }
}
