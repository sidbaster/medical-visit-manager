package pl.medical.visit.manager.service;

import pl.medical.visit.manager.model.Visit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitService {

    boolean save(Visit visit);

    List<Optional<Visit>> findByPatientId(Long patientId);

    BigDecimal sumAmountByDateBetween(LocalDate start, LocalDate end);
}
