package ob;

public class Card implements Comparable<Card> {
	private int N;
	

	public Card(int n) {
		this.N = n;
	}

	@Override
	public int compareTo(Card card) {
		// TODO Auto-generated method stub
		return this.getN() == card.getN() ? 1 : -1 ;
		
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	
	
}
