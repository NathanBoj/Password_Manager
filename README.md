# Password_Manager
## Software & Computer Security Project

## How to install:
Using your favourite Android device, install the APK [here.](https://github.com/NathanBoj/Password_Manager/tree/main/app/release)
(must allow installations from uknown sources)

## Functionality
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;With the growing number of online sites and services which all require you to have complex passwords, it can be tough to remember and keep track of so many different combinations. This project is aimed at creating a secure and user-friendly mobile application that will allow users to store their passwords and sensitive information in an encrypted format. The app will utilize strong encryption algorithms to ensure confidentiality, integrity, and authorization of user passwords, alongside removing the stress that comes with entering passwords.  

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The defined project requires our team to create an Android Application that features a login and register system, with a functional user database to ensure the applications integrity and validity. When the user successfully authenticates, they are able to store their valuable passwords, where the application will automatically encrypt and decrypt upon retrieval. The project will also utilize Firebase, a set of backend cloud computing services and application development platforms provided by Google, that will act as a host for our authentication and database services. The user interface will be designed to be intuitive and easy to use, with a minimalistic and modern design.The development team will prioritize security and testing throughout the development process to ensure the app is secure and free from vulnerabilities. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The resulting product is a fully functional and secure Password Manager Application that stores and retrieves user sensitive passwords. The application successfully registers a user, retrieves the data from an existing user, and correctly decrypts and encrypts a user's password. Overall, the Password Encryption Mobile Application project aims to provide users with a secure and convenient way to manage their passwords and sensitive information on-the-go

## Project Methodology Overview
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The Password Manager Application requires many pages to showcase its true functionality. The application first starts at the Login Page, and follows a secure process of activities to guarantee the confidentiality, integrity, and authorization of user passwords.

<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/system.png" width=70% height=70%>

We can list all of the system components and provide a brief overview of its functionality:
- <ins>Firebase Realtime Database:</ins> Helps us store and retrieve user information such as name, email, phone, and more delicate data such as stored passwords. Passwords that are stored in the realtime database are encrypted.
- <ins>Firebase Authentication:</ins> Takes care of sending authentication emails and SMS verification texts to users.
- <ins>Register Page:</ins> Registers a new user to our database. Email verification is initially sent and needs to be verified before logging in.  
- <ins>Forgot Password Page:</ins> Users have the option to reset their password via email.
- <ins>Login Page:</ins> Signs in an already registered and authenticated user. If provided credentials are valid they are prompted with the SMS verification page.
- <ins>SMS Verification Page:</ins> Signed in users are required to provide a new SMS code every time they log in. This is to ensure another level of security and authentication while using the app. Firebase Authentication handles the generation and sending of SMS codes.
- <ins>Home Page:</ins> Users can view previously stored passwords and create new passwords. This activity provides the decryption method so users can select which password they want to use. 
- <ins>Create Password Page:</ins> This activity gives the user the ability to create and store new passwords. It features the encryption method so passwords can be safely uploaded to the database. 

### Login Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/login.jpg" width=20% height=20%>

### Register Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/registration.jpg" width=20% height=20%>

### Reset Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/reset.jpg" width=20% height=20%>

### SMS Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/sms.jpg" width=20% height=20%>

### Home Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/home.jpg" width=20% height=20%>

### Decrypt Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/decrypt.jpg" width=20% height=20%>

### Create Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/create.jpg" width=20% height=20%>

### Updated Home Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/create2.jpg" width=20% height=20%>
