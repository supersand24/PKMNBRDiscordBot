package Pokemon;

import java.util.ArrayList;
import java.util.List;

public class Type {

    private final String name;

    private final List<Type> superEffectives = new ArrayList<>();
    private final List<Type> notEffectives = new ArrayList<>();
    private final List<Type> immunities = new ArrayList<>();

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Type> getSuperEffectives() {
        return superEffectives;
    }

    public List<Type> getNotEffectives() {
        return notEffectives;
    }

    public List<Type> getImmunities() {
        return immunities;
    }

    public Type addSuperEffective(Type type) {
        superEffectives.add(type);
        return this;
    }

    public Type addNotEffective(Type type) {
        notEffectives.add(type);
        return this;
    }

    public Type addImmunity(Type type) {
        immunities.add(type);
        return this;
    }

}
