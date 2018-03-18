package Model;

public class ForeignAttributeType {
    public final String attributeName;
    public final String relatedAttributeName;
    public final String table;

    ForeignAttributeType(String attributeName, String relatedAttributeName, String table) {
        this.attributeName = attributeName;
        this.relatedAttributeName = relatedAttributeName;
        this.table = table;
    }
}
