package pl.medical.visit.manager.dao;

import pl.medical.visit.manager.model.Patient;

public interface PatientDao {

    boolean save(Patient patient);
}
