**Java EE Coding Test Answer**
--
The code in this repository was created as my response/answer to an interview coding test question (the interview was for a Java Developer position).  This was a **single sit-down time limited test**  (about 3 hrs).  The time limit, available resources and test nature of the code influenced design decisions and patterns heavily  - please read the **\"Because It’s a Test\"** paragraph below.  Other than some refactoring (mostly to remove the company name) and adding more explanatory comments in the code, this is as as was completed during the interview.

**The Test Question/Task:**
--
_Using the _**_Java_**_ framework of your choice, please create a single page web application that allows a user to Create, List, Update and Delete customers.  A customer is made of up the following attributes;_

- _Name_
- _Email_
- _Telephone_
- _Address_
    - _Street_
    - _City_
    - _State_
    - _Zip_

_The web application should use JSON formatted HTTP requests and responses.  And the storage mechanism for each customer record should be either in a relational or a non-relational database._

_Please feel free to showcase your ability by adding functionality such as unit testing, error handling, data validation, etc. . . time permitting._

**My Approach/Goals:**
--
- Leverage Context and Dependency Injecting and, for the most part have this be  “CDI Based” application. I choose to use** JBoss Weld (**http://weld.cdi-spec.org)for the CDI framework implementation in this project.

- Use JPA exclusively for persistence and have the persistence be agnostic and abstracted (I.E.  you can run this app on top of MongoDB or your favorite SQL JDBC db). **EclipseLink** is used for the JPA implementation framework. (http://www.eclipse.org/eclipselink/). _*note: the project “as is” includes dependencies for and is configured with an in memory HyperSonic DB for quick build and demonstration._

- Have the application be drop in war deployable and run via servlet container such as Apache Tomcat (Tested Tomcat and Wildfly only).  Because Tomcat is not a full JEE server extra dependencies and configuration are included.

- Read the code comments for more. 


**Because It’s a (Time Limited) Test:**
--
- The Web UI is Junk!  It was created as the bare minimum needed to test the server side functionality.

- There are missing features one would expect to find in web services. I.E. searching, sorting, pagination and more data validation — again,  left out because of time constraints. Only the simple **CRUD** functions are implemented but, the design allows for easy addition of functionality.

- The data model is flat with no relational elements (honestly the question did require them). The database schema is left entirely to the JPA implementation (I’d never suggest or allow this in a production app).

- Logging and Exceptions - for the most part, exceptions that cannot be handled (most of them can’t ) are propagated to the end-points where they are logged and a internal server error response is returned to the client.   Adding new exception types at different abstraction points while handling, propagating and logging them as necessary could be done with more time.

- Other short cuts, if you’re good you will see them.


