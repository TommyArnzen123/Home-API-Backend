# Home-API-Backend
The Backend Spring Boot Application for the Home API Application.


Step One - Generate GitHub repositories for the frontend and backend applications. (COMPLETE)

Step Two - Generate Java/Spring Boot application and store it in the backend GitHub repository. (COMPLETE)
- Initial dependencies needed
- Spring Web
- PostgreSQL

Step Three - Make sure the backend GitHub repository can be accessed and updates can be pushed into it. (COMPLETE)
- Accessed (COMPLETE)
- Updates pushed (COMPLETE)

Step Four - Build out the backend application’s initial functionality.
- Build out the initial registration endpoints (able to make a connection in Postman).
  - User (COMPLETE)
  - Home (COMPLETE)
  - Location (Room) (RYAN)
  - Device (RYAN)


Future
- Add functionality to the registration endpoints.
  - Build out PostgreSQL database tables.
    - UserEntity - Used for registering users and logging users into the application. (COMPLETE)
    - Home Entity - Register a new home and associate it with a provided user id.
    - Location Entity - Register a new location (room) and associate it with a provided home id.
    - Device Entity - Register a new device and associate it with the provided location id.
  - Add encryption to the password field in the UserEntity table.
  - Do not allow users to be registered with usernames or email addresses that are already in the database.
- Build out the login endpoint.
  - Basic functionality (COMPLETE)
  - Add encryption.
- Build out the get home information endpoint (all homes for the provided user id).
- Build out the get location (room) information endpoint (all locations for the provided home id).
- Build out the record temperature (for location) endpoint.
- Build out the get temperature information (for location) endpoint (all temperature information for the provided location id).