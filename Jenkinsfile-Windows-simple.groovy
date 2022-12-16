podTemplate(yaml: '''
      apiVersion: v1
      kind: Pod
      spec:
        containers:
        - name: jnlp
          //image: jenkins/inbound-agent:jdk11-windowsservercore-1809
          image: jenkins/inbound-agent:3077.vd69cf116da_6f-4-jdk11-windowsservercore-ltsc2019
        - name: shell
          #image: mcr.microsoft.com/powershell:preview-windowsservercore-1809
          image: mcr.microsoft.com/powershell:latest
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