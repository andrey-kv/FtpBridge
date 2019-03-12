package com.epam.ankov.FtpBridge.repositories;

import com.epam.ankov.FtpBridge.models.FlightRecord;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface FlightRecordsRepository extends MongoRepository<FlightRecord, String> {
}
