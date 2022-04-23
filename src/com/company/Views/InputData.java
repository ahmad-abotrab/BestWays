package com.company.Views;

import com.company.Model.*;

import java.util.ArrayList;
import java.util.Scanner;


public class InputData {

    static public State from_KeyBoard() {

        System.out.println("enter dimension of city \n(----n---- then ----m----)");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ObjectInState[][] board = new ObjectInState[n][m];
        Position positionTruck = new Position();
        ArrayList<ObjectInState> receivingPlace = new ArrayList<>();
        ArrayList<ObjectInState> deliveryPlace = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Expelling expelling = new Expelling();
                System.out.println("enter what you need set in cell (" + i + "," + j + ")");
                String input = scanner.next();
                input = input.toUpperCase();
                char firstChar = input.charAt(0);
                String lastChar = String.valueOf(input.charAt(input.length() - 1));
                board[i][j] = new ObjectInState();
                switch (firstChar) {
                    case 'T' -> {
                        positionTruck.setX(i);
                        positionTruck.setY(j);
                        board[i][j].setTruck(new Truck('T', new ArrayList<Expelling>(), positionTruck, positionTruck, 0));
                        board[i][j].setType(".");
                        board[i][j].setPosition(new Position(i,j));
                    }
                    case 'P' -> {
                        board[i][j].setType(input);
                        System.out.println("Please enter cost of Expelling");
                        int cost = scanner.nextInt();
                        expelling.setCost(cost);
                        expelling.setCode(lastChar);
                        board[i][j].setExpelling(new Expelling(expelling));
                        board[i][j].setPosition(new Position(i,j));
                        receivingPlace.add(board[i][j]);

                    }
                    case 'D' -> {
                        board[i][j].setType(input);
                        board[i][j].setCode(lastChar);
                        board[i][j].setPosition(new Position(i,j));
                        deliveryPlace.add(board[i][j]);
                    }
                    default -> {
                        board[i][j].setType(input);
                        board[i][j].setPosition(new Position(i,j));
                    }
                }
            }
        }
        System.out.println("----------------------------------------------------------");
        return new State(n, m, board, positionTruck,receivingPlace,deliveryPlace);
    }
}