import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;



public class handleObj {

	/**
	 * 20180905 14：54 读取目标码文件并上色
	 * 
	 * @param sb  字符缓存区
	 * @param filepath 目标码文件路径
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void ColoringOB(StringBuilder sb,String filepath) throws IOException{
		
		List<String> list=new ArrayList<>();
		list=FileUtils.readLines(new File(filepath));
		File file=new File(filepath.trim());
		String fileName=file.getName();
		
	if(!list.isEmpty()) 
	{   
		sb.append("<tr Bgcolor=#1E90FF><td>");
		sb.append(fileName);
		sb.append("</td></tr>");
		
		for (int i = 4; i < list.size(); i++) {
			String[] s=list.get(i).split("\\s+");
			
			
			String Blank="&nbsp;&nbsp;&nbsp;&nbsp;";
			int run=Integer.parseInt(s[2]);
			 
		if(s.length>3) {
			if(s.length==5) {
				  if(run>0) {
					 sb.append("<tr Bgcolor=#00FF99>");
					 sb.append("<td>"+s[0].substring(1)+"</td>");
					 sb.append("<td>"+s[1]+"</td>");
					 sb.append("<td>"+s[4]+"</td>"+"</tr>");
					 
				  }
				  else {
					  sb.append("<tr Bgcolor=#FF6666>");
					  sb.append("<td>"+s[0].substring(1)+"</td>");
					  sb.append("<td>"+s[1]+"</td>");
				      sb.append("<td>"+s[4]+"</td>"+"</tr>");
				  }
			  }
			  
			  if(s.length==6) {
				  if(run>0) {
					 sb.append("<tr Bgcolor=#00FF99>");
					 sb.append("<td>"+s[0].substring(1)+"</td>");
					 sb.append("<td>"+s[1]+"</td>");
					 sb.append("<td>"+s[4]+" "+s[5]+"</td>"+"</tr>");
					 
				  }
				  else {
					  sb.append("<tr Bgcolor=#FF6666>");
					  sb.append("<td>"+s[0].substring(1)+"</td>");
					  sb.append("<td>"+s[1]+"</td>");
					  sb.append("<td>"+s[4]+" "+s[5]+"</td>"+"</tr>");
				  }
			  }
		}	  
			  else {
				  sb.append("<tr Bgcolor=#FFD700><td>");
					 sb.append(list.get(i));
					 sb.append("</td></tr>");
			  }
		}

	}
		
	else {
		System.out.println("CHECK_24_NULL");
	}
	
	}
	
	/**
	 * 20180905 16:29 读取.c源代码文件并上色
	 * 
	 * @param sb  字符缓存区
	 * @param Srcfilepath  源代码文件路径
	 * @throws IOException
	 */
	public void ColoringSRC(StringBuilder sb,String Srcfilepath,Map<Integer,Integer> SrcCovList) throws IOException{
		
		List<String> list=new ArrayList<>();
		list=FileUtils.readLines(new File(Srcfilepath));
		
		File file=new File(Srcfilepath.trim());
		String fileName=file.getName();
		
		
		if(!list.isEmpty()) {
			
			sb.append("<tr Bgcolor=#1E90FF><td>");
			sb.append(fileName);
			sb.append("</td></tr>");
			
		 for (int i = 0; i < list.size(); i++) {
			if(SrcCovList.get(i)!=null) {
				if(SrcCovList.get(i)>0) {
					sb.append("<tr Bgcolor=#00FF99>");
					sb.append("<td>"+i+"</td>");
					sb.append("<td>"+list.get(i)+"</td>"+"</tr>");
					
				}
				else {
					sb.append("<tr Bgcolor=#FF6666>");
					sb.append("<td>"+i+"</td>");
					sb.append("<td>"+list.get(i)+"</td>"+"</tr>");
				}
				
			}
			else {
				sb.append("<tr Bgcolor=#F8F8FF>");
				sb.append("<td>"+i+"</td>");
				sb.append("<td>"+list.get(i)+"</td>"+"</tr>");
			}
		}
			
		}
		else {
			System.out.println("CHECK_HandleObj_List_Null");
		}
	}
	
	
	public String getTemplatePath() throws IOException{
		String path=System.getProperty("user.dir")+System.getProperty("file.separator");
        return path;
	}
	
	
	/**
	 * 2018 0904 拼接
	 * 
	 * "<link rel=\"stylesheet\" type=\"text/css\" href=\"../gcov.css\">"
	 * @return
	 * @throws IOException 
	 */
	
