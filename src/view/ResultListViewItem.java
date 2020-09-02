package view;

public class ResultListViewItem {
	
	private String name;
	
	public ResultListViewItem(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
