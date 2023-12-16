def call() {
    pipeline {

        agent {
            node {
                label 'workstation'
            }
        }

        options {
            ansiColor('xterm')
        }
        stages {

            stage('code compile') {
                steps {
                    sh 'mvn compile'
                }
            }

            stage('code quality') {
                steps {
                    //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.42.130:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
                    sh 'Code quality'
                }
            }
            stage('unit test cases') {
                steps {
                    sh 'echo unit tests'
                    //sh 'mvn test'
                }
            }
            stage('checkmarx sast scan') {
                steps {
                    sh 'echo sast scan'
                }
            }
            stage('checkmarx SCA scan') {
                steps {
                    sh 'echo SCA scan'
                }
            }
        }

        post {
            always {
                cleanWs()
            }
        }

    }


}