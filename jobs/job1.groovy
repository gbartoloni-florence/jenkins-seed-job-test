import groovy.io.FileType
import java.util.logging.Logger
import org.yaml.snakeyaml.Yaml
import java.util.Random

// https://stackoverflow.com/questions/42146524/write-log-from-jenkinsfile
Logger logger = Logger.getLogger('org.example.jobdsl')

def list = []

def dir = new File(workspace + "/seed-job-config/configs")
dir.eachFileRecurse (FileType.FILES) { file ->
  if(file.name.endsWith('.yaml')) {
    list << file
  }
}

Yaml parser = new Yaml()

Random random = new Random()

list.each { configFile -> 
  logger.info("Opening " + configFile.path)
  Map configuration = parser.load((configFile as File).text)
  configuration.each{logger.info(it.toString())}
  configuration.apps.each { app ->


  // com.cloudbees.plugins.credentials.CredentialsProvider.Create,com.cloudbees.plugins.credentials.CredentialsProvider.Delete,com.cloudbees.plugins.credentials.CredentialsProvider.ManageDomains,com.cloudbees.plugins.credentials.CredentialsProvider.Update,com.cloudbees.plugins.credentials.CredentialsProvider.View,hudson.model.Item.Build,hudson.model.Item.Cancel,hudson.model.Item.Configure,hudson.model.Item.Create,hudson.model.Item.Delete,hudson.model.Item.Discover,hudson.model.Item.Move,hudson.model.Item.Read,hudson.model.Item.Workspace,hudson.model.Run.Delete,hudson.model.Run.Replay,hudson.model.Run.Update,hudson.model.View.Configure,hudson.model.View.Create,hudson.model.View.Delete,hudson.model.View.Read,hudson.scm.SCM.Tag

    multibranchPipelineJob(app.name) {
      authorization {
        configuration.users.each { user ->
          logger.info("Setting configuration for user " + user.toString())
          permissions (user.id, ["hudson.model.Item.Read", "hudson.model.View.Read", "hudson.model.Item.Build"])
          // permissionAll(user.id)
        }
      }
      branchSources {
          git {
              id = "^${app.name}"       // accessing variable with escaping
              remote(app.repoUrl)
              credentialsId('github-ci')
          }
      }
      orphanedItemStrategy {
          discardOldItems {
              numToKeep(10)
          }
      }
      properties {
        folderLibraries {
          libraries {
            libraryConfiguration {
              name("fcg-shared-lib")
              retriever {
                modernSCM {
                  scm {
                    git {
                      remote('https://github.com/gbartoloni-florence/jenkins-shared-library.git')
                    }
                  }
                }
              }
            }
          }
        }
      }
      factory {
        remoteJenkinsFileWorkflowBranchProjectFactory {
          remoteJenkinsFile('jenkinsfiles/job1.Jenkinsfile')
          localMarker('') /* everything is valid */
          remoteJenkinsFileSCM {
            gitSCM {
              userRemoteConfigs	{
                userRemoteConfig {
                  name('origin')
                  url('https://github.com/gbartoloni-florence/jenkins-shared-library.git')
                  refspec("+refs/heads/main:refs/remotes/origin/main")
                  // credentialsId(SCM_CREDENTIALS_ID)
                }
              }
              branches {
                branchSpec {
                  name('main')
                }
              }
              browser {} // required, but doesn't require configuration
              // gitTool('/usr/bin/env git') // or wherever makes sense
            }
          }
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


