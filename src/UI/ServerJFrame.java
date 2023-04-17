package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServerJFrame extends JFrame implements ActionListener {

    JButton startButton;
    JButton sendButton;
    JTextField portTextField;
    JTextArea textArea;
    JTextField sendTextField;
    Socket socket;

    public ServerJFrame(){
        //初始化界面
        initJFrame();

        JLabel jl1=new JLabel("Server Configure:");

        this.setVisible(true);

    }

    private void initJFrame() {
        this.setTitle("Server");
        this.setSize(500,500);					//设置窗口大小

        Container contain=this.getContentPane();			//新建容器并设置布局
        contain.setLayout(new BorderLayout());

        JPanel portPanel=new JPanel();			//新建port上面板

        portPanel.add(new JLabel("Port:",SwingConstants.LEFT));     //往上面板添加port标签

        portTextField =new JTextField(8);				//往上面板添加port单行文本
        portPanel.add(portTextField);

        startButton = new JButton("Start");			//往上面板添加start按钮
        portPanel.add(startButton);
        startButton.addActionListener(this);

        JPanel sayPanel = new JPanel();			//新建sayPanel下面板并设置布局
        sayPanel.setLayout(new BorderLayout());

        sayPanel.add(new JLabel("Say:"),BorderLayout.WEST);	//往下面板添加Say:标签

        sendTextField =new JTextField(25);				//往下面板添加sendArea单行文本
        sayPanel.add(sendTextField,BorderLayout.CENTER);

        sendButton =new JButton("Send");			//往下面板添加Send按钮
        sayPanel.add(sendButton, BorderLayout.EAST);
        sendButton.addActionListener(this);

        textArea=new JTextArea(10,30);			//新建主文本框
        JScrollPane scrollPane=new JScrollPane(textArea);               //添加滚动条

        contain.add(portPanel,BorderLayout.NORTH);		//往容器里添加上面板
        contain.add(scrollPane,BorderLayout.CENTER);		//往容器里添加主文本框
        contain.add(sayPanel,BorderLayout.SOUTH);		//往容器里添加下面板

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//设置点击关闭图标，结束窗口所在应用程序

        setVisible(true);					//设置窗口为可见
        pack();						//自动调整大小以适应其子组件大小与布局
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj=e.getSource();

        if (obj==startButton){
            //按下startButton的响应
            System.out.println("startButton Pressed!");
            startButton.setEnabled(false);

            try {
                ServerSocket s = new ServerSocket(Integer.parseInt(portTextField.getText())); // 创建服务器套接字对象
                socket = s.accept();
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);// 创建打印输出流对象
                textArea.append("Client connected"+"\n"); //对两个字符串进行拼接
                ServerThread st = new ServerThread();
                st.start(); //启动线程

                System.out.println("started!");
            } catch (Exception ex) {}

        } else if (obj==sendButton) {
            //按下sendButton的响应
            System.out.println("sendButton Pressed!");

            String str;
            try {PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);// 创建一个打印输出流，形参为从套接字socket 中获取的输出流
                str= sendTextField.getText();
                if(!str.isEmpty()){
                    out.println(new Date()+"\n"+str); //打印输出日期和发送的消息
                    textArea.append(new Date()+" \n me:"+str+"\n");
                    out.flush(); //清空缓存区
                }
                sendTextField.setText("");

                System.out.println("Sent!");
            } catch (Exception ex) {}

        }
    }

    class ServerThread extends Thread{
        public void run(){
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //创建一个缓冲输出流，其形参为从套接字 socket 中获取的输入流
                String str;
                while(true){
                    str = in.readLine(); //按行读取
                    textArea.append( str+"\n");
                }
            } catch (Exception ex) {}
        }
    }


}


