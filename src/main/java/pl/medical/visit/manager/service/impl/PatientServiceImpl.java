package pl.medical.visit.manager.service.impl;

import pl.medical.visit.manager.dao.PatientDao;
import pl.medical.visit.manager.dao.jdbc.PatientDaoJdbc;
import pl.medical.visit.manager.model.Patient;
import pl.medical.visit.manager.service.PatientService;

public class PatientServiceImpl implements PatientService {

    PatientDao patientDao = PatientDaoJdbc.getInstance();

    @Override
    public boolean save(Patient patient) {
        return patientDao.save(patient);
    }
}
