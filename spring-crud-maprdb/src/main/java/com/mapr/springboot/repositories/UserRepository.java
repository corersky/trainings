package com.mapr.springboot.repositories;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import com.mapr.springboot.model.User;
import org.ojai.Document;
import org.ojai.DocumentStream;
import org.ojai.store.QueryCondition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Component
public class UserRepository {
    Table table = MapRDB.getTable("/demo/user_salaries");


    public void save(User customer) {
        if(customer.getId() == null) {
             customer.setId(UUID.randomUUID().toString());
        }
        Document doc = MapRDB.newDocument()
                .set("name", customer.getName())
                .set("age", customer.getAge())
                .set("salary", customer.getSalary());
        table.insertOrReplace(customer.getId(), doc);
        table.flush();
    }

    public void delete(String userId) {
        table.delete(userId);
        table.flush();
    }

    public List<User> findAll() {
        DocumentStream documents = table.find();
        return getUsers(documents);

    }

    private List<User> getUsers(DocumentStream documents) {
        Iterator<Document> iterator = documents.iterator();
        List<User> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            result.add(maprUser(next));
        }
        documents.close();
        return result;
    }

    private User maprUser(Document next) {
        return new User(next.getIdString(), next.getString("name"), next.getInt("age"), next.getDouble("salary"));
    }

    public User findOne(String id) {
        Document byId = table.findById(id);
        if(byId == null) {
            return new User(id, "", 0, 0d);
        }
        return maprUser(byId);
    }

    public User findByName(String name) {
        QueryCondition condition = MapRDB.newCondition()
                .like("name", name + "%")
                .build();

        DocumentStream rs = table.find(condition);
        List<User> users = getUsers(rs);
        if(users.size()>0) {
            return users.get(0);
        }
        return null;
    }

    public void deleteAll() {
        List<User> all = findAll();
        for (User user : all) {
            delete(user.getId());
        }
    }
}
