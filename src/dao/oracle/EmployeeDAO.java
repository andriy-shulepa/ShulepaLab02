package dao.oracle;

import Model.Employee;

import java.math.BigInteger;

public class EmployeeDAO extends OracleAbstractDAO<Employee> {

    private static final String OBJECT_TYPE = "Employee";

    public EmployeeDAO(Roles role) {
        super(role);
    }

    @Override
    String getObjectType() {
        return OBJECT_TYPE;
    }

    @Override
    Employee getObject(BigInteger id) {
        return new Employee(id);
    }
}
