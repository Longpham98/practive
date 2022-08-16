package ob;

import java.util.List;

public class DataFinish {
	
	List<PlayFinishTrans> dataFinish;
	private int gameResult;
	
	public void setGameResult(int result) {
		this.gameResult = result;
	}
	public int getGameResult() {
		return this.gameResult;
	}
	
	public void setDataFinish(List<PlayFinishTrans> data) {
		this.dataFinish = data;
	}
	public List<PlayFinishTrans> getDataFinish() {
		return this.dataFinish;
	}
	
}
