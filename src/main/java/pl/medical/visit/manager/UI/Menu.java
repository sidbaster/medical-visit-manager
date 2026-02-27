package pl.medical.visit.manager.UI;

import pl.medical.visit.manager.model.Patient;
import pl.medical.visit.manager.model.Visit;
import pl.medical.visit.manager.service.PatientService;
import pl.medical.visit.manager.service.VisitService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final PatientService patientService;
    private final VisitService visitService;

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Menu(PatientService patientService, VisitService visitService) {
        this.patientService = patientService;
        this.visitService = visitService;
    }

    public void showMenu() {
        printMenu();
        String option = scanner.nextLine().trim().toLowerCase();

        switch (option) {
            case "a":
                addPatient();
                break;
            case "b":
                addVisit();
                break;
            case "c":
                showPatientVisits();
                break;
            case "d":
                calculateVisitSum();
                break;
            case "e":
                registerPatientWithVisit();
                break;
            default:
                System.out.println("Nieprawidłowa opcja.");
        }
    }

    private void printMenu() {
        System.out.println("Wybierz swoją opcję (a, b, c, d lub e)");
        System.out.println("a. dodanie pacjenta");
        System.out.println("b. dodanie wizyty");
        System.out.println("c. dobranie listy wizyt pacjenta");
        System.out.println("d. obliczenie sumy kwot wizyt w zadanym zakresie dat");
        System.out.println("e. transakcja (zapis pacjenta + wizyty w jednej transakcji)");
    }

    private void addPatient() {
        System.out.print("Imię: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Nazwisko: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("PESEL (11 cyfr): ");
        String pesel = scanner.nextLine().trim();

        boolean result = patientService.save(new Patient(firstName, lastName, pesel));

        System.out.println(result ? "Pacjent został dodany." : "Błąd: pacjent nie został dodany.");
    }

    private void addVisit() {
        Long patientId = readPatientIdByPesel();
        LocalDate visitDate = readVisitDate();
        String diagnosis = readDiagnosis();
        BigDecimal amount = readAmount();

        Visit visit = new Visit(patientId, visitDate, diagnosis, amount);

        boolean result = visitService.save(visit);

        System.out.println(result
                ? "Wizyta została dodana."
                : "Błąd: wizyta nie została dodana.");
    }

    private void showPatientVisits() {
        Long patientId = readPatientIdByPesel();

        List<Optional<Visit>> visits = visitService.findByPatientId(patientId);

        if (visits.isEmpty()) {
            System.out.println("Brak wizyt.");
            return;
        }

        visits.forEach(v ->
                System.out.printf("|%d|%d|%s|%s|%s|%n",
                        v.get().getVisitId(),
                        v.get().getPatient(),
                        v.get().getVisitDate(),
                        v.get().getDiagnosis(),
                        v.get().getAmount())
        );
    }

    private void calculateVisitSum() {
        LocalDate start = readDate("Data początkowa (yyyy-MM-dd): ");
        LocalDate end;

        while (true) {
            end = readDate("Data końcowa (yyyy-MM-dd): ");
            if (!end.isBefore(start)) break;
            System.out.println("Data końcowa nie może być wcześniejsza.");
        }

        BigDecimal total =
                visitService.sumAmountByDateBetween(start, end);

        System.out.printf("Suma od %s do %s: %.2f zł%n",
                start, end, total);

        if (total.compareTo(BigDecimal.ZERO) == 0) {
            System.out.println("Brak wizyt w podanym okresie.");
        }
    }

    private void registerPatientWithVisit() {

        System.out.print("Imię: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Nazwisko: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("PESEL: ");
        String pesel = scanner.nextLine().trim();

        Patient patient =
                new Patient(firstName, lastName, pesel);

        LocalDate visitDate = readVisitDate();
        String diagnosis = readDiagnosis();
        BigDecimal amount = readAmount();

        Visit visit =
                new Visit(visitDate, diagnosis, amount);

        boolean result =
                patientService.registerPatientAndVisit(patient, visit);

        System.out.println(result
                ? "Pacjent i wizyta zapisani."
                : "Błąd podczas zapisu.");
    }

    private Long readPatientIdByPesel() {
        while (true) {
            System.out.print("Podaj PESEL pacjenta: ");
            String pesel = scanner.nextLine().trim();

            long id = patientService.getPatientIdByPesel(pesel);

            if (id != 0) return id;

            System.out.println("Nie znaleziono pacjenta.");
        }
    }

    private LocalDate readVisitDate() {
        while (true) {
            LocalDate date = readDate("Data wizyty (yyyy-MM-dd): ");
            if (!date.isBefore(LocalDate.now())) return date;
            System.out.println("Data nie może być w przeszłości.");
        }
    }

    private LocalDate readDate(String message) {
        while (true) {
            System.out.print(message);
            try {
                return LocalDate.parse(scanner.nextLine().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Nieprawidłowy format daty.");
            }
        }
    }

    private String readDiagnosis() {
        while (true) {
            System.out.print("Rozpoznanie: ");
            String diagnosis = scanner.nextLine().trim();

            if (!diagnosis.isEmpty()) return diagnosis;

            System.out.println("Rozpoznanie jest wymagane.");
        }
    }

    private BigDecimal readAmount() {
        while (true) {
            System.out.print("Kwota (np. 150.00): ");
            try {
                BigDecimal amount = new BigDecimal(scanner.nextLine().trim());

                if (amount.compareTo(BigDecimal.ZERO) >= 0) return amount;

                System.out.println("Kwota nie może być ujemna.");
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowy format kwoty.");
            }
        }
    }
}
