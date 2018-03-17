package dao.binary;

import Model.Customer;

import java.math.BigInteger;

public class CustomerDAO extends BinaryAbstractDAO<Customer> {
    @Override
    String getFileName() {
        return "customers.ser";
    }

    @Override
    Customer prepareObjectWithId(Customer object, BigInteger id) {
        return new Customer(id, object);
    }
}
