import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class Lattice {                    //产生每一个格子对应的内容（-1为雷，空格为“ ”，数字为本身）
    int length,width,mineNumber;
    String Minecode="-1";//如果是雷，点击后变为-1
    String mine[][];//存按钮内容
    public Lattice(int length,int width,int mineNumber){
        this.length=length;
        this.width=width;
        this.mineNumber=mineNumber;
    }
    public void getFirst(int length,int width,int mineNumber){
        this.length=length;
        this.width=width;
        this.mineNumber=mineNumber;        
        mine=new String[length][width]; 
        for (int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                mine[i][j]="0";
            }
        }
    }
    //埋雷
    public void buildMine(){
        Random rand=new Random();
        for(int i=0;i<mineNumber;){
            int l=rand.nextInt(length);//产生行数上的随机位置的雷
            int w=rand.nextInt(width);//产生列上的随机位置的雷
            //如果在这个位置上没有雷，则此随机位置可放上雷
            if(mine[l][w].equals(Minecode)!=true){
                mine[l][w]=Minecode;
                i++;
            }

        }
    }
        //计算周边雷的数量
    public void otherLattice(){
        //计算某个随机非雷处周围的雷的数量（存数字）
        for(int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                if(mine[i][j]==Minecode)
                continue;
                //排除这个位置周围八个空的越界问题，并且统计数量
                int temp=0;
                if(i>0&&j>0&&mine[i-1][j-1]==Minecode) 
                temp++;
                if(i>0&&mine[i-1][j]==Minecode)
                temp++;
                if(i>0&&j<width-1&&mine[i-1][j+1]==Minecode)
                temp++;
                if(j>0&&mine[i][j-1]==Minecode)
                temp++;
                if(j<width-1&&mine[i][j+1]==Minecode)
                temp++;
                if(i<length-1&&j>0&&mine[i+1][j-1]==Minecode)
                temp++;
                if(i<length-1&&mine[i+1][j]==Minecode)
                temp++;
                if(i<length-1&&j<width-1&&mine[i+1][j+1]==Minecode)
                temp++;
                String a=Integer.toString(temp);
                mine[i][j]=a;
                //若非雷随机数周围雷数为0，则存入0(空格)
                if(temp==0)
                mine[i][j]=" ";   
            }
        }
    }
    //返回为雷的数组的下标
    public String getMine(int i,int j){
        return mine[i][j];
    }
}

class Click{
    //左击1
    //插旗2
    //未知3
    int length;
    int width;
    int[][] flag;            //存储该处按钮进行了什么操作
    public Click(int length,int width){
        this.length=length;
        this.width=width;
    }
    public void getFirst(int length,int width){
        this.length=length;
        this.width=width;       
        flag=new int[length][width]; 
        for (int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                flag[i][j]=0;
            }
        }
    }
      //打开空格周围的所有空格
    public boolean leftClick(int i,int j,Lattice game1){
        //如果是雷，则将flag改为1，返回false

        if(game1.mine[i][j].equals("-1")){
            return false;
        }
        //如果是空格
        else if(game1.mine[i][j]==" "){

            if(i>0&&game1.mine[i-1][j]==" " &&flag[i-1][j]==0){
                flag[i-1][j]=1;
                leftClick(i-1,j,game1);
            }
            if(j>0&&game1.mine[i][j-1]==" "&&flag[i][j-1]==0){
                flag[i][j-1]=1;
                leftClick(i, j-1,game1);
            }
            if(j<width-1&&game1.mine[i][j+1]==" "&&flag[i][j+1]==0){
                flag[i][j+1]=1;
                leftClick(i, j+1,game1);
            }
            if(i<length-1&&game1.mine[i+1][j]==" "&&flag[i+1][j]==0){
                flag[i+1][j]=1;
                leftClick(i+1, j,game1);
            }
         
            if(i>0&&game1.mine[i-1][j]!="-1" &&flag[i-1][j]==0){
                flag[i-1][j]=1;
            }
            if(j>0&&game1.mine[i][j-1]!="-1"&&flag[i][j-1]==0){
                flag[i][j-1]=1;
            }            
            if(j<width-1&&game1.mine[i][j+1]!="-1"&&flag[i][j+1]==0){
                flag[i][j+1]=1;
            }
            if(i<length-1&&game1.mine[i+1][j]!="-1"&&flag[i+1][j]==0){
                flag[i+1][j]=1;
            }

            return true;
        }
        else{
            flag[i][j]=1;
            return true;
        }
            
    }
    public void rightClick(int i,int j){
        if(flag[i][j]==0)
            flag[i][j]=2;
        else if(flag[i][j]==2)
            flag[i][j]=3;
        else if(flag[i][j]==3)
            flag[i][j]=0;
            
    }
    public int getFlag(int i,int j){
        return flag[i][j];
    } 
}

