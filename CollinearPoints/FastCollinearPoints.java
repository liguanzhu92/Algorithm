import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
public class FastCollinearPoints {
    private Node record;
    private int lines;
    private class Node {
        // record the line segment infor
        private LineSegment lineValue;
        private Node prev;
    }
    public FastCollinearPoints(Point[] points) {
        // consider the exception
        if (points == null) {
            throw new NullPointerException();
        }
        // finds all line segments containing 4 or more points
        int n = points.length;
        if (n < 4) {
            throw new IllegalArgumentException();
        }
        lines = 0;
        Point[] copyPoints = new Point[n];
        for (int i = 0; i < n; i++) {
            if (points == null) {
                throw new NullPointerException();
            }
            
            for (int j = i + 1; j < n; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            copyPoints[i] = points[i];
        }
        Arrays.sort(copyPoints);
        // build a fake start
        record = new Node();
        record.prev = null;
        for (int i = 0; i < n - 1; i++) {
            // points[i] as the origin point
            // create array[n] record the slopt
            Point[] temp = new Point[n - i - 1];
            int tempNum = 0;
            for (int j = i + 1; j < n; j++) {
                // point[j] determin the slope it makes with p;
                // copy the rest
                temp[tempNum] = copyPoints[j];
                tempNum++;
            }
            // sort, according to the copyPoints slope
            Arrays.sort(temp, copyPoints[i].slopeOrder());
            int count = 0; //  record the number of points in the line seg
            for (int j = 0; j < n - i - 2; j++) {
                // j: go through the temp points
                double cur = copyPoints[i].slopeTo(temp[j]);
                double nxt = copyPoints[i].slopeTo(temp[j+1]);
                if (cur == nxt) {
                    count++;
                } else {
                    count = 0;
                }
                if (count > 1) {
                    // add to node;
                    lines++;
                    Node next = new Node();
                    next.prev = record;
                    next.lineValue = new LineSegment(copyPoints[i], temp[j+1]);
                    record = next;
                }
            }
        }
    }
    
    public int numberOfSegments() {
        // the number of line segments
        return lines;
    }
    
    public LineSegment[] segments() {
        // the line segments
        LineSegment[] seg = new LineSegment[lines];
        // copy the infor from tail to front
        for (int i = 0; i < lines; i++) {
            seg[i] = record.lineValue;
            record = record.prev;            
        }
        return seg;
    }
    
    public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    }
}