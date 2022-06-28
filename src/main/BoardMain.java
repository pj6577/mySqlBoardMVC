package main;

import java.util.Scanner;

import connector.DBConnector;
import connector.MYSqlConnector;
import viewer.UserViewer;

public class BoardMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DBConnector connector = new MYSqlConnector();
        UserViewer viewer = new UserViewer(scanner, connector);
        
        viewer.showIndex();
        
        
    }
}
