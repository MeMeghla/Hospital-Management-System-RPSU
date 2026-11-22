import java.util.List;
import java.util.Scanner;

/**
 * Entry point of the Hospital Management System.
 * Displays menus and reads user input from the console.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static HospitalService service = new HospitalService();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = readIntChoice();
            switch (choice) {
                case 1:
                    patientMenu();
                    break;
                case 2:
                    doctorMenu();
                    break;
                case 3:
                    appointmentMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Hospital Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 0-3.");
                    break;
            }
        }
        scanner.close();
    }

    // ---------------- Main menu ----------------

    private static void showMainMenu() {
        System.out.println("");
        System.out.println("=====================================");
        System.out.println("       HOSPITAL MANAGEMENT SYSTEM");
        System.out.println("=====================================");
        System.out.println("");
        System.out.println("1. Patient Management");
        System.out.println("2. Doctor Management");
        System.out.println("3. Appointment Management");
        System.out.println("0. Exit");
        System.out.println("");
        System.out.print("Enter your choice: ");
    }

    // ---------------- Patient menu ----------------

    private static void patientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("");
            System.out.println("--------- PATIENT MANAGEMENT ---------");
            System.out.println("");
            System.out.println("1. Add Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient");
            System.out.println("4. Update Patient");
            System.out.println("5. Delete Patient");
            System.out.println("0. Back to Main Menu");
            System.out.println("");
            System.out.print("Enter your choice: ");

            int choice = readIntChoice();
            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    viewAllPatients();
                    break;
                case 3:
                    searchPatient();
                    break;
                case 4:
                    updatePatient();
                    break;
                case 5:
                    deletePatient();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 0-5.");
                    break;
            }
        }
    }

    private static void addPatient() {
        System.out.println("");
        System.out.println("--- Add Patient ---");
        String id = readNonEmpty("Enter patient ID: ");
        if (service.findPatientById(id) != null) {
            System.out.println("Error: Patient ID already exists.");
            return;
        }
        String name = readNonEmpty("Enter name: ");
        int age = readPositiveInt("Enter age: ");
        if (age <= 0) {
            System.out.println("Error: Age must be positive.");
            return;
        }
        String gender = readLine("Enter gender: ");
        String contact = readLine("Enter contact number: ");

        Patient patient = new Patient(id, name, age, gender, contact);
        if (service.addPatient(patient)) {
            System.out.println("Patient added successfully.");
        } else {
            System.out.println("Error: Could not add patient.");
        }
    }

    private static void viewAllPatients() {
        List<Patient> list = service.getAllPatients();
        System.out.println("");
        System.out.println("--- All Patients ---");
        if (list.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        for (Patient p : list) {
            System.out.println(p);
        }
    }

    private static void searchPatient() {
        String id = readLine("Enter patient ID to search: ");
        Patient p = service.findPatientById(id);
        if (p == null) {
            System.out.println("Patient not found with ID: " + id);
        } else {
            System.out.println("Patient found:");
            System.out.println(p);
        }
    }

    private static void updatePatient() {
        String id = readLine("Enter patient ID to update: ");
        Patient p = service.findPatientById(id);
        if (p == null) {
            System.out.println("Patient not found with ID: " + id);
            return;
        }
        System.out.println("Current record: " + p);
        System.out.println("Leave blank and press Enter to keep the current value.");

        String name = readLine("Enter new name [" + p.getName() + "]: ");
        if (name.isEmpty()) {
            name = p.getName();
        }
        if (name.isEmpty()) {
            System.out.println("Error: Name must not be empty.");
            return;
        }

        String ageStr = readLine("Enter new age [" + p.getAge() + "]: ");
        int age;
        if (ageStr.isEmpty()) {
            age = p.getAge();
        } else {
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                System.out.println("Error: Age must be a number.");
                return;
            }
            if (age <= 0) {
                System.out.println("Error: Age must be positive.");
                return;
            }
        }

        String gender = readLine("Enter new gender [" + p.getGender() + "]: ");
        if (gender.isEmpty()) {
            gender = p.getGender();
        }
        String contact = readLine("Enter new contact [" + p.getContact() + "]: ");
        if (contact.isEmpty()) {
            contact = p.getContact();
        }

        if (service.updatePatient(id, name, age, gender, contact)) {
            System.out.println("Patient updated successfully.");
        } else {
            System.out.println("Error: Could not update patient.");
        }
    }

    private static void deletePatient() {
        String id = readLine("Enter patient ID to delete: ");
        if (service.deletePatient(id)) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found with ID: " + id);
        }
    }

    // ---------------- Doctor menu ----------------

    private static void doctorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("");
            System.out.println("---------- DOCTOR MANAGEMENT ----------");
            System.out.println("");
            System.out.println("1. Add Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctor");
            System.out.println("4. Delete Doctor");
            System.out.println("0. Back to Main Menu");
            System.out.println("");
            System.out.print("Enter your choice: ");

            int choice = readIntChoice();
            switch (choice) {
                case 1:
                    addDoctor();
                    break;
                case 2:
                    viewAllDoctors();
                    break;
                case 3:
                    searchDoctor();
                    break;
                case 4:
                    deleteDoctor();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 0-4.");
                    break;
            }
        }
    }

    private static void addDoctor() {
        System.out.println("");
        System.out.println("--- Add Doctor ---");
        String id = readNonEmpty("Enter doctor ID: ");
        if (service.findDoctorById(id) != null) {
            System.out.println("Error: Doctor ID already exists.");
            return;
        }
        String name = readNonEmpty("Enter name: ");
        String specialization = readLine("Enter specialization: ");
        String contact = readLine("Enter contact number: ");

        Doctor doctor = new Doctor(id, name, specialization, contact);
        if (service.addDoctor(doctor)) {
            System.out.println("Doctor added successfully.");
        } else {
            System.out.println("Error: Could not add doctor.");
        }
    }

    private static void viewAllDoctors() {
        List<Doctor> list = service.getAllDoctors();
        System.out.println("");
        System.out.println("--- All Doctors ---");
        if (list.isEmpty()) {
            System.out.println("No doctors found.");
            return;
        }
        for (Doctor d : list) {
            System.out.println(d);
        }
    }

    private static void searchDoctor() {
        String id = readLine("Enter doctor ID to search: ");
        Doctor d = service.findDoctorById(id);
        if (d == null) {
            System.out.println("Doctor not found with ID: " + id);
        } else {
            System.out.println("Doctor found:");
            System.out.println(d);
        }
    }

    private static void deleteDoctor() {
        String id = readLine("Enter doctor ID to delete: ");
        if (service.deleteDoctor(id)) {
            System.out.println("Doctor deleted successfully.");
        } else {
            System.out.println("Doctor not found with ID: " + id);
        }
    }

    // ---------------- Appointment menu ----------------

    private static void appointmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("");
            System.out.println("------- APPOINTMENT MANAGEMENT -------");
            System.out.println("");
            System.out.println("1. Book Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. Cancel Appointment");
            System.out.println("0. Back to Main Menu");
            System.out.println("");
            System.out.print("Enter your choice: ");

            int choice = readIntChoice();
            switch (choice) {
                case 1:
                    bookAppointment();
                    break;
                case 2:
                    viewAllAppointments();
                    break;
                case 3:
                    cancelAppointment();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 0-3.");
                    break;
            }
        }
    }

    private static void bookAppointment() {
        System.out.println("");
        System.out.println("--- Book Appointment ---");
        String patientId = readLine("Enter patient ID: ");
        if (service.findPatientById(patientId) == null) {
            System.out.println("Error: Patient not found with ID: " + patientId);
            return;
        }
        String doctorId = readLine("Enter doctor ID: ");
        if (service.findDoctorById(doctorId) == null) {
            System.out.println("Error: Doctor not found with ID: " + doctorId);
            return;
        }
        String date = readLine("Enter appointment date (yyyy-MM-dd): ");
        if (!service.isValidDate(date)) {
            System.out.println("Error: Invalid date. Use format yyyy-MM-dd, e.g. 2026-05-20.");
            return;
        }
        Appointment a = service.bookAppointment(patientId, doctorId, date);
        if (a != null) {
            System.out.println("Appointment booked successfully with ID: " + a.getId());
        } else {
            System.out.println("Error: Could not book appointment.");
        }
    }

    private static void viewAllAppointments() {
        List<Appointment> list = service.getAllAppointments();
        System.out.println("");
        System.out.println("--- All Appointments ---");
        if (list.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        for (Appointment a : list) {
            Patient p = service.findPatientById(a.getPatientId());
            Doctor d = service.findDoctorById(a.getDoctorId());
            String patientName = (p != null) ? p.getName() : "Unknown";
            String doctorName = (d != null) ? d.getName() : "Unknown";
            System.out.println(a + " (Patient: " + patientName + ", Doctor: " + doctorName + ")");
        }
    }

    private static void cancelAppointment() {
        String id = readLine("Enter appointment ID to cancel: ");
        if (service.cancelAppointment(id)) {
            System.out.println("Appointment cancelled successfully.");
        } else {
            System.out.println("Appointment not found with ID: " + id);
        }
    }

    // ---------------- Input helpers ----------------

    /** Read a full line after showing a prompt. */
    private static String readLine(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        if (input == null) {
            return "";
        }
        return input.trim();
    }

    /** Keep asking until the user enters a non-empty value. */
    private static String readNonEmpty(String prompt) {
        while (true) {
            String value = readLine(prompt);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Error: This field must not be empty. Please try again.");
        }
    }

    /** Read menu choice as integer. Returns -1 if input is not a number. */
    private static int readIntChoice() {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** Read a positive integer. Returns -1 if invalid. */
    private static int readPositiveInt(String prompt) {
        String value = readLine(prompt);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
