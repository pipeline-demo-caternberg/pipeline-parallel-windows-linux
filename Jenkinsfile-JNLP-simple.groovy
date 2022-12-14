podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    spec:
      containers:
        - name: jnlp
          #image: jenkins/inbound-agent:jdk11-windowsservercore-1809
          image: jenkins/inbound-agent:3077.vd69cf116da_6f-4-jdk11-windowsservercore-ltsc2019
          workingDir: "/home/jenkins/agent"
      nodeSelector:
        kubernetes.io/os: windows
      ''') {
    node(POD_LABEL) {
        container('jnlp') {

            powershell 'Get-ChildItem Env: | Sort Name'
            powershell 'Write-Output "Hello WINDOWS"'
            powershell 'Get-ChildItem -Force'
        }
    }
}