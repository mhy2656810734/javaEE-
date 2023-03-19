import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import  java.sql.Connection;
/**
 * @author 26568
 * @date 2023-03-19 19:08
 */
// 通过这个类 把数据库连接封装一下
// 此处把这个DBUtil 作为一个工具类 提供 static 方法供其他代码调用
public class DBUtil {
    // 静态成员跟随类对象的,类对象在整个进程中只有唯一一份
    // 静态成员也相当于是唯一的实例(单例模式 饿汉模式)
    private static DataSource dataSource = new MysqlDataSource();
    // 使用静态代码块 在类加载的时候就初始化
    static{
        ((MysqlDataSource)dataSource).setURL("jdbc:mysql://127.0.0.1:3306/java106?characterEncoding=utf8&useSSL=false");
        ((MysqlDataSource)dataSource).setUser("root");
        ((MysqlDataSource)dataSource).setPassword("weiaizuqiu1314");
    }
    // 通过这个方法建立连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 断开连接  释放资源
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
