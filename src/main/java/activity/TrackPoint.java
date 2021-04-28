package activity;

public class TrackPoint {

    private final Coordinate coordinate;
    private final double elevation;

    public TrackPoint(Coordinate coordinate, double elevation) {
        this.coordinate = coordinate;
        this.elevation = elevation;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getElevation() {
        return elevation;
    }

    public double getDistanceFrom(TrackPoint trackPoint) {
        final int R = 6371; // Radius of the earth
        double latDistance = getLatDistance(trackPoint);
        double lonDistance = getLonDistance(trackPoint);

        double a = calculate_a(trackPoint, latDistance, lonDistance);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = this.elevation - trackPoint.elevation;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }

    private double calculate_a(TrackPoint trackPoint, double latDistance, double lonDistance) {
        return Math.pow(Math.sin(latDistance / 2), 2)
                + Math.cos(Math.toRadians(this.coordinate.getLatitude())) *
                 Math.cos(Math.toRadians(trackPoint.coordinate.getLatitude()))
                * Math.pow(Math.sin(lonDistance / 2), 2);
    }

    private double getLonDistance(TrackPoint trackPoint) {
        return Math.toRadians(trackPoint.coordinate.getLongitude() -
                this.coordinate.getLongitude());
    }

    private double getLatDistance(TrackPoint trackPoint) {
        return Math.toRadians(trackPoint.coordinate.getLatitude() -
                    this.coordinate.getLatitude());
    }
}
