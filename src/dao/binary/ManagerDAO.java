package dao.binary;

import Model.Manager;

import java.math.BigInteger;

public class ManagerDAO extends BinaryAbstractDAO<Manager> {
    @Override
    String getFileName() {
        return "managers.ser";
    }

    @Override
    Manager prepareObjectWithId(Manager object, BigInteger id) {
        return new Manager(id, object);
    }
}
