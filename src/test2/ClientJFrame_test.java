package test2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ClientJFrame_test extends JFrame implements ActionListener {
    //通过GPT修改 4/17

    JTextField ipTextField;
    JTextField portTextField;
    JTextField sendTextField;
    JTextArea textArea;
    JButton connectButton;
    JButton sendButton;
    Socket socket;

    public ClientJFrame_test(){

        //初始化界面
        initJFrame();

    }

    private void initJFrame() {
        this.setTitle("Client");
        this.setSize(500,500);					//设置窗口大小

        Container contain=this.getContentPane();			//新建容器并设置布局
        contain.setLayout(new BorderLayout());

        JPanel portPanel=new JPanel();			            //新建port上面板

//        portPanel.add(new JLabel("Server IP:",SwingConstants.LEFT));     //往上面板添加标签
        portPanel.add(new JLabel("Server IP:"));     //往上面板添加Server IP标签
        ipTextField = new JTextField(15);				//往上面板添加Server IP的单行文本
        ipTextField.setText("127.0.0.1");
        portPanel.add(ipTextField);

        portPanel.add(new JLabel("Port:"));     //往上面板添加Port标签
        portTextField = new JTextField(8);
        portPanel.add(portTextField);

        connectButton = new JButton("Connect");			//往上面板添加start按钮
        portPanel.add(connectButton);
        connectButton.addActionListener(this);

        JPanel sayPanel = new JPanel();			//新建sayPanel下面板并设置布局
        sayPanel.setLayout(new BorderLayout());

        sayPanel.add(new JLabel("Say:"),BorderLayout.WEST);	//往下面板添加Say:标签

        sendTextField =new JTextField(25);				//往下面板添加sendArea单行文本
        sayPanel.add(sendTextField,BorderLayout.CENTER);

        sendButton =new JButton("Send");			//往下面板添加Send按钮
        sayPanel.add(sendButton, BorderLayout.EAST);
        sendButton.addActionListener(this);

        textArea=new JTextArea(10,30);			//新建主文本框
        JScrollPane scrollPane=new JScrollPane(textArea);   //添加滚动条

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

        if (obj==connectButton){
            System.out.println("connectButton Pressed!");       //输出按下信息
            connectButton.setEnabled(false);

            int portNum;
            try {
                textArea.setText("Connect to server....\n"); //在文本区中显示 "Connect to server....\n"
                portNum =Integer.parseInt(portTextField.getText()); //读取 port 文本框中的字符串，并将字符串转化为整型赋给 portNum
                socket = new Socket(ipTextField.getText(),portNum); //定义对象 socket，并指定主机号（host）和端口号 portNum
                ClientThread ct = new ClientThread(); //定义一个 ClientThread 类的对象 ct
                ct.start(); //启动一个线程，并调用ClientThread 类中的 run（）方法
            } catch (Exception ex) {
                System.out.println(ex);
            }

        } else if (obj==sendButton) {
            System.out.println("sendButton Pressed!");      //输出按下信息

            String str;
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); //从 socket 获取字节输出流，并将其最终转化为 PrintWriter
                str= sendTextField.getText(); //从文本框中获取字符串赋给 str
                if(!str.isEmpty()){
                    out.println(new Date()+ "\n"+ str); //打印日期和 str
                    textArea.append(new Date()+"\n me:"+str+"\n");
                    out.flush(); //清空缓存区
                }
                sendTextField.setText("");
            } catch (Exception ex) {
                System.out.println(ex);
            }

        }

    }

    class ClientThread extends Thread{
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
