import java.io.*;
public class ReplaceText {
    public static void alterStringToCreateNewFile( String a, String b,String c,String d) throws IOException{
            File sourceFile=new File(a);
            File targetFile=new File(b);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader (
                            new FileInputStream(sourceFile))); 
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(targetFile,true)));
            String string = null; 
            while ((string = br.readLine()) != null){
                if (string.contains(c)){ 
                    string = new String(
                            string.replace(c,d));
                }
                bw.write(string); 
                bw.newLine(); 
            }
            br.close(); 
            bw.close();
        }
    public static void main(String[] args){
       try{
           alterStringToCreateNewFile(args[0],args[1],args[2],args[3]);
       } 
       catch(Throwable e){
           System.out.println("wrong input");
       }
    }
}

