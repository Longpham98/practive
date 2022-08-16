package ob;

import java.util.ArrayList;
import java.util.List;

import com.dst.GameUtil;
import com.google.gson.JsonObject;

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
	
	public static void main(String[] args) {
		List<DataSend> abc  = new ArrayList<DataSend>();
		abc.add(new DataSend(1233l, 2));
		abc.add(new DataSend(1222l, 1));
		JsonObject jo = new JsonObject();
		jo.add("data", GameUtil.gson.toJsonTree(abc));
		List<DataSend> listChoice = new ArrayList<>();
		DataSend [] arrReturn = GameUtil.gson.fromJson(jo.get("data"), DataSend [].class);
		
		for(int i=0; i< arrReturn.length; i++) {
			listChoice.add(arrReturn[i]);
		}

//		List<DataSend> abc  = new ArrayList<DataSend>();
//		abc.add(new DataSend(1233l, 2));
//		abc.add(new DataSend(1222l, 1));
//		
//		List<List<DataSend>> lst = new ArrayList<List<DataSend>>();
//		lst.add(abc);
		
		System.out.println(GameUtil.gson.toJson(arrReturn));
		System.out.println(GameUtil.gson.toJson(listChoice));
		for (DataSend choice : listChoice) {
			System.out.println(choice.tiencuoc);
			System.out.println(choice.typeScore);
		}
	}

}
