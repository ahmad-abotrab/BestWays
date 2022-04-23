package com.company.Model;

import java.util.ArrayList;

public class Truck implements Cloneable {
    char symbol;
    ArrayList<Expelling> expellingArrayList;
    Position initialPosition;
    Position currentPosition;
    int totalCostInTruck;

    public Truck(char symbol, ArrayList<Expelling> expellingArrayList, Position initialPosition, Position currentPosition, int totalCostInTruck) {
        this.symbol = symbol;
        this.expellingArrayList = expellingArrayList;
        this.initialPosition = initialPosition;
        this.currentPosition = currentPosition;
        this.totalCostInTruck = totalCostInTruck;
    }

    public Truck() {
        this.expellingArrayList = new ArrayList<>();
        this.initialPosition = new Position();
        this.currentPosition = new Position();
        this.totalCostInTruck = 0;
    }

    public Truck(Truck truck) {
        this.expellingArrayList = (ArrayList<Expelling>) truck.expellingArrayList.clone();
        this.currentPosition = new Position(truck.currentPosition);
        this.initialPosition = new Position(truck.initialPosition);
        this.symbol = truck.symbol;
        this.totalCostInTruck = truck.totalCostInTruck;
    }

    public ArrayList<Expelling> getExpellingArrayList() {
        return expellingArrayList;
    }

    public void setExpellingArrayList(ArrayList<Expelling> expellingArrayList) {
        this.expellingArrayList = expellingArrayList;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Position initialPosition) {
        this.initialPosition = initialPosition;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public int getTotalCostInTruck() {
        return totalCostInTruck;
    }

    public void setTotalCostInTruck(int totalCostInTruck) {
        this.totalCostInTruck = totalCostInTruck;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "symbol=" + symbol +
                ", expellingArrayList=" + expellingArrayList +
                ", initialPosition=" + initialPosition +
                ", currentPosition=" + currentPosition +
                ", totalCostInTruck=" + totalCostInTruck +
                '}' + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Truck)) return false;

        Truck truck = (Truck) o;

        if (getSymbol() != truck.getSymbol()) return false;
        if (getTotalCostInTruck() != truck.getTotalCostInTruck()) return false;
        if (!getExpellingArrayList().equals(truck.getExpellingArrayList())) return false;
        if (!getInitialPosition().equals(truck.getInitialPosition())) return false;
        return getCurrentPosition().equals(truck.getCurrentPosition());
    }

    @Override
    public int hashCode() {
        int result = getSymbol();
        result = 31 * result + getExpellingArrayList().hashCode();
        result = 31 * result + getInitialPosition().hashCode();
        result = 31 * result + getCurrentPosition().hashCode();
        result = 31 * result + getTotalCostInTruck();
        return result;
    }
}
