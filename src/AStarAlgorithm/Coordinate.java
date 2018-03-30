package AStarAlgorithm;

public class Coordinate {
    private double latitude;
    private double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Menghitung jarak diantara 2 point dengan diberikan lintang dan bujur
     * @param coor1 Source place berisi bujur dan lintang
     * @param coor2 Destination place berisi bujur dan lintang
     * @return Jarak dalam meter
    */
    public static float calculateDistance(Coordinate coor1, Coordinate coor2){
        final int R = 6371; //Radius of earth

        double latDistance = Math.toRadians(coor2.latitude - coor1.latitude);
        double lonDistance = Math.toRadians(coor2.longitude - coor1.longitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(coor1.latitude)) * Math.cos(Math.toRadians(coor2.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (float) (R * c * 1000);
    }
}
