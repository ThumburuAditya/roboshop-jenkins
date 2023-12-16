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
                  sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.42.130:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
              }
          }
            stage('unit test cases') {
                steps {
                    sh 'echo unit tests'
                    //sh 'npm test'

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
            stage('Release Application') {
                when{
                    expression{
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {
                    sh 'env'
                    sh 'Release Application'
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