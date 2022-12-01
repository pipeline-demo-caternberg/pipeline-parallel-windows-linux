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
                    steps {
                        echo "build WINDOWS"
                        // run powershell command here
                        powershell 'Get-ChildItem Env: | Sort Name'
                        //powershell 'Get-Date; Start-Sleep -Seconds 10; Get-Date'
                        powershell 'Write-Output "Hello WINDOWS"'
                        powershell '''for (){
                                          Get-Date; 
                                          Start-Sleep -Seconds 10;
                                          Write-Host "ECHO FROM WINDOWS"
                                       }'''
                    }
                }
                stage("linux") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/linux.yaml'
                            defaultContainer 'maven'
                        }
                    }
                    steps {
                        echo "build LINUX"
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

