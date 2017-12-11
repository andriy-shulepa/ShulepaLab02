package dao.collectoins;

import Model.Sprint;

import java.math.BigInteger;

public class SprintDAO extends CollectionsAbsractDAO<Sprint>{


    @Override
    protected Sprint cloneObjectWithId(BigInteger id, Sprint object) {
        return new Sprint(id,object);
    }
}
