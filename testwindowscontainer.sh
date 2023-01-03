#! /bin/bash

# For loop 5 times
for i in {1..5}
do

cat <<EOF | kubectl apply -f -
apiVersion: v1
kind: Pod
metadata:
  name: windows-pod-${i}
spec:
  containers:
  - name: windows
    image: mcr.microsoft.com/powershell:preview-windowsservercore-1809
    #      resources:
    #        requests:
    #          memory: "8Gi"
    #          cpu: "2"
    #          ephemeral-storage: "1Gi"
    #        limits:
    #          memory: "8Gi"
    #          cpu: "4"
    #          ephemeral-storage: "2Gi"
    imagePullPolicy: IfNotPresent
    command:
      - powershell
    args:
      - "Start-Sleep"
      - '999999'
  nodeSelector:
    kubernetes.io/os: windows
EOF

done

## Put all cust_func in the background and bash
## would wait until those are completed
## before displaying all done message
wait
echo "All done"