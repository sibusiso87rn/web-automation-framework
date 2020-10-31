package starter.testing.core.bean;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoConfig{

    public MongoClient mongoClient() {
        return MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
    }

}
