pipeline {
    agent none
    stages {
        stage("Windows and Linux") {
            parallel {
                stage("stage-windows") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/windows.yaml'
                            defaultContainer 'shell'
                        }
                    }
                    steps {
                        echo "steps WINDOWS"
                        powershell 'Get-ChildItem Env: | Sort Name'
                        powershell 'Write-Output "Hello WINDOWS"'
                        powershell '''for (){
                                          Get-Date; 
                                          Start-Sleep -Seconds 10;
                                          Write-Host "ECHO FROM WINDOWS"
                                       }'''
                    }
                }
                stage("stage-linux") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/linux.yaml'
                            defaultContainer 'maven'
                        }
                    }
                    steps {
                        echo "steps LINUX"
                        sh "hostname"
                        sh "env | sort"
                        sh '''
                                    while :; do echo 'ECHO FROM LINUX'; 
                                    date #"%s";
                                    sleep 10;done
                                '''
                    }
                }
            }
        }
    }
}

