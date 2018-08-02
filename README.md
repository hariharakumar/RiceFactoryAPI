-- You can Build the application using command : gradle clean build -x test deployToTomcat
   - To run the application, you can deploy to local tomcat container or use built in spring boot container

-- Uses logback for logging. Config file located in : /var/personal_projects/rice_factory/logback-spring.xml (refer to application.properties)
   - Make sure necessary directories are created and proper permissions are given for directories that store this application logs.
   - Attached sample logback.xml file to the project.

-- Application uses JWT. Use jwt.io to decode the token and see claims in the payload.
   - JWT token will be returned as Header in create user request - This is the only request that does not need JWT token.
   - Store the JWT returned as part of create user response and use that for subsequent requests.
   - Claims contain following fields : userId, iat, exp
   - jwt secret key is present in application.properties file
   - You can enable JWT by setting enable.jwt.token property to true.
   - JWT token expires in 1 day
   - Sample JWT for testing purposes that will expire in few years : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE4Mjg4OTQyNTAsInVzZXJJZCI6IjEwIiwiaWF0IjoxNDI4ODA3ODUyfQ.FiUha_zd1UrPzu16dSxwo56aTVX09KXCcZwQy-SYH3s


