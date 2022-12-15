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
                        powershell '''for ($i = 0; $i -lt 10; $i++){
                                            "$i";
                                            ls -Force;
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
                            defaultContainer 'linux'
                        }
                    }
                    steps {
                        echo "steps LINUX"
                        sh "hostname"
                        sh "pwd"
                        sh "pwd"
                        sh '''
                                        i=0
                                        while [ $i -ne 20 ]
                                        do
                                            i=$(($i+1))
                                            echo 'ECHO FROM LINUX';
                                            sleep 10;
                                        done
                                    '''
                    }
                }
            }
        }
    }
}

