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
                    // Check if port 8086 is already in use
                    def portAvailable = false
                    try {
                        // Check if port 8086 is in use
                        bat "netstat -ano | findstr :8086"
                        portAvailable = true
                    } catch (Exception e) {
                        echo "Port 8086 is available."
                    }

                    // If port 8086 is already in use, change to another port
                    if (portAvailable) {
                        echo "Port 8086 is already in use. Trying port 8087."
                        // Assign a new port (e.g., 8087) if 8086 is occupied
                        script {
                            HOST_PORT = '8087'
                        }
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
