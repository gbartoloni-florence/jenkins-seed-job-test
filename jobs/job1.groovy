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