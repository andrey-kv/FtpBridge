package com.epam.ankov.FtpBridge.repositories;

import com.epam.ankov.FtpBridge.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightsRepository extends MongoRepository<User, String>, FlightsRepositoryCustom {
}
