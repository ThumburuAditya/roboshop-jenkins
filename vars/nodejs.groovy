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
                  //sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.42.130:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
                sh 'echo Code quality'
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
                when {
                    expression {
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {
                    sh 'npm install'
                    sh 'echo $TAG_NAME >VERSION'
                    sh 'zip -r ${component}-${TAG_NAME}.zip node_modules server.js VERSION ${schema_dir}'
                    sh 'curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${component}-${TAG_NAME}.zip http://172.31.34.124:8081/repository/${component}/${component}-${TAG_NAME}.zip'

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