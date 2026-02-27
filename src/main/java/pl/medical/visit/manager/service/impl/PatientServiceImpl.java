package pl.medical.visit.manager.service.impl;

import pl.medical.visit.manager.dao.PatientDao;
import pl.medical.visit.manager.dao.jdbc.PatientDaoJdbc;
import pl.medical.visit.manager.model.Patient;
import pl.medical.visit.manager.model.Visit;
import pl.medical.visit.manager.service.PatientService;

import java.sql.SQLException;

public class PatientServiceImpl implements PatientService {

    PatientDao patientDao = PatientDaoJdbc.getInstance();

    @Override
    public boolean save(Patient patient) {
        return patientDao.save(patient);
    }

    @Override
    public long getPatientIdByPesel(String pesel) {
        return patientDao.getPatientIdByPesel(pesel);
    }

    @Override
    public boolean registerPatientAndVisit(Patient patient, Visit visit) {
        try {
            return patientDao.registerPatientAndVisit(patient, visit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
