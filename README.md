# Social Influence

Java network/graph framework with emphasis on social influence

## Usage

### Graph creation

```java
Graph g = new MemoryGraph();
Vertex v1 = g.addVertex();
Vertex v2 = g.addVertex();
g.addEdge(v1, v2);
```

### Graph generation

```java
Graph g = RandomG.generate(MemoryGraph.class, 100, 0.05);
System.out.println(g);
```

Inspect available generators in `gr.james.socialinfluence.graph.generators` package.

## TODO

- There needs to be a helper function or some `Graph` member method that can return if a graph is aperiodic or not.
- Consider converting `Move` to immutable.
- Considering converting `Move` to `Map`.
- Create a `UnmodifiableGraph` class that will derive `MemoryGraph`.
- `Game` should accept `Player`s not `Move`s.
- Grid generator and maybe some edge randomizer: http://www.cs.cornell.edu/home/kleinber/swn.d/swn.html
- Transformation "stretch" that extends edges
- Restore the inputs Graph and GameDefinition on Player and create separate class `PlayerExecutor` that executes it. `PlayerExecutor` should extend ThreadPool or sth and will be able to execute 2 players at once