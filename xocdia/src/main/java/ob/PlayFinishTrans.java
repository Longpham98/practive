package ob;

import java.io.Serializable;
import java.util.List;

public class PlayFinishTrans implements Serializable {

	private int pid;
	private Long AG = 0L;
	private Long M = 0L;
	private List<Integer> typeScore;
	private List<DataSend> choiceData;
	private int S;
	
	public PlayFinishTrans() {
		this.M = 0L;
		this.AG = 0L;
		this.pid = 0;
		this.S = 0;
	}
	
	
	public void incrementM(Long m) {
		this.M += m;
	}
	
	public Long getAG() {
		return AG;
	}
	public void setAG(Long ag) {
		this.AG = ag;
	}
	public List<Integer> getTypeScore() {
		return typeScore;
	}
	public void setTypeScore(List<Integer> typeScore) {
		this.typeScore = typeScore;
	}
	public Long getM() {
		return M;
	}
	public void setM(Long m) {
		this.M = m;
	}
	
	public void setChoiceData(List<DataSend> choiceData) {
		this.choiceData = choiceData;
	}
	public List<DataSend> getChoiceData() {
		return this.choiceData;
	}
	
	public void setS(int s) {
		this.S = s;
	}
	public int getS() {
		return this.S;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getPid() {
		return this.pid;
	}
}
