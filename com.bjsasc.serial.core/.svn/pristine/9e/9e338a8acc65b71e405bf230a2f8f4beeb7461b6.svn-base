/*    */ package com.sun.comm;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ class Win32SerialInputStream extends InputStream
/*    */ {
/*    */   private Win32SerialPort port;
/*    */ 
/*    */   Win32SerialInputStream(Win32SerialPort paramWin32SerialPort)
/*    */   {
/* 30 */     this.port = paramWin32SerialPort;
/*    */   }
/*    */ 
/*    */   public int read() throws IOException {
/* 34 */     return this.port.read();
/*    */   }
/*    */ 
/*    */   public int read(byte[] paramArrayOfByte) throws IOException {
/* 38 */     return this.port.read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*    */   }
/*    */ 
/*    */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/* 42 */     return this.port.read(paramArrayOfByte, paramInt1, paramInt2);
/*    */   }
/*    */ 
/*    */   public int available() throws IOException {
/* 46 */     return this.port.available();
/*    */   }
/*    */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     com.sun.comm.Win32SerialInputStream
 * JD-Core Version:    0.6.0
 */