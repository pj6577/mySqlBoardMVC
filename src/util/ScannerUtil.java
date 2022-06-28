package util;

import java.util.*;

public class  ScannerUtil{
//2차원 배열로

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 1. nextLine();
        String name = ScannerUtil.nextLine(scanner, "이름을 입력해주세요");
        System.out.println(name);
    }

    // 1. 스캐너 버그를 미리 해결한 nextLine()
    public static String nextLine(Scanner scanner, String message) {
        System.out.println(message);
        System.out.print(">");
        String temp = scanner.nextLine();
        // String 변수/상수 혹은 값의 경우
        // inEmpty() 메소드로 실행시키면
        // 아무런 값도 없으면 true, 아무 글자도 하나라도 없으면 flase 가 리턴된다
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
            System.out.println("잘못입력하셧");
            temp = nextInt(scanner, message);
        }
        return temp;
    }

}

