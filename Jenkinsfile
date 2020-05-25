pipeline {
    agent any
    stages {
        stage ('Build Backend') {
            steps {
                bat 'mvn clean package -DskipTests=true'
            }
        }
        stage ('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }
        stage ('Sonar Analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_local'){
                    bat "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=Backend -Dsonar.host.url=http://localhost:9000 -Dsonar.login=82d7045a66f4b6ae42a1ee462803f4cea02b9589 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/src/test/**,**/model/**,**Aplication.java"
                }
            }
        }
        stage ('Quality Gate') {
            steps {
                sleep(15)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}