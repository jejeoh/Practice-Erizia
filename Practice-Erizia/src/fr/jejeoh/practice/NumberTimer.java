package fr.jejeoh.practice;

public class NumberTimer {
	
	int id;
	int tim;
	
	public NumberTimer(Base main, int timer) {
		
		main.toid++;
		this.id = main.toid;
		this.tim = timer;
		
		
	}
	public int onId(){
		return id;
	}
	public void onDis() {
		tim--;
	}
	public int getTime() {
		return tim;
	}
}
