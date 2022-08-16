package ob;

import java.util.List;

public class BlindTrans {
	private String N;
	private int pid;
	private String evt;
	private long AG;
//	private List<Integer> option;
	private List<DataSend> option;
	
	public BlindTrans() {
		N = "";
		AG = 0l;
	}
	public String getN() {
		return this.N;
	}
	public void setN(String n) {
		this.N = n;
	}
	
	public String getEvt() {
		return this.evt;
	}
	
	public void setEvt(String evt) {
		this.evt = evt;
	}
	
	public long getAG() {
		return this.AG;
	}
	
	public void setAG(long ag) {
		this.AG = ag;
	}
	
//	public List<Integer> getOption() {
//		return this.option;
//	}
//	public void setOption(List<Integer> option) {
//		this.option = option;
//	}
//	
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getPid() {
		return this.pid;
	}
	
	public List<DataSend> getOption() {
		return this.option;
	}
	public void setOption(List<DataSend> option) {
		this.option = option;
	}
}
