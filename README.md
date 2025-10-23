Doctors-Patients App



A Java Swing application for managing doctors and patients.

The app allows users to:

\- Add, update, and delete doctors

\- Add, update, and delete patients

\- Assign patients to doctors

\- Remove patients from doctors

\- View the patients assigned to each doctor



------------------------------------------------------------



Table of Contents



1\. Project Overview

2\. Requirements

3\. Database Setup (Forward Engineering)

4\. Project Structure

5\. How to Run

6\. Usage

7\. License



------------------------------------------------------------



Project Overview



This project uses a monolithic approach:

\- All GUI frames, models, and logic are in the same project.

\- Database access is centralized via the DBUtil class, using Apache DBCP2 connection pooling.

\- There is no separate backend; the Swing GUI directly interacts with the database.



------------------------------------------------------------



Requirements



\- Java JDK 17+ (or compatible)

\- MySQL 8+ (or any JDBC-supported database)

\- IDE: IntelliJ, Eclipse, or VS Code with Java support

\- Apache Commons DBCP2 library

\- MySQL Connector/J library

\- Maven/Gradle (optional)



------------------------------------------------------------



Database Setup (Forward Engineering)



1\. Open MySQL Workbench or another database design tool.

2\. Create a new \*\*ER diagram\*\* for the application.

3\. Create the following tables:



Table: doctors

\- id INT AUTO\_INCREMENT PRIMARY KEY

\- firstname VARCHAR(45)

\- lastname VARCHAR(45)



Table: patients

\- id INT AUTO\_INCREMENT PRIMARY KEY

\- firstname VARCHAR(45)

\- lastname VARCHAR(45)



Table: doctorspatients

\- d\_id INT

\- p\_id INT

\- PRIMARY KEY (d\_id, p\_id)

\- FOREIGN KEY (d\_id) REFERENCES doctors(id)

\- FOREIGN KEY (p\_id) REFERENCES patients(id)



4\. Use the \*\*Forward Engineer\*\* feature to generate the SQL script and apply it to create the database `dpdb`.

5\. Set your database username and password for the user that will connect.

6\. Ensure your environment variable `PASS\_DP` is set with your database password, as used in DBUtil.java.



DBUtil Example:

\- URL: jdbc:mysql://localhost:3306/dpdb?serverTimeZone=UTC

\- Username: dpuser

\- Password: from environment variable PASS\_DP



------------------------------------------------------------



Project Structure



DoctorsPatientsApp/

|

├─ src/

│  └─ gr/aueb/cf/dpapp/

│     ├─ Main.java                     - Main launcher

│     ├─ MainMenuFrame.java            - Main menu GUI

│     ├─ DoctorsMenuFrame.java         - Doctors menu GUI

│     ├─ DoctorsInsertFrame.java       - Form to add doctors

│     ├─ DoctorsUpdateDeleteFrame.java - Form to update/delete doctors

│     ├─ PatientsMenuFrame.java        - Patients menu GUI

│     ├─ PatientsInsertFrame.java      - Form to add patients

│     ├─ PatientsUpdateDeleteFrame.java - Form to update/delete patients

│     ├─ DoctorsPatientsFrame.java     - Assign/remove patients to doctors

│     └─ util/DBUtil.java              - Centralized database connection utility

|

└─ resources/

&nbsp;  └─ medical.png                       - Application icon



Notes:

\- All database operations go through DBUtil with connection pooling.

\- The application is a single monolithic Swing project.

\- Make sure MySQL Connector/J is added to the project libraries.



------------------------------------------------------------



How to Run



1\. Open the project in your IDE.

2\. Make sure your database `dpdb` exists and DBUtil.java is configured correctly.

3\. Run Main.java to start the application.

4\. Navigate through the Main Menu to access doctors, patients, or doctor-patient assignment.



------------------------------------------------------------



Usage



Doctors:

\- Insert: Add a new doctor (firstname + lastname)

\- Update/Delete: Select a doctor from the table and modify or remove



Patients:

\- Insert: Add a new patient

\- Update/Delete: Select a patient from the table and modify or remove



Assigning Patients to Doctors:

\- Select a doctor from the dropdown

\- Select a patient from the available patients dropdown

\- Click Assign to link the patient

\- Use Remove to delete the link

\- Table updates automatically



------------------------------------------------------------



License



Open-source, free to use.



