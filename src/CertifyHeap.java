import stdlib.StdIn;
import stdlib.StdOut;

public class CertifyHeap {
    // Returns true if a[] represents a max-heap, and false otherwise.
    public static boolean isMaxHeap(Comparable[] a) {
        // Set n to the number of elements in a.
        int n = a.length;

        // For each node 1 <= i <= n / 2, if a[i] is less than either of its children, return
        // false, meaning a[] does not represent a max-heap. If no such i exists, return true.
        for (int i = 1; i <= n / 2; i++) {
            int l = 2 * i;
            int r = 2 * i + 1;
            if (l < n && less(a[i], a[l]) || r < n && less(a[i], a[r])) {
                return false;
            }
        }
        return true;
    }

    // Returns true of v is less than w, and false otherwise.
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        StdOut.println(isMaxHeap(a));
    }
}
