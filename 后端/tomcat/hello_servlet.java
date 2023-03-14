import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 26568
 * @date 2023-03-09 13:41
 */

// 这里的@WebServlet注解 作用是把当前的类和一个HTTP请求的路径关联起来.
@WebServlet("/hello")
public class helloServlet extends HttpServlet {
    // 这里继承的主要目的,是为了针对HttpServlet 进行功能的扩展
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        // 在服务器的控制台里面打印
        System.out.println("hello");
        // 要想把hello world 返回给客户端,需要使用下面的代码
        // getWriter 会得到一个 writer对象 这个writer对象是从属于resp对象的,
        //此处的write操作其实是往resp的body部分进行写入
        resp.getWriter().write("hello world");
    }
}
