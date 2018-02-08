package dao.oracle;

import Model.Customer;

import java.math.BigInteger;

public class CustomerDAO extends OracleAbstractDAO<Customer> {
    private static final String OBJECT_TYPE = "Customer";

    public CustomerDAO(Roles role) {
        super(role);
    }

    @Override
    String getObjectType() {
        return OBJECT_TYPE;
    }

    @Override
    Customer getObject(BigInteger id) {
        return new Customer(id);
    }


}
