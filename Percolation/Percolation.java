import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int topRoot;
    private int bottomRoot;
    private int openSites = 0;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF fullSites;
    private int dim;
    private boolean[][] sites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        dim = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        fullSites = new WeightedQuickUnionUF(n * n + 1);
        topRoot = n * n;
        bottomRoot = n * n + 1;
        sites = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int index = index(row, col);
        if (row == 1) {
            uf.union(index, topRoot);
            fullSites.union(index, topRoot);
        } else {
            int topRow = row - 1;
            unionIfOpen(topRow, col, index);
        }
        if (col < dim) {
            int rightCol = col + 1;
            unionIfOpen(row, rightCol, index);
        }

        if (row == dim) {
            uf.union(index, bottomRoot);
        } else {
            int bottomRow = row + 1;
            unionIfOpen(bottomRow, col, index);
        }

        if (col > 1) {
            int leftCol = col - 1;
            unionIfOpen(row, leftCol, index);
        }

        sites[row - 1][col - 1] = true;
        openSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        check(row, col);
        return sites[row - 1][col - 1] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row, col);
        int i = index(row, col);
        return fullSites.find(i) == fullSites.find(topRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(topRoot) == uf.find(bottomRoot);
    }

    // Helper Methods
    private void check(int row, int col) {
        if (row < 1 || row > dim || col < 1 || col > dim) {
            throw new IllegalArgumentException();
        }
    }

    private int index(int row, int col) {
        return dim * (row - 1) + (col - 1);
    }

    private void unionIfOpen(int row, int col, int indexToUnion) {
        if (isOpen(row, col)) {
            int index = index(row, col);
            uf.union(index, indexToUnion);
            fullSites.union(index, indexToUnion);
        }
    }
}
