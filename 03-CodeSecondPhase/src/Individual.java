
import java.util.Random;

public class Individual {
	boolean [] chromosome;
	double fitness = 0;
	boolean isrunning = false;
	static int counter;
	static double sum = 0;
	static Random rand = new Random();
	private int w1;
	private int w3;
	private int w4;
	private int w2;
	private int w5;
	private int w6;
	String str;
	public void setisrunning(boolean isrunning){
		this.isrunning = isrunning;
	}
	public boolean getisrunning(){
		return isrunning;
	}
	 Individual(boolean[] chromosome) {
		counter++;
		this.chromosome = chromosome;
		int x = 0;
		int y = 0;
		int z=0;
		int q =0;
		int w = 0;
		int v = 0;
		for(int i = 0;i<8;i++){
			x += (toInt(chromosome)[i])*Math.pow(2,7-i);
			y += (toInt(chromosome)[8+i])*Math.pow(2,7-i);
			z += (toInt(chromosome)[16+i])*Math.pow(2,7-i);
			q += (toInt(chromosome)[24+i])*Math.pow(2,7-i);
			w += (toInt(chromosome)[32+i])*Math.pow(2,7-i);
			v += (toInt(chromosome)[40+i])*Math.pow(2,7-i);
		}
		w1=x;
		w2=q;
		w3=y;
		w4=z;
		w5=w;
		w6=v;
		str=w1+"|"+w2+"|"+w3+"|"+w4+"|"+w5+"|"+w6;

		
		this.fitness = calcFitness(chromosome);
		
	}
	public String getstr(){
		return str;
	}
	
	public int [] toInt(boolean [] chrom){
		int [] samechrom = new int [chrom.length]; 
		for(int i = 0; i< chrom.length;i++){
			if(chrom[i]){
				samechrom[i]=1;
			}
			else{
				samechrom[i]=0;
			}
		}
		return samechrom;
	}

	public boolean[] getChromosome() {
		return chromosome;
	}

	public void setChromosome(boolean[] chromosome) {
		this.chromosome = chromosome;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {

		this.fitness = fitness;
	}

	public String genoToPhenotype() {
		StringBuilder builder = new StringBuilder();
		builder.append(chromosome);
		return builder.toString();
	}

	public Individual clone() {
		boolean[] chromClone = new boolean[chromosome.length];
		for (int i = 0; i < chromClone.length; i++) {
			chromClone[i] = chromosome[i];
		}
		return new Individual(chromClone);
	}

	public double calcFitness(boolean[] chromosome) {
		int i=0;
        for(i = 0;i<100;i++){
		isrunning=true;
        GAgame game = new GAgame(this);
        game.setField();
        GAbot bot = new GAbot(game);
		bot.setw1(w1/1000.0);
		bot.setw3(w3/1000.0);
		bot.setw4(w4/1000.0);
		bot.setw2(w2/1000.0);
		bot.setw5(w5/1000.0);
		bot.setw6(w6/1000.0);
        game.setBot(bot);
        game.game();
		while(isrunning==true){
			System.out.print("");
		}

		}
		System.out.println(GAgame.averagefitness);
		sum = GAgame.averagefitness;
		GAgame.fitness=0;
		return sum;
	}

	public Individual crossOver(Individual partner) {
		boolean[] child = new boolean[chromosome.length];
		for (int i = 0; i < child.length; i++) {
			int tempRand = rand.nextInt(2);
			if (tempRand == 1) {
				child[i] = chromosome[i];
			} else {
				child[i] = partner.getChromosome()[i];
			}
		}
		return new Individual(child);
	}

	public void mutateIndividual() {
		int tempRand = rand.nextInt(chromosome.length);
		if(chromosome[tempRand]==true){
			chromosome[tempRand]=false;
		}
		else{
			chromosome[tempRand]=true;
		}
	}

}
