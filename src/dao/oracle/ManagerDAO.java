package dao.oracle;

import Model.Manager;

import java.math.BigInteger;

public class ManagerDAO extends OracleAbstractDAO<Manager> {

    private static final String OBJECT_TYPE = "Manager";

    public ManagerDAO(Roles role) {
        super(role);
    }

    @Override
    String getObjectType() {
        return OBJECT_TYPE;
    }

    @Override
    Manager getObject(BigInteger id) {
        return new Manager(id);
    }
}
