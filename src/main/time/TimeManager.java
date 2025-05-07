package main.time;

public class TimeManager {
    public final int defaultMaxTime = 86400;
    public int maxTime = defaultMaxTime;
    public int time = (maxTime / 2) + (maxTime / 4);
    public int currentDayIndex = 0;
    public int timeSpeed = 1;
    public boolean timeFlows = true;

    String[] daysOfWeek = {
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
    };

    public void updateTime() {
        if (timeFlows) {
            time += timeSpeed;
            if (time >= maxTime) {
                time = 0;
                currentDayIndex = (currentDayIndex + 1) % daysOfWeek.length;
            }
        }
    }

    public int getHour() {
        return (int) ((time / (float) maxTime) * 1440) / 60;
    }

    public int getMinute() {
        return (int) ((time / (float) maxTime) * 1440) % 60;
    }

    public String getCurrentDay() {
        return daysOfWeek[currentDayIndex];
    }

    public float getDarkness(float maxDarkness) {
        float dayProgress = (float) time / maxTime;
        float distanceToMidday = Math.abs(0.5f - dayProgress) * 2f;
        float curvedDarkness = (float) Math.pow(distanceToMidday, 2.2f);
        return curvedDarkness * maxDarkness;
    }

}
