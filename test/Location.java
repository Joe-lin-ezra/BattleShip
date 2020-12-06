import java.io.Serializable;

public class Location implements Serializable{
	int x;
	int y;
	Location(int i,int j){
		this.x=i;
		this.y=j;
	}
}