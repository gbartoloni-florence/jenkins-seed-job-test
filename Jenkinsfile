pipeline {
    agent any
    
    stages {
        stage('Genera Pipelines') {
            steps {
                script {
                    def jobDslScript = '''
                        job('demo') {
                            steps {
                                shell('echo Hello World!')
                            }
                        }
                    '''
                    jobDsl {
                        scriptText(jobDslScript)
                    }
                }
            }
        }
    }
}