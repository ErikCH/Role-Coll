RoleColl was a senior project at the University of Nevada Reno in 2007. Here it is for anyone to enjoy. I will add an opensource license to it shortly. 

How to build and use RoleColl
Prerequisites
Download Maven 2.0.6 from http://maven.apache.org/ 
Extract to a directory of your choice
Add an environment variable of M2_HOME = directory maven is installed
Also add %M2_HOME%\bin to your path variable

Download and install Java SDK 1.5.0 from www.java.sun.com 
Add an environment variable of JAVA_HOME = directory Java is installed
Also add %JAVA_HOME%\bin to your path variable

Download and install Mysql 5.0 from www.mysql.com 
Set the root password to be 1234, or change the password in the 
.\source\main\beans\src\main\resources\hibernate.cfg.xml
Run all but the last script in the .\source\main\beans\src\main\resources\SQL file
(The last script allows users from other IPs to access the database)

Build Source
There are four dependencies that maven will not be able to download, because they are not hosted on any public maven repositories. They can be manually installed with the following commands. Note that path/to/file needs to be replaced with the appropriate path to each included jar respectively. 

mvn install:install-file -DgroupId=javax.transaction -DartifactId=jta -Dversion=1.0.1B -Dpackaging=jar -Dfile=/path/to/jta-1.0.1B.jar

mvn install:install-file -DgroupId=org.jdesktop -DartifactId=swing-layout -Dversion=1.0 -Dpackaging=jar -Dfile=/path/to/swing-layout-1.0.jar

mvn install:install-file -DgroupId=flexdock -DartifactId=flexdoc -Dversion=0.5.1 -Dpackaging=jar -Dfile=/path/to/flexdock-0.5.1.jar

mvn install:install-file -DgroupId=jtattoo -DartifactId=jtattoo -Dversion=1.1.12 -Dpackaging=jar -Dfile=/path/to/JTattoo.jar

Once all of the dependencies are installed to your local repository we can begin the complicate build process.

Open a command window change the directory to ./source/main then run “mvn install”

That is it!! Maven will download any other necessary dependencies. 

Run the Application
Once the build has completed you should get a message that says that the build was successful. Open a command window and change the directory to ./source/main/ui/target run “java -jar ui-1.0.0.jar”. The application should start up. If you have the database setup you will need to setup a user to access the main frame. The default admin user is admin with a password of intuit456. See our video of adding a user for more information about adding users www.cse.unr.edu/~rolecoll there are also videos on creating documents and adding notes. Also check out http://www.erikhanchett.com for more information. 
