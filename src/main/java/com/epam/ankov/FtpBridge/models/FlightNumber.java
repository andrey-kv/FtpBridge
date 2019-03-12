package com.epam.ankov.FtpBridge.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "flight_number")
public class FlightNumber {

    @Id
    private ObjectId _id;
    private String flightNnumber;

    public FlightNumber(String flightNnumber) {
        this.flightNnumber = flightNnumber;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getFlightNnumber() {
        return flightNnumber;
    }

    public void setFlightNnumber(String flightNnumber) {
        this.flightNnumber = flightNnumber;
    }

    @Override
    public String toString() {
        return "FlightNumber{" +
                "_id=" + _id +
                ", flightNnumber='" + flightNnumber + '\'' +
                '}';
    }
}
