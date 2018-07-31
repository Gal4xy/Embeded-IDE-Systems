/*     */ package javax.comm;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.StreamTokenizer;
/*     */ import java.io.StringReader;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class CommPortIdentifier
/*     */ {
/*     */   String name;
/*     */   private int portType;
/*     */   public static final int PORT_SERIAL = 1;
/*     */   public static final int PORT_PARALLEL = 2;
/*     */   private boolean nativeObjectsCreated;
/*     */   private int ownedEvent;
/*     */   private int unownedEvent;
/*     */   private int ownershipRequestedEvent;
/*     */   private int shmem;
/*     */   private String shname;
/*     */   private boolean maskOwnershipEvents;
/*     */   OwnershipEventThread oeThread;
/* 405 */   CpoList cpoList = new CpoList();
/*     */   CommPortIdentifier next;
/*     */   private CommPort port;
/*     */   private CommDriver driver;
/* 671 */   static Object lock = new Object();
/*     */   static String propfilename;
/*     */   static CommPortIdentifier masterIdList;
/*     */   boolean owned;
/*     */   String owner;
/*     */ 
/*     */   public static Enumeration getPortIdentifiers()
/*     */   {
/*  68 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  69 */     if (localSecurityManager != null) {
/*  70 */       localSecurityManager.checkDelete(propfilename);
/*     */     }
/*     */ 
/*  73 */     return new CommPortEnumerator();
/*     */   }
/*     */ 
/*     */   public static CommPortIdentifier getPortIdentifier(String paramString)
/*     */     throws NoSuchPortException
/*     */   {
/*  87 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  88 */     if (localSecurityManager != null) {
/*  89 */       localSecurityManager.checkDelete(propfilename);
/*     */     }
/*     */ 
/*  92 */     CommPortIdentifier localCommPortIdentifier = null;
/*     */ 
/*  94 */     synchronized (lock) {
/*  95 */       localCommPortIdentifier = masterIdList;
/*  96 */       while (localCommPortIdentifier != null) {
/*  97 */         if (localCommPortIdentifier.name.equals(paramString))
/*     */           break;
/*  99 */         localCommPortIdentifier = localCommPortIdentifier.next;
/*     */       }
/*     */     }
/* 102 */     if (localCommPortIdentifier != null) {
/* 103 */       return localCommPortIdentifier;
/*     */     }
/* 105 */     throw new NoSuchPortException();
/*     */   }
/*     */ 
/*     */   public static CommPortIdentifier getPortIdentifier(CommPort paramCommPort)
/*     */     throws NoSuchPortException
/*     */   {
/* 120 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 121 */     if (localSecurityManager != null) {
/* 122 */       localSecurityManager.checkDelete(propfilename);
/*     */     }
/*     */ 
/* 125 */     CommPortIdentifier localCommPortIdentifier = null;
/*     */ 
/* 127 */     synchronized (lock) {
/* 128 */       localCommPortIdentifier = masterIdList;
/* 129 */       while (localCommPortIdentifier != null) {
/* 130 */         if (localCommPortIdentifier.port == paramCommPort)
/*     */           break;
/* 132 */         localCommPortIdentifier = localCommPortIdentifier.next;
/*     */       }
/*     */     }
/* 135 */     if (localCommPortIdentifier != null) {
/* 136 */       return localCommPortIdentifier;
/*     */     }
/* 138 */     throw new NoSuchPortException();
/*     */   }
/*     */ 
/*     */   private static void addPort(CommPort paramCommPort, int paramInt)
/*     */   {
/* 147 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 148 */     if (localSecurityManager != null) {
/* 149 */       localSecurityManager.checkDelete(propfilename);
/*     */     }
/*     */ 
/* 152 */     CommPortIdentifier localCommPortIdentifier1 = 
/* 153 */       new CommPortIdentifier(paramCommPort.getName(), paramCommPort, paramInt, null);
/* 154 */     CommPortIdentifier localCommPortIdentifier2 = masterIdList; CommPortIdentifier localCommPortIdentifier3 = null;
/*     */ 
/* 156 */     synchronized (lock) {
/* 157 */       while (localCommPortIdentifier2 != null) {
/* 158 */         localCommPortIdentifier3 = localCommPortIdentifier2;
/* 159 */         localCommPortIdentifier2 = localCommPortIdentifier2.next;
/*     */       }
/* 161 */       if (localCommPortIdentifier3 != null)
/* 162 */         localCommPortIdentifier3.next = localCommPortIdentifier1;
/*     */       else
/* 164 */         masterIdList = localCommPortIdentifier1;
/* 156 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void addPortName(String paramString, int paramInt, CommDriver paramCommDriver)
/*     */   {
/* 180 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 181 */     if (localSecurityManager != null) {
/* 182 */       localSecurityManager.checkDelete(propfilename);
/*     */     }
/*     */ 
/* 185 */     CommPortIdentifier localCommPortIdentifier1 = 
/* 186 */       new CommPortIdentifier(paramString, null, paramInt, paramCommDriver);
/* 187 */     CommPortIdentifier localCommPortIdentifier2 = masterIdList; CommPortIdentifier localCommPortIdentifier3 = null;
/*     */ 
/* 189 */     synchronized (lock) {
/* 190 */       while (localCommPortIdentifier2 != null) {
/* 191 */         localCommPortIdentifier3 = localCommPortIdentifier2;
/* 192 */         localCommPortIdentifier2 = localCommPortIdentifier2.next;
/*     */       }
/* 194 */       if (localCommPortIdentifier3 != null)
/* 195 */         localCommPortIdentifier3.next = localCommPortIdentifier1;
/*     */       else
/* 197 */         masterIdList = localCommPortIdentifier1;
/* 189 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 213 */     return this.name;
/*     */   }
/*     */ 
/*     */   public int getPortType()
/*     */   {
/* 233 */     return this.portType;
/*     */   }
/*     */   private native int nCreateMutex(String paramString);
/*     */ 
/*     */   private native int nCreateEvent(String paramString);
/*     */ 
/*     */   private native boolean nClaimMutex(int paramInt1, int paramInt2);
/*     */ 
/*     */   private native boolean nReleaseMutex(int paramInt);
/*     */ 
/*     */   private native boolean nPulseEvent(int paramInt);
/*     */ 
/*     */   private native boolean nSetEvent(int paramInt);
/*     */ 
/*     */   private native boolean nCloseHandle(int paramInt);
/*     */ 
/* 256 */   private void createNativeObjects() { if (this.ownershipRequestedEvent == 0) {
/* 257 */       this.ownershipRequestedEvent = 
/* 258 */         nCreateEvent("javax.comm." + this.name + "-ownershipRequestedEvent");
/*     */     }
/* 260 */     if (this.ownedEvent == 0) {
/* 261 */       this.ownedEvent = nCreateEvent("javax.comm." + this.name + "-ownedEvent");
/*     */     }
/* 263 */     if (this.unownedEvent == 0) {
/* 264 */       this.unownedEvent = nCreateEvent("javax.comm." + this.name + "-unownedEvent");
/*     */     }
/* 266 */     this.nativeObjectsCreated = true;
/*     */   }
/*     */ 
/*     */   public synchronized CommPort open(String paramString, int paramInt)
/*     */     throws PortInUseException
/*     */   {
/* 300 */     if (!this.nativeObjectsCreated) {
/* 301 */       createNativeObjects();
/*     */     }
/*     */ 
/* 304 */     if (this.owned) {
/* 305 */       this.maskOwnershipEvents = true;
/* 306 */       fireOwnershipEvent(
/* 307 */         3);
/* 308 */       this.maskOwnershipEvents = false;
/* 309 */       if (this.owned) {
/* 310 */         throw new PortInUseException(this.owner);
/*     */       }
/*     */     }
/*     */ 
/* 314 */     this.port = this.driver.getCommPort(this.name, this.portType);
/*     */ 
/* 316 */     if (this.port == null)
/*     */     {
/* 322 */       nSetEvent(this.ownershipRequestedEvent);
/* 323 */       for (int i = paramInt > 200 ? paramInt : 200; i > 0; i -= 200) {
/*     */         try {
/* 325 */           wait(200L);
/*     */         } catch (InterruptedException localInterruptedException) {
/*     */         }
/* 328 */         this.port = this.driver.getCommPort(this.name, this.portType);
/* 329 */         if (this.port != null)
/*     */           break;
/*     */       }
/* 332 */       if (this.port == null) {
/* 333 */         String str = nGetOwner(this.shname);
/*     */ 
/* 335 */         if ((str == null) || (str.length() == 0))
/* 336 */           str = "Unknown Windows Application";
/* 337 */         throw new PortInUseException(str);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 342 */     this.owned = true;
/* 343 */     this.owner = paramString;
/*     */ 
/* 345 */     if ((paramString == null) || (paramString.length() == 0))
/* 346 */       this.shmem = nSetOwner(this.shname, "Unspecified Java Application", true);
/*     */     else {
/* 348 */       this.shmem = nSetOwner(this.shname, paramString, true);
/*     */     }
/*     */ 
/* 356 */     if (!nSetEvent(this.ownedEvent)) {
/* 357 */       System.err.println("Error pulsing ownedEvent");
/*     */     }
/*     */ 
/* 360 */     return this.port;
/*     */   }
/*     */ 
/*     */   private native int nSetOwner(String paramString1, String paramString2, boolean paramBoolean);
/*     */ 
/*     */   private native String nGetOwner(String paramString);
/*     */ 
/*     */   private native void nUnsetOwner(String paramString);
/*     */ 
/*     */   public String getCurrentOwner()
/*     */   {
/* 374 */     if (this.owned) {
/* 375 */       return this.owner;
/*     */     }
/* 377 */     String str = nGetOwner(this.shname);
/* 378 */     if ((str == null) || (str.length() == 0)) {
/* 379 */       return "Port currently not owned";
/*     */     }
/* 381 */     return str;
/*     */   }
/*     */ 
/*     */   public boolean isCurrentlyOwned()
/*     */   {
/* 392 */     if (this.owned) {
/* 393 */       return true;
/*     */     }
/* 395 */     String str = nGetOwner(this.shname);
/*     */ 
/* 397 */     return (str != null) && (str.length() != 0);
/*     */   }
/*     */ 
/*     */   public void addPortOwnershipListener(CommPortOwnershipListener paramCommPortOwnershipListener)
/*     */   {
/* 428 */     this.cpoList.add(paramCommPortOwnershipListener);
/*     */ 
/* 430 */     if (this.oeThread == null) {
/* 431 */       this.oeThread = new OwnershipEventThread(this);
/* 432 */       this.oeThread.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removePortOwnershipListener(CommPortOwnershipListener paramCommPortOwnershipListener)
/*     */   {
/* 446 */     this.cpoList.remove(paramCommPortOwnershipListener);
/*     */   }
/*     */ 
/*     */   private native int nWaitForEvents(int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   void ownershipThreadWaiter()
/*     */   {
/*     */     int i;
/* 467 */     if ((i = nWaitForEvents(this.ownedEvent, this.unownedEvent, 
/* 467 */       this.ownershipRequestedEvent)) >= 0) {
/* 468 */       this.maskOwnershipEvents = true;
/* 469 */       switch (i) {
/*     */       case 0:
/* 471 */         fireOwnershipEvent(1);
/* 472 */         break;
/*     */       case 1:
/* 474 */         fireOwnershipEvent(2);
/* 475 */         break;
/*     */       case 2:
/* 477 */         fireOwnershipEvent(
/* 478 */           3);
/* 479 */         break;
/*     */       }
/* 481 */       this.maskOwnershipEvents = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   synchronized void internalClosePort()
/*     */   {
/* 487 */     this.owned = false;
/* 488 */     this.owner = null;
/* 489 */     this.port = null;
/* 490 */     nUnsetOwner(this.shname);
/* 491 */     nSetEvent(this.unownedEvent);
/*     */   }
/*     */ 
/*     */   CommPortIdentifier(String paramString, CommPort paramCommPort, int paramInt, CommDriver paramCommDriver)
/*     */   {
/* 499 */     this.name = paramString;
/* 500 */     this.port = paramCommPort;
/* 501 */     this.portType = paramInt;
/* 502 */     this.next = null;
/* 503 */     this.driver = paramCommDriver;
/*     */ 
/* 505 */     this.shname = ("javax.comm-" + paramString);
/* 506 */     this.shmem = nSetOwner(this.shname, "", false);
/*     */   }
/*     */ 
/*     */   void fireOwnershipEvent(int paramInt)
/*     */   {
/* 516 */     CpoList localCpoList = this.cpoList.clonelist();
/* 517 */     localCpoList.fireOwnershipEvent(paramInt);
/*     */   }
/*     */ 
/*     */   private static String[] parsePropsFile(InputStream paramInputStream) {
/* 523 */     Vector localVector = new Vector();
/*     */     int i;
/*     */     try {
/* 527 */       byte[] arrayOfByte = new byte[4096];
/* 528 */       i = 0;
/* 529 */       int j = 0;
/*     */       int k;
/* 531 */       while ((k = paramInputStream.read()) != -1)
/*     */       {
/*     */         String str;
/* 533 */         switch (k)
/*     */         {
/*     */         case 10:
/*     */         case 13:
/* 539 */           if (i > 0) {
/* 540 */             str = new String(arrayOfByte, 0, 0, i);
/* 541 */             localVector.addElement(str);
/*     */           }
/* 543 */           i = 0;
/* 544 */           j = 0;
/* 545 */           break;
/*     */         case 35:
/* 547 */           j = 1;
/* 548 */           if (i > 0) {
/* 549 */             str = new String(arrayOfByte, 0, 0, i);
/* 550 */             localVector.addElement(str);
/*     */           }
/* 552 */           i = 0;
/* 553 */           break;
/*     */         default:
/* 555 */           if ((j != 0) || (i >= 4096)) continue;
/* 556 */           arrayOfByte[(i++)] = (byte)k;
/*     */         case 9:
/*     */         case 32:
/*     */         }
/*     */       }
/*     */     } catch (Throwable localThrowable) {
/* 560 */       System.err.println("Caught " + localThrowable + " parsing prop file.");
/*     */     }
/*     */ 
/* 565 */     if (localVector.size() > 0) {
/* 566 */       String[] arrayOfString = new String[localVector.size()];
/* 567 */       for (i = 0; i < localVector.size(); i++) {
/* 568 */         arrayOfString[i] = ((String)localVector.elementAt(i));
/*     */       }
/* 570 */       return arrayOfString;
/*     */     }
/* 572 */     return null;
/*     */   }
/*     */ 
/*     */   private static void loadDriver(String paramString)
/*     */     throws IOException
/*     */   {
/* 582 */     File localFile = new File(paramString);
/*     */ 
/* 587 */     BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(localFile));
/*     */ 
/* 589 */     String[] arrayOfString = parsePropsFile(localBufferedInputStream);
/*     */ 
/* 591 */     if (arrayOfString != null)
/* 592 */       for (int i = 0; i < arrayOfString.length; i++)
/* 593 */         if (arrayOfString[i].regionMatches(true, 0, "driver=", 0, 7)) {
/* 594 */           String str = arrayOfString[i].substring(7);
                    //modified by songjie at 2012-8-27
/* 595 */           str=str.trim();
                    //modified by songjie at 2012-8-27
/*     */           try {
/* 597 */             CommDriver localCommDriver = (CommDriver)
/* 598 */               Class.forName(str).newInstance();
/* 599 */             localCommDriver.initialize();
/*     */           } catch (Throwable localThrowable) {
/* 601 */             System.err.println("Caught " + localThrowable + 
/* 602 */               " while loading driver " + 
/* 603 */               str);
/*     */           }
/*     */         }
/*     */   }
/*     */ 
/*     */   private static String findPropFile()
/*     */   {
/* 617 */     String str1 = System.getProperty("java.class.path");
/*     */ 
/* 619 */     StreamTokenizer localStreamTokenizer = new StreamTokenizer(new StringReader(str1));
/*     */ 
/* 621 */     localStreamTokenizer.whitespaceChars(File.pathSeparatorChar, File.pathSeparatorChar);
/* 622 */     localStreamTokenizer.wordChars(File.separatorChar, File.separatorChar);
/* 623 */     localStreamTokenizer.ordinaryChar(46);
/* 624 */     localStreamTokenizer.wordChars(46, 46);
/*     */     try
/*     */     {
/* 628 */       while (localStreamTokenizer.nextToken() != -1)
/*     */       {
/* 630 */         int i = -1;
/*     */ 
/* 632 */         if ((localStreamTokenizer.ttype != -3) || 
/* 633 */           ((i = localStreamTokenizer.sval.indexOf("comm.jar")) == -1))
/*     */           continue;
/* 635 */         String str2 = new String(localStreamTokenizer.sval);
/*     */ 
/* 637 */         File localFile = new File(str2);
/*     */ 
/* 639 */         if (localFile.exists()) {
/* 640 */           String str3 = str2.substring(0, i);
/*     */ 
/* 642 */           if (str3 != null) {
/* 643 */             str3 = str3 + "." + File.separator + "javax.comm.properties";
/*     */           }
/*     */           else {
/* 646 */             str3 = "." + File.separator + "javax.comm.properties";
/*     */           }
/*     */ 
/* 649 */           localFile = new File(str3);
/* 650 */           if (localFile.exists()) {
/* 651 */             return new String(str3);
/*     */           }
/*     */ 
/* 654 */           return null;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*     */     }
/*     */ 
/* 663 */     return null;
/*     */   }
/*     */ 
/*     */   public CommPort open(FileDescriptor paramFileDescriptor)
/*     */     throws UnsupportedCommOperationException
/*     */   {
/* 735 */     throw new UnsupportedCommOperationException();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*     */     String str1;
/* 678 */     if ((str1 = System.getProperty("javax.comm.properties")) != null) {
/* 679 */       System.err.println("Comm Drivers: " + str1);
/*     */     }
/* 681 */     String str2 = System.getProperty("java.home") + 
/* 682 */       File.separator + 
/* 683 */       "lib" + 
/* 684 */       File.separator + 
/* 685 */       "javax.comm.properties";
/*     */     try
/*     */     {
/* 688 */       loadDriver(str2);
/* 689 */       propfilename = new String(str2);
/*     */ 
/*     */     }
/*     */     catch (IOException localIOException2)
/*     */     {
/* 692 */       propfilename = findPropFile();
/*     */       try
/*     */       {
/* 695 */         if (propfilename != null)
/*     */         {
/* 697 */           loadDriver(propfilename);
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (IOException localIOException1)
/*     */       {
/* 700 */         propfilename = new String(" ");
/*     */ 
/* 703 */         System.err.println(localIOException1);
/*     */ 
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     javax.comm.CommPortIdentifier
 * JD-Core Version:    0.6.0
 */