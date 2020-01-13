package pack;

import java.util.Random;

public class Ingredient {
	
/* This is a list of strings which contains the names of the ingredients  */
	private String[] listIngredients = {"musetel", "busuioc", "chimion", "cimbru", "ciulin", "dracila",
			"fenic", "hamei", "in", "marar"};
	
/* This variable stores the name of the ingredient that the demon produces */
	private String name;

	
	public Ingredient()
	{
		getIngredient();
	}
	/*  This method gets a random ingredient from the list  */
	public String getIngredient()
	{
		Random random = new Random();
		int index = random.nextInt(listIngredients.length);
		this.name = listIngredients[index];
		return this.name;
	}
	
	public String getName()
	{
		return this.name;
	}
}
