package activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Track {

    public static final int LATITUDE_BEGIN_INDEX = 15;
    public static final int LATITUDE_END_INDEX = 25;
    public static final int LONGITUDE_BEGIN_INDEX = 32;
    public static final int LONGITUDE_END_INDEX = 42;
    public static final String ELEVATION_LINE_MARK = "<ele";
    public static final String COORDINATES_LINE_MARK = "<trkpt";
    private final List<TrackPoint> trackPoints = new ArrayList<>();

    public List<TrackPoint> getTrackPoints() {
        return new ArrayList<>(trackPoints);
    }

    public void addTrackPoint(TrackPoint trackPoint) {
        trackPoints.add(trackPoint);
    }

    public Coordinate findMaximumCoordinate() {
        if (trackPoints.size() == 0) {
            throw new IllegalArgumentException("Empty list");
        }
        Double maxLat = null;
        Double maxLon = null;
        for (TrackPoint t: trackPoints) {
            maxLat = getMaxLat(maxLat, t);
            maxLon = getMaxLon(maxLon, t);
        }
        return new Coordinate(maxLat, maxLon);
    }

    private Double getMaxLon(Double maxLon, TrackPoint t) {
        if (maxLon == null) {
            maxLon = t.getCoordinate().getLongitude();
        }
        else {
            if (maxLon < t.getCoordinate().getLongitude()) {
                maxLon = t.getCoordinate().getLongitude();
            }
        }
        return maxLon;
    }

    private Double getMaxLat(Double maxLat, TrackPoint t) {
        if (maxLat == null) {
            maxLat = t.getCoordinate().getLatitude();
        }
        else {
            if (maxLat < t.getCoordinate().getLatitude()) {
                maxLat = t.getCoordinate().getLatitude();
            }
        }
        return maxLat;
    }

    public Coordinate findMinimumCoordinate() {
        if (trackPoints.size() == 0) {
            throw new IllegalArgumentException("Empty list");
        }
        Double minLat = null;
        Double minLon = null;
        for (TrackPoint t: trackPoints) {
            minLat = getMinLat(minLat, t);
            minLon = getMinLon(minLon, t);
        }
        return new Coordinate(minLat, minLon);
    }

    private Double getMinLon(Double minLon, TrackPoint t) {
        if (minLon == null) {
            minLon = t.getCoordinate().getLongitude();
        }
        else {
            if (minLon > t.getCoordinate().getLongitude()) {
                minLon = t.getCoordinate().getLongitude();
            }
        }
        return minLon;
    }

    private Double getMinLat(Double minLat, TrackPoint t) {
        if (minLat == null) {
            minLat = t.getCoordinate().getLatitude();
        }
        else {
            if (minLat > t.getCoordinate().getLatitude()) {
                minLat = t.getCoordinate().getLatitude();
            }
        }
        return minLat;
    }

    public double getDistance() {
        if (trackPoints.size() == 0) {
            throw new IllegalArgumentException("Empty list");
        }
        if (trackPoints.size() == 1) {
            return 0;
        }
        double distance = 0;
        for (int i = 1; i < trackPoints.size(); i++){
            distance += trackPoints.get(i-1).getDistanceFrom(trackPoints.get(i));
        }
        return distance;
    }

    public double getFullDecrease() {
        if (trackPoints.size() == 0) {
            throw new IllegalArgumentException("Empty list");
        }
        if (trackPoints.size() == 1) {
            return 0;
        }
        double decrease = 0;
        for (int i = 1; i < trackPoints.size(); i++) {
            if (trackPoints.get(i - 1).getElevation() > trackPoints.get(i).getElevation()) {
                decrease += trackPoints.get(i - 1).getElevation() - trackPoints.get(i).getElevation();
            }
        }
        return decrease;
    }

    public double getFullElevation() {
        if (trackPoints.size() == 0) {
            throw new IllegalArgumentException("Empty list");
        }
        if (trackPoints.size() == 1) {
            return 0;
        }
        double elevation = 0;
        for (int i = 1; i < trackPoints.size(); i++) {
            if (trackPoints.get(i - 1).getElevation() < trackPoints.get(i).getElevation()) {
                elevation += trackPoints.get(i).getElevation() - trackPoints.get(i - 1).getElevation();
            }
        }
        return elevation;
    }
    public double getRectangleArea() {
        if (trackPoints.size() == 0) {
            throw new IllegalArgumentException("Empty list");
        }
        if (trackPoints.size() == 1) {
            return 0;
        }
        Coordinate min = findMinimumCoordinate();
        Coordinate max = findMaximumCoordinate();
        return  (max.getLongitude() - min.getLongitude()) *(max.getLatitude() - min.getLatitude());
    }

    public void loadFromGpx(InputStream is) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            Coordinate coordinate = null;
            while ((line = reader.readLine()) != null) {
                coordinate = getCoordinate(line, coordinate);
                addTrackPointWithElevation(line, coordinate);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file", ioe);
        }
    }

    private void addTrackPointWithElevation(String line, Coordinate coordinate) {
        if (line.contains(ELEVATION_LINE_MARK)) {
            double ele = Double.parseDouble(line.substring(9, line.indexOf("</")));
            TrackPoint trackPoint = new TrackPoint(coordinate, ele);
            trackPoints.add(trackPoint);
        }
    }

    private Coordinate getCoordinate(String line, Coordinate coordinate) {
        if (line.contains(COORDINATES_LINE_MARK)) {
            double lat = Double.parseDouble(line.substring(LATITUDE_BEGIN_INDEX, LATITUDE_END_INDEX));
            double lon = Double.parseDouble(line.substring(LONGITUDE_BEGIN_INDEX, LONGITUDE_END_INDEX));
            coordinate = new Coordinate(lat, lon);
        }
        return coordinate;
    }
}
