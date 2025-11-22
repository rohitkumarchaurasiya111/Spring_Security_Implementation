# Spring Security Implementation (JWT Authentication)

This repository contains a Spring Boot backend project with **Spring Security implemented using JWT (JSON Web Token) based authentication**.  
The project is created for **reference and learning purposes**, helping you understand each part of Spring Security with clear explanations and comments in the code.

---

## 📌 Purpose of This Repository

- To serve as a **personal reference** while implementing Spring Security.
- To provide **well‑documented and commented code** for easy understanding.
- To demonstrate **JWT-based authentication and authorization** in Spring Boot.
- To act as a base template for securing future Spring Boot applications.

---

## 🔐 Features Implemented

### ✅ JWT Authentication  
- Login API generates a JWT token  
- Token contains user identity and roles  
- Token expiry & validation handled via filters  

### ✅ Spring Security Setup  
- Custom `UserDetailsService`  
- Password encryption using **BCrypt**  
- Authentication Manager & Security Configuration  
- Role-based authorization  

### ✅ Filters & Request Pipeline  
- JWT Authentication Filter  
- JWT Authorization Filter  
- Security filter chain customization  

### ✅ User Management  
- In-memory or database-based user details (depending on your setup)  
- Username + Password authentication  
- Secure password hashing  

### ✅ Well‑explained Code  
Every file contains **clear comments** explaining:
- What the class does  
- How Spring Security processes requests  
- How JWT is validated  
- Why certain configurations are required  

This makes it extremely easy to **revise or reuse the implementation** in other projects.

---

## 🏗️ Project Structure

```text
Spring_Security_Implementation/
├── config/            # Security configuration + JWT utilities
├── controllers/       # Test + authentication controllers
├── filters/           # JWT filters for authentication & authorization
├── services/          # Custom UserDetailsService implementation
├── models/            # Entities (User, Roles etc.)
└── utils/             # Token helper classes
```

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **JWT (jjwt / java-jwt libraries)**
- **BCrypt Password Encoder**

---

## 🚀 How to Run

### 1️⃣ Clone the repository

```bash
git clone https://github.com/rohitkumarchaurasiya111/Spring_Security_Implementation
```

### 2️⃣ Open in your IDE  
Use IntelliJ, Eclipse, VS Code with Spring Boot extension, etc.

### 3️⃣ Configure application properties  
Add database credentials if needed (for in-memory users, no DB is required).

### 4️⃣ Run the Application  
Start the Spring Boot project from IDE or use:

```bash
mvn spring-boot:run
```

---

## 🔗 API Endpoints (Sample)

### **Authentication**
```
POST /user/login
```
Returns a JWT token on successful login.

### **Protected Endpoint**
```
GET /api/products
```
Requires a valid JWT token in the header:
```
Authorization: Bearer <token>
```

---

## 📘 How JWT Flow Works (Quick Summary)

1. **User logs in** → Sends username + password  
2. **Server validates credentials** → Generates JWT token  
3. Token is returned to client  
4. Client sends token inside `Authorization` header for all protected routes  
5. Filters validate the token  
6. If valid → Request is allowed  
7. If invalid/expired → Access is denied  

---

## 💡 Why This Repository Is Useful

- Acts as a **ready-made template** for any Spring Security + JWT project  
- Every file includes **descriptive comments** for better understanding  
- Great for **revision** during internships, academic work, or job preparation  
- Helps avoid confusion during real project implementations  

---

## 📄 License

This project is for **learning and reference purposes only**.  

---

## 👨‍💻 Author

**Rohit Kumar Chaurasia**  
GitHub: [@rohitkumarchaurasiya111](https://github.com/rohitkumarchaurasiya111)

