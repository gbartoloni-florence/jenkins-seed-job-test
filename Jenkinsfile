pipeline {
    agent any
    
    stages {
        stage('Genera Pipelines') {
            steps {
                
                    
                    jobDsl {
                        job('demo') {
                            steps {
                                shell('echo Hello World!')
                            }
                        }
                    }
                
            }
        }
    }
}