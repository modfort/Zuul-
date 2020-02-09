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
		this.name			= name;
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
			weight+=ele.GetWeight();
		}	
	}

	public Item DropItem(Item ele)
	{	
		item.remove(item.indexOf(ele));
		return ele;
	}
} 