package se.liu.jonbj802.collisions;

import java.util.HashMap;
import java.util.Map;

/**
 * CollisionHandler allows registering CollisionActions to run when a
 * collision occurs between two set CollisionTypes.
 */
public class CollisionHandler
{
    private Map<Pair, CollisionAction> collisionMap = new HashMap<>();
    private boolean enabled = true;

    public void register(final CollisionType collider1, final CollisionType collider2, final CollisionAction action) {
        collisionMap.put(new Pair(collider1, collider2), action);
    }

    public void register(final CollisionType collider1, final CollisionType collider2) {
        collisionMap.put(new Pair(collider1, collider2), () -> {});
    }

    public boolean collide(final CollisionType collider1, final CollisionType collider2) {
        final CollisionAction action = collisionMap.get(new Pair(collider1, collider2));
        if (!enabled || action == null) {
            return false;
        }

        action.handleCollision();
        return true;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Pair allows grouping two CollisionTypes into one type that can be used in maps.
     * The Pair type is defined like a set, meaning that Pair(a, b) == Pair(b, a).
     */
    private class Pair
    {
        private final CollisionType collider1;
        private final CollisionType collider2;

        private Pair(final CollisionType collider1, final CollisionType collider2) {
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
            return collider1.hashCode() + collider2.hashCode();
        }
    }
}
