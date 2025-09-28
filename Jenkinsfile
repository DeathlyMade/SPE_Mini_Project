pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6' // Replace with your Maven tool name in Jenkins
        nodejs 'NodeJS 16.14.2' // Replace with your NodeJS tool name in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/DeathlyMade/SPE_Mini_Project.git'
            }
        }

        stage('Build Backend') {
            steps {
                sh 'mvn -f pom.xml clean install'
            }
        }

        stage('Test Backend') {
            steps {
                sh 'mvn -f pom.xml test'
            }
        }

        stage('Build Frontend') {
            steps {
                dir('frontend/science-calc') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Test Frontend') {
            steps {
                dir('frontend/science-calc') {
                    sh 'npm test'
                }
            }
        }

        stage('Package Applications') {
            steps {
                // Example of archiving artifacts
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                dir('frontend/science-calc/build') {
                    archiveArtifacts artifacts: '**/*', fingerprint: true
                }
            }
        }
    }

    post {
        always {
            echo 'Build process finished.'
        }
        success {
            echo 'Build successful!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}