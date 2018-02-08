package dao.oracle;

import Model.Sprint;

import java.math.BigInteger;

public class SprintDAO extends OracleAbstractDAO<Sprint> {

    private static final String OBJECT_TYPE = "Sprint";

    public SprintDAO(Roles role) {
        super(role);
    }

    @Override
    String getObjectType() {
        return OBJECT_TYPE;
    }

    @Override
    Sprint getObject(BigInteger id) {
        return new Sprint(id);
    }
}
