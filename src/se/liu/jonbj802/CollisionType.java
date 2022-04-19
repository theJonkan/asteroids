package se.liu.jonbj802;

/**
 * CollisionType defines set types of collisions. These are used to
 * by MoveableObjects and can then be registered with the CollisionHandler.
 */
public enum CollisionType
{
    ASTEROID, SAUCER, BULLET_PLAYER, BULLET_ENEMY, ROCKET
}
