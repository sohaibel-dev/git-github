# git-github

This is a short description of how the project in this Repository works and how to get it up and running, at the request of Atos for technical testing.

# Functioning: 

Java 1.8 

The application is a Spring boot API for user registration and login, using Spring Security. The application also allows the display of all users in the database or only users by sorting username (mail) or date of birth or country of residence.

In addition, while there are mandatory fields (email, password in 6 and 10 characters, firstname, lastname, birthdate (age > 18 years) and country (only France Allowed), other fields are optional (gender and phone number).

A validation of the inputs allows the erroneous ones to be reported thanks to messages at the FrontEnd level (use of thymeleaf). 
If the form is validated, the user is registered and redirected to the login page.
If the form contains errors, the user will see messages describing the type(s) of error(s) made while remaining on the same registration page.
After the login is done, the user could display all the users present in the database.

# Starting up the application:

The application: it could be launched in an IDE like eclipse or via its Jar (command : mvn clean package).
Database: Option 1: on server, MySQL, Option 2: embedded H2 --> configuration to be changed on "application.properties" depending on choice.
The FrontEnd: http://localhost:8080/

path for registration: http://localhost:8080/register
path for login: http://localhost:8080/login
path to display all users: /users
path to search by email: users/email/{email} 
path to search by id: users/id/{id}
path to search by date of birth: users/birthdate/{birthdate}

# FrontEnd Templates :

index.html --> login
signup_form.htmll --> register
register_succes --> redirect to login
register_fail --> redirect to failed register (not used)
users --> display all users after login


