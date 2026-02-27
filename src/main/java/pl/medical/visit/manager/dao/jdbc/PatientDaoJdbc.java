package pl.medical.visit.manager.dao.jdbc;

import pl.medical.visit.manager.dao.ConnectionManager;
import pl.medical.visit.manager.dao.PatientDao;
import pl.medical.visit.manager.exception.DaoException;
import pl.medical.visit.manager.model.Patient;

import java.sql.*;

public class PatientDaoJdbc implements PatientDao {
    private static final PatientDaoJdbc INSTANCE = new PatientDaoJdbc();
    private static final String INSERT_SQL_SCRIPT = "INSERT INTO patient(first_name, last_name, pesel) VALUES(?, ?, ?)";

    public static PatientDaoJdbc getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean save(Patient patient) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL_SCRIPT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, patient.getFirstName());
            statement.setString(2, patient.getLastName());
            statement.setString(3, patient.getPesel());

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                patient.setPatientId(generatedKeys.getLong("patient_id"));
            }

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
