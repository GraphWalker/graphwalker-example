This example will run simulated test using GraphWalker started as a WebSocket REST service.

 * Download [latest GraphWalker standalone jar](http://graphwalker.org/archive/graphwalker-cli-3.4.0.jar).
 * `git clone https://github.com/GraphWalker/graphwalker-example.git`
 * `cd c-sharp-rest/SmallModel`
 * Download [latest GraphWalker standalone jar](http://graphwalker.org/archive/graphwalker-cli-3.4.0.jar).
 * Start GraphWalker as a restful service: `java -jar graphwalker-cli-3.4.0.jar -d all online -s RESTFUL -m SmallModel.graphml "random(edge_coverage(100))"`.
 * Build the SmallModel project.<br>
   On linux: `xbuild `
 * Run the program:
   On linux: `mono SmallModel/bin/Debug/SmallModel.exe`

When run, the SmallModel program will connect to the GraphWalker service.

It will then query the state machine in the GraphWalker service for steps to execute. The psuedo code would look something like the:
```
    while hasNext()
        step = getNext()
        "Call method step in C# SmallModel class"
        invoke SmallModel.'step'
        print getData()
```


