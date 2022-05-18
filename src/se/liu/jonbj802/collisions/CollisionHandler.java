package se.liu.jonbj802.collisions;

import java.util.HashMap;
import java.util.Map;

/**
 * CollisionHandler allows registering CollisionActions to run when a collision occurs between two set CollisionTypes.
 */
public class CollisionHandler
{
    private Map<Pair, CollisionAction> collisionMap = new HashMap<>();
    private boolean enabled = true;

    public void register(final CollisionType collider1, final CollisionType collider2, final CollisionAction action) {
	collisionMap.put(new Pair(collider1, collider2), action);
    }

    public void register(final CollisionType collider1, final CollisionType collider2) {
	collisionMap.put(new Pair(collider1, collider2), () -> {
	});
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
     * Pair allows grouping two CollisionTypes into one type that can be used in maps. The Pair type is defined like a set, meaning that
     * Pair(a, b) == Pair(b, a).
     */
    private class Pair
    {
	private final CollisionType first;
	private final CollisionType second;

	private Pair(final CollisionType first, final CollisionType second) {
	    this.first = first;
	    this.second = second;
	}

	@Override public boolean equals(final Object obj) {
	    if (this == obj) {
		return true;
	    }
	    if (obj instanceof Pair pair) {
		return (first == pair.first && second == pair.second) || (first == pair.second && second == pair.first);
	    }

	    return false;
	}

	@Override public int hashCode() {
	    return first.hashCode() + second.hashCode();
	}
    }
}
