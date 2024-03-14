
# Running Project Hosted on GitHub in IntelliJ

## Prerequisites
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) installed on your machine.

## Steps

### 1. Clone the Repository
- Open IntelliJ IDEA.
- Click on `Get from VCS` or `Checkout from Version Control` and choose `Git`.
- Enter the URL of the GitHub repository.
- Choose the directory where you want to clone the project.
- Click `Clone` to clone the repository.

### 2. Import the Project
- After cloning, IntelliJ IDEA will ask if you want to open the project. Click on `Yes` to open it.

### 3. Resolve Dependencies
- Open the `pom.xml` file.
- IntelliJ IDEA should automatically detect that it is a Maven project and prompt you to import dependencies. If not, you can manually trigger this process by right-clicking on the `pom.xml` file and selecting `Maven` -> `Reimport`.

### 4. Run the Application
- Locate the main class of your Spring Boot application (usually annotated with `@SpringBootApplication`).
- Right-click on the main class file and select `Run [ClassName]`.

### 5. Access the Application
- Once the application is running, open a web browser.
- Enter the URL `localhost:9090` in the address bar.

### Development Login Information
- **User 1:**
    - Username: user1
    - Password: password
    - Papers: 0
    - Reviews: 0
    - Assigned Reviews: 2

- **User 2:**
    - Username: user2
    - Password: password
    - Papers: 2
    - Reviews: 0
    - Assigned Reviews: 0

### Data Storage
- There is no database configured for this application.
- All information is stored in memory using repository classes.

## Additional Information
- Make sure you have a compatible JDK configured in IntelliJ IDEA.
- Check the project's `README.md` file on GitHub for any specific instructions or configurations required for running the project.

