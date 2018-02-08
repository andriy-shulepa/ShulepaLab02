package Model;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractDAOObject {
    static final Set<String> attributes = new LinkedHashSet<>();
    BigInteger id;
    int version;
    String name;

    public AbstractDAOObject() {
        this(null);
    }

    public AbstractDAOObject(BigInteger id) {
        this.id = id;
        version = 1;
    }

    public BigInteger getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<String> getAttributesSet() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getAttribute(String attributeName);

    public abstract void setAttribute(String attributeName, String attributeValue);
}
