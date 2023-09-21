pipeline {
    agent any
    
    stages {
        stage('Genera Pipelines') {
            steps {
                    jobDsl targets: ['jobs/*.groovy'].join('\n'),
                        removedJobAction: 'DELETE',
                        removedViewAction: 'DELETE',
                        lookupStrategy: 'SEED_JOB',
                        additionalClasspath: [].join('\n'),
                        additionalParameters: [message: 'Hello from pipeline']

            }
        }
    }
}