package dao.collectoins;

import Model.Manager;

import java.math.BigInteger;

public class ManagerDAO extends CollectionsAbsractDAO<Manager> {
    @Override
    protected Manager cloneObjectWithId(BigInteger id, Manager object) {
        return new Manager(id,object);
    }
}
