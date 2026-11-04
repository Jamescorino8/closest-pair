import java.util.Arrays;

/**
 * A main class for the Closest Pair algorithms
 * Programming assignment for
 * CSI403 Algorithms and Data Structures
 * University at Albany - SUNY
 * <p>
 * Instructions: Implement methods:
 * 1) getCPBruteForce()
 * 2) getCPDivideAndConquer()
 * As discussed in class and in the assignment part (a)
 */

public class ClosestPair {

    /**
     * TODO: IMPLEMENT
     *  A brute force method for the closest pair
     *  @returns an array of exactly the two closest points
     *  IMPORTANT: DO NOT CHANGE THIS METHOD SIGNATURE,
     *  ONLY THE BODY WILL BE CONSIDERED FOR GRADING
     */
    public static Point[] getCPBruteForce(Point[] pts) {
        //TODO: Implement this method for part (a) of the assignment


        return null;
    }

    /**
     * A driver for the Divide-And-Conquer method for the closest pair
     * takes unsorted array of points, sorts them and invokes
     * the recursive method you are required to implement
     *
     * @returns an array of exactly the two closest points
     * IMPORTANT: DO NOT CHANGE THIS METHOD
     */
    public static Point[] getCPDivideAndConquer(Point[] pts) {
        Point[] ptsX = Point.sortByX(pts);
        Point[] ptsY = Point.sortByY(pts);
        return getCPDivideAndConquer(ptsX, ptsY);
    }

    /**
     * TODO: IMPLEMENT
     *  A Divide-And-Conquer method for the closest pair
     *  takes as input the points sorted by increasing x
     *  and y coordinates in ptsX and ptsY respectively
     *  @returns an array of exactly the two closest points.
     *  IMPORTANT: DO NOT CHANGE THIS METHOD SIGNATURE,
     *  ONLY THE BODY WILL BE CONSIDERED FOR GRADING
     */
    public static Point[] getCPDivideAndConquer(Point[] ptsX, Point[] ptsY) {
        int n = ptsX.length;
        if (n < 2) return null;
        if (n == 2) return ptsX;

        double m;
        if (n % 2 == 0) {
            m = ptsX[n / 2].x;
        } else {
            m = (ptsX[(n / 2) - 1].x + ptsX[n / 2].x) / 2.0;
        }

        int mid = n / 2;
        double midX = ptsX[mid].x;
        Point[] ptsX_left = Arrays.copyOfRange(ptsX, 0, mid);
        Point[] ptsX_right = Arrays.copyOfRange(ptsX, mid, n);

        Point[] ptsY_left = new Point[mid];
        Point[] ptsY_right = new Point[n - mid];
        java.util.Set<Point> leftSet = new java.util.HashSet<>(Arrays.asList(ptsX_left));
        int li = 0, ri = 0;
        for (Point p : ptsY) {
            if (leftSet.contains(p)) ptsY_left[li++] = p;
            else ptsY_right[ri++] = p;
        }

        Point[] pair_left = getCPDivideAndConquer(ptsX_left, ptsY_left);
        Point[] pair_right = getCPDivideAndConquer(ptsX_right, ptsY_right);

        Point[] res;
        if (pair_left == null) res = pair_right;
        else if (pair_right == null) res = pair_left;
        else res = pair_left[0].dist(pair_left[1]) <= pair_right[0].dist(pair_right[1]) ? pair_left : pair_right;
        double d = res[0].dist(res[1]);

        Point[] temp = new Point[n];
        int count = 0;
        for (Point p : ptsY) {
            if (Math.abs(p.x - midX) < d) temp[count++] = p;
        }
        Point[] ptsWithinD = Arrays.copyOf(temp, count);

        for (int i = 1; i < ptsWithinD.length; i++) {
            for (int j = 1; j < Math.min(ptsWithinD.length, i + 7); j++) {
                if (ptsWithinD[i].dist(ptsWithinD[j]) < d) {
                    res[0] = ptsWithinD[i];
                    res[1] = ptsWithinD[j];
                    d = ptsWithinD[i].dist(ptsWithinD[j]);
                }
            }
        }
        return res;
    }
}
