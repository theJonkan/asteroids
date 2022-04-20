package se.liu.jonbj802.collisions;

/**
 * Pair allows grouping two CollisionTypes into one type that can be used in maps.
 * The Pair type is defined like a set, meaning that Pair(a, b) == Pair(b, a).
 */
public class Pair
{
    private final CollisionType collider1;
    private final CollisionType collider2;

    public Pair(final CollisionType collider1, final CollisionType collider2) {
        this.collider1 = collider1;
        this.collider2 = collider2;
    }

    @Override public boolean equals(final Object obj) {
        if (this == obj){
            return true;
        } if (obj instanceof Pair pair) {
            return (collider1 == pair.collider1 && collider2 == pair.collider2) || (collider1 == pair.collider2 && collider2 == pair.collider1);
        }

        return false;
    }

    @Override public int hashCode() {
        return collider1.hashCode() * 37 + collider2.hashCode();
    }
}
