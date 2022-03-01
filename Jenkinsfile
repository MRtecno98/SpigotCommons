pipeline {
    agent any
    
    tools {
        jdk "jdk16"
        maven "3.8.1"
    }
    
    stages {
    	stage("Build") {
    	    steps {
    	        withMaven {
    	            sh "mvn clean compile"
    	        }
    	    }
    	}
    	
    	stage("Test") {
    	    steps {
    	        withMaven {
    	            sh "mvn test"
    	        }
    	    }
    	}
    	
    	stage("Package") {
    	    steps {
    	        withMaven {
    	            sh "mvn package"
    	        }
    	    }
    	}
    	
    	stage("Deploy") {
			when {
				allOf {
					// buildingTag()
					environment name: 'CHANGE_ID', value: ''
					// branch 'master'
				}
			}
			
			steps {
			    withMaven {
    	            sh "mvn -X deploy"
    	        }
			}
    	}
    }
}
