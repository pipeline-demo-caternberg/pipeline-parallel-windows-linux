/*
this file describe a declarative jenkins pipeline for development of MPDP
    - configure conan
    - execute different builds with matrix
    - run unit tests
    - do code analysis
*/
def myls = 'ls -lR'

pipeline {
    parameters {
        choice choices: ['all' ,'linux', 'windows'], description: 'Run on specific platform', name: 'PLATFORM_FILTER'
    }

    agent none

    environment {
      THEJOB="${env.JOB_NAME.replaceAll("/", "-")}"
      JFROG_CLI_BUILD_NAME = "$THEJOB"
      JFROG_CLI_BUILD_NUMBER = "${env.BUILD_NUMBER}"
      JFROG_CLI_BUILD_URL = "${env.BUILD_URL}"
    }

    stages {


        // Example 2 Create agents in every stage block.
        stage('Code-Analyses2') {
            parallel {
                stage('ClangFormat') {
                    agent {
                        kubernetes {
                            defaultContainer 'linux'
                            yamlFile 'pods/linux.yaml'
                        }
                    }
                    steps { 
                        sh 'echo default container' 
                        sh "${myls}"
                    }
                } // end ClangFormat
                stage('GoFormat') {
                    agent {
                        kubernetes {
                            defaultContainer 'linux'
                            yamlFile 'pods/linux.yaml'
                        }
                    }
                    steps { 
                        sh 'echo default container' 
                        sh "${myls}"
                    }
                } // end GoFormat
                stage('ProgetFormat') {
                    agent {
                        kubernetes {
                            defaultContainer 'linux'
                            yamlFile 'pods/linux.yaml'
                        }
                    }
                    steps { 
                        sh 'echo default container' 
                        sh "${myls}"
                    }
                } // end ProgetFormat
            } // end parallel
        }

        stage('Matrix Build and Test') {
            matrix {
                agent {
                    kubernetes {
                        defaultContainer "${PLATFORM}"
                        yamlFile "pods/${PLATFORM}.yaml"
                    }
                }

                when {
                    anyOf {
                        expression { params.PLATFORM_FILTER == 'all' }
                        expression { params.PLATFORM_FILTER == "${PLATFORM}" }
                    }
                }

                axes {
                    axis {
                        name 'PLATFORM'
                        values 'linux', 'windows'
                    }
                    axis {
                        name 'COMPILER'
                        values 'clang13', 'gcc11', 'msvc'
                    }
                    axis {
                        name 'BUILD_TYPE'
                        values 'debug', 'release'
                    }
                } // end of axes

                excludes {
                    exclude {
                        axis {
                            name 'PLATFORM'
                            values 'linux'
                        }
                        axis {
                            name 'COMPILER'
                            values 'msvc'
                        }
                        axis {
                            name 'BUILD_TYPE'
                            values 'debug', 'release'
                        }
                    } // linux_msvc_{debug,release}

                    exclude {
                        axis {
                            name 'PLATFORM'
                            values 'windows'
                        }
                        axis {
                            name 'COMPILER'
                            values 'clang13', 'gcc11'
                        }
                        axis {
                            name 'BUILD_TYPE'
                            values 'debug', 'release'
                        }
                    } // win_{clang13,gcc10}_{debug,release}
                } // end of excludes

                stages {
                    stage('Configure') {
                      steps {
                        script {
                          if (PLATFORM == 'linux') {
                            sh 'echo "Configure"'
                          } else {
                            powershell 'echo "Configure"'
                          }
                        }
                      }
                    }// end of Configure

                    // steps that should happen on all nodes go here
                    stage('Build') {
                      steps {

                        script {
                          if (PLATFORM == 'linux') {
                            echo "steps LINUX"
                            sh "hostname"
                            sh '''
                                        i=0
                                        while [ $i -ne 100 ]
                                        do
                                            i=$(($i+1))
                                            echo 'ECHO FROM LINUX';
                                            ls -l:
                                            sleep 10;
                                        done
                                    '''
                          } else {
                            echo "steps WINDOWS"
                            powershell 'Write-Output "Hello WINDOWS"'
                            powershell '''for (){
                                            Get-ChildItem -Force
                                            Get-Date;
                                            Start-Sleep -Seconds 10;                                              
                                            Write-Host "ECHO FROM WINDOWS"
                                        }'''
                          }
                        }
                      }
                    }// end of Build

                    stage('Test') {
                      steps {

                        script {
                          if (PLATFORM == 'linux') {
                            sh 'echo "Test"'
                          } else {
                            powershell 'echo "Test"'
                          }
                        }
                      }
                    } // end of Test
                } // end of stages
            } // end of matrix
        } // Matrix Build and Test
    }
}
