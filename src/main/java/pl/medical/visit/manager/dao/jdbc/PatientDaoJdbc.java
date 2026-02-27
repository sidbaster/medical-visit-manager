package pl.medical.visit.manager.dao.jdbc;

import pl.medical.visit.manager.dao.ConnectionManager;
import pl.medical.visit.manager.dao.PatientDao;
import pl.medical.visit.manager.exception.DaoException;
import pl.medical.visit.manager.model.Patient;
import pl.medical.visit.manager.model.Visit;
import java.sql.*;

public class PatientDaoJdbc implements PatientDao {
    private static final PatientDaoJdbc INSTANCE = new PatientDaoJdbc();
    private static final String INSERT_PATIENT_SQL_SCRIPT = "INSERT INTO patient(first_name, last_name, pesel) VALUES(?, ?, ?)";
    private static final String INSERT_VISIT_SQL_SCRIPT = "INSERT INTO visit(patient_id, visit_date, diagnosis, amount) VALUES(?, ?, ?, ?)";
    private static final String SELECT_PATIENT_ID_BY_PESEL = "SELECT patient_id FROM patient WHERE pesel = ?";

    public static PatientDaoJdbc getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean save(Patient patient) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(INSERT_PATIENT_SQL_SCRIPT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setString(3, patient.getPesel());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating patient failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patient.setPatientId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating patient failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long getPatientIdByPesel(String pesel) {
        long id = 0;
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(SELECT_PATIENT_ID_BY_PESEL)) {

            statement.setString(1, pesel);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }

            return id;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean registerPatientAndVisit(Patient patient, Visit visit) {
        try (Connection connection = ConnectionManager.open()) {
            connection.setAutoCommit(false);

            try (PreparedStatement savePatientStatement = connection.prepareStatement(INSERT_PATIENT_SQL_SCRIPT, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement saveVisitStatement = connection.prepareStatement(INSERT_VISIT_SQL_SCRIPT, Statement.RETURN_GENERATED_KEYS)) {

                savePatientStatement.setString(1, patient.getFirstName());
                savePatientStatement.setString(2, patient.getLastName());
                savePatientStatement.setString(3, patient.getPesel());

                savePatientStatement.executeUpdate();

                try (ResultSet rs = savePatientStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        patient.setPatientId(rs.getLong(1));
                    } else {
                        throw new SQLException("No patient ID generated");
                    }
                }

                saveVisitStatement.setLong(1, patient.getPatientId());
                saveVisitStatement.setDate(2, Date.valueOf(visit.getVisitDate()));
                saveVisitStatement.setString(3, visit.getDiagnosis());
                saveVisitStatement.setBigDecimal(4, visit.getAmount());

                saveVisitStatement.executeUpdate();

                try (ResultSet rs = saveVisitStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        visit.setVisitId(rs.getLong(1));
                    } else {
                        throw new SQLException("No visit ID generated");
                    }
                }

                connection.commit();

                return true;

            } catch (Exception e) {
                connection.rollback();
                throw e;
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
