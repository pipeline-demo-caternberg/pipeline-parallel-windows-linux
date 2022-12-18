podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    spec:
      containers:
        - name: jnlp
          #image: jenkins/inbound-agent:jdk11-windowsservercore-1809
          image: jenkins/inbound-agent:3077.vd69cf116da_6f-4-jdk11-windowsservercore-ltsc2019
          command:
            - cat
          tty: true
          resources:
            requests:
              memory: "4Gi"
              cpu: "2"
              ephemeral-storage: "1Gi"
            limits:
              memory: "6Gi"
              cpu: "4"
              ephemeral-storage: "2Gi"
      nodeSelector:
        kubernetes.io/os: windows
      ''') {
    node(POD_LABEL) {
        container('jnlp') {
            sh 'hello connected'
            sh 'env | sort'
        }
    }
}