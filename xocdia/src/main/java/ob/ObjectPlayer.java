package ob;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class ObjectPlayer implements Serializable {
	
	private static final Logger LOGGER = Logger.getLogger("xocdia");
	private int Pid;
	private String userName;
	private Field option;
	
	
	private int roomId;
	private Long markCuoc;
	private Long AG = 0L;
	private int timeToStart;
	
	public ItemPlayer getItemPlayer() {
		ItemPlayer p = new ItemPlayer();
		p.setId(this.Pid);
		p.setN(this.userName);
		p.setAG(this.AG);
		p.setTimeToStart(this.timeToStart);
		
		return p;
		
	}
	
	public int getPid() {
		return Pid;
	}
	public void setPid(int pid) {
		Pid = pid;
	}

	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public Long getMarkCuoc() {
		return markCuoc;
	}
	public void setMarkCuoc(Long markCuoc) {
		this.markCuoc = markCuoc;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String name) {
		this.userName = name;
	}
	public Long getAG() {
		return AG;
	}
	public void setAG(Long aG) {
		AG = aG;
	}
	public Field getOption() {
		return this.option;
	}
	public void setOption(Field option) {
		this.option = option;
	}
	public int getTimeToStart() {
		return timeToStart;
	}
	public void setTimeToStart(int time) {
		this.timeToStart = time;
	}
	
	
}
