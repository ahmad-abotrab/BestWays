package com.company.Controller;

import com.company.Model.*;
import com.company.Views.InputData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

abstract public class Manager {

    public void task(State state1, Position position) {
        state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getTruck().setCurrentPosition(new Position(position));
        Truck truck = new Truck(state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getTruck());

        state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].setTruck(new Truck());
        state1.getBoard()[position.getX()][position.getY()].setTruck(new Truck(truck));

        state1.setTruckPosition(new Position(position));
    }

    public boolean checkAllExpellingIsDelivery(State state) {
        for (ObjectInState[] objectInStates : state.getBoard()) {
            for (ObjectInState justState : objectInStates) {
                if (justState.getType().charAt(0) == 'D' && justState.getExpelling().equals(new Expelling())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkIfComeBackTruckToHome(State state) {
        Position initialPositionForTruck = state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getTruck().getInitialPosition();
        Position currentPositionForTruck = state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getTruck().getCurrentPosition();
        if (!initialPositionForTruck.equals(currentPositionForTruck)) return false;
        return true;
    }

    public boolean movementIsAcceptable(State state, Position position) {
        if (state.getN() <= position.getX() || state.getM() <= position.getY() || position.getY() < 0 || position.getX() < 0) {
            return false;
        }
        if (state.getBoard()[position.getX()][position.getY()].getType().equals("#")) {
            return false;
        }
        return true;
    }

    public void procedureToChangePositionOfTruckIfCan(State state, Position position, PriorityQueue<State> statePriorityQueue) {
    }

    public int findCodeToDelivery(Truck truck, String code) {
        for (int i = 0; i < truck.getExpellingArrayList().size(); i++) {
            if (truck.getExpellingArrayList().get(i).getCode().equals(code)) return i;
        }
        return -1;
    }

    public PriorityQueue<State> getAllNext(State state) {

        PriorityQueue<State> statePriorityQueue = new PriorityQueue<>();
        Position position = null;

        ///_____________ CASE up ___________________
        position = new Position(state.getTruckPosition());
        position.setX(position.getX() - 1);
        procedureToChangePositionOfTruckIfCan(new State(state), position, statePriorityQueue);
        position.setX(position.getX() + 1);
        ////_______________________________________________

        ///_____________ CASE down ___________________
        position = new Position(state.getTruckPosition());
        position.setX(position.getX() + 1);
        procedureToChangePositionOfTruckIfCan(new State(state), position, statePriorityQueue);
        position.setX(position.getX() - 1);
        ////_______________________________________________

        ///_____________ CASE right ___________________

        position = new Position(state.getTruckPosition());
        position.setY(position.getY() + 1);
        procedureToChangePositionOfTruckIfCan(new State(state), position, statePriorityQueue);
        position.setY(position.getY() - 1);
        ////_______________________________________________

        ///_____________ CASE left ___________________
        position = new Position(state.getTruckPosition());
        position.setY(position.getY() - 1);
        procedureToChangePositionOfTruckIfCan(new State(state), position, statePriorityQueue);
        position.setY(position.getY() + 1);

        ////_______________________________________________

        ///_____________ CASE get ___________________
        if (state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getType().charAt(0) == 'P') {

            ///_____________ CASE 1 ___________________
            Expelling expelling = null;
            if (!state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getExpelling().equals(new Expelling())) {
                expelling = new Expelling(state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getExpelling());

                State state1 = null;

                state1 = new State(state);

                state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].setExpelling(new Expelling());

                state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getTruck().getExpellingArrayList().add(new Expelling(expelling));

                int totalCost = state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getTruck().getTotalCostInTruck();
                state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getTruck().setTotalCostInTruck(totalCost + expelling.getCost());
                state1.setParent(state);
                statePriorityQueue.add(state1);
            }
        }

        ////_______________________________________________

        //_____________ CASE put ___________________
        if (state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getType().charAt(0) == 'D') {

            ///_____________ CASE 1 ___________________
            String code = state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getCode();
            Truck truck = null;

            truck = new Truck(state.getBoard()[state.getTruckPosition().getX()][state.getTruckPosition().getY()].getTruck());

            int index = findCodeToDelivery(truck, code);
            if (index != -1) {
                Expelling expelling = new Expelling(truck.getExpellingArrayList().get(index));
                truck.getExpellingArrayList().remove(index);
                truck.setTotalCostInTruck(Math.abs(truck.getTotalCostInTruck() - expelling.getCost()));
                State state1 = new State(state);

                state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].setExpelling((new Expelling(expelling)));
                state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].setTruck((new Truck(truck)));
                state1.setParent(state);
                statePriorityQueue.add(state1);
            }
        }
        return statePriorityQueue;
    }

    public void printThePath(Stack<State> stateStack) {
        int n = stateStack.get(0).getN();
        int m = stateStack.get(0).getM();
        ObjectInState[][] newBoard = new ObjectInState[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newBoard[i][j] = new ObjectInState();
                newBoard[i][j].setType(".");
            }
        }
        ArrayList<Position> build = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (stateStack.get(0).getBoard()[i][j].getType().equals("#")) {
                    build.add(stateStack.get(0).getBoard()[i][j].getPosition());
                }
            }
        }
        for (Position p : build
        ) {
            newBoard[p.getX()][p.getY()].setType("#");
        }
        int k = 0;
        while (!stateStack.isEmpty()) {
            State state1 = stateStack.pop();
            if (newBoard[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getType().equals(".") || newBoard[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getType().equals("#"))
                newBoard[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].setType(String.valueOf(k));
            else {
                newBoard[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].setType(newBoard[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getType()+"|" +String.valueOf(k));
            }
            k++;
//            System.out.println(state1.getTruckPosition() + " " + "total Weight in Truck is : " + state1.getBoard()[state1.getTruckPosition().getX()][state1.getTruckPosition().getY()].getTruck().getTotalCostInTruck() + "  " + state1.getStep() + "  " + state1.getTotal());
        }
        State state = new State(n,m);
        state.setBoard(newBoard);
        System.out.println(state);
    }

    public void gameOn() {
        HashSet<State> stateHashSet = new HashSet<>();
        PriorityQueue<State> statePriorityQueue = new PriorityQueue<>();
        State state = InputData.from_KeyBoard();
        statePriorityQueue = getAllNext(state);
        Stack<State> stateStack = new Stack<>();
        while (!statePriorityQueue.isEmpty()) {
            State state1 = statePriorityQueue.poll();
            if (checkAllExpellingIsDelivery(state1) && checkIfComeBackTruckToHome(state1)) {
                System.out.println("total cost is : " + state1.getTotal());
                while (state1.getParent() != null) {
                    stateStack.push(state1);
                    state1 = state1.getParent();
                }
                stateStack.push(state1);
                break;
            }
            if (stateHashSet.contains(state1)) continue;
            stateHashSet.add(state1);
            statePriorityQueue.addAll(getAllNext(state1));
        }
        System.out.println("Number of All nodes is  : " + stateHashSet.size());
        printThePath(stateStack);
    }
}
