Download Maven 2.0.6 from http://maven.apache.org/
Extract to a directory of your choice
Add a enviroment variable of M2_HOME = directory maven is installed
Also add %M2_Home%\bin to your path variable

Downloadand install Java SDK 1.5.0_XX from www.sun.com
Add a enviroment variable of JAVA_HOME = directory Java is installed
Also add %JAVA_Home%\bin to your path variable

Then run "mvn clean install" from the //source/main folder.

Download and install Mysql 5.0 from www.mysql.com
setup the root password to be 1234, or change the password in the 
.\source\main\beans\src\main\resources\hibernate.cfg.xml
Run all but the last script in the .\source\main\beans\src\main\resources\SQL file

To create a netbeans project run "mvn netbeans-freeform:generate-netbeans-project" from //source/main>

To create a IntelJ project run "mvn idea:idea" from //source/main

To create documentation run "mvn javadoc:javadoc"

*** install third party jars in to maven repo

mvn install:install-file -DgroupId=javax.transaction -DartifactId=jta -Dversion=1.0.1B -Dpackaging=jar -Dfile=/path/to/file

mvn install:install-file -DgroupId=org.jdesktop -DartifactId=swing-layout -Dversion=1.0 -Dpackaging=jar -Dfile=/path/to/file

mvn install:install-file -DgroupId=flexdock -DartifactId=flexdoc -Dversion=0.5.1 -Dpackaging=jar -Dfile=/path/to/file

mvn install:install-file -DgroupId=jtattoo -DartifactId=jtattoo -Dversion=1.1.12 -Dpackaging=jar -Dfile=/path/to/file