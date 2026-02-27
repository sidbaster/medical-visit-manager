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
        return visitDao.save(visit);
    }

    @Override
    public List<Optional<Visit>> findByPatientId(Long patientId) {
        return visitDao.findByPatientId(patientId);
    }

    @Override
    public BigDecimal sumAmountByDateBetween(LocalDate start, LocalDate end) {
        return visitDao.sumAmountByDateBetween(start, end);
    }
}
