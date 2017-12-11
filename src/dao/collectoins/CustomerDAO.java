package dao.collectoins;

import Model.Customer;
import dao.DAOUtils;

import java.math.BigInteger;

public class CustomerAbsractDAO extends CollectionsAbsractDAO<Customer> {

    @Override
    public BigInteger insert(Customer object) {
        BigInteger id = DAOUtils.generateID(0);
        Customer customer = new Customer(id);


        return null;
    }
}
