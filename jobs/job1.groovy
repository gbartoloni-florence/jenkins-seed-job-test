import groovy.io.FileType
import java.util.logging.Logger
import org.yaml.snakeyaml.Yaml


// https://stackoverflow.com/questions/42146524/write-log-from-jenkinsfile
Logger logger = Logger.getLogger('org.example.jobdsl')
logger.info(message)


def list = []

def dir = new File(workspace + "/seed-job-config/configs")
dir.eachFileRecurse (FileType.FILES) { file ->
  list << file
}

Yaml parser = new Yaml()


list.each {
  logger.info(it.path)
  logger.info(it.name)
  List configuration = parser.load((it as File).text)
  configuration.each{logger.info(it.subject)}
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