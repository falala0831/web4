package cn.bupt.edu.web4.controller;

import cn.bupt.edu.web4.Data.MessageInfor;
import cn.bupt.edu.web4.Data.LoginInfor;
import cn.bupt.edu.web4.Data.Table;
import cn.bupt.edu.web4.model.CheckLogin;
import cn.bupt.edu.web4.model.TableAlters;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class WebController {
    //请求登录界面
    @RequestMapping("/login")
    public String login(LoginInfor user, Model model){
        model.addAttribute("user", user); // 需要先向view模型发送infor数据， 如果已有则覆盖
        // 然后通过数据进行渲染，并且该数据可以进行提交
        return "login";
    }
    // 登录检测界面 (登录界面通过post方法发送)
    @SneakyThrows
    @PostMapping("/checklogin")
    public String checkLogin(LoginInfor user, Model model, HttpServletRequest request) {        // 该参数是从view模型中提取出来的
        if (true == CheckLogin.Check(user)) {                        // 通过校验
            user.setMessage("OK");
            request.getSession().setAttribute("login", "OK");   // 设置标识
            return "redirect:/message";                                // 重定向到message界面
        } else {                                                       // 没通过校验
            user.setMessage("账号或密码错误");                       // 向模型中添加预装属性，然后返回该模型
            user.setPassword("");                                   // 缺点：地址并没有真正的重定向
            return login(user, model);                              // 优点：便于传递参数
        }
    }
    // 如果使用get方法直接请求 登录检测界面，进行重定向
    // 这种重定向的controller映射响应函数可以不返回view 而不返回， 但是不能返回其他非字符串类型
    // 如果登录了 就重定向到main界面，如果没登录就重定向去登录界面
    @SneakyThrows
    @GetMapping("/checklogin")
    public String redirectLogin(HttpServletRequest request) {
        if (null == request.getSession().getAttribute("login"))  // 没登录重定向到登录界面
            return "redirect:/login";
        else                                                       // 登录了重定向到主界面
            return "redirect:/message";
    }
    // 访问主界面 如果还没有登录 将被返回到登录界面
    @SneakyThrows
    @GetMapping("/message")
    public String showMain(HttpServletRequest request) {
        Object flag = request.getSession().getAttribute("login");
        if (null != flag) {
            HttpSession session = request.getSession();
            if (null == session.getAttribute("table")) {
                Table table = new Table();
                session.setAttribute("table", table);
            }
            return "message";
        } else
            return "redirect:/login";
    }
    // 访问添加页面 需要传递一个和前端表单交互数据的提交类
    @RequestMapping("/add")
    public String showAdd(MessageInfor cont, Model model) {
        model.addAttribute("cont", cont);
        return "add";
    }

    // 访问修改联系人 -- 因为修改联系人界面必然是 form 表单通过post提交的
    @PostMapping("/alter")
    public String showAlter(HttpServletRequest request, @ModelAttribute(value = "row") Integer row, MessageInfor m, Model model) {
        Table t = (Table) request.getSession().getAttribute("table");
        MessageInfor infor = t.getTableinfo().elementAt(row);
        model.addAttribute("cont", infor);
        return "alter";
    }
    // 直接访问修改联系人 --- 直接弹回去
    @GetMapping("/alter")
    public String redirectAlter() {
        return "redirect:/message";
    }

    // 直接访问判断联系人修改 直接重定向回去
    @GetMapping("/checkalter")
    public String redirectCheckAlter() {
        return "redirect:/message";
    }

    // 修改联系人信息 （因为名字不能修改 所以一定可以修改成功）
    @PostMapping("/checkalter")
    public String checkAlter(MessageInfor infor, HttpServletRequest request) {
        Table t = (Table) request.getSession().getAttribute("table");
        TableAlters.alterElem(t, infor);
        return "redirect:/message";
    }

    // 删除联系人，不需要返回view 直接使用model 进行处理 如果是直接请求，必然是逻辑错误 直接跳转回去
    @GetMapping("/del")
    public String redirectDel() {
        return "redirect:/message";
    }

    // 删除联系人
    @PostMapping("del")    // ModelAttribute 可以理解成随请求一起发过来的参数Param
    public String DeleteContact(@ModelAttribute(value = "row") Integer row, HttpServletRequest request) {
        Table t = (Table) request.getSession().getAttribute("table");
        TableAlters.deleteElem(t, row);
        return "redirect:/message";
    }

    // 处理添加的URL 如果不是通过post请求的说明是手动请求的，将跳转回去，否则进行处理
    @GetMapping("/checkadd")
    public String redirectAdd() {
        return "add";
    }

    // 处理添加的URL 如果是通过POST提交的，则进行处理
    @PostMapping("/checkadd")
    public String checkAdd(MessageInfor cont, HttpServletRequest request, Model model) {
        Table t = (Table) request.getSession().getAttribute("table");
        boolean is_valid = TableAlters.checkValidAdd(t, cont);
        if (true == is_valid) {
            t.getTableinfo().addElement(cont);
            return "redirect:/message";
        } else {
            cont.setMessage("联系人名称已存在");
            cont.setContactname("");
            return showAdd(cont, model);
        }
    }
}
