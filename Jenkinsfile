pipeline {
    agent any
    
    stages {
        stage('Genera Pipelines') {
            steps {
                script {
                    def jobDslScript = '''
                        jobarray = ['job1','job2']
                        for(currentjob in jobarray)
                        multibranchPipelineJob("$currentjob") { // normal variable syntax
                            branchSources {
                                git {
                                    id = "^${currentjob}"       // accessing variable with escaping
                                    remote('https://github.com/jenkinsci/configuration-as-code-plugin.git')
                                }
                            }
                        }
                    '''
                    jobDsl(jobDslScript)
                }
            }
        }
    }
}