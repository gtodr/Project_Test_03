package test;

import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServerJF extends JFrame {
    JButton startButton, sendButton;
    JTextField portTextField;
    JTextArea mainArea;
    JTextField sendArea;

    ServerSocket serversocket=null;
    Socket socket=null;
    DataOutputStream in=null;
    DataOutputStream out=null;
    Thread thread;

    TestServerJF() {

        //初始化界面
        initJFrame();



    }

    private void initJFrame() {
        this.setTitle("Server");
        this.setSize(500,500);					//设置窗口大小

        Container contain=this.getContentPane();			//新建容器并设置布局
        contain.setLayout(new BorderLayout());

        JPanel portPanel=new JPanel();			//新建port上面板

        portPanel.add(new JLabel("Port:",SwingConstants.LEFT));     //往上面板添加port标签

        JTextField portTextField =new JTextField(30);				//往上面板添加port单行文本
        portPanel.add(portTextField);

        startButton = new JButton("Start");			//往上面板添加start按钮
        portPanel.add(startButton);

        JPanel sayPanel = new JPanel();			//新建sayPanel下面板并设置布局
        sayPanel.setLayout(new BorderLayout());

        sayPanel.add(new JLabel("Say:"),BorderLayout.WEST);	//往下面板添加Say:标签

        JTextField sendArea=new JTextField(25);				//往下面板添加sendArea单行文本
        sayPanel.add(sendArea,BorderLayout.CENTER);

        sendButton =new JButton("Send");			//往下面板添加Send按钮
        sayPanel.add(sendButton, BorderLayout.EAST);

        JTextArea textArea=new JTextArea(10,30);			//新建主文本框
        JScrollPane scrollPane=new JScrollPane(textArea);               //添加滚动条

        contain.add(portPanel,BorderLayout.NORTH);		//往容器里添加上面板
        contain.add(scrollPane,BorderLayout.CENTER);		//往容器里添加主文本框
        contain.add(sayPanel,BorderLayout.SOUTH);		//往容器里添加下面板

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//设置点击关闭图标，结束窗口所在应用程序

        setVisible(true);					//设置窗口为可见
        pack();						//自动调整大小以适应其子组件大小与布局
    }
}
