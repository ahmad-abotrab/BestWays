package com.company.Controller;

import com.company.Model.Expelling;
import com.company.Model.Position;
import com.company.Model.State;

import java.util.PriorityQueue;


public class ManagerUCS extends Manager {

    @Override
    public void procedureToChangePositionOfTruckIfCan(State state, Position position, PriorityQueue<State> statePriorityQueue) {
        if (movementIsAcceptable(state, position)) {
            State state1 = null;
            state1 = new State(state);
            //Editing current position for truck
            task(state1, position);
            //Calculation cost of movement
            state1.setStep(state1.getStep() + 1 + state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getTruck().getTotalCostInTruck());
            state1.calcTotalFor_UCD();
            //to save the road
            state1.setParent(state);
            statePriorityQueue.add(state1);
        }
    }
}