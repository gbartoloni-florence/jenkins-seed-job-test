import groovy.io.FileType
import java.util.logging.Logger
import org.yaml.snakeyaml.Yaml
import java.util.Random

// https://stackoverflow.com/questions/42146524/write-log-from-jenkinsfile
Logger logger = Logger.getLogger('org.example.jobdsl')
logger.info(message)


def list = []

def dir = new File(workspace + "/seed-job-config/configs")
dir.eachFileRecurse (FileType.FILES) { file ->
  list << file
}

Yaml parser = new Yaml()

Random random = new Random()

list.each { configFile -> 
  logger.info("Opening " + configFile.path)
  Map configuration = parser.load((configFile as File).text)
  configuration.each{logger.info(it.toString())}
  configuration.apps.each { app ->

    multibranchPipelineJob(app.name) {
      authorization {
        configuration.users.each { user ->
          permissions (user.id, ["View/Read"])
        }
      }
      branchSources {
          git {
              id((random.nextInt(10 ** 8)).toString()) // IMPORTANT: use a constant and unique identifier
              remote(app.repoUrl)
              credentialsId('github-ci')
          }
      }
      orphanedItemStrategy {
          discardOldItems {
              numToKeep(10)
          }
      }
    }
  }
  listView(configuration.project) {
    jobs {
      configuration.apps.each { app ->
        name(app.name)
      }
    }
    columns{
      status()
      weather()
      name()
      lastSuccess()
      lastFailure()
      lastDuration()
      buildButton()
    }
  }
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