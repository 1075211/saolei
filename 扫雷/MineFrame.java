import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class Lattice {                    //����ÿһ�����Ӷ�Ӧ�����ݣ�-1Ϊ�ף��ո�Ϊ�� ��������Ϊ����
    int length,width,mineNumber;
    String Minecode="-1";//������ף�������Ϊ-1
    String mine[][];//�水ť����
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
    //����
    public void buildMine(){
        Random rand=new Random();
        for(int i=0;i<mineNumber;){
            int l=rand.nextInt(length);//���������ϵ����λ�õ���
            int w=rand.nextInt(width);//�������ϵ����λ�õ���
            //��������λ����û���ף�������λ�ÿɷ�����
            if(mine[l][w].equals(Minecode)!=true){
                mine[l][w]=Minecode;
                i++;
            }

        }
    }
        //�����ܱ��׵�����
    public void otherLattice(){
        //����ĳ��������״���Χ���׵������������֣�
        for(int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                if(mine[i][j]==Minecode)
                continue;
                //�ų����λ����Χ�˸��յ�Խ�����⣬����ͳ������
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
                //�������������Χ����Ϊ0�������0(�ո�)
                if(temp==0)
                mine[i][j]=" ";   
            }
        }
    }
    //����Ϊ�׵�������±�
    public String getMine(int i,int j){
        return mine[i][j];
    }
}

