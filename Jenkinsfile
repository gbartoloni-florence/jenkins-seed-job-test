pipeline {
    agent any
    
    stages {
        stage('Genera Pipelines') {
            steps {
                
                    
                    jobDsl {
                        scriptText('''
                        job('demo') {
                            steps {
                                shell('echo Hello World!')
                            }
                        }'''
                        )
                    }
                
            }
        }
    }
}