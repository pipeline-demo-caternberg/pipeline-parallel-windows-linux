//Delete build queue
import hudson.model.*
import jenkins.model.Jenkins

def q = Jenkins.instance.queue
q.items.findAll { it.task.name.startsWith('my') }.each { q.cancel(it.task) }

//delete all idle nodes
for (aSlave in hudson.model.Hudson.instance.slaves) {
    if (aSlave.getComputer().isOffline()) {
        aSlave.getComputer().setTemporarilyOffline(true,null);
        aSlave.getComputer().doDoDelete();
    }
}