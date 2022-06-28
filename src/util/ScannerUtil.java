package util;

import java.util.*;

public class  ScannerUtil{
//2���� �迭��

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 1. nextLine();
        String name = ScannerUtil.nextLine(scanner, "�̸��� �Է����ּ���");
        System.out.println(name);
    }

    // 1. ��ĳ�� ���׸� �̸� �ذ��� nextLine()
    public static String nextLine(Scanner scanner, String message) {
        System.out.println(message);
        System.out.print(">");
        String temp = scanner.nextLine();
        // String ����/��� Ȥ�� ���� ���
        // inEmpty() �޼ҵ�� �����Ű��
        // �ƹ��� ���� ������ true, �ƹ� ���ڵ� �ϳ��� ������ flase �� ���ϵȴ�
        if (temp.isEmpty()) {
            temp = scanner.nextLine();
        }
        return temp;
    }
      
    public static void printMessage(String message) {
     System.out.println("-----------------");   
     System.out.println(message);   
     System.out.println("-----------------");   
    }
    public static int nextInt(Scanner scanner, String message) {
        printMessage(message);
        int temp = scanner.nextInt();
        return temp; 
        }
    
    public static int nextInt(Scanner scanner, String message, int min, int max) {
        int temp = nextInt(scanner, message);
        while(temp <min || temp>max ) {
            System.out.println("�߸��Է��ϼ�");
            temp = nextInt(scanner, message);
        }
        return temp;
    }

}

