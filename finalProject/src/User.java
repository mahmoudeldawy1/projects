import java.io.Serializable;

public abstract class User implements Serializable {
    private final String id;
    private final String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User[ID: " + id + ", Name: " + name + "]";
    }
}