-- You can Run the application using command : gradle clean build -x test deployToTomcat

-- Uses logback for logging. Config file located in : /var/personal_projects/rice_factory/logback-spring.xml (refer to application.properties)
   - Make sure necessary directories are created and proper permissions are given for directories that store this application logs.
   - Attached sample logback.xml file to the project.

-- Application uses JWT. Use jwt.io to decode the token and see claims in the payload.
   - Claims contain following fields : userId, createdAt
   - jwt secret key is present in application.properties file

