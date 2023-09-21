import groovy.io.FileType
import java.util.logging.Logger

// https://stackoverflow.com/questions/42146524/write-log-from-jenkinsfile
Logger logger = Logger.getLogger('org.example.jobdsl')
logger.info(message)


def list = []

def dir = new File(workspace)
dir.eachFileRecurse (FileType.FILES) { file ->
  list << file
}


list.each {
  logger.info(it.path)
}




jobarray = ['job1','job3']
for(currentjob in jobarray)
    multibranchPipelineJob("$currentjob") { // normal variable syntax
        branchSources {
            git {
                id = "^${currentjob}"       // accessing variable with escaping
                remote('https://github.com/jenkinsci/configuration-as-code-plugin.git')
            }
        }
    }