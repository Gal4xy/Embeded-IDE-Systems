package com.bjsasc.utils.mylist;

import java.util.ArrayList;
import java.util.List;

public class MyList {
	public static ArrayList fileArray = new ArrayList();
	private final static int LIMIT = 100;
	private int size;
	private int sectorSize;
	private List list;
	private int sector;
	private RwObj rwobj;

	public MyList() {
		this.size = 0;
		this.list = new ArrayList();
		this.sector = 0;
		rwobj = new RwObj("event.data");
		this.sectorSize = LIMIT;
	}

	public MyList(int sectorSize,String path) {
		this();
		this.sectorSize = sectorSize;
		rwobj = new RwObj(path);
	}

	public int size() {
		return this.size;
	}

	public int getSectorSize() {
		return sectorSize;
	}

	private Object get(int index) {
		if (index >= sector * getSectorSize()
				&& index < (sector + 1) * getSectorSize())
			return list.get(index % this.sectorSize);
		else
			return getList(index).get(index % this.sectorSize);
	}

	public List getList(int index) {
		this.sector = index / sectorSize;
		this.list = readList();
		return this.list;
	}

	public void add(Object o) {
		if (size >= sector * getSectorSize()
				&& size <= (sector + 1) * getSectorSize())
			this.list.add(o);
		else {
			this.list.clear();
			this.list.add(o);
			this.sector = size / getSectorSize();
		}
		size++;
		writeList();

	}

	// 从文件读取list
	private List readList() {
		List list = rwobj.readObj(sector*sectorSize, sectorSize);
		return list;

	}

	// 保存list到文件
	private void writeList() {
		rwobj.writeObj(this.list.get(list.size() - 1));
		// this.fileArray.add(this.list.get(list.size()-1));
	}
	public List getAllList() {
		return rwobj.readObj(0, size);
	}

	public static void main(String args[]) {
		MyList mylist = new MyList(3,"event.data");
		mylist.add(1);
		mylist.add(2);
		mylist.add(3);
		mylist.add(4);
		for(int i=0;i<mylist.size;i++)
			System.out.println(mylist.get(i));
		mylist.add(5);
		mylist.add(6);
		mylist.add(7);

		for(int i=0;i<mylist.size;i++)
			System.out.println(mylist.get(i));
		
		return;
	}
}
