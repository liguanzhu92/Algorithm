import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private double[] rec;
    private int times;
    public PercolationStats(int n, int trials) {
        this.times = trials;
        // perform trials independent experiments on an n-by-n grid
         if (n <= 0 | trials <= 0) 
             throw new IndexOutOfBoundsException("input is out of bounds");
         rec = new double[trials];
         for (int i = 0; i < trials; i++) {
             Percolation perc = new Percolation(n);
             // count the open cell
             int count = 0;
             while (!perc.percolates()) {
                 int x = StdRandom.uniform(n)+1;
                 int y = StdRandom.uniform(n)+1;
                 if (perc.isOpen(x, y)) {
                     continue;
                 } else {
                     perc.open(x, y);
                 }
                 count++;
             }
             rec[i] = (double) count/((double) n*(double) n);
         }
    }
    
    public double mean() {
        // sample mean of percolation threshold
        double mean = StdStats.mean(rec);
        return mean;        
    }
    
    public double stddev() {
        // sample standard deviation of percolation threshold
        double std = StdStats.stddev(rec);
        return std;
    }
    
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        double mean = mean();
        double dev = stddev();
        double lo = mean - 1.96*dev/Math.sqrt(times);
        return lo;
    }
    
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        double mean = mean();
        double dev = stddev();
        double hi = mean + 1.96*dev/Math.sqrt(times);
        return hi;        
    }

    public static void main(String[] args) {
        // test client (described below)
        PercolationStats stat = 
            new PercolationStats(Integer.parseInt(args[0]), 
                                 Integer.parseInt(args[1]));
        double mean = stat.mean();
        double std = stat.stddev();
        double lo = stat.confidenceLo();
        double hi = stat.confidenceHi();
        System.out.println("mean = " + mean);
        System.out.println("stddev = " + std);
        System.out.println("95% confidence interval = " + lo +", "+ hi);       
    }
}
