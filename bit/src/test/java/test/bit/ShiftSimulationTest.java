package test.bit;

import bit.ShiftSimulation;
import org.junit.Test;

public class ShiftSimulationTest {
    @Test
    public void testShift() {
        ShiftSimulation sim = new ShiftSimulation();
        int arr[] = {0, 0, 0, 0, 0};
        for (int i = 0; i < (1 << arr.length) - 1; i++) {
            sim.simShift(arr);
            System.out.print("shift " + (i + 1) + "th:\t");
            for (int j = 0; j < arr.length; j++) {
                System.out.print(arr[j] + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testCompute() {
        ShiftSimulation sim = new ShiftSimulation();
        double[] arr = {0.1, 0.2, 0.3, 0.4, 0.5};
        double r1 = sim.compute(arr);
        double r2 = sim.shiftCompute(arr);
        System.out.println(r1);
        System.out.println(r2);
        System.out.printf("%d",(1L << 63)-1);
    }
}
