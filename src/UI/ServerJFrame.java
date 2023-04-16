package UI;

import javax.swing.*;

public class ServerJFrame extends JFrame {

    public ServerJFrame(){
        //初始化界面
        initJframe();

        JLabel jl1=new JLabel("Server Configure:");

        this.setVisible(true);

    }

    private void initJframe() {
        //设置长宽
        this.setSize(400,540);

        //标题
        this.setTitle("Server");

        //居中
        this.setLocationRelativeTo(null);

        //设置关闭操作
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setLayout(null);
    }

}
