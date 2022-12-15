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
                       /* powershell '''for ($i = 0; $i -lt 5; $i++){
                                            "$i";
                                            ls -Force;
                                            Get-Date;
                                            Start-Sleep -Seconds 5;
                                            Write-Host "ECHO FROM WINDOWS"
                                        }'''

                        */
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
                        sh "sleep 5m"
                       /* sh '''
                                        i=0
                                        while [ $i -ne 50 ]
                                        do
                                            i=$(($i+1))
                                            echo 'ECHO FROM LINUX';
                                            sleep 10;
                                        done
                                    '''

                        */
                    }
                }
            }
        }
    }
}

