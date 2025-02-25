# **Food Ordering System Backend (Refactored Version)**

## **📌 Project Overview**
This is the **refactored backend** of a food ordering system, originally built with **MyBatis Plus**, now migrated to **Hibernate + Spring Data JPA** for better ORM support and maintainability. Security has been enhanced with **Spring Security + JWT authentication**, and **Redis caching** has been integrated for session management.

## **🔹 Key Features & Enhancements**
### **✅ Refactored from MyBatis Plus to Hibernate**
- Improved maintainability and reduced redundant SQL queries.
- Leveraged Hibernate's built-in ORM capabilities for efficient database interactions.

### **✅ Enhanced Authentication & Security**
- Implemented **Spring Security with JWT** for secure user authentication.
- Used **RBAC (Role-Based Access Control)** to manage user permissions (Upcoming feature).

### **✅ Redis Integration for Session Management**
- Stored authentication sessions in Redis to reduce database queries for user validation.
- Improved API response time by handling session verification efficiently.

### **✅ Optimized Query Performance**
- Implemented **Hibernate-based pagination** for handling large dataset queries efficiently.
- Tuned SQL queries and indexes for better API responsiveness.

### **✅ RESTful API Development**
- Designed and implemented a structured **RESTful API**.
- API testing and debugging done using **Postman**.

## **📦 Tech Stack**
- **Backend:** Java 11, Spring Boot, Spring Security, Spring Data JPA (Hibernate)
- **Database:** MySQL
- **Caching:** Redis
- **Dev Tools:** IntelliJ IDEA, Maven, Postman
- **Deployment:** Linux, Docker

## **🚀 Setup & Installation**
### **🔹 Prerequisites**
Make sure you have the following installed:
- Java 11+
- MySQL
- Redis
- Maven

### **🔹 Clone the Repository**
```sh
 git clone https://github.com/your-username/food-ordering-system-backend.git
 cd food-ordering-system-backend
```

### **🔹 Configure the Database**
Update `application.properties` with your MySQL and Redis configurations:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/food_order_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.redis.host=localhost
spring.redis.port=6379
```

### **🔹 Run the Application**
```sh
mvn spring-boot:run
```

### **🔹 API Testing**
Use Postman to test endpoints. Example:
```http
POST /api/auth/login  -> User Login
GET /api/orders      -> Get Orders
POST /api/orders     -> Place Order
```

## **🔍 Future Improvements**
- Implement OAuth2 authentication for third-party login (Google, Facebook).
- Expand RBAC for granular access control.
- Introduce microservices architecture for better scalability.

## **🤝 Contributing**
Feel free to fork this repository and submit pull requests for enhancements!

---

📌 **GitHub Repository:** [Your GitHub Link]  
📧 **Contact:** your.email@example.com

