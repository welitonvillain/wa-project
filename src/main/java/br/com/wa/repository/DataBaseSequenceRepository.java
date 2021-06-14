package br.com.wa.repository;

import br.com.wa.domain.user.DataBaseSequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataBaseSequenceRepository extends MongoRepository<DataBaseSequence, String> {
}
