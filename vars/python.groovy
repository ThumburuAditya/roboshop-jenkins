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

            stage('code quality') {
                steps {
                    sh 'echo code quality'
                }
            }
            stage('unit test cases') {
                steps {
                    sh 'echo unit tests'
                }
            }
            stage('checkmarx sast scan') {
                steps {
                    sh 'sast scan'
                }
            }
            stage('checkmarx SCA scan') {
                steps {
                    sh 'SCA scan'
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