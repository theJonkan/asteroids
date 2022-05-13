package se.liu.jonbj802.graphics;

/**
 * CacheEntry is a small wrapper for putting together an object with name, size and angle.
 */
public class CacheEntry
{
    final String name;
    final int size, angle;

    public CacheEntry(final String name, final int size, final int angle) {
	this.name = name;
	this.size = size;
	this.angle = angle;
    }

    @Override public boolean equals(final Object obj) {
	if (this == obj){
	    return true;
	} if (obj instanceof CacheEntry cache) {
	    return cache.name.equals(name) && cache.size == size && cache.angle == angle;
	}

	return false;
    }

    @Override public int hashCode() {
	return name.hashCode() + size + angle;
    }
}
