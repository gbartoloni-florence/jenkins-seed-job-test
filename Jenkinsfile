pipeline {
    agent any
    
    stages {
        stage("Clone Config Git Repository") {
            steps {
                dir('seed-job-config') {
                    git(
                        url: "https://github.com/gbartoloni-florence/jenkins-seed-job-config.git",
                        branch: "main",
                        changelog: true,
                        poll: false
                    )
                }
            }
        }
        stage('Genera Pipelines') {
            steps {
                    // https://stackoverflow.com/questions/41588626/invoke-job-dsl-from-jenkins-pipeline
                    // https://github.com/jenkinsci/job-dsl-plugin/wiki/User-Power-Moves#use-job-dsl-in-pipeline-scripts
                    jobDsl targets: ['jobs/*.groovy'].join('\n'),
                        removedJobAction: 'DELETE',
                        removedViewAction: 'DELETE',
                        lookupStrategy: 'SEED_JOB',
                        additionalClasspath: [].join('\n'),
                        additionalParameters: [workspace: WORKSPACE]

            }
        }
    }
}