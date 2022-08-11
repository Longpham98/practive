package ob;

import java.io.Serializable;

public class PlayFinishTrans implements Serializable {
	private String N;
	private Long AG = 0L;
	private Long M = 0L;
	private Field typeScore;
	
	
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
	public Field getTypeScore() {
		return typeScore;
	}
	public void setTypeScore(Field typeScore) {
		this.typeScore = typeScore;
	}
	public Long getM() {
		return M;
	}
	public void setM(Long m) {
		this.M = m;
	}
}
