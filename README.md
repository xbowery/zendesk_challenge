# Zendesk Intern Coding Challenge

This repository serves as my submission for the 2022 Zendesk Intern Coding Challenge (Singapore).

## About

The requirement of this challenge was to build a Ticket Viewer that will:

- Connect to the Zendesk API
- Request all the tickets for your account
- Display them in a list
- Display individual ticket details
- Page through tickets when more than 25 are returned

# Pre-requisites for installation

Please ensure that you have `JDK 11+` as well as `Maven 3.3` installed in your machine before running the application locally.


## To-dos before running the application

Before you run the application, please ensure that you have changed the following variables:

- In **Line 16 of `ZendeskApplication.java`**, please ensure you have changed **`{subdomain}`** to your **Zendesk subdomain name**.
- If you are planning to use the **Password access authentication method** to access the Zendesk API, please ensure you have done the following:

   1. In **Line 23 of `TicketService.java`**, change the variables **`{email_address}`** and **`{password}`** to your **Zendesk account's email address and password** respectively.

- If you are planning to use the **Token access authentication method** to access the Zendesk API, please ensure you have done the following:

   1. Comment out **lines 23 & 27** and uncomment **lines 25 and 29-30** of **`TicketService.java`**. 
   2. In **Line 25 of `TicketService.java`**, change the variables **`{email_address}`** and **`{api_token}`** to your **Zendesk account's email address and token** respectively.
   3. In **Line 55 of `TicketService.java`**, change the **`encodedAuthPassword`** variable to **`encodedAuthToken`**.

## Steps to run the application

To run the application locally, please do the following steps:

```bash
# Open the folder in your favourite IDE. If you are using VSCode:
code .

# For the CLI application
cd zendesk/

# For MacOS and Linux
./mvnw spring-boot:run 

# For windows OS
mvn spring-boot:run
```

The application will be running on port 8080 and can be interacted with in your command line terminal.

**Alternative method**

```bash
# Open the folder in your favourite IDE. If you are using VSCode:
code .

# For the CLI application
cd zendesk/

# For MacOS and Linux
./mvnw package 

# For windows OS
mvn package

# Running the application
java -jar  target/zendesk-0.0.1-SNAPSHOT.jar
```

The application will be running on your machine and you can interact it with your command line terminal.

## Interacting with the application

Upon a successful launch of the application, you will be prompted to enter the number corresponding to the following 3 options:

1. View all tickets
2. View individual ticket
3. Exit

### Option 1 (View all tickets)

The application will display the first page containing the first 25 tickets in the entire list of tickets. You will then be prompted whether you want to view other pages (if there is more than 1 page).

If there are no tickets, the application will notify you that there are no tickets for you to view.

### Option 2 (View individual ticket)

The application will prompt you to enter the Ticket ID, before displaying the ticket to you.

If there is no ticket found with the associated Ticket ID, the application will notify you that no such ticket is found.

### Handling exit of application
If you select option 3, the application will exit.

After the selection of option 1 or 2, there will be a prompt to ask whether you will want to continue using the application. If you select no, the application will exit.

