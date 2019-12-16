# Use custom generators

This example shows code where a custom generator is created. To use the new generator, it has been added to an example model `SimpleModel.json`, where the generator is referenced:

`"generator": "pluginGenerator(edge_coverage(100))",`

The custom generator needs to extend the GraphWalker class `PathGeneratorBase<StopCondition>`
See [PluginGenerator.java](https://github.com/GraphWalker/graphwalker-example/blob/master/java-plugin-generator/src/main/java/com/mycompany/lib/PluginGenerator.java).

To use the custom generator with GraphWlalker CLI or Studio, the new class needs to be added to the classpath:
```
mvn package
wget https://github.com/GraphWalker/graphwalker-project/releases/download/LATEST-BUILDS/graphwalker-cli-4.1.0.jar
java -cp ./graphwalker-cli-4.1.0.jar:./target/java-plugin-generator-4.1.0.jar org.graphwalker.cli.CLI offline -g src/main/resources/com/company/SimpleModel.json
```
