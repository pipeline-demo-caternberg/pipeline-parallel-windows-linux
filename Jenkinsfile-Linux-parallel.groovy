pipeline {
    agent none
    stages {
        stage("Windows and Linux") {
            parallel {
                stage("stage-linux2") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/linux.yaml'
                            defaultContainer 'linux'
                        }
                    }
                    steps {
                        echo "steps LINUX"
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
                        echo "steps LINUX"
                        sh "hostname"
                        sh "sleep 1m"
                      }
                }
            }
        }
    }
}

