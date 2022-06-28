package controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connector.DBConnector;
import model.UserDTO;

public class UserController {
    private Connection conn;

    public UserController(DBConnector connector) {
        conn = connector.makeConnection();
    }

    // 1. 로그인
    public UserDTO logIn(String username, String password) {
        String query = "SELECT * FROM `user` WHERE `username` = ? AND `password` = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, convertToSha(password));

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                UserDTO u = new UserDTO();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setNickname(rs.getString("nickname"));

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 2. 회원가입
    public boolean register(UserDTO u) {
        String query = "INSERT INTO `user` (`userName`,`passWord`,`nickName`) VALUES(?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, u.getUsername());
            pstmt.setString(2, convertToSha(u.getPassword()));
            pstmt.setString(3, u.getNickname());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 3. 회원정보 수정
    public void update(UserDTO u) {
        String query = "UPDATE `user` SET `passWord` = ?, `nickName` = ? WHERE `id` = ?";
        try {
            PreparedStatement pstmt;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, convertToSha(u.getPassword()));
            pstmt.setString(2, u.getNickname());
            pstmt.setInt(3, u.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 4. 회원정보 삭제
    public void delete(int id) {
        String query = "DELETE FROM `user` WHERE `id` = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. sha2 암호화
    public String convertToSha(String password) {
        String converted = null;
        StringBuilder builder = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));

            builder = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                builder.append(String.format("%02x", 255 & hash[i]));
            }

            converted = builder.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return converted;
    }

    // 6. 회원 번호를 토대로 회원을 리턴하는 메소드
    public UserDTO selectOne(int id) {
        UserDTO u = null;
        String query = "SELECT *FROM `user` Where `id` = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                u = new UserDTO();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("userName"));
                u.setNickname(rs.getString("nickName"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }
}
