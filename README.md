This README contains information on how to setup, build and run the project. Read it. It is useful.

Getting and setting up the project:

1. Pull the project down.
2. Open the project in IntelliJ. Other IDEs may be used, but the project is configured to work correctly with IntelliJ. Other configurations may be necessary in order to get the project to build and run within other IDEs such as Eclipse.
3. Download and install a local instance of MySql server. Ensure the service is running properly. Take note of your credentials.
4. In IntelliJ Navigate to pom.xml file within the project in IntelliJ and make sure all dependencies are downloaded. If some are missing, right click anywhere on the pom.xml document and navigate to Maven and select "Download Sources". Then navigate to Maven and reload project.
5. Setup environment variables in IntelliJ. You can use the configuration settings. You will need to set them like so:
   * MYSQL_USERNAME={yourLocalUserName};MYSQL_PASSWORD={yourLocalMySqlPassword}
6. The project is configured to connect to MySql server localhost instance and port # 3306
7. Build the project
8. Run the project

Download and install MySql workbench. Sql credentials will be provided. They are also currently visible in the code, but will soon be deleted from code and changed to be stored in azure key vault.

Application User Credentials

1. Hotel Guest => Simply register with a valid email and a password. The email has to be unique so if it already exists in the database you will not be able to register with it.
2. Hotel Clerk => Simply create a hotel clerk account while logged into the system as a sys admin.
3. Sys Admin => Email: sysadmin@j3.net Password: password

