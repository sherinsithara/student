pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'container-student' // Docker image name
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
                    // Run the Docker container
                    bat "docker run -d -p 8086:8086 ${DOCKER_IMAGE}"
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Stop and remove any running containers after the build is done
                    bat """
                        FOR /F "tokens=*" %%i IN ('docker ps -q --filter ancestor=${DOCKER_IMAGE}') DO docker stop %%i
                        FOR /F "tokens=*" %%i IN ('docker ps -aq --filter ancestor=${DOCKER_IMAGE}') DO docker rm %%i
                    """
                }
            }
        }
    }

    post {
        always {
            // Clean up: Remove Docker containers
            bat """
                FOR /F "tokens=*" %%i IN ('docker ps -q --filter ancestor=${DOCKER_IMAGE}') DO docker stop %%i
                FOR /F "tokens=*" %%i IN ('docker ps -aq --filter ancestor=${DOCKER_IMAGE}') DO docker rm %%i
            """
        }
    }
}
