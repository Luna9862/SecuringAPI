To test the Spring Boot application's security using Postman, first install Postman and enter the base URL of your API (e.g., http://localhost:8080). 
Start by testing user registration through a POST request to the /api/register endpoint, providing user details like username and password in the request body in JSON format. 
After registering a user, test login by sending another POST request to /api/login with the same credentials, which will return a JWT token upon success. 
To access protected endpoints, use this token by selecting "Bearer Token" in the "Authorization" tab of Postman and pasting the token in the field. 
Finally, send requests to protected endpoints (e.g., /api/protected), and based on the user’s role, Postman will either grant access or return an error (like "403 Forbidden").
Repeat this process for different roles (e.g., ADMIN or USER) to ensure the role-based access control is functioning as expected. 
For example: 
I entered the following in the POST:
http://localhost:8080/api/register
Then: Body, JSON, raw,
{
"username": "john",
"password": "password123"
}
Then it will say user successfully, it will say user successfully made or Username is already taken. 