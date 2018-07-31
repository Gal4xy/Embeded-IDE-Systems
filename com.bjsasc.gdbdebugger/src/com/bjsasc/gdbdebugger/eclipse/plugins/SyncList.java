package com.bjsasc.gdbdebugger.eclipse.plugins;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SyncList {
	protected List list;

	  public SyncList()
	  {
	    this.list = Collections.synchronizedList(new LinkedList());
	  }

	  public Object removeItem(int timeout) throws InterruptedException
	  {
	    synchronized (this.list) {
	      if (this.list.isEmpty())
	      {
	        this.list.wait(timeout, 0);
	      }

	      Object item = this.list.remove(0);

	      return item;
	    }
	  }

	  public void addItem(Object item)
	  {
	    synchronized (this.list)
	    {
	      this.list.add(item);

	      this.list.notifyAll();
	    }
	  }

	  public Object[] clearItems()
	  {
		Object[] array;
	    synchronized (this.list) {
	      array = this.list.toArray();
	      this.list.clear();
	    }
	    return array;
	  }

	  public boolean isEmpty()
	  {
	    boolean empty;
	    synchronized (this.list) {
	      empty = this.list.isEmpty();
	    }
	    return empty;
	  }
}
