/*    */ package com.sun.comm;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Enumeration;
/*    */ import java.util.Vector;
/*    */ import javax.comm.CommDriver;
/*    */ import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
/*    */ 
/*    */ public class Win32Driver
/*    */   implements CommDriver
/*    */ {
/*    */   public void initialize()
/*    */   {
/*    */     try
/*    */     {
/* 33 */       System.loadLibrary("win32com");
/*    */     } catch (SecurityException localSecurityException) {
/* 35 */       System.err.println("Security Exception win32com: " + localSecurityException);
/* 36 */       return;
/*    */     } catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {
/* 38 */       System.err.println("Error loading win32com: " + localUnsatisfiedLinkError);
/* 39 */       return;
/*    */     }
/*    */ 
/* 42 */     Vector localVector = new Vector();
/* 43 */     readRegistrySerial(localVector);
/* 44 */     Enumeration localEnumeration1 = localVector.elements();
/* 45 */     while (localEnumeration1.hasMoreElements()) {
/* 46 */       String localObject = (String)localEnumeration1.nextElement();
/* 47 */       CommPortIdentifier.addPortName((String)localObject, 
/* 48 */         1, this);
/*    */     }
/*    */ 
/* 51 */     Object localObject = new Vector();
/* 52 */     readRegistryParallel((Vector)localObject);
/* 53 */     Enumeration localEnumeration2 = ((Vector)localObject).elements();
/* 54 */     while (localEnumeration2.hasMoreElements()) {
/* 55 */       String str = (String)localEnumeration2.nextElement();
/* 56 */       CommPortIdentifier.addPortName(str, 
/* 57 */         2, this);
/*    */     }
/*    */   }
/*    */ 
/*    */   public CommPort getCommPort(String paramString, int paramInt)
/*    */   {
/* 64 */     Object localObject = null;
/*    */     try
/*    */     {
/* 67 */       switch (paramInt) {
/*    */       case 1:
/* 69 */         localObject = new Win32SerialPort(paramString);
/* 70 */         break;
/*    */       case 2:
/* 72 */         localObject = new Win32ParallelPort(paramString);
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (IOException localIOException)
/*    */     {
/*    */     }
/*    */ 
/* 80 */     return (CommPort)localObject;
/*    */   }
/*    */ 
/*    */   private static native int readRegistrySerial(Vector paramVector);
/*    */ 
/*    */   private static native int readRegistryParallel(Vector paramVector);
/*    */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     com.sun.comm.Win32Driver
 * JD-Core Version:    0.6.0
 */