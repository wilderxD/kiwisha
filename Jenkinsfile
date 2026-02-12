pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                sh './mvnw clean compile'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Quality Scan') {
            steps {
                sh './mvnw checkstyle:check'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw package -DskipTests'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            jacoco execPattern: 'target/jacoco.exec'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
        success {
            echo '¡Pipeline completado con éxito!'
        }
        failure {
            echo 'El pipeline ha fallado. Revisar logs.'
        }
    }
}
