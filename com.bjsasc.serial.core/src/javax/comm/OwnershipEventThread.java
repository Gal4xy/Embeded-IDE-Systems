/*     */ package javax.comm;
/*     */ 
/*     */ class OwnershipEventThread extends Thread
/*     */ {
/*     */   CommPortIdentifier portId;
/*     */ 
/*     */   OwnershipEventThread(CommPortIdentifier paramCommPortIdentifier)
/*     */   {
/* 797 */     this.portId = paramCommPortIdentifier;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 807 */     while (!this.portId.cpoList.isEmpty())
/*     */     {
/* 809 */       this.portId.ownershipThreadWaiter();
/*     */     }
/*     */ 
/* 812 */     this.portId.oeThread = null;
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.OwnershipEventThread
 * JD-Core Version:    0.6.0
 */