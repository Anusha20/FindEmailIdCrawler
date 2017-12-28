## Prerequisite 
Please install and configure Maven 3.x 
## To run
use the following command in the home directory of the project
mvn exec:java -Dexec.mainClass="interview.jana.crawler.FindEmailIds" -Dexec.args=<input>
### example
mvn exec:java -Dexec.mainClass="interview.jana.crawler.FindEmailIds" -Dexec.args="www.abc.com"
