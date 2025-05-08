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

        stage('Build JAR') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
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
            bat "docker stop ${CONTAINER_NAME} || true"
            bat "docker rm ${CONTAINER_NAME} || true"
        }
    }
}