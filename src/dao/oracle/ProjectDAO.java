package dao.oracle;

import Model.Project;

import java.math.BigInteger;

public class ProjectDAO extends OracleAbstractDAO<Project> {

    private static final String OBJECT_TYPE = "Project";

    public ProjectDAO(Roles role) {
        super(role);
    }

    @Override
    String getObjectType() {
        return OBJECT_TYPE;
    }

    @Override
    Project getObject(BigInteger id) {
        return new Project(id);
    }
}
