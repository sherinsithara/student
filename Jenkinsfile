pipeline {
    agent any

    stages {
        stage('Build Docker Image') {
            steps {
                bat 'docker build -t student-app .'
            }
        }

        stage('Run Docker Container') {
            steps {
                bat 'docker run -d -p 8086:8080 --name student-container student-app'
            }
        }
    }
}
