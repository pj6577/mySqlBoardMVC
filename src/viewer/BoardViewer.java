package viewer;

import java.util.ArrayList;
import java.util.Scanner;

import connector.DBConnector;
import controller.BoardController;
import controller.UserController;
import model.BoardDTO;
import model.UserDTO;
import util.ScannerUtil;

public class BoardViewer {
    private Scanner scanner;
    private DBConnector connector;
    private UserDTO logIn;

    public BoardViewer(Scanner scanner, DBConnector connector, UserDTO logIn) {
        this.scanner = scanner;
        this.connector = connector;
        this.logIn = logIn;
    }

    public void showMenu() {
        String message = " 1. �� �� ���� 2. �� ��� ���� 3. �ڷ� ����";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                write();
            } else if (userChoice == 2) {
                printList();
            } else if (userChoice == 3) {
                System.out.println("���� ȭ������ ���� ���ϴ�");
                break;
            }
        }
    }

    private void write() {
        BoardDTO b = new BoardDTO();
        b.setWriterId(logIn.getId());

        b.setTitle(ScannerUtil.nextLine(scanner, "������ �Է����ּ���"));
        b.setContent(ScannerUtil.nextLine(scanner, "���븦 �Է����ּ���"));

        BoardController controller = new BoardController(connector);
        controller.insert(b);
    }

    private void printList() {
        BoardController controller = new BoardController(connector);
        ArrayList<BoardDTO> list = controller.selectAll();

        if (list.isEmpty()) {
            System.out.println("���� ���� �����ϴ�");
        } else {
            for (BoardDTO b : list) {
                System.out.printf("%d. %s\n", b.getId(), b.getTitle());
            }
            String messgae = "�󼼺��� �� ���� ��ȣ�� �ڷ� ���Ƿ��� 0�� �Է����ּ���";
            int userChoice = ScannerUtil.nextInt(scanner, messgae);

            while (userChoice != 0 && controller.selectOne(userChoice) == null) {
                System.out.println("�߸��Է��ϼ̽��ϴ�");
                userChoice = ScannerUtil.nextInt(scanner, messgae);
            }
            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    private void printOne(int id) {
        BoardController boardcontroller = new BoardController(connector);
        UserController userController = new UserController(connector);
        BoardDTO b = boardcontroller.selectOne(id);

        System.out.println("���� : " + b.getTitle());
        System.out.println("�� ��ȣ : " + b.getId());
        System.out.println("�� �ۼ��� : " + userController.selectOne(b.getWriterId()).getNickname());
        System.out.println("�� ���� : ");
        System.out.println(b.getContent());

        ReplyViewer replyViewer = new ReplyViewer(scanner, connector, logIn);
        replyViewer.printList(id);

        if (logIn.getId() == b.getWriterId()) {
            int userChoice = ScannerUtil.nextInt(scanner, "1 . �� �����ϱ� 2 . �� �����ϱ� 3. ��� �ޱ� 4. �ڷΰ���");
            if (userChoice == 1) {
                update(id);
            } else if (userChoice == 2) {
                delete(id);
            } else if (userChoice == 3) {
                replyViewer.write(id);
            } else if (userChoice == 4) {
                printList();
            }
        } else {
            int userChoice = ScannerUtil.nextInt(scanner, "1. ��� �ޱ� 2. �ڷΰ���");
            if (userChoice == 1) {
                replyViewer.write(id);
            } else if (userChoice == 2) {
                printList();
            }
        }

    }

    private void update(int id) {
        BoardController controller = new BoardController(connector);
        BoardDTO b = controller.selectOne(id);

        b.setTitle(ScannerUtil.nextLine(scanner, "������ �Է����ּ���"));
        b.setContent(ScannerUtil.nextLine(scanner, "������ �Է����ּ���"));

        controller.update(b);
    }

    private void delete(int id) {
        BoardController controller = new BoardController(connector);

        String yesNo = ScannerUtil.nextLine(scanner, "������ ���� �Ͻðڽ��ϱ� ? Y/N ");
        if (yesNo.equalsIgnoreCase("Y")) {
            controller.delete(id);
            printList();
        } else {
            printOne(id);
        }
    }

}
