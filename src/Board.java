import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    // Tiles in the board, int[][] tiles
    int[][] tiles;
    // Board size, int n
    int n;
    // Hamming distance to the goal board, int hamming
    int hamming;
    // Manhattan distance to the goal board, int manhattan
    int manhattan;
    // Position of the blank tile in row-major order, int blankPo
    int blankPos;

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                /* find the hamming distance by
                using the encode equation from percolation
                and then seeing if tiles lines up with it
                 */
                this.hamming = 0;
                for (int k = 0; k < n; k++) {
                    for (int m = 0; m < n; m++) {
                        if (this.tiles[k][m] == 0) {
                            // set blank position and continues the loop not changing hamming
                            // distance
                            blankPos = (n*k)+m+1;
                            continue;
                        }
                        if (this.tiles[k][m] != (n*k)+m+1) {
                            // increment if the value doesnt equal the index
                            this.hamming++;
                        }
                    }
                }
            /* manhattan distance is Manhattan(i,j) = |i1 - i2| + |j1 + j2| thus we see if the
            board matches the same condition as hamming and then do the formula
            for manhattan distance */
                this.manhattan = 0;
                for (int p = 0; p < n; p++) {
                    for (int q = 0; q < n; q++) {
                        if (this.tiles[p][q] == (n*p)+q+1) {
                            // don't increment if the value is where it should be
                            this.manhattan += 0;
                        }
                        if (this.tiles[p][q] == 0) {
                           // skip over the "blank" space
                            this.manhattan += 0;
                        } else /*(this.tiles[p][q] != (n*p)+q)*/ {
                            // gives us the desired column
                            int goalC = (tiles[p][q] - 1) % n;
                            // gives us the desired row
                            int goalR = (tiles[p][q] - 1) / n; // something
                            // find manhattan distance
                            this.manhattan += Math.abs(p - goalR) + Math.abs(q - goalC);
                        }
                    }
                }
            }
        }
    }

    // Returns the size of this board.
    public int size() {
        return this.n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return this.tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        return this.hamming;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        return this.manhattan;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        // if hamming and manhattan are 0, returns true
        return hamming() == 0 && manhattan() == 0;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int [] solvable = new int[n*n-1];
        // int len = solvable.length;
        // inverions plus index
        int sum = 0;
        int k = 0;
        int row = 0;

        // loop 2D array tiles into 1D array solvable
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((n * i) + j + 1 != blankPos) {
                    solvable[k] = tiles[i][j];
                    k++;
                } else {
                    row = i;
                }
            }
        }
        // if the number of inversions is even return true
        /* if (Inversions.count(solbvable) % 2 == 0 && len % 2 == 0) {
            return true;
        } */
        if (n % 2 != 0) {
            if (Inversions.count(solvable) % 2 == 0) {
                return true;
            }
            return false;
        } else {
            sum = (int) Inversions.count(solvable) + row;
            if (sum % 2 == 0) {
                return false;
            }
            return true;
        }
    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        // Create a queue q of Board objects
        LinkedQueue<Board> q = new LinkedQueue<>();
        int [][] copy;
        int p = blankPos - 1;
        int row = (p/n);
        int column = (p % n);
        // north
        if (row > 0) { /*(row - 1 >= 0 && row / n - 1 < n - 1){*/
            // Clone the tiles of the board
            copy = cloneTiles();
            // Exchange an appropriate tile with the blank tile in the clone
            int tile = copy[row][column];
            copy[row][column] = copy[row - 1][column];
            copy[row - 1][column] = tile;
            // Construct a Board object from the clone, and enqueue it into q
            q.enqueue(new Board(copy));
        }
        // south
        if (row < n-1) { /*(row + 1 >= 0 && row + 1 < n - 1)  {*/
            // Clone the tiles of the board
            copy = cloneTiles();
            // Exchange the appropriate tile with the blank tile in the clone
            int tile = copy[row][column];
            copy[row][column] = copy[row + 1][column];
            copy[row + 1][column] = tile;
            // Board b = new Board(copy);
            // Construct a Board object from the clone, and enqueue it into q
            q.enqueue(new Board(copy));
        }
        // west
        if (column > 0) { /*(column - 1 >= 0 && column - 1  < n - 1) {*/
            // Clone the tiles of the board
            copy = cloneTiles();
            // Exchange the appropriate tile with the blank tile in the clone
            int tile = copy[row][column];
            copy[row][column] = copy[row][column - 1];
            copy[row][column - 1] = tile;
            // Construct a Board object from the clone, and enqueue it into q
            q.enqueue(new Board(copy));
        }
        // east
        if (column < n-1) { /*(column + 1 >= 0 && column + 1 < n - 1) {*/
            // Clone the tiles of the board
            copy = cloneTiles();
            // Exchange the appropriate tile with the blank tile in the clone
            int tile = copy[row][column];
            copy[row][column] = copy[row][column + 1];
            copy[row][column + 1] = tile;
            // Construct a Board object from the clone, and enqueue it into q
            q.enqueue(new Board(copy));
        }
        return q;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        if (this.n != ((Board) other).n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // return false if the boards do not equal each other
                if (this.tiles[i][j] != ((Board) other).tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}