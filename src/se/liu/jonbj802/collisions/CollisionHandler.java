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

    public void register(final CollisionType collider1, final CollisionType collider2, final CollisionAction action) {
        collisionMap.put(new Pair(collider1, collider2), action);
    }

    public void register(final CollisionType collider1, final CollisionType collider2) {
        collisionMap.put(new Pair(collider1, collider2), () -> {});
    }

    public boolean collide(final CollisionType collider1, final CollisionType collider2) {
        final CollisionAction action = collisionMap.get(new Pair(collider1, collider2));
        if (action == null) {
            return false;
        }

        action.handleCollision();
        return true;
    }
}
