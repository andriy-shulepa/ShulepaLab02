package dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface GenericDAO<E> {
    E getByPK(BigInteger id);
    Set<E> getAll();
    BigInteger insert(E object);
    void update (E object);
    void delete (E object);
}

