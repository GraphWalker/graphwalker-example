# A simple example using GraphWalker with data from the code

## to run the test


```
mvn graphwalker:test
```

The test will use up all names in `userNames`, and when the list is exhausted, it
will set `isDone`, which is data in the model, to false. 

Since GraphWalker evaluates the conditional expressions, or guards, in the out-edges from `v_B`,
 it will force GraphWalker to choose the `e_Done` edge, to the vertex  `v_Done`.