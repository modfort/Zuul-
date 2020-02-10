public class Item
{

	private String description;
	private double weight;

	public Item(String des,double wei)
	{
		description=des;
		this.weight=wei;
	}

	public String ToString()
	{
		return description+" "+weight;
	}

	public double GetWeight()
	{
		return weight;
	}

	public String GetDescription()
	{
		return description;
	}

}