package ob;

import java.util.ArrayList;
import java.util.List;

public class TableInfo {
	
	private int id;
	private String N;
	private Integer M;
	private List<ItemPlayer> arrPlayer;
	private Integer AG;
	private Integer S;
	
	public TableInfo() {
		arrPlayer = new ArrayList<ItemPlayer>();
	}
	
	
	public Integer getAG() {
		return AG;
	}
	public void setAG(Integer aG) {
		AG = aG;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getN() {
		return N;
	}
	public void setN(String n) {
		this.N = n;
	}
	
	public Integer getM() {
		return M;
	}
	public void setM(Integer m) {
		this.M = m;
	}
	
	public Integer getS() {
		return S;
	}
	public void setS(Integer s) {
		this.S = s;
	}
	
	public List<ItemPlayer> getArrPlayer(){
		return arrPlayer;
	}
	public void setArrPlayer(List<ItemPlayer> arrP) {
		this.arrPlayer = arrP;
	}
	
	
}
