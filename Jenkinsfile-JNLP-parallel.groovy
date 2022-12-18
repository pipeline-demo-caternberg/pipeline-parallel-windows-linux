pipeline {
    agent none
    stages {
        stage("JNLP") {
            parallel {
                stage("stage-jnlp1") {
                    options {
                        skipDefaultCheckout(true)
                    }
                    agent {
                        kubernetes {
                            cloud "myk8s"
                            yamlFile 'pods/jnlp.yaml'
                            defaultContainer 'jnlp'
                        }
                    }
                    steps {
                        powershell 'Get-ChildItem Env: | Sort Name'
                        powershell 'Write-Output "Hello JNLP1"'
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
                stage("stage-jnlp2") {
                    options {
                        skipDefaultCheckout(true)
                    }
                    agent {
                        kubernetes {
                            cloud "myk8s"
                            yamlFile 'pods/jnlp.yaml'
                            defaultContainer 'jnlp'
                        }
                    }
                    steps {
                        powershell 'Get-ChildItem Env: | Sort Name'
                        powershell 'Write-Output "Hello JNLP2"'
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

