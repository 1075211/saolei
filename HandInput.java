import java.util.Scanner;
import java.io.*;
public class HandInput {
    public static void main(String[] args) throws Exception{
        try (Scanner scan = new Scanner(System.in)) {
            String filename = null;
            String filecont = null; 
            System.out.println("输入指定的文件：");
            if(scan.hasNext()){
                filename = scan.next();
                File file = new File(filename);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("文件已存在，请重新指定保存的文件！");
                    System.exit(0);
                }
                System.out.print("请输入文件内容：");
                PrintStream out = new PrintStream(new FileOutputStream(file));
                while(scan.hasNext()){
                    filecont = scan.next();
                    if(filecont.equals("end#")){
                        System.exit(0);
                    }
                    out.println(filecont);
                }
                out.close();    
            }
        }
    }
}