class Click{
    //���1
    //����2
    //δ֪3
    int length;
    int width;
    int[][] flag;            //�洢�ô���ť������ʲô����
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
      //�򿪿ո���Χ�����пո�
    public boolean leftClick(int i,int j,Lattice game1){
        //������ף���flag��Ϊ1������false

        if(game1.mine[i][j].equals("-1")){
            return false;
        }
        //����ǿո�
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
    public int getHighest() throws IOException{       //��ȡ���ʱ��
        if(x==9&&y==9){
                i=1;
        }
         else if(x==16&&y==16){
                i=2;
        }
        else if(x==16&&y==30){
            i=3;
        }   //iֵ�ֱ������и�ģʽ
        String easy="easy.txt";
        String middle="middle.txt";
        String hard="hard.txt";
        File file1 = new File(easy);
        File file2=new File(middle);
        File file3=new File(hard);
        //����ļ������ڣ������ļ�
        if (!file1.exists()) 
            file1.createNewFile();
        if (!file2.exists()) 
            file2.createNewFile();
        if (!file3.exists()) 
            file3.createNewFile();
        String time2="0",time3="0",time1="0";
        int minTime;
        if(i==1){                              //9*9�Ѷ�ʱ
            FileReader fr=new FileReader(file1);
            BufferedReader br=new BufferedReader(fr);
            while(br.ready())
            {
                time1=br.readLine();               //����ԭ������߼�¼
            }
            FileWriter fw=new FileWriter(file1);
            BufferedWriter bw=new BufferedWriter(fw);
            if(time1=="0"){                             //ԭ��Ϊ��ʱ
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else if(Integer.parseInt(time1)>Calendartime){     //�µ�ʱ�����ʱ
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else{                                   //ԭ��ʱ�����ʱ
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
        if(i==2){                                    //16*16�Ѷ�ʱ
            FileReader fr=new FileReader(file2);
            BufferedReader br=new BufferedReader(fr);
            while(br.ready())
            {
                time2=br.readLine();                  //����ԭ������߼�¼
            }
            FileWriter fw=new FileWriter(file2);
            BufferedWriter bw=new BufferedWriter(fw);
            if(time2=="0"){                                  //ԭ��Ϊ��ʱ
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else if(Integer.parseInt(time2)>Calendartime){        //�µ�ʱ�����ʱ
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else{                                       //ԭ��ʱ�����ʱ
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
        if(i==3){                                        //16*30�Ѷ�ʱ
            FileReader fr=new FileReader(file3);
            BufferedReader br=new BufferedReader(fr);
            while(br.ready())
            {
                time3=br.readLine();                      //����ԭ������߼�¼
            }
            FileWriter fw=new FileWriter(file3);
            BufferedWriter bw=new BufferedWriter(fw);
            if(time3=="0"){                               //ԭ��Ϊ��ʱ
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else if(Integer.parseInt(time3)>Calendartime){        //�µ�ʱ�����ʱ
                bw.write(String.valueOf(Calendartime));
                minTime=Calendartime;
            }
            else{                                  //ԭ��ʱ�����ʱ
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
    JMenuBar mb=new JMenuBar();                                         //�˵���
    FgMenu mGame=new FgMenu("��Ϸ(G)",KeyEvent.VK_G);             //����Ϸ���˵�
    FgMenu mHelp=new FgMenu("����(H)",KeyEvent.VK_H);             //���������˵�
    JMenuItem miEasy=new JMenuItem("��(E)",KeyEvent.VK_E);
    JMenuItem miNormal=new JMenuItem("��ͨ(N)",KeyEvent.VK_N);
    JMenuItem miHard=new JMenuItem("����(D)",KeyEvent.VK_D);
    JMenuItem miQuit=new JMenuItem("�˳�(Q)",KeyEvent.VK_Q);
    JMenuItem miRule=new JMenuItem("����(R)",KeyEvent.VK_R);
    JPanel upPane=new JPanel();                  //����ϰ벿�����������
    JPanel downPane=new JPanel();                 //����°벿�����������
    JPanel Pane1=new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));      //�����ʾʱ����������������ʽ����
    JPanel Pane2=new JPanel();                                                      //������¿�ʼ�İ�ť������
    JPanel Pane3=new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));      //�����ʾʣ����������������������ʽ����
    JPanel Panec;                                //����5��panel��ʹɨ�ײ��־����м�
    JPanel Panen=new JPanel();
    JPanel Panes=new JPanel();
    JPanel Panew=new JPanel();
    JPanel Panee=new JPanel();
    JSplitPane sp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,upPane,downPane);      //����ˮƽ�ָ���������Ϊ����������
    ImageIcon[] ic=new ImageIcon[10];
    JLabel time1,time2,time3;              //�����ʾʱ��ͼƬ������
    JButton again;             //Ц����ť
    JButton[][] mineButton;    //ɨ�װ�ť
    JLabel number1,number2;   //���ʣ����������
    int row;
    int cols;
    int mineNumber,flagNumber=10;         //��������������
    int second,temp=0;                   //�����Ϸ���е�ʱ��
    boolean gameRun=true;             //��¼��Ϸ�Ƿ��ڽ��У����ڼ�ʱ�Ŀ�ʼ
    Lattice lattice=new Lattice(9,9,10);         //Ĭ�ϲ���9*9���͵ĵ�����
    Click click=new Click(9,9);       //Ĭ������9*9�ĵ����

    MineFrame(String sTitle){
        super(sTitle);        
        row=9;             //��ʼʱĬ���Ѷ�Ϊ9*9��������Ϊ10
        cols=9;
        mineNumber=10;
        addMenus();        //��Ӳ˵���
        miRule.addActionListener(this);
        miQuit.addActionListener(this);
        Panec=new JPanel();
        addupPane();            //��������
        adddownPane();          //��������
        centerWindows();         //ʹ��������ʾ���о�����ʾ
        Toolkit tk=getToolkit();   //�õ�Toolkit����
        Image icon=tk.getImage("mine.png");     //��ȡͼ��
        setIconImage(icon);     //�ı䴰��ͼ��
        setBomb();            //��ӵ��װ�ť
        lattice.getFirst(row,cols,mineNumber);       //�����Ѷȼ�������
        lattice.buildMine();          //�����������
        lattice.otherLattice();       //����ʣ�ಿ������
        click.getFirst(row,cols);     //���õ�����ڵ��к���
    }

    private void addupPane(){                 //���upPane���ּ����
        Insets space = new Insets(0, 0, 0, 0);    //ָ��������������ÿ�������µĿռ�
        sp.setDividerSize(5);          //���÷ָ���������Ϊ5������
        ic[0]=new ImageIcon("smile.png");
        time1=new JLabel(new ImageIcon("0.png"));      //��ʱ���ʼ��Ϊ000
        time2=new JLabel(new ImageIcon("0.png"));
        time3=new JLabel(new ImageIcon("0.png"));
        again=new JButton(ic[0]);                         //���ð�ťͼƬ
        again.setMargin(space);                           //���ð�ť�߿�ͱ�ǩ֮��Ŀհ׾�,ʹ��ť��ӦͼƬ��С
        number1=new JLabel(new ImageIcon("1.png"));   //����������ʼ��Ϊ00
        number2=new JLabel(new ImageIcon("0.png"));
        upPane.setLayout(new GridLayout(1,3));     //upPane���ַ�ʽΪ���񻯲��֣�1��3�У�
        upPane.add(Pane1);             //������
        upPane.add(Pane2);
        upPane.add(Pane3);
        Pane1.add(time1);
        Pane1.add(time2);
        Pane1.add(time3);
        Pane2.add(again);
        again.addActionListener(new ActionListener(){      //Ц����ť���¼�������ͨ�������ڲ���ʵ�֣�
            public void actionPerformed(ActionEvent e){
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < cols; j++) {
                        mineButton[i][j].setIcon(ic[1]);      //����������Ϸ����ťͼ��
                    }
                }
                lattice.getFirst(row,cols,mineNumber);     //�����Ѷȼ�������
                lattice.buildMine();           //���²������������
                lattice.otherLattice();        //�������ʣ�ಿ������
                click.getFirst(row,cols);      //���õ�����ڵ��к���
                second=0;                   //ʱ������Ϊ0
                temp=0;
                gameRun=true;              //��Ϸ����
                flagNumber=mineNumber;     //�����������ڵ�����
                time1.setIcon(new ImageIcon("0.png"));
                time2.setIcon(new ImageIcon("0.png"));
                time3.setIcon(new ImageIcon("0.png"));
                again.setIcon(new ImageIcon("smile.png"));
                number1.setIcon(new ImageIcon(flagNumber/10+".png"));
                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
            }
        });
        Pane3.add(number1);                    //�����ʾʱ�书�ܵ����
        Pane3.add(number2);
        Pane1.setBackground(Color.GRAY);       //����Pane1������ɫΪ��ɫ
        Pane2.setBackground(Color.GRAY);       //����Pane2������ɫΪ��ɫ
        Pane3.setBackground(Color.GRAY);       //����Pane3������ɫΪ��ɫ
        upPane.setBackground(Color.GRAY);      //����upPane������ɫΪ��ɫ
        Container c=getContentPane();          //��ȡ�������
        c.add(sp);                             //��ӷָ���
        sp.setDividerLocation(0.3);     //����ռ0.3������ռ0.7     
    }

    private void adddownPane(){         //���downPane���ּ����
        downPane.setLayout(new BorderLayout());   //�����µı߿򲼾֣����֮��û�м�϶
        downPane.add("Center",Panec);        //��������ȷ����λ
        downPane.add("North",Panen);
        downPane.add("South",Panes);
        downPane.add("West",Panew);
        downPane.add("East",Panee);
        Panen.setBackground(Color.GRAY);        //����Panen������ɫΪ��ɫ
        Panew.setBackground(Color.GRAY);        //����Panew������ɫΪ��ɫ
        Panee.setBackground(Color.GRAY);        //����Panee������ɫΪ��ɫ
        Panes.setBackground(Color.GRAY);        //����Panes������ɫΪ��ɫ
        downPane.setBackground(Color.GRAY);     //����downPane������ɫΪ��ɫ

    }

    private void addMenus(){
        setJMenuBar(mb);

        mGame.add(miEasy);          //ģʽѡ�񣺼�
        mGame.add(miNormal);        //ģʽѡ����ͨ
        mGame.add(miHard);          //ģʽѡ������
        mGame.add(miQuit);          //�˳�
        mHelp.add(miRule);          //����

        mb.add(mGame);              //������Ϸ���˵���ӵ��˵���
        mb.add(mHelp);              //�����������˵���ӵ��˵���

        miEasy.addActionListener(new ActionListener(){            //ѡ���ģʽʱ������С��Ϊ9*9��ͬʱ��ʱ������
            public void actionPerformed(ActionEvent e){
                if(cols!=9){                  //���ǰ��Ϊ9*9���������м�������������������
                    row=9;
                    cols=9;
                    mineNumber=10;                
                    setBomb();
                }
                else{                //���ǰΪ9*9��������Ϸ����ťͼ��
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < cols; j++) {
                            mineButton[i][j].setIcon(ic[1]);
                        }
                    }
                }
                lattice.getFirst(row,cols,mineNumber);       //���õ�����͵����
                lattice.buildMine();
                lattice.otherLattice();
                click.getFirst(row,cols);
                again.setIcon(new ImageIcon("smile.png"));   //��ťͼ���ʱ����ʾ����
                time1.setIcon(new ImageIcon("0.png"));
                time2.setIcon(new ImageIcon("0.png"));
                time3.setIcon(new ImageIcon("0.png"));
                second=0;
                temp=0;
                gameRun=true;      //��Ϸ����
                flagNumber=mineNumber;
                number1.setIcon(new ImageIcon(flagNumber/10+".png"));    //����ʣ��������
                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
            }
        });
        miNormal.addActionListener(new ActionListener(){              //ѡ����ͨģʽʱ������С��Ϊ16*16��ͬʱ��ʱ������
            public void actionPerformed(ActionEvent e){
                if(cols!=16){               //���ǰ��Ϊ16*16���������м�������������������
                    row=16;
                    cols=16;
                    mineNumber=40;                
                    setBomb();
                }
                else{                     //���ǰΪ16*16��������Ϸ����ťͼ��
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < cols; j++) {
                            mineButton[i][j].setIcon(ic[1]);
                        }
                    }
                }
                lattice.getFirst(row,cols,mineNumber);         //���õ�����͵����
                lattice.buildMine();
                lattice.otherLattice();
                click.getFirst(row,cols);
                again.setIcon(new ImageIcon("smile.png"));   //��ťͼ���ʱ����ʾ����
                time1.setIcon(new ImageIcon("0.png"));
                time2.setIcon(new ImageIcon("0.png"));
                time3.setIcon(new ImageIcon("0.png"));
                second=0;
                temp=0;
                gameRun=true;             //��Ϸ����
                flagNumber=mineNumber;
                number1.setIcon(new ImageIcon(flagNumber/10+".png"));        //����ʣ��������
                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
            }
        });
        miHard.addActionListener(new ActionListener(){               //ѡ������ģʽʱ������С��Ϊ16*30��ͬʱ��ʱ������
            public void actionPerformed(ActionEvent e){
                if(cols!=30){             //���ǰ��Ϊ16*30���������м�������������������
                    row=16;
                    cols=30;
                    mineNumber=99;                
                    setBomb();
                }
                else{                 //���ǰΪ16*30��������Ϸ����ťͼ��
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < cols; j++) {
                            mineButton[i][j].setIcon(ic[1]);
                        }
                    }
                }
                lattice.getFirst(row,cols,mineNumber);       //���õ�����͵����
                lattice.buildMine();
                lattice.otherLattice();
                click.getFirst(row,cols);
                again.setIcon(new ImageIcon("smile.png"));    //��ťͼ���ʱ����ʾ����
                time1.setIcon(new ImageIcon("0.png"));
                time2.setIcon(new ImageIcon("0.png"));
                time3.setIcon(new ImageIcon("0.png"));
                second=0;
                temp=0;
                gameRun=true;             //��Ϸ����
                flagNumber=mineNumber;
                number1.setIcon(new ImageIcon(flagNumber/10+".png"));        //����ʣ��������
                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
            }
        });
    }

