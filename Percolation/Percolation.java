import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean[] matrix;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufCheck;
    private int n;
    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("input is out of bounds");
        this.n = n;
        uf = new WeightedQuickUnionUF((n+2)*n+1);
        ufCheck = new WeightedQuickUnionUF((n+1)*n+ 1);
        matrix = new boolean[(n+2)*n+1];
        for (int i = 1; i <= n; i++) {
            uf.union(1, i);
            matrix[i] = true;
            uf.union((n+1)*n + 1, (n+1)*n + i);
            matrix[(n+1)*n+i] = true;
            ufCheck.union(1, i);
        }
    }
    public void open(int i, int j) {
        // open site (row i, column j) if it is not open already
        if (i < 1 || i > n) {  
            throw new IndexOutOfBoundsException("row out of bounds");  
        }
        if (j < 1 || j > n) {  
            throw new IndexOutOfBoundsException("column out of bounds");  
        }
        if (matrix[i*n+j]) {  
            return;  
        }
        matrix[i*n+j] = true;
        // update the matrix
        // up
        if (matrix[(i-1)*n + j]) {
            uf.union((i-1)*n + j, i*n+j);
            ufCheck.union((i-1)*n + j, i*n+j);
        }
        // down
        if (matrix[(i+1)*n + j]) {
            uf.union((i+1)*n + j, i*n+j);
            if (i != n) {
                ufCheck.union((i+1)*n + j, i*n+j);
            }
        }
        // left
        if (j > 1 && matrix[i*n + j-1]) {
            uf.union(i*n + j-1, i*n+j);
            ufCheck.union(i*n + j-1, i*n+j);
        }
        // right
        if (j < n && matrix[i*n + j+1]) {
            uf.union(i*n + j+1, i*n+j);
            ufCheck.union(i*n + j+1, i*n+j);
        }   
    }
    
    public boolean isOpen(int i, int j) {
        // is site (row i, column j) open?
        if (i < 1 || i > n) {  
            throw new IndexOutOfBoundsException("row out of bounds");  
        }
        if (j < 1 || j > n) {  
            throw new IndexOutOfBoundsException("column out of bounds");  
        }
        return matrix[i*n+j]; 
    }
    
    public boolean isFull(int i, int j) {
        // is site (row i, column j) full?
        if (i < 1 || i > n) {  
            throw new IndexOutOfBoundsException("row out of bounds");  
        }
        if (j < 1 || j > n) {  
            throw new IndexOutOfBoundsException("column out of bounds");  
        }
        return ufCheck.connected(i*n+j, 1) && matrix[i*n + j];
    }
    
    public boolean percolates() {
        // does the system percolate?
        return uf.connected(1, (n+1)*n+1);
    }

    public static void main(String[] args) {
        // test client (optional)
        Percolation perc = new Percolation(2);
        perc.open(1, 1);
        perc.open(1, 2);
        perc.open(2, 1);
        System.out.println(perc.percolates());
    }
}