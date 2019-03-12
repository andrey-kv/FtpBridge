package com.epam.ankov.FtpBridge.repositories;

import com.epam.ankov.FtpBridge.models.FlightNumber;
import com.epam.ankov.FtpBridge.models.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class FlightsRepositoryCustomImpl implements FlightsRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public UpdateResult appendUser(String firstName, String secondName) {
        createUserCollection();
        Query query = new Query(Criteria.where("firstName").is(firstName).and("secondName").is(secondName));
        Update upd = new Update();
        upd.set("firstName", firstName);
        upd.set("secondName", secondName);
        return mongoTemplate.upsert(query, upd, User.class);
    }

    @Override
    public UpdateResult appendFlightNumber(String flightNumber) {
        createFlightNumberCollection();
        Query query = new Query(Criteria.where("flightNumber").is(flightNumber));
        Update upd = new Update();
        upd.set("number", flightNumber);
        return mongoTemplate.upsert(query, upd, FlightNumber.class);
    }

    private void createUserCollection() {
        if (!mongoTemplate.collectionExists(User.class)) {
            MongoCollection<Document> us = mongoTemplate.createCollection(User.class);
            us.createIndex(Indexes.ascending("firstName", "secondName"));
        }
    }

    private void createFlightNumberCollection() {
        if (!mongoTemplate.collectionExists(FlightNumber.class)) {
            MongoCollection<Document> us = mongoTemplate.createCollection(FlightNumber.class);
            us.createIndex(Indexes.ascending("flightNumber"));
        }
    }
}
