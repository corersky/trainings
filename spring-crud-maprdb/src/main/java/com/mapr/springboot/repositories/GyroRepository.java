package com.mapr.springboot.repositories;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import com.mapr.springboot.model.Esp32Gyro;
import org.ojai.Document;
import org.ojai.DocumentStream;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Component
public class GyroRepository {
    Table table = MapRDB.getTable("/demo/esp32_gyro");


    public void save(Esp32Gyro gyro) {
        if (gyro.getId() == null) {
            gyro.setId(UUID.randomUUID().toString());
        }
        Document doc = MapRDB.newDocument()
                .set("temperature", gyro.getTemperature())
                .set("raw_x", gyro.getRawX())
                .set("raw_y", gyro.getRawY())
                .set("raw_z", gyro.getRawZ())
                .set("norm_x", gyro.getNormX())
                .set("norm_y", gyro.getNormY())
                .set("norm_z", gyro.getNormZ());
        table.insertOrReplace(gyro.getId(), doc);
        table.flush();
    }

    public void delete(String id) {
        table.delete(id);
        table.flush();
    }

    public List<Esp32Gyro> findAll() {
        DocumentStream documents = table.find();
        return getGyros(documents);

    }

    private List<Esp32Gyro> getGyros(DocumentStream documents) {
        Iterator<Document> iterator = documents.iterator();
        List<Esp32Gyro> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            result.add(maprEsp32Gyro(next));
        }
        documents.close();
        return result;
    }

    private Esp32Gyro maprEsp32Gyro(Document next) {
        return new Esp32Gyro(next.getIdString(), next.getDouble("temperature"), next.getDouble("raw_x"), next.getDouble("raw_y"), next.getDouble("raw_z"), next.getDouble("norm_x"), next.getDouble("norm_y"), next.getDouble("norm_z"));
    }

    public Esp32Gyro findOne(String id) {
        Document byId = table.findById(id);
        if (byId == null) {
            return new Esp32Gyro(id, 0d, 0d, 0d, 0d, 0d, 0d, 0d);
        }
        return maprEsp32Gyro(byId);
    }


    public void deleteAll() {
        List<Esp32Gyro> all = findAll();
        for (Esp32Gyro esp32Gyro : all) {
            delete(esp32Gyro.getId());
        }
    }
}
