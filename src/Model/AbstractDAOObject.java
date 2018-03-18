package Model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringJoiner;

public abstract class AbstractDAOObject implements Serializable {
    static final Set<String> attributes = new LinkedHashSet<>();

    static Set<ForeignAttributeType> foreignAttributes = new LinkedHashSet<>();

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

    public Set<ForeignAttributeType> getForeignAttributes() {
        return foreignAttributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getAttribute(String attributeName);

    public abstract void setAttribute(String attributeName, String attributeValue);

    Set<BigInteger> stringToSet(String toParse) {
        String[] values = toParse.split(",");
        Set<BigInteger> result = new LinkedHashSet<>();
        for (String value : values) {
            result.add(new BigInteger(value));
        }
        return result;
    }

    String setToString(Set<BigInteger> set) {
        StringJoiner joiner = new StringJoiner(",");
        for (BigInteger item : set) {
            joiner.add(item.toString());
        }

        return joiner.toString();
    }
}
