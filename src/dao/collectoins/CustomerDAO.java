package dao.collectoins;

import Model.Customer;

import java.math.BigInteger;

public class CustomerDAO extends CollectionsAbsractDAO<Customer> {

    @Override
    protected Customer cloneObjectWithId(BigInteger id, Customer object) {
        return new Customer(id,object);
    }


}
