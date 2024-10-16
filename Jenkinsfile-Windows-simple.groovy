podTemplate(yaml: '''
      apiVersion: v1
      kind: Pod
      spec:
        containers:
        - name: jnlp
          image: jenkins/inbound-agent:jdk17-windowsservercore-ltsc2019
          #image: cloudbees/cloudbees-core-agent:2.462.3.3-windowsservercore-ltsc2022
        - name: shell
          image: mcr.microsoft.com/powershell:preview-windowsservercore-1809
          command:
          - powershell
          args:
          - Start-Sleep
          - 999999
        nodeSelector:
          kubernetes.io/os: windows
      ''') {
    node(POD_LABEL) {
        container('shell') {
            // run powershell command here
            powershell 'Get-ChildItem Env: | Sort Name'
        }
    }
}
