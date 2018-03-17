package dao.binary;

import Model.AbstractDAOObject;
import dao.Cache;
import dao.DAOUtils;
import dao.GenericDAO;
import dao.OutdatedObjectVersionException;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class BinaryAbstractDAO<E extends AbstractDAOObject> implements GenericDAO<E> {
    private Cache<E> cache = new Cache<>();

    private String DIRECTORY_PATH = "./binary_files/";

    @Override
    public E getByPK(BigInteger id) {
        if (cache.isActual(id)) {
            return cache.get(id);
        }

        E object;
        Map<BigInteger, E> map = readMap();
        object = map.get(id);

        cache.add(object);
        return object;
    }

    @Override
    public Set<E> getAll() {
        Set<E> objectSet = new LinkedHashSet<>();
        E object = null;
        Map<BigInteger, E> map = readMap();
        for (BigInteger id : map.keySet()) {
            object = map.get(id);
            objectSet.add(object);
            cache.add(object);
        }

        return objectSet;
    }

    @Override
    public BigInteger insert(E object) {
        BigInteger id;

        Map<BigInteger, E> map = readMap();
        id = DAOUtils.generateID(1);
        E objectToInsert = prepareObjectWithId(object, id);
        map.put(id, objectToInsert);
        writeMap(map);


        return id;
    }


    @Override
    public void update(E object) throws OutdatedObjectVersionException {
        if (!cache.isCorrectVersion(object)) {
            throw new OutdatedObjectVersionException();
        }
        Map<BigInteger, E> map = readMap();
        map.replace(object.getId(), object);
        writeMap(map);
    }

    @Override
    public void delete(E object) {
        Map<BigInteger, E> map = readMap();
        map.remove(object);
        writeMap(map);
        cache.remove(object);


    }

    private Map<BigInteger, E> readMap() {
        Map<BigInteger, E> map = null;
        try {
            FileInputStream fileIn = new FileInputStream(DIRECTORY_PATH + getFileName());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            map = (Map<BigInteger, E>) in.readObject();
            in.close();
            fileIn.close();
        } catch (EOFException e) {
            map = new HashMap<>();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return map;
    }

    private void writeMap(Map<BigInteger, E> map) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(DIRECTORY_PATH + getFileName());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(map);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    abstract String getFileName();

    abstract E prepareObjectWithId(E object, BigInteger id);
}
