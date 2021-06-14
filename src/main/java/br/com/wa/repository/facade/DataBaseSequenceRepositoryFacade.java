package br.com.wa.repository.facade;

import br.com.wa.domain.user.DataBaseSequence;

public interface DataBaseSequenceRepositoryFacade {
  DataBaseSequence findNextSequence(String sequenceName);
}
