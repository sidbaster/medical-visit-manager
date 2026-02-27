package pl.medical.visit.manager.service.impl;

import pl.medical.visit.manager.dao.VisitDao;
import pl.medical.visit.manager.dao.jdbc.VisitDaoJdbc;
import pl.medical.visit.manager.model.Visit;
import pl.medical.visit.manager.service.VisitService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class VisitServiceImpl implements VisitService {
    VisitDao visitDao = VisitDaoJdbc.getInstance();


    @Override
    public boolean save(Visit visit) {
        validateVisit(visit);
        return visitDao.save(visit);
    }

    @Override
    public List<Optional<Visit>> findByPatientId(Long patientId) {
        return visitDao.findByPatientId(patientId);
    }

    @Override
    public BigDecimal sumAmountByDateBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Obie daty są wymagane");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Data początkowa nie może być późniejsza niż data końcowa");
        }

        return visitDao.sumAmountByDateBetween(start, end);
    }

    private void validateVisit(Visit visit) {
        if ( visit.getPatient() <= 0) {
            throw new IllegalArgumentException("Nieprawidłowy ID pacjenta");
        }
        if (visit.getVisitDate() == null) {
            throw new IllegalArgumentException("Data wizyty jest wymagana");
        }
        if (visit.getVisitDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data wizyty nie może być w przeszłości");
        }
        if (visit.getDiagnosis() == null || visit.getDiagnosis().trim().isEmpty()) {
            throw new IllegalArgumentException("Rozpoznanie jest wymagane");
        }
        if (visit.getAmount() == null || visit.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Kwota nie może być ujemna");
        }
    }
}
