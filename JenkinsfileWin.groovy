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

                stage("windows2") {
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
            }
        }
    }
}
