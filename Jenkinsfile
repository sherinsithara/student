pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm // Checkout the source code from Git
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvnw clean install'  // Build the application using Maven Wrapper
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t ${DOCKER_IMAGE} ."  // Build Docker image
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Try ports 8086, 8087, 8088, etc.
                    def portFound = false
                    def triedPorts = ['8086', '8087', '8088', '8089', '8090']
                    for (port in triedPorts) {
                        try {
                            // Check if the port is available
                            bat "netstat -ano | findstr :${port}"
                            echo "Port ${port} is already in use."
                        } catch (Exception e) {
                            echo "Port ${port} is available."
                            HOST_PORT = port
                            portFound = true
                            break
                        }
                    }

                    if (!portFound) {
                        error "No available ports found."
                    }

                    // Run the Docker container on the selected port
                    bat "docker run -d -p ${HOST_PORT}:8086 --name ${CONTAINER_NAME} ${DOCKER_IMAGE}"
                    echo "Container is running on port ${HOST_PORT}."
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Stop and remove any running containers with the name 'my-student-container'
                    bat """
                        FOR /F "tokens=*" %%i IN ('docker ps -aq --filter "name=${CONTAINER_NAME}"') DO (
                            echo Stopping container %%i
                            docker stop %%i
                            echo Removing container %%i
                            docker rm %%i
                        )
                    """
                }
            }
        }
    }

    post {
        always {
            // Clean up: Remove Docker containers after the build is done
            bat """
                FOR /F "tokens=*" %%i IN ('docker ps -aq --filter "name=${CONTAINER_NAME}"') DO (
                    echo Stopping container %%i
                    docker stop %%i
                    echo Removing container %%i
                    docker rm %%i
                )
            """
        }
    }
}