    public void setBomb(){                  //������Ϸ����ť
        Panec.removeAll();                 //�Ӵ�������ɾ���������
        Panec.setLayout(new GridLayout(row,cols));         //���񻯲��֣�row��cols�У�
        Panec.setBackground(Color.GRAY);                   //������Ϊ��ɫ
        ic[1]=new ImageIcon("dark.png");
        mineButton = new JButton[row][cols];             //������Ϊ��ɫ
        for (int i = 0; i < row; i++) {                  //����ѭ�����ð�ť��ͼƬ��ʹ��ť��ӦͼƬ��С
            for (int j = 0; j < cols; j++) { 
                mineButton[i][j]=new JButton(ic[1]);
                mineButton[i][j].addActionListener(this);
                JButton btn=new JButton();
                btn=mineButton[i][j];
                btn.addMouseListener(new MouseAdapter(){        //Ϊ��Ϸ����ť����¼�������ͨ�������ڲ���ʵ�֣�
                    int x=0,y=0;
                    public void mouseClicked(MouseEvent e){
                        for(int i1=0;i1<row;i1++){
                            for (int j1= 0; j1< cols; j1++){
                                if(e.getSource()==mineButton[i1][j1]){
                                    x=i1;               //��ȡ����İ�ť���±�
                                    y=j1;
                                }
                            }
                        }
                        if(gameRun==true){                         //��Ϸ������
                            if(e.getButton()==MouseEvent.BUTTON3&&click.getFlag(x,y)==0&&flagNumber>0){     //�һ����Ҹô�δ���в�����������������ʣ��
                                mineButton[x][y].setIcon(new ImageIcon("flag.png"));      //����Ϊflagͼ��
                                click.rightClick(x,y);
                                flagNumber--;                                           //���죬���������ٲ�������ʾ
                                number1.setIcon(new ImageIcon(flagNumber/10+".png"));       
                                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
                            }
                            else if(e.getButton()==MouseEvent.BUTTON3&&click.getFlag(x,y)==2){     //�һ����Σ�ͼ������ı���ʺ�
                                mineButton[x][y].setIcon(new ImageIcon("problem.png"));
                                click.rightClick(x,y);        
                                flagNumber++;                                                 //���������Ӳ�������ʾ
                                number1.setIcon(new ImageIcon(flagNumber/10+".png"));
                                number2.setIcon(new ImageIcon(flagNumber%10+".png"));
                            }
                            else if(e.getButton()==MouseEvent.BUTTON3&&click.getFlag(x,y)==3){       //�һ����Σ�ͼ����ʺű����ͨ��ť
                                mineButton[x][y].setIcon(new ImageIcon("dark.png"));
                                click.rightClick(x,y);
                            }
                        }
                    }
                });
                Panec.add(mineButton[i][j]);            //��Ӱ�ť
            }
        }
        setSize(cols*40,row*40+90);            //����ҳ���С
        setResizable(false);         //ʹҳ���С���ܸı�

    }

