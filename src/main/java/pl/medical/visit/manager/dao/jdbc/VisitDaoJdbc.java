package pl.medical.visit.manager.dao.jdbc;

import pl.medical.visit.manager.dao.ConnectionManager;
import pl.medical.visit.manager.dao.PatientDao;
import pl.medical.visit.manager.dao.VisitDao;
import pl.medical.visit.manager.exception.DaoException;
import pl.medical.visit.manager.model.Visit;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VisitDaoJdbc implements VisitDao {
    private static final VisitDao INSTANCE = new VisitDaoJdbc();
    private static final String INSERT_SQL_SCRIPT = "INSERT INTO visit(patient_id, visit_date, diagnosis, amount) VALUES(?, ?, ?, ?)";
    private static final String FIND_VISIT_BY_PATIENT_ID = "SELECT visit_id, patient_id, visit_date, diagnosis, amount FROM visit WHERE patient_id = ?";
    private static final String SUM_AMOUNT_BY_VISIT_DATE = "SELECT COALESCE(SUM(amount), 0) AS SUMA_KWOT FROM visit WHERE visit_date BETWEEN ? AND ?";


    public static VisitDao getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Optional<Visit>> findByPatientId(Long patientId) {
        List<Optional<Visit>> visits = new ArrayList<>();

        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(FIND_VISIT_BY_PATIENT_ID)) {

            statement.setLong(1, patientId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                visits.add(buildVisit(resultSet));
            }

            return visits;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean save(Visit visit) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL_SCRIPT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, visit.getVisitId());
            statement.setLong(2, visit.getPatient());
            statement.setDate(3, Date.valueOf(visit.getVisitDate()));
            statement.setString(4, visit.getDiagnosis());
            statement.setBigDecimal(5, visit.getAmount());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating visit failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    visit.setVisitId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating visit failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public BigDecimal sumAmountByDateBetween(LocalDate start, LocalDate end) {
        BigDecimal amount = BigDecimal.valueOf(0);

        try (Connection connection = ConnectionManager.open();
             PreparedStatement statement = connection.prepareStatement(SUM_AMOUNT_BY_VISIT_DATE)) {

            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                amount = resultSet.getBigDecimal(1);
            }

            return amount;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Optional<Visit> buildVisit(ResultSet resultSet) throws SQLException {
        return Optional.of(new Visit(resultSet.getLong(1),
                resultSet.getLong(2),
                resultSet.getDate(3).toLocalDate(),
                resultSet.getString(4),
                resultSet.getBigDecimal(5)));
    }
}
