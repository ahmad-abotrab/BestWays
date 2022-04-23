package com.company.Model;

public class Expelling implements Cloneable {
    int cost;
    String code;

    public Expelling() {
        this.cost = -1;
        this.code = "";
    }

    public Expelling(Expelling expelling) {
        this.cost = expelling.cost;
        this.code = expelling.code;
    }
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Expelling{" +
                "cost=" + cost + "\t" +
                ", code='" + code + '\'' +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expelling)) return false;

        Expelling expelling = (Expelling) o;

        if (getCost() != expelling.getCost()) return false;
        return getCode().equals(expelling.getCode());
    }

    @Override
    public int hashCode() {
        int result = getCost();
        result = 31 * result + getCode().hashCode();
        return result;
    }
}
