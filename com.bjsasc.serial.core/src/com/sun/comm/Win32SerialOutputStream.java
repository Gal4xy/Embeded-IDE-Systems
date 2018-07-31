/*    */ package com.sun.comm;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ class Win32SerialOutputStream extends OutputStream
/*    */ {
/*    */   private Win32SerialPort port;
/*    */ 
/*    */   Win32SerialOutputStream(Win32SerialPort paramWin32SerialPort)
/*    */   {
/* 30 */     this.port = paramWin32SerialPort;
/*    */   }
/*    */ 
/*    */   public synchronized void write(int paramInt) throws IOException {
/* 34 */     this.port.write(paramInt);
/*    */   }
/*    */ 
/*    */   public synchronized void write(byte[] paramArrayOfByte) throws IOException {
/* 38 */     this.port.write(paramArrayOfByte, 0, paramArrayOfByte.length);
/*    */   }
/*    */ 
/*    */   public synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*    */   {
/* 43 */     this.port.write(paramArrayOfByte, paramInt1, paramInt2);
/*    */   }
/*    */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     com.sun.comm.Win32SerialOutputStream
 * JD-Core Version:    0.6.0
 */