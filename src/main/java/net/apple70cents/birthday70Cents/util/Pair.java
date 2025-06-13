package net.apple70cents.birthday70Cents.util;

/**
 * A simple container for a pair of objects.
 * @param <L> The type of the left element
 * @param <R> The type of the right element
 */
public class Pair<L, R> {
    private final L left;
    private final R right;

    /**
     * Creates a new pair.
     * 
     * @param left the left element
     * @param right the right element
     */
    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Factory method to create a new pair.
     * 
     * @param left the left element
     * @param right the right element
     * @param <L> the type of the left element
     * @param <R> the type of the right element
     * @return a new pair with the given elements
     */
    public static <L, R> Pair<L, R> of(L left, R right) {
        return new Pair<>(left, right);
    }

    /**
     * Gets the left element.
     * 
     * @return the left element
     */
    public L getLeft() {
        return left;
    }

    /**
     * Gets the right element.
     * 
     * @return the right element
     */
    public R getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (left != null ? !left.equals(pair.left) : pair.left != null) return false;
        return right != null ? right.equals(pair.right) : pair.right == null;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "(" + left + ", " + right + ")";
    }
}