package AStarAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<String> path; //Jalur: A-B-C
    private float f;
    private float g; // Nilai g: Dihitung dari jarak simpul n ke parent
    private float h; // Nilai heuristik: Dihitung dari jarak euclidean

    public Path(String source, String currentPlace, float g, float h){
        path = new ArrayList<>();
        path.add(source);
        path.add(currentPlace);
        this.g = g;
        this.h = h;
        this.f = this.g + this.h;
    }

    public Path(Path minPath, String currentPlace, float g, float h) {
        path = new ArrayList<>(minPath.path.size());
        path.addAll(minPath.path);
        path.add(currentPlace);
        this.g = minPath.g + g;
        this.h = minPath.h + h;
        this.f = this.g + this.h;
    }

    public Path() {

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

    public static String getLastPlace(Path p) {
        return p.path.get(p.path.size()-1);
    }

    public void printPath(){
        System.out.println("Cost: " + f);
        for(int i = 0; i < path.size(); i++){
            if (i != path.size() - 1)
                System.out.print(path.get(i) + " -> ");
            else
                System.out.println(path.get(i));
        }
    }
}
