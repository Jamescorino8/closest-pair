/**
 * A driver class for the Closest Pair algorithms
 * Programming assignment for
 * CSI403 Algorithms and Data Structures
 * University at Albany - SUNY
 * <p>
 * Instructions: Only change the runnningTimeComparison method
 * All other methods will not be considered when testing your
 * program.
 */

public class ClosestPairDriver {

    /**
     * A unit test for BruteForce on five points
     *
     * @return true if closest pair successfully found
     */
    public static boolean testFivePointsBruteForce() {
        Point[] pts = {new Point(0.0, 0.0),
            new Point(0.0, 1.0),
            new Point(1.0, 0.0),
            new Point(1.0, 1.0),
            new Point(0.2, 0.2)
        };
        Point[] cp = ClosestPair.getCPBruteForce(pts);
        Double actual = (new Point(0.0, 0.0)).dist(new Point(0.2, 0.2));
        if (actual.equals(cp[0].dist(cp[1])))
            return true;
        else
            return false;
    }

    /**
     * A unit test for Divide-And-Conquer on five points
     *
     * @return true if closest pair successfully found
     */
    public static boolean testFivePointsDaC() {
        Point[] pts = {new Point(0.0, 0.0),
            new Point(0.0, 1.0),
            new Point(1.0, 0.0),
            new Point(1.0, 1.0),
            new Point(0.2, 0.2)
        };

        Point[] cp = ClosestPair.getCPDivideAndConquer(pts);
        Double actual = (new Point(0.0, 0.0)).dist(new Point(0.2, 0.2));
        if (actual.equals(cp[0].dist(cp[1])))
            return true;
        else
            return false;
    }

    /**
     * A unit test for comparing the results of
     * Divide-And-Conquer and BruteForce on five points.
     * It also demonstrates how to time the executions
     * for part (b) of your assignment
     *
     * @return true if closest pairs' distances match
     */
    public static boolean testRandom(int numpoints) {
        Point[] pts = getRandomPoints(numpoints);

        // Execute and time BruteForce
        long tick = System.currentTimeMillis();
        Point[] cpBF = ClosestPair.getCPBruteForce(pts);
        long tock = System.currentTimeMillis();
        System.out.println("Exhaustive: " + numpoints + " (" + (tock - tick) + "ms)");

        // Execute and time Divide-and-Conquer
        tick = System.currentTimeMillis();
        Point[] cpDQ = ClosestPair.getCPDivideAndConquer(pts);
        tock = System.currentTimeMillis();
        System.out.println("Divide-And-Conquer: " + numpoints + " (" + (tock - tick) + "ms)");

        // Check if distances of pairs agree
        if (cpBF[0].dist(cpBF[1]).equals(cpDQ[0].dist(cpDQ[1])))
            return true;
        else
            return false;
    }

    /**
     * Generates @numpoints random points in the [0,100] square
     *
     * @return true if closest pair successfully found
     */
    private static Point[] getRandomPoints(int numpoints) {
        Point[] pts = new Point[numpoints];
        for (int i = 0; i < numpoints; i++) {
            pts[i] = new Point(Math.random() * 100, Math.random() * 100);
        }
        return pts;
    }

    /**
     * TODO: IMPLEMENT
     *  Runs and times the BruteForce and Divide-And-Conquer
     *  algorithms for 10,100,1000 and 10000 random points
     *  Should print out using stdout the running times for both
     *  algorithms for the above sizes.
     *  Use the provided random point generator getRandomPoints()
     */
    private static void runnningTimeComparison() {
        int[] sizes = {10, 100, 1000, 10000};
        int runs = 5;

        System.out.println();
        System.out.println("Num points\tBrute Force (ms)\tDivide-and-Conquer (ms)");
        for (int n : sizes) {
            long totalBruteForce = 0, totalDivideConquer = 0;
            for (int r = 0; r < runs; r++) {
                Point[] pts = getRandomPoints(n);

                long tick = System.nanoTime();
                ClosestPair.getCPBruteForce(pts);
                totalBruteForce += System.nanoTime() - tick;

                tick = System.nanoTime();
                ClosestPair.getCPDivideAndConquer(pts);
                totalDivideConquer += System.nanoTime() - tick;
            }
            double avgBruteFore = totalBruteForce / (runs * 1e6);
            double avgDivideConquer = totalDivideConquer / (runs * 1e6);
            System.out.printf("%d\t\t%.3f\t\t\t%.3f%n", n, avgBruteFore, avgDivideConquer);
        }
    }

    /**
     * Driver class for tests
     */
    public static void main(String[] args) {

        if (testFivePointsBruteForce())
            System.out.println("Test BruteForce: SUCCESS");
        else
            System.err.println("Test BruteForce: FAILED");


        if (testFivePointsDaC())
            System.out.println("Test Divide-And-Conquer: SUCCESS");
        else
            System.err.println("Test Divide-And-Conquer: FAILED");

        int numPoints = 10000;
        if (testRandom(numPoints))
            System.out.println("Test " + numPoints + " Points: SUCCESS");
        else
            System.err.println("Test " + numPoints + " Points: FAILED");

        // Running time comparison
        runnningTimeComparison();

    }
}
