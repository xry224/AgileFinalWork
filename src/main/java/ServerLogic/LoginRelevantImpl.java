package ServerLogic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import Bean.User;
import DataBase.DataBase;

public class LoginRelevantImpl {

    private static DataBase db = new DataBase();
    /**
     * @param certificate 登录凭证，可以是用户名或邮箱。种类需要后台自行判断
     * @param password    登录密码
     * @return Bean.User
     * @description 根据登录凭证与密码登录系统，返回此次登录的User，如果该User未注册则返回NULL
     */
    public static User login(String certificate, String password) {
        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        System.out.println(certificate + " " + password);
        try {
            conn = db.getConnection();
            String sql = "select id from user where username = ? or email = ? and password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, certificate);
            stmt.setString(2, certificate);
            stmt.setString(3, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                user = new getDataImpl().getUser(id);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);
        } finally {
            db.closeConnection(stmt, conn);
        }
        return user;
    }
    /**
     * @param certificate 用户的邮箱
     * @param userName    用户名
     * @param password    密码
     * @param verifyCode  用户输入的验证码，此条选择性实现
     * @param expectedVerifyCode 正确的验证码
     * @return Bean.User
     * @description 用户注册，注册成功则返回本次注册的User并直接登录，注册失败则返回NULL
     */
    public static String register(String certificate, String userName, String password, String verifyCode, String expectedVerifyCode) {
        //用户名存在
        String info = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "select username from user where username=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "用户名已存在！";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }

        //验证码错误
        if (!expectedVerifyCode.equals(verifyCode))
            return "验证码错误";

        //正常注册
        conn = null;
        stmt = null;
        try {
            conn = db.getConnection();
            String sql = "insert into user(username,password,email) values(?,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, password);
            stmt.setString(3, certificate);
            int row = stmt.executeUpdate();
            if (row > 0) {
                info = "注册成功！";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return info;
    }
    private static MimeMessage createSimpleMail(Session session, String mailfrom, String mailTo, String mailTittle, String mailText) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailfrom));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        message.setSubject(mailTittle);
        message.setContent(mailText, "text/html;charset=UTF-8");
        return message;
    }

    private static String generateVerifyCode() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder uuid = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            uuid.append(ch);
        }
        return uuid.toString();
    }
    /**
     * @param target 目标邮箱地址
     * @return boolean
     * @description 往目标邮箱中发送验证码，发送成功范围true，否则返回false
     */
    public static String sendVerifyCode(String target) throws Exception {
        String verifyCode = generateVerifyCode();
        String mailFrom = "602300728@qq.com";//发送方邮件
        String mailFrom_password = "hdsoydfqjmjtbcde";//发送方密码
        String mailTittle = "验证码";
        String mailText = "验证码：" + verifyCode;
        String mail_host = "smtp.qq.com";
        Properties prop = new Properties();
        prop.setProperty("mail.host", mail_host);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop);
        session.setDebug(true);
        Transport ts = session.getTransport();
        ts.connect(mail_host, mailFrom, mailFrom_password);
        Message message = createSimpleMail(session, mailFrom, target, mailTittle, mailText);
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
        return verifyCode;
    }

    /**
     * @param mail               用户注册时使用的邮箱
     * @param verifyCode         用户输入的验证码
     * @param expectedVerifyCode 正确的验证码
     * @param newPassword        新密码
     * @param passwordConfirm    确认密码
     * @return boolean
     * @description 重置密码，成功返回true，否则返回false
     */
    public static boolean resetPassword(String mail, String verifyCode, String expectedVerifyCode, String newPassword, String passwordConfirm) {
        if (!newPassword.equals(passwordConfirm) || !verifyCode.equals(expectedVerifyCode))
            return false;
        boolean success = false;
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = db.getConnection();
            String sql = "update user set password = ? where email = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, mail);
            int row = stmt.executeUpdate();
            if (row > 0)
                success = true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.out);;
        } finally {
            db.closeConnection(stmt, conn);
        }
        return success;
    }
}
