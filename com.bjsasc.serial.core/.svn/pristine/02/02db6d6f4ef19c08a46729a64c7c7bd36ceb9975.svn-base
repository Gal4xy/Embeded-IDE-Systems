/*     */ package com.sun.comm;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.TooManyListenersException;
/*     */ import javax.comm.CommPort;
import javax.comm.NoSuchPortException;
/*     */ import javax.comm.SerialPort;
/*     */ import javax.comm.SerialPortEvent;
/*     */ import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
/*     */ 
/*     */ class Win32SerialPort extends SerialPort
/*     */ {
/*     */   int nativeHandle;
/*     */   private InputStream ins;
/*     */   private OutputStream outs;
/*  72 */   private int rcvThreshold = -1;
/*     */ 
/*  99 */   int rcvTimeout = -1;
/*     */ 
/* 128 */   private boolean framing = false;
/*     */   private int framingByte;
/*     */   boolean framingByteReceived;
/*     */   private int baudrate;
/*     */   private int parity;
/*     */   private int dataBits;
/*     */   private int stopBits;
/*     */   private int flowcontrol;
/* 240 */   private static int[] speeds = { 75, 110, 134, 150, 300, 600, 1200, 2400, 
/* 241 */     4800, 9600, 19200, 38400, 57600, 115200 };
/*     */ 
/* 312 */   private boolean dtr = true;
/*     */ 
/* 342 */   private boolean rts = true;
/*     */   NotificationThread notificationThread;
/*     */   SerialPortEventListener eventListener;
/*     */   private int notifyMask;
/*     */   static final int NOTIFY_DATA_AVAIL = 1;
/*     */   static final int NOTIFY_OUTPUT_EMPTY = 2;
/*     */   static final int NOTIFY_RI = 4;
/*     */   static final int NOTIFY_CTS = 8;
/*     */   static final int NOTIFY_DSR = 16;
/*     */   static final int NOTIFY_CD = 32;
/*     */   static final int NOTIFY_OE = 64;
/*     */   static final int NOTIFY_PE = 128;
/*     */   static final int NOTIFY_FE = 256;
/*     */   static final int NOTIFY_BI = 512;
/*     */   private boolean stateRI;
/*     */   private boolean stateCTS;
/*     */   private boolean stateDSR;
/*     */   private boolean stateCD;
/*     */   private boolean stateOE;
/*     */   private boolean statePE;
/*     */   private boolean stateFE;
/*     */   private boolean stateBI;
/*     */   Object readSignal;
/*     */   private byte[] wa;
/*     */   private static final int READ_POLL = 200;
/* 781 */   boolean closed = false;
/*     */ 
/*     */   Win32SerialPort(String paramString)
/*     */     throws IOException
/*     */   {
/*  31 */     this.name = paramString;
/*  32 */     if (!nativeConstructor(paramString)) {
/*  33 */       String str = "Unable to create port " + paramString;
/*     */ 
/*  35 */       throw new IOException(str);
/*     */     }
/*     */ 
/*  38 */     this.outs = new Win32SerialOutputStream(this);
/*  39 */     this.ins = new Win32SerialInputStream(this);
/*     */ 
/*  41 */     this.readSignal = new Object();
/*  42 */     this.wa = new byte[1];
/*     */     try
/*     */     {
/*  45 */       setFlowControlMode(0);
/*  46 */       setSerialPortParams(9600, 8, 1, 0);
/*     */     } catch (UnsupportedCommOperationException localUnsupportedCommOperationException) {
/*     */     }
/*  49 */     this.notificationThread = new NotificationThread(
/*  50 */       "Win32SerialPort Notification thread", this);
/*  51 */     this.notificationThread.start();
/*     */   }
/*     */ 
/*     */   private native boolean nativeConstructor(String paramString);
/*     */ 
/*     */   public InputStream getInputStream() throws IOException {
/*  58 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  59 */     return this.ins;
/*     */   }
/*     */ 
/*     */   public OutputStream getOutputStream() throws IOException
/*     */   {
/*  64 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  65 */     return this.outs;
/*     */   }
/*     */ 
/*     */   public void enableReceiveThreshold(int paramInt)
/*     */     throws UnsupportedCommOperationException
/*     */   {
/*  76 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*     */ 
/*  78 */     if (paramInt < 0) throw new UnsupportedCommOperationException("This threshold value is not supported");
/*     */ 
/*  80 */     this.rcvThreshold = paramInt;
/*     */   }
/*     */ 
/*     */   public void disableReceiveThreshold() {
/*  84 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  85 */     this.rcvThreshold = -1;
/*     */   }
/*     */   public boolean isReceiveThresholdEnabled() {
/*  88 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  89 */     return this.rcvThreshold != -1;
/*     */   }
/*     */   public int getReceiveThreshold() {
/*  92 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*  93 */     return this.rcvThreshold;
/*     */   }
/*     */ 
/*     */   public void enableReceiveTimeout(int paramInt)
/*     */     throws UnsupportedCommOperationException
/*     */   {
/* 103 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*     */ 
/* 105 */     if (paramInt < 0) throw new UnsupportedCommOperationException("This timeout value is not supported");
/*     */ 
/* 107 */     this.rcvTimeout = paramInt;
/*     */   }
/*     */ 
/*     */   public void disableReceiveTimeout() {
/* 111 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 112 */     this.rcvTimeout = -1;
/*     */   }
/*     */ 
/*     */   public int getReceiveTimeout() {
/* 116 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 117 */     return this.rcvTimeout;
/*     */   }
/*     */ 
/*     */   public boolean isReceiveTimeoutEnabled() {
/* 121 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 122 */     return this.rcvTimeout != -1;
/*     */   }
/*     */ 
/*     */   public void disableReceiveFraming()
/*     */   {
/* 133 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 134 */     this.framing = false;
/* 135 */     nativeDisableFraming();
/*     */   }
/*     */ 
/*     */   public void enableReceiveFraming(int paramInt) throws UnsupportedCommOperationException
/*     */   {
/* 140 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 141 */     this.framing = true;
/* 142 */     this.framingByte = (paramInt & 0xFF);
/* 143 */     nativeEnableFraming(paramInt);
/*     */   }
/*     */ 
/*     */   public int getReceiveFramingByte() {
/* 147 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 148 */     return this.framingByte;
/*     */   }
/*     */ 
/*     */   public boolean isReceiveFramingEnabled() {
/* 152 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 153 */     return this.framing; } 
/*     */   native void nativeEnableFraming(int paramInt);
/*     */ 
/*     */   native void nativeDisableFraming();
/*     */ 
/* 160 */   public void setInputBufferSize(int paramInt) { if (this.closed) throw new IllegalStateException("Port Closed");
/* 161 */     nsetInputBufferSize(paramInt); } 
/*     */   private native void nsetInputBufferSize(int paramInt);
/*     */ 
/* 165 */   public int getInputBufferSize() { if (this.closed) throw new IllegalStateException("Port Closed");
/* 166 */     return ngetInputBufferSize(); } 
/*     */   private native int ngetInputBufferSize();
/*     */ 
/* 170 */   public void setOutputBufferSize(int paramInt) { if (this.closed) throw new IllegalStateException("Port Closed");
/* 171 */     nsetOutputBufferSize(paramInt); } 
/*     */   private native void nsetOutputBufferSize(int paramInt);
/*     */ 
/* 175 */   public int getOutputBufferSize() { if (this.closed) throw new IllegalStateException("Port Closed");
/* 176 */     return ngetOutputBufferSize();
/*     */   }
/*     */ 
/*     */   private native int ngetOutputBufferSize();
/*     */ 
/*     */   public int getBaudRate()
/*     */   {
/* 189 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 190 */     return this.baudrate;
/*     */   }
/*     */   public int getDataBits() {
/* 193 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 194 */     return this.dataBits;
/*     */   }
/*     */   public int getStopBits() {
/* 197 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 198 */     return this.stopBits;
/*     */   }
/*     */   public int getParity() {
/* 201 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 202 */     return this.parity;
/*     */   }
/*     */ 
/*     */   public void setFlowControlMode(int paramInt) throws UnsupportedCommOperationException
/*     */   {
/* 207 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*     */ 
/* 211 */     if ((paramInt & 
/* 211 */       0x3) != 0)
/*     */     {
/* 213 */       if ((paramInt & 
/* 213 */         0xC) != 0) {
/* 214 */         throw new UnsupportedCommOperationException(
/* 215 */           "Cannot mix hardware and software flow control");
/*     */       }
/*     */     }
/*     */ 
/* 219 */     this.flowcontrol = paramInt;
/* 220 */     nativeSetFlowcontrolMode(paramInt);
/*     */ 
/* 229 */     if ((paramInt & 0x1) == 0) {
/* 230 */       this.rts = true;
/* 231 */       this.dtr = true;
/*     */     }
/*     */   }
/*     */   native void nativeSetFlowcontrolMode(int paramInt);
/*     */ 
/* 236 */   public int getFlowControlMode() { if (this.closed) throw new IllegalStateException("Port Closed");
/* 237 */     return this.flowcontrol;
/*     */   }
/*     */ 
/*     */   public void setSerialPortParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */     throws UnsupportedCommOperationException
/*     */   {
/* 246 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*     */ 
/* 248 */     for (int i = 0; i < speeds.length; i++) {
/* 249 */       if (paramInt1 == speeds[i])
/*     */         break;
/* 251 */       if (paramInt1 < speeds[i]) {
/* 252 */         throw new UnsupportedCommOperationException(
/* 253 */           "Unsupported baud rate");
/*     */       }
/*     */     }
/* 256 */     if ((paramInt2 != 5) && (paramInt2 != 6) && 
/* 257 */       (paramInt2 != 7) && (paramInt2 != 8)) {
/* 258 */       throw new UnsupportedCommOperationException("Unsupported num of databits");
/*     */     }
/*     */ 
/* 261 */     if ((paramInt3 != 1) && (paramInt3 != 2) && 
/* 262 */       (paramInt3 != 3)) {
/* 263 */       throw new UnsupportedCommOperationException("Unsupported num of stopbits");
/*     */     }
/*     */ 
/* 266 */     if ((paramInt4 != 2) && (paramInt4 != 1) && 
/* 267 */       (paramInt4 != 0)) {
/* 268 */       throw new UnsupportedCommOperationException("Unsupported parity value");
/*     */     }
/*     */ 
/* 271 */     this.baudrate = paramInt1;
/* 272 */     this.parity = paramInt4;
/* 273 */     this.dataBits = paramInt2;
/* 274 */     this.stopBits = paramInt3;
/*     */ 
/* 276 */     setCommDeviceParams(paramInt1, paramInt4, paramInt2, paramInt3);
/*     */ 
/* 278 */     saveCommDeviceState();
/*     */     try
/*     */     {
/* 281 */       setFlowControlMode(this.flowcontrol);
/*     */ 
/* 280 */       return;
/*     */     }
/*     */     catch (UnsupportedCommOperationException localUnsupportedCommOperationException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public native void setCommDeviceParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   public void sendBreak(int paramInt) {
/* 291 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 292 */     synchronized (this) {
/* 293 */       setCommBreak();
/*     */       try {
/* 295 */         Thread.sleep(paramInt);
/*     */       } catch (InterruptedException localInterruptedException) {
/*     */       }
/* 298 */       clearCommBreak();
/*     */ 
/* 292 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   private native void setCommBreak();
/*     */ 
/*     */   private native void clearCommBreak();
/*     */ 
/*     */   public void setDTR(boolean paramBoolean)
/*     */   {
/* 315 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*     */ 
/* 320 */     if ((this.flowcontrol & 0x1) == 1) {
/* 321 */       return;
/*     */     }
/*     */ 
/* 324 */     nativeSetDTR(paramBoolean);
/* 325 */     this.dtr = paramBoolean;
/*     */   }
/*     */   private native void nativeSetDTR(boolean paramBoolean);
/*     */ 
/* 330 */   public boolean isDTR() { if (this.closed) throw new IllegalStateException("Port Closed");
/* 331 */     return this.dtr;
/*     */   }
/*     */ 
/*     */   public void setRTS(boolean paramBoolean)
/*     */   {
/* 345 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*     */ 
/* 350 */     if ((this.flowcontrol & 0x1) == 1) {
/* 351 */       throw new IllegalStateException(
/* 352 */         "Cannot modify RTS when Hardware flowcontrol is on.");
/*     */     }
/*     */ 
/* 355 */     nativeSetRTS(paramBoolean);
/* 356 */     this.rts = paramBoolean;
/*     */   }
/*     */   private native void nativeSetRTS(boolean paramBoolean);
/*     */ 
/* 361 */   public boolean isRTS() { if (this.closed) throw new IllegalStateException("Port Closed");
/* 362 */     return this.rts; }
/*     */ 
/*     */   public boolean isCTS()
/*     */   {
/* 366 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 367 */     return nativeIsCTS();
/*     */   }
/*     */   private native boolean nativeIsCTS();
/*     */ 
/* 372 */   public boolean isDSR() { if (this.closed) throw new IllegalStateException("Port Closed");
/* 373 */     return nativeIsDSR(); } 
/*     */   private native boolean nativeIsDSR();
/*     */ 
/*     */   public boolean isRI() {
/* 378 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 379 */     return nativeIsRI();
/*     */   }
/*     */   private native boolean nativeIsRI();
/*     */ 
/* 384 */   public boolean isCD() { if (this.closed) throw new IllegalStateException("Port Closed");
/* 385 */     return nativeIsCD();
/*     */   }
/*     */ 
/*     */   private native boolean nativeIsCD();
/*     */ 
/*     */   public synchronized void addEventListener(SerialPortEventListener paramSerialPortEventListener)
/*     */     throws TooManyListenersException
/*     */   {
/* 395 */     if (this.closed) throw new IllegalStateException("Port Closed");
/*     */ 
/* 397 */     if (this.eventListener != null) {
/* 398 */       throw new TooManyListenersException();
/*     */     }
/*     */ 
/* 401 */     this.eventListener = paramSerialPortEventListener;
/*     */ 
/* 403 */     if ((this.eventListener != null) && (this.notificationThread == null)) {
/* 404 */       this.notificationThread = new NotificationThread(
/* 405 */         "Win32SerialPort Notification thread", this);
/* 406 */       this.notificationThread.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void removeEventListener()
/*     */   {
/* 412 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 413 */     this.eventListener = null;
/*     */   }
/*     */ 
/*     */   public synchronized void notifyOnDataAvailable(boolean paramBoolean)
/*     */   {
/* 433 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 434 */     if (paramBoolean)
/* 435 */       this.notifyMask |= 1;
/*     */     else
/* 437 */       this.notifyMask &= -2;
/* 438 */     nnotifyOnDataAvailable(paramBoolean);
/*     */   }
/*     */   private native void nnotifyOnDataAvailable(boolean paramBoolean);
/*     */ 
/* 443 */   public synchronized void notifyOnOutputEmpty(boolean paramBoolean) { if (this.closed) throw new IllegalStateException("Port Closed");
/* 444 */     if (paramBoolean)
/* 445 */       this.notifyMask |= 2;
/*     */     else
/* 447 */       this.notifyMask &= -3;
/* 448 */     nnotifyOnOutputEmpty(paramBoolean); } 
/*     */   private native void nnotifyOnOutputEmpty(boolean paramBoolean);
/*     */ 
/*     */   public synchronized void notifyOnCTS(boolean paramBoolean) {
/* 453 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 454 */     if (paramBoolean)
/* 455 */       this.notifyMask |= 8;
/*     */     else
/* 457 */       this.notifyMask &= -9;
/* 458 */     nnotifyOnCTS(paramBoolean);
/*     */   }
/*     */   private native void nnotifyOnCTS(boolean paramBoolean);
/*     */ 
/* 463 */   public synchronized void notifyOnDSR(boolean paramBoolean) { if (this.closed) throw new IllegalStateException("Port Closed");
/* 464 */     if (paramBoolean)
/* 465 */       this.notifyMask |= 16;
/*     */     else
/* 467 */       this.notifyMask &= -17;
/* 468 */     nnotifyOnDSR(paramBoolean); } 
/*     */   private native void nnotifyOnDSR(boolean paramBoolean);
/*     */ 
/*     */   public synchronized void notifyOnCarrierDetect(boolean paramBoolean) {
/* 473 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 474 */     if (paramBoolean)
/* 475 */       this.notifyMask |= 32;
/*     */     else
/* 477 */       this.notifyMask &= -33;
/* 478 */     nnotifyOnCarrierDetect(paramBoolean);
/*     */   }
/*     */   private native void nnotifyOnCarrierDetect(boolean paramBoolean);
/*     */ 
/* 483 */   public synchronized void notifyOnRingIndicator(boolean paramBoolean) { if (this.closed) throw new IllegalStateException("Port Closed");
/* 484 */     if (paramBoolean)
/* 485 */       this.notifyMask |= 4;
/*     */     else
/* 487 */       this.notifyMask &= -5;
/* 488 */     nnotifyOnRingIndicator(paramBoolean); } 
/*     */   private native void nnotifyOnRingIndicator(boolean paramBoolean);
/*     */ 
/*     */   public synchronized void notifyOnOverrunError(boolean paramBoolean) {
/* 493 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 494 */     if (paramBoolean)
/* 495 */       this.notifyMask |= 64;
/*     */     else
/* 497 */       this.notifyMask &= -65;
/* 498 */     nnotifyOnOverrunError(paramBoolean);
/*     */   }
/*     */   private native void nnotifyOnOverrunError(boolean paramBoolean);
/*     */ 
/* 503 */   public synchronized void notifyOnParityError(boolean paramBoolean) { if (this.closed) throw new IllegalStateException("Port Closed");
/* 504 */     if (paramBoolean)
/* 505 */       this.notifyMask |= 128;
/*     */     else
/* 507 */       this.notifyMask &= -129;
/* 508 */     nnotifyOnParityError(paramBoolean); } 
/*     */   private native void nnotifyOnParityError(boolean paramBoolean);
/*     */ 
/*     */   public synchronized void notifyOnFramingError(boolean paramBoolean) {
/* 513 */     if (this.closed) throw new IllegalStateException("Port Closed");
/* 514 */     if (paramBoolean)
/* 515 */       this.notifyMask |= 256;
/*     */     else
/* 517 */       this.notifyMask &= -257;
/* 518 */     nnotifyOnFramingError(paramBoolean);
/*     */   }
/*     */   private native void nnotifyOnFramingError(boolean paramBoolean);
/*     */ 
/* 523 */   public synchronized void notifyOnBreakInterrupt(boolean paramBoolean) { if (this.closed) throw new IllegalStateException("Port Closed");
/* 524 */     if (paramBoolean)
/* 525 */       this.notifyMask |= 512;
/*     */     else
/* 527 */       this.notifyMask &= -513;
/* 528 */     nnotifyOnBreakInterrupt(paramBoolean);
/*     */   }
/*     */ 
/*     */   private native void nnotifyOnBreakInterrupt(boolean paramBoolean);
/*     */ 
/*     */   native int waitForEvent();
/*     */ 
/*     */   private void saveCommDeviceState()
/*     */   {
/* 546 */     this.stateRI = isRI();
/* 547 */     this.stateCTS = isCTS();
/* 548 */     this.stateDSR = isDSR();
/* 549 */     this.stateCD = isCD();
/*     */   }
/*     */ 
/*     */   void sendCTSevent()
/*     */   {
/* 554 */     if ((this.notifyMask & 0x8) == 8) {
/* 555 */       boolean bool = isCTS();
/* 556 */       if (bool != this.stateCTS) {
/* 557 */         SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 558 */           3, this.stateCTS, bool);
/* 559 */         this.eventListener.serialEvent(localSerialPortEvent);
/* 560 */         this.stateCTS = bool;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendDSRevent() {
/* 566 */     if ((this.notifyMask & 0x10) == 16) {
/* 567 */       boolean bool = isDSR();
/* 568 */       if (bool != this.stateDSR) {
/* 569 */         SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 570 */           4, this.stateDSR, bool);
/* 571 */         this.eventListener.serialEvent(localSerialPortEvent);
/* 572 */         this.stateDSR = bool;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendCDevent() {
/* 578 */     if ((this.notifyMask & 0x20) == 32) {
/* 579 */       boolean bool = isCD();
/* 580 */       if (bool != this.stateCD) {
/* 581 */         SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 582 */           6, this.stateCD, bool);
/* 583 */         this.eventListener.serialEvent(localSerialPortEvent);
/* 584 */         this.stateCD = bool;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendRIevent() {
/* 590 */     if ((this.notifyMask & 0x4) == 4) {
/* 591 */       boolean bool = isRI();
/* 592 */       if (bool != this.stateRI) {
/* 593 */         SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 594 */           5, this.stateRI, bool);
/* 595 */         this.eventListener.serialEvent(localSerialPortEvent);
/* 596 */         this.stateRI = bool;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendBIevent() {
/* 602 */     if ((this.notifyMask & 0x200) == 512) {
/* 603 */       SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 604 */         10, false, true);
/*     */ 
/* 606 */       this.eventListener.serialEvent(localSerialPortEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendOEevent() {
/* 611 */     if ((this.notifyMask & 0x40) == 64) {
/* 612 */       SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 613 */         7, false, true);
/*     */ 
/* 615 */       this.eventListener.serialEvent(localSerialPortEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendPEevent() {
/* 620 */     if ((this.notifyMask & 0x80) == 128) {
/* 621 */       SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 622 */         8, false, true);
/*     */ 
/* 624 */       this.eventListener.serialEvent(localSerialPortEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendFEevent() {
/* 629 */     if ((this.notifyMask & 0x100) == 256) {
/* 630 */       SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 631 */         9, false, true);
/*     */ 
/* 633 */       this.eventListener.serialEvent(localSerialPortEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendOutputEmptyEvent() {
/* 638 */     if ((this.notifyMask & 0x2) == 2) {
/* 639 */       SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 640 */         2, false, true);
/* 641 */       this.eventListener.serialEvent(localSerialPortEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   void sendDataAvailEvent() {
/* 646 */     if ((this.notifyMask & 0x1) == 1) {
/* 647 */       SerialPortEvent localSerialPortEvent = new SerialPortEvent(this, 
/* 648 */         1, false, true);
/* 649 */       this.eventListener.serialEvent(localSerialPortEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void finalize() throws Throwable
/*     */   {
/* 655 */     nativeFinalize();
/*     */   }
/*     */ 
/*     */   private native void nativeFinalize();
/*     */ 
/*     */   void write(int paramInt) throws IOException
/*     */   {
/* 663 */     this.wa[0] = (byte)paramInt;
/* 664 */     write(this.wa, 0, 1);
/*     */   }
/*     */ 
/*     */   void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/* 669 */     int i = 0;
/*     */ 
/* 671 */     while (i < paramInt2) {
/* 672 */       int j = nwrite(paramArrayOfByte, paramInt1 + i, 
/* 673 */         paramInt2 - i > 512 ? 512 : paramInt2 - i);
/* 674 */       if (j > 0)
/* 675 */         i += j;
/*     */       else
/* 677 */         throw new IOException("write error"); 
/*     */     }
/*     */   }
/*     */ 
/*     */   private native int nwrite(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*     */ 
/*     */   private native int nread(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException;
/*     */ 
/*     */   native int available() throws IOException;
/*     */ 
/*     */   int read() throws IOException {
/* 690 */     byte[] arrayOfByte = new byte[1];
/* 691 */     switch (read(arrayOfByte, 0, 1)) {
/*     */     case 1:
/* 693 */       return arrayOfByte[0] & 0xFF;
/*     */     }
/* 695 */     return -1;
/*     */   }
/*     */ 
/*     */   int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 704 */     int i = 200;
/* 705 */     int j = 0;
/* 706 */     if (this.rcvTimeout == 0) {
/* 707 */       j = 0;
/* 708 */     } else if (this.rcvTimeout == -1) {
/* 709 */       j = 0;
/*     */     } else {
/* 711 */       i = this.rcvTimeout < 200 ? this.rcvTimeout : 200;
/* 712 */       j = this.rcvTimeout / i;
/*     */     }
/*     */ 
/* 715 */     int k = 0;
/*     */ 
/* 717 */     int m = 0;
/*     */ 
/* 719 */     while (k < paramInt2)
/*     */     {
/* 721 */       int n = available();
/*     */       int i1;
/* 722 */       if (n > 0) {
/* 723 */         i1 = nread(paramArrayOfByte, paramInt1 + k, 
/* 725 */           paramInt2 - k);
/* 726 */         if (i1 >= 0)
/* 727 */           k += i1;
/*     */         else {
/* 729 */           return k;
/*     */         }
/*     */       }
/* 732 */       if (this.rcvTimeout == 0) {
/* 733 */         return k;
/*     */       }
/* 735 */       if (k == paramInt2) {
/* 736 */         return k;
/*     */       }
/* 738 */       if ((this.framing) && (this.framingByteReceived)) {
/* 739 */         this.framingByteReceived = false;
/* 740 */         return k;
/*     */       }
/* 742 */       if (this.rcvTimeout == -1) {
/* 743 */         if (this.rcvThreshold == -1) {
/* 744 */           if (k > 0) {
/* 745 */             return k;
/*     */           }
/*     */         }
/* 748 */         else if (k >= Math.min(this.rcvThreshold, paramInt2))
/* 749 */           return k;
/*     */       }
/*     */       else
/*     */       {
/* 753 */         if (m >= j) {
/* 754 */           return k;
/*     */         }
/* 756 */         if (this.rcvThreshold == -1) {
/* 757 */           if (k > 0) {
/* 758 */             return k;
/*     */           }
/*     */         }
/* 761 */         else if (k >= Math.min(this.rcvThreshold, paramInt2)) {
/* 762 */           return k;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 767 */       synchronized (this.readSignal) {
/* 768 */         m++;
/*     */         try {
/* 770 */           this.readSignal.wait(i);
/*     */         } catch (InterruptedException localInterruptedException) {
/* 772 */           i1 = -1;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 777 */     return k;
/*     */   }
/*     */ 
/*     */   public void close() throws NoSuchPortException, IOException
/*     */   {
/* 785 */     this.eventListener = null;
/* 786 */     nativeFinalize();
/* 787 */     this.nativeHandle = 0;
/* 788 */     this.closed = true;
/* 789 */     super.close();
/*     */   }
/*     */ }

/* Location:           E:\project\szide_projects_3.7_20111017\com.bjsasc.Dsuflashwriter\comm.jar
 * Qualified Name:     com.sun.comm.Win32SerialPort
 * JD-Core Version:    0.6.0
 */