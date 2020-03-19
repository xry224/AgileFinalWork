package Bean;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

public class FileGetter {

    public static Connection conn = new Connection("172.19.240.244");

    public static boolean login() {
        try {
            //连接远程服务器
            conn.connect();
            //使用用户名和密码登录
            return conn.authenticateWithPassword("root", "password");
        } catch (IOException e) {
            System.err.print("登录服务器失败！");
            e.printStackTrace();
        }
        return false;
    }

    public static File copyFile(Connection conn, String fileName, String localPath) {
        // filename: /home/agile/gym.jpg
        // localPath: F:/
        SCPClient sc = new SCPClient(conn);
        String[] fileNameSplit = fileName.split("/");
        try {
            sc.get(fileName, localPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(localPath + fileNameSplit[fileNameSplit.length - 1]);
    }

}
