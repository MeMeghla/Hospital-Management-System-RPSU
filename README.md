# Hospital Management System

A simple beginner-level console application in Java for practicing
Object-Oriented Programming (OOP) and file handling.

Manages patient records, doctor information, and appointments.
All data is stored permanently in text files inside the `data` folder.

## Project Structure

```text
HospitalManagementSystem/
├── README.md
├── data/
│   ├── patients.txt
│   ├── doctors.txt
│   └── appointments.txt
└── src/
    ├── Main.java
    ├── Patient.java
    ├── Doctor.java
    ├── Appointment.java
    ├── HospitalService.java
    └── FileManager.java
```

- `Main.java` — starts the program and displays menus.
- `Patient.java` — represents a patient.
- `Doctor.java` — represents a doctor.
- `Appointment.java` — represents an appointment.
- `HospitalService.java` — handles patient, doctor, and appointment operations.
- `FileManager.java` — reads and writes text files.

## Requirements

- Java 17 or compatible (also works on Java 8+)
- Terminal / console only, no database, no GUI

## How to Compile and Run

Open a terminal in the `HospitalManagementSystem` folder.

### 1. Compile

```bash
javac -d out src/*.java
```

This creates compiled `.class` files in the `out` folder.

### 2. Run

```bash
java -cp out Main
```

On Windows (PowerShell / CMD), use:

```powershell
javac -d out src/*.java
java -cp out Main
```

> Important: Run the commands from the `HospitalManagementSystem` folder
> so the program can find/create the `data` folder automatically.

### Alternative (without `out` folder)

```bash
cd src
javac *.java
java Main
cd ..
```

Note: if you run from inside `src`, the `data` folder will be created
inside `src`. The recommended way is the `out` method above.

## File Formats

Files use `|` as separator. One record per line.

`patients.txt`:
```text
id|name|age|gender|contact
P001|Ali Ahmed|25|Male|03001234567
```

`doctors.txt`:
```text
id|name|specialization|contact
D001|Dr. Sara|Cardiology|03009876543
```

`appointments.txt`:
```text
id|patientId|doctorId|date
A001|P001|D001|2026-05-20
```

- The `data` folder and files are created automatically if missing.
- Invalid lines are skipped safely when loading.
- Appointment IDs (A001, A002, ...) are generated automatically.
- Appointment date format: `yyyy-MM-dd` (example: `2026-05-20`).

## Menus

Main menu:

```text
=====================================
       HOSPITAL MANAGEMENT SYSTEM
=====================================

1. Patient Management
2. Doctor Management
3. Appointment Management
0. Exit

Enter your choice:
```

## Features

- Patient: add, view all, search by ID, update, delete
- Doctor: add, view all, search by ID, delete
- Appointment: book (patient + doctor must exist), view all, cancel
- Basic validation: non-empty names, positive age, unique IDs,
  valid dates, safe handling of invalid menu choices.
