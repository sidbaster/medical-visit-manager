package pl.medical.visit.manager.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Visit {
    private long visitId;
    private Patient patient;
    private LocalDateTime visitDate;
    private String diagnosis;
    private BigDecimal amount;

    public Visit(long visitId, Patient patient, LocalDateTime visitDate, String diagnosis, BigDecimal amount) {
        this.visitId = visitId;
        this.patient = patient;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    public LocalDateTime getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDateTime visitDate) {
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
