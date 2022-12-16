pipeline {
    agent {
        kubernetes {
            cloud "myk8s"
            yamlFile 'pods/windows.yaml'
            defaultContainer 'windows'
        }
    }
    stages {
        stage("Windows and Linux") {
            parallel {
                stage("stage-windows1") {
                    options {
                        skipDefaultCheckout(true)
                    }

                    steps {
                        echo "steps WINDOWS"
                        powershell 'Get-ChildItem Env: | Sort Name'
                        powershell 'Write-Output "Hello WINDOWS1"'
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
                stage("stage-windows2") {
                    options {
                        skipDefaultCheckout(true)
                    }

                    steps {
                        echo "steps WINDOWS"
                        powershell 'Get-ChildItem Env: | Sort Name'
                        powershell 'Write-Output "Hello WINDOWS2"'
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
            }
        }
    }
}

