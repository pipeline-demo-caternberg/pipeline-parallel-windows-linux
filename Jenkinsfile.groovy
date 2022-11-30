pipeline {
    agent none

    stages {
        stage("build and deploy on Windows and Linux") {
            parallel {
                stage("windows") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/win.yaml'
                            defaulContainer 'shell'
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
                            defaulContainer 'shell'
                        }
                    }
                    stages {
                        stage("build") {
                            steps {
                                echo "build LINUX"
                                sh "hostname"
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
