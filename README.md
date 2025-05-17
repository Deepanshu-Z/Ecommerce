# 🛒 E-Commerce Web Application

## 📌 Description
This is a full-stack **E-Commerce Web Application** built using **Spring Boot**
for the backend and **React** for the frontend. It offers complete e-commerce 
functionality including product listing, user registration, authentication with JWT and cart management

------------------------------------------------------------

## ✨ Features

- 🔐 User Authentication – JWT + BCrypt secure login/register
- 🛍️ Product Catalog – View product list and details
- 🛒 Shopping Cart – Add, remove, and update cart items
- 🌐 REST APIs – Spring Boot powered endpoints
- ⚛️ Modern UI – Built with React and Axios

------------------------------------------------------------

## 🛠️ Tech Stack

| Layer        | Technology                                      |
|--------------|-------------------------------------------------|
| Backend      | Spring Boot, Spring Security, Spring Data JPA   |
|              | JWT, Maven                                      |
| Frontend     | React.js, React Router, Axios, Bootstrap        |
| Database     | MySQL                                           |
| Build Tools  | Maven (Backend), npm (Frontend)                 |


------------------------------------------------------------

## ✅ Prerequisites

Make sure the following are installed:

- Java 17+
- Maven
- Node.js + npm
- MySQL
- Git
- IntelliJ (for backend), VS Code (for frontend)

------------------------------------------------------------

## 🔐 Environment Variables

In `backend/src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:mysql://localhost:3306/Ecomdb  
spring.datasource.username=YOUR_DB_USERNAME  
spring.datasource.password=YOUR_DB_PASSWORD  
jwt.secret=YOUR_SECRET_KEY  
server.port=8080  
```
In `frontend/.env` (if needed):
```
REACT_APP_API_URL=http://localhost:8080
```
------------------------------------------------------------

## 🚀 How to Run the Project Locally

🔧 Step 1: Clone the repository
```
$ git clone https://github.com/Deepanshu-Z/Ecommerce.git  
$ cd Ecommerce
```

⚙️ Step 2: Run the Spring Boot backend
```
$ cd backend  
$ mvn clean install  
$ mvn spring-boot:run

🟢 Backend runs at: http://localhost:8080
```
🌐 Step 3: Run the React frontend

# Open new terminal
```
$ cd frontend  
$ npm install  
$ npm start

🟢 Frontend runs at: http://localhost:3000
```
------------------------------------------------------------

## 🙋‍♂️ Author

**Deepanshu Pokhriyal**  
📧 deepanshupokhriyal07@gmail.com  
🔗 GitHub: https://github.com/Deepanshu-Z

------------------------------------------------------------

## 🌟 Support

If you find this project useful, consider starring ⭐ the repository!

