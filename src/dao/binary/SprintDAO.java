package dao.binary;

import Model.Sprint;

import java.math.BigInteger;

public class SprintDAO extends BinaryAbstractDAO<Sprint> {
    @Override
    String getFileName() {
        return "sprints.ser";
    }

    @Override
    Sprint prepareObjectWithId(Sprint object, BigInteger id) {
        return new Sprint(id, object);
    }
}
