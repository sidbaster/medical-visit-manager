package pl.medical.visit.manager.dao;

import pl.medical.visit.manager.model.Visit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VisitDao {

    List<Visit> findByPatientId(Long patientId);

    boolean save(Visit visit, Long patientId);

    BigDecimal sumAmountByDateBetween(LocalDate start, LocalDate end);
}
