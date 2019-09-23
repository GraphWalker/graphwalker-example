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
 
```shell script
git clone https://github.com/PrestaShop/PrestaShop
cd PrestaShop
sudo chown -R www-data:www-data *
docker-compose up
```

If you encounter any problems with the `docker` command, please google your problems. This example will ot provide any help.

When `docker-compse up` is done (it will take some time the first time it's run), PrestaShop needs to be configured.
 * Goto http://localhost:8001

![alt tag](images/prestashop/After_installation.png)

## Run the GraphWalker test

```shell script
git clone https://github.com/GraphWalker/graphwalker-example.git
cd graphwalker-example/java-prestashop
mvn compile exec:java -Dexec.mainClass="com.prestashop.runners.SeleniumRunner"
```