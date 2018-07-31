/*     */ package com.sun.comm;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.TooManyListenersException;
/*     */ import javax.comm.CommPort;
import javax.comm.NoSuchPortException;
/*     */ import javax.comm.ParallelPort;
/*     */ import javax.comm.ParallelPortEventListener;
import javax.comm.UnsupportedCommOperationException;
/*     */ 
/*     */ class Win32ParallelPort extends ParallelPort
/*     */ {
/*     */   private File prfile;
/*     */   private OutputStream outstream;
/*  31 */   private boolean closed = false;
/*     */ 
/*     */   Win32ParallelPort(String paramString) throws IOException
/*     */   {
/*  35 */     this.name = paramString;
/*     */     try
/*     */     {
/*  38 */       this.prfile = new File(paramString);
/*     */     } catch (NullPointerException localNullPointerException) {
/*  40 */       throw new IOException(localNullPointerException + " while opening port");
/*     */     }
/*     */ 
/*  43 */     this.outstream = new FileOutputStream(this.prfile);
/*     */   }
/*     */ 
/*     */   public void addEventListener(ParallelPortEventListener paramParallelPortEventListener)
/*     */     throws TooManyListenersException
/*     */   {
/*  49 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public void removeEventListener()
/*     */   {
/*  53 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public void notifyOnError(boolean paramBoolean)
/*     */   {
/*  57 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public void notifyOnBuffer(boolean paramBoolean) {
/*  60 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public int getOutputBufferFree()
/*     */   {
/*  64 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  65 */     return 512;
/*     */   }
/*     */ 
/*     */   public boolean isPaperOut() {
/*  69 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  70 */     return false;
/*     */   }
/*     */   public boolean isPrinterBusy() {
/*  73 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  74 */     return false;
/*     */   }
/*     */   public boolean isPrinterSelected() {
/*  77 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  78 */     return false;
/*     */   }
/*     */   public boolean isPrinterTimedOut() {
/*  81 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  82 */     return false;
/*     */   }
/*     */   public boolean isPrinterError() {
/*  85 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   public void restart() {
/*  90 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public void suspend() {
/*  93 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public int getMode()
/*     */   {
/*  97 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  98 */     return 1;
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream() throws IOException {
/* 102 */     throw new IOException("Unsupported operation. Output only mode");
/*     */   }
/*     */   public OutputStream getOutputStream() throws IOException {
/* 105 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 106 */     return this.outstream;
/*     */   }
/*     */ 
/*     */   public void enableReceiveThreshold(int paramInt) throws UnsupportedCommOperationException
/*     */   {
/* 111 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public void disableReceiveThreshold() {
/* 114 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public boolean isReceiveThresholdEnabled() {
/* 117 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 118 */     return false;
/*     */   }
/*     */   public int getReceiveThreshold() {
/* 121 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 122 */     return 1;
/*     */   }
/*     */ 
/*     */   public void enableReceiveTimeout(int paramInt) throws UnsupportedCommOperationException {
/* 126 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public void disableReceiveTimeout() {
/* 129 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public boolean isReceiveTimeoutEnabled() {
/* 132 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 133 */     return false;
/*     */   }
/*     */   public int getReceiveTimeout() {
/* 136 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 137 */     return 0;
/*     */   }
/*     */ 
/*     */   public void enableReceiveFraming(int paramInt) throws UnsupportedCommOperationException {
/* 141 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public void disableReceiveFraming() {
/* 144 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public int getReceiveFramingByte() {
/* 147 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 148 */     return 0;
/*     */   }
/*     */   public boolean isReceiveFramingEnabled() {
/* 151 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 152 */     return false;
/*     */   }
/*     */ 
/*     */   public void setInputBufferSize(int paramInt) {
/* 156 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public int getInputBufferSize() {
/* 159 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 160 */     return 1024;
/*     */   }
/*     */   public void setOutputBufferSize(int paramInt) {
/* 163 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
/*     */   }
/*     */ 
/*     */   public int getOutputBufferSize() {
/* 166 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 167 */     return 1024;
/*     */   }
/*     */ 
/*     */   public int setMode(int paramInt) throws UnsupportedCommOperationException {
/* 171 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 172 */     if (paramInt != 1) {
/* 173 */       throw new UnsupportedCommOperationException();
/*     */     }
/* 175 */     return 1;
/*     */   }
/*     */ 
/*     */   public void close() throws NoSuchPortException, IOException
/*     */   {
/* 180 */     if (this.closed) throw new IllegalStateException("Port Closed"); 
			  //try
/*     */     {
/* 182 */       this.outstream.close();
/*     */     }// catch (IOException localIOException) {
/*     */     //}
/* 185 */     this.closed = true;
/* 186 */     super.close();
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     com.sun.comm.Win32ParallelPort
 * JD-Core Version:    0.6.0
 */