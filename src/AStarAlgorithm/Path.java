package AStarAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<String> path; //Jalur: A-B-C
    private float f;
    private float g; // Nilai g: Dihitung dari jarak simpul n ke parent
    private float h; // Nilai heuristik: Dihitung dari jarak euclidean

    public Path(String source, String n, float g, float h){
        path = new ArrayList<>();
        path.add(source);
        path.add(n);
        this.g = g;
        this.h = h;
        this.f = this.g + this.h;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public void addPlaceToPath(String s){
        path.add(s);
    }

    public List<String> getPath(){
        return path;
    }
}
