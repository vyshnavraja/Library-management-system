# Digital Library

This project contains a digital library system implemented in Java. It provides classes and functionalities for managing a digital library, including user authentication, librarian management, and user interface components.

## Package Hierarchy

- `digital`
  - `HomePage`: Represents the home page of the digital library system.
  - `LibrarianPage`: Represents the page for librarian management and operations.
  - `LoginPage`: Provides functionality for user authentication and login.
  - `SignUp`: Allows users to sign up and create new accounts.
  - `User`: Represents a user in the digital library system.

## Class Hierarchy

- `java.lang.Object`
- `java.awt.Component` (implements `java.awt.image.ImageObserver`, `java.awt.MenuContainer`, `java.io.Serializable`)
- `java.awt.Container`
- `java.awt.Window` (implements `javax.accessibility.Accessible`)
- `java.awt.Frame` (implements `java.awt.MenuContainer`)
- `javax.swing.JFrame` (implements `javax.accessibility.Accessible`, `javax.swing.RootPaneContainer`, `javax.swing.WindowConstants`)
- `digital.HomePage`
- `digital.LibrarianPage`
- `digital.LoginPage`
- `digital.SignUp`
- `digital.User`

## Usage

To set up and run the digital library system, please follow the instructions below:

### 1. Set up XAMPP

- Download and install XAMPP from the official website (https://www.apachefriends.org/index.html).
- Start the XAMPP Control Panel and ensure that the Apache and MySQL services are running.

### 2. Import the Roy Database

- Locate the `roy.sql` file in the project directory.
- Open phpMyAdmin by clicking on the "Admin" button next to the MySQL service in the XAMPP Control Panel.
- Create a new database called "roy" in phpMyAdmin.
- Select the "roy" database from the sidebar and click on the "Import" tab.
- Choose the `roy.sql` file and click on the "Go" button to import the database structure and data.

### 3. Set up NetBeans

- Download and install NetBeans IDE from the official website (https://netbeans.apache.org/download/index.html).
- Launch NetBeans and create a new Java project.
- Copy the contents of the `lib.rar` file into the project's `lib` directory.
- Right-click on the project in NetBeans, select "Properties," and navigate to the "Libraries" tab.
- Click on the "Add JAR/Folder" button and select all the JAR files from the `lib` directory.
- Click "OK" to add the JAR files to the project's libraries.

### 4. Configure the Project

- Extract the project files to a local directory.
- Open the project in NetBeans.
- In the project files, locate the `Database.java` file in the `digital` package.
- Update the database connection details in the `Database.java` file to match your XAMPP configuration (e.g., database URL, username, and password).

### 5. Run the Application

- Right-click on the project in NetBeans and select "Run" to launch the digital library system.
- The application should start, and you can interact with the different pages and functionalities.

## Contributing

Contributions to the digital library project are welcome. If you find any issues or have suggestions for improvements, please submit a pull request or open an issue in the project repository.

## License

The digital library project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code as per the terms of the license.

## Contact

If you have any questions or need further assistance, you can reach out to the project maintainer at vyshnav7675@gmail.com
