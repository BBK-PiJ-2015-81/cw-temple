package student;

import game.EscapeState;
import game.ExplorationState;
import game.NodeStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Explorer {

    /**
     * Explore the cavern, trying to find the orb in as few steps as possible.
     * Once you find the orb, you must return from the function in order to pick
     * it up. If you continue to move after finding the orb rather
     * than returning, it will not count.
     * If you return from this function while not standing on top of the orb,
     * it will count as a failure.
     * <p>
     * There is no limit to how many steps you can take, but you will receive
     * a score bonus multiplier for finding the orb in fewer steps.
     * <p>
     * At every step, you only know your current tile's ID and the ID of all
     * open neighbor tiles, as well as the distance to the orb at each of these tiles
     * (ignoring walls and obstacles).
     * <p>
     * To get information about the current state, use functions
     * getCurrentLocation(),
     * getNeighbours(), and
     * getDistanceToTarget()
     * in ExplorationState.
     * You know you are standing on the orb when getDistanceToTarget() is 0.
     * <p>
     * Use function moveTo(long id) in ExplorationState to move to a neighboring
     * tile by its ID. Doing this will change state to reflect your new position.
     * <p>
     * A suggested first implementation that will always find the orb, but likely won't
     * receive a large bonus multiplier, is a depth-first search.
     *
     * @param state the information available at the current state
     */
    public void explore(ExplorationState state) {
        //TODO : Explore the cavern and find the orb


        // Create a list to store tiles checked for the Orb
        List<game.NodeStatus> checkedTiles = new ArrayList<NodeStatus>();

        // Call explore method
        depthFirstSearch(state, state.getCurrentLocation(), checkedTiles);


        // Remember to return after my search method;
        return;
    }

    /**
     * Escape from the cavern before the ceiling collapses, trying to collect as much
     * gold as possible along the way. Your solution must ALWAYS escape before time runs
     * out, and this should be prioritized above collecting gold.
     * <p>
     * You now have access to the entire underlying graph, which can be accessed through EscapeState.
     * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
     * will return a collection of all nodes on the graph.
     * <p>
     * Note that time is measured entirely in the number of steps taken, and for each step
     * the time remaining is decremented by the weight of the edge taken. You can use
     * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
     * on your current tile (this will fail if no such gold exists), and moveTo() to move
     * to a destination node adjacent to your current node.
     * <p>
     * You must return from this function while standing at the exit. Failing to do so before time
     * runs out or returning from the wrong location will be considered a failed run.
     * <p>
     * You will always have enough time to escape using the shortest path from the starting
     * position to the exit, although this will not collect much gold.
     *
     * @param state the information available at the current state
     */
    public void escape(EscapeState state) {
        //TODO: Escape from the cavern before time runs out

        alwaysEscapes(state);

        // Remember to return after my escape method;
        return;


    }


// Search methods

    public void depthFirstSearch(ExplorationState myState, long prevTile, List<NodeStatus> checked) {

        long currentTile = myState.getCurrentLocation();

        // If we find the Orb immediately
        if (myState.getDistanceToTarget() == 0 ) {
            return;
        }


        // Sort my neighbours so the 'closest' neighbour to orb is checked first
        List<game.NodeStatus> myNeighbours;
        myNeighbours = (List<NodeStatus>) myState.getNeighbours();
        Collections.sort(myNeighbours);

        // Check each neighbour and it's neighbours
        for (NodeStatus neighbourTile : myNeighbours) {

            // If this neighbour tile isn't on my 'checked' list, add to my list and take a step
            if (!checked.contains(neighbourTile)) {
                checked.add(neighbourTile);
                myState.moveTo(neighbourTile.getId());
                // Look at new neighbours, take a step
                depthFirstSearch(myState, currentTile, checked);

            }

           if(myState.getDistanceToTarget() == 0) {
               break;
           }

        }

        // Need to do something if all the neighbours have been checked and we're not at the Orb
        if (checked.containsAll(myState.getNeighbours())){
            myState.moveTo(prevTile);
        }

    }

// Escape Methods

    public void alwaysEscapes(EscapeState myState) {

        // Return when we reach the exit
        if (myState.getCurrentNode() == myState.getExit()) {
            return;
        }

        // Find shortest path to the exit

        // Get all the Nodes

        myState.getVertices();


        // Find the shortest path to each node


    }


}

