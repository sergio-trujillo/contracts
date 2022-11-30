# Contract Management Coding Challenge

## Background

For this coding challenge you will help to implement a Contract Management application that a General
Contractor can use to track contract progress across various Subcontractors and Vendors. The application will be used to
track the following data points:

- Contract Code - A unique, user-defined identifier per contract
- Description - A free-text description of the goods/services being provided
- Vendor - The name of the Vendor/Subcontractor who is providing the goods/services
- Contract Amount - The total dollar amount of the contract to be paid to the Vendor/Subcontractor
- Invoiced Amount - The total amount that has been invoiced by the Vendor/Subcontractor to date. (Typically contracts on
  a large construction project are invoiced in installments as the work is performed)

This project is implemented using a framework called [Dropwizard](http://www.dropwizard.io/0.9.2/docs/) which bundles
together the following tools:

- Jetty for an HTTP Server
- Jackson for serialization
- Jersey for a REST framework
- Hibernate for ORM
- Liquibase for database migrations
- Mustache for view templating

The user interface is comprised of:

- AngularJS
- Bootstrap

The database is H2.

## Instructions

### Prerequisites

Please ensure you have the following installed:

- Java 11
- Maven 3
- Git
- An IDE (IntelliJ IDEA preferred - Community Edition is fine)

### Running

Unzip the attached contract-management.zip. Run the application as follows from your terminal:

```
cd /path/to/contract-management

# First build the application
mvn clean install -DskipTests

# Migrate the database.
java -jar target/contract-1.0.0-SNAPSHOT.jar db migrate src/main/resources/app.yml

# Run the application from the command line
java -jar target/contract-1.0.0-SNAPSHOT.jar server src/main/resources/app.yml
```

You should now be able to view the application by visiting [http://localhost:9090/](http://localhost:9090/)

If everything is working properly, you should see something like this:
![Contracts Grid](screenshots/getting-started.png?raw=true "Contracts grid")

### Coding Tasks

1. Apply formatting to Contract Amount and Invoiced Amount values
    - Should be formatted as dollar values with 2 decimals of precision, e.g. `$10,000.00`
2. Add a "Percent Invoiced" column
    - Percent Invoiced = (Invoiced Amount / Contract Amount) * 100
    - Should be formatted as a percentage with one decimal place of precision, i.e. `50.0%`
3. Add a "Delete" button for each Contract in the grid
    - When the Delete button is clicked, the Contract should be removed from the database and grid
4. Add an "Add Contract" button and modal to create a new Contract
    - When the button is clicked, a modal should pop up allowing the user to enter details for a new contract
    - When the modal is submitted, the new contract should be persisted in the database and displayed in the grid
5. Add validation to prevent duplicate Contract Codes
    - If the user tries to submit a contract with a duplicate Contract Code, display the following error message in the
     modal:
      - "A contract with this code already exists."
6. Add a summary row below the Contracts grid to display totals across all contracts
     - Contract Amount and Invoiced rows should be summed in the summary row
     - Percent Invoiced should display the total Invoiced Amount as a percentage of the total Contract Amount
    
### Reference screen shots
See the following screenshots for an example of how the application might look after implementing the above coding tasks. Note that this example is only presented for the sake of clarity, and it's fine if your results look different. 

Contracts Grid:
![Completed Contracts Grid](screenshots/contracts-grid-final.png?raw=true "Completed Contracts Grid")

Add Contract Modal:
![Completed Add Contract Modal](screenshots/modal-final.png?raw=true "Contracts grid")

