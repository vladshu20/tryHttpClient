package logic;

import java.util.Arrays;

public class PercentileCalculator {
    public static long CalculatePercentile(long[] time){
        Arrays.sort(time);
        return time[9];
    }
}
