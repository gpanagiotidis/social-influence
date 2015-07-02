package gr.james.socialinfluence.graph.io;

import gr.james.socialinfluence.api.Graph;
import gr.james.socialinfluence.api.GraphExporter;
import gr.james.socialinfluence.api.GraphImporter;
import gr.james.socialinfluence.graph.MemoryGraph;
import gr.james.socialinfluence.graph.Vertex;
import gr.james.socialinfluence.graph.algorithms.iterators.IndexIterator;
import gr.james.socialinfluence.helper.Finals;

import java.io.*;
import java.util.Arrays;

public class Csv implements GraphImporter, GraphExporter {
    @Override
    public Graph from(InputStream source) throws IOException {
        Graph g = new MemoryGraph();

        BufferedReader reader = new BufferedReader(new InputStreamReader(source, Finals.DEFAULT_IO_ENCODING));
        String line;
        boolean firstLine = true;
        IndexIterator it = null;
        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                g.addVertices(line.split(";").length - 1);
                it = new IndexIterator(g);
            } else {
                Vertex v = it.next();
                String[] splitted = line.split(";");
                splitted = Arrays.copyOfRange(splitted, 1, splitted.length);
                IndexIterator it2 = new IndexIterator(g);
                for (String t : splitted) {
                    Vertex u = it2.next();
                    double value = Double.parseDouble(t);
                    if (value > 0) {
                        g.addEdge(v, u).setWeight(value);
                    }
                }
            }
            firstLine = false;
        }
        reader.close();

        return g.setMeta("name", "CSVImport")
                .setMeta("source", source.toString());
    }

    @Override
    public void to(Graph g, OutputStream out) throws IOException {
        throw new UnsupportedOperationException();
    }
}