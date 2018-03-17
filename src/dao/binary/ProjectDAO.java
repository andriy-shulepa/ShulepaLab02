package dao.binary;

import Model.Project;

import java.math.BigInteger;

public class ProjectDAO extends BinaryAbstractDAO<Project> {
    @Override
    String getFileName() {
        return "projects.ser";
    }

    @Override
    Project prepareObjectWithId(Project object, BigInteger id) {
        return new Project(id, object);
    }
}
