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
                script {
                    // Check if port 8086 is available, else use 8087
                    def portCheck = bat(script: 'netstat -ano | findstr :8086', returnStatus: true)
                    if (portCheck != 0) {
                        bat 'docker run -d -p 8086:8080 --name student-container student-app'
                    } else {
                        bat 'docker run -d -p 8087:8080 --name student-container student-app'
                    }
                }
            }
        }
    }
}
