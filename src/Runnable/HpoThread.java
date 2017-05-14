package Runnable;

import Model.Model;

public class HpoThread implements Runnable {

	Model model;
	String[] items;
	//private final Object lock1;
	public HpoThread(Model m,String[] items/*,Object lock1*/)
	{
		model = m;
		this.items = items;
		//this.lock1 = lock1;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
