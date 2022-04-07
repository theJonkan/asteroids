package se.liu.jonbj802;

/**
 * Edge is an enum with the different edges of the screen.
 */
public enum Edge
{
    LEFT, RIGHT, TOP, BOTTOM;

    // Edge.values() allocates each time it is called. Cache the result.
    private static final Edge[] VALUES = Edge.values();

    public static Edge get(final int i){
        return VALUES[i];
    }
    public static int size(){
        return VALUES.length;
    }
}
