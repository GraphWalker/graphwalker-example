Get and start the PetClinic Sample Application
```bash
git clone https://github.com/SpringSource/spring-petclinic.git
cd spring-petclinic
git reset --hard 482eeb1c217789b5d772f5c15c3ab7aa89caf279
mvn tomcat7:run
```

To verify that the Sample Application is running, open it: http://localhost:9966/petclinic/. It should display this: 
![alt tag](http://graphwalker.github.io/images/spring-pet-clinic.png)

Get and run the GraphWalker test example
```bash
git clone https://github.com/GraphWalker/graphwalker-example.git
cd graphwalker-example/java-petclinic
mvn graphwalker:test
```
