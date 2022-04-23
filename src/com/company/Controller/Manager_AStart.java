package com.company.Controller;

import com.company.Model.ObjectInState;
import com.company.Model.Position;
import com.company.Model.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class Manager_AStart extends Manager {

    @Override
    public void procedureToChangePositionOfTruckIfCan(State state, Position position, PriorityQueue<State> statePriorityQueue) {
        if (movementIsAcceptable(state, position)) {
            State state1 = null;
            state1 = new State(state);
            task(state1, position);
            state1.setStep(state1.getStep() + 1 + state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getTruck().getTotalCostInTruck());
//            state1.calcTotalForA_star(heuristic1(state1));
//            state1.calcTotalForA_star(heuristic2(state1,0));
            state1.calcTotalForA_star(heuristic3(state1));
            state1.setParent(state);
            statePriorityQueue.add(state1);
        }
    }

    public int heuristic1(State state) {
        return state.manhattanDistance(state.getTruckPosition(), state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getTruck().getInitialPosition());
    }

    public int heuristic2(State state, int index) {
        if (state.expellingReceiveNotDeliver(index)) {
            ObjectInState objectInState = state.getDeliveryPlace().get(state.getTemp());
            return state.manhattanDistance(state.getTruckPosition(), objectInState.getPosition());
        } else {
            return heuristic1(state);
        }
    }

    public int heuristic3(State state) {

        //here should make a* between truck and position of expelling have high weight
        int lengthTruck = state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getTruck().getExpellingArrayList().size();
        if (lengthTruck <= 0) {
            return heuristic1(state);
        }
        ArrayList<Integer> integers = new ArrayList<>();
        for (int j = 0; j < lengthTruck; j++) {
            integers.add(heuristic2(state, j));
        }
        Collections.sort(integers);
        return integers.get(integers.size() - 1);
    }
}


