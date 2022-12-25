package io.springworks.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.springworks.models.Ledger;

public interface LedgerRepo extends MongoRepository<Ledger, Integer> {

}