    public void count()throws InterruptedException{       //��ʱ���ı�ͼƬ���
        second=0;              //������ʼ��Ϊ0
        while(true)
        {
            second++;           //������1
            time1.setIcon(new ImageIcon(second/100+".png"));       //�ı�ʱ����ʾ
            time2.setIcon(new ImageIcon(second/10%10+".png"));
            time3.setIcon(new ImageIcon(second%10+".png"));
            Thread.sleep(1000);        //���1�����
        } 
    }


    public void centerWindows(){
        Toolkit tk=getToolkit();       //�����ʾ�����洰�ڵĴ�С
        Dimension dm=tk.getScreenSize();
        setLocation((int)(dm.getWidth()-getWidth())/2,(int)(dm.getHeight()-getHeight())/2);     //�ô��ھ�����ʾ
    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource() == miQuit) {                   //�˳�����
            System.exit(0);
        }
        if (e.getSource() == miRule) {                  //������Ϣ����ʾ����
            JOptionPane.showMessageDialog(this,"��Ϸ�Ļ��������������������Left Click�����Ҽ�������Right Click����˫����Chording�����֡�����������ڴ򿪰�ȫ�ĸ��ӣ��ƽ���Ϸ���ȣ�\n�Ҽ����ڱ�ǵ��ף��Ը����жϣ���Ϊ��������˫����׼����˫����һ��������Χ�ĵ��ױ����ʱ���൱�ڶ�������Χδ�򿪵ķ��������һ���������������\n������������жϳ������׵ķ����ϰ�����������Դ򿪸÷��顣��������ϳ������֣�������ֱ�ʾ����Χ3��3�����еĵ�����\n��һ��Ϊ8�����ӣ����ڱ߿�Ϊ5�����ӣ����ڽǿ�Ϊ3�����ӡ�����ɨ������������Ϊ8�������������Ϊ�գ��൱��0��������Եݹ�ش�������ڵķ��飻������Ҵ��ף�����Ϸ������\n�Ҽ����������ж�Ϊ���׵ķ����ϰ����Ҽ������Ա�ǵ��ף���ʾΪС���죩��\n�ظ�һ�λ����β�����ȡ����ǣ��������Ϸ�˵��й�ѡ�� ���(?)������Ҫ���β�����ȡ�����ף���\n˫����ͬʱ����������Ҽ����˫������˫��λ����Χ�ѱ���������ڸ�λ������ʱ������Ч��\n�൱�ڶԸ�������Χδ�򿪵ķ��������һ�������������������δ�����ȫʱʹ��˫����Ч����������Χ�б��ĵ��ף�����Ϸ���������ĵ����ϻ���ʾһ����.","��ʾ",JOptionPane.INFORMATION_MESSAGE);
            miRule.setVisible(true);
        }
        if(gameRun==true){            //��Ϸ������
            for(int i=0;i<row;i++){               //����ѭ���ж����ĸ���ť�����
                for(int j=0;j<cols;j++){
                    if(e.getSource()==mineButton[i][j]){
                        if(lattice.getMine(i,j).equals("-1")&&click.getFlag(i,j)==0){    //�����Ϊ�����Ҹô�δ�������
                            mineButton[i][j].setIcon(new ImageIcon("boom.png"));    //���������ը��
                            for(int i1=0;i1<row;i1++){
                                for(int j1=0;j1<cols;j1++){
                                    if(lattice.getMine(i1,j1).equals("-1")&(i1!=i||j1!=j)){            //���ױ��������ʾ���е���
                                        mineButton[i1][j1].setIcon(new ImageIcon("mine.png"));
                                    }
                                }
                            }
                            again.setIcon(new ImageIcon("cry.png"));        //Ц����ť��ɿ���
                            JOptionPane.showMessageDialog(this,"��ȵ�����\n��Ϸʧ�ܣ�","ʧ��",JOptionPane.INFORMATION_MESSAGE);  //������Ϣ����ʾ��Ϸʧ��
                            gameRun=click.leftClick(i,j,lattice);    //��Ϸ����
                        } 
                        else if(lattice.getMine(i,j).equals(" ")&&click.getFlag(i,j)==0){        //����İ�ť��Ϊ�ո���δ�������
                            mineButton[i][j].setIcon(new ImageIcon("blank.png"));
                            gameRun=click.leftClick(i,j,lattice);             //��Ϸ����
                            for(int i1=0;i1<row;i1++){
                                for(int j1=0;j1<cols;j1++){                                    //�ӿո�ը��ֱ����ԵΪ����
                                    if(lattice.getMine(i1,j1).equals(" ")&&click.getFlag(i1,j1)==1){
                                        mineButton[i1][j1].setIcon(new ImageIcon("blank.png"));
                                    }
                                    else if(!lattice.getMine(i1,j1).equals("-1")&&click.getFlag(i1,j1)==1){
                                        mineButton[i1][j1].setIcon(new ImageIcon(lattice.getMine(i1,j1)+lattice.getMine(i1,j1)+".png"));
                                    }
                                }
                            }
                        }
                        else if(click.getFlag(i,j)==0){     //�ð�ťΪ������δ�������
                            mineButton[i][j].setIcon(new ImageIcon(lattice.getMine(i,j)+lattice.getMine(i,j)+".png"));
                            gameRun=click.leftClick(i,j,lattice);            //��Ϸ����
                        }
                        temp=second;
                    }
                }
            }
        }
        int mn=0;
        for(int i1=0;i1<row;i1++){
            for(int j1=0;j1<cols;j1++){
                if(click.getFlag(i1,j1)==0||click.getFlag(i1,j1)==2||click.getFlag(i1,j1)==3){      //��δ���㿪���ѱ����������Ϊ�ʺŵİ�ť���м���
                    mn++;                    
                }
            }
        } 
        mn--;
        if(mn==mineNumber){     //ʣ�ఴť�����ڵ�����ʱ
            gameRun=false;      //��Ϸ����
            Record record=new Record(temp,row,cols);      //������¼����
            try {
                again.setIcon(new ImageIcon("win.png"));
                JOptionPane.showMessageDialog(this,"��ϲ��ȡ��ʤ��,��������ʱ��Ϊ"+temp+"�룬���Ѷ���߼�¼"+record.getHighest()+"��","�ɹ�",JOptionPane.INFORMATION_MESSAGE);        //������Ϣ����ʾ���ʤ������߼�¼
            }
            catch (HeadlessException e1) {} 
            catch (IOException e1) {}  
        }
    }


    public static void main(String args[])throws InterruptedException,IOException{
        MineFrame frm=new MineFrame("Minesweeper");          //�������ڶ���
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setVisible(true);
        frm.count();
    }
}

class FgMenu extends JMenu{           //�Զ���˵�
    public FgMenu(String label){
        super(label);
    }
    public FgMenu(String label,int nAccelerator){
        super(label);
        setMnemonic(nAccelerator);
    }
}