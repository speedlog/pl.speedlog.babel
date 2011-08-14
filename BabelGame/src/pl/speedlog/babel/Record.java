package pl.speedlog.babel;

public class Record implements Comparable<Record> {
	
	private String nick;
	private int points;
	
	public Record(String nick, int points)
	{
		this.nick=nick;
		this.points=points;
	}

	@Override
	public int compareTo(Record arg0) {
		
		return points-arg0.points;
	}
	
	@Override
	public String toString()
	{
		return nick+"\t"+points+"\n";
	}
	
	/**
	 * Getter points
	 * @return
	 */
	public int GetPoints()
	{
		return points;
	}

}
