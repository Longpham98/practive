package ob;

import java.util.List;

public class SesKuFunctions {
	
	public static Field checkResult(List<Card> listCard) {
		int trangCount = 0;
		int doCount = 0;
		
		for(int i=0; i<listCard.size(); i++) {
			if(listCard.get(i).getN() == 0) {
				trangCount ++ ;
			}
			if(listCard.get(i).getN() == 1) {
				doCount ++ ;
			}
		}
		if(trangCount == 4) {
			return Field.BON_TRANG;
		} else if(trangCount == 3) {
			return Field.BA_TRANG;
		} else if(trangCount == 2) {
			return Field.EVEN;
		} else if(trangCount == 1) {
			return Field.BA_DO;
		} else if(trangCount == 0) {
			return Field.BON_DO;
		}
		
		return null;
	}
	
}
