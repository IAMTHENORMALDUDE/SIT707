package ontrack.model;

import java.util.Objects;

/**
 * Represents a unit of study in OnTrack.
 */
public class Unit {
    private final String id;
    private final String name;

    public Unit(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit name cannot be null or empty.");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(id, unit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Unit{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
