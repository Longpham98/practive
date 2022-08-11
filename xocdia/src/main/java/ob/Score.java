package ob;

public class Score {
	private int typeScore;
	
	public Score() {
		typeScore = -1;
	}
	
	public int compare(Score s) {
		if(this.getTypeScore() == s.getTypeScore()) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public int compare(int score) {
		if(this.getTypeScore() == score) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public int getTypeScore() {
		return this.typeScore;
	}
	public void setTypeScore(int score) {
		this.typeScore = score;
	}
}
