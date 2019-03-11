package com.epam.ankov.FtpBridge.models;

import java.util.Objects;

public class FlightRecord {

    public String firstName;
    public String secondName;
    public String flightNumber;

    public FlightRecord() {}

    public FlightRecord(String firstName, String secondName, String flightNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.flightNumber = flightNumber;
    }

    public FlightRecord(String flightInfo) {
        String[] fields = flightInfo.split(",");
        this.firstName = fields[0];
        this.secondName = fields[1];
        this.flightNumber = fields[2];
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightnumber) {
        this.flightNumber = flightnumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlightRecord)) return false;
        FlightRecord that = (FlightRecord) o;
        return firstName.equals(that.firstName) &&
                secondName.equals(that.secondName) &&
                flightNumber.equals(that.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, flightNumber);
    }
}
