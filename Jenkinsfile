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
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image for your app
                    sh "docker build -t ${DOCKER_IMAGE} ."
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Run the Docker container
                    sh "docker run -d -p 8086:8086 ${DOCKER_IMAGE}"
                }
            }
        }

        stage('Cleanup') {
            steps {
                script {
                    // Stop and remove any running containers after the build is done
                    sh "docker ps -q --filter ancestor=${DOCKER_IMAGE} | xargs docker stop || true"
                    sh "docker ps -aq --filter ancestor=${DOCKER_IMAGE} | xargs docker rm || true"
                }
            }
        }
    }

    post {
        always {
            // Clean up: Remove Docker containers
            sh "docker ps -q --filter ancestor=${DOCKER_IMAGE} | xargs docker stop || true"
            sh "docker ps -aq --filter ancestor=${DOCKER_IMAGE} | xargs docker rm || true"
        }
    }
}
