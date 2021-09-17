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
				buildingTag()
			}
			
			steps {
			    withMaven(
                	globalMavenSettingsConfig: "fb86ca6b-4fd8-4c7b-87a0-871913764d10"
                ) {
    	            sh "mvn deploy"
    	        }
			}
    	}
    }
}