	public String getCSS() throws IOException {
		String s1="<link rel=\\\"stylesheet\\\" type=\\\"text/css\\\"";
		String s3=this.getTemplatePath();
		String s2=" href=\\\""+s3+"\\\">";
		String css=s1+s2;
		System.out.println(css);
		return css;
	}
	
	
	
  /**
   * 20180904 15:17 完成INDEX
   *  sb.append("");
   * @throws IOException
   */
	public void addIndexContent() throws IOException{
		String path=this.getTemplatePath()+"1.html";
		File html=new File(path);
		//List<String> content=new ArrayList<>();
		PrintStream ps=new PrintStream(new FileOutputStream(html));
		//content=FileUtils.readLines(new File(Constants.ObjPath));
		StringBuilder sb=new StringBuilder();
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//\">\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">\r\n" + 
				"  \r\n" + 
				"  <link rel=\"stylesheet\" type=\"text/css\" href=\"gcov.css\">\r\n" + 
				"\r\n" + 
				"</head>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"<body>\r\n" + 
				"  <table width=\"100%\" border=0 cellspacing=0 cellpadding=0>");
		//Title
		sb.append("<tr><td class=\"title\">覆盖率分析报告</td></tr>");
		//
		sb.append("<tr><td class=\"ruler\"><hr style=\"height: 1.5px\" /></td></tr>\r\n" + 
				"    <tr>\r\n" + 
				"      <td width=\"100%\">\r\n" + 
				"        <table cellpadding=1 border=0 width=\"100%\">\r\n" + 
				"          <tr>\r\n" + 
				"            <td width=\"10%\" class=\"headerItem\">当前视图:</td>");
		//Top Level
		sb.append("<td width=\"35%\" class=\"headerValue\"><a href=\"../index.html\">上一级</a> - test</td>");
		//
		
		sb.append("<td width=\"5%\"></td>\r\n" + 
				"            <td width=\"15%\"></td>\r\n" + 
				"            <td width=\"10%\" class=\"headerCovTableHead\">覆盖行数</td>\r\n" + 
				"            <td width=\"10%\" class=\"headerCovTableHead\">指令行数</td>\r\n" + 
				"            <td width=\"15%\" class=\"headerCovTableHead\">覆盖率</td>\r\n" + 
				"          </tr>\r\n" + 
				"          <tr>\r\n" + 
				"            <td class=\"headerItem\">项目名:</td>");
		
		//Test Name
		sb.append("<td class=\"headerValue\">test_test.info</td>");
		//
		
		sb.append("<td></td>\r\n" + 
				"            <td class=\"headerItem\">行数:</td>");
		
		//Line
		sb.append("<td class=\"headerCovTableEntry\">15</td>");
		sb.append("<td class=\"headerCovTableEntry\">24</td>");
		sb.append("<td class=\"headerCovTableEntryMed\">62.5 %</td>");
		//
		
		sb.append(" </tr>\r\n" + 
				"          <tr>\r\n" + 
				"            <td class=\"headerItem\">测试日期:</td>");
		
		//Date
		sb.append("<td class=\"headerValue\">2018-09-04 15:40:17</td>");
		//
		
		sb.append("<td></td>\r\n" + 
				"            <td class=\"headerItem\">分支:</td>");
		
