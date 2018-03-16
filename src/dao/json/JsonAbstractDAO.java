package dao.json;

import Model.AbstractDAOObject;
import dao.Cache;
import dao.DAOUtils;
import dao.GenericDAO;
import dao.OutdatedObjectVersionException;

import javax.json.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class JsonAbstractDAO<E extends AbstractDAOObject> implements GenericDAO<E> {
    static final String ID = "id";
    private Cache<E> cache = new Cache<>();


    protected abstract String getFilePath();

    protected abstract E parseObjectFromJson(JsonObject jsonObject);

    protected abstract JsonObject createJsonFromObject(E object, BigInteger id);

    @Override
    public E getByPK(BigInteger id) {
        if (cache.isActual(id)) {
            return cache.get(id);
        }
        E object = null;
        try (JsonReader reader = Json.createReader(new FileReader(getFilePath()))) {
            JsonArray jsonObjectsArray = reader.readArray();
            for (int i = 0; i < jsonObjectsArray.size(); i++) {
                JsonObject jsonObject = jsonObjectsArray.getJsonObject(i);
                BigInteger currID = new BigInteger(jsonObject.getString(ID));
                if (id.equals(currID)) {
                    object = parseObjectFromJson(jsonObject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cache.add(object);
        return object;
    }

    @Override
    public Set<E> getAll() {
        Set<E> objectSet = new LinkedHashSet<>();
        try (JsonReader reader = Json.createReader(new FileReader(getFilePath()))) {
            JsonArray jsonObjectsArray = reader.readArray();
            for (int i = 0; i < jsonObjectsArray.size(); i++) {
                JsonObject jsonObject = jsonObjectsArray.getJsonObject(i);
                objectSet.add(parseObjectFromJson(jsonObject));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (E item : objectSet) {
            cache.add(item);
        }

        return objectSet;
    }

    @Override
    public BigInteger insert(E object) {
        BigInteger id = DAOUtils.generateID(1);
        JsonObject jsonObject = createJsonFromObject(object, id);

        try {
            JsonReader reader = Json.createReader(new FileReader(getFilePath()));
            JsonArray array = reader.readArray();
            reader.close();

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (int i = 0; i < array.size(); i++) {
                arrayBuilder.add(array.getJsonObject(i));
            }
            arrayBuilder.add(jsonObject);
            array = arrayBuilder.build();

            JsonWriter writer = Json.createWriter(new FileWriter(getFilePath()));
            writer.writeArray(array);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void update(E object) throws OutdatedObjectVersionException {
        if (!cache.isCorrectVersion(object)) {
            throw new OutdatedObjectVersionException();
        }
        JsonObject jsonObject = createJsonFromObject(object, object.getId());

        try {
            JsonReader reader = Json.createReader(new FileReader(getFilePath()));
            JsonArray array = reader.readArray();
            reader.close();

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (int i = 0; i < array.size(); i++) {
                if (!array.getJsonObject(i).getString(ID).equals(object.getId().toString())) {
                    arrayBuilder.add(array.getJsonObject(i));
                } else {
                    arrayBuilder.add(jsonObject);
                }
            }
            array = arrayBuilder.build();

            JsonWriter writer = Json.createWriter(new FileWriter(getFilePath()));
            writer.writeArray(array);
            writer.close();
            cache.update(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(E object) {
        try {
            JsonReader reader = Json.createReader(new FileReader(getFilePath()));
            JsonArray array = reader.readArray();
            reader.close();

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (int i = 0; i < array.size(); i++) {
                if (!array.getJsonObject(i).getString(ID).equals(object.getId().toString())) {
                    arrayBuilder.add(array.getJsonObject(i));

                }
            }
            array = arrayBuilder.build();

            JsonWriter writer = Json.createWriter(new FileWriter(getFilePath()));
            writer.writeArray(array);
            writer.close();
            cache.remove(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
