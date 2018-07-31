/*     */ package javax.comm;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ 
/*     */ public class ParallelPortEvent extends EventObject
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*     */   public int eventType;
/*     */   private boolean oldValue;
/*     */   private boolean newValue;
/*     */   public static final int PAR_EV_ERROR = 1;
/*     */   public static final int PAR_EV_BUFFER = 2;
/*     */ 
/*     */   public ParallelPortEvent(ParallelPort paramParallelPort, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  59 */     super(paramParallelPort);
/*     */ 
/*  61 */     this.eventType = paramInt;
/*     */     //modified by songjie at 2012-8-27
/*  63 */     //this.oldValue = this.oldValue;
              //modified by songjie at 2012-8-27
/*  64 */     this.newValue = paramBoolean2;
/*     */   }
/*     */ 
/*     */   public int getEventType()
/*     */   {
/*  85 */     return this.eventType;
/*     */   }
/*     */ 
/*     */   public boolean getNewValue()
/*     */   {
/*  93 */     return this.newValue;
/*     */   }
/*     */ 
/*     */   public boolean getOldValue()
/*     */   {
/* 101 */     return this.oldValue;
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.ParallelPortEvent
 * JD-Core Version:    0.6.0
 */