		//Fragment
		sb.append("<td class=\"headerCovTableEntry\">2</td>");
		sb.append("<td class=\"headerCovTableEntry\">2</td>");
		sb.append("<td class=\"headerCovTableEntryHi\">100.0 %</td>");
		sb.append(" </tr>\r\n" + 
				"        </table>\r\n" + 
				"      </td>\r\n" + 
				"    </tr> \r\n" + 
				"      <tr><td width=\"100%\" class=\"ruler\"><hr style=\"height: 1.5px\" /></td></tr>\r\n" + 
				"  </table>");
		
		
		
		
	   sb.append("<center>\r\n" + 
	   		"  <table width=\"80%\" cellpadding=1 cellspacing=1 border=0>\r\n" + 
	   		"\r\n" + 
	   		"    <tr>\r\n" + 
	   		"      <td width=\"50%\"><br></td>\r\n" + 
	   		"      <td width=\"10%\"></td>\r\n" + 
	   		"      <td width=\"10%\"></td>\r\n" + 
	   		"      <td width=\"10%\"></td>\r\n" + 
	   		"      <td width=\"10%\"></td>\r\n" + 
	   		"      <td width=\"10%\"></td>\r\n" + 
	   		"    </tr>\r\n" + 
	   		"     \r\n" + 
	   		"     <tr>\r\n" + 
	   		"      <td class=\"tableHead\">项目名称 <span class=\"tableHeadSort\"></span></td>\r\n" + 
	   		"      <td class=\"tableHead\" colspan=3>指令覆盖率 <span class=\"tableHeadSort\"><a href=\"index-sort-l.html\"></a></span></td>\r\n" + 
	   		"      <td class=\"tableHead\" colspan=2>分支覆盖率 <span class=\"tableHeadSort\"><a href=\"index-sort-f.html\"></a></span></td>\r\n" + 
	   		"    </tr>");
	   
	   //File1
	   sb.append("<tr>");
	   sb.append("<td class=\"coverFile\"><a href=\"2.html\">WHETS.c</a></td>");
	   sb.append("<td class=\"coverBar\" align=\"left\">");
	   //
	   sb.append("</td>");
	   sb.append("<td class=\"coverPerMed\">80.0&nbsp;%</td>");
	   sb.append("<td class=\"coverNumMed\">4 / 5</td>");
	   sb.append("<td class=\"coverPerHi\">100.0&nbsp;%</td>");
	   sb.append("<td class=\"coverNumHi\">1 / 1</td>");
	 
	  //
	   
	   //File2
	   sb.append("<tr>");
	   sb.append("<td class=\"coverFile\"><a href=\"2.html\">TIME.c</a></td>");
	   sb.append("<td class=\"coverBar\" align=\"left\">");
	   //
	   sb.append("</td>");
	   sb.append("<td class=\"coverPerLo\">50.0&nbsp;%</td>");
	   sb.append("<td class=\"coverNumLo\">4 / 8</td>");
	   sb.append("<td class=\"coverPerMed\">70.0&nbsp;%</td>");
	   sb.append("<td class=\"coverNumMed\">7 / 10</td>");
	  //
	   
	 
	   
	   
	   
	   
	   sb.append("</table>\r\n" + 
	   		"  <hr width=\"100%\" style=\"height: 1.5px\" />\r\n" + 
	   		"  </center>");
		  
		  
		  //sb.append("");
				
		
		
		
		
		
		sb.append(" <table width=\"100%\" border=0 cellspacing=0 cellpadding=0>\r\n"  + 
				"    <tr><td class=\"versionInfo\">Generated by: <a href=\"http://ltp.sourceforge.net/coverage/lcov.php\">Galaxy</a></td></tr>\r\n" + 
				"  </table>\r\n" + 
				"  <br>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"</html> \r\n" + 
				"");
		
		
		
