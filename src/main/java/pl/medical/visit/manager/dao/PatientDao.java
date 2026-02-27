package pl.medical.visit.manager.dao;

import pl.medical.visit.manager.model.Patient;
import pl.medical.visit.manager.model.Visit;

import java.sql.SQLException;

public interface PatientDao {

    boolean save(Patient patient);

    long getPatientIdByPesel(String pesel);

    boolean registerPatientAndVisit(Patient patient, Visit visit) throws SQLException;
}
