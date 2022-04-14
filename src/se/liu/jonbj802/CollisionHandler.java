package se.liu.jonbj802;

import javax.swing.AbstractAction;
import java.util.HashMap;
import java.util.Map;

public class CollisionHandler
{
    private Map<Pair, AbstractAction> collisionMap = new HashMap<>();

    public void register(final CollisionType collider1, final CollisionType collider2, final AbstractAction action) {
        collisionMap.put(new Pair(collider1, collider2), action);
    }

    public boolean collide(final CollisionType collider1, final CollisionType collider2) {
        final AbstractAction action = collisionMap.get(new Pair(collider1, collider2));
        if (action == null) {
            return false;
        }

        action.actionPerformed(null);
        return true;
    }
}