class Record{
    int x,y,i;
    int Calendartime;
    public Record(int time,int x,int y)
    {
        Calendartime=time;
        this.x=x;
        this.y=y;
    }
    public int getHighest() throws IOException{       //获取最短时间
        if(x==9&&y==9){
                i=1;
        }
         else if(x==16&&y==16){
                i=2;
        }
        else if(x==16&&y==30){
            i=3;
        }   //i值分别代表初中高模式
        String easy="easy.txt";
        String middle="middle.txt";
        String hard="hard.txt";
        File file1 = new File(easy);
        File file2=new File(middle);
        File file3=new File(hard);
        //如果文件不存在，创建文件
        if (!file1.exists()) 
            file1.createNewFile();
        if (!file2.exists()) 
            file2.createNewFile();
        if (!file3.exists()) 
            file3.createNewFile();
        String time2="0",time3="0",time1="0";
        int minTime;
        if(i==1){                              //9*9难度时
            FileReader fr=new FileReader(file1);
            BufferedReader br=new BufferedReader(fr);
            while(br.ready())
            {
                time1=br.readLine();               //读入原本的最高记录
            }
            FileWriter fw=new FileWriter(file1);
            BufferedWriter bw=new BufferedWriter(fw);
            if(time1=="0"){                             //原本为空时
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else if(Integer.parseInt(time1)>Calendartime){     //新的时间更短时
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else{                                   //原本时间更短时
                bw.write(time1);
                minTime=Integer.parseInt(time1);
            }
            bw.flush();
            bw.close();
            br.close();
            fw.close();
            br.close();
            fr.close();
            return minTime;
        }
        if(i==2){                                    //16*16难度时
            FileReader fr=new FileReader(file2);
            BufferedReader br=new BufferedReader(fr);
            while(br.ready())
            {
                time2=br.readLine();                  //读入原本的最高记录
            }
            FileWriter fw=new FileWriter(file2);
            BufferedWriter bw=new BufferedWriter(fw);
            if(time2=="0"){                                  //原本为空时
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else if(Integer.parseInt(time2)>Calendartime){        //新的时间更短时
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else{                                       //原本时间更短时
                bw.write(time2);
                minTime=Integer.parseInt(time2);
            }
            bw.flush();
            bw.close();
            br.close();
            fw.close();
            br.close();
            fr.close();
            return minTime;
        }
        if(i==3){                                        //16*30难度时
            FileReader fr=new FileReader(file3);
            BufferedReader br=new BufferedReader(fr);
            while(br.ready())
            {
                time3=br.readLine();                      //读入原本的最高记录
            }
            FileWriter fw=new FileWriter(file3);
            BufferedWriter bw=new BufferedWriter(fw);
            if(time3=="0"){                               //原本为空时
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else if(Integer.parseInt(time3)>Calendartime){        //新的时间更短时
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else{                                  //原本时间更短时
                bw.write(time3);
                minTime=Integer.parseInt(time3);
            }
            bw.flush();
            bw.close();
            br.close();
            fw.close();
            br.close();
            fr.close();
            return minTime;
        }
    return 0;
    }
}

public class MineFrame extends JFrame implements ActionListener{
    JMenuBar mb=new JMenuBar();                                         //菜单栏
    FgMenu mGame=new FgMenu("游戏(G)",KeyEvent.VK_G);             //“游戏”菜单
    FgMenu mHelp=new FgMenu("帮助(H)",KeyEvent.VK_H);             //“帮助”菜单
    JMenuItem miEasy=new JMenuItem("简单(E)",KeyEvent.VK_E);
    JMenuItem miNormal=new JMenuItem("普通(N)",KeyEvent.VK_N);
    JMenuItem miHard=new JMenuItem("困难(D)",KeyEvent.VK_D);
    JMenuItem miQuit=new JMenuItem("退出(Q)",KeyEvent.VK_Q);
    JMenuItem miRule=new JMenuItem("规则(R)",KeyEvent.VK_R);
    JPanel upPane=new JPanel();                  //存放上半部分组件的容器
    JPanel downPane=new JPanel();                 //存放下半部分组件的容器
    JPanel Pane1=new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));      //存放显示时间的组件的容器，流式布局
    JPanel Pane2=new JPanel();                                                      //存放重新开始的按钮的容器
    JPanel Pane3=new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));      //存放显示剩余地雷数的组件的容器，流式布局
    JPanel Panec;                                //定义5个panel，使扫雷部分居于中间
    JPanel Panen=new JPanel();
    JPanel Panes=new JPanel();
    JPanel Panew=new JPanel();
    JPanel Panee=new JPanel();
    JSplitPane sp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,upPane,downPane);      //创建水平分割条，即分为上下两部分
    ImageIcon[] ic=new ImageIcon[10];
    JLabel time1,time2,time3;              //存放显示时间图片的容器
    JButton again;             //笑脸按钮
    JButton[][] mineButton;    //扫雷按钮
    JLabel number1,number2;   //存放剩余旗帜数量
    int row;
    int cols;
    int mineNumber,flagNumber=10;         //地雷数及旗帜数
    int second,temp=0;                   //存放游戏进行的时间
    boolean gameRun=true;             //记录游戏是否在进行，用于计时的开始
    Lattice lattice=new Lattice(9,9,10);         //默认产生9*9类型的地雷类
    Click click=new Click(9,9);       //默认生成9*9的点击类

    MineFrame(String sTitle){
        super(sTitle);        
        row=9;             //初始时默认难度为9*9，地雷数为10
        cols=9;
        mineNumber=10;
        addMenus();        //添加菜单栏
        miRule.addActionListener(this);
        miQuit.addActionListener(this);
        Panec=new JPanel();
        addupPane();            //添加上面板
        adddownPane();          //添加下面板
        centerWindows();         //使窗口在显示屏中居中显示
        Toolkit tk=getToolkit();   //得到Toolkit对象
        Image icon=tk.getImage("mine.png");     //获取图标
        setIconImage(icon);     //改变窗口图标
        setBomb();            //添加地雷按钮
        lattice.getFirst(row,cols,mineNumber);       //重置难度及地雷数
        lattice.buildMine();          //随机产生地雷
        lattice.otherLattice();       //产生剩余部分内容
        click.getFirst(row,cols);     //重置点击类内的行和列
    }

    private void addupPane(){                 //设计upPane布局及组件
        Insets space = new Insets(0, 0, 0, 0);    //指定容器必须在其每条边留下的空间
        sp.setDividerSize(5);          //设置分割条本身宽度为5个像素
        ic[0]=new ImageIcon("smile.png");
        time1=new JLabel(new ImageIcon("0.png"));      //将时间初始化为000
        time2=new JLabel(new ImageIcon("0.png"));
        time3=new JLabel(new ImageIcon("0.png"));
        again=new JButton(ic[0]);                         //设置按钮图片
        again.setMargin(space);                           //设置按钮边框和标签之间的空白距,使按钮适应图片大小
        number1=new JLabel(new ImageIcon("1.png"));   //将地雷数初始化为00
        number2=new JLabel(new ImageIcon("0.png"));
        upPane.setLayout(new GridLayout(1,3));     //upPane布局方式为网格化布局（1行3列）
        upPane.add(Pane1);             //添加组件
        upPane.add(Pane2);
        upPane.add(Pane3);
        Pane1.add(time1);
        Pane1.add(time2);
        Pane1.add(time3);
        Pane2.add(again);
        again.addActionListener(new ActionListener(){      //笑脸按钮的事件监听（通过匿名内部类实现）
            public void actionPerformed(ActionEvent e){
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < cols; j++) {
                        mineButton[i][j].setIcon(ic[1]);      //重新设置游戏区按钮图标
                    }
                }
                lattice.getFirst(row,cols,mineNumber);     //重置难度及地雷数
                lattice.buildMine();           //重新产生随机地雷数
                lattice.otherLattice();        //重新填充剩余部分内容
                click.getFirst(row,cols);      //重置点击类内的行和列
                second=0;                   //时间重置为0
                temp=0;
                gameRun=true;              //游戏进行
                flagNumber=mineNumber;     //旗帜总数等于地雷数
                time1.setIcon(new ImageIcon("0.png"));
                time2.setIcon(new ImageIcon("0.png"));
                time3.setIcon(new ImageIcon("0.png"));
                again.setIcon(new ImageIcon("smile.png"));
                number1.setIcon(new ImageIcon(flagNumber/10+".png"));
                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
            }
        });
        Pane3.add(number1);                    //添加显示时间功能的组件
        Pane3.add(number2);
        Pane1.setBackground(Color.GRAY);       //设置Pane1背景颜色为灰色
        Pane2.setBackground(Color.GRAY);       //设置Pane2背景颜色为灰色
        Pane3.setBackground(Color.GRAY);       //设置Pane3背景颜色为灰色
        upPane.setBackground(Color.GRAY);      //设置upPane背景颜色为灰色
        Container c=getContentPane();          //获取窗口面板
        c.add(sp);                             //添加分割条
        sp.setDividerLocation(0.3);     //上面占0.3，下面占0.7     
    }

    private void adddownPane(){         //设计downPane布局及组件
        downPane.setLayout(new BorderLayout());   //构造新的边框布局，组件之间没有间隙
        downPane.add("Center",Panec);        //添加组件并确定方位
        downPane.add("North",Panen);
        downPane.add("South",Panes);
        downPane.add("West",Panew);
        downPane.add("East",Panee);
        Panen.setBackground(Color.GRAY);        //设置Panen背景颜色为灰色
        Panew.setBackground(Color.GRAY);        //设置Panew背景颜色为灰色
        Panee.setBackground(Color.GRAY);        //设置Panee背景颜色为灰色
        Panes.setBackground(Color.GRAY);        //设置Panes背景颜色为灰色
        downPane.setBackground(Color.GRAY);     //设置downPane背景颜色为灰色

    }

    private void addMenus(){
        setJMenuBar(mb);

        mGame.add(miEasy);          //模式选择：简单
        mGame.add(miNormal);        //模式选择：普通
        mGame.add(miHard);          //模式选择：困难
        mGame.add(miQuit);          //退出
        mHelp.add(miRule);          //规则

        mb.add(mGame);              //将“游戏”菜单添加到菜单栏
        mb.add(mHelp);              //将“帮助”菜单添加到菜单栏

        miEasy.addActionListener(new ActionListener(){            //选择简单模式时，将大小改为9*9，同时将时间重置
            public void actionPerformed(ActionEvent e){
                if(cols!=9){                  //点击前不为9*9则重置行列及地雷数并重新添加组件
                    row=9;
                    cols=9;
                    mineNumber=10;                
                    setBomb();
                }
                else{                //点击前为9*9则重置游戏区按钮图标
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < cols; j++) {
                            mineButton[i][j].setIcon(ic[1]);
                        }
                    }
                }
                lattice.getFirst(row,cols,mineNumber);       //重置地雷类和点击类
                lattice.buildMine();
                lattice.otherLattice();
                click.getFirst(row,cols);
                again.setIcon(new ImageIcon("smile.png"));   //按钮图标和时间显示重置
                time1.setIcon(new ImageIcon("0.png"));
                time2.setIcon(new ImageIcon("0.png"));
                time3.setIcon(new ImageIcon("0.png"));
                second=0;
                temp=0;
                gameRun=true;      //游戏运行
                flagNumber=mineNumber;
                number1.setIcon(new ImageIcon(flagNumber/10+".png"));    //重置剩余旗帜数
                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
            }
        });
        miNormal.addActionListener(new ActionListener(){              //选择普通模式时，将大小改为16*16，同时将时间重置
            public void actionPerformed(ActionEvent e){
                if(cols!=16){               //点击前不为16*16则重置行列及地雷数并重新添加组件
                    row=16;
                    cols=16;
                    mineNumber=40;                
                    setBomb();
                }
                else{                     //点击前为16*16则重置游戏区按钮图标
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < cols; j++) {
                            mineButton[i][j].setIcon(ic[1]);
                        }
                    }
                }
                lattice.getFirst(row,cols,mineNumber);         //重置地雷类和点击类
                lattice.buildMine();
                lattice.otherLattice();
                click.getFirst(row,cols);
                again.setIcon(new ImageIcon("smile.png"));   //按钮图标和时间显示重置
                time1.setIcon(new ImageIcon("0.png"));
                time2.setIcon(new ImageIcon("0.png"));
                time3.setIcon(new ImageIcon("0.png"));
                second=0;
                temp=0;
                gameRun=true;             //游戏运行
                flagNumber=mineNumber;
                number1.setIcon(new ImageIcon(flagNumber/10+".png"));        //重置剩余旗帜数
                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
            }
        });
        miHard.addActionListener(new ActionListener(){               //选择困难模式时，将大小改为16*30，同时将时间重置
            public void actionPerformed(ActionEvent e){
                if(cols!=30){             //点击前不为16*30则重置行列及地雷数并重新添加组件
                    row=16;
                    cols=30;
                    mineNumber=99;                
                    setBomb();
                }
                else{                 //点击前为16*30则重置游戏区按钮图标
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < cols; j++) {
                            mineButton[i][j].setIcon(ic[1]);
                        }
                    }
                }
                lattice.getFirst(row,cols,mineNumber);       //重置地雷类和点击类
                lattice.buildMine();
                lattice.otherLattice();
                click.getFirst(row,cols);
                again.setIcon(new ImageIcon("smile.png"));    //按钮图标和时间显示重置
                time1.setIcon(new ImageIcon("0.png"));
                time2.setIcon(new ImageIcon("0.png"));
                time3.setIcon(new ImageIcon("0.png"));
                second=0;
                temp=0;
                gameRun=true;             //游戏运行
                flagNumber=mineNumber;
                number1.setIcon(new ImageIcon(flagNumber/10+".png"));        //重置剩余旗帜数
                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
            }
        });
    }

    public void setBomb(){                  //设置游戏区按钮
        Panec.removeAll();                 //从此容器中删除所有组件
        Panec.setLayout(new GridLayout(row,cols));         //网格化布局（row行cols列）
        Panec.setBackground(Color.GRAY);                   //背景设为灰色
        ic[1]=new ImageIcon("dark.png");
        mineButton = new JButton[row][cols];             //背景设为灰色
        for (int i = 0; i < row; i++) {                  //二重循环设置按钮的图片并使按钮适应图片大小
            for (int j = 0; j < cols; j++) { 
                mineButton[i][j]=new JButton(ic[1]);
                mineButton[i][j].addActionListener(this);
                JButton btn=new JButton();
                btn=mineButton[i][j];
                btn.addMouseListener(new MouseAdapter(){        //为游戏区按钮添加事件监听（通过匿名内部类实现）
                    int x=0,y=0;
                    public void mouseClicked(MouseEvent e){
                        for(int i1=0;i1<row;i1++){
                            for (int j1= 0; j1< cols; j1++){
                                if(e.getSource()==mineButton[i1][j1]){
                                    x=i1;               //获取点击的按钮的下标
                                    y=j1;
                                }
                            }
                        }
                        if(gameRun==true){                         //游戏进行中
                            if(e.getButton()==MouseEvent.BUTTON3&&click.getFlag(x,y)==0&&flagNumber>0){     //右击并且该处未进行操作，且旗帜数仍有剩余
                                mineButton[x][y].setIcon(new ImageIcon("flag.png"));      //设置为flag图标
                                click.rightClick(x,y);
                                flagNumber--;                                           //插旗，旗帜数减少并重新显示
                                number1.setIcon(new ImageIcon(flagNumber/10+".png"));       
                                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
                            }
                            else if(e.getButton()==MouseEvent.BUTTON3&&click.getFlag(x,y)==2){     //右击两次，图标从旗帜变成问号
                                mineButton[x][y].setIcon(new ImageIcon("problem.png"));
                                click.rightClick(x,y);        
                                flagNumber++;                                                 //旗帜数增加并重新显示
                                number1.setIcon(new ImageIcon(flagNumber/10+".png"));
                                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
                            }
                            else if(e.getButton()==MouseEvent.BUTTON3&&click.getFlag(x,y)==3){       //右击三次，图标从问号变成普通按钮
                                mineButton[x][y].setIcon(new ImageIcon("dark.png"));
                                click.rightClick(x,y);
                            }
                        }
                    }
                });
                Panec.add(mineButton[i][j]);            //添加按钮
            }
        }
        setSize(cols*40,row*40+90);            //设置页面大小
        setResizable(false);         //使页面大小不能改变

    }

    public void count()throws InterruptedException{       //计时并改变图片输出
        second=0;              //秒数初始化为0
        while(true)
        {
            second++;           //秒数加1
            time1.setIcon(new ImageIcon(second/100+".png"));       //改变时间显示
            time2.setIcon(new ImageIcon(second/10%10+".png"));
            time3.setIcon(new ImageIcon(second%10+".png"));
            Thread.sleep(1000);        //间隔1秒进行
        } 
    }


    public void centerWindows(){
        Toolkit tk=getToolkit();       //获得显示屏桌面窗口的大小
        Dimension dm=tk.getScreenSize();
        setLocation((int)(dm.getWidth()-getWidth())/2,(int)(dm.getHeight()-getHeight())/2);     //让窗口居中显示
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == miQuit) {                   //退出程序
            System.exit(0);
        }
        if (e.getSource() == miRule) {                  //弹出消息框显示规则
            JOptionPane.showMessageDialog(this,"游戏的基本操作包括左键单击（Left Click）、右键单击（Right Click）、双击（Chording）三种。其中左键用于打开安全的格子，推进游戏进度；\n右键用于标记地雷，以辅助判断，或为接下来的双击做准备；双击在一个数字周围的地雷标记完时，相当于对数字周围未打开的方块均进行一次左键单击操作：\n左键单击：在判断出不是雷的方块上按下左键，可以打开该方块。如果方块上出现数字，则该数字表示其周围3×3区域中的地雷数\n（一般为8个格子，对于边块为5个格子，对于角块为3个格子。所以扫雷中最大的数字为8）；如果方块上为空（相当于0），则可以递归地打开与空相邻的方块；如果不幸触雷，则游戏结束。\n右键单击：在判断为地雷的方块上按下右键，可以标记地雷（显示为小红旗）。\n重复一次或两次操作可取消标记（如果在游戏菜单中勾选了 标记(?)，则需要两次操作来取消标雷）。\n双击：同时按下左键和右键完成双击。当双击位置周围已标记雷数等于该位置数字时操作有效，\n相当于对该数字周围未打开的方块均进行一次左键单击操作。地雷未标记完全时使用双击无效。若数字周围有标错的地雷，则游戏结束，标错的地雷上会显示一个×.","提示",JOptionPane.INFORMATION_MESSAGE);
            miRule.setVisible(true);
        }
        if(gameRun==true){            //游戏进行中
            for(int i=0;i<row;i++){               //二重循环判断是哪个按钮被点击
                for(int j=0;j<cols;j++){
                    if(e.getSource()==mineButton[i][j]){
                        if(lattice.getMine(i,j).equals("-1")&&click.getFlag(i,j)==0){    //点击处为地雷且该处未被点击过
                            mineButton[i][j].setIcon(new ImageIcon("boom.png"));    //点击处地雷炸开
                            for(int i1=0;i1<row;i1++){
                                for(int j1=0;j1<cols;j1++){
                                    if(lattice.getMine(i1,j1).equals("-1")&(i1!=i||j1!=j)){            //地雷被点击后显示所有地雷
                                        mineButton[i1][j1].setIcon(new ImageIcon("mine.png"));
                                    }
                                }
                            }
                            again.setIcon(new ImageIcon("cry.png"));        //笑脸按钮变成哭脸
                            JOptionPane.showMessageDialog(this,"你踩到了雷\n游戏失败！","失败",JOptionPane.INFORMATION_MESSAGE);  //弹出消息框显示游戏失败
                            gameRun=click.leftClick(i,j,lattice);    //游戏结束
                        } 
                        else if(lattice.getMine(i,j).equals(" ")&&click.getFlag(i,j)==0){        //点击的按钮处为空格，且未被点击过
                            mineButton[i][j].setIcon(new ImageIcon("blank.png"));
                            gameRun=click.leftClick(i,j,lattice);             //游戏继续
                            for(int i1=0;i1<row;i1++){
                                for(int j1=0;j1<cols;j1++){                                    //从空格处炸开直到边缘为数字
                                    if(lattice.getMine(i1,j1).equals(" ")&&click.getFlag(i1,j1)==1){
                                        mineButton[i1][j1].setIcon(new ImageIcon("blank.png"));
                                    }
                                    else if(!lattice.getMine(i1,j1).equals("-1")&&click.getFlag(i1,j1)==1){
                                        mineButton[i1][j1].setIcon(new ImageIcon(lattice.getMine(i1,j1)+lattice.getMine(i1,j1)+".png"));
                                    }
                                }
                            }
                        }
                        else if(click.getFlag(i,j)==0){     //该按钮为数字且未被点击过
                            mineButton[i][j].setIcon(new ImageIcon(lattice.getMine(i,j)+lattice.getMine(i,j)+".png"));
                            gameRun=click.leftClick(i,j,lattice);            //游戏继续
                        }
                        temp=second;
                    }
                }
            }
        }
        int mn=0;
        for(int i1=0;i1<row;i1++){
            for(int j1=0;j1<cols;j1++){
                if(click.getFlag(i1,j1)==0||click.getFlag(i1,j1)==2||click.getFlag(i1,j1)==3){      //对未被点开或已被插旗或设置为问号的按钮进行计数
                    mn++;                    
                }
            }
        } 
        mn--;
        if(mn==mineNumber){     //剩余按钮数等于地雷数时
            gameRun=false;      //游戏结束
            Record record=new Record(temp,row,cols);      //创建记录对象
            try {
                again.setIcon(new ImageIcon("win.png"));
                JOptionPane.showMessageDialog(this,"恭喜你取得胜利,你所花的时间为"+temp+"秒，该难度最高记录"+record.getHighest()+"秒","成功",JOptionPane.INFORMATION_MESSAGE);        //弹出消息框显示获得胜利及最高纪录
            }
            catch (HeadlessException e1) {} 
            catch (IOException e1) {}  
        }
    }


    public static void main(String args[])throws InterruptedException,IOException{
        MineFrame frm=new MineFrame("Minesweeper");          //创建窗口对象
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        frm.count();
    }
}

class FgMenu extends JMenu{           //自定义菜单
    public FgMenu(String label){
        super(label);
    }
    public FgMenu(String label,int nAccelerator){
        super(label);
        setMnemonic(nAccelerator);
    }
}