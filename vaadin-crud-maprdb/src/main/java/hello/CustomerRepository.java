package hello;

import com.mapr.db.MapRDB;
import com.mapr.db.Table;
import org.ojai.Document;
import org.ojai.DocumentStream;
import org.ojai.store.QueryCondition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class CustomerRepository {
    Table table = MapRDB.getTable("/demo/user_profiles");

    public List<Customer> findByLastNameStartsWithIgnoreCase(String lastName) {
        QueryCondition condition = MapRDB.newCondition()
                .like("lastName", lastName + "%")
                .build();

        DocumentStream rs = table.find(condition);
        return getCustomers(rs);
    }

    public void save(Customer customer) {
        Document doc = MapRDB.newDocument()
                .set("firstName", customer.getFirstName())
                .set("lastName", customer.getLastName());
        table.insertOrReplace(customer.getId(), doc);
        table.flush();
    }

    public void delete(Customer customer) {
        table.delete(customer.getId());
    }

    public List<Customer> findAll() {
        DocumentStream documents = table.find();
        return getCustomers(documents);

    }

    private List<Customer> getCustomers(DocumentStream documents) {
        Iterator<Document> iterator = documents.iterator();
        List<Customer> result = new ArrayList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            result.add(mapCustomer(next));
        }
        documents.close();
        return result;
    }

    private Customer mapCustomer(Document next) {
        return new Customer(next.getIdString(), next.getString("firstName"), next.getString("lastName"));
    }

    public Customer findOne(String id) {
        Document byId = table.findById(id);
        if(byId == null) {
            return new Customer(id, "", "");
        }
        return mapCustomer(byId);
    }
}
