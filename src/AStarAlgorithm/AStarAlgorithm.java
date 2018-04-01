package AStarAlgorithm;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AStarAlgorithm {
    //Atribut : Places (nama tempat) dan Matrix Ketetanggaan Berbobot
    private Path solutionPath = null;
    private ArrayList<String> places;
    private float[][] matriksBobot;
    private Coordinate[] coordinates;
    private Queue<Path> pathQueue;
    private boolean hasSolution = false;

    public boolean hasSolution() {
        return hasSolution;
    }

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
        solutionPath = null;
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

        int u = 0;
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
                System.out.println("Iterasi ke-" + u);
                src = places.indexOf(Path.getLastPlace(minPath));
                for (int j = 0; j < places.size(); j++) {
                    if (matriksBobot[src][j] != -1.0f) {
                        Path tempPath = new Path(minPath, places.get(j), matriksBobot[src][j], Coordinate.calculateDistance(coordinates[j], coordinates[dest]));
                        pathQueue.add(tempPath);
                        matriksBobot[src][j] = -1.0f;
                        matriksBobot[j][src] = -1.0f;
                        System.out.println(tempPath.printPath(true));
                        System.out.println();
                    }
                }
                u++;
            }
        }

        if (solutionPath != null){
            hasSolution = true;
        }
    }

    public String pathToString(){
        return solutionPath.printPath(false);
    }

    public void setMatriksBobot(float[][] matriksBobot) {
        this.matriksBobot = matriksBobot;
    }

    public void drawMap() {
        if (isCreateJSONSuccess()){
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            webEngine.load("http://localhost:63342/Shortest-Path/html/location.html?_ijt=37f5kprohf7h6jead5sdfvm2c6");

            Stage stage = new Stage();
            stage.setTitle("Direction from: " + solutionPath.getPath().get(0) + " to " + solutionPath.getPath().get(solutionPath.getPath().size() - 1));
            Scene scene = new Scene(webView, 400, 420);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }

    private boolean isCreateJSONSuccess() {
        if (hasSolution){
            JSONObject featureCollection = new JSONObject();
            featureCollection.put("type", "FeatureCollection");
            JSONArray features = new JSONArray();
            JSONObject feature;
            JSONObject geometry;
            JSONArray coors;

            //Add point
            for (int i = 0; i < places.size(); i++){
                //Object feature
                feature = new JSONObject();
                feature.put("type", "Feature");
                feature.put("id", i);

                //Add property seperti nama tempat
                JSONObject name = new JSONObject();
                name.put("name", places.get(i));
                feature.put("properties", name);

                //Object geometry
                geometry = new JSONObject();
                geometry.put("type", "Point");

                //Add latitude and longitude
                coors = new JSONArray();
                coors.add(0, coordinates[i].getLongitude());
                coors.add(1, coordinates[i].getLatitude());

                //Add coord to geometry object
                geometry.put("coordinates", coors);

                //Add geomtry to feature object
                feature.put("geometry", geometry);

                //Add to features
                features.add(feature);
            }

            //call constructor
            feature = new JSONObject();
            feature.put("type", "Feature");
            feature.put("id", places.size());

            //Add property
            JSONObject name = new JSONObject();
            name.put("name", "Route");
            feature.put("properties", name);

            //Object geometry
            geometry = new JSONObject();
            geometry.put("type", "LineString");

            //Array object coors
            coors = new JSONArray();
            for (int i = 0; i < solutionPath.getPath().size(); i++){
                JSONArray coor = new JSONArray();
                coor.add(0, coordinates[places.indexOf(solutionPath.getPath().get(i))].getLongitude());
                coor.add(1, coordinates[places.indexOf(solutionPath.getPath().get(i))].getLatitude());

                //Add latitude and longitude
                coors.add(i, coor);
            }

            //Add to geometry
            geometry.put("coordinates", coors);

            //Add geomtry to feature object
            feature.put("geometry", geometry);

            //Add to features
            features.add(feature);

            //Add to featureCollection
            featureCollection.put("features", features);

            final String dir = System.getProperty("user.dir");
            try{
                FileWriter file = new FileWriter(dir + "/json/location.json");
                file.write(featureCollection.toJSONString());
                file.flush();
                file.close();
                Thread.sleep(200);
                return true;
            } catch (IOException ex){
                return false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
