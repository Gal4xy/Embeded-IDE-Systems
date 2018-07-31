/*     */ package javax.comm;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ class CpoList
/*     */ {
/*     */   CpoListEntry listHead;
/*     */ 
/*     */   synchronized void add(CommPortOwnershipListener paramCommPortOwnershipListener)
/*     */   {
/* 835 */     CpoListEntry localCpoListEntry1 = this.listHead;
/* 836 */     while (localCpoListEntry1 != null) {
/* 837 */       if (localCpoListEntry1.listener == paramCommPortOwnershipListener) {
/* 838 */         return;
/*     */       }
/* 840 */       localCpoListEntry1 = localCpoListEntry1.next;
/*     */     }
/*     */ 
/* 843 */     CpoListEntry localCpoListEntry2 = new CpoListEntry(paramCommPortOwnershipListener);
/* 844 */     localCpoListEntry2.next = this.listHead;
/* 845 */     this.listHead = localCpoListEntry2;
/*     */   }
/*     */ 
/*     */   synchronized void remove(CommPortOwnershipListener paramCommPortOwnershipListener)
/*     */   {
/* 851 */     Object localObject = null;
/* 852 */     CpoListEntry localCpoListEntry = this.listHead;
/* 853 */     while (localCpoListEntry != null) {
/* 854 */       if (localCpoListEntry.listener == paramCommPortOwnershipListener) {
/* 855 */         if (localObject != null)
/* 856 */           ((CpoListEntry)localObject).next = localCpoListEntry.next;
/*     */         else
/* 858 */           this.listHead = localCpoListEntry.next;
/* 859 */         localCpoListEntry.listener = null;
/* 860 */         localCpoListEntry.next = null;
/* 861 */         return;
/*     */       }
/* 863 */       localObject = localCpoListEntry;
/* 864 */       localCpoListEntry = localCpoListEntry.next;
/*     */     }
/*     */   }
/*     */ 
/*     */   synchronized CpoList clonelist() {
/* 869 */     Object localObject = null;
/* 870 */     CpoListEntry localCpoListEntry1 = null;
/*     */ 
/* 872 */     CpoListEntry localCpoListEntry2 = this.listHead;
/*     */ 
/* 874 */     while (localCpoListEntry2 != null)
/*     */     {
/* 876 */       localCpoListEntry1 = new CpoListEntry(localCpoListEntry2.listener);
/* 877 */       localCpoListEntry1.next = (CpoListEntry) localObject;
/* 878 */       localObject = localCpoListEntry1;
/*     */ 
/* 880 */       localCpoListEntry2 = localCpoListEntry2.next;
/*     */     }
/*     */ 
/* 883 */     CpoList localCpoList = new CpoList();
/* 884 */     localCpoList.listHead = (CpoListEntry) localObject;
/* 885 */     return localCpoList;
/*     */   }
/*     */ 
/*     */   synchronized boolean isEmpty() {
/* 889 */     return this.listHead == null;
/*     */   }
/*     */ 
/*     */   synchronized void fireOwnershipEvent(int paramInt) {
/* 893 */     CpoListEntry localCpoListEntry = this.listHead;
/* 894 */     while (localCpoListEntry != null)
/*     */     {
/* 902 */       localCpoListEntry.listener.ownershipChange(paramInt);
/* 903 */       localCpoListEntry = localCpoListEntry.next;
/*     */     }
/*     */   }
/*     */ 
/*     */   synchronized void dump() {
/* 908 */     CpoListEntry localCpoListEntry = this.listHead;
/* 909 */     while (localCpoListEntry != null) {
/* 910 */       System.err.println("    CpoListEntry - " + localCpoListEntry.listener.toString());
/* 911 */       localCpoListEntry = localCpoListEntry.next;
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.CpoList
 * JD-Core Version:    0.6.0
 */