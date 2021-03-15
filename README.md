# Revature-SRE-Project1
## Course Management Web Application
- Migrated a RESTful Javalin web application to Spring Boot deployed on Kubernetes. Application logs are aggregated with FluentD agents exporting to Loki and displayed in a Grafana Dashboard.
- Application Features
    - Create user account by type
        - Student
        - Instructor
        - Admin
    - Login/logout (Non-CRUD)
    - Admin
        - Manage users, students, instructors
            - Can perform all CRUD operations on them (view/add/edit/delete)
        - Create a Course
        - Modify/Delete a Course
        - Assign an Instructor to Course
    - Instructor
        - Update Instructor info
        - View all Courses they're assigned to
        - View all Students of a Course
    - Student
        - Update Student info
        - Enroll in a Course
        - Drop a Course
        - View all Courses they enroll in
        - Calculate GPA (Non-CRUD)
- Technologies: Kubernetes, Maven, Git, Log4J, Java, PostgreSQL, Spring Boot, Spring Data, AWS RDS, Grafana, Loki, FluentD
