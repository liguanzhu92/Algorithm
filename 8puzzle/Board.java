import edu.princeton.cs.algs4.MinPQ;
import java.util.Arrays;
import java.util.Comparator;


public class Board {
    private int[][] blocks;
    private int n; // length
    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        n = blocks.length;
        this.blocks = new int[n][];  
        for (int i = 0; i < n; i++) {  
            this.blocks[i] = blocks[i].clone();  
        } 
    }
    
    public int dimension() {
        // board dimension n
        return blocks.length;
    }
    
    public int hamming() {
        // number of blocks out of place
        int count = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length && i+j < 2*n - 2; j++) {
                if (blocks[i][j] != i * n + j + 1) {
                    count++;
                }
            }
        }
        return count;
    }
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int sum = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                } else if (blocks[i][j] != i * n + j + 1) {
                    int cur = blocks[i][j];
                    cur -= 1;
                    // target
                    int x = cur / n;
                    int y = cur % n;
                    sum += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        }
        return sum;
    }
    
    public boolean isGoal() {
        // is this board the goal board?
        return hamming() == 0;
        
    }
    
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[][] twinBoard = new int[n][blocks[0].length];
        for (int i = 0; i < n; i++) {
            twinBoard[i] = blocks[i].clone();
        }
        if (blocks[0][0] != 0 && blocks[0][1] != 0) {
            int temp = twinBoard[0][0];
            twinBoard[0][0] = twinBoard[0][1];
            twinBoard[0][1] = temp;
        } else {
            int temp = twinBoard[1][0];
            twinBoard[1][0] = twinBoard[1][1];
            twinBoard[1][1] = temp;
        }
        return new Board(twinBoard);
    }
    
    public boolean equals(Object y) {
        // does this board equal y?
        if (this == y) return true; // address same
        if (y == null) return false;
        if (y.getClass() != this.getClass()) 
            return false; // not same instance of class
        Board that = (Board) y;
        if (that.n != this.n) return false;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }

        return true;
    }
    
    public Iterable<Board> neighbors() {
        int x = n;
        int y = n;
        // find blank
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                     break;
                 }
            }
        }
        MinPQ<Board> p = new MinPQ<Board>(new Comparator<Board>() {
            @Override
            public int compare(Board o1, Board o2) {
                if (o1.manhattan() < o2.manhattan()) return -1;
                else if (o1.manhattan() == o2.manhattan()) return 0;
                else return +1;
            }
        });
        if (x - 1 >= 0) {
            int[][] temp = getCopy();
            temp[x][y] = temp[x - 1][y];
            temp[x - 1][y] = 0;
            p.insert(new Board(temp));
        }
        if (x + 1 < n) {
            int[][] temp = getCopy();
            temp[x][y] = temp[x + 1][y];
            temp[x + 1][y] = 0;
            p.insert(new Board(temp));
        }
        if (y - 1 >= 0) {
            int[][] temp = getCopy();
            temp[x][y] = temp[x][y - 1];
            temp[x][y - 1] = 0;
            p.insert(new Board(temp));
        }
        if (y + 1 < n) {
            int[][] temp = getCopy();
            temp[x][y] = temp[x][y + 1];
            temp[x][y + 1] = 0;
            p.insert(new Board(temp));
        }
        return p;
    }
    
    private int[][] getCopy() {
        int[][] result = new int[n][];
        for (int i = 0; i < n; i++) {
            result[i] = Arrays.copyOf(blocks[i], n);
        }
        return result;
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
        
    }

    public static void main(String[] args) {
// unit tests (not graded)
//        In in = new In(args[0]);
//        int n = in.readInt();
//        int[][] blocks = new int[n][n];
//        for (int i = 0; i < n; i++)
//            for (int j = 0; j < n; j++)
//                blocks[i][j] = in.readInt();
//        Board initial = new Board(blocks);
//        int test = initial.manhattan();
//        StdOut.println(test);
    }
}