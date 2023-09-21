pipeline {
    agent any
    
    stages {
        stage('Genera Pipelines') {
            steps {
                
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