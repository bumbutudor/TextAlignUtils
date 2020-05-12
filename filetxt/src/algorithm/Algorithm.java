package algorithm;

import java.util.HashMap;
import java.util.HashSet;

public class Algorithm {
    public String process(String[] words, boolean outputType) {
        HashMap<String, Node> addedNodes = new HashMap<String, Node>();
        for (String word : words) {
            Node newNode = getNode(word, addedNodes);

            if (!newNode.isProcessed()) {
                newNode.setProcessed();
                HashMap<String, Integer> neighbours = getNeighbours(word, words);
                for (String neighbour : neighbours.keySet()) {
                    Node neighbourNode = getNode(neighbour, addedNodes);
                    newNode.addVertex(new Vertex(neighbourNode, neighbours.get(neighbour)));
                }
            }
        }
        String result = "";

        if (outputType) {

            for (String key : addedNodes.keySet()) {
                result += key + ": \n";
                for (Vertex vertex : addedNodes.get(key).vertices) {
                    result += "    " + vertex.node.value + " " + vertex.count + "\n";
                }
                result += " \n";
            }
        } else {
            StringBuilder builder = new StringBuilder();
            HashSet<String> visitedNodes = new HashSet<String>();
            Node firstNode = getNode(words[0], addedNodes);
            visitedNodes.add(firstNode.value);
            builder.append(firstNode.value + ": \n");
            for (Vertex vertex : firstNode.vertices) {
                visit(vertex.node, visitedNodes, 1, vertex.count, builder);
            }
            result = builder.toString();
        }

        return result;
    }

    private HashMap<String, Integer> getNeighbours(String word, String[] words) {
        HashMap<String, Integer> neighbours = new HashMap<String, Integer>();
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].equals(word)) {
                String neighbour = words[i + 1];
                if (neighbours.containsKey(neighbour)) {
                    neighbours.put(neighbour, neighbours.get(neighbour) + 1);
                } else {
                    neighbours.put(neighbour, 1);
                }
            }
        }

        return neighbours;
    }

    private Node getNode(String word, HashMap<String, Node> addedNodes) {
        if (addedNodes.containsKey(word)) {
            return addedNodes.get(word);
        }

        addedNodes.put(word, new Node(word));

        return addedNodes.get(word);
    }

    private static void visit(Node node, HashSet<String> visitedNodes, int indent, int count, StringBuilder builder) {
        String spaces = String.format("%" + indent + "s", "");
        if (visitedNodes.contains(node.value)) {
            builder.append(spaces + "Has visited " + node.value + " " + count + " Times. Already been here :| \n");
        } else {
            visitedNodes.add(node.value);
            builder.append(spaces + "Has visited " + node.value + " " + count + " \n");
            for (Vertex vertex : node.vertices) {
                visit(vertex.node, visitedNodes, indent + 1, vertex.count, builder);
            }
        }
    }
}
