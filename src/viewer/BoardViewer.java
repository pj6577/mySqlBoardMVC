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
        String message = " 1. 새 글 쓰기 2. 글 목록 보기 3. 뒤로 가기";
        while (true) {
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if (userChoice == 1) {
                write();
            } else if (userChoice == 2) {
                printList();
            } else if (userChoice == 3) {
                System.out.println("메인 화면으로 돌아 갑니다");
                break;
            }
        }
    }

    private void write() {
        BoardDTO b = new BoardDTO();
        b.setWriterId(logIn.getId());

        b.setTitle(ScannerUtil.nextLine(scanner, "제목을 입력해주세요"));
        b.setContent(ScannerUtil.nextLine(scanner, "내용를 입력해주세요"));

        BoardController controller = new BoardController(connector);
        controller.insert(b);
    }

    private void printList() {
        BoardController controller = new BoardController(connector);
        ArrayList<BoardDTO> list = controller.selectAll();

        if (list.isEmpty()) {
            System.out.println("아직 글이 없습니다");
        } else {
            for (BoardDTO b : list) {
                System.out.printf("%d. %s\n", b.getId(), b.getTitle());
            }
            String messgae = "상세보기 할 글의 번호나 뒤로 가실려면 0을 입력해주세요";
            int userChoice = ScannerUtil.nextInt(scanner, messgae);

            while (userChoice != 0 && controller.selectOne(userChoice) == null) {
                System.out.println("잘못입력하셨습니다");
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

        System.out.println("제목 : " + b.getTitle());
        System.out.println("글 번호 : " + b.getId());
        System.out.println("글 작성자 : " + userController.selectOne(b.getWriterId()).getNickname());
        System.out.println("글 내용 : ");
        System.out.println(b.getContent());

        ReplyViewer replyViewer = new ReplyViewer(scanner, connector, logIn);
        replyViewer.printList(id);

        if (logIn.getId() == b.getWriterId()) {
            int userChoice = ScannerUtil.nextInt(scanner, "1 . 글 수정하기 2 . 글 삭제하기 3. 댓글 달기 4. 뒤로가기");
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
            int userChoice = ScannerUtil.nextInt(scanner, "1. 댓글 달기 2. 뒤로가기");
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

        b.setTitle(ScannerUtil.nextLine(scanner, "제목을 입력해주세여"));
        b.setContent(ScannerUtil.nextLine(scanner, "내용을 입력해주세여"));

        controller.update(b);
    }

    private void delete(int id) {
        BoardController controller = new BoardController(connector);

        String yesNo = ScannerUtil.nextLine(scanner, "정말로 삭제 하시겠습니까 ? Y/N ");
        if (yesNo.equalsIgnoreCase("Y")) {
            controller.delete(id);
            printList();
        } else {
            printOne(id);
        }
    }

}
