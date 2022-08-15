package ob;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

public class ObjectPlayer implements Serializable {
	
	private static final Logger LOGGER = Logger.getLogger("xocdia");
	private int Pid;
	private String userName;
	private List<Integer> option;
//	private DataSend choiceData;
	private List<DataSend> choiceData;
	
	private int roomId;
	private Long markCuoc;
	private Long AG = 0L;
	private int timeToStart;
	private boolean autoExit;
	private String language;
	
	
	public ItemPlayer getItemPlayer() {
		ItemPlayer p = new ItemPlayer();
		p.setId(this.Pid);
		p.setN(this.userName);
		p.setAG(this.AG);
		p.setTimeToStart(this.timeToStart);
		
		return p;
		
	}
	
	public Long sumTienCuoc() {
		Long sum = 0L;
		for(int i=0; i<choiceData.size(); i++) {
			sum += choiceData.get(i).getTiencuoc();
		}
		
		return sum;
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
	public List<Integer> getOption() {
		return this.option;
	}
	public void setOption(List<Integer> option) {
		this.option = option;
	}
	public int getTimeToStart() {
		return timeToStart;
	}
	public void setTimeToStart(int time) {
		this.timeToStart = time;
	}
	
	public void setChoiceData(List<DataSend> list) {
		this.choiceData = list;
	}
	public List<DataSend> getChoiceData() {
		return this.choiceData;
	}
	
	public boolean isAutoExit() {
		return this.autoExit;
	}
	public void setAutoExit(boolean b) {
		this.autoExit = b;
	}
	
	public String getLanguage() {
		return this.language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}
