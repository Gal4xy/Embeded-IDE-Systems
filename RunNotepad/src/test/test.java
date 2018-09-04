package test;
 

import java.io.IOException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

 

/**

 * Java调用Windows命令测试

 * @author liuyazhuang

 *

 */

public class test {

	/*static {
		System.load("C:\\Users\\Galaxy\\Desktop\\simtest\\testcase\\msvcr100d.dll");
		System.load("C:\\Users\\Galaxy\\Desktop\\simtest\\testcase\\mcs51_sim.dll");
	}*/
	
	
	public static void main(String args[]) {
        
		//String s1="C:/Users/Galaxy/Desktop/runtime-EclipseApplication(1)/test3/temp_data/";
		//String s2="C:/Users/Galaxy/Desktop/runtime-EclipseApplication(1)/test3/cov_data/";
	//	String s3="C:\Users\Galaxy\Desktop\runtime-EclipseApplication(1)\test3\\TIME.C|C:\Users\Galaxy\Desktop\runtime-EclipseApplication(1)\test3\WHETS.C";
		//String s4="C:\Users\Galaxy\Desktop\runtime-EclipseApplication(1)\test3\Whets";
		
		
        testWinCmd();
       
       // dirOpt();

    }

 

    public static void testWinCmd() {

        System.out.println("------------------testWinCmd()--------------------");

        Runtime runtime = Runtime.getRuntime();

        System.out.println(runtime.totalMemory());

        System.out.println(runtime.freeMemory());

        System.out.println(runtime.maxMemory());

        System.out.println(runtime.availableProcessors());   //处理器数

        try {

        	List<String> cmdLine = new ArrayList<String>();
            //执行系统命令

          
          
           // runtime.exec("cmd /c mkdir C:\\test456");

        //String s0="C:\\Users\\Galaxy\\Desktop\\001.bat";
        String s0="C:\\Users\\Galaxy\\Desktop\\simtest\\testcase\\sim8051.exe";
        String s1=".\\\\tmp_data\\\\";
        String s2=".\\\\cov_data\\\\";
        String s3=".\\\\case_data\\\\WHETS.C|.\\\\case_data\\\\TIME.C";
        String s4=".\\\\case_data\\\\Whets";
        String filepath="C:\\Users\\Galaxy\\Desktop\\simtest\\testcase\\";
        
       cmdLine.add(s0);
       cmdLine.add(s1);
       cmdLine.add(s2);
       cmdLine.add(s3);
       cmdLine.add(s4);
       
       String[] cmds= {};
		cmds=cmdLine.toArray(cmds);
       
		for(int i=0;i<cmds.length;i++) {
			System.out.println(" argv "+i+cmds[i]);
		}
		
		
		
		//Process p=runtime.exec(cmds);
		Process p=runtime.exec(cmds, null, new File(filepath));
		
		InputStream fis=p.getInputStream();    
        //用一个读输出流类去读    
         InputStreamReader isr=new InputStreamReader(fis);    
        //用缓冲器读行    
         BufferedReader br=new BufferedReader(isr);    
         String line=null;    
        //直到读完为止    
        while((line=br.readLine())!=null)    
         {    
             System.out.println(line);    
         }    


        } catch (IOException e) {

            e.printStackTrace();

        }

    } 

  

}
