package se.liu.jonbj802;

import se.liu.jonbj802.moveable_objects.MoveableObject;

/**
 * SpawnListener allows for requesting to spawn new MoveableObjects.
 */
public interface SpawnListener
{
    public void spawn(final MoveableObject... objects);
}
