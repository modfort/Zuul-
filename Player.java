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

	public void ShowItem()
	{
		for (Item e : item) {
			System.out.println(e.GetDescription()+" "+ e.GetWeight());
		}
		System.out.println("you have a weight of "+weight);
	}

	public Item DropItem(String ele)
	{	for(Item e : item)
			if(e.GetDescription().equals(ele))
			{	
				weight -= e.GetWeight();
				item.remove(item.indexOf(e));
				return e;
			}
		System.out.println("you don't have this item");
		return null;
	}


	public void cookie()
	{
			for(Item e : item)
				if(e.GetDescription().equals("Cookie"))
				{	
					maxWeight	+= maxWeight/4;
					System.out.println("now you have a capacity of "+maxWeight+" kg");
					item.remove(item.indexOf(e));
					return;
				}
			System.out.println("you dont have any cookie");	
	}
} 