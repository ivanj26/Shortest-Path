package controller;

import AStarAlgorithm.AStarAlgorithm;
import AStarAlgorithm.Coordinate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    public Button directionButton;
    public ChoiceBox<String> sourceChoiceBox;
    public ChoiceBox<String> destChoiceBox;
    private AStarAlgorithm aStarAlgorithm;
    private float[][] mat;

    public void onAboutClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About program");

        alert.setContentText("Author I\t=\t1. Ivan Jonathan\n" +
                             "Author II\t=\t2. Alfian\n" +
                             "NIM I\t=\t13516059\n" +
                             "NIM II\t=\t13516104\n\n"
        );
        alert.setHeaderText("Several Information");
        alert.show();
    }

    public void onGetDirection(ActionEvent actionEvent) throws IOException {
        if (sourceChoiceBox.getValue().equals(destChoiceBox.getValue())){
            JOptionPane.showMessageDialog(null, "Source is the same place as Destination\nPlease choose different destination place.", "Message", JOptionPane.INFORMATION_MESSAGE );
        } else {
            Runtime.getRuntime().exec("clear");
            aStarAlgorithm.setMatriksBobot(deepCopy(mat));
            aStarAlgorithm.findShortestPath(sourceChoiceBox.getValue(), destChoiceBox.getValue());
        }
    }

    public void onChooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Choose file..");
        fileChooser.getExtensionFilters().add(extensionFilter);
        File selectedFile =  fileChooser.showOpenDialog(null);

        if (selectedFile != null && selectedFile.exists()){
            readFile(selectedFile.getPath());
        }
    }

    private void readFile(String path) {
        try{
            StringBuffer stringBuffer = new StringBuffer();
            ArrayList<String> places = new ArrayList<>();
            InputStream in = new FileInputStream(path);
            int i;
            char c;

            //Baca vertex nya
            while ((i = in.read()) != '\n') {
                c = (char) i;
                if (c != ' '){
                    stringBuffer.append(c);
                } else {
                    places.add(stringBuffer.toString());
                    stringBuffer.setLength(0);
                }
            }

            places.add(stringBuffer.toString());
            stringBuffer.setLength(0);
            int rows = places.size();
            int cols = rows;
            in.close();

            //Matriks berbobot siap diassign
            in = new FileInputStream(path);
            mat = new float[rows][cols];

            int k = 0;
            int l = 0;

            while ((i = in.read()) != '\n') {
                //do nothing, lewatin line pertama yang isinya tempat2
            }

            while ((char) (i = in.read()) != '&'){
                // converts integer to character
                c = (char) i;

                if (c != ' ' && c != '\n') {
                    stringBuffer.append(c);
                } else if (c == ' ') {
                    mat[k][l] = Float.parseFloat(stringBuffer.toString());
                    l++;
                    stringBuffer.setLength(0);
                } else {
                    mat[k][l] = Float.parseFloat(stringBuffer.toString());
                    stringBuffer.setLength(0);
                    k++;
                    l = 0;
                }
            }
            mat[k][l] = Float.parseFloat(stringBuffer.toString());
            stringBuffer.setLength(0);
            in.read();

            //Baca lintang dan bujur
            Coordinate[] coordinates = new Coordinate[rows];
            k = 0;
            double lat = 0;
            double lon;
            while ((i = in.read()) != -1){
                c = (char) i;
                if (c != ' ' && c != '\n') {
                    stringBuffer.append(c);
                } else if (c == ' ') {
                    lat = Double.parseDouble(stringBuffer.toString());
                    stringBuffer.setLength(0);
                } else {
                    lon = Double.parseDouble(stringBuffer.toString());
                    stringBuffer.setLength(0);
                    coordinates[k] = new Coordinate(lat, lon);
                    k++;
                }
            }
            in.close();

            sourceChoiceBox.setItems(FXCollections.observableArrayList(places));
            destChoiceBox.setItems(FXCollections.observableArrayList(places));
            sourceChoiceBox.setValue(places.get(0));
            destChoiceBox.setValue(places.get(0));
            directionButton.setDisable(false);

            aStarAlgorithm = new AStarAlgorithm(places, deepCopy(mat), coordinates);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private float[][] deepCopy(float[][] mat) {
        final float[][] result = new float[mat.length][];
        for (int i = 0; i < mat.length; i++) {
            result[i] = Arrays.copyOf(mat[i], mat[i].length);
            // For Java versions prior to Java 6 use the next:
            // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }
        return result;
    }
}
