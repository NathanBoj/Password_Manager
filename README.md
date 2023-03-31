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

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Users who have successfully registered and verified their email address can now sign into the application by providing their credentials. A user will be denied entry if a password and email combination is not found on the database, as well as an unverified email address. A user who may have forgotten their password may reset their password from this page by clicking on the forgot password link. Upon providing the email address, a password reset email will be sent to the user's email address, where the user may change their password. 

### Register Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/registration.jpg" width=20% height=20%>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;New users to the application will experience the register page first. This allows users to securely register for the application and log into their profile so they may use the applications functionalities. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;A valid email, name, and password are required for a successful register. An email address is valid if it contains an appropriate handle, such as including a username, an ‘@’ sign, and a domain name. Also, an email cannot be registered to a new user if that email is already registered under a different user. A valid password consists of at least 10 characters, an uppercase letter, and a special character. The user is prompted to re-input the password in case they may have mistakenly inputted a wrong character. The register page will not submit a new user to the database if one of these requirements are not met. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Once the input values are valid and it is time to complete the register process, an email verification link is sent to the user’s provided email address, in which they must open and verify. 

### Reset Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/reset.jpg" width=20% height=20%>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The password reset functionality was developed for the users ease of use, however this feature may raise some security concerns when dealing with hackers. Hackers can gain access to users accounts by exploiting this feature if the hacker has access to their main email account. To combat this, we decided to include SMS multi-factor authentication, which significantly adds a layer of security to users accounts.

### SMS Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/sms.jpg" width=20% height=20%>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The way that the SMS MFA will work is upon successful email and password login, the user will receive a text message that contains a verification code in which they will need to enter into the MFA field. The user has 60 seconds to enter the code, and has an option to generate a new code. If all of these verification steps are successful, the user will gain access to their homepage that contains all their stored passwords.

### Home Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/home.jpg" width=20% height=20%>

### Decrypt Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/decrypt.jpg" width=20% height=20%>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The Home Page is the base of the application where users can view previously stored passwords or create new passwords. The home page makes a request to the database to retrieve all currently stored password information, and will display the password title and ciphertext for all stored passwords. This activity provides the decryption method so when users select the “unlock” icon, they will be able to see their decrypted password.  

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To decrypt a password, the decrypt method will pass through the encrypted password, its key, and initialization vector. The decryption method uses the same key and iv which were used for encryption and it calls the decode method on the key, iv, and encrypted password since these values were originally encoded when encrypted. The decode method decodes a base 64 encoded string back into a byte array. 

### Create Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/create.jpg" width=20% height=20%>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This activity gives the user the ability to create and store new passwords. New passwords have the same input validation that is used when registering for the app. The page features the encryption method so passwords can be safely uploaded to the database. Passwords have attributes such as titles to help differentiate the many passwords associated with the user, ciphertext, key, and initialization vector. 

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To encrypt the provided passwords, we will use the cipher class in Android Studio. The cipher class provides many different encryption methods to use in your code such as AES and DES. For our Project, we will be encrypting our passwords using AES encryption with a randomly generated 16 bytes key and 12 bytes initialization vector.

### Updated Home Page
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/create2.jpg" width=50% height=50%>

### Register Process Sequence Diagram
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/register_seq.png" width=50% height=50%>

### Login Process Sequence Diagram
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/login_seq.png" width=50% height=50%>

### Decryption Process Sequence Diagram
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/decrypt_seq.png" width=50% height=50%>

### Encryption Process Sequence Diagram
<img src= "https://github.com/NathanBoj/Password_Manager/blob/main/screenshots/encrypt_seq.png" width=50% height=50%>
