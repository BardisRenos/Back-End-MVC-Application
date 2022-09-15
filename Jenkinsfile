pipeline {
    agent any

    stages {
        stage('Hello'){
            steps {
                echo 'Hello World'
            }
        }
        stage("Build"){
            steps {
                sh "./mvn clean install"
            }
        }
        stage("Tests"){
            steps {
                sh "./mvn test"
            }
        }
    }
}