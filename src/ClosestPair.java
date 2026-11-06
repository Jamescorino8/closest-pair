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
        if (pts.length < 2) return null;

        Point[] res = {pts[0], pts[1]};
        double d = pts[0].dist(pts[1]);

        // Check all n(n-1)/2 pairs
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dist = pts[i].dist(pts[j]);
                if (dist < d) {
                    d = dist;
                    res = new Point[]{pts[i], pts[j]};
                }
            }
        }

        return res;
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
        // Divide
        int n = ptsX.length;
        if (n < 2) return null;
        if (n == 2) return ptsX;

        int mid = n / 2;
        double midX = ptsX[mid].x;

        Point[] ptsX_left = Arrays.copyOfRange(ptsX, 0, mid);
        Point[] ptsX_right = Arrays.copyOfRange(ptsX, mid, n);

        Point[] ptsY_left = new Point[mid];
        Point[] ptsY_right = new Point[n - mid];
        // A point is on the left if it sorts before ptsX[mid], using the same order as Point.sortByX, so points with x == midX go to correct side
        Point m = ptsX[mid];
        int li = 0, ri = 0;
        for (Point p : ptsY) {
            int c = Double.compare(p.x, m.x);
            if (c == 0) c = Double.compare(p.y, m.y);
            if (c == 0) c = Integer.compare(p.hashCode(), m.hashCode());
            boolean isLeft = c < 0;
            if (c == 0 && p != m) {
                for (int k = 0; k < mid; k++) {
                    if (ptsX_left[k] == p) {
                        isLeft = true;
                        break;
                    }
                }
            }
            if (isLeft) ptsY_left[li++] = p;
            else ptsY_right[ri++] = p;
        }

        // Conquer
        Point[] pair_left = getCPDivideAndConquer(ptsX_left, ptsY_left);
        Point[] pair_right = getCPDivideAndConquer(ptsX_right, ptsY_right);

        // Combine
        // Best pair of the two halves.
        Point[] res;
        if (pair_left == null) res = pair_right;
        else if (pair_right == null) res = pair_left;
        else res = pair_left[0].dist(pair_left[1]) <= pair_right[0].dist(pair_right[1]) ? pair_left : pair_right;
        double d = res[0].dist(res[1]);

        // Check pairs split by the line x = midX.
        Point[] temp = new Point[n];
        int count = 0;
        for (Point p : ptsY) {
            if (Math.abs(p.x - midX) < d) temp[count++] = p;
        }
        Point[] ptsWithinD = Arrays.copyOf(temp, count);

        for (int i = 0; i < ptsWithinD.length; i++) {
            for (int j = i + 1; j < Math.min(ptsWithinD.length, i + 8); j++) {
                if (ptsWithinD[i].dist(ptsWithinD[j]) < d) {
                    res = new Point[]{ptsWithinD[i], ptsWithinD[j]};
                    d = ptsWithinD[i].dist(ptsWithinD[j]);
                }
            }
        }
        return res;
    }
}
