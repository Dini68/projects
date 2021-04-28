package activity;

import java.util.ArrayList;
import java.util.List;

public class Activities {

    private final List<Activity> activities;

    public Activities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Report> distancesByTypes() {
        List<Report> result = new ArrayList<>();
        result.add(new Report(ActivityType.BIKING, 0));
        result.add(new Report(ActivityType.HIKING, 0));
        result.add(new Report(ActivityType.RUNNING, 0));
        result.add(new Report(ActivityType.BASKETBALL, 0));
        for (Activity ac: activities) {
            int index = ac.getType().ordinal();
            double distance = result.get(index).getDistance() + ac.getDistance();
            result.set(index, new Report(ac.getType(), distance));
        }
        return result;
    }

    public int numberOfTrackActivities() {
        int number = 0;
        for (Activity ac: activities) {
            if (ac.getType() != ActivityType.BASKETBALL) {
                number ++;
            }
        }
        return number;
    }

    public int numberOfWithoutTrackActivities() {
        int number = 0;
        for (Activity ac: activities) {
            if (ac.getType() == ActivityType.BASKETBALL) {
                number ++;
            }
        }
        return number;
    }

}
