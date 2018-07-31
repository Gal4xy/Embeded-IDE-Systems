/*     */ package javax.comm;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ 
/*     */ public class SerialPortEvent extends EventObject
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*     */   public int eventType;
/*     */   private boolean oldValue;
/*     */   private boolean newValue;
/*     */   public static final int DATA_AVAILABLE = 1;
/*     */   public static final int OUTPUT_BUFFER_EMPTY = 2;
/*     */   public static final int CTS = 3;
/*     */   public static final int DSR = 4;
/*     */   public static final int RI = 5;
/*     */   public static final int CD = 6;
/*     */   public static final int OE = 7;
/*     */   public static final int PE = 8;
/*     */   public static final int FE = 9;
/*     */   public static final int BI = 10;
/*     */ 
/*     */   public SerialPortEvent(SerialPort paramSerialPort, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  58 */     super(paramSerialPort);
/*     */ 
/*  60 */     this.eventType = paramInt;
/*     */     //modified by songjie at 2012-8-27
/*  62 */     //this.oldValue = this.oldValue;
              //modified by songjie at 2012-8-27
/*  63 */     this.newValue = paramBoolean2;
/*     */   }
/*     */ 
/*     */   public int getEventType()
/*     */   {
/* 138 */     return this.eventType;
/*     */   }
/*     */ 
/*     */   public boolean getNewValue()
/*     */   {
/* 148 */     return this.newValue;
/*     */   }
/*     */ 
/*     */   public boolean getOldValue()
/*     */   {
/* 158 */     return this.oldValue;
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.SerialPortEvent
 * JD-Core Version:    0.6.0
 */