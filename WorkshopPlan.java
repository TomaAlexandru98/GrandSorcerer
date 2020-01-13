package pack;

public class WorkshopPlan {

	private int numberCovens;
	private int numberWitches;
	Workshop workshop;
	
	public WorkshopPlan(int numberCovens, int numberWitches)
	{
		this.numberCovens = numberCovens;
		this.numberWitches = numberWitches;
		
		CreateWorkshop();
	}
	
	public void CreateWorkshop()
	{
		this.workshop = new Workshop(numberCovens, numberWitches);
		workshop.start();
	}
}
