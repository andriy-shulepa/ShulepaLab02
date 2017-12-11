package dao.collectoins;

import Model.Employee;

import java.math.BigInteger;

public class EmployeeDAO extends CollectionsAbsractDAO<Employee> {
    @Override
    protected Employee cloneObjectWithId(BigInteger id, Employee object) {
        return new Employee(id,object);
    }
}
