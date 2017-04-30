package earthGrid;

/*
 * Author: Scott Perfetti
 * Class: CSE311
 * Professor: Dr Kiper
 * Description: This class acts like a datagrid. It takes a row/column and gets/sets the value
 * Date: 4/7/2017
 */

public class Grid {
	final double DEFAULT_VALUE = 15;
	final double MAX_WIDTH_DEGREES = 360;
	final double MAX_LENGTH_DEGREES = 180;
	final double EQUATOR_DEGREES = 90;
	public int height = 0;
	public int width = 0;
	
	GridCell[][] GridCell;
	
	public Grid(int width, int height){
		MakeGrid(width, height);
	}
	
	public void MakeGrid(int width, int height){
		MakeGrid(width,height,DEFAULT_VALUE);
	}
	
	public void MakeGrid(int width, int height, double DefaultValue){
		this.height = height;
		this.width = width;
		
		GridCell = new GridCell[width][height];
				
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				InitializeGridCell(x,y,DefaultValue);
			}
		}
	}
	
	private void InitializeGridCell(int X, int Y, double DefaultValue){
		//Figure the angles out and initialize the grid cell
		//NOTE: This leaves the edges alone. This will be wrapped together later
		double AngleX = 0;
		double AngleY = 0;
				
		if(X > 0 && X < this.width){
			AngleX = (X / (double)(this.width - 1)) * MAX_WIDTH_DEGREES;
			//Make 90 the center point
			AngleY = EQUATOR_DEGREES - ((Y / (double)(this.height - 1)) * MAX_LENGTH_DEGREES);
		}
		
		GridCell LocalGridCell = new GridCell(AngleX, AngleY, DefaultValue);
		GridCell[X][Y] = LocalGridCell;
	}
	
	public double GetValue(int X, int Y){
		return GridCell[X][Y].GetTemperature();
	}
	
	public void SetValue(int X, int Y, double Value){
		GridCell[X][Y].SetTemperature(Value); 
	}
	
	public int height(){
		return height;
	}
	
	public int width(){
		return width;
	}
	
	public void deepCopyFrom(Grid InputGridCopyFrom){
		for (int x = 0; x <= InputGridCopyFrom.width - 1; x++) {
			 for (int y = 0; y <= InputGridCopyFrom.height - 1; y++) {
				 SetValue(x, y, InputGridCopyFrom.GetValue(x, y));
			 }
		 }
	}
	
	public Grid deepCopy(){
		Grid Return = new Grid(this.width, this.height);
		for (int x = 0; x <= this.width - 1; x++) {
			 for (int y = 0; y <= this.height - 1; y++) {
				 Return.SetValue(x, y, this.GetValue(x, y));
			 }
		 }
		return Return;
	}
	
	public void WrapGrid(){
		//This takes the grid's outer edges and properly wraps them to the correct position
		//on the other side of the earth
		//The end column - 1  column goes to the 0 column
		for(int y = 0; y <= this.height - 1; y++){
			GridCell[0][y].SetTemperature(GridCell[this.width - 2][y].GetTemperature());
		}
		//The 1 column goes to the end column 
		for(int y = 0; y <= this.height - 1; y++){
			GridCell[this.width - 1][y].SetTemperature(GridCell[1][y].GetTemperature());
		}
		//wrap the top of the grid
		for(int x = 0; x < this.width / 2; x++){
			GridCell[x][0].SetTemperature(GridCell[x + (int)(this.width / 2)][1].GetTemperature());
			GridCell[x][this.height - 1].SetTemperature(GridCell[x + (int)(this.width / 2)][this.height - 2].GetTemperature());
		}
		//wrap the bottom of the grid
		for(int x = (int)(this.width / 2); x < this.width; x++){
			GridCell[x][0].SetTemperature(GridCell[x - (int)(this.width / 2)][1].GetTemperature());
			GridCell[x][this.height - 1].SetTemperature(GridCell[x - (int)(this.width / 2)][this.height - 2].GetTemperature());
		}
	}
	
	public GridCell GetCell(int X, int Y){
		return GridCell[X][Y];
	}
}
