package ob;

import java.io.Serializable;

public class Packet implements Serializable {
	private String evt;
	private String data;
	private long time;
	
	public Packet(String evt, String data) {
		this.evt = evt;
		this.data = data;
	}
	
	public Packet(String evt, String data, long time) {
		this.evt = evt;
		this.data = data;
		this.time = time;
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getEvt() {
		return evt;
	}
	public void setEvt(String evt) {
		this.evt = evt;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
	
}
