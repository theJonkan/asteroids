package se.liu.jonbj802.moveable_objects;

/**
 * EnemyObject is a superset of MoveableObject that handles health.
 */
public interface EnemyObject extends MoveableObject
{
    public int getHealth();
    public void setHealth();

}
