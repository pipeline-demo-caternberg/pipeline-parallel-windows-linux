pipeline {
    agent none
    stages {
        stage("Linux") {
            parallel {
                stage("stage-linux1") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/linux.yaml'
                            defaultContainer 'linux'
                        }
                    }
                    steps {
                        echo "steps LINUX1"
                        sh "hostname"
                        sh "sleep 1m"
                    }
                }
                stage("stage-linux2") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/linux.yaml'
                            defaultContainer 'linux'
                        }
                    }
                    steps {
                        echo "steps LINUX2"
                        sh "hostname"
                        sh "sleep 1m"
                      }
                }
            }
        }
    }
}

