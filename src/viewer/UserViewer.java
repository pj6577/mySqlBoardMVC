package viewer;

import java.util.Scanner;

import connector.DBConnector;
import connector.MYSqlConnector;
import controller.UserController;
import model.UserDTO;
import util.ScannerUtil;

public class UserViewer {
    private Scanner scanner;
    private UserDTO logIn;
    private DBConnector connector;

    public UserViewer(Scanner scanner, DBConnector connector) {
        this.scanner = scanner;
        this.connector = connector;
    }

    public void showIndex() {
        String message = "1. �α��� 2. ȸ������ 3. ���� ";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                // �α���
                login();
                if (logIn != null) {
                    // �Խ��� �޴� �̵�
                    showMenu();
                }
            } else if (userChoice == 2) {
                // ȸ������
                register();
            } else if (userChoice == 3) {
                // ����
                System.out.println("�����մϴ�");
                break;
            }
        }

    }

    private void login() {
        String message;
        message = " ���̵� �Է����ּ���";
        String userName = ScannerUtil.nextLine(scanner, message);
        message = " ��й�ȣ�� �Է����ּ���";
        String passWord = ScannerUtil.nextLine(scanner, message);

        UserController controller = new UserController(connector);

        logIn = controller.logIn(userName, passWord);

        while (logIn == null) {
            System.out.println("�� �� �Է��ϼ̽��ϴ�");
            userName = ScannerUtil.nextLine(scanner, "���̵� �Է����ּ��� // �ڷ� ���÷��� x�� �Է����ּ���");
            if (userName.equalsIgnoreCase("X")) {
                break;
            }
            userName = ScannerUtil.nextLine(scanner, "��й�ȣ�� �Է��ּ���");
            logIn = controller.logIn(userName, passWord);
        }
    }

    private void register() {
        UserDTO u = new UserDTO();
        String message = "����Ͻ� ���̵� �Է����ּ���";
        u.setUsername(ScannerUtil.nextLine(scanner, message));

        message = "����Ͻ� ��й�ȣ�� �Է����ּ���";
        u.setPassword(ScannerUtil.nextLine(scanner, message));

        message = "����Ͻ� �г����� �Է����ּ���";
        u.setNickname(ScannerUtil.nextLine(scanner, message));

        UserController controller = new UserController(connector);

        while (!controller.register(u)) {
            System.out.println("�߸� �Է��ϼ̽��ϴ�");
            String yesNo = ScannerUtil.nextLine(scanner, "���ο� ���̵� �Է��Ͻðڽ��ϱ�? Y/N");
            if (yesNo.equalsIgnoreCase("N")) {
                break;
            }
            u.setUsername(ScannerUtil.nextLine(scanner, "����Ͻ� ���̵� �Է����ּ���"));
        }
    }

    private void showMenu() {
        String message = "1. �Խ��� 2.ȸ������ 3. �α׾ƿ�";
        while (logIn != null) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                BoardViewer boardViewer = new BoardViewer(scanner, connector, logIn);
                boardViewer.showMenu();
            } else if (userChoice == 2) {
                printOne();
            } else if (userChoice == 3) {
                System.out.println("�α׾ƿ� �ϼ̽��ϴ�");
                logIn = null;

            }
        }
    }

    private void printOne() {
        System.out.println("���̵� + " + logIn.getUsername());
        System.out.println("�г��� + " + logIn.getNickname());
        int userChocie = ScannerUtil.nextInt(scanner, "1. ȸ�� ���� ���� 2. ȸ��Ż�� 3. �ڷΰ���");
        if (userChocie == 1) {
            update();
        } else if (userChocie == 2) {
            delete();
        }
    }

    public void update() {
        String message = "���ο� ��� ��ȣ�� �Է��ֻ���";
        String newPassWord = ScannerUtil.nextLine(scanner, message);
        message = "���ο� �г����� �Է����ּ���";
        String newUserNickName = ScannerUtil.nextLine(scanner, message);
        message = "���� ��й�ȣ�� �Է����ּ���";
        String oldPassWord = ScannerUtil.nextLine(scanner, message);

        UserController controller = new UserController(connector);
        if (controller.convertToSha(oldPassWord).equals(logIn.getPassword())) {
            logIn.setPassword(newPassWord);
            logIn.setNickname(newUserNickName);

            controller.update(logIn);
        } else {
            System.out.println("�߸� �Է� �ϼ̽��ϴ�");
        }
    }

    private void delete() {
        String yesNo = ScannerUtil.nextLine(scanner, "������ Ż�� �Ͻðڽ��ϱ�? Y/N");
        if (yesNo.equalsIgnoreCase("Y")) {
            String passWord = ScannerUtil.nextLine(scanner, "���� ��й�ȣ�� �Է����ּ���");
            UserController controller = new UserController(connector);
            if (controller.convertToSha(passWord).equals(logIn.getPassword())) {
                controller.delete(logIn.getId());
                logIn = null;
            }
        }

    }
}
