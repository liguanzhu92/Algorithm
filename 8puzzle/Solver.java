import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class Solver {
    private boolean solve = false;
    private int moves = -1;
    private List<Board> solution = new ArrayList<Board>();
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int move;
        private final int priority;
        private final SearchNode parent;
        private final boolean isTwin;

        public SearchNode(Board board, int move, SearchNode parent, boolean isTwin) {
            this.board = board;
            this.move = move;
            this.priority = board.manhattan() + move;
            this.parent = parent;
            this.isTwin = isTwin;
        }

        @Override
        public int compareTo(SearchNode that) {
            if (this.board.equals(that.board)) return 0;
            if (this.priority < that.priority) return -1;
            else return 1;
        }
    }

    private MinPQ<SearchNode> minPQ = 
        new MinPQ<SearchNode>(new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return o1.priority - o2.priority;
        }
    });
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        Board temp = initial.twin();
        SearchNode init = new SearchNode(initial, 0, null, false);
        SearchNode twinInit = new SearchNode(temp, 0, null, true);
        minPQ.insert(init);
        minPQ.insert(twinInit);
        while (true) {
            SearchNode cur = minPQ.delMin();
            if (cur.board.isGoal()) {
                if (cur.isTwin) {
                    moves = -1;
                    solve = false;
                } else {
                    solve = true;
                    moves = cur.move;
                    solution.add(cur.board);
                    // push all the parents, find the solve path
                    while (cur.parent != null) {
                        cur = cur.parent;
                        solution.add(cur.board);
                    }
                }
                break;
            } else {
                for (Board newBoard : cur.board.neighbors()) {
                    SearchNode newSearch = 
                        new SearchNode(newBoard, cur.move+1, cur, cur.isTwin);
                    if (cur.parent == null) {
                        minPQ.insert(newSearch);
                    } else if (!cur.parent.board.equals(newBoard)) {
                        minPQ.insert(newSearch);
                    }
                }
            }
        }
    }
    
    public boolean isSolvable() {
        // is the initial board solvable?
        return solve;
    }
    
    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return moves;
    }
    
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (solve) {
            Collections.reverse(solution);
            return solution;
        } else {
            return null;
        }
    }
    
    public static void main(String[] args) {
// solve a slider puzzle (given below)
            // create initial board from file

    }
}