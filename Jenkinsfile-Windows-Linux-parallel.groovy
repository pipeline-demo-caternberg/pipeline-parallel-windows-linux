pipeline {
    agent none
    stages {
        stage("Windows and Linux") {
            parallel {
                stage("stage-windows") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/windows.yaml'
                            defaultContainer 'windows'
                        }
                    }
                    steps {
                        echo "steps WINDOWS"
                        powershell 'Get-ChildItem Env: | Sort Name'
                        powershell 'Write-Output "Hello WINDOWS"'
                        powershell 'Get-ChildItem -Force'
                        powershell '''for (){
                                          Get-Date; 
                                          Start-Sleep -Seconds 10;
                                          Write-Host "ECHO FROM WINDOWS";
                                          Get-ChildItem -Force
                                       }'''
                    }
                }
                stage("stage-linux") {
                    agent {
                        kubernetes {
                            yamlFile 'pods/linux.yaml'
                            defaultContainer 'linux'
                        }
                    }
                    steps {
                        echo "steps LINUX"
                        sh "hostname"
                        sh "pwd"
                        sh "pwd"
                        sh '''
                                    while :; do echo 'ECHO FROM LINUX'; 
                                    date;
                                    ls -l;
                                    sleep 10;done
                                '''
                    }
                }
            }
        }
    }
}

