package pl.medical.visit.manager.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Visit {
    private long visitId;
    private long patientId;
    private LocalDate visitDate;
    private String diagnosis;
    private BigDecimal amount;

    public Visit(long visitId, long patientId, LocalDate visitDate, String diagnosis, BigDecimal amount) {
        this.visitId = visitId;
        this.patientId = patientId;
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.amount = amount;
    }

    public Visit() {
    }

    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public long getPatient() {
        return patientId;
    }

    public void setPatient(long patient) {
        this.patientId = patient;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "visitId=" + visitId +
                ", visitDate=" + visitDate +
                ", diagnosis='" + diagnosis + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return visitId == visit.visitId && Objects.equals(visitDate, visit.visitDate) && Objects.equals(diagnosis, visit.diagnosis) && Objects.equals(amount, visit.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitId, visitDate, diagnosis, amount);
    }
}
