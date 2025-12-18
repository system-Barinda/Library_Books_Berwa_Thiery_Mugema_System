package service;

import java.util.List;
import model.Fan;

public class FanService {

    public Fan highestAverage(List<Fan> fans) {
        Fan top = null;
        double max = 0;

        for (Fan f : fans) {
            if (f.getAverage() > max) {
                max = f.getAverage();
                top = f;
            }
        }
        return top;
    }
}
