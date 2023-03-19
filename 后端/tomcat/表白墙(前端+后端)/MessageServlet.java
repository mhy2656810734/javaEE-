import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 26568
 * @date 2023-03-19 8:58
 */
class Message{
    public String from;
    public String to;
    public String message;
}
@WebServlet("/message")
public class MessageServlet extends HttpServlet {
   // private List<Message> messageList = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    // 向服务器提交数据
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 把body中的数据读取出来,解析成一个Message 对象
        Message message = objectMapper.readValue(req.getInputStream(),Message.class);
        save(message);
        // 此处的状态码不设定也是200
        resp.setStatus(200);
    }
    // 从服务器获取数据
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 显式的告诉浏览器 数据是json 格式的 字符集是 utf8
        resp.setContentType("application/json;charset=utf8");
        List<Message> messageList = load();
        objectMapper.writeValue(resp.getWriter(),messageList);
    }
    // 删除服务器中的数据
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 直接删除list最后的一个元素即可
//        List<Message> messageList = load();
//        messageList.remove(messageList.size()-1);
        // 直接调用删除的方法即可
        //delete();
    }
    // 提供如下三个方法: 往数据库中存数据 以及删数据
    private void save(Message message) {
        // JDBC 操作
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //1.建立连接
            connection = DBUtil.getConnection();
            // 2.构造字符串
            String sql = "insert into message values(?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,message.from);
            statement.setString(2,message.to);
            statement.setString(3,message.message);
            // 3.执行语句
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4.关闭资源
            DBUtil.close(connection,statement,null);
        }
    }
    // 将所有数据返回
    private List<Message> load() {
        List<Message> messageList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            // 1.建立数据库连接
            connection = DBUtil.getConnection();
            // 2.构造字符串
            String sql = "select * from message";
            statement = connection.prepareStatement(sql);
            // 3.执行sql
            resultSet = statement.executeQuery();
            // 4.循环遍历出结果
            while (resultSet.next()) {
                Message message = new Message();
                message.from = resultSet.getString("from");
                message.to = resultSet.getString("to");
                message.message = resultSet.getString("message");
                messageList.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // 5.断开连接,释放资源
            DBUtil.close(connection,statement,resultSet);
        }
        return messageList;
    }
//    private static void delete() {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        try {
//            // 1.建立连接
//            connection = DBUtil.getConnection();
//            // 2.构造字符串
//            String sql = "delete from message where id = (select max(id) from student ";
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
