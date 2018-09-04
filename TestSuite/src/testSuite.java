import java.io.File;
import java.io.IOException;

public class testSuite {

	public static void main(String[] args) throws IOException {
		testSuite ts=new testSuite();
		ts.createFileSystem("Mytest");
	}
	
	
	public void createFileSystem(String PrjName) {
		String s=System.getProperty("user.home");
		String filepath=s+File.separator+"IDE_WORKSPACE"+
		File.separator+PrjName;
		System.out.println("CHECK_11"+filepath);
		
		File file=new File(filepath);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
}
