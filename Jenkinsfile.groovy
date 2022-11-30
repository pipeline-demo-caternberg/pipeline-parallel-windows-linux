pipeline {
    agent none

    stages {
        stage("build and deploy on Windows and Linux") {
            parallel {
                stage("windows") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/windows.yaml'
                            defaultContainer 'shell'
                        }
                    }
                    stages {
                        stage("build") {
                            steps {
                                echo "build WINDOWS"
                               // run powershell command here
                               powershell 'Get-ChildItem Env: | Sort Name'
                            }
                        }
                        stage("deploy") {
                            steps {
                                echo "deploy threadA"
                            }
                        }
                    }
                }

                stage("linux") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/linux.yaml'
                            defaultContainer 'maven'
                        }
                    }
                    stages {
                        stage("build") {
                            steps {
                                echo "build LINUX"
                                sh "hostname"
                                sh 'sleep 100000'
                            }
                        }
                        stage("deploy") {
                            steps {
                                sh "echo deploy threadB"
                            }
                        }
                    }
                }
            }
        }
    }
}
