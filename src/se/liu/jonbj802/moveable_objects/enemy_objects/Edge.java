package se.liu.jonbj802.moveable_objects.enemy_objects;

/**
 * Edge is an enum with the different edges of the screen.
 */
public enum Edge
{
    LEFT, RIGHT, TOP, BOTTOM;

    // Edge.values() allocates each time it is called. Cache the result.
    private static final int LENGTH = Edge.values().length;

    public static Edge get(final int i){
        return values()[i];
    }
    public static int size(){
        return LENGTH;
    }
}
