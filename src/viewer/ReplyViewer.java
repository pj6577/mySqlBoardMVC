package viewer;

import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Scanner;

import connector.DBConnector;
import controller.ReplyController;
import controller.UserController;
import model.ReplyDTO;
import model.UserDTO;
import util.ScannerUtil;

public class ReplyViewer {
    private Scanner scanner;
    private DBConnector connector;
    private UserDTO logIn;
    
    
    public ReplyViewer(Scanner scanner, DBConnector connector, UserDTO logIn) {
        this.scanner = scanner;
        this.connector = connector;
        this.logIn = logIn;
    }

    public void printList(int boardId) {
        ReplyController controller = new ReplyController(connector);
        ArrayList<ReplyDTO> list = controller.selectAll(boardId);
        
        if(list.isEmpty()) {
            System.out.println("아직 댓글이 없습니다");
        }else {
            UserController userController = new UserController(connector);
            for(ReplyDTO r : list) {
                System.out.printf("%s : %s\n",
                        userController.selectOne(r.getWriterId()).getNickname(),
                                r.getContent());
            }
        }
    }

    public void write(int boardId) {
        ReplyDTO r = new ReplyDTO();
        r.setWriterId(logIn.getId());
       r.setBoardId(boardId);
       
       r.setContent(ScannerUtil.nextLine(scanner, "댓글 내용을 입력해주세요"));
       ReplyController controller = new ReplyController(connector);
       controller.insert(r);
    }

}
