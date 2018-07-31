/*     */ package javax.comm;
/*     */ 
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ class CommPortEnumerator
/*     */   implements Enumeration
/*     */ {
/*     */   private CommPortIdentifier curEntry;
/*     */ 
/*     */   public Object nextElement()
/*     */   {
/* 757 */     synchronized (CommPortIdentifier.lock) {
/* 758 */       if (this.curEntry != null)
/* 759 */         this.curEntry = this.curEntry.next;
/*     */       else {
/* 761 */         this.curEntry = CommPortIdentifier.masterIdList;
/*     */       }
/*     */     }
/* 764 */     return this.curEntry;
/*     */   }
/*     */ 
/*     */   public boolean hasMoreElements()
/*     */   {
/* 773 */     synchronized (CommPortIdentifier.lock)
/*     */     {
/*     */       int i;
/* 774 */       if (this.curEntry != null) {
/* 775 */         i = this.curEntry.next == null ? 0 : 1; 
/*     */       }
/* 777 */       return CommPortIdentifier.masterIdList == null ? false :true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.CommPortEnumerator
 * JD-Core Version:    0.6.0
 */