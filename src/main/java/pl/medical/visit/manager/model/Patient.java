package pl.medical.visit.manager.model;

import java.util.Objects;

public class Patient {
    private long patientId;
    private String firstName;
    private String lastName;
    private String pesel;


    public Patient(long patientId, String firstName, String lastName, String pesel) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public Patient() {
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patientId == patient.patientId && Objects.equals(firstName, patient.firstName) && Objects.equals(lastName, patient.lastName) && Objects.equals(pesel, patient.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, firstName, lastName, pesel);
    }
}
