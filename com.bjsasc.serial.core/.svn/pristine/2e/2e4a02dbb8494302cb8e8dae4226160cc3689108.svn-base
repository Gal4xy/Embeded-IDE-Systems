/*     */ package javax.comm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
import java.io.OutputStream;
/*     */ 
/*     */ public abstract class CommPort
/*     */ {
/*     */   protected String name;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  76 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  85 */     return this.name;
/*     */   }
/*     */ 
/*     */   public abstract InputStream getInputStream()
/*     */     throws IOException;
/*     */ 
/*     */   public abstract OutputStream getOutputStream()
/*     */     throws IOException;
/*     */ 
/*     */   public void close() throws NoSuchPortException, IOException
/*     */   {
/*     */    // try
/*     */    // {
/* 226 */       CommPortIdentifier.getPortIdentifier(this).internalClosePort();
/*     */ 
/* 225 */       return;
/*     */     //}
/*     */     //catch (NoSuchPortException localNoSuchPortException)
/*     */     //{
/*     */     //}
/*     */   }
/*     */ 
/*     */   public abstract void enableReceiveThreshold(int paramInt)
/*     */     throws UnsupportedCommOperationException;
/*     */ 
/*     */   public abstract void disableReceiveThreshold();
/*     */ 
/*     */   public abstract boolean isReceiveThresholdEnabled();
/*     */ 
/*     */   public abstract int getReceiveThreshold();
/*     */ 
/*     */   public abstract void enableReceiveTimeout(int paramInt)
/*     */     throws UnsupportedCommOperationException;
/*     */ 
/*     */   public abstract void disableReceiveTimeout();
/*     */ 
/*     */   public abstract boolean isReceiveTimeoutEnabled();
/*     */ 
/*     */   public abstract int getReceiveTimeout();
/*     */ 
/*     */   public abstract void enableReceiveFraming(int paramInt)
/*     */     throws UnsupportedCommOperationException;
/*     */ 
/*     */   public abstract void disableReceiveFraming();
/*     */ 
/*     */   public abstract boolean isReceiveFramingEnabled();
/*     */ 
/*     */   public abstract int getReceiveFramingByte();
/*     */ 
/*     */   public abstract void setInputBufferSize(int paramInt);
/*     */ 
/*     */   public abstract int getInputBufferSize();
/*     */ 
/*     */   public abstract void setOutputBufferSize(int paramInt);
/*     */ 
/*     */   public abstract int getOutputBufferSize();
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.CommPort
 * JD-Core Version:    0.6.0
 */