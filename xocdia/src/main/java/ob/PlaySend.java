package ob;

public class PlaySend {
	private String evt;
	private int time;
	
	public PlaySend(String evt, int time) {
		this.evt = evt;
		this.time = time;
	}
	
	public String getEvt() {
		return evt;
	}
	public void setEvt(String evt) {
		this.evt = evt;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int t) {
		this.time = t;
	}
}
