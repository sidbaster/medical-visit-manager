package pl.medical.visit.manager;

import pl.medical.visit.manager.UI.Menu;
import pl.medical.visit.manager.service.impl.PatientServiceImpl;
import pl.medical.visit.manager.service.impl.VisitServiceImpl;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu(new PatientServiceImpl(), new VisitServiceImpl());
        menu.showMenu();
    }
}