package ob;

import java.io.Serializable;

public class ItemPlayer implements Serializable {
	
	private int id;
	private String N;
	private Long AG = 0L;
	private int timeToStart;
	
	public Long getAG() {
		return AG;
	}
	public void setAG(Long aG) {
		AG = aG;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getN() {
		return N;
	}
	public void setN(String n) {
		this.N = n;
	}
	
	public int getTimeToStart() {
		return timeToStart;
	}
	public void setTimeToStart(int time) {
		this.timeToStart = time;
	}
	
	
	
	
}
