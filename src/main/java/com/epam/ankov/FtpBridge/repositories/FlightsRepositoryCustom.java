package com.epam.ankov.FtpBridge.repositories;

import com.mongodb.client.result.UpdateResult;

public interface FlightsRepositoryCustom {
    UpdateResult appendUser(String firstName, String secondName);
    UpdateResult appendFlightNumber(String flightNumber);
}