		ps.println(sb.toString()); 
		ps.close();
		
		
		
		
	}
	
	
	/**
	 * 20180903  15:24 Source 
	 * @throws IOException
	 */
	public void addSrcContent(String Srcfilepath,String Cfilepath) throws IOException{
		String path=this.getTemplatePath()+"1.html";
		File html=new File(path);
		PrintStream ps=new PrintStream(new FileOutputStream(html));
		StringBuilder sb=new StringBuilder();
		
		Map<Integer,Integer> SrcCovList=this.getMap(Srcfilepath);
	    //Start
		sb.append("<!DOCTYPE HTML PUBLIC>\r\n" + 
				"<head>\r\n" + 
				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<h1 align=\"center\">源代码测试覆盖详细信息</h1>\r\n" + 
				"<br>\r\n" + 
				"<table align=\"center\" border=0 width=70%>\r\n" + 
				"	<tr Bgcolor=#F0F8FF>\r\n" + 
				"	<td width=\"5%\">行号</td>\r\n" + 
				"	<td>代码</td>\r\n" + 
				"    </tr>\r\n" + 
				"");
		
		this.ColoringSRC(sb, Cfilepath, SrcCovList);
		
		//End
		sb.append("</td></tr>\r\n" + 
				"\r\n" + 
				"</table>\r\n" + 
				"<br>\r\n" + 
				"</body>\r\n" + 
				"</html>");

		ps.println(sb.toString()); 
		ps.close();
		
	}
	
	
	
	/**
	 * 20180905 10:44  Object
	 * @throws IOException
	 */
	public void addObjContent(List<String> Paths) throws IOException {
		String path=this.getTemplatePath()+"2.html";
		File html=new File(path);
		//List<String> content=new ArrayList<>();
		PrintStream ps=new PrintStream(new FileOutputStream(html));
		//content=FileUtils.readLines(new File(Constants.ObjPath));
		StringBuilder sb=new StringBuilder();
	   
		//Start
		sb.append("<!DOCTYPE HTML PUBLIC>\r\n" + 
				"<head>\r\n" + 
				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<h1 align=\"center\">目标码测试覆盖详细信息</h1>\r\n" + 
				"<br>\r\n" + 
				"<table align=\"center\" border=0 width=70%>\r\n" + 
				"<tr Bgcolor=#F0F8FF>\r\n" + 
				"	<td width=\"10%\">PC地址</td>\r\n" + 
				"	<td>指令码</td>\r\n" + 
				"	<td>反汇编</td>\r\n" + 
				"</tr>");
		
		//
		for (int i = 0; i < Paths.size(); i++) {
			this.ColoringOB(sb, Paths.get(i));
		}
		
		
        //End		
		sb.append("</table>\r\n" + 
				"<br>\r\n" + 
				"</body>\r\n" + 
				"</html>");
		
	
		ps.println(sb.toString());
		ps.close();
	}
	
	
	
	/**
	 * 20180905  16：43 根据SRCCOV获得键值对 key=行号 Value=执行次数 
	 * 
	 * @param filepath SRCCOV文件地址
	 * @return
	 * @throws IOException
	 */
	public Map<Integer,Integer> getMap(String filepath) throws IOException {
		Map<Integer,Integer> SrcCovList=new HashMap<>();
		List<String> list=new ArrayList<>();
		list=FileUtils.readLines(new File(filepath));
		
		for (int i = 0; i < list.size(); i++) {
			String[] s=list.get(i).split("\\*");
			  if(s.length==3) {
				int LineNum=Integer.parseInt(s[0]);
				int ExecNum=Integer.parseInt(s[2]);
				SrcCovList.put(LineNum,ExecNum);}
			  if(s.length==2) {
				  int LineNum=Integer.parseInt(s[0].substring(1));
				  int ExecNum=Integer.parseInt(s[1]);
				  SrcCovList.put(LineNum,ExecNum);}
			  
		}
		
		return SrcCovList;
		
	}
	
	
	public static void main(String args[]) throws IOException {
	   
		StringBuilder sb=new StringBuilder();
		String objpath1=Constants.ObjPath1;
		String objpath2=Constants.ObjPath2;
		String srcpath1=Constants.srcPath1;
		String srcpath2=Constants.srcPath2;
		String Cpath1=Constants.CPath1;
		
		List<String> OPaths=new ArrayList<>();
		OPaths.add(objpath1);
		OPaths.add(objpath2);
		
		handleObj h=new handleObj();
	   
		//h.addObjContent(Paths);
		h.addSrcContent(srcpath1,Cpath1);
		//h.getMap(srcpath1);
	}
	
}
