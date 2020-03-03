public class Item
{

	private String description , longdesc;
	private double weight , price;

	public Item(String des,double wei,String lon )
	{
		description=des;
		this.weight=wei;
		price      = 0;
		longdesc   = lon ; 
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
	public String GetLongDescription()
	{
		return longdesc;
	}
}