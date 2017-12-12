package dao;

import java.math.BigInteger;
import java.util.Set;

public interface GenericDAO<E> {
    E getByPK(BigInteger id) throws IllegalRoleException;
    Set<E> getAll() throws IllegalRoleException;
    BigInteger insert(E object) throws IllegalRoleException;
    void update (E object) throws OutdatedObjectVersionException, IllegalRoleException;
    void delete (E object) throws IllegalRoleException;
}

