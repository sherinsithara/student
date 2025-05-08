pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'student-app'
        DOCKER_TAG = 'latest'
        CONTAINER_NAME = 'student-container'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/sherinsithara/student.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }

        stage('Post-Build Actions') {
            steps {
                echo 'Build and deployment complete!'
            }
        }
    }

    post {
        always {
            // Stop and remove the container after the build
            bat "docker stop ${CONTAINER_NAME} || true"
            bat "docker rm ${CONTAINER_NAME} || true"
        }
    }
}