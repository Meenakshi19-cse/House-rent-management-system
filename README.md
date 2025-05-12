# House-rent-management-system
A web-based House Rent Management System built with Spring Boot, 
featuring owner, user, and house integration for 
seamless rental operations and connected with MySQL database

**Features**

-> Owner registration and property listing
-> User registration and rental booking
->Admin panel to manage users, owners, and houses
-> Search and edit houses by house id.

Integrated with MySQL database for testing

🔧 Tech Stack
Java + Spring Boot

Spring MVC, Spring Data JPA

H2 Database

HTML/CSS/JS

 **How to Run
Clone the repository:**

bash
Copy
Edit
git clone https://github.com/yourusername/your-repo-name.git
Open the project in your IDE (e.g., IntelliJ or Eclipse).

**Run the application using:**

bash
Copy
Edit
mvn spring-boot:run
Visit http://localhost:8080 in your browser.

**📂 Folder Structure**

.
├── main
│   └── java
│       └── com.example.demo
│           ├── controller
│           │   ├── ClientController.java
│           │   ├── HomeController.java
│           │   ├── HouseController.java
│           │   ├── ImageProxyController.java
│           │   └── OwnerController.java
│           ├── exception
│           │   └── GlobalExceptionHandler.java
│           ├── model
│           │   ├── Client.java
│           │   ├── House.java
│           │   └── Owner.java
│           ├── repository
│           │   ├── ClientRepository.java
│           │   ├── HouseRepository.java
│           │   └── OwnerRepository.java
│           └── DemoApplication.java
├── resources
│   ├── static
│   │   ├── new.html
│   │   └── welcome.html
│   ├── templates
│   └── application.properties
├── test
│   └── java
│       └── com.example.demo
│           └── DemoApplicationTests.java
├── target
├── .gitignore
├── .gitattributes
├── HELP.md
├── mvnw
└── mvnw.cmd
|__ pom.xml

**📌 Todo / Improvements**
->Add email notification for booking
-> Add user authentication with JWT
->Integrate payment gateway
