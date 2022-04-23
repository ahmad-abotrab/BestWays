package com.company.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class State implements Comparable, Cloneable {
    int n;
    int m;
    ObjectInState[][] board;
    Position truckPosition;
    State parent;
    int step;
    int total;
    ArrayList<ObjectInState> receivingPlace;
    ArrayList<ObjectInState> deliveryPlace;
    int temp = 0;

    public State(State state) {
        this.n = state.n;
        this.m = state.m;
        this.total = state.total;
        this.step = state.step;
        this.parent = state.parent;
        this.receivingPlace = state.receivingPlace;
        this.deliveryPlace = state.deliveryPlace;
        this.truckPosition = new Position(state.truckPosition);
        this.board = new ObjectInState[this.n][this.m];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                this.board[i][j] = new ObjectInState(state.board[i][j]);
            }
        }
    }

    public State(int n, int m) {
        this.n = n;
        this.m = m;
        this.step = 0;
        this.total = 0;
        this.parent = null;
        this.deliveryPlace = new ArrayList<>();
        this.receivingPlace = new ArrayList<>();
        this.truckPosition = new Position();
        this.board = new ObjectInState[this.n][this.m];
    }

    public State(int n, int m, ObjectInState[][] board, Position truckPosition, ArrayList<ObjectInState> receivingPlace, ArrayList<ObjectInState> deliveryPlace) {
        this.n = n;
        this.m = m;
        this.board = board;
        this.truckPosition = truckPosition;
        this.receivingPlace = receivingPlace;
        this.deliveryPlace = deliveryPlace;
        this.total = 0;
        this.step = 0;
        this.parent = null;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public ObjectInState[][] getBoard() {
        return board;
    }

    public void setBoard(ObjectInState[][] board) {
        this.board = board;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Position getTruckPosition() {
        return truckPosition;
    }

    public void setTruckPosition(Position truckPosition) {
        this.truckPosition = truckPosition;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }

    public ArrayList<ObjectInState> getReceivingPlace() {
        return receivingPlace;
    }

    public void setReceivingPlace(ArrayList<ObjectInState> receivingPlace) {
        this.receivingPlace = receivingPlace;
    }

    public ArrayList<ObjectInState> getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(ArrayList<ObjectInState> deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getTemp() {
        return this.temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }


    ////-----------------Methods---------------------

    public boolean expellingReceiveNotDeliver(int index) {
        boolean check = false;
        for (Expelling e : this.board[this.truckPosition.x][this.truckPosition.y].getTruck().getExpellingArrayList()
        ) {
            if (e.getCode().equals(String.valueOf(index))) {
                check = true;
                break;
            }
        }
        if (check == false) return false;
        else {
            for (int i = 0; i < this.receivingPlace.size(); i++) {
                if (this.receivingPlace.get(i).getCode().equals(String.valueOf(index))) {
                    Position position = this.receivingPlace.get(i).getPosition();
                    if (!this.board[position.getX()][position.getY()].getExpelling().equals(new Expelling()))
                        return false;
                    else break;
                }
            }
            for (int i = 0; i < this.deliveryPlace.size(); i++) {
                if (this.deliveryPlace.get(i).getCode().equals(String.valueOf(index))) {
                    Position position = this.deliveryPlace.get(i).getPosition();
                    if (!this.board[position.getX()][position.getY()].getExpelling().equals(new Expelling()))
                        return false;
                    else {
                        temp = i;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int manhattanDistance(Position position1, Position position2) {
        return Math.abs(position1.getX() - position2.getX()) + Math.abs(position1.getY() - position2.getY());
    }

    public void calcTotalForA_star(int heuristicValue) {
        this.total = this.step + heuristicValue;
    }

    //Is Right
    public void calcTotalFor_UCD() {

        this.total = this.step;
    }


    ///-----------Override------------
    @Override
    public int compareTo(Object o) {
        State state = (State) o;

        if (state.total > this.total) {
            return -1;
        } else {
            if (state.total < this.total) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (getN() != state.getN()) return false;
        if (getM() != state.getM()) return false;
//        if (getStep() != state.getStep()) return false;
        if (getTotal() != state.getTotal()) return false;
        if (getTemp() != state.getTemp()) return false;
        if (!Arrays.deepEquals(getBoard(), state.getBoard())) return false;
        if (!getTruckPosition().equals(state.getTruckPosition())) return false;
        if (!getReceivingPlace().equals(state.getReceivingPlace())) return false;
        return getDeliveryPlace().equals(state.getDeliveryPlace());
    }

    @Override
    public int hashCode() {
        int result = getN();
        result = 31 * result + getM();
        result = 31 * result + Arrays.deepHashCode(getBoard());
        result = 31 * result + getTruckPosition().hashCode();
//        result = 31 * result + getStep();
//        result = 31 * result + getTotal();
        result = 31 * result + getReceivingPlace().hashCode();
        result = 31 * result + getDeliveryPlace().hashCode();
        result = 31 * result + getTemp();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            sb.append("....");
        }
        sb.delete(sb.length() - 2, sb.length() - 1);
        sb.append("\n");
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (max < this.board[i][j].getType().length())
                    max = this.board[i][j].getType().length();
            }
        }
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {

                if (this.board[i][j].getTruck().getSymbol() == 'T') {
                    sb.append(this.board[i][j].getTruck().getSymbol());

                } else {
                    sb.append(this.board[i][j].getType());

                }
                for (int k = this.board[i][j].getType().length(); k <= max; k++) {
                    sb.append(" ");
                }
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append('\n');
        }

        return sb.toString();
    }
}