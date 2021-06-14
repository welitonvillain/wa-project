package br.com.wa.repository.facade.impl;

import br.com.wa.domain.user.DataBaseSequence;
import br.com.wa.domain.user.User;
import br.com.wa.repository.DataBaseSequenceRepository;
import br.com.wa.repository.facade.DataBaseSequenceRepositoryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class DataBaseSequenceRepositoryFacadeImpl implements DataBaseSequenceRepositoryFacade {

  @Autowired
  DataBaseSequenceRepository repository;

  @Override
  public DataBaseSequence findNextSequence(String sequenceName) {
    try {
      DataBaseSequence dataBaseSequence = repository.findById(sequenceName).orElseThrow();
      int sequence = dataBaseSequence.getSequenceValue();
      dataBaseSequence.setSequenceValue(sequence + 1);
      return repository.save(dataBaseSequence);
    } catch (NoSuchElementException ex) {
      DataBaseSequence dataBaseSequence = new DataBaseSequence();
      dataBaseSequence.setSequenceValue(1);
      dataBaseSequence.setId(User.SEQUENCE_NAME);
      return repository.save(dataBaseSequence);
    }
  }
}
