pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'container-student'  // Docker image name
        HOST_PORT = '8086'  // Default host port
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm // Checkout the source code from Git
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build the application using Maven (adjust if using Gradle or another tool)
                    bat './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image for your app
                    bat "docker build -t ${DOCKER_IMAGE} ."
                }
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
                    bat "docker run -d -p ${HOST_PORT}:8086 ${DOCKER_IMAGE}"
                    echo "Container is running on port ${HOST_PORT}."
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Stop and remove any running containers after the build is done
                    bat """
                        FOR /F "tokens=*" %%i IN ('docker ps -q --filter "ancestor=${DOCKER_IMAGE}"') DO docker stop %%i
                        FOR /F "tokens=*" %%i IN ('docker ps -aq --filter "ancestor=${DOCKER_IMAGE}"') DO docker rm %%i
                    """
                }
            }
        }
    }

    post {
        always {
            // Clean up: Remove Docker containers
            bat """
                FOR /F "tokens=*" %%i IN ('docker ps -q --filter "ancestor=${DOCKER_IMAGE}"') DO docker stop %%i
                FOR /F "tokens=*" %%i IN ('docker ps -aq --filter "ancestor=${DOCKER_IMAGE}"') DO docker rm %%i
            """
        }
    }
}
