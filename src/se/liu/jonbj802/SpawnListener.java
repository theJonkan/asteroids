package se.liu.jonbj802;

import se.liu.jonbj802.moveable_objects.MoveableObject;

import java.util.List;

/**
 * SpawnListener allows for components to request new MoveableObjects to spawn.
 */
public interface SpawnListener
{
    public void spawn(final MoveableObject... objects);
}
