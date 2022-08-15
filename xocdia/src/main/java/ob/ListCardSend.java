package ob;

public class ListCardSend {
	private String evt;
	private int[] arr;
	private int playerNum;
	private int typeScore;
	
	
	
	public ListCardSend(String evt, int[] arr, int playerNum, int typeScore) {
		
		this.evt = evt;
		this.arr = arr;
		this.playerNum = playerNum;
		this.typeScore = typeScore;
	}
	
	
	public void setEvt(String evt) {
		this.evt = evt;
	}
	public String getEvt() {
		return this.evt;
	}
	
	public void setArr(int[] arr) {
		this.arr = arr;
	}
	public int[] getArr() {
		return this.arr;
	}
	
	public void setPlayerNum(int number) {
		this.playerNum = number;
	}
	public int getPlayerNum() {
		return this.playerNum;
	}
	
	public void setTypeScore(int typeScore) {
		this.typeScore = typeScore;
	}
	public int getTypeScore() {
		return this.typeScore;
	}
	
}
