# Closest Pair of Points

Two algorithms for the 2D closest pair problem, brute force and divide and conquer, with a comparison of their running times. Written for CSI 403: Algorithms and Data Structures at the University at Albany (SUNY).

Given `n` points in the plane, find the two points with the smallest Euclidean distance:

```
d(p, q) = sqrt((xp - xq)^2 + (yp - yq)^2)
```

## Files

| File | Purpose |
|---|---|
| `src/Point.java` | The `Point` class: `x`/`y` coordinates, `dist()`, and the `sortByX()` / `sortByY()` helpers. Provided by the course. |
| `src/ClosestPair.java` | The two closest-pair algorithms: `getCPBruteForce()` and `getCPDivideAndConquer()`. |
| `src/ClosestPairDriver.java` | Correctness tests, a random point generator, and `runnningTimeComparison()` for the timing experiments. |
| `RunningTimeAnalysis.pdf` | The running-time report: results table, chart with fitted curves, and discussion. |

## Algorithms

### Brute force: Θ(n²)

Computes the distance between all `n(n - 1)/2` pairs of points and keeps the smallest.

### Divide and conquer: O(n log n)

`getCPDivideAndConquer(pts)` sorts the points once by x (`ptsX`) and once by y (`ptsY`), then runs the recursive method:

1. **Base cases:** fewer than 2 points returns `null`; exactly 2 points returns that pair.
2. **Divide:** split the points at the median x coordinate `m` into a left and a right half. Both halves of `ptsY` are built in a single linear pass, so they stay sorted by y without sorting again.
3. **Conquer:** recursively find the closest pair in each half.
4. **Combine:** let `d` be the smaller of the two half distances. The only pairs that could beat `d` are split by the line `x = m`, so both points lie within `d` of it. Collect those points in y order (`ptsWithinD`) and compare each one with only the **next 7** points in that list. At most 8 points fit in a `2d × d` box, so no other comparisons are needed. That keeps the combine step O(n).

The recurrence `T(n) = 2T(n/2) + cn` is the same as merge sort's and solves to **O(n log n)**.

## Build and run

Requires Java 7 or newer.

```sh
cd src
javac *.java
java ClosestPairDriver
```

Expected output:

```
Test BruteForce: SUCCESS
Test Divide-And-Conquer: SUCCESS
Exhaustive: 10000 (…ms)
Divide-And-Conquer: 10000 (…ms)
Test 10000 Points: SUCCESS

Num points	Brute Force (ms)	Divide-and-Conquer (ms)
10		…			…
100		…			…
1000		…			…
10000		…			…
```

## Running-time comparison

`runnningTimeComparison()` times both algorithms on the same random points at each size, using `System.nanoTime()` and averaging 5 runs. The full write-up, including the chart, is in [RunningTimeAnalysis.pdf](RunningTimeAnalysis.pdf). Measured times:

| No. of points | Brute Force (ms) | Divide-and-Conquer (ms) |
|---|---|---|
| 10 | 0.002 | 0.006 |
| 100 | 0.099 | 0.159 |
| 1,000 | 1.729 | 3.316 |
| 10,000 | 102.120 | 16.829 |

Fitting `f(n) = c1·n²` and `g(n) = c2·n log₂ n` to the 10,000-point times gives c1 ≈ 1.02 × 10⁻⁶ ms and c2 ≈ 1.27 × 10⁻⁴ ms. c2 is about 124 times larger, because each step of divide and conquer also pays for array copying, splitting and recursion.

- **Small n:** the constants decide. Brute force was faster at 10, 100 and 1,000 points, and the fitted curves cross near 1,300 points.
- **Large n:** the growth rate decides. From 1,000 to 10,000 points, brute force grew about 59x (n² predicts 100x) while divide and conquer grew about 5x (n log n predicts 13x). At 10,000 points divide and conquer was about 6.1 times faster. At 1,000,000 points, the fitted curves predict about 17 minutes for brute force and about 2.5 seconds for divide and conquer.

Times depend on the machine and vary between runs, especially at small sizes.
