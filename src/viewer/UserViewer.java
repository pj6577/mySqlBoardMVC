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
        String message = "1. 로그인 2. 회원가입 3. 종료 ";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                // 로그인
                login();
                if (logIn != null) {
                    // 게시판 메뉴 이동
                    showMenu();
                }
            } else if (userChoice == 2) {
                // 회원가입
                register();
            } else if (userChoice == 3) {
                // 종료
                System.out.println("종료합니다");
                break;
            }
        }

    }

    private void login() {
        String message;
        message = " 아이디를 입력해주세요";
        String userName = ScannerUtil.nextLine(scanner, message);
        message = " 비밀번호를 입력해주세요";
        String passWord = ScannerUtil.nextLine(scanner, message);

        UserController controller = new UserController(connector);

        logIn = controller.logIn(userName, passWord);

        while (logIn == null) {
            System.out.println("잘 못 입력하셨습니다");
            userName = ScannerUtil.nextLine(scanner, "아이디를 입력해주세요 // 뒤로 가시려면 x를 입력해주세요");
            if (userName.equalsIgnoreCase("X")) {
                break;
            }
            userName = ScannerUtil.nextLine(scanner, "비밀번호를 입력주세요");
            logIn = controller.logIn(userName, passWord);
        }
    }

    private void register() {
        UserDTO u = new UserDTO();
        String message = "사용하실 아이디를 입력해주세요";
        u.setUsername(ScannerUtil.nextLine(scanner, message));

        message = "사용하실 비밀번호를 입력해주세요";
        u.setPassword(ScannerUtil.nextLine(scanner, message));

        message = "사용하실 닉네임을 입력해주세요";
        u.setNickname(ScannerUtil.nextLine(scanner, message));

        UserController controller = new UserController(connector);

        while (!controller.register(u)) {
            System.out.println("잘못 입력하셨습니다");
            String yesNo = ScannerUtil.nextLine(scanner, "새로운 아이디를 입력하시겠습니까? Y/N");
            if (yesNo.equalsIgnoreCase("N")) {
                break;
            }
            u.setUsername(ScannerUtil.nextLine(scanner, "사용하실 아이디를 입력해주세요"));
        }
    }

    private void showMenu() {
        String message = "1. 게시판 2.회원정보 3. 로그아웃";
        while (logIn != null) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                BoardViewer boardViewer = new BoardViewer(scanner, connector, logIn);
                boardViewer.showMenu();
            } else if (userChoice == 2) {
                printOne();
            } else if (userChoice == 3) {
                System.out.println("로그아웃 하셨습니다");
                logIn = null;

            }
        }
    }

    private void printOne() {
        System.out.println("아이디 + " + logIn.getUsername());
        System.out.println("닉네임 + " + logIn.getNickname());
        int userChocie = ScannerUtil.nextInt(scanner, "1. 회원 정보 수정 2. 회원탈퇴 3. 뒤로가기");
        if (userChocie == 1) {
            update();
        } else if (userChocie == 2) {
            delete();
        }
    }

    public void update() {
        String message = "새로운 비밀 번호를 입력주새요";
        String newPassWord = ScannerUtil.nextLine(scanner, message);
        message = "새로운 닉네임을 입력해주세요";
        String newUserNickName = ScannerUtil.nextLine(scanner, message);
        message = "기존 비밀번호를 입력해주세요";
        String oldPassWord = ScannerUtil.nextLine(scanner, message);

        UserController controller = new UserController(connector);
        if (controller.convertToSha(oldPassWord).equals(logIn.getPassword())) {
            logIn.setPassword(newPassWord);
            logIn.setNickname(newUserNickName);

            controller.update(logIn);
        } else {
            System.out.println("잘못 입력 하셨습니다");
        }
    }

    private void delete() {
        String yesNo = ScannerUtil.nextLine(scanner, "정말로 탈퇴 하시겠습니까? Y/N");
        if (yesNo.equalsIgnoreCase("Y")) {
            String passWord = ScannerUtil.nextLine(scanner, "기존 비밀번호를 입력해주세여");
            UserController controller = new UserController(connector);
            if (controller.convertToSha(passWord).equals(logIn.getPassword())) {
                controller.delete(logIn.getId());
                logIn = null;
            }
        }

    }
}
