package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connector.DBConnector;
import model.ReplyDTO;

public class ReplyController {
    private Connection conn;
    
    public ReplyController(DBConnector connector) {
        conn = connector.makeConnection();
    }
    
    // 1. ��� �ۼ�
    public void insert(ReplyDTO r) {
        String query = "INSERT INTO `reply` (`boardId`, `writerId`, `content`) VALUES(?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, r.getBoardId());
            pstmt.setInt(2, r.getWriterId());
            pstmt.setString(3, r.getContent());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     // 2. Ư�� ��� ��� ��ȸ
    public ArrayList<ReplyDTO> selectAll(int boardId) {
        String query = "SELECT *FROM `reply` WHERE `boardId` = ?";
        ArrayList<ReplyDTO> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, boardId);
            
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
                ReplyDTO r = new ReplyDTO();
                r.setId(rs.getInt("id"));
                r.setBoardId(rs.getInt("boardId"));
                r.setWriterId(rs.getInt("writerId"));
                r.setContent(rs.getString("content"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
