Get and start the PetClinic demo application
```bash
git clone https://github.com/SpringSource/spring-petclinic.git
cd spring-petclinic
git reset --hard e2fbc561309d03d92a0958f3cf59219b1fc0d985
./mvnw spring-boot:run
```

To verify that PetClinic is running, open it: http://localhost:8080. It should display this: 

![alt tag](http://graphwalker.github.io/images/spring-pet-clinic.png)

Get and run the GraphWalker test example
```bash
git clone https://github.com/GraphWalker/graphwalker-example.git
cd graphwalker-example/java-petclinic
./mvnw graphwalker:test
```
