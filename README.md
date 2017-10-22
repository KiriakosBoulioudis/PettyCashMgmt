# PettyCashMgmt

PettyCashMgmt is a Java sample Web-Application (Spring-MVC) with a public ('/', 'public/**') and a secured ('custodio/**') area.
Access to the secured area if using the sql-scripts (DDL_pettycash.sql and DML_pettycash.sql) 
are: (name:'custodio' with password:'custodio') and (name:'custodio2' with password:'custodio2')


## Important Packages and Files
pom.xml
dbscripts:									
		- DDL_pettycash.sql
		- DML_pettycash.sql   
src\main\java\com\pettycash\configuration:	
		- AppConfiguration.java
		- SecurityConfiguration.java
src\main\java\com\pettycash\controller:
src\main\java\com\pettycash\dao:
src\main\java\com\pettycash\model:
src\main\java\com\pettycash\service:
src\main\resources:
		- log4j.properties
src\main\webapp\WEB-INF\views:
		- main.jsp
		- public.jsp
		- custodio.jsp
		



## Design
### Database structure:
user : The user of the application and the public user(name) which asked for money
role : The possible roles that user can have in the application
user_role : The Relation of user and roles.
public_request : The information of the public money requests.
custodian_request : The information of the custodian money registrations.
cash_balance : The current cash balance of a custodian.

	|--------------|		|---------------|		|---------------|
	| Cash_Balance |----------------|	User	|---------------| User_Role	|
	|--------------|		|---------------|		|---------------|
					| 	| 	|
					|	|	|		|---------------|
					|	|	|---------------| Custodian_Req	|
					|	|			|---------------|
					|---------------|					
					| Public_Req	|
					|---------------|

												
### Application Architecture	
The Application is using the Spring Annotation Based Configuration without web.xml, the main configuration file: AppConfiguration.java.
For the secured custodian area and session handling is used the Spring Security, configured at SecurityConfiguration.java
The used Web-Framework is the Spring MVC with JPA and JTA using Hibernate.

Spring MVC uses the Model-View-Controller concept: 
Model: The service, model and data access classes (packages src\main\java\com\pettycash\ model, service and dao).
Controller: The controller of the application (package src\main\java\com\pettycash\controller).
View: The jsp files which are used to view the application (package src\main\webapp\WEB-INF\views).

3-Tier Concept: Client Tier, Application Tier and Data Tier
And the Application Tier is divide in 3-Layers:
Presentation-Layer: Controller classes (package src\main\java\com\pettycash\controller).
Business-Layer: Service classes (package src\main\java\com\pettycash\service).
Data Access Layer: Model and DAO classes (packages src\main\java\com\pettycash\ model and dao).


## Requirements
- Java jdk 
- Java Servlet Container Server (example: tomcat)
- Database (example: postgres)
- Maven 


## Configuration
The included pom.xml file can be used to retrieve the necessary libraries ,to compile the sources and generate the .war file of the application. 
Its necessary to configure the compiler (javac) location of the jdk installation at your environment.
You can find this in the pom.xml file under the structure:(<build><plugins><plugin><configuration><executable>).

The used Database at your environment must be configured in the AppConfiguration class under the Bean datasource.
Change the values for URL, Username and Password (at the methods .setUrl(), setUsername() and setPassword())
like they are for the access at your environment.

The Logging configuration can be changed at log4j.properties


## Run
### Database
Execute at the database for the application the dbscripts: DDL_pettycash.sql and DML_pettycash.sql
The database user for the application access must have the following priveleges:

### Application 
The generated pettycash.war file can be deployed at a servlet container server (example tomcat).
And can be accessed under the URL: server main path + /pettycash .
