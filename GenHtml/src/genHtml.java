import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class genHtml {

	public static void main(String args[]) throws IOException {
	   
		String[] Args= {"abc"};
		
	/*	try {	    	
			genHtml.genFile(Constants.filepath, 
					Constants.dispath, Constants.filename, Args);
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		//TEST SUITE
		//System.out.println("result "+handleObj.indexOfCov()+" ");
		handleObj.indexOfCov();
	}
	
	
	/**
	 * 20180903 14:56
	 * 
	 * @param filepath
	 * @param dispath
	 * @param filename
	 * @param Args
	 */
	
	public static void genFile(String filepath,String dispath,String filename,String[] Args) throws IOException{
		
		FileInputStream fs=new FileInputStream(new File(filepath));
		int length=fs.available();
		byte bytes[]=new byte[length];
		fs.read(bytes);
		fs.close();
		
		String tempContent=new String(bytes);
		tempContent=tempContent.replaceAll("###abc###","abc");
		System.out.println(tempContent);
		
		String fileName=filename+".html";
		String filePath=dispath+File.separator+fileName;
		System.out.println("CHECK_34_genHtml_filePath "+filePath);
		FileOutputStream fo=new FileOutputStream(new File(filePath));
		byte byte_out[]=tempContent.getBytes();
		fo.write(byte_out);
		fo.close();
		
	}
}
