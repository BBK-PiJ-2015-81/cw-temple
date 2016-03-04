/**
 * Created by andre on 04/03/2016.
 */

package student;

import game.ExplorationState;
import game.NodeStatus;
import java.util.Collections;
import java.util.List;

public class FindOrb {

    public void depthFirstSearch(ExplorationState myState, long prevTile, List<NodeStatus> checked) {

        long currentTile = myState.getCurrentLocation();

        // If we find the Orb immediately
        if (myState.getDistanceToTarget() == 0 ) {
            return;
        }


        // Sort my neighbours so the 'closest' neighbour to the orb is checked first
        List<game.NodeStatus> myNeighbours;
        myNeighbours = (List<game.NodeStatus>) myState.getNeighbours();
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
        if (checked.containsAll(myState.getNeighbours()) && myState.getDistanceToTarget() > 0){
            myState.moveTo(prevTile);
        }

    }

}
