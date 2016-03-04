/**
 * Created by andre on 26/02/2016.
 */

package student;

import game.EscapeState;
import game.Node;
import game.Edge;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collections;

public class ShortestPathDijkstra {

    public void takePath (EscapeState state, List<Node> pathToExit) {


        //Traversing shortest path from the start to the end, picking up gold on or near the path
        for(Node nextNode: pathToExit) {

            //System.out.println(state.getTimeRemaining());

            // Head straight to exit when 150 steps left
            if (pathToExit.size() +150 < state.getTimeRemaining()) {

                Node recentOnPath = state.getCurrentNode();
                List<Node> adjacentTilesA = new ArrayList<Node>(recentOnPath.getNeighbours());


                // Grab gold on adjacent tiles and return to path
                for (Node a : adjacentTilesA) {

                    // Only grab gold from near tiles if we have time
                    // Retrace steps after picking up gold
                    // Get back on shortest path

                    if (a.getTile().getGold() != 0 && !pathToExit.contains(a) && pathToExit.size() +200 < state.getTimeRemaining()) {
                        state.moveTo(a);
                        state.pickUpGold();

                        List<Node> adjacentTilesB = new ArrayList<Node>(a.getNeighbours());
                        for (Node b : adjacentTilesB) {
                            if (b.getTile().getGold() != 0 && !pathToExit.contains(b) && pathToExit.size() + 250 < state.getTimeRemaining()) {
                                state.moveTo(b);
                                state.pickUpGold();

                                List<Node> adjacentTilesC = new ArrayList<Node>(b.getNeighbours());
                                for (Node c : adjacentTilesC) {
                                    if (c.getTile().getGold() != 0 && !pathToExit.contains(c) && pathToExit.size() + 300 < state.getTimeRemaining()) {
                                        state.moveTo(c);
                                        state.pickUpGold();

                                        List<Node> adjacentTilesD = new ArrayList<Node>(c.getNeighbours());
                                        for (Node d : adjacentTilesD) {
                                            if (d.getTile().getGold() != 0 && !pathToExit.contains(d) && pathToExit.size() + 350 < state.getTimeRemaining()) {
                                                state.moveTo(d);
                                                state.pickUpGold();
                                                state.moveTo(c);
                                            }
                                        }
                                        state.moveTo(b);
                                    }
                                }
                                state.moveTo(a);
                            }
                        }
                        state.moveTo(recentOnPath);
                    }
                }
            }

            // Move down path and grab gold on path
            state.moveTo(nextNode);
            if (nextNode.getTile().getGold() != 0)
                state.pickUpGold();
        }

    }

    public static <T> List<Node> dijkstra(Node start, Node end) {

        PriorityQueueImpl<Node> visited = new PriorityQueueImpl<Node>();
        HashMap<Node, NodeInfo> NodeInfo = new HashMap<Node, NodeInfo>();

        visited.add(start, 0);
        NodeInfo.put(start, new NodeInfo());

        while (!visited.isEmpty() && visited.peek() != end) {

            Node u = visited.poll();
            NodeInfo uInfo = NodeInfo.get(u);

            Set<Edge> edges = u.getExits();
            for (Edge edge : edges) {
                Node v = edge.getOther(u);
                NodeInfo vInfo = NodeInfo.get(v);
                int vDistance = uInfo.distance + edge.length;
                if (vInfo == null) {
                    visited.add(v, vDistance);
                    NodeInfo.put(v, new NodeInfo(u, vDistance));
                } else if (vDistance < vInfo.distance) {
                    visited.updatePriority(v, vDistance);
                    vInfo.distance = vDistance;
                    vInfo.predecessor = u;
                }
            }

        }
        if (visited.isEmpty()) {
            return new ArrayList<Node>();
        }

        return buildPath(visited.peek(), NodeInfo);
    }


    private static <T> List<Node> buildPath(Node end, HashMap<Node, NodeInfo> NodeInfo) {
        List<Node> path = new ArrayList<Node>();
        Node p = end;
        while (p != null) {
            path.add(p);
            p = NodeInfo.get(p).predecessor;
        }

        // Reverse path order so we can traverse it from the start
        Collections.reverse(path);
        return path;
    }

    private static class NodeInfo {
        private Node predecessor;
        private int distance;


        private NodeInfo(Node p, int d) {
            predecessor = p;
            distance = d;
        }


        private NodeInfo() {
        }
    }
}