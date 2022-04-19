package se.liu.jonbj802;

import java.util.List;

/**
 * SpawnListener allows for components to request new MoveableObjects to spawn.
 */
public interface SpawnListener
{
    public void spawn(final List<MoveableObject> objects);
}
