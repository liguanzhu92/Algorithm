import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
public class BruteCollinearPoints {
    private int line;
    private Node record;
     /**
     * Initializes BruteCollinearPoints
     *
     * @param  line: the number of lines 
     * @param  record: record the line segment which consist of 4 points or more
     * @param  len: lenth of inputs
     */
    private class Node {
        // record the line segment infor
        private Node prev;
        private LineSegment lineValue;
    }
    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        line = 0;
        if (points == null) {
            throw new NullPointerException();
        }
        int len = points.length;
        if (len < 4) {
            throw new IllegalArgumentException();
        }
        Point[] copyPoints = new Point[len];
        for (int i = 0; i < len; i++) {
            if (points == null) {
                throw new NullPointerException();
            }
            
            for (int j = i + 1; j < len; j++) {
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
        // compare
        for (int a = 0; a < len; a++) {
            for (int b = a + 1; b < len; b++) {
                for (int c = b + 1; c < len; c++) {
                    for (int d = c + 1; d < len; d++) {
                        double abS = copyPoints[a].slopeTo(copyPoints[b]);
                        double bcS = copyPoints[b].slopeTo(copyPoints[c]);
                        double cdS = copyPoints[c].slopeTo(copyPoints[d]);
                        if (abS == bcS && bcS == cdS) {
                            line++;
                            // move forwards;
                            Node next = new Node();
                            next.prev = record;
                            next.lineValue = 
                                new LineSegment(copyPoints[a], copyPoints[d]);
                            record = next;
                            // duplicate drawing
                        }
                    }
                }
            }
        }
    }
    
    public int numberOfSegments() {
        // the number of line segments
        return line;
    }
    
    public LineSegment[] segments() {
        // the line segments
        LineSegment[] seg = new LineSegment[line];
        // copy the infor from tail to front
        for (int i = 0; i < line; i++) {
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    // StdOut.println(collinear.numberOfSegments());
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
    }
}