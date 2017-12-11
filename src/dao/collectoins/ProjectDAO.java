package dao.collectoins;

import Model.Project;

import java.math.BigInteger;

public class ProjectDAO extends CollectionsAbsractDAO<Project>{

    @Override
    protected Project cloneObjectWithId(BigInteger id, Project object) {
        return new Project(id,object);
    }
}
