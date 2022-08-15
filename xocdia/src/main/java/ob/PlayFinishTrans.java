package ob;

import java.io.Serializable;
import java.util.List;

public class PlayFinishTrans implements Serializable {
	private String N;
	private Long AG = 0L;
	private Long M = 0L;
	private List<Integer> typeScore;
	private List<DataSend> choiceData;
	
	
	public void incrementM(Long m) {
		this.M += m;
	}
	
	public String getN() {
		return N;
	}
	public void setN(String n) {
		this.N = n;
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
}
