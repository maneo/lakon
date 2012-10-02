How to build lakon gui?

1. what you need?	 maven2 (http://maven.apache.org)

2. type in command line: 
       mvn eclipse:eclipse    - creates eclipse project descriptor
	   mvn assembly:directory - in target/gui-dist you will find ready to use distribution pack
	   mvn assembly:assembly  - creates zip archive with zip distribution
	   
	   