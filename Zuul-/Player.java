import java.util.ArrayList;
import java.util.List;

public class Player{

	private double 		life ;
	private String 		name ;
	private Room 		currentRoom; 
	private List<Item> 	item ;
	private Double 		maxWeight;
	private Double 		weight ;

	public Player()
	{
		item 				= new ArrayList<Item>();
		life				= 100.0;
		maxWeight 			= 20.0;
		weight 				= 0.0;
	}

	public void SetRoom(Room next)
	{
		this.currentRoom	=	next ;
	}

	public Room GetRoom()
	{
		return  currentRoom;
	}
	public void AddItem(Item ele)
	{	if(ele.GetWeight()+weight > maxWeight)
			System.out.println("you cannot take this item you have to drop");
		else
		{			
			item.add(ele);
			weight  		+= ele.GetWeight();
		}	
	}

	public String ShowItem()
	{	String s = new String();
		for (Item e : item) {
			s+=e.GetDescription()+" "+ e.GetWeight()+"\n";
		}
		s+="you have a weight of "+weight+"\n";
		return s;
	}

	public Item DropItem(String ele)
	{	for(Item e : item)
			if(e.GetDescription().equals(ele))
			{	
				weight -= e.GetWeight();
				item.remove(item.indexOf(e));
				return e;
			}
		return null;
	}


	public String cookie()
	{		String s =new String();
			for(Item e : item)
				if(e.GetDescription().equals("Cookie"))
				{	
					maxWeight	+= maxWeight/4;
					s+="now you have a capacity of "+maxWeight+" kg\n";
					item.remove(item.indexOf(e));
					return s ;
				}
			return "you dont have any cookie\n";	
	}
} 