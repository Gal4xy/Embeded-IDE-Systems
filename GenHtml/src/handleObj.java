import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;


public class handleObj {

	/**
	 * 20180903 16:35 获取第一个字符所在位置是17
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static void indexOfCov() throws IOException{
		List<String> list=new ArrayList<>();
		list=FileUtils.readLines(new File(Constants.ObjPath));
	 
	if(!list.isEmpty()) 
	{
		int index1=list.get(11).indexOf("1");
		int index2=list.get(15).indexOf("1");
		for (String string : list) {
		     System.out.println(string);						
		}
	      System.out.println(index1+list.get(11));	
	      System.out.println(index2+list.get(15));
	}
		
	else {
		System.out.println("CHECK_24_NULL");
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
	public void addSrcContent() throws IOException{
		String path=this.getTemplatePath()+"2.html";
		File html=new File(path);
		//List<String> content=new ArrayList<>();
		PrintStream ps=new PrintStream(new FileOutputStream(html));
		//content=FileUtils.readLines(new File(Constants.ObjPath));
		StringBuilder sb=new StringBuilder();
		
		//sb.append("");
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional\">\r\n" + 
				"<html >\r\n" + 
				"<head>\r\n" + 
				"  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">\r\n" + 
				"  <link rel=\"stylesheet\" type=\"text/css\" href=\"gcov.css\">\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"  <table width=\"100%\" border=0 cellspacing=0 cellpadding=0>\r\n" + 
				"    <tr><td class=\"title\">覆盖率分析报告</td></tr>\r\n" + 
				"    <tr><td class=\"ruler\"><img src=\"../glass.png\" width=3 height=3 alt=\"\"></td></tr>\r\n" + 
				"    <tr>\r\n" + 
				"      <td width=\"100%\">\r\n" + 
				"        <table cellpadding=1 border=0 width=\"100%\">\r\n" + 
				"          <tr>\r\n" + 
				"            <td width=\"10%\" class=\"headerItem\">当前视图:</td>");
		//Top level
		sb.append(" <td width=\"35%\" class=\"headerValue\"><a href=\"1.html\">上一级</a> - <a href=\"1.html\">test</a> - hello.c<span style=\"font-size: 80%;\"> "
				+ "(source / <a href=\"hello.c.func-sort-c.html\">functions</a>)</span></td>");
		//
		
		sb.append("<td width=\"5%\"></td>\r\n" + 
				"            <td width=\"15%\"></td>\r\n" + 
				"            <td width=\"10%\" class=\"headerCovTableHead\">覆盖行数</td>\r\n" + 
				"            <td width=\"10%\" class=\"headerCovTableHead\">指令行数</td>\r\n" + 
				"            <td width=\"15%\" class=\"headerCovTableHead\">覆盖率</td>\r\n" + 
				"          </tr>\r\n" + 
				"          <tr>\r\n" + 
				"            <td class=\"headerItem\">测试:</td>");
		//Test
		sb.append("<td class=\"headerValue\">test_test.info</td>");
		//
		
		//Line
		sb.append("<td></td>\r\n" + 
				"            <td class=\"headerItem\">行数:</td>");
		sb.append("<td class=\"headerCovTableEntry\">4</td>");
		sb.append("<td class=\"headerCovTableEntry\">5</td>");
		sb.append("<td class=\"headerCovTableEntryMed\">80.0 %</td>");
		sb.append("</tr>");
		
		//Date
		sb.append("<tr>\r\n" + 
				"            <td class=\"headerItem\">测试日期:</td>");
		sb.append("<td class=\"headerValue\">2018-09-04 15:46:17</td>");
		sb.append("<td></td>");
		
		//Fragment
		sb.append("<td class=\"headerItem\">分支:</td>");
		sb.append("<td class=\"headerCovTableEntry\">1</td>");
		sb.append("<td class=\"headerCovTableEntry\">1</td>");
		sb.append("<td class=\"headerCovTableEntryHi\">100.0 %</td>");
		sb.append(" </tr>\r\n" + 
				"          <tr><td><img src=\"../glass.png\" width=3 height=3 alt=\"\"></td></tr>\r\n" + 
				"        </table>\r\n" + 
				"      </td>\r\n" + 
				"    </tr>\r\n" + 
				"\r\n" + 
				"    <tr><td class=\"ruler\"><img src=\"../glass.png\" width=3 height=3 alt=\"\"></td></tr>\r\n" + 
				"  </table>");
		
		
 //Table2
		//SRC
		sb.append("<table cellpadding=0 cellspacing=0 border=0>\r\n" + 
				"    <tr>\r\n" + 
				"      <td><br></td>\r\n" + 
				"    </tr>\r\n" + 
				"    <tr>\r\n" + 
				"      <td>\r\n" + 
				"<pre class=\"sourceHeading\">          Line data    Source code</pre>\r\n" + 
				"<pre class=\"source\">\r\n" + 
				"<a name=\"1\"><span class=\"lineNum\">       1 </span>            : #include&lt;stdio.h&gt;</a>\r\n" + 
				"<span class=\"lineNum\">       2 </span>            : \r\n" + 
				"<span class=\"lineNum\">       3 </span><span class=\"lineCov\">          1 : int main(int argc,char* argv[])</span>\r\n" + 
				"<span class=\"lineNum\">       4 </span>            : {\r\n" + 
				"<span class=\"lineNum\">       5 </span><span class=\"lineCov\">          1 :     if(argc&gt;1)</span>\r\n" + 
				"<span class=\"lineNum\">       6 </span><span class=\"lineNoCov\">          0 :        printf(&quot;AAAA\\n&quot;);</span>\r\n" + 
				"<span class=\"lineNum\">       7 </span>            :     else\r\n" + 
				"<span class=\"lineNum\">       8 </span><span class=\"lineCov\">          1 :        printf(&quot;BBB\\n&quot;);</span>\r\n" + 
				"<span class=\"lineNum\">       9 </span><span class=\"lineCov\">          1 :     return 0;</span>\r\n" + 
				"<span class=\"lineNum\">      10 </span>            : }\r\n" + 
				"</pre>\r\n" + 
				"      </td>\r\n" + 
				"    </tr>\r\n" + 
				"  </table>\r\n" + 
				"  <br>");
		
//Table3  	
		
		sb.append("<table width=\"100%\" border=0 cellspacing=0 cellpadding=0>\r\n" + 
				"    <tr><td class=\"ruler\"><img src=\"../glass.png\" width=3 height=3 alt=\"\"></td></tr>\r\n" + 
				"    <tr><td class=\"versionInfo\">Generated by: <a href=\"http://ltp.sourceforge.net/coverage/lcov.php\" target=\"_parent\">Galaxy</a></td></tr>\r\n" + 
				"  </table>\r\n" + 
				"  <br>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>");
	   

		ps.println(sb.toString()); 
		ps.close();
		
	}
	
	
	
	
	
	
	public static void main(String args[]) throws IOException {
	   handleObj h=new handleObj();
	   
		h.getCSS();
		h.addIndexContent();
		h.addSrcContent();
	  // handleObj.indexOfCov();
	   
	}
	
}
