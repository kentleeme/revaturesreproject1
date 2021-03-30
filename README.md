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
## Getting Started
- Set up PostgreSQL either locally or on AWS.
- Install / set up helm.
    - Add ingress-nginx, https://kubernetes.github.io/ingress-nginx and grafana, https://grafana.github.io/helm-charts to helm repo.
    - Run `helm install ingress-nginx ingress-nginx/ingress/ngninx`;
    - Run `helm install grafana grafana/grafana-chart -f values.yaml`;
    - Run `helm install loki grafana/loki-chart -f values.yaml`;
- Create hung-ly namespace.
- Create environment variables for DB_URL, DB_PASSWORD, and DB_USERNAME.
- Create secret and fluentD config:
    - `kubectl create secret generic hung-credentials --from-literal=url=$DB_URL --from-literal=username=$DB_USERNAME --from-literal=password=$DB_PASSWORD`
    - `kubectl create configmap fluent-conf --from-file fluent.conf`
- Apply the following manifests:
    - `kubectl apply -f kubernetes/loki-external.yml`
    - `kubectl apply -f kubernetes/project1-service.yml`
    - `kubectl apply -f kubernetes/project1-ingress.yml`
    - `kubectl apply -f kubernetes/project1-deployment.yml`
## Use
Install Postman to send requests to pahts set up in Controller classes. For example, send a `GET` request to `localhost:7000/learning-management/users` to see a list of users.
