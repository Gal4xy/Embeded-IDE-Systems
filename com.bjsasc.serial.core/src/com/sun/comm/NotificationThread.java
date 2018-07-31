/*     */ package com.sun.comm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ class NotificationThread extends Thread
/*     */ {
/*     */   Win32SerialPort port;
/* 805 */   private static int EV_RXCHAR = 1;
/* 806 */   private static int EV_RXFLAG = 2;
/* 807 */   private static int EV_TXEMPTY = 4;
/* 808 */   private static int EV_CTS = 8;
/* 809 */   private static int EV_DSR = 16;
/* 810 */   private static int EV_RLSD = 32;
/* 811 */   private static int EV_BREAK = 64;
/* 812 */   private static int EV_ERR = 128;
/* 813 */   private static int EV_RING = 256;
/*     */ 
/* 815 */   private static int CE_OVERRUN = 2;
/* 816 */   private static int CE_RXPARITY = 4;
/* 817 */   private static int CE_FRAME = 8;
/* 818 */   private static int CE_BREAK = 16;
/*     */ 
/*     */   NotificationThread(String paramString, Win32SerialPort paramWin32SerialPort)
/*     */   {
/* 800 */     super(paramString);
/* 801 */     this.port = paramWin32SerialPort;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     while (true)
/*     */     {
/* 823 */       if ((this.port.closed) || (this.port.eventListener == null))
/*     */       {
/* 825 */         this.port.notificationThread = null;
/* 826 */         return;
/*     */       }
/*     */ 
/* 829 */       int i = this.port.waitForEvent();
/*     */ 
/* 831 */       if ((this.port.closed) || (this.port.eventListener == null))
/*     */       {
/* 833 */         this.port.notificationThread = null;
/* 834 */         return;
/*     */       }
/*     */ 
/* 842 */       if ((i & EV_CTS) == EV_CTS) {
/* 843 */         this.port.sendCTSevent();
/*     */       }
/* 845 */       if ((i & EV_DSR) == EV_DSR) {
/* 846 */         this.port.sendDSRevent();
/*     */       }
/* 848 */       if ((i & EV_RLSD) == EV_RLSD) {
/* 849 */         this.port.sendCDevent();
/*     */       }
/* 851 */       if ((i & EV_RING) == EV_RING) {
/* 852 */         this.port.sendRIevent();
/*     */       }
/* 854 */       if ((i & EV_BREAK) == EV_BREAK) {
/* 855 */         this.port.sendBIevent();
/*     */       }
/* 857 */       if ((i & EV_ERR) == EV_ERR) {
/* 858 */         int j = i >> 16;
/* 859 */         if ((j & CE_OVERRUN) == CE_OVERRUN) {
/* 860 */           this.port.sendOEevent();
/*     */         }
/* 862 */         if ((j & CE_RXPARITY) == CE_RXPARITY) {
/* 863 */           this.port.sendPEevent();
/*     */         }
/* 865 */         if ((j & CE_FRAME) == CE_FRAME) {
/* 866 */           this.port.sendFEevent();
/*     */         }
/*     */       }
/* 869 */       if ((i & EV_TXEMPTY) == EV_TXEMPTY) {
/* 870 */         this.port.sendOutputEmptyEvent();
/*     */       }
/* 872 */       if ((i & EV_RXCHAR) == EV_RXCHAR) {
/* 873 */         synchronized (this.port.readSignal) {
/* 874 */           this.port.readSignal.notifyAll();
/*     */         }
/*     */         try {
/* 877 */           if (this.port.available() > 0)
/* 878 */             this.port.sendDataAvailEvent();
/*     */         }
/*     */         catch (IOException localIOException) {
/*     */         }
/*     */       }
/* 883 */       if ((i & EV_RXFLAG) != EV_RXFLAG) continue;
/* 884 */       this.port.framingByteReceived = true;
/* 885 */       synchronized (this.port.readSignal) {
/* 886 */         this.port.readSignal.notifyAll();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     com.sun.comm.NotificationThread
 * JD-Core Version:    0.6.0
 */