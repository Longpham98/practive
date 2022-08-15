package ob;

import java.util.ArrayList;
import java.util.List;

public class DataSend {
	private Long tiencuoc;
//	private List<Integer> listChoice = new ArrayList<>();
	private int typeScore;
	
	public DataSend(Long tiencuoc,int typeScore) {
		this.tiencuoc = tiencuoc;
		this.typeScore= typeScore;
	}
	
	public Long getTiencuoc() {
		return this.tiencuoc;
	}
	public void setTiencuoc(Long tiencuoc) {
		this.tiencuoc = tiencuoc;
	}
	
//	public List<Integer> getListChoice() {
//		return this.listChoice;
//	}
//	public void setListChoice(List<Integer> listchoice) {
//		this.listChoice = listchoice;
//	}
	
	public void setTypeScore (int typeScore) {
		this.typeScore = typeScore;
	}
	public int getTypeScore () {
		return this.typeScore;
	}
	
	

}
