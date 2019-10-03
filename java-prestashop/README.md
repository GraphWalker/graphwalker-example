# This project was made for a presentation at  [SAST](http://sast.se/meeting.jsp?id=381)

The example uses [PrestaSop](https://www.prestashop.com/en) as the System Under Test [SUT].
To get the the SUT installed locally the presentation uses the docker version of PrestaShop. Go to [PrestaShops GitHub
page](https://github.com/PrestaShop/PrestaShop) and follow the instructions there.  

Before running the example, make sure following requirements are met/installed on your machine:
* Java JDK >= 8
* Maven >= 3.5
* Docker >= 18 
* Firefox latest version

## Get the PrestaShop demo running

**NOTE: Due to a bug(?), PrestaShop on docker is not working as expected. Keep an eye on: https://github.com/PrestaShop/PrestaShop/issues/15747**
 
```shell script
git clone https://github.com/PrestaShop/PrestaShop
cd PrestaShop
sudo chown -hR www-data:www-data *
docker-compose up
```

If you encounter any problems with the `docker` command, please google your problems. This example can not provide any
help in that regards.

The `docker-compose up` command will launch 2 services, one database and one web. It will take a while until all is up running. When done, goto http://localhost:8001 to see PrestaShop.

![alt tag](images/prestashop/After_installation.png)


## GraphWalker running tests using different test tools

Extending the same interface `PrestaShop`, different implementations can be used to run th same tests. The interface is created from models in the folder<br>
 `src/main/resources/com/prestashop`<br>
and will end up in this folder<br>
 `target/generated-sources/graphwalker/com/prestashop`.

Interfaces are created by graphwalker by running:<br>
```shell script
mvn graphwalker:generate-sources
```
They are also created during the `compile` lifecycle of maven. 


### Run the GraphWalker test with [Selenium](https://www.seleniumhq.org/)

The model implementation using [selenium](https://github.com/GraphWalker/graphwalker-example/blob/master/java-prestashop/src/main/java/com/prestashop/modelimplementation/SeleniumImpl.java).

```shell script
git clone https://github.com/GraphWalker/graphwalker-example.git
cd graphwalker-example/java-prestashop
mvn -Pselenium compile exec:java -Dexec.cleanupDaemonThreads=false -Dexec.mainClass="com.prestashop.runners.SeleniumRunner"
```

### Run the GraphWalker test with [Eye](https://eyeautomate.com/eye/)

The model implementation using [eye](https://github.com/GraphWalker/graphwalker-example/blob/master/java-prestashop/src/main/java/com/prestashop/modelimplementation/EyeImpl.java).

You need to install eye2.jar in order for the below to work

```shell script
mvn install:install-file -Dfile=<PATH TO JAR>/eye2.jar -DgroupId=eye -DartifactId=Eye -Dversion=2 -Dpackaging=jar

mvn -Peye compile exec:java -Dexec.cleanupDaemonThreads=false -Dexec.mainClass="com.prestashop.runners.EyeRunner"
```

### Run the GraphWalker test with [SikuliX](http://sikulix.com/)

The model implementation using [sikuli](https://github.com/GraphWalker/graphwalker-example/blob/master/java-prestashop/src/main/java/com/prestashop/modelimplementation/SikuliImpl.java).

```shell script
mvn -Psikuli compile exec:java -Dexec.cleanupDaemonThreads=false -Dexec.mainClass="com.prestashop.runners.SikuliRunner"
```
